package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> pending = userRepo.findByApprovedFalse();
        model.addAttribute("pending", pending);
        return "admin/dashboard";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        userRepo.findById(id).ifPresent(u -> {
            u.setApproved(true);
            u.setEnabled(true);
            userRepo.save(u);
        });
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        userRepo.findById(id).ifPresent(u -> userRepo.delete(u));
        return "redirect:/admin/dashboard";
    }
}
