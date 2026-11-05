package id.ac.tazkia.minibank.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Sub-Dashboard Endpoints Complete Removal Verification")
public class SubDashboardRemovalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "cs1", roles = {"CS"})
    @DisplayName("GET /cs/dashboard MUST return HTTP 404 (Endpoint completely removed)")
    void csDashboard_shouldReturn404() throws Exception {
        mockMvc.perform(get("/cs/dashboard"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "teller1", roles = {"TELLER"})
    @DisplayName("GET /teller/dashboard MUST return HTTP 404 (Endpoint completely removed)")
    void tellerDashboard_shouldReturn404() throws Exception {
        mockMvc.perform(get("/teller/dashboard"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("GET /admin/dashboard MUST return HTTP 404 (Endpoint completely removed)")
    void adminDashboard_shouldReturn404() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR", "BRANCH_MANAGER"})
    @DisplayName("GET /supervisor/dashboard MUST return HTTP 404 (Endpoint completely removed)")
    void supervisorDashboard_shouldReturn404() throws Exception {
        mockMvc.perform(get("/supervisor/dashboard"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "cs1", roles = {"CS"})
    @DisplayName("GET /dashboard is the ONLY dashboard endpoint and returns HTTP 200 OK")
    void unifiedDashboard_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk());
    }
}
