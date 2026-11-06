package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.rbac.UserCreateForm;
import id.ac.tazkia.minibank.dto.rbac.UserEditForm;
import id.ac.tazkia.minibank.dto.rbac.UserPasswordForm;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.service.RbacUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rbac/users")
@RequiredArgsConstructor
public class RbacUserController {

    private final RbacUserService rbacUserService;

    public static final List<String> STUDY_PROGRAMS = List.of(
            "Ekonomi Syariah",
            "Manajemen Bisnis Syariah",
            "Akuntansi Syariah",
            "Hukum Ekonomi Syariah",
            "Komunikasi dan Penyiaran Islam"
    );

    public static final List<String> AVAILABLE_ROLES = List.of(
            "ROLE_CS",
            "ROLE_TELLER",
            "ROLE_BRANCH_MANAGER",
            "ROLE_ADMIN"
    );

    @GetMapping("/list")
    public String listUsers(@RequestParam(name = "search", required = false) String search,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size,
                            Model model) {
        Page<User> usersPage = rbacUserService.searchUsers(search,
                PageRequest.of(page, size, Sort.by("id").descending()));

        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("page", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("totalElements", usersPage.getTotalElements());
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("form")) {
            UserCreateForm form = new UserCreateForm();
            form.setRoles(new ArrayList<>(List.of("ROLE_CS")));
            model.addAttribute("form", form);
        }
        model.addAttribute("branches", rbacUserService.listActiveBranches());
        model.addAttribute("availableRoles", AVAILABLE_ROLES);
        model.addAttribute("studyPrograms", STUDY_PROGRAMS);
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/create";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("form") UserCreateForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("branches", rbacUserService.listActiveBranches());
            model.addAttribute("availableRoles", AVAILABLE_ROLES);
            model.addAttribute("studyPrograms", STUDY_PROGRAMS);
            model.addAttribute("activeNav", "rbac-users");
            return "rbac/users/create";
        }

        try {
            rbacUserService.createUser(form);
            ra.addFlashAttribute("successMessage", "Pengguna " + form.getUsername() + " berhasil dibuat.");
            return "redirect:/rbac/users/list";
        } catch (IllegalArgumentException ex) {
            result.reject("duplicate", ex.getMessage());
            model.addAttribute("branches", rbacUserService.listActiveBranches());
            model.addAttribute("availableRoles", AVAILABLE_ROLES);
            model.addAttribute("studyPrograms", STUDY_PROGRAMS);
            model.addAttribute("activeNav", "rbac-users");
            return "rbac/users/create";
        }
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = rbacUserService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = rbacUserService.findById(id);

        UserEditForm form = new UserEditForm();
        form.setId(user.getId());
        form.setUsername(user.getUsername());
        form.setFullName(user.getFullName());
        form.setEmail(user.getEmail());
        if (user.getBranch() != null) {
            form.setBranchId(user.getBranch().getId());
        }
        form.setEnabled(user.isEnabled());
        form.setLocked(user.isLocked());

        model.addAttribute("form", form);
        model.addAttribute("user", user);
        model.addAttribute("branches", rbacUserService.listActiveBranches());
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("form") UserEditForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes ra) {
        form.setId(id);
        if (result.hasErrors()) {
            User user = rbacUserService.findById(id);
            model.addAttribute("user", user);
            model.addAttribute("branches", rbacUserService.listActiveBranches());
            model.addAttribute("activeNav", "rbac-users");
            return "rbac/users/edit";
        }

        try {
            rbacUserService.updateUser(form);
            ra.addFlashAttribute("successMessage", "Data pengguna berhasil diperbarui.");
            return "redirect:/rbac/users/view/" + id;
        } catch (IllegalArgumentException ex) {
            result.reject("error", ex.getMessage());
            User user = rbacUserService.findById(id);
            model.addAttribute("user", user);
            model.addAttribute("branches", rbacUserService.listActiveBranches());
            model.addAttribute("activeNav", "rbac-users");
            return "rbac/users/edit";
        }
    }

    @GetMapping("/{id}/password")
    public String showPasswordForm(@PathVariable Long id, Model model) {
        User user = rbacUserService.findById(id);

        UserPasswordForm form = new UserPasswordForm();
        form.setUserId(user.getId());
        form.setUsername(user.getUsername());
        form.setFullName(user.getFullName());
        form.setEmail(user.getEmail());
        form.setActive(user.isEnabled());

        model.addAttribute("form", form);
        model.addAttribute("user", user);
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/password";
    }

    @PostMapping("/{id}/password")
    public String setPassword(@PathVariable Long id,
                              @Valid @ModelAttribute("form") UserPasswordForm form,
                              BindingResult result,
                              Model model,
                              RedirectAttributes ra) {
        form.setUserId(id);
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Konfirmasi password tidak cocok dengan password baru.");
        }

        if (result.hasErrors()) {
            User user = rbacUserService.findById(id);
            form.setUsername(user.getUsername());
            form.setFullName(user.getFullName());
            form.setEmail(user.getEmail());
            form.setActive(user.isEnabled());
            model.addAttribute("user", user);
            model.addAttribute("activeNav", "rbac-users");
            return "rbac/users/password";
        }

        rbacUserService.setPassword(id, form.getNewPassword());
        ra.addFlashAttribute("successMessage", "Password pengguna " + form.getUsername() + " berhasil diperbarui.");
        return "redirect:/rbac/users/view/" + id;
    }

    @GetMapping("/{id}/roles")
    public String showRolesManagement(@PathVariable Long id, Model model) {
        User user = rbacUserService.findById(id);
        List<Role> allRoles = rbacUserService.listActiveRoles();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/roles";
    }

    @PostMapping("/{id}/roles/assign")
    public String assignRole(@PathVariable Long id,
                             @RequestParam("roleId") Long roleId,
                             RedirectAttributes ra) {
        try {
            rbacUserService.assignRole(id, roleId);
            ra.addFlashAttribute("successMessage", "Role berhasil ditugaskan.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/rbac/users/" + id + "/roles";
    }

    @PostMapping("/{id}/roles/remove")
    public String removeRole(@PathVariable Long id,
                             @RequestParam(value = "roleId", required = false) Long roleId,
                             @RequestParam(value = "userRoleId", required = false) Long userRoleId,
                             RedirectAttributes ra) {
        Long targetRoleId = roleId != null ? roleId : userRoleId;
        if (targetRoleId != null) {
            rbacUserService.removeRole(id, targetRoleId);
            ra.addFlashAttribute("successMessage", "Role berhasil dihapus dari pengguna.");
        }
        return "redirect:/rbac/users/" + id + "/roles";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id, RedirectAttributes ra) {
        rbacUserService.toggleActive(id, false);
        ra.addFlashAttribute("successMessage", "Pengguna berhasil dinonaktifkan.");
        return "redirect:/rbac/users/list";
    }

    @PostMapping("/{id}/activate")
    public String activateUser(@PathVariable Long id, RedirectAttributes ra) {
        rbacUserService.toggleActive(id, true);
        ra.addFlashAttribute("successMessage", "Pengguna berhasil diaktifkan.");
        return "redirect:/rbac/users/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        try {
            rbacUserService.deleteUser(id);
            ra.addFlashAttribute("successMessage", "Pengguna berhasil dihapus.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Gagal menghapus pengguna: " + ex.getMessage());
        }
        return "redirect:/rbac/users/list";
    }
}

