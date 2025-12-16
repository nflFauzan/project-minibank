package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.SignupForm;
import id.ac.tazkia.minibank.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Authentication auth) {
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {

            Set<String> roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            if (roles.contains("ROLE_ADMIN")) return "redirect:/admin/dashboard";
            if (roles.contains("ROLE_SUPERVISOR")) return "redirect:/supervisor/dashboard";
            if (roles.contains("ROLE_CS")) return "redirect:/cs/dashboard";
            if (roles.contains("ROLE_TELLER")) return "redirect:/teller/dashboard";

            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new SignupForm());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute("form") SignupForm form,
                               BindingResult br,
                               RedirectAttributes ra) {
        if (br.hasErrors()) return "signup";

        registrationService.register(form);
        ra.addFlashAttribute("registered", true);
        return "redirect:/login?registered";
    }
}
