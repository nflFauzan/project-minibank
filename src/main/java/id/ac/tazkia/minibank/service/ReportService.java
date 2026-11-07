package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.report.ReportSummaryDto;
import id.ac.tazkia.minibank.dto.report.ReportTransactionItemDto;
import id.ac.tazkia.minibank.entity.StatusTransaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransaksiRepository transaksiRepository;
    private final RekeningRepository rekeningRepository;
    private final NasabahRepository nasabahRepository;

    private static final Locale ID_LOCALE = new Locale("id", "ID");

    public ReportSummaryDto generateReport(String periodType, String dateStr, Integer month, Integer year) {
        String type = (periodType == null || periodType.isBlank()) ? "DAILY" : periodType.toUpperCase();

        LocalDate today = LocalDate.now();
        LocalDateTime start;
        LocalDateTime end;
        String periodLabel;
        String selectedDate = dateStr;
        Integer selectedMonth = month;
        Integer selectedYear = year;

        if ("MONTHLY".equals(type)) {
            int m = (month != null && month >= 1 && month <= 12) ? month : today.getMonthValue();
            int y = (year != null && year >= 2000 && year <= 2100) ? year : today.getYear();
            selectedMonth = m;
            selectedYear = y;
            YearMonth ym = YearMonth.of(y, m);
            start = ym.atDay(1).atStartOfDay();
            end = ym.atEndOfMonth().atTime(LocalTime.MAX);
            periodLabel = ym.format(DateTimeFormatter.ofPattern("MMMM yyyy", ID_LOCALE));
        } else if ("YEARLY".equals(type)) {
            int y = (year != null && year >= 2000 && year <= 2100) ? year : today.getYear();
            selectedYear = y;
            start = LocalDate.of(y, 1, 1).atStartOfDay();
            end = LocalDate.of(y, 12, 31).atTime(LocalTime.MAX);
            periodLabel = "Tahun " + y;
        } else {
            type = "DAILY";
            LocalDate targetDate;
            try {
                targetDate = (dateStr != null && !dateStr.isBlank()) ? LocalDate.parse(dateStr) : today;
            } catch (Exception e) {
                targetDate = today;
            }
            selectedDate = targetDate.toString();
            start = targetDate.atStartOfDay();
            end = targetDate.atTime(LocalTime.MAX);
            periodLabel = targetDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", ID_LOCALE));
        }

        List<Transaksi> rawList = transaksiRepository.findByProcessedAtBetweenOrderByProcessedAtAsc(start, end);

        // Filter: only successful (or legacy transactions with null status)
        List<Transaksi> validList = rawList.stream()
                .filter(t -> t.getStatus() == null || t.getStatus() == StatusTransaksi.SUCCESS)
                .toList();

        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalKredit = BigDecimal.ZERO;
        List<ReportTransactionItemDto> itemDtos = new ArrayList<>();

        for (Transaksi t : validList) {
            BigDecimal debit = null;
            BigDecimal kredit = null;
            String ket = t.getKeterangan() != null ? t.getKeterangan().toLowerCase() : "";

            if (t.getTipe() == TipeTransaksi.WITHDRAWAL) {
                debit = t.getJumlah();
                totalDebit = totalDebit.add(debit);
            } else if (t.getTipe() == TipeTransaksi.DEPOSIT) {
                kredit = t.getJumlah();
                totalKredit = totalKredit.add(kredit);
            } else if (t.getTipe() == TipeTransaksi.TRANSFER) {
                if (ket.startsWith("transfer ke")) {
                    debit = t.getJumlah();
                    totalDebit = totalDebit.add(debit);
                } else {
                    kredit = t.getJumlah();
                    totalKredit = totalKredit.add(kredit);
                }
            }

            itemDtos.add(ReportTransactionItemDto.builder()
                    .nomorTransaksi(t.getNomorTransaksi())
                    .processedAt(t.getProcessedAt())
                    .tipe(t.getTipe() != null ? t.getTipe().name() : "-")
                    .nomorRekening(t.getNomorRekening())
                    .namaRekening(t.getNamaRekening())
                    .debit(debit)
                    .kredit(kredit)
                    .processedBy(t.getProcessedByFullName() != null ? t.getProcessedByFullName() : t.getProcessedBy())
                    .keterangan(t.getKeterangan())
                    .build());
        }

        BigDecimal netFlow = totalKredit.subtract(totalDebit);
        long newAccounts = rekeningRepository.countByCreatedAtBetween(start, end);
        long newCustomers = nasabahRepository.countByCreatedAtBetween(start, end);

        return ReportSummaryDto.builder()
                .periodType(type)
                .periodLabel(periodLabel)
                .selectedDate(selectedDate)
                .selectedMonth(selectedMonth)
                .selectedYear(selectedYear)
                .startDate(start)
                .endDate(end)
                .totalTransactions(itemDtos.size())
                .totalDebit(totalDebit)
                .totalKredit(totalKredit)
                .netFlow(netFlow)
                .newAccountsCount(newAccounts)
                .newCustomersCount(newCustomers)
                .transactions(itemDtos)
                .build();
    }
}

