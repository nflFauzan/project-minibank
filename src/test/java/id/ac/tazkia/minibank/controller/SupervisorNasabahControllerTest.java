package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("SupervisorNasabahController Integration Tests")
class SupervisorNasabahControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private UserRepository userRepository;

    private Long nasabahId;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C8888001");
        n.setNik("8888000000000001");
        n.setNamaSesuaiIdentitas("Test Pending Nasabah");
        n.setStatus(NasabahStatus.INACTIVE);
        n = nasabahRepository.save(n);
        nasabahId = n.getId();

        if (userRepository.findByUsername("supervisor1").isEmpty()) {
            User u = new User();
            u.setUsername("supervisor1");
            u.setPassword("$2a$10$dummy");
            u.setEmail("supervisor@tazkia.ac.id");
            u.setFullName("Supervisor Satu");
            u.setApproved(true);
            u.setEnabled(true);
            userRepository.save(u);
        }
    }

    @Test
    @DisplayName("GET /supervisor/nasabah - list pending")
    void list_shouldReturnPending() throws Exception {
        mockMvc.perform(get("/supervisor/nasabah").param("filter", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/nasabah/list"))
                .andExpect(model().attributeExists("list", "filter", "pendingCount"));
    }

    @Test
    @DisplayName("GET /supervisor/nasabah/{id} - detail view")
    void detail_shouldReturnView() throws Exception {
        mockMvc.perform(get("/supervisor/nasabah/" + nasabahId))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/nasabah/detail"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @WithMockUser(username = "supervisor1", roles = {"SUPERVISOR"})
    @DisplayName("POST /supervisor/nasabah/{id}/approve - approve & status berubah di DB")
    void approve_shouldRedirect() throws Exception {
        mockMvc.perform(post("/supervisor/nasabah/" + nasabahId + "/approve")
                        .param("notes", "ok"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah"))
                .andExpect(flash().attributeExists("success"));

        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.ACTIVE, after.getStatus());
    }

    @Test
    @DisplayName("POST /supervisor/nasabah/{id}/reject - without reason should fail")
    void reject_withoutReason() throws Exception {
        mockMvc.perform(post("/supervisor/nasabah/" + nasabahId + "/reject")
                        .param("reason", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah/" + nasabahId))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @WithMockUser(username = "supervisor1", roles = {"SUPERVISOR"})
    @DisplayName("POST /supervisor/nasabah/{id}/reject - with reason should redirect & status REJECTED")
    void reject_withReason() throws Exception {
        mockMvc.perform(post("/supervisor/nasabah/" + nasabahId + "/reject")
                        .param("reason", "Data tidak lengkap")
                        .param("notes", "nope"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah"))
                .andExpect(flash().attributeExists("success"));

        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.REJECTED, after.getStatus());
    }
}
