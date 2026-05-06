package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("CsViewAdvice Integration Tests")
class CsViewAdviceTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        if (userRepository.findByUsername("csuser").isEmpty()) {
            User u = new User();
            u.setUsername("csuser");
            u.setPassword("$2a$10$dummy");
            u.setEmail("cs@tazkia.ac.id");
            u.setFullName("CS User");
            u.setApproved(true);
            u.setEnabled(true);
            userRepository.save(u);
        }
    }

    @Test
    @WithMockUser(username = "csuser", roles = {"CS"})
    @DisplayName("GET /cs/dashboard - CsViewAdvice menyuntikkan atribut model")
    void csDashboard_shouldHaveAdviceAttributes() throws Exception {
        mockMvc.perform(get("/cs/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/dashboard"))
                .andExpect(model().attributeExists("nowText", "roleLabel"));
    }

    @Test
    @DisplayName("GET /teller/dashboard - CsViewAdvice tidak aktif di /teller/")
    void tellerDashboard_csAdviceNotActive() throws Exception {
        mockMvc.perform(get("/teller/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/dashboard"));
    }
}
