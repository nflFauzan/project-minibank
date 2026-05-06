package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("CsDashboardController Integration Tests")
class CsDashboardControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("GET /cs/dashboard - should return cs/dashboard view")
    void csDashboard_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/dashboard"))
                .andExpect(model().attributeExists("summary", "products"));
    }
}
