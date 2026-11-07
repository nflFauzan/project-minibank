package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.AdminUserService;
import id.ac.tazkia.minibank.service.RbacUserService;
import id.ac.tazkia.minibank.service.TellerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller")
public class TellerDashboardController {

    private final TellerDashboardService dashboardService;
    private final AdminUserService adminService;
    private final RbacUserService rbacUserService;

    // Sub-dashboard /teller/dashboard removed. All roles use unified /dashboard.
    
    @GetMapping("/transaction")
    public String transaction(Model model) {
        model.addAttribute("active", "transaction");
        model.addAttribute("page", Page.empty());
        return "teller/transaction/list";
    }

    // Pengaturan Sistem & Pengelolaan Hak Akses (RBAC)
    @GetMapping("/settings")
    public String settings(Authentication auth, Model model) {
        boolean isAdmin = false;
        boolean isBranchManager = false;
        boolean isCs = false;
        boolean isTeller = false;
        String username = "Staff";
        String roleLabel = "Staff";

        if (auth != null && auth.isAuthenticated()) {
            username = auth.getName();
            isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            isBranchManager = isAdmin || auth.getAuthorities().stream().anyMatch(a ->
                    a.getAuthority().equals("ROLE_BRANCH_MANAGER") || a.getAuthority().equals("ROLE_SUPERVISOR"));
            isCs = isAdmin || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS"));
            isTeller = isAdmin || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER"));

            if (isAdmin) {
                roleLabel = "Administrator";
            } else if (isBranchManager) {
                roleLabel = "Branch Manager";
            } else if (isCs) {
                roleLabel = "Customer Service";
            } else if (isTeller) {
                roleLabel = "Teller";
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("roleLabel", roleLabel);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isBranchManager", isBranchManager);
        model.addAttribute("isCs", isCs);
        model.addAttribute("isTeller", isTeller);
        model.addAttribute("active", "settings");

        // Load RBAC Management data for Admin
        if (isAdmin) {
            try {
                var allUsers = adminService.listAllUsers();
                var pendingUsers = adminService.listPending();
                model.addAttribute("allUsers", allUsers);
                model.addAttribute("pendingUsers", pendingUsers);
                model.addAttribute("pendingCount", pendingUsers != null ? pendingUsers.size() : 0);
                model.addAttribute("roles", rbacUserService.listActiveRoles());
                model.addAttribute("branches", rbacUserService.listActiveBranches());
            } catch (Exception ignored) {}
        }

        return "teller/settings";
    }
}
