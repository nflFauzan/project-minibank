package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.StatusTransaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupervisorTransactionApprovalService {

    private final TransaksiRepository transaksiRepository;
    private final RekeningRepository rekeningRepository;

    public long pendingCount() {
        return listPendingTransactions().size();
    }

    public Transaksi getByIdOrThrow(UUID id) {
        return transaksiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaksi tidak ditemukan: " + id));
    }

    public List<Transaksi> listPendingTransactions() {
        List<Transaksi> all = transaksiRepository.findByStatusOrderByProcessedAtDesc(StatusTransaksi.PENDING_APPROVAL);
        // Exclude the incoming leg of transfers to prevent double counting in approval queue
        return all.stream()
                .filter(tx -> tx.getTipe() != TipeTransaksi.TRANSFER
                        || tx.getKeterangan() == null
                        || !tx.getKeterangan().toLowerCase().startsWith("transfer dari"))
                .toList();
    }

    @Transactional
    public void approve(UUID txId, String supervisorUsername, String notes) {
        Transaksi tx = getByIdOrThrow(txId);
        if (tx.getStatus() != StatusTransaksi.PENDING_APPROVAL) {
            throw new IllegalStateException("Transaksi tidak berstatus PENDING_APPROVAL.");
        }

        LocalDateTime now = LocalDateTime.now();

        if (tx.getTipe() == TipeTransaksi.DEPOSIT) {
            Rekening r = rekeningRepository.findByNomorRekeningForUpdate(tx.getNomorRekening())
                    .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan."));
            BigDecimal sebelum = r.getSaldo() != null ? r.getSaldo() : BigDecimal.ZERO;
            BigDecimal sesudah = sebelum.add(tx.getJumlah());
            r.setSaldo(sesudah);
            rekeningRepository.save(r);

            tx.setSaldoSebelum(sebelum);
            tx.setSaldoSesudah(sesudah);
            tx.setStatus(StatusTransaksi.SUCCESS);
            tx.setApprovedBy(supervisorUsername);
            tx.setApprovedAt(now);
            tx.setApprovalNotes(notes);
            transaksiRepository.save(tx);

        } else if (tx.getTipe() == TipeTransaksi.WITHDRAWAL) {
            Rekening r = rekeningRepository.findByNomorRekeningForUpdate(tx.getNomorRekening())
                    .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan."));
            BigDecimal sebelum = r.getSaldo() != null ? r.getSaldo() : BigDecimal.ZERO;
            if (sebelum.compareTo(tx.getJumlah()) < 0) {
                throw new IllegalStateException("Saldo rekening tidak mencukupi untuk penarikan.");
            }
            BigDecimal sesudah = sebelum.subtract(tx.getJumlah());
            r.setSaldo(sesudah);
            rekeningRepository.save(r);

            tx.setSaldoSebelum(sebelum);
            tx.setSaldoSesudah(sesudah);
            tx.setStatus(StatusTransaksi.SUCCESS);
            tx.setApprovedBy(supervisorUsername);
            tx.setApprovedAt(now);
            tx.setApprovalNotes(notes);
            transaksiRepository.save(tx);

        } else if (tx.getTipe() == TipeTransaksi.TRANSFER) {
            List<Transaksi> group = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(tx.getGroupId());
            for (Transaksi t : group) {
                Rekening r = rekeningRepository.findByNomorRekeningForUpdate(t.getNomorRekening())
                        .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan: " + t.getNomorRekening()));
                BigDecimal sebelum = r.getSaldo() != null ? r.getSaldo() : BigDecimal.ZERO;
                BigDecimal sesudah;
                if (t.getKeterangan() != null && t.getKeterangan().toLowerCase().startsWith("transfer ke")) {
                    if (sebelum.compareTo(t.getJumlah()) < 0) {
                        throw new IllegalStateException("Saldo rekening sumber tidak mencukupi.");
                    }
                    sesudah = sebelum.subtract(t.getJumlah());
                } else {
                    sesudah = sebelum.add(t.getJumlah());
                }
                r.setSaldo(sesudah);
                rekeningRepository.save(r);

                t.setSaldoSebelum(sebelum);
                t.setSaldoSesudah(sesudah);
                t.setStatus(StatusTransaksi.SUCCESS);
                t.setApprovedBy(supervisorUsername);
                t.setApprovedAt(now);
                t.setApprovalNotes(notes);
                transaksiRepository.save(t);
            }
        }
    }

    @Transactional
    public void reject(UUID txId, String supervisorUsername, String reason, String notes) {
        Transaksi tx = getByIdOrThrow(txId);
        if (tx.getStatus() != StatusTransaksi.PENDING_APPROVAL) {
            throw new IllegalStateException("Transaksi tidak berstatus PENDING_APPROVAL.");
        }

        LocalDateTime now = LocalDateTime.now();

        if (tx.getTipe() == TipeTransaksi.TRANSFER) {
            List<Transaksi> group = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(tx.getGroupId());
            for (Transaksi t : group) {
                t.setStatus(StatusTransaksi.REJECTED);
                t.setApprovedBy(supervisorUsername);
                t.setApprovedAt(now);
                t.setRejectionReason(reason);
                t.setApprovalNotes(notes);
                transaksiRepository.save(t);
            }
        } else {
            tx.setStatus(StatusTransaksi.REJECTED);
            tx.setApprovedBy(supervisorUsername);
            tx.setApprovedAt(now);
            tx.setRejectionReason(reason);
            tx.setApprovalNotes(notes);
            transaksiRepository.save(tx);
        }
    }
}
