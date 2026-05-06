package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RekeningService - Additional Integration Tests")
class RekeningServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private RekeningService rekeningService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9900001");
        n.setNik("9900001234567890");
        n.setNamaSesuaiIdentitas("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54399000101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setProduk("Tabungan Wadiah");
        rekeningRepository.save(r);

        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("REKADD01");
        p.setNamaProduk("Tabungan Wadiah RekAdd");
        p.setAktif(true);
        produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("listAccounts - tanpa search, kembalikan semua dari DB")
    void listAccounts_noSearch() {
        List<Rekening> result = rekeningService.listAccounts(null, "ACTIVE");
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("listAccounts - dengan search query dari DB")
    void listAccounts_withSearch() {
        List<Rekening> result = rekeningService.listAccounts("budi", "ACTIVE");
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("listAccounts - search blank string, fallback ke findByStatus")
    void listAccounts_blankSearch() {
        List<Rekening> result = rekeningService.listAccounts("  ", "ACTIVE");
        assertNotNull(result);
    }

    @Test
    @DisplayName("listEligibleCustomers - tanpa query, kembalikan ACTIVE dari DB")
    void listEligibleCustomers_noQuery() {
        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, null);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("listEligibleCustomers - dengan query dari DB")
    void listEligibleCustomers_withQuery() {
        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, "budi");
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("listActiveProducts - mengembalikan daftar produk aktif dari DB")
    void listActiveProducts_success() {
        List<ProdukTabungan> result = rekeningService.listActiveProducts();
        assertFalse(result.isEmpty());
    }
}
