package id.ac.tazkia.minibank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupervisorController {
    @GetMapping("/supervisor/dashboard")
    public String supervisorDashboard() { return "supervisor/dashboard"; }
}
