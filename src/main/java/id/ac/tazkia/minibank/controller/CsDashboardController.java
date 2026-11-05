package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.DashboardSummaryDto;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
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
    // Sub-dashboard /cs/dashboard removed. All roles use unified /dashboard.
}
