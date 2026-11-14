package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.rbac.UserCreateForm;
import id.ac.tazkia.minibank.dto.rbac.UserEditForm;
import id.ac.tazkia.minibank.dto.rbac.UserPasswordForm;
import id.ac.tazkia.minibank.entity.Branch;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.service.AdminUserService;
import id.ac.tazkia.minibank.service.RbacUserService;
import id.ac.tazkia.minibank.service.UserActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/rbac/users")
@RequiredArgsConstructor
@Slf4j
public class RbacUserController {

    private final RbacUserService rbacUserService;
    private final AdminUserService adminService;
    private final UserActivityService userActivityService;

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
                            @RequestParam(name = "status", required = false) String status,
                            @RequestParam(name = "role", required = false) String role,
                            @RequestParam(name = "branchId", required = false) Long branchId,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size,
                            Model model) {
        int safePage = Math.max(0, page);
        int safeSize = Math.max(1, size);
        Page<User> usersPage;
        try {
            usersPage = rbacUserService.searchUsersFiltered(search, status, role, branchId,
                    PageRequest.of(safePage, safeSize, Sort.by("id").descending()));
        } catch (Exception e) {
            log.error("Error searching filtered users: {}", e.getMessage(), e);
            usersPage = Page.empty(PageRequest.of(safePage, safeSize));
        }

        if (usersPage == null) {
            usersPage = Page.empty(PageRequest.of(safePage, safeSize));
        }

        List<Branch> branches;
        try {
            branches = rbacUserService.listActiveBranches();
        } catch (Exception e) {
            log.error("Error listing branches: {}", e.getMessage(), e);
            branches = Collections.emptyList();
        }

        long pending = 0;
        long active = 0;
        long inactive = 0;
        try {
            pending = rbacUserService.countPending();
            active = rbacUserService.countActive();
            inactive = rbacUserService.countInactive();
        } catch (Exception e) {
            log.error("Error counting users by status: {}", e.getMessage(), e);
        }

        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("page", usersPage);
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("totalElements", usersPage.getTotalElements());
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("status", status != null ? status : "ALL");
        model.addAttribute("selectedRole", role != null ? role : "");
        model.addAttribute("selectedBranchId", branchId);
        model.addAttribute("branches", branches);
        model.addAttribute("availableRoles", AVAILABLE_ROLES);
        model.addAttribute("pendingCount", pending);
        model.addAttribute("activeCount", active);
        model.addAttribute("inactiveCount", inactive);
        model.addAttribute("activeNav", "rbac-users");
        return "rbac/users/list";
    }

    @PostMapping("/{id}/approve")
    public String approveUser(@PathVariable Long id,
                              @RequestParam(name = "roles", required = false) List<String> roles,
                              RedirectAttributes ra) {
        if (roles != null && !roles.isEmpty()) {
            adminService.approve(id, roles);
        } else {
            adminService.approve(id);
        }
        ra.addFlashAttribute("successMessage", "Pengguna berhasil disetujui dan telah aktif.");
        return "redirect:/rbac/users/list?status=PENDING";
    }

    @PostMapping("/{id}/reject")
    public String rejectUser(@PathVariable Long id, RedirectAttributes ra) {
        adminService.reject(id);
        ra.addFlashAttribute("successMessage", "Permohonan registrasi pengguna ditolak.");
        return "redirect:/rbac/users/list?status=PENDING";
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

        if (userActivityService != null && user != null) {
            String uname = user.getUsername();
            model.addAttribute("activitySummary", userActivityService.getUserSummary(uname));
            model.addAttribute("recentActivities", userActivityService.getUserRecentActivities(uname, 15));
            model.addAttribute("userCustomers", userActivityService.getUserCustomers(uname, PageRequest.of(0, 10)));
            model.addAttribute("userAccounts", userActivityService.getUserAccounts(uname, PageRequest.of(0, 10)));
            model.addAttribute("userTransactions", userActivityService.getUserTransactions(uname, PageRequest.of(0, 10)));
        }

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

