package id.ac.tazkia.minibank.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalLayoutAdvice {

    @ModelAttribute
    public void injectHeaderMeta(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) return;

        String username = auth.getName();
        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        boolean isAdmin = roles.contains("ROLE_ADMIN");
        boolean isBranchManager = roles.contains("ROLE_BRANCH_MANAGER") || roles.contains("ROLE_SUPERVISOR");
        boolean isCs = roles.contains("ROLE_CS");
        boolean isTeller = roles.contains("ROLE_TELLER");

        String roleLabel = "Staff";
        if (isBranchManager || isAdmin) {
            roleLabel = "Branch Manager";
        } else if (isCs) {
            roleLabel = "Customer Service";
        } else if (isTeller) {
            roleLabel = "Teller";
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isBranchManager", isBranchManager);
        model.addAttribute("isCs", isCs);
        model.addAttribute("isTeller", isTeller);
        model.addAttribute("roleLabel", roleLabel);
        model.addAttribute("username", username);
        model.addAttribute("userCode", username);
        model.addAttribute("employeeId", username);

        // Date & time in Indonesian format
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy | HH:mm", new Locale("id", "ID"));
        String formattedNow = LocalDateTime.now().format(fmt);
        model.addAttribute("nowText", formattedNow);
        model.addAttribute("currentDate", formattedNow);
    }
}
