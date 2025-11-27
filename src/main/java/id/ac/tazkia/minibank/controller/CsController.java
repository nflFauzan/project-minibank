package id.ac.tazkia.minibank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CsController {
    @GetMapping("/cs/dashboard")
    public String csDashboard() { return "cs/dashboard"; }
}
