package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.security.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, HttpServletRequest request, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        // If user selected or has an active module in session, prioritize it
        HttpSession session = request.getSession(false);
        String activeModule = (session != null) ? (String) session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE) : null;
        if (activeModule != null) {
            switch (activeModule) {
                case "CS" -> {
                    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS"))) {
                        return "redirect:/cs/dashboard";
                    }
                }
                case "TELLER" -> {
                    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER"))) {
                        return "redirect:/teller/dashboard";
                    }
                }
                case "BRANCH_MANAGER", "SUPERVISOR" -> {
                    if (auth.getAuthorities().stream().anyMatch(a ->
                            a.getAuthority().equals("ROLE_BRANCH_MANAGER") || a.getAuthority().equals("ROLE_SUPERVISOR"))) {
                        return "redirect:/supervisor/dashboard";
                    }
                }
                case "ADMIN" -> {
                    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        return "redirect:/admin/dashboard";
                    }
                }
            }
        }

        // Default hierarchy when no specific active module is set in session:
        // 1. Admin
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }

        // 2. Customer Service (primary front-line role)
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS"))) {
            return "redirect:/cs/dashboard";
        }

        // 3. Teller
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER"))) {
            return "redirect:/teller/dashboard";
        }

        // 4. Branch Manager (or Supervisor)
        if (auth.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_BRANCH_MANAGER") || a.getAuthority().equals("ROLE_SUPERVISOR"))) {
            return "redirect:/supervisor/dashboard";
        }

        // 5. Fallback for accounts with no operational role yet
        model.addAttribute("username", auth.getName());
        return "dashboard";
    }
}
