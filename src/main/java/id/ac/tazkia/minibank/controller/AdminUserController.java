package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", adminService.listPending()); // FIX: "users"
        return "admin/dashboard";
    }

    @GetMapping("/approval/{id}")
    public String approvalDetail(@PathVariable Long id, Model model) {
        User u = adminService.findById(id);
        model.addAttribute("user", u);
        model.addAttribute("maskedPassword", "********");
        model.addAttribute("maskedConfirm", "********");
        return "admin/approval";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        adminService.approve(id);
        return "redirect:/admin/dashboard?approved";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        adminService.reject(id);
        return "redirect:/admin/dashboard?rejected";
    }
}
