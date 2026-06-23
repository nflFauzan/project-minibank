package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.RedirectStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test coverage tambahan untuk LoginSuccessHandler, melengkapi:
 * - LoginSuccessHandlerTest (5 test)
 * - LoginSuccessHandlerAdditionalTest (5 test)
 *
 * <p>Branch yang ditutup oleh file ini:
 * <ul>
 *   <li>SUPERVISOR: role benar → /supervisor/dashboard</li>
 *   <li>SUPERVISOR: role salah → /login?error=role</li>
 *   <li>module kosong "" → /login?error=module</li>
 *   <li>module whitespace "  " → /login?error=module (setelah trim)</li>
 *   <li>module lowercase: teller, admin, supervisor → normalisasi uppercase</li>
 *   <li>Verifikasi SESSION_ACTIVE_MODULE di-set pada saat sukses</li>
 *   <li>Verifikasi SESSION_ACTIVE_MODULE TIDAK di-set saat role salah</li>
 *   <li>Verifikasi SESSION_ACTIVE_MODULE TIDAK di-set saat module tidak dikenal</li>
 *   <li>User dengan multiple roles (cross-role)</li>
 *   <li>Semua kombinasi modul salah yang belum tercakup</li>
 * </ul>
 */
@DisplayName("LoginSuccessHandler – Coverage Test (Gap Filler)")
class LoginSuccessHandlerCoverageTest extends BaseIntegrationTest {

    @Autowired
    private LoginSuccessHandler handler;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RedirectStrategy redirectStrategy;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        redirectStrategy = mock(RedirectStrategy.class);
        handler.setRedirectStrategy(redirectStrategy);
        when(request.getSession(true)).thenReturn(session);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helper
    // ──────────────────────────────────────────────────────────────────────────

