package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TellerDashboardService - Additional Integration Tests")
class TellerDashboardServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private TellerDashboardService dashboardService;

    @Autowired private NasabahRepository originalNasabahRepository;
    @Autowired private RekeningRepository originalRekeningRepository;
    @Autowired private ProdukTabunganRepository originalProdukTabunganRepository;
    @Autowired private TransaksiRepository originalTransaksiRepository;

    private NasabahRepository mockNasabahRepository;
    private RekeningRepository mockRekeningRepository;
    private ProdukTabunganRepository mockProdukTabunganRepository;
    private TransaksiRepository mockTransaksiRepository;

    @BeforeEach
    void setUpMocks() {
        mockNasabahRepository = mock(NasabahRepository.class);
        mockRekeningRepository = mock(RekeningRepository.class);
        mockProdukTabunganRepository = mock(ProdukTabunganRepository.class);
        mockTransaksiRepository = mock(TransaksiRepository.class);

        ReflectionTestUtils.setField(dashboardService, "nasabahRepository", mockNasabahRepository);
        ReflectionTestUtils.setField(dashboardService, "rekeningRepository", mockRekeningRepository);
        ReflectionTestUtils.setField(dashboardService, "produkTabunganRepository", mockProdukTabunganRepository);
        ReflectionTestUtils.setField(dashboardService, "transaksiRepository", mockTransaksiRepository);
    }

    @AfterEach
    void restoreOriginals() {
        ReflectionTestUtils.setField(dashboardService, "nasabahRepository", originalNasabahRepository);
        ReflectionTestUtils.setField(dashboardService, "rekeningRepository", originalRekeningRepository);
        ReflectionTestUtils.setField(dashboardService, "produkTabunganRepository", originalProdukTabunganRepository);
        ReflectionTestUtils.setField(dashboardService, "transaksiRepository", originalTransaksiRepository);
    }

    @Test
    @DisplayName("totalNasabahAktif - ketika tidak ada nasabah aktif - mengembalikan 0")
    void totalNasabahAktif_empty_returnsZero() {
        when(mockNasabahRepository.countByStatus(NasabahStatus.ACTIVE)).thenReturn(0L);

        long result = dashboardService.totalNasabahAktif();
        assertEquals(0, result);
        verify(mockNasabahRepository, times(1)).countByStatus(NasabahStatus.ACTIVE);
    }

    @Test
    @DisplayName("totalNasabahAktif - ketika jumlah nasabah sangat besar - mengembalikan nilai besar")
    void totalNasabahAktif_largeValue_returnsLargeValue() {
        when(mockNasabahRepository.countByStatus(NasabahStatus.ACTIVE)).thenReturn(999999999L);

        long result = dashboardService.totalNasabahAktif();
        assertEquals(999999999L, result);
    }

    @Test
    @DisplayName("totalRekeningAktif - ketika tidak ada rekening aktif - mengembalikan 0")
    void totalRekeningAktif_empty_returnsZero() {
        when(mockRekeningRepository.countByStatusActive(true)).thenReturn(0L);

        long result = dashboardService.totalRekeningAktif();
        assertEquals(0, result);
        verify(mockRekeningRepository, times(1)).countByStatusActive(true);
    }

    @Test
    @DisplayName("totalRekeningAktif - ketika jumlah rekening sangat besar - mengembalikan nilai besar")
    void totalRekeningAktif_largeValue_returnsLargeValue() {
        when(mockRekeningRepository.countByStatusActive(true)).thenReturn(888888888L);

        long result = dashboardService.totalRekeningAktif();
        assertEquals(888888888L, result);
    }

    @Test
    @DisplayName("totalDepositAwal - ketika sum bernilai null (kosong di DB) - mengembalikan null atau fallback")
    void totalDepositAwal_nullFromRepository_returnsNull() {
        when(mockRekeningRepository.sumNominalSetoranAwalActive()).thenReturn(null);

        BigDecimal result = dashboardService.totalDepositAwal();
        assertNull(result);
        verify(mockRekeningRepository, times(1)).sumNominalSetoranAwalActive();
    }

    @Test
    @DisplayName("totalDepositAwal - ketika sum bernilai 0 - mengembalikan ZERO")
    void totalDepositAwal_zero_returnsZero() {
        when(mockRekeningRepository.sumNominalSetoranAwalActive()).thenReturn(BigDecimal.ZERO);

        BigDecimal result = dashboardService.totalDepositAwal();
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("totalDepositAwal - ketika sum sangat besar - mengembalikan nominal besar")
    void totalDepositAwal_largeValue_returnsLargeValue() {
        BigDecimal largeVal = new BigDecimal("99999999999999.99");
        when(mockRekeningRepository.sumNominalSetoranAwalActive()).thenReturn(largeVal);

        BigDecimal result = dashboardService.totalDepositAwal();
        assertEquals(largeVal, result);
    }

    @Test
    @DisplayName("totalTransaksi - ketika tidak ada transaksi teller - mengembalikan 0")
    void totalTransaksi_empty_returnsZero() {
        when(mockTransaksiRepository.countByChannel("TELLER")).thenReturn(0L);

        long result = dashboardService.totalTransaksi();
        assertEquals(0, result);
        verify(mockTransaksiRepository, times(1)).countByChannel("TELLER");
    }

    @Test
    @DisplayName("totalTransaksi - ketika jumlah transaksi teller sangat besar - mengembalikan nilai besar")
    void totalTransaksi_largeValue_returnsLargeValue() {
        when(mockTransaksiRepository.countByChannel("TELLER")).thenReturn(777777777L);

        long result = dashboardService.totalTransaksi();
        assertEquals(777777777L, result);
    }

    @Test
    @DisplayName("produkAktif - ketika tidak ada produk aktif - mengembalikan list kosong")
    void produkAktif_emptyList_returnsEmptyList() {
        when(mockProdukTabunganRepository.findActiveProducts()).thenReturn(Collections.emptyList());

        List<ProdukTabungan> result = dashboardService.produkAktif();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockProdukTabunganRepository, times(1)).findActiveProducts();
    }

    @Test
    @DisplayName("produkAktif - ketika ada beberapa produk aktif - mengembalikan daftar produk")
    void produkAktif_multipleProducts_returnsList() {
        ProdukTabungan p1 = new ProdukTabungan();
        p1.setKodeProduk("P1");
        p1.setNamaProduk("Produk 1");
        p1.setAktif(true);

        ProdukTabungan p2 = new ProdukTabungan();
        p2.setKodeProduk("P2");
        p2.setNamaProduk("Produk 2");
        p2.setAktif(true);

        when(mockProdukTabunganRepository.findActiveProducts()).thenReturn(List.of(p1, p2));

        List<ProdukTabungan> result = dashboardService.produkAktif();
        assertEquals(2, result.size());
        assertEquals("P1", result.get(0).getKodeProduk());
        assertEquals("P2", result.get(1).getKodeProduk());
    }
}
