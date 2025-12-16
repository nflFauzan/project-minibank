package id.ac.tazkia.minibank.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String module = request.getParameter("module"); // dari <select name="module">
        if (module == null || module.isBlank()) {
            response.sendRedirect("/login?error=role");
            return;
        }

        String requiredRole = "ROLE_" + module.toUpperCase();

        boolean hasRequiredRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(requiredRole::equals);

        if (!hasRequiredRole) {
            // user tidak punya role yang dipilih
            response.sendRedirect("/login?error=role");
            return;
        }

        // simpan role aktif di session (opsional, tapi bagus buat “mode” yang dipilih)
        request.getSession(true).setAttribute("ACTIVE_ROLE", requiredRole);

        String targetUrl = switch (module.toUpperCase()) {
            case "ADMIN" -> "/admin/dashboard";
            case "SUPERVISOR" -> "/supervisor/dashboard";
            case "TELLER" -> "/teller/dashboard";
            default -> "/cs/dashboard";
        };

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
