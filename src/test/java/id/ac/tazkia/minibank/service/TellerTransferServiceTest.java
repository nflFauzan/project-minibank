package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TellerTransferService Integration Tests")
class TellerTransferServiceTest extends BaseIntegrationTest {

    @Autowired private TellerTransferService transferService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    @BeforeEach
    void setUp() {
        Nasabah n1 = new Nasabah();
        n1.setCif("C9820001");
        n1.setNik("9820001111111111");
        n1.setNamaSesuaiIdentitas("Budi");
        n1.setStatus(NasabahStatus.ACTIVE);
        n1 = nasabahRepository.save(n1);

        Nasabah n2 = new Nasabah();
        n2.setCif("C9820002");
        n2.setNik("9820002222222222");
        n2.setNamaSesuaiIdentitas("Aisyah");
        n2.setStatus(NasabahStatus.ACTIVE);
        n2 = nasabahRepository.save(n2);

        Rekening r1 = new Rekening();
        r1.setNomorRekening("54398200101");
        r1.setStatusActive(true);
        r1.setSaldo(new BigDecimal("1000000"));
        r1.setNasabah(n1);
        r1.setCifNasabah(n1.getCif());
        r1.setNamaNasabah(n1.getNamaSesuaiIdentitas());
        r1.setProduk("Tabungan Wadiah");
        rekeningRepository.save(r1);

        Rekening r2 = new Rekening();
        r2.setNomorRekening("54398200202");
        r2.setStatusActive(true);
        r2.setSaldo(new BigDecimal("500000"));
        r2.setNasabah(n2);
        r2.setCifNasabah(n2.getCif());
        r2.setNamaNasabah(n2.getNamaSesuaiIdentitas());
        r2.setProduk("Tabungan Mudharabah");
        rekeningRepository.save(r2);
    }

    @Test
    @DisplayName("transfer - berhasil, saldo sumber berkurang dan tujuan bertambah di DB")
    void transfer_success() {
        UUID groupId = transferService.transfer(
                "54398200101", "54398200202",
                new BigDecimal("200000"), "Bayar hutang", null, "teller1");

        assertNotNull(groupId);

        Rekening sumber = rekeningRepository.findByNomorRekening("54398200101").orElseThrow();
        Rekening tujuan = rekeningRepository.findByNomorRekening("54398200202").orElseThrow();
        assertEquals(0, new BigDecimal("800000").compareTo(sumber.getSaldo()));
        assertEquals(0, new BigDecimal("700000").compareTo(tujuan.getSaldo()));
    }

    @Test
    @DisplayName("transfer - throw jika rekening sumber blank")
    void transfer_shouldThrow_whenSumberBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("", "54398200202",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika sumber sama dengan tujuan")
    void transfer_shouldThrow_whenSameAccount() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54398200101", "54398200101",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika jumlah null")
    void transfer_shouldThrow_whenJumlahNull() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54398200101", "54398200202",
                        null, null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika jumlah 0")
    void transfer_shouldThrow_whenJumlahZero() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54398200101", "54398200202",
                        BigDecimal.ZERO, null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika saldo tidak cukup")
    void transfer_shouldThrow_whenSaldoInsufficient() {
        assertThrows(IllegalStateException.class,
                () -> transferService.transfer("54398200101", "54398200202",
                        new BigDecimal("5000000"), null, null, "teller1"));
    }
}
