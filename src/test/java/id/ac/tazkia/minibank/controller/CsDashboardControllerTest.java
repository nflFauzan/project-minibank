package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.DashboardSummaryDto;
import id.ac.tazkia.minibank.service.DashboardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CsDashboardController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("CsDashboardController Unit Tests")
class CsDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Test
    @DisplayName("GET /cs/dashboard - should return cs/dashboard view")
    void dashboard_shouldReturnView() throws Exception {
        DashboardSummaryDto summary = new DashboardSummaryDto();
        summary.setTotalNasabah(100L);
        summary.setTotalRekening(50L);
        summary.setTotalProduk(5L);
        summary.setNasabahTerbaru(List.of());

        when(dashboardService.getSummary()).thenReturn(summary);
        when(dashboardService.getActiveProdukTabungan()).thenReturn(List.of());

        mockMvc.perform(get("/cs/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/dashboard"))
                .andExpect(model().attributeExists("summary", "totalNasabah", "totalRekeningAktif", "totalProduk", "nasabahTerbaru", "currentDate", "csName", "roleAndName", "products"));
    }
}
