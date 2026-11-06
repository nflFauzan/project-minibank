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

    @GetMapping("/account/open/{id}")
    public String accountOpenWithId(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return "redirect:/cs/account/open/" + id;
    }

    @GetMapping("/account/view/{id}")
    public String accountViewWithId(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return "redirect:/cs/account/" + id;
    }

    @GetMapping("/transaction/account/{id}")
    public String transactionAccountWithId(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return "redirect:/cs/account/" + id + "/transactions";
    }

    @GetMapping("/account/{id}/close")
    public String accountCloseGetWithId(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return "redirect:/cs/account/" + id + "/close";
    }

    @PostMapping("/account/{id}/close")
    public String accountClosePostWithId(@org.springframework.web.bind.annotation.PathVariable Long id,
                                         @org.springframework.web.bind.annotation.RequestParam(value = "reason", required = false) String reason,
                                         org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        return "forward:/cs/account/" + id + "/close";
    }

    @GetMapping("/transaction/list")
    public String transactionList() {
        return "redirect:/teller/transaction/list";
    }

    @GetMapping({"/teller/deposit", "/transaction/cash-deposit"})
    public String transactionDeposit() {
        return "redirect:/teller/transaction/deposit";
    }

    @GetMapping("/teller/withdrawal")
    public String transactionWithdrawal() {
        return "redirect:/teller/transaction/withdrawal";
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
        return "redirect:/dashboard";
    }

    @GetMapping("/settings")
    public String settings() {
        return "redirect:/teller/settings";
    }
}

