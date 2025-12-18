package id.ac.tazkia.minibank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ActiveModuleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // biarkan static resources & logout lewat
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/images/")
                || uri.equals("/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        if (!loggedIn) {
            filterChain.doFilter(request, response);
            return;
        }

        Object m = request.getSession(false) == null ? null :
                request.getSession(false).getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE);

        if (m == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String module = String.valueOf(m).toUpperCase();
        String allowedPrefix;
        String dashboard;

        switch (module) {
            case "ADMIN" -> { allowedPrefix = "/admin/"; dashboard = "/admin/dashboard"; }
            case "SUPERVISOR" -> { allowedPrefix = "/supervisor/"; dashboard = "/supervisor/dashboard"; }
            case "CS" -> { allowedPrefix = "/cs/"; dashboard = "/cs/dashboard"; }
            case "TELLER" -> {
                allowedPrefix = "/teller/"; dashboard = "/teller/dashboard";
            }
            default -> {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // kalau user coba buka halaman di luar modul aktif -> paksa balik
        if (!uri.startsWith(allowedPrefix)) {
            response.sendRedirect(dashboard);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
