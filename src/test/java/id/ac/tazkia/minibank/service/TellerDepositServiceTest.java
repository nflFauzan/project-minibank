package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TellerDepositService Integration Tests")
class TellerDepositServiceTest extends BaseIntegrationTest {

    @Autowired private TellerDepositService depositService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9810001");
        n.setNik("9810001234567890");
        n.setNamaSesuaiIdentitas("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54398100101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setProduk("Tabungan Wadiah");
        rekeningRepository.save(r);
    }

    @Test
    @DisplayName("deposit - berhasil menambah saldo dan menyimpan transaksi di DB")
    void deposit_success() {
        var result = depositService.deposit("54398100101",
                new BigDecimal("500000"), "Setoran Tunai", null, "teller1");

        assertNotNull(result);
        assertNotNull(result.nomorTransaksi());

        Rekening after = rekeningRepository.findByNomorRekening("54398100101").orElseThrow();
        assertEquals(0, new BigDecimal("1500000").compareTo(after.getSaldo()));
    }

    @Test
    @DisplayName("deposit - throw exception jika jumlah null")
    void deposit_shouldThrow_whenJumlahNull() {
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54398100101", null, "Setoran", null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika jumlah < 10.000")
    void deposit_shouldThrow_whenJumlahBelowMinimum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54398100101",
                        new BigDecimal("5000"), "Setoran", null, "teller1"));
        assertTrue(ex.getMessage().contains("10.000"));
    }

    @Test
    @DisplayName("deposit - throw exception jika keterangan blank")
    void deposit_shouldThrow_whenKeteranganBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54398100101",
                        new BigDecimal("50000"), "", null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika keterangan > 500 karakter")
    void deposit_shouldThrow_whenKeteranganTooLong() {
        String longText = "A".repeat(501);
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54398100101",
                        new BigDecimal("50000"), longText, null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika noReferensi > 100 karakter")
    void deposit_shouldThrow_whenNoReferensiTooLong() {
        String longRef = "R".repeat(101);
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54398100101",
                        new BigDecimal("50000"), "Setoran", longRef, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika rekening tidak ditemukan")
    void deposit_shouldThrow_whenRekeningNotFound() {
        assertThrows(EntityNotFoundException.class,
                () -> depositService.deposit("99999",
                        new BigDecimal("50000"), "Setoran", null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika rekening tidak aktif")
    void deposit_shouldThrow_whenRekeningInactive() {
        Rekening r = rekeningRepository.findByNomorRekening("54398100101").orElseThrow();
        r.setStatusActive(false);
        rekeningRepository.saveAndFlush(r);

        assertThrows(IllegalStateException.class,
                () -> depositService.deposit("54398100101",
                        new BigDecimal("50000"), "Setoran", null, "teller1"));
    }

    @Test
    @DisplayName("getActiveRekening - berhasil jika rekening aktif di DB")
    void getActiveRekening_success() {
        Rekening r = depositService.getActiveRekening("54398100101");
        assertNotNull(r);
        assertTrue(r.isStatusActive());
    }

    @Test
    @DisplayName("getActiveRekening - throw jika rekening tidak aktif")
    void getActiveRekening_shouldThrow_whenInactive() {
        Rekening r = rekeningRepository.findByNomorRekening("54398100101").orElseThrow();
        r.setStatusActive(false);
        rekeningRepository.saveAndFlush(r);

        assertThrows(IllegalStateException.class,
                () -> depositService.getActiveRekening("54398100101"));
    }
}
