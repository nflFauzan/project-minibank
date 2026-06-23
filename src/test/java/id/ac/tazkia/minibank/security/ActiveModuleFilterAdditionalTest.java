package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

/**
 * Test tambahan untuk ActiveModuleFilter yang melengkapi ActiveModuleFilterTest.
 *
 * <p>Coverage gap yang ditutup:
 * <ul>
 *   <li>Semua public/tech path selain /login (root, /signup, /logout, /error, /css/, /js/,
 *       /images/, /api/, /favicon.ico)</li>
 *   <li>Session ada tapi activeModule null → pass-through</li>
 *   <li>Session ada tapi activeModule unknown (bukan CS/TELLER/SUPERVISOR/ADMIN) → pass-through</li>
 *   <li>Semua modul yang benar mengakses prefix-nya masing-masing (TELLER, SUPERVISOR, ADMIN)</li>
 *   <li>Semua modul yang salah di-redirect ke dashboard modul yang aktif
 *       (TELLER, SUPERVISOR, ADMIN accessing prefix yang bukan miliknya)</li>
 *   <li>URI yang baru dimulai mirip prefix tapi tidak sama persis (e.g. /css vs /css/)</li>
 * </ul>
 */
@DisplayName("ActiveModuleFilter – Additional Integration Tests")
class ActiveModuleFilterAdditionalTest extends BaseIntegrationTest {

    @Autowired
    private ActiveModuleFilter filter;

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers
    // ──────────────────────────────────────────────────────────────────────────

    private HttpServletRequest requestFor(String uri) {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn(uri);
        return req;
    }

    private HttpServletRequest requestWithSession(String uri, String activeModule) {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getRequestURI()).thenReturn(uri);
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn(activeModule);
        return req;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 1. Public / Tech path – semua varian
    // ──────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Public & Tech Paths – harus pass-through tanpa menyentuh session")
    class PublicAndTechPaths {

