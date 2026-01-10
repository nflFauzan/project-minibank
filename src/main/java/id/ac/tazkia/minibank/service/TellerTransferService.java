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
public class TellerTransferService {

    private final RekeningRepository rekeningRepository;
    private final TransaksiRepository transaksiRepository;
    private final UserRepository userRepository;

    @Transactional
    public UUID transfer(
            String rekeningSumber,
            String rekeningTujuan,
            BigDecimal jumlah,
            String keteranganTambahan,
            String noReferensi,
            String usernameLogin
    ) {

        if (rekeningSumber == null || rekeningSumber.isBlank()) {
            throw new IllegalArgumentException("Rekening sumber wajib diisi.");
        }
        if (rekeningTujuan == null || rekeningTujuan.isBlank()) {
            throw new IllegalArgumentException("Rekening tujuan wajib diisi.");
        }
        if (rekeningSumber.equals(rekeningTujuan)) {
            throw new IllegalArgumentException("Rekening sumber dan tujuan tidak boleh sama.");
        }

        if (jumlah == null || jumlah.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah transfer harus lebih dari 0.");
        }
        if (jumlah.scale() > 2) {
            throw new IllegalArgumentException("Jumlah tidak boleh lebih dari 2 angka desimal.");
        }

        String ketTambahan = (keteranganTambahan == null) ? "" : keteranganTambahan.trim();
        String ref = (noReferensi == null || noReferensi.isBlank()) ? null : noReferensi.trim();

        // lock order ASC biar aman dari deadlock
        String first = rekeningSumber.compareTo(rekeningTujuan) < 0 ? rekeningSumber : rekeningTujuan;
        String second = rekeningSumber.compareTo(rekeningTujuan) < 0 ? rekeningTujuan : rekeningSumber;

        Rekening r1 = rekeningRepository.findByNomorRekeningForUpdate(first)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan: " + first));
        Rekening r2 = rekeningRepository.findByNomorRekeningForUpdate(second)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan: " + second));

        Rekening sumber = r1.getNomorRekening().equals(rekeningSumber) ? r1 : r2;
        Rekening tujuan = r1.getNomorRekening().equals(rekeningTujuan) ? r1 : r2;

        if (!sumber.isStatusActive()) {
            throw new IllegalStateException("Rekening sumber tidak aktif.");
        }
        if (!tujuan.isStatusActive()) {
            throw new IllegalStateException("Rekening tujuan tidak aktif.");
        }

        BigDecimal saldoSumberAwal = sumber.getSaldo() == null ? BigDecimal.ZERO : sumber.getSaldo();
        BigDecimal saldoTujuanAwal = tujuan.getSaldo() == null ? BigDecimal.ZERO : tujuan.getSaldo();

        if (saldoSumberAwal.compareTo(jumlah) < 0) {
            throw new IllegalStateException("Saldo tidak mencukupi.");
        }

        BigDecimal saldoSumberAkhir = saldoSumberAwal.subtract(jumlah);
        BigDecimal saldoTujuanAkhir = saldoTujuanAwal.add(jumlah);

        sumber.setSaldo(saldoSumberAkhir);
        tujuan.setSaldo(saldoTujuanAkhir);

        rekeningRepository.save(sumber);
        rekeningRepository.save(tujuan);

        UUID groupId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        long seqOut = transaksiRepository.nextSeq();
        long seqIn = transaksiRepository.nextSeq();

        // format nomor transaksi transfer: T3 + 6 digit
        String noOut = "T3" + String.format("%06d", seqOut);
        String noIn = "T3" + String.format("%06d", seqIn);

        String processedByUsername = (usernameLogin == null || usernameLogin.isBlank()) ? "-" : usernameLogin;
        String processedByFullName = userRepository.findByUsername(processedByUsername)
                .map(u -> (u.getFullName() == null || u.getFullName().isBlank()) ? processedByUsername : u.getFullName())
                .orElse(processedByUsername);

        // kolom DB processed_by NOT NULL → isi String (pakai username biar konsisten)
        String processedBy = processedByUsername;

        // ===== TRANSFER OUT =====
        Transaksi out = new Transaksi();
        out.setId(UUID.randomUUID());
        out.setGroupId(groupId);
        out.setRekening(sumber); // rekening_id NOT NULL
        out.setNomorTransaksi(noOut);
        out.setTipe(TipeTransaksi.TRANSFER);
        out.setChannel("TELLER");

        out.setNomorRekening(sumber.getNomorRekening());
        out.setNamaRekening(namaRekening(sumber));
        out.setCifNasabah(sumber.getCifNasabah());
        out.setProduk(sumber.getProduk());

        out.setJumlah(jumlah);
        out.setSaldoSebelum(saldoSumberAwal);
        out.setSaldoSesudah(saldoSumberAkhir);

        out.setKeterangan("Transfer ke " + tujuan.getNomorRekening() + (ketTambahan.isEmpty() ? "" : " - " + ketTambahan));
        out.setNoReferensi(ref);

        out.setProcessedAt(now);
        out.setProcessedBy(processedBy);
        out.setProcessedByUsername(processedByUsername);
        out.setProcessedByFullName(processedByFullName);

        // ===== TRANSFER IN =====
        Transaksi in = new Transaksi();
        in.setId(UUID.randomUUID());
        in.setGroupId(groupId);
        in.setRekening(tujuan);
        in.setNomorTransaksi(noIn);
        in.setTipe(TipeTransaksi.TRANSFER);
        in.setChannel("TELLER");

        in.setNomorRekening(tujuan.getNomorRekening());
        in.setNamaRekening(namaRekening(tujuan));
        in.setCifNasabah(tujuan.getCifNasabah());
        in.setProduk(tujuan.getProduk());

        in.setJumlah(jumlah);
        in.setSaldoSebelum(saldoTujuanAwal);
        in.setSaldoSesudah(saldoTujuanAkhir);

        in.setKeterangan("Transfer dari " + sumber.getNomorRekening() + (ketTambahan.isEmpty() ? "" : " - " + ketTambahan));
        in.setNoReferensi(ref);

        in.setProcessedAt(now);
        in.setProcessedBy(processedBy);
        in.setProcessedByUsername(processedByUsername);
        in.setProcessedByFullName(processedByFullName);

        transaksiRepository.save(out);
        transaksiRepository.save(in);

        return groupId;
    }

    private String namaRekening(Rekening r) {
        String nama = (r.getNamaNasabah() == null) ? "" : r.getNamaNasabah();
        String produk = (r.getProduk() == null) ? "" : r.getProduk();
        return nama + " - " + produk;
    }
}