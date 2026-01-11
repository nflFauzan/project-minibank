package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.service.DashboardService;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import id.ac.tazkia.minibank.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

@Autowired
    private DashboardService dashboardService;
    private final NasabahRepository nasabahRepository;

    public SupervisorController(NasabahRepository nasabahRepository) {
        this.nasabahRepository = nasabahRepository;
    }

    @GetMapping("/dashboard")
    public String supervisorDashboard(Model model) {

        long pendingCount = nasabahRepository.countByStatus(NasabahStatus.INACTIVE);
        model.addAttribute("pendingCount", pendingCount);
  model.addAttribute("products", dashboardService.getActiveProdukTabungan());
        return "supervisor/dashboard";
    }
}
