package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("LoginSuccessHandler – Coverage Test for Unified Dashboard Routing")
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
        when(request.getSession(false)).thenReturn(session);
    }

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

    @ParameterizedTest
    @ValueSource(strings = {"ROLE_CS", "ROLE_TELLER", "ROLE_BRANCH_MANAGER", "ROLE_SUPERVISOR", "ROLE_ADMIN"})
    @DisplayName("Single role login unconditionally redirects to /dashboard")
    void singleRole_redirectsToDashboard(String role) throws Exception {
        handler.onAuthenticationSuccess(request, response, authWithRole(role));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/dashboard", captor.getValue());
    }

    @Test
    @DisplayName("Multiple roles login unconditionally redirects to /dashboard")
    void multiRole_redirectsToDashboard() throws Exception {
        handler.onAuthenticationSuccess(request, response, authWithRoles("ROLE_CS", "ROLE_TELLER", "ROLE_BRANCH_MANAGER"));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/dashboard", captor.getValue());
    }

    @Test
    @DisplayName("User without specific roles also redirects to /dashboard")
    void noRole_redirectsToDashboard() throws Exception {
        handler.onAuthenticationSuccess(request, response, authWithRoles());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/dashboard", captor.getValue());
    }

    @Test
    @DisplayName("Login with query parameters or leftover module parameter still redirects cleanly to /dashboard")
    void withModuleParam_stillRedirectsToDashboard() throws Exception {
        when(request.getParameter("module")).thenReturn("CS");
        handler.onAuthenticationSuccess(request, response, authWithRole("ROLE_CS"));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redirectStrategy).sendRedirect(eq(request), eq(response), captor.capture());
        assertEquals("/dashboard", captor.getValue());
    }
}
