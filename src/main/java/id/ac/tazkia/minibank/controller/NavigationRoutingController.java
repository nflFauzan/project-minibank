package id.ac.tazkia.minibank.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/product/create")
    public String productCreate() {
        return "redirect:/cs/product/new";
    }

    @PostMapping("/product/create")
    public String productCreatePost() {
        return "forward:/cs/product/new";
    }

    @GetMapping("/product/view/{id}")
    public String productViewWithId(@PathVariable String id) {
        return "redirect:/cs/product/" + id;
    }

    @GetMapping("/product/edit/{id}")
    public String productEditWithId(@PathVariable String id) {
        return "redirect:/cs/product/" + id + "/edit";
    }

    @PostMapping("/product/edit/{id}")
    public String productEditPostWithId(@PathVariable String id) {
        return "forward:/cs/product/" + id + "/edit";
    }

    @PostMapping("/product/delete/{id}")
    public String productDeleteWithId(@PathVariable String id) {
        return "forward:/cs/product/" + id + "/delete";
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
    public String accountOpenWithId(@PathVariable Long id) {
        return "redirect:/cs/account/open/" + id;
    }

    @GetMapping("/account/view/{id}")
    public String accountViewWithId(@PathVariable Long id) {
        return "redirect:/cs/account/" + id;
    }

    @GetMapping("/transaction/account/{id}")
    public String transactionAccountWithId(@PathVariable Long id) {
        return "redirect:/cs/account/" + id + "/transactions";
    }

    @GetMapping("/account/{id}/close")
    public String accountCloseGetWithId(@PathVariable Long id) {
        return "redirect:/cs/account/" + id + "/close";
    }

    @PostMapping("/account/{id}/close")
    public String accountClosePostWithId(@PathVariable Long id,
                                         @RequestParam(value = "reason", required = false) String reason,
                                         RedirectAttributes ra) {
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
                a.getAuthority().equals("ROLE_SUPERVISOR") || a.getAuthority().equals("ROLE_BRANCH_MANAGER") || a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/supervisor/nasabah";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/reports")
    public String reports() {
        return "redirect:/teller/transaction/list";
    }

    @GetMapping("/settings")
    public String settings() {
        return "redirect:/teller/settings";
    }
}
