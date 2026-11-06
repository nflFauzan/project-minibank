package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.rbac.PermissionForm;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.service.RbacPermissionService;
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

import java.util.List;

@Controller
@RequestMapping("/rbac/permissions")
@RequiredArgsConstructor
public class RbacPermissionController {

    private final RbacPermissionService rbacPermissionService;

    private static final List<String> CATEGORIES = List.of(
            "ACCOUNT", "APPROVAL", "AUDIT", "BRANCH", "CUSTOMER",
            "PRODUCT", "REPORT", "TRANSACTION", "USER"
    );

    @GetMapping("/list")
    public String listPermissions(@RequestParam(name = "category", required = false) String category,
                                  @RequestParam(name = "search", required = false) String search,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  Model model) {
        Page<Permission> permPage = rbacPermissionService.searchPermissions(category, search,
                PageRequest.of(page, size, Sort.by("category").ascending().and(Sort.by("permissionCode").ascending())));

        model.addAttribute("permissions", permPage.getContent());
        model.addAttribute("page", permPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", permPage.getTotalPages());
        model.addAttribute("totalElements", permPage.getTotalElements());
        model.addAttribute("selectedCategory", category != null ? category : "");
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("activeNav", "rbac-permissions");
        return "rbac/permissions/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new PermissionForm());
        }
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("activeNav", "rbac-permissions");
        return "rbac/permissions/create";
    }

    @PostMapping("/create")
    public String createPermission(@Valid @ModelAttribute("form") PermissionForm form,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("activeNav", "rbac-permissions");
            return "rbac/permissions/create";
        }

        try {
            rbacPermissionService.createPermission(form);
            ra.addFlashAttribute("successMessage", "Permission " + form.getPermissionCode() + " berhasil dibuat.");
            return "redirect:/rbac/permissions/list";
        } catch (IllegalArgumentException ex) {
            result.reject("duplicate", ex.getMessage());
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("activeNav", "rbac-permissions");
            return "rbac/permissions/create";
        }
    }

    @GetMapping("/view/{id}")
    public String viewPermission(@PathVariable Long id, Model model) {
        Permission perm = rbacPermissionService.findById(id);
        model.addAttribute("permission", perm);
        model.addAttribute("activeNav", "rbac-permissions");
        return "rbac/permissions/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Permission perm = rbacPermissionService.findById(id);

        PermissionForm form = new PermissionForm();
        form.setId(perm.getId());
        form.setPermissionCode(perm.getPermissionCode());
        form.setPermissionName(perm.getPermissionName());
        form.setCategory(perm.getCategory());
        form.setDescription(perm.getDescription());

        model.addAttribute("form", form);
        model.addAttribute("permission", perm);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("activeNav", "rbac-permissions");
        return "rbac/permissions/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePermission(@PathVariable Long id,
                                   @Valid @ModelAttribute("form") PermissionForm form,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes ra) {
        form.setId(id);
        if (result.hasErrors()) {
            Permission perm = rbacPermissionService.findById(id);
            model.addAttribute("permission", perm);
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("activeNav", "rbac-permissions");
            return "rbac/permissions/edit";
        }

        try {
            rbacPermissionService.updatePermission(form);
            ra.addFlashAttribute("successMessage", "Data permission berhasil diperbarui.");
            return "redirect:/rbac/permissions/view/" + id;
        } catch (IllegalArgumentException ex) {
            result.reject("error", ex.getMessage());
            Permission perm = rbacPermissionService.findById(id);
            model.addAttribute("permission", perm);
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("activeNav", "rbac-permissions");
            return "rbac/permissions/edit";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePermission(@PathVariable Long id, RedirectAttributes ra) {
        try {
            rbacPermissionService.deletePermission(id);
            ra.addFlashAttribute("successMessage", "Permission berhasil dihapus.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Gagal menghapus permission: " + ex.getMessage());
        }
        return "redirect:/rbac/permissions/list";
    }
}

