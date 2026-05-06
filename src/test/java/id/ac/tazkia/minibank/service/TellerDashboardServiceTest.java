package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TellerDashboardService Integration Tests")
class TellerDashboardServiceTest extends BaseIntegrationTest {

    @Autowired private TellerDashboardService service;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9880001");
        n.setNik("9880001234567890");
        n.setNamaSesuaiIdentitas("Budi");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54398800101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNominalSetoranAwal(new BigDecimal("100000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        rekeningRepository.save(r);

        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("TLDASH01");
        p.setNamaProduk("Tabungan Wadiah TlDash");
        p.setAktif(true);
        produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("totalNasabahAktif - dari DB")
    void totalNasabahAktif() {
        assertTrue(service.totalNasabahAktif() >= 1);
    }

    @Test
    @DisplayName("totalRekeningAktif - dari DB")
    void totalRekeningAktif() {
        assertTrue(service.totalRekeningAktif() >= 1);
    }

    @Test
    @DisplayName("totalDepositAwal - dari DB")
    void totalDepositAwal() {
        assertNotNull(service.totalDepositAwal());
    }

    @Test
    @DisplayName("totalTransaksi - dari DB")
    void totalTransaksi() {
        assertNotNull(service.totalTransaksi());
    }

    @Test
    @DisplayName("produkAktif - dari DB")
    void produkAktif() {
        assertFalse(service.produkAktif().isEmpty());
    }
}
