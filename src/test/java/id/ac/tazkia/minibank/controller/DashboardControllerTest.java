package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("DashboardController Integration Tests")
class DashboardControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("GET / - should redirect or show index")
    void root_shouldReturnView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
