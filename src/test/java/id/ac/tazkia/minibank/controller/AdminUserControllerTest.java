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
    @DisplayName("POST /admin/approve/{id} - approve user default")
    void approve_success() throws Exception {
        mockMvc.perform(post("/admin/approve/" + pendingUser.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard?approved"));

        User approved = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertTrue(approved.isApproved());
        assertTrue(approved.isEnabled());
        assertFalse(approved.getRoles().isEmpty());
    }

    @Test
    @DisplayName("POST /admin/approve/{id} - approve user dengan role custom")
    void approve_withCustomRoles() throws Exception {
        mockMvc.perform(post("/admin/approve/" + pendingUser.getId())
                        .param("roles", "ROLE_BRANCH_MANAGER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard?approved"));

        User approved = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertTrue(approved.isApproved());
        assertEquals(1, approved.getRoles().size());
        assertTrue(approved.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_BRANCH_MANAGER")));
    }

    @Test
    @DisplayName("POST /admin/reject/{id} - reject & hapus user dari DB")
    void reject_success() throws Exception {
        Long userId = pendingUser.getId();

        mockMvc.perform(post("/admin/reject/" + userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard?rejected"));

        assertFalse(userRepository.findById(userId).isPresent());
    }

    @Test
    @DisplayName("GET /admin/approval/{id} - detail approval page menyediakan availableRoles")
    void approval_detail() throws Exception {
        mockMvc.perform(get("/admin/approval/" + pendingUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/approval"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("availableRoles"));
    }

    @Test
    @DisplayName("GET /admin/users/create - menampilkan form create user langsung")
    void showCreateUserForm() throws Exception {
        mockMvc.perform(get("/admin/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user_create"))
                .andExpect(model().attributeExists("userDto"))
                .andExpect(model().attributeExists("availableRoles"));
    }

    @Test
    @DisplayName("POST /admin/users/create - membuat user baru langsung aktif")
    void createUser_success() throws Exception {
        mockMvc.perform(post("/admin/users/create")
                        .param("username", "directadminuser")
                        .param("password", "directpassword")
                        .param("email", "directadmin@tazkia.ac.id")
                        .param("fullName", "Direct Admin User")
                        .param("nim", "123456")
                        .param("prodi", "Akuntansi Syariah")
                        .param("roles", "ROLE_TELLER", "ROLE_CS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard?userCreated"));

        User created = userRepository.findByUsername("directadminuser").orElseThrow();
        assertTrue(created.isApproved());
        assertTrue(created.isEnabled());
        assertEquals(2, created.getRoles().size());
    }

    @Test
    @DisplayName("GET /admin/users/{id}/roles - form edit role user")
    void showEditUserRoles() throws Exception {
        mockMvc.perform(get("/admin/users/" + pendingUser.getId() + "/roles"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user_edit_roles"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("availableRoles"));
    }

    @Test
    @DisplayName("POST /admin/users/{id}/roles - update role user yang ada")
    void updateUserRoles_success() throws Exception {
        mockMvc.perform(post("/admin/users/" + pendingUser.getId() + "/roles")
                        .param("roles", "ROLE_BRANCH_MANAGER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard?rolesUpdated"));

        User updated = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertTrue(updated.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_BRANCH_MANAGER")));
    }
}
