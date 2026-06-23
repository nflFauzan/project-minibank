package id.ac.tazkia.minibank.config;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.security.LoginSuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc // addFilters = true by default, enabling spring security filter chain
@ActiveProfiles("test")
@Transactional
@DisplayName("SecurityConfig – Web Security Policy & Access Control Tests")
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        // Seed users with different roles for authentication flows
        createTestUser("test_admin", "password", "ROLE_ADMIN");
        createTestUser("test_cs", "password", "ROLE_CS");
        createTestUser("test_teller", "password", "ROLE_TELLER");
        createTestUser("test_supervisor", "password", "ROLE_SUPERVISOR");
    }

    private void createTestUser(String username, String rawPassword, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName)));

        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setEmail(username + "@tazkia.ac.id");
        u.setFullName("Test " + username);
        u.setApproved(true);
        u.setEnabled(true);
        u.setRoles(Set.of(role));
        userRepository.save(u);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 1. Public Endpoints (permitAll)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Public Endpoint Policy (permitAll)")
    class PublicEndpoints {

        @Test
        @DisplayName("GET / - accessible without authentication")
        void getRoot_shouldBeOk() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }

        @Test
        @DisplayName("GET /login - accessible without authentication")
        void getLogin_shouldBeOk() throws Exception {
            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"));
        }

        @Test
        @DisplayName("GET /signup - accessible without authentication")
        void getSignup_shouldBeOk() throws Exception {
            mockMvc.perform(get("/signup"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("signup"));
        }

        @Test
        @DisplayName("GET /css/custom.css - static resource is accessible")
        void getStaticCss_shouldBeOk() throws Exception {
            mockMvc.perform(get("/css/custom.css"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("GET /js/app.js - non-existent static file returns 404, not redirected/forbidden")
        void getStaticJs_shouldReturn404() throws Exception {
            mockMvc.perform(get("/js/app.js"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("GET /api/postal-code/provinces - REST API is accessible without authentication")
        void getApiProvinces_shouldBeOk() throws Exception {
            mockMvc.perform(get("/api/postal-code/provinces"))
                    .andExpect(status().isOk());
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 2. Protected Endpoints (Anonymous Redirection)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Protected Endpoint Access Rules")
    class ProtectedEndpoints {

        @Test
        @DisplayName("GET /dashboard - anonymous access redirects to /login")
        void getDashboardAnonymous_shouldRedirectToLogin() throws Exception {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("/login")));
        }

        @Test
        @DisplayName("GET /admin/dashboard - anonymous access redirects to /login")
        void getAdminDashboardAnonymous_shouldRedirectToLogin() throws Exception {
            mockMvc.perform(get("/admin/dashboard"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("/login")));
        }

        @Test
        @DisplayName("GET /cs/dashboard - anonymous access redirects to /login")
        void getCsDashboardAnonymous_shouldRedirectToLogin() throws Exception {
            mockMvc.perform(get("/cs/dashboard"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("/login")));
        }

        @Test
        @DisplayName("GET /teller/dashboard - anonymous access redirects to /login")
        void getTellerDashboardAnonymous_shouldRedirectToLogin() throws Exception {
            mockMvc.perform(get("/teller/dashboard"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("/login")));
        }

        @Test
        @DisplayName("GET /supervisor/dashboard - anonymous access redirects to /login")
        void getSupervisorDashboardAnonymous_shouldRedirectToLogin() throws Exception {
            mockMvc.perform(get("/supervisor/dashboard"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("/login")));
        }

        @Test
        @DisplayName("GET /any-undefined-protected-path - anonymous access redirects to /login")
        void getUndefinedProtectedPathAnonymous_shouldRedirectToLogin() throws Exception {
            mockMvc.perform(get("/any-undefined-protected-path"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("/login")));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 3. Role-Based Authorization
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Role-Based Access Control (RBAC)")
    class RoleBasedAccessControl {

        @Test
        @WithMockUser(username = "adminUser", roles = {"ADMIN"})
        @DisplayName("ROLE_ADMIN - can access admin pages but not others")
        void adminAccessControl() throws Exception {
            mockMvc.perform(get("/admin/dashboard"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/cs/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/teller/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/supervisor/dashboard"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "csUser", roles = {"CS"})
        @DisplayName("ROLE_CS - can access CS pages but not others")
        void csAccessControl() throws Exception {
            mockMvc.perform(get("/admin/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/cs/dashboard"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/teller/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/supervisor/dashboard"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "tellerUser", roles = {"TELLER"})
        @DisplayName("ROLE_TELLER - can access Teller pages but not others")
        void tellerAccessControl() throws Exception {
            mockMvc.perform(get("/admin/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/cs/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/teller/dashboard"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/supervisor/dashboard"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "supervisorUser", roles = {"SUPERVISOR"})
        @DisplayName("ROLE_SUPERVISOR - can access Supervisor pages but not others")
        void supervisorAccessControl() throws Exception {
            mockMvc.perform(get("/admin/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/cs/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/teller/dashboard"))
                    .andExpect(status().isForbidden());

            mockMvc.perform(get("/supervisor/dashboard"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "anyUser", roles = {"USER"})
        @DisplayName("ROLE_USER - access to prefix paths is forbidden")
        void userAccessControl() throws Exception {
            mockMvc.perform(get("/admin/dashboard")).andExpect(status().isForbidden());
            mockMvc.perform(get("/cs/dashboard")).andExpect(status().isForbidden());
            mockMvc.perform(get("/teller/dashboard")).andExpect(status().isForbidden());
            mockMvc.perform(get("/supervisor/dashboard")).andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "anyUser", roles = {"ADMIN"})
        @DisplayName("authenticated user with ROLE_ADMIN - can access general authenticated endpoints")
        void authenticatedAccess_shouldBeOk() throws Exception {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("dashboard-admin"));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 4. Custom Login Success/Failure Flows
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Form Login & Success/Failure Flows")
    class FormLoginFlows {

        @Test
        @DisplayName("POST /login with admin credentials and module=ADMIN - triggers custom handler redirect")
        void loginSuccess_admin() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", "test_admin")
                            .param("password", "password")
                            .param("module", "ADMIN")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/admin/dashboard"));
        }

        @Test
        @DisplayName("POST /login with CS credentials and module=CS - triggers custom handler redirect")
        void loginSuccess_cs() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", "test_cs")
                            .param("password", "password")
                            .param("module", "CS")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/cs/dashboard"));
        }

        @Test
        @DisplayName("POST /login with Teller credentials and module=TELLER - triggers custom handler redirect")
        void loginSuccess_teller() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", "test_teller")
                            .param("password", "password")
                            .param("module", "TELLER")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/teller/dashboard"));
        }

        @Test
        @DisplayName("POST /login with Supervisor credentials and module=SUPERVISOR - triggers custom handler redirect")
        void loginSuccess_supervisor() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", "test_supervisor")
                            .param("password", "password")
                            .param("module", "SUPERVISOR")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/supervisor/dashboard"));
        }

        @Test
        @DisplayName("POST /login with invalid credentials - redirects to /login?error=true")
        void loginFailure_invalidCredentials() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", "test_admin")
                            .param("password", "wrongpassword")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login?error=true"));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 5. Logout Flow
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Logout Policy Flow")
    class LogoutFlows {

        @Test
        @DisplayName("POST /logout - redirects to /login?logout and invalidates credentials")
        void postLogout_shouldRedirectToLoginLogout() throws Exception {
            mockMvc.perform(post("/logout")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login?logout"));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 6. Security Filter Chain Custom Filters
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Security Custom Filter Configuration")
    class CustomFilterVerification {

        @Test
        @WithMockUser(username = "crossUser", roles = {"CS", "TELLER"})
        @DisplayName("GET /cs/dashboard with activeModule=TELLER in session - redirected to teller dashboard by ActiveModuleFilter")
        void activeModuleFilterMismatchedModule_shouldRedirectToActiveDashboard() throws Exception {
            mockMvc.perform(get("/cs/dashboard")
                            .sessionAttr(LoginSuccessHandler.SESSION_ACTIVE_MODULE, "TELLER"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/teller/dashboard"));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 7. Security Beans Verification
    // ──────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("PasswordEncoder bean should be BCryptPasswordEncoder")
    void passwordEncoderBean_shouldBeBCrypt() {
        assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);
    }
}
