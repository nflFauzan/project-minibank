package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.report.ReportSummaryDto;
import id.ac.tazkia.minibank.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports")
    public String showReport(@RequestParam(name = "period", required = false, defaultValue = "DAILY") String period,
                             @RequestParam(name = "date", required = false) String date,
                             @RequestParam(name = "month", required = false) Integer month,
                             @RequestParam(name = "year", required = false) Integer year,
                             Model model,
                             Authentication auth) {

        ReportSummaryDto report = reportService.generateReport(period, date, month, year);

        model.addAttribute("active", "reports");
        model.addAttribute("report", report);
        model.addAttribute("periodType", report.getPeriodType());
        model.addAttribute("currentYear", LocalDate.now().getYear());
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");

        return "reports/index";
    }
}

