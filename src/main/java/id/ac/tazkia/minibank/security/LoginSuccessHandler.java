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

    public static final String SESSION_ACTIVE_MODULE = "ACTIVE_MODULE";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        String module = request.getParameter("module");
        if (module == null) module = "";
        module = module.trim().toUpperCase();

        String requiredRole;
        String targetUrl;

        switch (module) {
            case "ADMIN" -> { requiredRole = "ROLE_ADMIN"; targetUrl = "/admin/dashboard"; }
            case "SUPERVISOR" -> { requiredRole = "ROLE_SUPERVISOR"; targetUrl = "/supervisor/dashboard"; }
            case "CS" -> { requiredRole = "ROLE_CS"; targetUrl = "/cs/dashboard"; }
            case "TELLER" -> { requiredRole = "ROLE_TELLER"; targetUrl = "/teller/dashboard"; }
            default -> {
                // kalau user pilih kosong/aneh, lempar balik
                getRedirectStrategy().sendRedirect(request, response, "/login?error=module");
                return;
            }
        }

        // kalau tidak punya role yg dipilih -> tolak
        if (!roles.contains(requiredRole)) {
            getRedirectStrategy().sendRedirect(request, response, "/login?error=role");
            return;
        }

        request.getSession(true).setAttribute(SESSION_ACTIVE_MODULE, module);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
