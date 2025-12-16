package id.ac.tazkia.minibank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ActiveRoleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        if (!loggedIn) {
            chain.doFilter(request, response);
            return;
        }

        // skip static + auth endpoints
        if (path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images")
                || path.startsWith("/logout") || path.startsWith("/login") || path.startsWith("/signup")) {
            chain.doFilter(request, response);
            return;
        }

        String activeRole = (String) request.getSession().getAttribute("ACTIVE_ROLE");
        if (activeRole == null) {
            // kalau belum ada (legacy session), biarin lewat atau set default dari role tertinggi
            chain.doFilter(request, response);
            return;
        }

        // mapping path -> required ACTIVE_ROLE
        if (path.startsWith("/cs") && !activeRole.equals("ROLE_CS")) {
            response.sendRedirect(dashboardFor(activeRole));
            return;
        }
        if (path.startsWith("/teller") && !activeRole.equals("ROLE_TELLER")) {
            response.sendRedirect(dashboardFor(activeRole));
            return;
        }
        if (path.startsWith("/supervisor") && !activeRole.equals("ROLE_SUPERVISOR")) {
            response.sendRedirect(dashboardFor(activeRole));
            return;
        }
        if (path.startsWith("/admin") && !activeRole.equals("ROLE_ADMIN")) {
            response.sendRedirect(dashboardFor(activeRole));
            return;
        }

        chain.doFilter(request, response);
    }

    private String dashboardFor(String role) {
        return switch (role) {
            case "ROLE_ADMIN" -> "/admin/dashboard";
            case "ROLE_SUPERVISOR" -> "/supervisor/dashboard";
            case "ROLE_TELLER" -> "/teller/dashboard";
            default -> "/cs/dashboard";
        };
    }
}