        @ParameterizedTest(name = "URI [{0}] → pass-through")
        @ValueSource(strings = {
                "/",
                "/login",
                "/login?error=role",
                "/signup",
                "/signup/register",
                "/logout",
                "/error",
                "/error/404",
                "/css/main.css",
                "/js/app.js",
                "/images/logo.png",
                "/api/health",
                "/api/v1/data",
                "/favicon.ico"
        })
        void publicOrTechPath_shouldPassThrough(String uri) throws Exception {
            HttpServletRequest req = requestFor(uri);
            HttpServletResponse res = mock(HttpServletResponse.class);
            FilterChain chain = mock(FilterChain.class);

            filter.doFilter(req, res, chain);

            verify(chain).doFilter(req, res);
            // Tidak boleh ada redirect
            verify(res, never()).sendRedirect(anyString());
            // Tidak boleh menyentuh session sama sekali
            verify(req, never()).getSession(anyBoolean());
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 2. Session ada, activeModule null → pass-through
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("session ada, activeModule null → pass-through (prefix null)")
    void sessionExists_activeModuleNull_shouldPassThrough() throws Exception {
        HttpServletRequest req = requestWithSession("/cs/customers", null);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 3. Session ada, activeModule tidak dikenal → pass-through
    // ──────────────────────────────────────────────────────────────────────────

    @ParameterizedTest(name = "activeModule [{0}] → tidak dikenal, pass-through")
    @ValueSource(strings = {"UNKNOWN", "MANAGER", "AUDITOR", "", "cs", "teller"})
    @DisplayName("activeModule tidak dikenal → modulePrefix null → pass-through")
    void sessionExists_unknownActiveModule_shouldPassThrough(String module) throws Exception {
        HttpServletRequest req = requestWithSession("/cs/customers", module);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 4. Modul TELLER – mengakses prefix sendiri → pass-through
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TELLER mengakses /teller/dashboard → pass-through")
    void tellerModule_accessingTellerDashboard_shouldPassThrough() throws Exception {
        HttpServletRequest req = requestWithSession("/teller/dashboard", "TELLER");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    @DisplayName("TELLER mengakses /teller/transaksi → pass-through")
    void tellerModule_accessingTellerTransaksi_shouldPassThrough() throws Exception {
        HttpServletRequest req = requestWithSession("/teller/transaksi", "TELLER");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 5. Modul SUPERVISOR – mengakses prefix sendiri → pass-through
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("SUPERVISOR mengakses /supervisor/dashboard → pass-through")
    void supervisorModule_accessingSupervisorDashboard_shouldPassThrough() throws Exception {
        HttpServletRequest req = requestWithSession("/supervisor/dashboard", "SUPERVISOR");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 6. Modul ADMIN – mengakses prefix sendiri → pass-through
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("ADMIN mengakses /admin/dashboard → pass-through")
    void adminModule_accessingAdminDashboard_shouldPassThrough() throws Exception {
        HttpServletRequest req = requestWithSession("/admin/dashboard", "ADMIN");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    @DisplayName("ADMIN mengakses /admin/users → pass-through")
    void adminModule_accessingAdminUsers_shouldPassThrough() throws Exception {
        HttpServletRequest req = requestWithSession("/admin/users", "ADMIN");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(res, never()).sendRedirect(anyString());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 7. Redirect – modul salah → redirect ke dashboard modul aktif
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TELLER mengakses /cs/ → redirect ke /teller/dashboard")
    void tellerModule_accessingCsPath_shouldRedirectToTellerDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/cs/customers", "TELLER");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/teller/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("TELLER mengakses /admin/ → redirect ke /teller/dashboard")
    void tellerModule_accessingAdminPath_shouldRedirectToTellerDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/admin/users", "TELLER");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/teller/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("SUPERVISOR mengakses /cs/ → redirect ke /supervisor/dashboard")
    void supervisorModule_accessingCsPath_shouldRedirectToSupervisorDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/cs/customers", "SUPERVISOR");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/supervisor/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("SUPERVISOR mengakses /teller/ → redirect ke /supervisor/dashboard")
    void supervisorModule_accessingTellerPath_shouldRedirectToSupervisorDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/teller/transaksi", "SUPERVISOR");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/supervisor/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("ADMIN mengakses /cs/ → redirect ke /admin/dashboard")
    void adminModule_accessingCsPath_shouldRedirectToAdminDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/cs/customers", "ADMIN");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/admin/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("ADMIN mengakses /supervisor/ → redirect ke /admin/dashboard")
    void adminModule_accessingSupervisorPath_shouldRedirectToAdminDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/supervisor/laporan", "ADMIN");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/admin/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("CS mengakses /supervisor/ → redirect ke /cs/dashboard")
    void csModule_accessingSupervisorPath_shouldRedirectToCsDashboard() throws Exception {
        HttpServletRequest req = requestWithSession("/supervisor/laporan", "CS");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/cs/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 8. Edge case: URI mirip prefix tapi bukan sub-path yang valid
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("CS mengakses /cs (tanpa trailing slash) → tidak dianggap prefix /cs/ → redirect")
    void csModule_accessingCsWithoutTrailingSlash_shouldRedirect() throws Exception {
        // /cs TIDAK startsWith /cs/ → harus redirect
        HttpServletRequest req = requestWithSession("/cs", "CS");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/cs/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("TELLER mengakses /teller (tanpa trailing slash) → redirect ke /teller/dashboard")
    void tellerModule_accessingTellerWithoutTrailingSlash_shouldRedirect() throws Exception {
        HttpServletRequest req = requestWithSession("/teller", "TELLER");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).sendRedirect("/teller/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 9. Verifikasi: tidak ada chain.doFilter setelah redirect
    // ──────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Setelah redirect, filter chain tidak boleh dipanggil (tidak ada double response)")
    void afterRedirect_filterChainMustNotBeCalled() throws Exception {
        HttpServletRequest req = requestWithSession("/admin/users", "CS");
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res, times(1)).sendRedirect("/cs/dashboard");
        verifyNoInteractions(chain);
    }
}
