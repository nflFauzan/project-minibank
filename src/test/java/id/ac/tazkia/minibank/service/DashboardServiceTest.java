package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.DashboardSummaryDto;
import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DashboardService Unit Tests")
class DashboardServiceTest {

    @Mock private NasabahRepository nasabahRepository;
    @Mock private RekeningRepository rekeningRepository;
    @Mock private ProdukTabunganRepository produkTabunganRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    @DisplayName("getSummary - mengembalikan DTO dengan data ringkasan")
    void getSummary() {
        when(nasabahRepository.countByStatus(NasabahStatus.ACTIVE)).thenReturn(10L);
        when(rekeningRepository.countByStatusActive(true)).thenReturn(5L);
        when(produkTabunganRepository.count()).thenReturn(3L);
        Nasabah n = new Nasabah();
        n.setId(1L);
        n.setNamaSesuaiIdentitas("Budi");
        when(nasabahRepository.findTop5ByStatusOrderByCreatedAtDesc(NasabahStatus.ACTIVE))
                .thenReturn(List.of(n));

        DashboardSummaryDto dto = dashboardService.getSummary();
        assertEquals(10L, dto.getTotalNasabah());
        assertEquals(5L, dto.getTotalRekening());
        assertEquals(3L, dto.getTotalProduk());
        assertEquals(1, dto.getNasabahTerbaru().size());
    }

    @Test
    @DisplayName("getActiveProdukTabungan - mengembalikan list produk aktif")
    void getActiveProdukTabungan() {
        ProdukTabungan p = new ProdukTabungan();
        p.setNamaProduk("Tabungan Wadiah");
        when(produkTabunganRepository.findActiveProducts()).thenReturn(List.of(p));
        List<ProdukTabungan> result = dashboardService.getActiveProdukTabungan();
        assertEquals(1, result.size());
    }
}
