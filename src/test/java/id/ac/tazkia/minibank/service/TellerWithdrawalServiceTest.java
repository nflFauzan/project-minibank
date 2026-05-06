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

@DisplayName("TellerWithdrawalService Integration Tests")
class TellerWithdrawalServiceTest extends BaseIntegrationTest {

    @Autowired private TellerWithdrawalService withdrawalService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9840001");
        n.setNik("9840001234567890");
        n.setNamaSesuaiIdentitas("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54398400101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setProduk("Tabungan Wadiah");
        rekeningRepository.save(r);
    }

    @Test
    @DisplayName("withdraw - berhasil mengurangi saldo di DB")
    void withdraw_success() {
        var result = withdrawalService.withdraw("54398400101",
                new BigDecimal("300000"), "Penarikan Tunai", null, "teller1");

        assertNotNull(result);
        assertNotNull(result.nomorTransaksi());

        Rekening after = rekeningRepository.findByNomorRekening("54398400101").orElseThrow();
        assertEquals(0, new BigDecimal("700000").compareTo(after.getSaldo()));
    }

    @Test
    @DisplayName("withdraw - throw jika jumlah null")
    void withdraw_shouldThrow_whenJumlahNull() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54398400101", null, "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika jumlah <= 0")
    void withdraw_shouldThrow_whenJumlahZeroOrNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54398400101", BigDecimal.ZERO, "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika keterangan blank")
    void withdraw_shouldThrow_whenKeteranganBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54398400101", new BigDecimal("50000"), "  ", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika saldo tidak cukup")
    void withdraw_shouldThrow_whenSaldoInsufficient() {
        assertThrows(IllegalStateException.class,
                () -> withdrawalService.withdraw("54398400101", new BigDecimal("2000000"), "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika rekening tidak ditemukan")
    void withdraw_shouldThrow_whenRekeningNotFound() {
        assertThrows(EntityNotFoundException.class,
                () -> withdrawalService.withdraw("99999", new BigDecimal("50000"), "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("getActiveRekening - berhasil dari DB")
    void getActiveRekening_success() {
        Rekening r = withdrawalService.getActiveRekening("54398400101");
        assertNotNull(r);
        assertTrue(r.isStatusActive());
    }
}
