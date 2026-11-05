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
    @DisplayName("GET /dashboard for ROLE_ADMIN should render dashboard directly with Branch Manager label")
    void adminDashboard_rendersDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("roleLabel", "Branch Manager"))
                .andExpect(model().attribute("userInitial", "A"))
                .andExpect(model().attribute("isAdmin", true))
                .andExpect(model().attributeExists("totalCustomers", "activeAccounts", "totalDeposits", "todayTransactions"));
    }

    @Test
    @WithMockUser(username = "bm_user", roles = {"BRANCH_MANAGER"})
    @DisplayName("GET /dashboard for ROLE_BRANCH_MANAGER should render dashboard directly with Branch Manager label")
    void branchManagerDashboard_rendersDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("roleLabel", "Branch Manager"))
                .andExpect(model().attribute("userInitial", "B"))
                .andExpect(model().attribute("isBranchManager", true))
                .andExpect(model().attributeExists("totalCustomers", "activeAccounts", "totalDeposits", "todayTransactions"));
    }

    @Test
    @WithMockUser(username = "cs_user", roles = {"CS"})
    @DisplayName("GET /dashboard for ROLE_CS should render dashboard directly with Customer Service label")
    void csDashboard_rendersDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("roleLabel", "Customer Service"))
                .andExpect(model().attribute("userInitial", "C"))
                .andExpect(model().attribute("isCs", true))
                .andExpect(model().attributeExists("totalCustomers", "activeAccounts", "totalDeposits", "todayTransactions"));
    }

    @Test
    @WithMockUser(username = "teller_user", roles = {"TELLER"})
    @DisplayName("GET /dashboard for ROLE_TELLER should render dashboard directly with Teller label")
    void tellerDashboard_rendersDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("roleLabel", "Teller"))
                .andExpect(model().attribute("userInitial", "T"))
                .andExpect(model().attribute("isTeller", true))
                .andExpect(model().attributeExists("totalCustomers", "activeAccounts", "totalDeposits", "todayTransactions"));
    }

    @Test
    @WithMockUser(username = "guest_user", roles = {"USER"})
    @DisplayName("GET /dashboard for user with no operational role should render dashboard with Staff label")
    void noRoleDashboard_rendersDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("roleLabel", "Staff"))
                .andExpect(model().attribute("username", "guest_user"));
    }

    @Test
    @DisplayName("GET /dashboard unauthenticated should redirect to /login")
    void unauthenticated_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
