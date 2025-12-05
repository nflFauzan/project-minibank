package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.DashboardSummaryDto;
import id.ac.tazkia.minibank.entity.Product;
import id.ac.tazkia.minibank.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CsDashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/cs/dashboard")
    public String dashboard(Model model){
        DashboardSummaryDto summary = dashboardService.getSummary();
        List<Product> products = dashboardService.getActiveProducts();

        model.addAttribute("summary", summary);
        model.addAttribute("products", products);
        model.addAttribute("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy | HH:mm")) );
        model.addAttribute("csName", "Tazkia");
        model.addAttribute("roleAndName", "| Costumer Service | ID00010003");

        return "cs/dashboard";
    }
}
