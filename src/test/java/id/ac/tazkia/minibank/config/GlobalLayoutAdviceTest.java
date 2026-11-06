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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("GlobalLayoutAdviceTest")
public class GlobalLayoutAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "fauzan", roles = {"CS", "TELLER", "SUPERVISOR"})
    @DisplayName("GlobalLayoutAdvice injects multi-role flags and Branch Manager roleLabel on CS page")
    void multiRoleUserOnCsPage_hasUnifiedAttributes() throws Exception {
        mockMvc.perform(get("/cs/customers"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isCs", true))
                .andExpect(model().attribute("isTeller", true))
                .andExpect(model().attribute("isBranchManager", true))
                .andExpect(model().attribute("roleLabel", "Branch Manager"))
                .andExpect(model().attribute("username", "fauzan"));
    }

    @Test
    @WithMockUser(username = "fauzan", roles = {"CS", "TELLER", "SUPERVISOR"})
    @DisplayName("GlobalLayoutAdvice injects multi-role flags and Branch Manager roleLabel on Teller page")
    void multiRoleUserOnTellerPage_hasUnifiedAttributes() throws Exception {
        mockMvc.perform(get("/teller/transaction/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isCs", true))
                .andExpect(model().attribute("isTeller", true))
                .andExpect(model().attribute("isBranchManager", true))
                .andExpect(model().attribute("roleLabel", "Branch Manager"))
                .andExpect(model().attribute("username", "fauzan"));
    }

    @Test
    @WithMockUser(username = "teller_only", roles = {"TELLER"})
    @DisplayName("Single role user has accurate role flags and Teller roleLabel")
    void singleRoleTeller_hasAccurateFlags() throws Exception {
        mockMvc.perform(get("/teller/transaction/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isTeller", true))
                .andExpect(model().attribute("isCs", false))
                .andExpect(model().attribute("isBranchManager", false))
                .andExpect(model().attribute("roleLabel", "Teller"))
                .andExpect(model().attribute("username", "teller_only"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("GlobalLayoutAdvice injects superuser role flags for ROLE_ADMIN")
    void adminUser_hasAllRoleFlags() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdmin", true))
                .andExpect(model().attribute("isCs", true))
                .andExpect(model().attribute("isTeller", true))
                .andExpect(model().attribute("isBranchManager", true))
                .andExpect(model().attribute("roleLabel", "Branch Manager"))
                .andExpect(model().attribute("username", "admin"));
    }
}
