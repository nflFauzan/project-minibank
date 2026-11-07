package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.passbook.*;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.PassbookRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class PassbookService {

    private static final Logger log = LoggerFactory.getLogger(PassbookService.class);

    private final PassbookRepository passbookRepository;
    private final RekeningRepository rekeningRepository;
    private final TransaksiRepository transaksiRepository;
    private final NasabahRepository nasabahRepository;

    public PassbookService(PassbookRepository passbookRepository,
                           RekeningRepository rekeningRepository,
                           TransaksiRepository transaksiRepository,
                           NasabahRepository nasabahRepository) {
        this.passbookRepository = passbookRepository;
        this.rekeningRepository = rekeningRepository;
        this.transaksiRepository = transaksiRepository;
        this.nasabahRepository = nasabahRepository;
    }

    @Transactional(readOnly = true)
    public Rekening findRekening(String accountIdentifier) {
        if (accountIdentifier == null || accountIdentifier.isBlank()) {
            throw new EntityNotFoundException("Account identifier cannot be null or empty");
        }

        // 1. Try finding by nomor rekening
        Optional<Rekening> byNomor = rekeningRepository.findByNomorRekening(accountIdentifier);
        if (byNomor.isPresent()) {
            return byNomor.get();
        }

        // 2. Try finding by Long ID if numeric
        if (accountIdentifier.matches("\\d+")) {
            try {
                Long id = Long.parseLong(accountIdentifier);
                Optional<Rekening> byId = rekeningRepository.findById(id);
                if (byId.isPresent()) {
                    return byId.get();
                }
            } catch (NumberFormatException ignored) {
            }
        }

        // 3. Try finding by Passbook UUID
        try {
            UUID uuid = UUID.fromString(accountIdentifier);
            Optional<Passbook> byPb = passbookRepository.findById(uuid);
            if (byPb.isPresent()) {
                return byPb.get().getRekening();
            }
        } catch (IllegalArgumentException ignored) {
        }

        throw new EntityNotFoundException("Account not found for identifier: " + accountIdentifier);
    }

    public Passbook getOrCreatePassbook(Rekening rekening) {
        return passbookRepository.findByRekening(rekening).orElseGet(() -> {
            String pbNumber;
            try {
                pbNumber = passbookRepository.formatNextPassbookNumber();
            } catch (Exception e) {
                log.warn("Sequence passbook_no_seq failed, falling back to ID based: {}", e.getMessage());
                pbNumber = String.format("PB%07d", rekening.getId() != null ? rekening.getId() : System.currentTimeMillis() % 10000000);
            }

            Passbook newPb = new Passbook(
                    UUID.randomUUID(),
                    rekening,
                    rekening.getNomorRekening(),
                    pbNumber,
                    1,
                    0,
                    30,
                    "ACTIVE",
                    null,
                    LocalDateTime.now(),
                    null
            );

            return passbookRepository.save(newPb);
        });
    }

    @Transactional(readOnly = true)
    public PassbookStatusResponse getStatus(String accountIdentifier) {
        Rekening rekening = findRekening(accountIdentifier);
        Passbook pb = getOrCreatePassbook(rekening);
        long unprintedCount = transaksiRepository.countByRekeningAndPrintedFalse(rekening);

        return new PassbookStatusResponse(
                true,
                pb.getPassbookNumber(),
                pb.getCurrentPage(),
                pb.getLastPrintedLine(),
                pb.getRemainingLines(),
                unprintedCount,
                pb.getStatus(),
                pb.getLastPrintDate()
        );
    }

    @Transactional(readOnly = true)
    public PassbookPrintDataResponse getPrintData(String accountIdentifier) {
        Rekening rekening = findRekening(accountIdentifier);
        Passbook pb = getOrCreatePassbook(rekening);
        Nasabah nasabah = rekening.getNasabah();

        List<Transaksi> unprinted = transaksiRepository.findByRekeningAndPrintedFalseOrderByProcessedAtAsc(rekening);

        List<PassbookPrintDataResponse.TransactionDto> txDtos = new ArrayList<>();
        int startLine = pb.getLastPrintedLine() != null ? pb.getLastPrintedLine() : 0;

        for (int i = 0; i < unprinted.size(); i++) {
            Transaksi t = unprinted.get(i);
            int lineNum = startLine + i + 1;

            BigDecimal debit = null;
            BigDecimal credit = null;

            if (t.getTipe() == TipeTransaksi.WITHDRAWAL) {
                debit = t.getJumlah() != null ? t.getJumlah().abs() : BigDecimal.ZERO;
            } else {
                credit = t.getJumlah() != null ? t.getJumlah().abs() : BigDecimal.ZERO;
            }

            String sandi = t.getSandiCode();
            if (sandi == null || sandi.isBlank()) {
                if (t.getTipe() == TipeTransaksi.DEPOSIT) {
                    sandi = "52";
                } else if (t.getTipe() == TipeTransaksi.WITHDRAWAL) {
                    sandi = "26";
                } else if (t.getTipe() == TipeTransaksi.TRANSFER) {
                    sandi = "213";
                } else {
                    sandi = "99";
                }
            }

            txDtos.add(new PassbookPrintDataResponse.TransactionDto(
                    t.getId() != null ? t.getId().toString() : "",
                    t.getNomorTransaksi(),
                    t.getProcessedAt(),
                    t.getTipe() != null ? t.getTipe().name() : "DEPOSIT",
                    t.getKeterangan(),
                    debit,
                    credit,
                    t.getSaldoSesudah(),
                    sandi,
                    lineNum,
                    t.getProcessedByFullName() != null ? t.getProcessedByFullName() : ""
            ));
        }

        PassbookPrintDataResponse.AccountDto accDto = new PassbookPrintDataResponse.AccountDto(
                rekening.getId() != null ? rekening.getId().toString() : pb.getId().toString(),
                rekening.getNomorRekening(),
                rekening.getNamaNasabah() != null ? rekening.getNamaNasabah() : "",
                rekening.getProduk() != null ? rekening.getProduk() : "Tabungan Wadiah Basic",
                rekening.getSaldo(),
                rekening.getTanggalPembukaan() != null ? rekening.getTanggalPembukaan() : LocalDate.now(),
                rekening.isStatusActive() ? "ACTIVE" : "CLOSED"
        );

        PassbookPrintDataResponse.CustomerDto custDto = new PassbookPrintDataResponse.CustomerDto(
                nasabah != null && nasabah.getId() != null ? nasabah.getId().toString() : "",
                rekening.getCifNasabah() != null ? rekening.getCifNasabah() : (nasabah != null ? nasabah.getCif() : ""),
                rekening.getNamaNasabah() != null ? rekening.getNamaNasabah() : (nasabah != null ? nasabah.getNamaLengkap() : ""),
                "PERSONAL"
        );

        PassbookPrintDataResponse.PassbookDto pbDto = new PassbookPrintDataResponse.PassbookDto(
                pb.getId().toString(),
                pb.getPassbookNumber(),
                pb.getCurrentPage(),
                pb.getLastPrintedLine(),
                pb.getLinesPerPage(),
                pb.getRemainingLines(),
                pb.getStatus(),
                pb.getLastPrintDate()
        );

        return new PassbookPrintDataResponse(
                accDto,
                custDto,
                pbDto,
                new PassbookPrintDataResponse.ConfigDto(),
                txDtos
        );
    }

    public NextPageResponse advanceToNextPage(String accountIdentifier) {
        try {
            Rekening rekening = findRekening(accountIdentifier);
            Passbook pb = getOrCreatePassbook(rekening);

            pb.setCurrentPage(pb.getCurrentPage() + 1);
            pb.setLastPrintedLine(0);
            pb.setLastPrintDate(LocalDateTime.now());
            passbookRepository.save(pb);

            return new NextPageResponse(true, pb.getCurrentPage(), null);
        } catch (Exception e) {
            log.error("Failed to advance passbook page for {}: {}", accountIdentifier, e.getMessage());
            return new NextPageResponse(false, null, "Failed to advance to next page");
        }
    }

    public boolean reportPrintResult(PrintResultRequest request) {
        if (request == null || request.getPrintedTransactionIds() == null || request.getPrintedTransactionIds().isEmpty()) {
            throw new IllegalArgumentException("No valid transaction IDs provided");
        }

        Rekening rekening = findRekening(request.getAccountId());
        Passbook pb = getOrCreatePassbook(rekening);

        List<Transaksi> updated = new ArrayList<>();
        int currentLine = pb.getLastPrintedLine() != null ? pb.getLastPrintedLine() : 0;

        for (String idStr : request.getPrintedTransactionIds()) {
            try {
                UUID txId = UUID.fromString(idStr);
                Optional<Transaksi> opt = transaksiRepository.findById(txId);
                if (opt.isPresent()) {
                    Transaksi t = opt.get();
                    currentLine++;
                    t.setPrinted(true);
                    t.setPrintedAt(LocalDateTime.now());
                    t.setPrintedPage(pb.getCurrentPage());
                    t.setPrintedLine(currentLine);
                    if (t.getSandiCode() == null || t.getSandiCode().isBlank()) {
                        t.setSandiCode(t.getTipe() == TipeTransaksi.WITHDRAWAL ? "26" : "52");
                    }
                    updated.add(t);
                }
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (updated.isEmpty()) {
            throw new IllegalArgumentException("No valid transaction IDs provided");
        }

        transaksiRepository.saveAll(updated);

        pb.setLastPrintedLine(currentLine);
        pb.setLastPrintDate(LocalDateTime.now());
        passbookRepository.save(pb);

        return true;
    }

    public void initializePassbook(String accountIdentifier, int currentPage, int lastPrintedLine, String lastPrintedTransactionId) {
        Rekening rekening = findRekening(accountIdentifier);
        Passbook pb = getOrCreatePassbook(rekening);

        pb.setCurrentPage(Math.max(1, currentPage));
        pb.setLastPrintedLine(Math.max(0, Math.min(30, lastPrintedLine)));
        pb.setLastPrintDate(LocalDateTime.now());
        passbookRepository.save(pb);

        List<Transaksi> allTxs = transaksiRepository.findByRekeningOrderByProcessedAtAsc(rekening);

        if (lastPrintedTransactionId != null && !lastPrintedTransactionId.isBlank()) {
            UUID targetId;
            try {
                targetId = UUID.fromString(lastPrintedTransactionId);
            } catch (IllegalArgumentException e) {
                targetId = null;
            }

            boolean foundTarget = false;
            for (Transaksi t : allTxs) {
                if (!foundTarget) {
                    t.setPrinted(true);
                    t.setPrintedAt(LocalDateTime.now());
                    if (t.getId().equals(targetId)) {
                        foundTarget = true;
                    }
                } else {
                    t.setPrinted(false);
                    t.setPrintedAt(null);
                    t.setPrintedPage(null);
                    t.setPrintedLine(null);
                }
            }
        } else {
            // None selected -> Reset all to unprinted
            for (Transaksi t : allTxs) {
                t.setPrinted(false);
                t.setPrintedAt(null);
                t.setPrintedPage(null);
                t.setPrintedLine(null);
            }
        }

        transaksiRepository.saveAll(allTxs);
    }

    @Transactional(readOnly = true)
    public List<Rekening> listAccountsForPassbook(String search) {
        if (search != null && !search.isBlank()) {
            return rekeningRepository.search(search.trim(), "ACTIVE");
        }
        return rekeningRepository.findByStatus("ACTIVE");
    }

    @Transactional(readOnly = true)
    public List<Transaksi> getRecentTransactions(Rekening rekening, int limit) {
        List<Transaksi> all = transaksiRepository.findByRekeningOrderByProcessedAtDesc(rekening);
        if (all.size() > limit) {
            return all.subList(0, limit);
        }
        return all;
    }

    @Transactional(readOnly = true)
    public List<Transaksi> getTransactionsForPrint(Rekening rekening, LocalDate fromDate, LocalDate toDate) {
        if (fromDate != null && toDate != null) {
            return transaksiRepository.findByRekeningAndProcessedAtBetweenOrderByProcessedAtAsc(
                    rekening, fromDate.atStartOfDay(), toDate.atTime(LocalTime.MAX));
        } else if (fromDate != null) {
            return transaksiRepository.findByRekeningAndProcessedAtGreaterThanEqualOrderByProcessedAtAsc(
                    rekening, fromDate.atStartOfDay());
        } else if (toDate != null) {
            return transaksiRepository.findByRekeningAndProcessedAtLessThanEqualOrderByProcessedAtAsc(
                    rekening, toDate.atTime(LocalTime.MAX));
        } else {
            return transaksiRepository.findByRekeningOrderByProcessedAtAsc(rekening);
        }
    }
}
