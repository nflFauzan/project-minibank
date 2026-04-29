package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
@DisplayName("CsViewAdvice Unit Tests")
class CsViewAdviceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private Authentication auth;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CsViewAdvice csViewAdvice;

    @Test
    @DisplayName("injectCsHeader - should inject attributes when URI starts with /cs")
    void injectCsHeader_shouldInjectWhenUriStartsWithCs() {
        when(request.getRequestURI()).thenReturn("/cs/dashboard");
        when(auth.getName()).thenReturn("testuser");
        
        User user = new User();
        user.setFullName("Test User");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        csViewAdvice.injectCsHeader(model, auth, request);

        verify(model).addAttribute(eq("nowText"), anyString());
        verify(model).addAttribute("roleLabel", "Customer Service");
        verify(model).addAttribute("currentFullName", "Test User");
        verify(model).addAttribute("employeeId", "testuser");
    }

    @Test
    @DisplayName("injectCsHeader - should use username if full name is missing")
    void injectCsHeader_shouldUseUsernameIfFullNameMissing() {
        when(request.getRequestURI()).thenReturn("/cs/customers");
        when(auth.getName()).thenReturn("testuser");
        
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        csViewAdvice.injectCsHeader(model, auth, request);

        verify(model).addAttribute("currentFullName", "testuser");
    }

    @Test
    @DisplayName("injectCsHeader - should skip if URI is null")
    void injectCsHeader_shouldSkipIfUriNull() {
        when(request.getRequestURI()).thenReturn(null);

        csViewAdvice.injectCsHeader(model, auth, request);

        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectCsHeader - should skip if URI does not start with /cs")
    void injectCsHeader_shouldSkipIfUriDoesNotStartWithCs() {
        when(request.getRequestURI()).thenReturn("/teller/dashboard");

        csViewAdvice.injectCsHeader(model, auth, request);

        verifyNoInteractions(model);
    }

    @Test
    @DisplayName("injectCsHeader - should not add user info if auth is null")
    void injectCsHeader_shouldNotAddUserInfoIfAuthNull() {
        when(request.getRequestURI()).thenReturn("/cs/dashboard");

        csViewAdvice.injectCsHeader(model, null, request);

        verify(model).addAttribute(eq("nowText"), anyString());
        verify(model).addAttribute("roleLabel", "Customer Service");
        verify(model, never()).addAttribute(eq("currentFullName"), any());
        verify(model, never()).addAttribute(eq("employeeId"), any());
    }
}
