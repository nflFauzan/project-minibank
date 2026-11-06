package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

@DisplayName("ActiveModuleFilter Integration Tests")
class ActiveModuleFilterTest extends BaseIntegrationTest {

    @Autowired private ActiveModuleFilter filter;

    @Test
    @DisplayName("public path (/login) - langsung lewat")
    void publicPath_shouldPassThrough() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        when(req.getRequestURI()).thenReturn("/login");

        filter.doFilter(req, res, chain);
        verify(chain).doFilter(req, res);
    }

    @Test
    @DisplayName("no session - langsung lewat")
    void noSession_shouldPassThrough() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        when(req.getRequestURI()).thenReturn("/cs/customers");
        when(req.getSession(false)).thenReturn(null);

        filter.doFilter(req, res, chain);
        verify(chain).doFilter(req, res);
    }

    @Test
    @DisplayName("correct module (CS accessing /cs/) - langsung lewat")
    void correctModule_shouldPassThrough() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getRequestURI()).thenReturn("/cs/customers");
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("CS");

        filter.doFilter(req, res, chain);
        verify(chain).doFilter(req, res);
    }

    @Test
    @DisplayName("wrong module (CS accessing /teller/) - redirect ke /dashboard")
    void wrongModule_shouldRedirect() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getRequestURI()).thenReturn("/teller/transaction");
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("CS");

        filter.doFilter(req, res, chain);
        verify(res).sendRedirect("/dashboard");
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    @DisplayName("ROLE_ADMIN user accessing cross-module paths (/teller/) - langsung lewat")
    void adminUser_crossModule_shouldPassThrough() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);
        org.springframework.security.core.Authentication auth = mock(org.springframework.security.core.Authentication.class);
        org.springframework.security.core.GrantedAuthority authority = new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN");

        doReturn(java.util.List.of(authority)).when(auth).getAuthorities();
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);

        try {
            when(req.getRequestURI()).thenReturn("/teller/transaction");
            when(req.getSession(false)).thenReturn(session);
            when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("ADMIN");

            filter.doFilter(req, res, chain);
            verify(chain).doFilter(req, res);
            verify(res, never()).sendRedirect(anyString());
        } finally {
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
        }
    }
}
