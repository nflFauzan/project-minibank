package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.rbac.RoleForm;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.service.RbacRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rbac/roles")
@RequiredArgsConstructor
public class RbacRoleController {

    private final RbacRoleService rbacRoleService;

    @GetMapping("/list")
    public String listRoles(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Role> roles = rbacRoleService.listRoles(search);
        model.addAttribute("roles", roles);
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("activeNav", "rbac-roles");
        return "rbac/roles/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new RoleForm());
        }
        model.addAttribute("activeNav", "rbac-roles");
        return "rbac/roles/create";
    }

    @PostMapping("/create")
    public String createRole(@Valid @ModelAttribute("form") RoleForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("activeNav", "rbac-roles");
            return "rbac/roles/create";
        }

        try {
            rbacRoleService.createRole(form);
            ra.addFlashAttribute("successMessage", "Role " + form.getRoleCode() + " berhasil dibuat.");
            return "redirect:/rbac/roles/list";
        } catch (IllegalArgumentException ex) {
            result.reject("duplicate", ex.getMessage());
            model.addAttribute("activeNav", "rbac-roles");
            return "rbac/roles/create";
        }
    }

    @GetMapping("/view/{id}")
    public String viewRole(@PathVariable Long id, Model model) {
        Role role = rbacRoleService.findById(id);
        model.addAttribute("role", role);
        model.addAttribute("activeNav", "rbac-roles");
        return "rbac/roles/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Role role = rbacRoleService.findById(id);

        RoleForm form = new RoleForm();
        form.setId(role.getId());
        form.setRoleCode(role.getRoleCode() != null ? role.getRoleCode() : role.getName().replace("ROLE_", ""));
        form.setRoleName(role.getDisplayName() != null ? role.getDisplayName() : role.getName());
        form.setDescription(role.getDescription());

        model.addAttribute("form", form);
        model.addAttribute("role", role);
        model.addAttribute("activeNav", "rbac-roles");
        return "rbac/roles/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateRole(@PathVariable Long id,
                             @Valid @ModelAttribute("form") RoleForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes ra) {
        form.setId(id);
        if (result.hasErrors()) {
            Role role = rbacRoleService.findById(id);
            model.addAttribute("role", role);
            model.addAttribute("activeNav", "rbac-roles");
            return "rbac/roles/edit";
        }

        try {
            rbacRoleService.updateRole(form);
            ra.addFlashAttribute("successMessage", "Data role berhasil diperbarui.");
            return "redirect:/rbac/roles/view/" + id;
        } catch (IllegalArgumentException ex) {
            result.reject("error", ex.getMessage());
            Role role = rbacRoleService.findById(id);
            model.addAttribute("role", role);
            model.addAttribute("activeNav", "rbac-roles");
            return "rbac/roles/edit";
        }
    }

    @GetMapping("/{id}/permissions")
    public String showRolePermissions(@PathVariable Long id, Model model) {
        Role role = rbacRoleService.findById(id);
        List<Permission> allPermissions = rbacRoleService.listAllPermissions();

        model.addAttribute("role", role);
        model.addAttribute("allPermissions", allPermissions);
        model.addAttribute("activeNav", "rbac-roles");
        return "rbac/roles/permissions";
    }

    @PostMapping("/{id}/permissions/assign")
    public String assignPermission(@PathVariable Long id,
                                   @RequestParam("permissionId") Long permissionId,
                                   RedirectAttributes ra) {
        try {
            rbacRoleService.assignPermission(id, permissionId);
            ra.addFlashAttribute("successMessage", "Permission berhasil ditugaskan ke role.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/rbac/roles/" + id + "/permissions";
    }

    @PostMapping("/{id}/permissions/remove")
    public String removePermission(@PathVariable Long id,
                                   @RequestParam(value = "permissionId", required = false) Long permissionId,
                                   @RequestParam(value = "rolePermissionId", required = false) Long rolePermissionId,
                                   RedirectAttributes ra) {
        Long targetPermId = permissionId != null ? permissionId : rolePermissionId;
        if (targetPermId != null) {
            rbacRoleService.removePermission(id, targetPermId);
            ra.addFlashAttribute("successMessage", "Permission berhasil dilepas dari role.");
        }
        return "redirect:/rbac/roles/" + id + "/permissions";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateRole(@PathVariable Long id, RedirectAttributes ra) {
        rbacRoleService.toggleActive(id, false);
        ra.addFlashAttribute("successMessage", "Role berhasil dinonaktifkan.");
        return "redirect:/rbac/roles/list";
    }

    @PostMapping("/{id}/activate")
    public String activateRole(@PathVariable Long id, RedirectAttributes ra) {
        rbacRoleService.toggleActive(id, true);
        ra.addFlashAttribute("successMessage", "Role berhasil diaktifkan.");
        return "redirect:/rbac/roles/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, RedirectAttributes ra) {
        try {
            rbacRoleService.deleteRole(id);
            ra.addFlashAttribute("successMessage", "Role berhasil dihapus.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Gagal menghapus role: " + ex.getMessage());
        }
        return "redirect:/rbac/roles/list";
    }
}

