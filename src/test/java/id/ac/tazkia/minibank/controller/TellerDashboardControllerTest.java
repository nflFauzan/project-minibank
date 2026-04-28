package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.TellerDashboardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TellerDashboardController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("TellerDashboardController Unit Tests")
class TellerDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TellerDashboardService dashboardService;

    @Test
    @DisplayName("GET /teller/dashboard - should return teller/dashboard view")
    void dashboard_shouldReturnView() throws Exception {
        when(dashboardService.totalNasabahAktif()).thenReturn(10L);
        when(dashboardService.totalRekeningAktif()).thenReturn(5L);
        when(dashboardService.totalDepositAwal()).thenReturn(BigDecimal.valueOf(1000));
        when(dashboardService.totalTransaksi()).thenReturn(20L);
        when(dashboardService.produkAktif()).thenReturn(List.of());

        mockMvc.perform(get("/teller/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/dashboard"))
                .andExpect(model().attributeExists("totalNasabah", "totalRekeningAktif", "totalDeposit", "totalTransaksi", "products"));
    }

    @Test
    @DisplayName("GET /teller/transaction - should return teller/transaction/list view")
    void transaction_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"))
                .andExpect(model().attributeExists("active"));
    }

    @Test
    @DisplayName("GET /teller/settings - should return teller/settings view")
    void settings_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/settings"));
    }
}
