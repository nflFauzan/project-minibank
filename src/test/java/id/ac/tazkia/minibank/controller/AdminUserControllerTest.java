package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("AdminUserController Integration Tests")
class AdminUserControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;

    private User pendingUser;

    @BeforeEach
    void setUp() {
        User u = new User();
        u.setUsername("pendinguser");
        u.setPassword("$2a$10$dummyhash");
        u.setEmail("pending@tazkia.ac.id");
        u.setFullName("Pending User");
        u.setApproved(false);
        u.setEnabled(false);
        pendingUser = userRepository.save(u);
    }

    @Test
    @DisplayName("GET /admin/dashboard - menampilkan daftar user pending")
    void listPending_shouldReturnView() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @DisplayName("POST /admin/approve/{id} - approve user & DB berubah")
    void approve_success() throws Exception {
        mockMvc.perform(post("/admin/approve/" + pendingUser.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard?approved"));

        User approved = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertTrue(approved.isApproved());
        assertTrue(approved.isEnabled());
        assertFalse(approved.getRoles().isEmpty());
    }

    @Test
    @DisplayName("POST /admin/reject/{id} - reject & hapus user dari DB")
    void reject_success() throws Exception {
        Long userId = pendingUser.getId();

        mockMvc.perform(post("/admin/reject/" + userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard?rejected"));

        assertFalse(userRepository.findById(userId).isPresent());
    }

    @Test
    @DisplayName("GET /admin/approval/{id} - detail approval page")
    void approval_detail() throws Exception {
        mockMvc.perform(get("/admin/approval/" + pendingUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/approval"))
                .andExpect(model().attributeExists("user"));
    }
}
