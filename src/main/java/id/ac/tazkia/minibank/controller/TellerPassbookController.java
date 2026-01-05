package id.ac.tazkia.minibank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teller/passbook")
public class TellerPassbookController {

    @GetMapping("/select-account")
    public String selectAccount() {
        return "teller/passbook/select-account";
    }
}
