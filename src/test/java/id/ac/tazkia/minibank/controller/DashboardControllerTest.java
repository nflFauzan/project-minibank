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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("DashboardControllerTest")
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin_user", roles = {"ADMIN"})
    @DisplayName("GET /dashboard for ROLE_ADMIN should redirect to /admin/dashboard")
    void adminDashboard_redirect() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"));
    }

    @Test
    @WithMockUser(username = "bm_user", roles = {"BRANCH_MANAGER"})
    @DisplayName("GET /dashboard for ROLE_BRANCH_MANAGER should redirect to /supervisor/dashboard")
    void branchManagerDashboard_redirect() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/dashboard"));
    }

    @Test
    @WithMockUser(username = "cs_user", roles = {"CS"})
    @DisplayName("GET /dashboard for ROLE_CS should redirect to /cs/dashboard")
    void csDashboard_redirect() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/dashboard"));
    }

    @Test
    @WithMockUser(username = "teller_user", roles = {"TELLER"})
    @DisplayName("GET /dashboard for ROLE_TELLER should redirect to /teller/dashboard")
    void tellerDashboard_redirect() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/dashboard"));
    }

    @Test
    @WithMockUser(username = "guest_user", roles = {"USER"})
    @DisplayName("GET /dashboard for user without operational role should render fallback dashboard view")
    void noRoleDashboard_view() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }
}
