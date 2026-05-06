package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.RedirectStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("LoginSuccessHandler Integration Tests")
class LoginSuccessHandlerTest extends BaseIntegrationTest {

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
    @DisplayName("CS module - redirect ke /cs/dashboard")
    void onSuccess_CS_module() throws Exception {
        when(request.getParameter("module")).thenReturn("CS");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/cs/dashboard", captor.getValue());
    }

    @Test
    @DisplayName("TELLER module - redirect ke /teller/dashboard")
    void onSuccess_TELLER_module() throws Exception {
        when(request.getParameter("module")).thenReturn("TELLER");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_TELLER"));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/teller/dashboard", captor.getValue());
    }

    @Test
    @DisplayName("ADMIN module - redirect ke /admin/dashboard")
    void onSuccess_ADMIN_module() throws Exception {
        when(request.getParameter("module")).thenReturn("ADMIN");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_ADMIN"));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/admin/dashboard", captor.getValue());
    }

    @Test
    @DisplayName("Wrong role - redirect ke /login?error=role")
    void onSuccess_wrongRole() throws Exception {
        when(request.getParameter("module")).thenReturn("ADMIN");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/login?error=role", captor.getValue());
    }

    @Test
    @DisplayName("Unknown module - redirect ke /login?error=module")
    void onSuccess_unknownModule() throws Exception {
        when(request.getParameter("module")).thenReturn("UNKNOWN");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/login?error=module", captor.getValue());
    }
}