    private Authentication authWithRole(String role) {
        return new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority(role)));
    }

    private Authentication authWithRoles(String... roles) {
        List<SimpleGrantedAuthority> authorities = java.util.Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new UsernamePasswordAuthenticationToken("user", "pass", authorities);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 1. SUPERVISOR – branch yang sama sekali belum diuji
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("SUPERVISOR module")
    class SupervisorModule {

        @Test
        @DisplayName("role benar → redirect ke /supervisor/dashboard")
        void supervisorModule_correctRole_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("SUPERVISOR");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
            assertEquals("/supervisor/dashboard", captor.getValue());
        }

        @Test
        @DisplayName("role ROLE_CS (salah) → redirect ke /login?error=role")
        void supervisorModule_wrongRole_CS_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("SUPERVISOR");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("role ROLE_ADMIN (salah) → redirect ke /login?error=role")
        void supervisorModule_wrongRole_ADMIN_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("SUPERVISOR");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("role ROLE_TELLER (salah) → redirect ke /login?error=role")
        void supervisorModule_wrongRole_TELLER_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("SUPERVISOR");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 2. Cross-role salah – kombinasi yang belum dicakup sebelumnya
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Cross-role salah – kombinasi yang belum ada di test lain")
    class CrossRoleErrors {

        @Test
        @DisplayName("ADMIN module + ROLE_TELLER → /login?error=role")
        void adminModule_roleTeller_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("ADMIN");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("ADMIN module + ROLE_SUPERVISOR → /login?error=role")
        void adminModule_roleSupervisor_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("ADMIN");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("CS module + ROLE_ADMIN → /login?error=role")
        void csModule_roleAdmin_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("CS module + ROLE_SUPERVISOR → /login?error=role")
        void csModule_roleSupervisor_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("TELLER module + ROLE_ADMIN → /login?error=role")
        void tellerModule_roleAdmin_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("TELLER");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }

        @Test
        @DisplayName("TELLER module + ROLE_SUPERVISOR → /login?error=role")
        void tellerModule_roleSupervisor_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("TELLER");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 3. module kosong / whitespace → default branch → /login?error=module
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("module kosong dan whitespace → default branch")
    class EmptyAndWhitespaceModule {

        @Test
        @DisplayName("module \"\" (kosong) → /login?error=module")
        void emptyModule_redirectsToModuleError() throws Exception {
            when(request.getParameter("module")).thenReturn("");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=module");
        }

        @Test
        @DisplayName("module \"  \" (spasi saja) → setelah trim menjadi \"\" → /login?error=module")
        void whitespaceModule_afterTrimBecomesEmpty_redirectsToModuleError() throws Exception {
            when(request.getParameter("module")).thenReturn("   ");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=module");
        }

        @Test
        @DisplayName("module \" CS \" (ada spasi di kiri-kanan) → dinormalisasi → /cs/dashboard")
        void moduleWithPaddingSpaces_trimmedAndUppercased_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn(" CS ");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(redirectStrategy).sendRedirect(request, response, "/cs/dashboard");
        }

        @Test
        @DisplayName("module \" SUPERVISOR \" (ada spasi) → dinormalisasi → /supervisor/dashboard")
        void supervisorWithPaddingSpaces_trimmedAndUppercased_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn(" SUPERVISOR ");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            verify(redirectStrategy).sendRedirect(request, response, "/supervisor/dashboard");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 4. Normalisasi lowercase – semua modul yang belum dicakup
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Normalisasi lowercase → uppercase sebelum switch")
    class LowercaseNormalization {

        @Test
        @DisplayName("module \"teller\" (lowercase) → /teller/dashboard")
        void tellerLowercase_normalizedToUppercase_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("teller");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));

            verify(redirectStrategy).sendRedirect(request, response, "/teller/dashboard");
        }

        @Test
        @DisplayName("module \"admin\" (lowercase) → /admin/dashboard")
        void adminLowercase_normalizedToUppercase_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("admin");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));

            verify(redirectStrategy).sendRedirect(request, response, "/admin/dashboard");
        }

        @Test
        @DisplayName("module \"supervisor\" (lowercase) → /supervisor/dashboard")
        void supervisorLowercase_normalizedToUppercase_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("supervisor");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            verify(redirectStrategy).sendRedirect(request, response, "/supervisor/dashboard");
        }

        @Test
        @DisplayName("module \"Teller\" (mixed case) → /teller/dashboard")
        void tellerMixedCase_normalizedToUppercase_redirectsToDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("Teller");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));

            verify(redirectStrategy).sendRedirect(request, response, "/teller/dashboard");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 5. Verifikasi SESSION_ACTIVE_MODULE di-set dengan nilai yang benar
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Session attribute SESSION_ACTIVE_MODULE – verifikasi set/tidak-set")
    class SessionAttributeVerification {

        @Test
        @DisplayName("CS sukses → session.setAttribute(ACTIVE_MODULE, \"CS\")")
        void csSuccess_sessionAttributeIsSetToCS() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(session).setAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE, "CS");
        }

        @Test
        @DisplayName("TELLER sukses → session.setAttribute(ACTIVE_MODULE, \"TELLER\")")
        void tellerSuccess_sessionAttributeIsSetToTELLER() throws Exception {
            when(request.getParameter("module")).thenReturn("TELLER");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));

            verify(session).setAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE, "TELLER");
        }

        @Test
        @DisplayName("ADMIN sukses → session.setAttribute(ACTIVE_MODULE, \"ADMIN\")")
        void adminSuccess_sessionAttributeIsSetToADMIN() throws Exception {
            when(request.getParameter("module")).thenReturn("ADMIN");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));

            verify(session).setAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE, "ADMIN");
        }

        @Test
        @DisplayName("SUPERVISOR sukses → session.setAttribute(ACTIVE_MODULE, \"SUPERVISOR\")")
        void supervisorSuccess_sessionAttributeIsSetToSUPERVISOR() throws Exception {
            when(request.getParameter("module")).thenReturn("SUPERVISOR");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_SUPERVISOR"));

            verify(session).setAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE, "SUPERVISOR");
        }

        @Test
        @DisplayName("lowercase 'cs' sukses → session.setAttribute(ACTIVE_MODULE, \"CS\") (uppercase)")
        void csLowercase_sessionAttributeStoredAsUppercase() throws Exception {
            when(request.getParameter("module")).thenReturn("cs");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            // Nilai yang disimpan ke session harus uppercase (hasil dari .toUpperCase())
            verify(session).setAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE, "CS");
        }

        @Test
        @DisplayName("role salah → session TIDAK boleh di-set")
        void wrongRole_sessionAttributeNeverSet() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));

            // Session tidak boleh disentuh sama sekali — early return sebelum setAttribute
            verify(session, never()).setAttribute(anyString(), any());
        }

        @Test
        @DisplayName("module tidak dikenal → session TIDAK boleh di-set")
        void unknownModule_sessionAttributeNeverSet() throws Exception {
            when(request.getParameter("module")).thenReturn("INVALID");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(session, never()).setAttribute(anyString(), any());
        }

        @Test
        @DisplayName("module null → session TIDAK boleh di-set")
        void nullModule_sessionAttributeNeverSet() throws Exception {
            when(request.getParameter("module")).thenReturn(null);
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(session, never()).setAttribute(anyString(), any());
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 6. Verifikasi tidak ada double redirect (chain vs redirect)
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Verifikasi hanya satu sendRedirect dipanggil per request")
    class SingleRedirectVerification {

        @Test
        @DisplayName("Sukses → tepat 1 kali sendRedirect ke dashboard")
        void success_exactlyOneRedirect() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(redirectStrategy, times(1)).sendRedirect(any(), any(), any());
        }

        @Test
        @DisplayName("Role salah → tepat 1 kali sendRedirect ke /login?error=role")
        void wrongRole_exactlyOneRedirectToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));

            verify(redirectStrategy, times(1)).sendRedirect(any(), any(), eq("/login?error=role"));
        }

        @Test
        @DisplayName("Module tidak dikenal → tepat 1 kali sendRedirect ke /login?error=module")
        void unknownModule_exactlyOneRedirectToModuleError() throws Exception {
            when(request.getParameter("module")).thenReturn("GHOST");
            handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

            verify(redirectStrategy, times(1)).sendRedirect(any(), any(), eq("/login?error=module"));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 7. Multiple roles – user yang punya lebih dari 1 authority
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("User dengan multiple roles")
    class MultipleRolesAuthentication {

        @Test
        @DisplayName("user punya ROLE_CS + ROLE_ADMIN, module=CS → /cs/dashboard")
        void multiRole_csAndAdmin_moduleCS_redirectsToCsDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("CS");
            handler.onAuthenticationSuccess(request, response,
                    authWithRoles("ROLE_CS", "ROLE_ADMIN"));

            verify(redirectStrategy).sendRedirect(request, response, "/cs/dashboard");
        }

        @Test
        @DisplayName("user punya ROLE_CS + ROLE_ADMIN, module=ADMIN → /admin/dashboard")
        void multiRole_csAndAdmin_moduleADMIN_redirectsToAdminDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("ADMIN");
            handler.onAuthenticationSuccess(request, response,
                    authWithRoles("ROLE_CS", "ROLE_ADMIN"));

            verify(redirectStrategy).sendRedirect(request, response, "/admin/dashboard");
        }

        @Test
        @DisplayName("user punya ROLE_TELLER + ROLE_SUPERVISOR, module=SUPERVISOR → /supervisor/dashboard")
        void multiRole_tellerAndSupervisor_moduleSUPERVISOR_redirectsToSupervisorDashboard() throws Exception {
            when(request.getParameter("module")).thenReturn("SUPERVISOR");
            handler.onAuthenticationSuccess(request, response,
                    authWithRoles("ROLE_TELLER", "ROLE_SUPERVISOR"));

            verify(redirectStrategy).sendRedirect(request, response, "/supervisor/dashboard");
        }

        @Test
        @DisplayName("user punya ROLE_CS + ROLE_TELLER, module=ADMIN (tidak punya) → /login?error=role")
        void multiRole_csAndTeller_moduleADMIN_redirectsToRoleError() throws Exception {
            when(request.getParameter("module")).thenReturn("ADMIN");
            handler.onAuthenticationSuccess(request, response,
                    authWithRoles("ROLE_CS", "ROLE_TELLER"));

            verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 8. Module tidak dikenal – berbagai variasi string
    // ──────────────────────────────────────────────────────────────────────────

    @ParameterizedTest(name = "module [{0}] → /login?error=module")
    @ValueSource(strings = {
            "MANAGER", "AUDITOR", "OWNER", "ROOT", "USER",
            "CS_ADMIN", "SUPER", "TELR", "123"
    })
    @DisplayName("String module tidak dikenal → /login?error=module")
    void unrecognizedModuleStrings_redirectToModuleError(String module) throws Exception {
        when(request.getParameter("module")).thenReturn(module);
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

        verify(redirectStrategy).sendRedirect(request, response, "/login?error=module");
    }
}
