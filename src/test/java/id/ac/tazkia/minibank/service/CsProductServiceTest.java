package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CsProductService Integration Tests")
class CsProductServiceTest extends BaseIntegrationTest {

    @Autowired private CsProductService csProductService;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    private ProdukTabungan existingProduk;

    @BeforeEach
    void setUp() {
        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("TAB01");
        p.setNamaProduk("Tabungan Wadiah");
        p.setDeskripsiSingkat("Tabungan berbasis Wadiah");
        p.setJenisAkad("WADIAH");
        p.setSetoranAwalMinimum(new BigDecimal("100000"));
        p.setAktif(true);
        existingProduk = produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("akadOptions - mengembalikan 8 pilihan akad")
    void akadOptions_shouldReturn8Options() {
        List<CsProductService.AkadOption> opts = csProductService.akadOptions();
        assertEquals(8, opts.size());
        assertTrue(opts.stream().anyMatch(o -> o.value().equals("WADIAH")));
    }

    @Test
    @DisplayName("create - berhasil membuat produk baru & tersimpan di DB")
    void create_success() {
        long countBefore = produkTabunganRepository.count();
        ProdukTabungan result = csProductService.create(
                "NEWPROD", "Produk Baru", "Deskripsi", "WADIAH", new BigDecimal("50000"));
        assertNotNull(result);
        assertEquals("NEWPROD", result.getKodeProduk());
        assertTrue(produkTabunganRepository.count() > countBefore);
    }

    @Test
    @DisplayName("create - kode produk di-normalize ke uppercase")
    void create_normalizeKode() {
        ProdukTabungan result = csProductService.create(
                "  tabwad  ", "Tabungan Wadiah2", "Desc", "wadiah", new BigDecimal("50000"));
        assertEquals("TABWAD", result.getKodeProduk());
        assertEquals("WADIAH", result.getJenisAkad());
    }

    @Test
    @DisplayName("create - throw jika kode produk blank")
    void create_throwIfKodeBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("", "Produk", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika nama produk blank")
    void create_throwIfNamaBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "  ", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika jenis akad blank")
    void create_throwIfAkadBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika jenis akad tidak valid")
    void create_throwIfAkadInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "RIBA", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika setoran awal null")
    void create_throwIfSetoranNull() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH", null));
    }

    @Test
    @DisplayName("create - throw jika setoran awal nol atau negatif")
    void create_throwIfSetoranZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH", BigDecimal.ZERO));
    }

    @Test
    @DisplayName("create - throw jika setoran awal lebih dari maksimum")
    void create_throwIfSetoranTooLarge() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH", new BigDecimal("1000000001")));
    }

    @Test
    @DisplayName("create - throw jika kode produk sudah ada")
    void create_throwIfKodeDuplikat() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("TAB01", "Produk Duplikat", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("update - berhasil update produk & data berubah di DB")
    void update_success() {
        ProdukTabungan result = csProductService.update(existingProduk.getId(),
                "NEWKODE", "Nama Baru", "Deskripsi Baru", "MUDHARABAH", new BigDecimal("200000"));
        assertNotNull(result);
        assertEquals("NEWKODE", result.getKodeProduk());
        assertEquals("Nama Baru", result.getNamaProduk());
    }

    @Test
    @DisplayName("update - throw jika produk tidak ditemukan")
    void update_throwIfNotFound() {
        assertThrows(EntityNotFoundException.class, () ->
                csProductService.update(999L, "KD01", "Nama", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("toggleAktif - mengubah aktif dari true ke false di DB")
    void toggleAktif_trueToFalse() {
        csProductService.toggleAktif(existingProduk.getId());
        ProdukTabungan toggled = produkTabunganRepository.findById(existingProduk.getId()).orElseThrow();
        assertFalse(toggled.getAktif());
    }

    @Test
    @DisplayName("toggleAktif - throw jika produk tidak ditemukan")
    void toggleAktif_throwIfNotFound() {
        assertThrows(EntityNotFoundException.class, () -> csProductService.toggleAktif(999L));
    }

    @Test
    @DisplayName("search - kembalikan page hasil pencarian dari DB")
    void search_withQuery() {
        Page<ProdukTabungan> result = csProductService.search("wadiah", true, PageRequest.of(0, 10));
        assertNotNull(result);
        assertTrue(result.getTotalElements() >= 1);
    }

    @Test
    @DisplayName("search - tanpa filter q dan aktif")
    void search_noFilter() {
        Page<ProdukTabungan> result = csProductService.search(null, null, PageRequest.of(0, 10));
        assertNotNull(result);
    }
}
