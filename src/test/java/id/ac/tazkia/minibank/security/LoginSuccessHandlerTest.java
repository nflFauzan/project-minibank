package id.ac.tazkia.minibank.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("LoginSuccessHandlerTest")
public class LoginSuccessHandlerTest {

    private final LoginSuccessHandler handler = new LoginSuccessHandler();

    @Test
    @DisplayName("Login success without module parameter should redirect directly to /dashboard")
    void loginSuccess_shouldRedirectToDashboard() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "cs_user",
                "pass",
                List.of(new SimpleGrantedAuthority("ROLE_CS"))
        );

        handler.onAuthenticationSuccess(request, response, auth);

        assertEquals("/dashboard", response.getRedirectedUrl());
    }
}
