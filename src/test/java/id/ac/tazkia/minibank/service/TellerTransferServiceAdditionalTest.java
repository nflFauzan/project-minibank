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

@DisplayName("TellerTransferService - Additional Integration Tests")
class TellerTransferServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private TellerTransferService transferService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Nasabah n1 = new Nasabah();
        n1.setCif("C9830001");
        n1.setNik("9830001111111111");
        n1.setNamaSesuaiIdentitas("Budi");
        n1.setStatus(NasabahStatus.ACTIVE);
        n1 = nasabahRepository.save(n1);

        Nasabah n2 = new Nasabah();
        n2.setCif("C9830002");
        n2.setNik("9830002222222222");
        n2.setNamaSesuaiIdentitas("Aisyah");
        n2.setStatus(NasabahStatus.ACTIVE);
        n2 = nasabahRepository.save(n2);

        Rekening r1 = new Rekening();
        r1.setNomorRekening("54398300101");
        r1.setStatusActive(true);
        r1.setSaldo(new BigDecimal("2000000"));
        r1.setNasabah(n1);
        r1.setCifNasabah(n1.getCif());
        r1.setNamaNasabah(n1.getNamaSesuaiIdentitas());
        r1.setProduk("Tabungan Wadiah");
        rekeningRepository.save(r1);

        Rekening r2 = new Rekening();
        r2.setNomorRekening("54398300202");
        r2.setStatusActive(true);
        r2.setSaldo(new BigDecimal("300000"));
        r2.setNasabah(n2);
        r2.setCifNasabah(n2.getCif());
        r2.setNamaNasabah(n2.getNamaSesuaiIdentitas());
        r2.setProduk("Tabungan Mudharabah");
        rekeningRepository.save(r2);
    }

    @Test
    @DisplayName("transfer - berhasil dengan username di DB")
    void transfer_withFullName() {
        UUID groupId = transferService.transfer(
                "54398300101", "54398300202",
                new BigDecimal("100000"), null, null, "teller1");
        assertNotNull(groupId);
    }

    @Test
    @DisplayName("transfer - throw jika rekening first tidak ditemukan")
    void transfer_throwIfFirstNotFound() {
        assertThrows(Exception.class,
                () -> transferService.transfer(
                        "NOTEXIST01", "54398300202",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika lebih dari 2 desimal")
    void transfer_shouldThrow_whenTooManyDecimals() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54398300101", "54398300202",
                        new BigDecimal("100.123"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika rekening tujuan blank")
    void transfer_shouldThrow_whenTujuanBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54398300101", "",
                        new BigDecimal("100000"), null, null, "teller1"));
    }
}
