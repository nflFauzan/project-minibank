package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.security.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TellerViewAdvice Unit Tests")
class TellerViewAdviceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private Authentication auth;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private TellerViewAdvice tellerViewAdvice;

    @Test
    @DisplayName("injectTellerTopbar - should inject attributes when URI starts with /teller/ and module is TELLER")
    void injectTellerTopbar_success() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("TELLER");
        when(auth.getName()).thenReturn("telleruser");
        
        User user = new User();
        user.setFullName("Teller User");
        when(userRepository.findByUsername("telleruser")).thenReturn(Optional.of(user));

        tellerViewAdvice.injectTellerTopbar(model, request, auth);

        verify(model).addAttribute("roleLabel", "Teller");
        verify(model).addAttribute("employeeId", "telleruser");
        verify(model).addAttribute("currentFullName", "Teller User");
        verify(model).addAttribute(eq("nowText"), anyString());
    }

    @Test
    @DisplayName("injectTellerTopbar - should skip if request is null")
    void injectTellerTopbar_requestNull() {
        tellerViewAdvice.injectTellerTopbar(model, null, auth);
        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectTellerTopbar - should skip if URI is null")
    void injectTellerTopbar_uriNull() {
        when(request.getRequestURI()).thenReturn(null);
        tellerViewAdvice.injectTellerTopbar(model, request, auth);
        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectTellerTopbar - should skip if URI does not start with /teller/")
    void injectTellerTopbar_wrongUri() {
        when(request.getRequestURI()).thenReturn("/cs/dashboard");
        tellerViewAdvice.injectTellerTopbar(model, request, auth);
        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectTellerTopbar - should skip if session is null")
    void injectTellerTopbar_sessionNull() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");
        when(request.getSession(false)).thenReturn(null);
        tellerViewAdvice.injectTellerTopbar(model, request, auth);
        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectTellerTopbar - should skip if active module is not TELLER")
    void injectTellerTopbar_wrongModule() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("CS");
        
        tellerViewAdvice.injectTellerTopbar(model, request, auth);
        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectTellerTopbar - should skip if active module is missing")
    void injectTellerTopbar_noModule() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn(null);
        
        tellerViewAdvice.injectTellerTopbar(model, request, auth);
        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectTellerTopbar - should use username if auth is null")
    void injectTellerTopbar_authNull() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("TELLER");
        
        when(userRepository.findByUsername("-")).thenReturn(Optional.empty());

        tellerViewAdvice.injectTellerTopbar(model, request, null);

        verify(model).addAttribute("employeeId", "-");
        verify(model).addAttribute("currentFullName", "-");
    }

    @Test
    @DisplayName("injectTellerTopbar - should use username if full name is blank")
    void injectTellerTopbar_blankFullName() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE)).thenReturn("TELLER");
        when(auth.getName()).thenReturn("telleruser");
        
        User user = new User();
        user.setFullName(" ");
        user.setUsername("telleruser");
        when(userRepository.findByUsername("telleruser")).thenReturn(Optional.of(user));

        tellerViewAdvice.injectTellerTopbar(model, request, auth);

        verify(model).addAttribute("currentFullName", "telleruser");
    }
}
