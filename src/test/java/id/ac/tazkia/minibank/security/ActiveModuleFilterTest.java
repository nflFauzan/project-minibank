package id.ac.tazkia.minibank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@DisplayName("ActiveModuleFilter Unit Tests")
class ActiveModuleFilterTest {

    private final ActiveModuleFilter filter = new ActiveModuleFilter();

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
    @DisplayName("wrong module (CS accessing /teller/) - redirect ke /cs/dashboard")
    void wrongModule_shouldRedirect() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getRequestURI()).thenReturn("/teller/dashboard");
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("CS");

        filter.doFilter(req, res, chain);
        verify(res).sendRedirect("/cs/dashboard");
        verify(chain, never()).doFilter(req, res);
    }
}
