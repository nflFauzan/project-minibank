package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.AdminCreateUserDto;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminService;

    private static final List<String> AVAILABLE_ROLES = List.of(
            "ROLE_CS",
            "ROLE_TELLER",
            "ROLE_BRANCH_MANAGER",
            "ROLE_ADMIN"
    );

    // Sub-dashboard /admin/dashboard removed. All roles use unified /dashboard.

    @GetMapping("/approval/{id}")
    public String approvalDetail(@PathVariable Long id, Model model) {
        User u = adminService.findById(id);
        model.addAttribute("user", u);
        model.addAttribute("maskedPassword", "********");
        model.addAttribute("maskedConfirm", "********");
        model.addAttribute("availableRoles", AVAILABLE_ROLES);
        return "admin/approval";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id, @RequestParam(name = "roles", required = false) List<String> roles) {
        if (roles != null && !roles.isEmpty()) {
            adminService.approve(id, roles);
        } else {
            adminService.approve(id);
        }
        return "redirect:/dashboard?approved";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        adminService.reject(id);
        return "redirect:/dashboard?rejected";
    }

    @GetMapping("/users/create")
    public String showCreateUserForm() {
        return "redirect:/rbac/users/create";
    }

    @PostMapping("/users/create")
    public String createUser(@Valid @ModelAttribute("userDto") AdminCreateUserDto dto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("availableRoles", AVAILABLE_ROLES);
            return "admin/user_create";
        }
        adminService.createUser(dto);
        return "redirect:/dashboard?userCreated";
    }

    @GetMapping("/users/{id}/roles")
    public String showEditUserRoles(@PathVariable Long id, Model model) {
        User u = adminService.findById(id);
        model.addAttribute("user", u);
        model.addAttribute("availableRoles", AVAILABLE_ROLES);
        return "admin/user_edit_roles";
    }

    @PostMapping("/users/{id}/roles")
    public String updateUserRoles(@PathVariable Long id,
                                  @RequestParam(name = "roles", required = false) List<String> roles) {
        adminService.updateRoles(id, roles);
        return "redirect:/dashboard?rolesUpdated";
    }
}
