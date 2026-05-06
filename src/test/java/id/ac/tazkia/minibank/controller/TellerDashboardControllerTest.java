package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TellerDashboardController Integration Tests")
class TellerDashboardControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("GET /teller/dashboard - should return teller/dashboard view")
    void dashboard_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/dashboard"))
                .andExpect(model().attributeExists("totalNasabah", "totalRekeningAktif",
                        "totalDeposit", "totalTransaksi", "products"));
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
