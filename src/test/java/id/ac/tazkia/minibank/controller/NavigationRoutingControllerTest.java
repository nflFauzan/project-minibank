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
@DisplayName("NavigationRoutingControllerTest")
public class NavigationRoutingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "CS")
    @DisplayName("GET /product/list redirects to /cs/product")
    void productList_redirects() throws Exception {
        mockMvc.perform(get("/product/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/product"));
    }

    @Test
    @WithMockUser(roles = "CS")
    @DisplayName("GET /customer/list redirects to /cs/customers")
    void customerList_redirects() throws Exception {
        mockMvc.perform(get("/customer/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"));
    }

    @Test
    @WithMockUser(roles = "CS")
    @DisplayName("GET /customer/create redirects to /cs/customers/new")
    void customerCreate_redirects() throws Exception {
        mockMvc.perform(get("/customer/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers/new"));
    }

    @Test
    @WithMockUser(roles = "CS")
    @DisplayName("GET /account/list redirects to /cs/account")
    void accountList_redirects() throws Exception {
        mockMvc.perform(get("/account/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account"));
    }

    @Test
    @WithMockUser(roles = "CS")
    @DisplayName("GET /account/open redirects to /cs/account/open")
    void accountOpen_redirects() throws Exception {
        mockMvc.perform(get("/account/open"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account/open"));
    }

    @Test
    @WithMockUser(roles = "TELLER")
    @DisplayName("GET /transaction/list redirects to /teller/transaction/list")
    void transactionList_redirects() throws Exception {
        mockMvc.perform(get("/transaction/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"));
    }

    @Test
    @WithMockUser(roles = "TELLER")
    @DisplayName("GET /transaction/cash-deposit redirects to /teller/transaction/deposit")
    void transactionDeposit_redirects() throws Exception {
        mockMvc.perform(get("/transaction/cash-deposit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/deposit"));
    }

    @Test
    @WithMockUser(roles = "TELLER")
    @DisplayName("GET /passbook/select-account redirects to /teller/passbook/select-account")
    void passbookSelectAccount_redirects() throws Exception {
        mockMvc.perform(get("/passbook/select-account"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/passbook/select-account"));
    }

    @Test
    @WithMockUser(roles = "SUPERVISOR")
    @DisplayName("GET /approval/queue for SUPERVISOR redirects to /supervisor/nasabah")
    void approvalQueueSupervisor_redirects() throws Exception {
        mockMvc.perform(get("/approval/queue"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /approval/queue for ADMIN redirects to /dashboard")
    void approvalQueueAdmin_redirects() throws Exception {
        mockMvc.perform(get("/approval/queue"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /rbac/users/list redirects to /dashboard")
    void rbacUsersList_redirects() throws Exception {
        mockMvc.perform(get("/rbac/users/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /rbac/users/create redirects to /admin/users/create")
    void rbacUsersCreate_redirects() throws Exception {
        mockMvc.perform(get("/rbac/users/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/create"));
    }
}

