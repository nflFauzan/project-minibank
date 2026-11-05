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
    // Sub-dashboard /supervisor/dashboard removed. All roles use unified /dashboard.
}
