package id.ac.tazkia.minibank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller/transaction")
public class TellerTransactionController {

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("active", "transaction");
        return "teller/transaction/list";
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("active", "deposit");
        return "teller/transaction/deposit";
    }

    @GetMapping("/withdrawal")
    public String withdrawal(Model model) {
        model.addAttribute("active", "withdrawal");
        return "teller/transaction/withdrawal";
    }

    @GetMapping("/transfer")
    public String transfer(Model model) {
        model.addAttribute("active", "transfer");
        return "teller/transaction/transfer";
    }
}
