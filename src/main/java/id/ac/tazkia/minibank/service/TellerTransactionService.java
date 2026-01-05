package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TellerTransactionService {

    private final RekeningRepository rekeningRepository;
    private final TransaksiRepository transaksiRepository;
    private final UserRepository userRepository;

    private String fullNameOrUsername(String username) {
        return userRepository.findByUsername(username)
                .map(u -> (u.getFullName() == null || u.getFullName().isBlank()) ? username : u.getFullName())
                .orElse(username);
    }

    private String nextNomorTransaksi() {
        long seq = transaksiRepository.nextNo();      // 3000000...
        return "T" + seq;                             // T3000588
    }

    @Transactional
    public Transaksi deposit(String rekeningNo, BigDecimal amount, String note, String username) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Jumlah harus > 0");

        Rekening r = rekeningRepository.findByNomorRekeningForUpdate(rekeningNo)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan"));

        BigDecimal before = r.getSaldo();
        BigDecimal after = before.add(amount);
        r.setSaldo(after);

        String nomor = nextNomorTransaksi();
        Transaksi t = new Transaksi();
        t.setGroupId(UUID.randomUUID());
        t.setNomorTransaksi(nomor);
        t.setTipe(TipeTransaksi.DEPOSIT);
        t.setRekening(r);
        t.setJumlah(amount);
        t.setSaldoSebelum(before);
        t.setSaldoSesudah(after);
        t.setKeterangan((note == null || note.isBlank()) ? "Setoran Tunai" : note.trim());
        t.setProcessedBy(fullNameOrUsername(username));

        rekeningRepository.save(r);
        return transaksiRepository.save(t);
    }

    @Transactional
    public Transaksi withdrawal(String rekeningNo, BigDecimal amount, String note, String username) {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Jumlah harus > 0");

        Rekening r = rekeningRepository.findByNomorRekeningForUpdate(rekeningNo)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan"));

        BigDecimal before = r.getSaldo();
        if (before.compareTo(amount) < 0) throw new IllegalStateException("Saldo tidak cukup");

        BigDecimal after = before.subtract(amount);
        r.setSaldo(after);

        String nomor = nextNomorTransaksi();
        Transaksi t = new Transaksi();
        t.setGroupId(UUID.randomUUID());
        t.setNomorTransaksi(nomor);
        t.setTipe(TipeTransaksi.WITHDRAWAL);
        t.setRekening(r);
        t.setJumlah(amount.negate()); // keluar = negatif (biar list gampang)
        t.setSaldoSebelum(before);
        t.setSaldoSesudah(after);
        t.setKeterangan((note == null || note.isBlank()) ? "Penarikan Tunai" : note.trim());
        t.setProcessedBy(fullNameOrUsername(username));

        rekeningRepository.save(r);
        return transaksiRepository.save(t);
    }

    @Transactional
    public List<Transaksi> transfer(String fromNo, String toNo, BigDecimal amount, String note, String username) {
        if (fromNo == null || toNo == null || fromNo.equals(toNo)) {
            throw new IllegalArgumentException("Rekening sumber & tujuan harus berbeda");
        }
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Jumlah harus > 0");

        // lock dua rekening sekaligus (minim deadlock)
        List<String> nos = new ArrayList<>(List.of(fromNo, toNo));
        nos.sort(String::compareTo);

        List<Rekening> locked = rekeningRepository.findByNomorRekeningInForUpdate(nos);
        if (locked.size() != 2) throw new EntityNotFoundException("Rekening sumber/tujuan tidak ditemukan");

        Rekening a = locked.get(0).getNomorRekening().equals(fromNo) ? locked.get(0) : locked.get(1);
        Rekening b = locked.get(0).getNomorRekening().equals(toNo) ? locked.get(0) : locked.get(1);

        BigDecimal beforeA = a.getSaldo();
        if (beforeA.compareTo(amount) < 0) throw new IllegalStateException("Saldo rekening sumber tidak cukup");

        BigDecimal afterA = beforeA.subtract(amount);
        BigDecimal beforeB = b.getSaldo();
        BigDecimal afterB = beforeB.add(amount);

        a.setSaldo(afterA);
        b.setSaldo(afterB);

        String nomor = nextNomorTransaksi();
        UUID groupId = UUID.randomUUID();
        String by = fullNameOrUsername(username);
        String baseNote = (note == null || note.isBlank()) ? "Transfer Dana" : note.trim();

        // OUT (sumber)
        Transaksi out = new Transaksi();
        out.setGroupId(groupId);
        out.setNomorTransaksi(nomor);
        out.setTipe(TipeTransaksi.TRANSFER);
        out.setRekening(a);
        out.setJumlah(amount.negate());
        out.setSaldoSebelum(beforeA);
        out.setSaldoSesudah(afterA);
        out.setKeterangan(baseNote + " ke " + toNo);
        out.setProcessedBy(by);

        // IN (tujuan)
        Transaksi in = new Transaksi();
        in.setGroupId(groupId);
        in.setNomorTransaksi(nomor);
        in.setTipe(TipeTransaksi.TRANSFER);
        in.setRekening(b);
        in.setJumlah(amount);
        in.setSaldoSebelum(beforeB);
        in.setSaldoSesudah(afterB);
        in.setKeterangan(baseNote + " dari " + fromNo);
        in.setProcessedBy(by);

        rekeningRepository.save(a);
        rekeningRepository.save(b);
        transaksiRepository.save(out);
        transaksiRepository.save(in);

        return List.of(out, in);
    }
}
