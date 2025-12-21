package id.ac.tazkia.minibank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ActiveModuleFilter extends OncePerRequestFilter {

    private boolean isPublicOrTechPath(String uri) {
        return uri.equals("/")
                || uri.startsWith("/login")
                || uri.startsWith("/signup")
                || uri.startsWith("/logout")
                || uri.startsWith("/error")
                || uri.startsWith("/css/")
                || uri.startsWith("/js/")
                || uri.startsWith("/images/")
                || uri.startsWith("/api/")
                || uri.startsWith("/favicon.ico");
    }

    private String modulePrefix(String module) {
        if (module == null) return null;
        return switch (module) {
            case "CS" -> "/cs/";
            case "TELLER" -> "/teller/";
            case "SUPERVISOR" -> "/supervisor/";
            case "ADMIN" -> "/admin/";
            default -> null;
        };
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        // Jangan ganggu request yang harus bebas (assets + api + auth pages)
        if (isPublicOrTechPath(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String activeModule = (String) session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE);
        String prefix = modulePrefix(activeModule);

        // Kalau module belum diset, jangan sok mengarahkan
        if (prefix == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Kalau sudah sesuai module, lanjut
        if (uri.startsWith(prefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Selain itu: paksa balik ke dashboard module aktif
        response.sendRedirect(prefix + "dashboard");
    }
}
