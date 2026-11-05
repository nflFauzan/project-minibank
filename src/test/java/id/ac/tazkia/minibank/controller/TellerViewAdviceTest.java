package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TellerViewAdvice Integration Tests")
class TellerViewAdviceTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        if (userRepository.findByUsername("telleruser").isEmpty()) {
            User u = new User();
            u.setUsername("telleruser");
            u.setPassword("$2a$10$dummy");
            u.setEmail("teller@tazkia.ac.id");
            u.setFullName("Teller User");
            u.setApproved(true);
            u.setEnabled(true);
            userRepository.save(u);
        }
    }

    @Test
    @DisplayName("GET /teller/transaction - TellerViewAdvice menambahkan atribut model")
    void tellerDashboard_shouldHaveAdviceAttributes() throws Exception {
        mockMvc.perform(get("/teller/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"));
    }

    @Test
    @DisplayName("GET /cs/customers - TellerViewAdvice tidak aktif di path /cs/")
    void csDashboard_tellerAdviceNotActive() throws Exception {
        mockMvc.perform(get("/cs/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers"));
    }
}
