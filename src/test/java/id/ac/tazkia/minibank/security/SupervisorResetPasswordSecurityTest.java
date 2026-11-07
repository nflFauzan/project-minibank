package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupervisorResetPasswordSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long studentUserId;

    @BeforeEach
    void setUp() {
        Role roleTeller = roleRepository.findByName("ROLE_TELLER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_TELLER")));

        User student = userRepository.findByUsername("student_teller").orElse(null);
        if (student == null) {
            student = new User();
            student.setUsername("student_teller");
            student.setPassword(passwordEncoder.encode("oldPassword123"));
            student.setEmail("student@tazkia.ac.id");
            student.setFullName("Mahasiswa Teller");
            student.setApproved(true);
            student.setEnabled(true);
            student.setRoles(Set.of(roleTeller));
            student = userRepository.save(student);
        }
        studentUserId = student.getId();
    }

    @Test
    @WithMockUser(username = "spv1", roles = {"SUPERVISOR"})
    @DisplayName("SUPERVISOR can view users list")
    void supervisorCanViewUsersList() throws Exception {
        mockMvc.perform(get("/rbac/users/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/list"));
    }

    @Test
    @WithMockUser(username = "spv1", roles = {"SUPERVISOR"})
    @DisplayName("SUPERVISOR can view user detail")
    void supervisorCanViewUserDetail() throws Exception {
        mockMvc.perform(get("/rbac/users/view/" + studentUserId))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/view"));
    }

    @Test
    @WithMockUser(username = "spv1", roles = {"SUPERVISOR"})
    @DisplayName("SUPERVISOR can access reset password form for user")
    void supervisorCanAccessResetPasswordForm() throws Exception {
        mockMvc.perform(get("/rbac/users/" + studentUserId + "/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/password"));
    }

    @Test
    @WithMockUser(username = "spv1", roles = {"SUPERVISOR"})
    @DisplayName("SUPERVISOR can successfully reset password for a user")
    void supervisorCanResetPassword() throws Exception {
        mockMvc.perform(post("/rbac/users/" + studentUserId + "/password")
                        .with(csrf())
                        .param("userId", String.valueOf(studentUserId))
                        .param("newPassword", "newPassword456")
                        .param("confirmPassword", "newPassword456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rbac/users/view/" + studentUserId))
                .andExpect(flash().attributeExists("successMessage"));

        User updated = userRepository.findById(studentUserId).orElseThrow();
        assertTrue(passwordEncoder.matches("newPassword456", updated.getPassword()));
    }

    @Test
    @WithMockUser(username = "spv1", roles = {"SUPERVISOR"})
    @DisplayName("SUPERVISOR is forbidden from creating users directly")
    void supervisorCannotCreateUsers() throws Exception {
        mockMvc.perform(get("/rbac/users/create"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "spv1", roles = {"SUPERVISOR"})
    @DisplayName("SUPERVISOR is forbidden from managing roles directly")
    void supervisorCannotManageRoles() throws Exception {
        mockMvc.perform(get("/rbac/roles/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "teller1", roles = {"TELLER"})
    @DisplayName("TELLER cannot access password reset form")
    void tellerCannotResetPassword() throws Exception {
        mockMvc.perform(get("/rbac/users/" + studentUserId + "/password"))
                .andExpect(status().isForbidden());
    }
}

