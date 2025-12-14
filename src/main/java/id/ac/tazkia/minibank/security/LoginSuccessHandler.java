package id.ac.tazkia.minibank.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // ambil pilihan modul dari form login
        String module = request.getParameter("module"); // CS / TELLER / SUPERVISOR / ADMIN
        String wantedRole = (module == null) ? null : "ROLE_" + module.toUpperCase();

        // kalau user memilih module, pastikan dia memang punya role itu
        if (wantedRole != null) {
            if (!roles.contains(wantedRole)) {
                // role dipilih tapi user tidak punya -> jangan biarkan login
                SecurityContextHolder.clearContext();
                request.getSession().invalidate();
                getRedirectStrategy().sendRedirect(request, response, "/login?error=role");
                return;
            }

            // set "active role" jadi hanya role yang dipilih
            var chosenAuthorities = authentication.getAuthorities().stream()
        .filter(a -> a.getAuthority().equals(wantedRole))
        .toList();

            UsernamePasswordAuthenticationToken newAuth =
                    new UsernamePasswordAuthenticationToken(
                            authentication.getPrincipal(),
                            authentication.getCredentials(),
                            chosenAuthorities
                    );
            newAuth.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            // redirect sesuai role yang dipilih
            getRedirectStrategy().sendRedirect(request, response, targetUrlByRole(wantedRole));
            return;
        }

        // fallback kalau tidak ada module (harusnya tidak terjadi karena field required)
        String targetUrl = "/";
        if (roles.contains("ROLE_ADMIN")) targetUrl = "/admin/dashboard";
        else if (roles.contains("ROLE_SUPERVISOR")) targetUrl = "/supervisor/dashboard";
        else if (roles.contains("ROLE_CS")) targetUrl = "/cs/dashboard";
        else if (roles.contains("ROLE_TELLER")) targetUrl = "/teller/dashboard";

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String targetUrlByRole(String role) {
        return switch (role) {
            case "ROLE_ADMIN" -> "/admin/dashboard";
            case "ROLE_SUPERVISOR" -> "/supervisor/dashboard";
            case "ROLE_CS" -> "/cs/dashboard";
            case "ROLE_TELLER" -> "/teller/dashboard";
            default -> "/";
        };
    }
}
