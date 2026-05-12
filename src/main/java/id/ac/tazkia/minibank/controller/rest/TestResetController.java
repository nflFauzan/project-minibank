package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestResetController {

    private final TransaksiRepository transaksiRepo;
    private final RekeningRepository rekeningRepo;
    private final NasabahRepository nasabahRepo;
    private final ProdukTabunganRepository produkRepo;

    @PostMapping("/reset")
    public Map<String, String> reset() {
        try {
            // Hapus data transaksi dulu (karena foreign key)
            transaksiRepo.deleteAll();
            
            // Hapus rekening
            rekeningRepo.deleteAll();
            
            // Hapus nasabah
            nasabahRepo.deleteAll();
            
            // Hapus produk
            produkRepo.deleteAll();

            // Seed ulang data wajib untuk test
            seedTestData();

            return Map.of("status", "success", "message", "Database reset successfully");
        } catch (Exception e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
    }

    private void seedTestData() {
        // Nasabah ACTIVE default untuk testing
        Nasabah n = new Nasabah();
        n.setCif("C0000001");
        n.setNik("1234567890123456");
        n.setNamaLengkap("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        n.setTempatLahir("Jakarta");
        n.setNamaIbuKandung("Siti Aminah");
        nasabahRepo.save(n);

        // Produk default
        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("TAB_UTAMA");
        p.setNamaProduk("Tabungan Utama");
        p.setJenisAkad("WADIAH");
        p.setSetoranAwalMinimum(new BigDecimal("100000"));
        p.setAktif(true);
        produkRepo.save(p);
    }
}
