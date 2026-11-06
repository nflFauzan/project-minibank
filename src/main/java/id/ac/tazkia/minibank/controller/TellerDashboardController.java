package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.TellerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller")
public class TellerDashboardController {

    private final TellerDashboardService dashboardService;

    // Sub-dashboard /teller/dashboard removed. All roles use unified /dashboard.
    
    @GetMapping("/transaction")
    public String transaction() {
        return "redirect:/teller/transaction/list";
    }

    // placeholder settings biar link sidebar ga 500
    @GetMapping("/settings")
    public String settings() {
        return "teller/settings";
    }
}
