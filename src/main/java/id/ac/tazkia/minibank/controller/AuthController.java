package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/signup")
    public String signupForm() { return "signup"; }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String nim,
                           @RequestParam String prodi,
                           @RequestParam String dosenPembimbing,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }
        try {
            User u = new User();
            u.setFullName(fullName);
            u.setEmail(email);
            u.setNim(nim);
            u.setProdi(prodi);
            u.setDosenPembimbing(dosenPembimbing);
            u.setUsername(username);
            u.setPassword(password);
            registrationService.registerStudent(u);
            model.addAttribute("success", "Registration submitted. Wait for approval by your admin (dosen).");
            return "login";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            @RequestParam(required = false) String accessDenied,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid credentials");
        if (logout != null) model.addAttribute("message", "Logged out");
        if (accessDenied != null) model.addAttribute("error", "Access denied for module selected");
        return "login";
    }
}
