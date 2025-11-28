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
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

     @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user) {
        System.out.println(">>> SIGNUP FIRED <<<");
        System.out.println(user);

        registrationService.registerStudent(user);

        return "redirect:/login?registered";
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
