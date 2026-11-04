package id.ac.tazkia.minibank.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        // 1. Admin
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }

        // 2. Branch Manager (or Supervisor)
        if (auth.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_BRANCH_MANAGER") || a.getAuthority().equals("ROLE_SUPERVISOR"))) {
            return "redirect:/supervisor/dashboard";
        }

        // 3. Customer Service
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS"))) {
            return "redirect:/cs/dashboard";
        }

        // 4. Teller
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER"))) {
            return "redirect:/teller/dashboard";
        }

        // 5. Fallback for accounts with no operational role yet
        model.addAttribute("username", auth.getName());
        return "dashboard";
    }
}
