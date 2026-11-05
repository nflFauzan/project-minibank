package id.ac.tazkia.minibank.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Maps standard Minibank route paths (matching minibank.tazkia.ac.id)
 * to internal operational module controllers.
 */
@Controller
public class NavigationRoutingController {

    @GetMapping("/product/list")
    public String productList() {
        return "redirect:/cs/product";
    }

    @GetMapping("/customer/list")
    public String customerList() {
        return "redirect:/cs/customers";
    }

    @GetMapping("/customer/create")
    public String customerCreate() {
        return "redirect:/cs/customers/new";
    }

    @GetMapping("/account/list")
    public String accountList() {
        return "redirect:/cs/account";
    }

    @GetMapping("/account/open")
    public String accountOpen() {
        return "redirect:/cs/account/open";
    }

    @GetMapping("/transaction/list")
    public String transactionList() {
        return "redirect:/teller/transaction/list";
    }

    @GetMapping("/transaction/cash-deposit")
    public String transactionDeposit() {
        return "redirect:/teller/transaction/deposit";
    }

    @GetMapping("/passbook/select-account")
    public String passbookSelectAccount() {
        return "redirect:/teller/passbook/select-account";
    }

    @GetMapping("/approval/queue")
    public String approvalQueue(Authentication auth) {
        if (auth != null && auth.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_SUPERVISOR") || a.getAuthority().equals("ROLE_BRANCH_MANAGER"))) {
            return "redirect:/supervisor/nasabah";
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping({"/rbac/users/list", "/rbac/roles/list", "/rbac/permissions/list"})
    public String rbacUsersList() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/rbac/users/create")
    public String rbacUsersCreate() {
        return "redirect:/admin/users/create";
    }
}
