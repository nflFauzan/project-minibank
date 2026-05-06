package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.RedirectStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("LoginSuccessHandler - Additional Integration Tests")
class LoginSuccessHandlerAdditionalTest extends BaseIntegrationTest {

    @Autowired private LoginSuccessHandler handler;

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

    private Authentication authWithRole(String role) {
        return new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority(role)));
    }

    @Test
    @DisplayName("CS module - salah role redirect ke /login?error=role")
    void csModule_wrongRole() throws Exception {
        when(request.getParameter("module")).thenReturn("CS");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));
        verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
    }

    @Test
    @DisplayName("TELLER module - salah role redirect ke /login?error=role")
    void tellerModule_wrongRole() throws Exception {
        when(request.getParameter("module")).thenReturn("TELLER");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));
        verify(redirectStrategy).sendRedirect(request, response, "/login?error=role");
    }

    @Test
    @DisplayName("module null - redirect ke /login?error=module")
    void moduleNull_redirectToError() throws Exception {
        when(request.getParameter("module")).thenReturn(null);
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));
        verify(redirectStrategy).sendRedirect(request, response, "/login?error=module");
    }

    @Test
    @DisplayName("module lowercase 'cs' - tetap diproses (uppercase normalization)")
    void moduleLowecase_normalizedToUppercase() throws Exception {
        when(request.getParameter("module")).thenReturn("cs");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));
        verify(redirectStrategy).sendRedirect(request, response, "/cs/dashboard");
    }

    @Test
    @DisplayName("SESSION_ACTIVE_MODULE - konstanta memiliki nilai 'ACTIVE_MODULE'")
    void sessionActiveModule_constantValue() {
        assertEquals("ACTIVE_MODULE", LoginSuccessHandler.SESSION_ACTIVE_MODULE);
    }
}
