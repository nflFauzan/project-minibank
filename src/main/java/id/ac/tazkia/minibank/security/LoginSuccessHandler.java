package id.ac.tazkia.minibank.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    // We allow students to choose any module at login (per your decision)
    // Admin must choose Admin module to access admin area — check below.

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String module = request.getParameter("module"); // expected values: ADMIN, CS, TELLER, SUPERVISOR
        if (module == null) module = "CS";

        // normalize
        module = module.toUpperCase();

        if ("ADMIN".equals(module)) {
            // ensure user has ROLE_ADMIN
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/dashboard");
                return;
            } else {
                // not admin: logout then show access denied page
                request.getSession().invalidate();
                response.sendRedirect("/login?accessDenied");
                return;
            }
        }

        // for CS/TELLER/SUPERVISOR - we allow any approved user to access (they choose module at login)
        switch (module) {
            case "TELLER":
                response.sendRedirect("/teller/dashboard");
                break;
            case "SUPERVISOR":
                response.sendRedirect("/supervisor/dashboard");
                break;
            case "CS":
            default:
                response.sendRedirect("/cs/dashboard");
                break;
        }
    }
}
