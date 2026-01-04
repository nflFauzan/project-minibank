package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.TellerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller")
public class TellerDashboardController {

    private final TellerDashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("active","dashboard");
        model.addAttribute("totalNasabah", dashboardService.totalNasabahAktif());
        model.addAttribute("totalRekeningAktif", dashboardService.totalRekeningAktif());
        model.addAttribute("totalDeposit", dashboardService.totalDepositAwal());
        model.addAttribute("totalTransaksi", dashboardService.totalTransaksi());
        model.addAttribute("products", dashboardService.produkAktif());
        return "teller/dashboard";
    }

    
@GetMapping("/transaction")
public String transaction(Model model) {
    model.addAttribute("active", "transaction");
    return "teller/transaction/list";
}

    // placeholder settings biar link sidebar ga 500
    @GetMapping("/settings")
    public String settings() {
        return "teller/settings";
    }
}
