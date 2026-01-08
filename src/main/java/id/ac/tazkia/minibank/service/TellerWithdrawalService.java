package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TellerWithdrawalService {

    private final RekeningRepository rekeningRepository;
    private final TransaksiRepository transaksiRepository;
    private final UserRepository userRepository;

    public record WithdrawalResult(String nomorTransaksi, BigDecimal saldoBaru) {}

    @Transactional
    public WithdrawalResult withdraw(String nomorRekening,
                                     BigDecimal jumlah,
                                     String keterangan,
                                     String noReferensi,
                                     String usernameLogin) {

        if (jumlah == null) throw new IllegalArgumentException("Jumlah penarikan wajib diisi.");
        if (jumlah.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah penarikan tidak boleh 0.");
        }
        if (keterangan == null || keterangan.isBlank()) {
            throw new IllegalArgumentException("Keterangan wajib diisi.");
        }
        if (keterangan.length() > 500) {
            throw new IllegalArgumentException("Keterangan maksimal 500 karakter.");
        }
        if (noReferensi != null && noReferensi.length() > 100) {
            throw new IllegalArgumentException("Nomor referensi maksimal 100 karakter.");
        }

        Rekening r = rekeningRepository.findByNomorRekeningForUpdate(nomorRekening)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan."));

        if (!r.isStatusActive()) {
            throw new IllegalStateException("Rekening tidak aktif, tidak bisa diproses.");
        }

        BigDecimal saldoSebelum = (r.getSaldo() == null) ? BigDecimal.ZERO : r.getSaldo();

        // no overdraft
        if (saldoSebelum.compareTo(jumlah) < 0) {
            throw new IllegalStateException("Saldo tidak cukup.");
        }

        BigDecimal saldoSesudah = saldoSebelum.subtract(jumlah);

        r.setSaldo(saldoSesudah);
        rekeningRepository.save(r);

        long seq = transaksiRepository.nextSeq();
        String nomorTransaksi = "T2" + String.format("%06d", seq); // 2 = withdrawal

        String fullName = userRepository.findByUsername(usernameLogin)
                .map(u -> (u.getFullName() == null || u.getFullName().isBlank()) ? usernameLogin : u.getFullName())
                .orElse(usernameLogin);

        String namaRekening = (r.getNamaNasabah() == null ? "" : r.getNamaNasabah())
                + " - "
                + (r.getProduk() == null ? "" : r.getProduk());

        Transaksi t = new Transaksi();
        t.setId(UUID.randomUUID());
        t.setGroupId(t.getId());
        t.setNomorTransaksi(nomorTransaksi);
        t.setTipe(TipeTransaksi.WITHDRAWAL);
        t.setChannel("TELLER");

        // ini kolom-kolom yang kamu pakai sekarang
        t.setNomorRekening(r.getNomorRekening());
        t.setNamaRekening(namaRekening);
        t.setCifNasabah(r.getCifNasabah());
        t.setProduk(r.getProduk());

        t.setJumlah(jumlah);
        t.setSaldoSebelum(saldoSebelum);
        t.setSaldoSesudah(saldoSesudah);

        t.setKeterangan(keterangan.trim());
        t.setNoReferensi((noReferensi == null || noReferensi.isBlank()) ? null : noReferensi.trim());

        t.setProcessedAt(LocalDateTime.now());
        t.setProcessedByUsername(usernameLogin);
        t.setProcessedByFullName(fullName);

        transaksiRepository.save(t);

        return new WithdrawalResult(nomorTransaksi, saldoSesudah);
    }

    @Transactional(readOnly = true)
    public Rekening getActiveRekening(String nomorRekening) {
        Rekening r = rekeningRepository.findByNomorRekening(nomorRekening)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan."));
        if (!r.isStatusActive()) {
            throw new IllegalStateException("Rekening tidak aktif, tidak bisa diproses.");
        }
        return r;
    }
}
