package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String index() {
        return "index"; // choice page with Sign In / Sign Up buttons
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value="error", required=false) String error,
                            @RequestParam(value="logout", required=false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid credentials");
        if (logout != null) model.addAttribute("message", "You have logged out");
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm() { return "signup"; }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String fullName,
                           Model model) {
        try {
            registrationService.registerNewUser(username, password, fullName);
            model.addAttribute("success", "Registration successful. Silakan login.");
            return "login";
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "signup";
        }
    }
}
