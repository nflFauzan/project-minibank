package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserRepository userRepo;
    private final AdminUserService adminService;

    @GetMapping("/pending")
    public String pendingList(Model model) {
        model.addAttribute("users", userRepo.findByApprovedFalse());
        return "admin-pending-users";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        adminService.approve(id);
        return "redirect:/admin/users/pending";
    }
}
