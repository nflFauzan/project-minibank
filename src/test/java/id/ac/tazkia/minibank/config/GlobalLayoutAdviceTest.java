package id.ac.tazkia.minibank.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // Defaults to addFilters = true, which activates security filters
@ActiveProfiles("test")
@Transactional
@DisplayName("GlobalLayoutAdvice – Controller Advice Tests")
public class GlobalLayoutAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    // ──────────────────────────────────────────────────────────────────────────
    // 1. Direct Unit Tests (Fast branch verification)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Unit Tests (Isolated Method Calls)")
    class UnitTests {

        @Test
        @DisplayName("When authentication is null – returns immediately and injects no attributes")
        void authNull_shouldDoNothing() {
            GlobalLayoutAdvice advice = new GlobalLayoutAdvice();
            Model mockModel = mock(Model.class);
            
            advice.injectHeaderMeta(mockModel, null);
            
            verifyNoInteractions(mockModel);
        }

        @Test
        @DisplayName("When roles contain ROLE_ADMIN – injects 'Admin' label and userCode")
        void hasRoleAdmin_shouldInjectAdminLabel() {
            GlobalLayoutAdvice advice = new GlobalLayoutAdvice();
            Model mockModel = mock(Model.class);
            Authentication mockAuth = mock(Authentication.class);
            
            doReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .when(mockAuth).getAuthorities();
            when(mockAuth.getName()).thenReturn("adminUser");

            advice.injectHeaderMeta(mockModel, mockAuth);

            verify(mockModel).addAttribute("roleLabel", "Admin");
            verify(mockModel).addAttribute("userCode", "adminUser");
        }

        @Test
        @DisplayName("When roles contain ROLE_SUPERVISOR – injects 'Supervisor' label and userCode")
        void hasRoleSupervisor_shouldInjectSupervisorLabel() {
            GlobalLayoutAdvice advice = new GlobalLayoutAdvice();
            Model mockModel = mock(Model.class);
            Authentication mockAuth = mock(Authentication.class);
            
            doReturn(List.of(new SimpleGrantedAuthority("ROLE_SUPERVISOR")))
                    .when(mockAuth).getAuthorities();
            when(mockAuth.getName()).thenReturn("supervisorUser");

            advice.injectHeaderMeta(mockModel, mockAuth);

            verify(mockModel).addAttribute("roleLabel", "Supervisor");
            verify(mockModel).addAttribute("userCode", "supervisorUser");
        }

        @Test
        @DisplayName("When roles contain ROLE_TELLER – injects 'Teller' label and userCode")
        void hasRoleTeller_shouldInjectTellerLabel() {
            GlobalLayoutAdvice advice = new GlobalLayoutAdvice();
            Model mockModel = mock(Model.class);
            Authentication mockAuth = mock(Authentication.class);
            
            doReturn(List.of(new SimpleGrantedAuthority("ROLE_TELLER")))
                    .when(mockAuth).getAuthorities();
            when(mockAuth.getName()).thenReturn("tellerUser");

            advice.injectHeaderMeta(mockModel, mockAuth);

            verify(mockModel).addAttribute("roleLabel", "Teller");
            verify(mockModel).addAttribute("userCode", "tellerUser");
        }

        @Test
        @DisplayName("When roles contain ROLE_CS – injects 'Customer Service' label and userCode")
        void hasRoleCs_shouldInjectCsLabel() {
            GlobalLayoutAdvice advice = new GlobalLayoutAdvice();
            Model mockModel = mock(Model.class);
            Authentication mockAuth = mock(Authentication.class);
            
            doReturn(List.of(new SimpleGrantedAuthority("ROLE_CS")))
                    .when(mockAuth).getAuthorities();
            when(mockAuth.getName()).thenReturn("csUser");

            advice.injectHeaderMeta(mockModel, mockAuth);

            verify(mockModel).addAttribute("roleLabel", "Customer Service");
            verify(mockModel).addAttribute("userCode", "csUser");
        }

        @Test
        @DisplayName("When roles contain fallback roles (e.g., ROLE_USER) – injects 'User' label and userCode")
        void fallbackRole_shouldInjectUserLabel() {
            GlobalLayoutAdvice advice = new GlobalLayoutAdvice();
            Model mockModel = mock(Model.class);
            Authentication mockAuth = mock(Authentication.class);
            
            doReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                    .when(mockAuth).getAuthorities();
            when(mockAuth.getName()).thenReturn("standardUser");

            advice.injectHeaderMeta(mockModel, mockAuth);

            verify(mockModel).addAttribute("roleLabel", "User");
            verify(mockModel).addAttribute("userCode", "standardUser");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 2. MVC Integration Tests (Verifying global injection on controllers)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("MVC Integration Tests")
    class MvcIntegrationTests {

        @Test
        @DisplayName("GET / (anonymous) – should not inject any meta because auth is null")
        void anonymousRequest_shouldNotInjectMeta() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeDoesNotExist("roleLabel"))
                    .andExpect(model().attributeDoesNotExist("userCode"));
        }

        @Test
        @WithMockUser(username = "custom_admin", roles = {"ADMIN"})
        @DisplayName("GET /dashboard (authenticated as ADMIN) – should inject 'Admin' label and username")
        void authenticatedAdminRequest_shouldInjectAdminMeta() throws Exception {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("roleLabel", "Admin"))
                    .andExpect(model().attribute("userCode", "custom_admin"));
        }

        @Test
        @WithMockUser(username = "custom_supervisor", roles = {"SUPERVISOR"})
        @DisplayName("GET /dashboard (authenticated as SUPERVISOR) – should inject 'Supervisor' label and username")
        void authenticatedSupervisorRequest_shouldInjectSupervisorMeta() throws Exception {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("roleLabel", "Supervisor"))
                    .andExpect(model().attribute("userCode", "custom_supervisor"));
        }

        @Test
        @WithMockUser(username = "custom_teller", roles = {"TELLER"})
        @DisplayName("GET /dashboard (authenticated as TELLER) – should inject 'Teller' label and username")
        void authenticatedTellerRequest_shouldInjectTellerMeta() throws Exception {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("roleLabel", "Teller"))
                    .andExpect(model().attribute("userCode", "custom_teller"));
        }

        @Test
        @WithMockUser(username = "custom_cs", roles = {"CS"})
        @DisplayName("GET /dashboard (authenticated as CS) – should inject 'Customer Service' label and username")
        void authenticatedCsRequest_shouldInjectCsMeta() throws Exception {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("roleLabel", "Customer Service"))
                    .andExpect(model().attribute("userCode", "custom_cs"));
        }
    }
}
