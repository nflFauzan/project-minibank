package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RekeningService {

    private final RekeningRepository rekeningRepository;
    private final NasabahRepository nasabahRepository;
    private final ProdukTabunganRepository produkTabunganRepository;

    @Data
    public static class OpenAccountForm {
        private Long produkId;
        private BigDecimal nominalSetoranAwal;
        private String tujuanPembukaan;
    }

    @Transactional(readOnly = true)
    public List<Rekening> listAccounts(String search, String status) {
        if (search != null && !search.isBlank()) {
            return rekeningRepository.search(search, status);
        }
        return rekeningRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Nasabah> listEligibleCustomers() {
        // sesuai requirement kamu: hanya ACTIVE boleh buka rekening
        return nasabahRepository.findByStatus(NasabahStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public Nasabah getNasabahActiveById(Long id) {
        Nasabah n = nasabahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nasabah tidak ditemukan."));
        if (n.getStatus() != NasabahStatus.ACTIVE) {
            throw new EntityNotFoundException("Nasabah belum ACTIVE, tidak bisa buka rekening.");
        }
        return n;
    }

    @Transactional(readOnly = true)
    public List<ProdukTabungan> listActiveProducts() {
        return produkTabunganRepository.findActiveProducts();
    }

    @Transactional(readOnly = true)
    public Rekening getAccountById(Long id) {
        return rekeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan."));
    }

    @Transactional
    public Rekening openAccount(Long nasabahId, Long produkId, BigDecimal setoranAwal, String tujuan) {

        Nasabah n = getNasabahActiveById(nasabahId);

        ProdukTabungan produk = produkTabunganRepository.findById(produkId)
                .orElseThrow(() -> new EntityNotFoundException("Product tidak ditemukan."));

        if (produk.getAktif() == null || !produk.getAktif()) {
            throw new IllegalStateException("Product tidak aktif.");
        }

        // nomor rekening: 543 + no_urut(6) + kode_produk(2) => kamu minta 01 dll
        String kodeProduk2 = produk.getKodeProduk();
        if (kodeProduk2 == null) throw new IllegalStateException("Kode produk null.");
        // kalau di DB kamu simpan "01" langsung, aman. kalau "TAB_WAD" dll, kamu harus mapping sendiri.
        // sementara: ambil 2 char terakhir kalau panjang > 2
        if (kodeProduk2.length() > 2) {
            kodeProduk2 = kodeProduk2.substring(kodeProduk2.length() - 2);
        }

        String noUrut6 = rekeningRepository.nextSequence6();

        Rekening r = new Rekening();
        r.setNasabah(n);
        r.setNomorRekening("543" + noUrut6 + kodeProduk2);

        r.setStatusActive(true);
        r.setCifNasabah(n.getCif());
        r.setNik(n.getNik());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setEmail(n.getEmail());
        r.setNomorTelepon(n.getNoHp());
        r.setAlamatDomisili(n.getAlamatDomisili());

        r.setProduk(produk.getNamaProduk());
        r.setNominalSetoranAwal(setoranAwal);
        r.setTujuanPembukaan(tujuan);
        r.setTanggalPembukaan(LocalDate.now());

        r.setCabangPembukaan("543");
        r.setPetugasCs("CS");

        return rekeningRepository.save(r);
    }

    @Transactional
    public void closeAccount(Long rekeningId) {
        Rekening r = getAccountById(rekeningId);
        r.setStatusActive(false);
        rekeningRepository.save(r);
    }
}
