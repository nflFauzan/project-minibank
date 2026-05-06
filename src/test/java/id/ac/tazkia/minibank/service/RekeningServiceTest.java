package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RekeningService Integration Tests")
class RekeningServiceTest extends BaseIntegrationTest {

    @Autowired private RekeningService rekeningService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    private Nasabah activeNasabah;
    private ProdukTabungan activeProduk;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();

        activeNasabah = new Nasabah();
        activeNasabah.setCif("C9890001");
        activeNasabah.setNik("9890001234567890");
        activeNasabah.setNamaSesuaiIdentitas("Budi Santoso");
        activeNasabah.setEmail("budi@email.com");
        activeNasabah.setNoHp("08123456789");
        activeNasabah.setAlamatDomisili("Jl. Merdeka No. 1");
        activeNasabah.setStatus(NasabahStatus.ACTIVE);
        activeNasabah = nasabahRepository.save(activeNasabah);

        activeProduk = new ProdukTabungan();
        activeProduk.setKodeProduk("REK01");
        activeProduk.setNamaProduk("Tabungan Wadiah Rek");
        activeProduk.setAktif(true);
        activeProduk = produkTabunganRepository.save(activeProduk);
    }

    @Test
    @DisplayName("openAccount - harus buat rekening dengan nomor rekening yang benar & data di DB")
    void openAccount_shouldCreateRekening_withCorrectNomorRekening() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        Rekening result = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("500000"), "Menabung");

        assertNotNull(result);
        assertNotNull(result.getId());
        assertTrue(result.getNomorRekening().startsWith("543"));
        assertEquals("C9890001", result.getCifNasabah());
        assertEquals("Budi Santoso", result.getNamaNasabah());
        assertTrue(result.isStatusActive());
        assertTrue(rekeningRepository.findById(result.getId()).isPresent());
    }

    @Test
    @DisplayName("openAccount - harus throw exception jika nasabah belum ACTIVE")
    void openAccount_shouldThrow_whenNasabahNotActive() {
        Nasabah inactiveNasabah = new Nasabah();
        inactiveNasabah.setCif("C9890002");
        inactiveNasabah.setNik("9890002234567890");
        inactiveNasabah.setNamaSesuaiIdentitas("Inactive");
        inactiveNasabah.setStatus(NasabahStatus.INACTIVE);
        inactiveNasabah = nasabahRepository.save(inactiveNasabah);

        Long inactiveId = inactiveNasabah.getId();

        assertThrows(EntityNotFoundException.class,
                () -> rekeningService.openAccount(inactiveId, activeProduk.getId(),
                        new BigDecimal("500000"), "Menabung"));
    }

    @Test
    @DisplayName("openAccount - harus throw exception jika produk tidak ditemukan")
    void openAccount_shouldThrow_whenProdukNotFound() {
        assertThrows(EntityNotFoundException.class,
                () -> rekeningService.openAccount(activeNasabah.getId(), 999L,
                        new BigDecimal("500000"), "Menabung"));
    }

    @Test
    @DisplayName("openAccount - harus throw exception jika produk tidak aktif")
    void openAccount_shouldThrow_whenProdukNotAktif() {
        ProdukTabungan inactiveProduk = new ProdukTabungan();
        inactiveProduk.setKodeProduk("REK02");
        inactiveProduk.setNamaProduk("Tabungan Mudharabah");
        inactiveProduk.setAktif(false);
        inactiveProduk = produkTabunganRepository.save(inactiveProduk);

        Long inactiveProdukId = inactiveProduk.getId();

        assertThrows(IllegalStateException.class,
                () -> rekeningService.openAccount(activeNasabah.getId(), inactiveProdukId,
                        new BigDecimal("500000"), "Menabung"));
    }

    @Test
    @DisplayName("closeAccount - harus set statusActive menjadi false di DB")
    void closeAccount_shouldSetStatusActiveFalse() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        Rekening created = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("500000"), "Menabung");
        assertTrue(created.isStatusActive());

        rekeningService.closeAccount(created.getId());

        Rekening closed = rekeningRepository.findById(created.getId()).orElseThrow();
        assertFalse(closed.isStatusActive());
    }

    @Test
    @DisplayName("getAccountById - harus throw exception jika tidak ditemukan")
    void getAccountById_shouldThrow_whenNotFound() {
        assertThrows(EntityNotFoundException.class,
                () -> rekeningService.getAccountById(999L));
    }
}
