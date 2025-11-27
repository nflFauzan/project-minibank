package id.ac.tazkia.minibank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TellerController {
    @GetMapping("/teller/dashboard")
    public String tellerDashboard() { return "teller/dashboard"; }
}
