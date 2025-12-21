package id.ac.tazkia.minibank.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // dipakai ActiveModuleFilter
    public static final String SESSION_ACTIVE_MODULE = "ACTIVE_MODULE";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String module = request.getParameter("module");
        module = (module == null) ? "" : module.trim().toUpperCase();

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        String targetUrl;

        switch (module) {
            case "ADMIN" -> {
                if (!roles.contains("ROLE_ADMIN")) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=role");
                    return;
                }
                targetUrl = "/admin/dashboard";
            }
            case "SUPERVISOR" -> {
                if (!roles.contains("ROLE_SUPERVISOR")) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=role");
                    return;
                }
                targetUrl = "/supervisor/dashboard";
            }
            case "CS" -> {
                if (!roles.contains("ROLE_CS")) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=role");
                    return;
                }
                // WAJIB: balik ke flow yang kamu minta
                targetUrl = "/cs/dashboard";
            }
            case "TELLER" -> {
                if (!roles.contains("ROLE_TELLER")) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=role");
                    return;
                }
                targetUrl = "/teller/dashboard";
            }
            default -> {
                getRedirectStrategy().sendRedirect(request, response, "/login?error=module");
                return;
            }
        }

        request.getSession(true).setAttribute(SESSION_ACTIVE_MODULE, module);
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
