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

@DisplayName("DashboardService Integration Tests")
class DashboardServiceTest extends BaseIntegrationTest {

    @Autowired private DashboardService dashboardService;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9850001");
        n.setNik("9850001234567890");
        n.setNamaSesuaiIdentitas("Budi");
        n.setStatus(NasabahStatus.ACTIVE);
        nasabahRepository.save(n);

        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("DASH01");
        p.setNamaProduk("Tabungan Wadiah Dashboard");
        p.setAktif(true);
        produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("getSummary - mengembalikan DashboardSummaryDto dari DB")
    void getSummary() {
        var summary = dashboardService.getSummary();
        assertNotNull(summary);
        assertTrue(summary.getTotalNasabah() >= 1);
    }

    @Test
    @DisplayName("getActiveProdukTabungan - mengembalikan daftar produk aktif dari DB")
    void getActiveProdukTabungan() {
        var products = dashboardService.getActiveProdukTabungan();
        assertFalse(products.isEmpty());
    }
}
