package id.ac.tazkia.minibank.config;

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
@DisplayName("RoleSecurityIntegrationTest")
public class RoleSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "bm_user", roles = {"BRANCH_MANAGER"})
    @DisplayName("User with ROLE_BRANCH_MANAGER should access /supervisor/nasabah")
    void branchManagerCanAccessSupervisorDashboard() throws Exception {
        mockMvc.perform(get("/supervisor/nasabah"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "teller_user", roles = {"TELLER"})
    @DisplayName("User without BRANCH_MANAGER should be forbidden from /supervisor/nasabah")
    void tellerForbiddenFromSupervisorDashboard() throws Exception {
        mockMvc.perform(get("/supervisor/nasabah"))
                .andExpect(status().isForbidden());
    }
}

