package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.audit.UserActivityFilterDto;
import id.ac.tazkia.minibank.entity.ActivityType;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.entity.UserActivityLog;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.UserActivityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/audit/activities")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'BRANCH_MANAGER') or hasAuthority('AUDIT_VIEW')")
public class UserActivityController {

    private final UserActivityService userActivityService;
    private final UserRepository userRepository;

    public static final List<String> MODULES = List.of(
            "CUSTOMER",
            "ACCOUNT",
            "TELLER",
            "APPROVAL",
            "AUTH",
            "USER",
            "SYSTEM"
    );

    @GetMapping
    public String index(@ModelAttribute("filter") UserActivityFilterDto filter,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "20") int size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<UserActivityLog> activityPage = userActivityService.searchActivities(filter, pageable);
        if (activityPage == null) {
            activityPage = Page.empty(pageable);
        }

        List<User> userList = userRepository.findAll(Sort.by("fullName").ascending());

        model.addAttribute("activities", activityPage.getContent());
        model.addAttribute("page", activityPage);
        model.addAttribute("filter", filter);
        model.addAttribute("usersList", userList);
        model.addAttribute("modulesList", MODULES);
        model.addAttribute("activityTypes", ActivityType.values());
        model.addAttribute("activeNav", "audit-activities");
        model.addAttribute("active", "audit-activities");

        return "audit/activities";
    }

    @GetMapping("/export")
    public void exportCsv(@ModelAttribute("filter") UserActivityFilterDto filter,
                          HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"audit_activities.csv\"");

        // Ambil hingga 1000 rekaman untuk export CSV
        Pageable pageable = PageRequest.of(0, 1000, Sort.by("timestamp").descending());
        Page<UserActivityLog> activityPage = userActivityService.searchActivities(filter, pageable);
        if (activityPage == null) {
            activityPage = Page.empty(pageable);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,Timestamp,Username,Full Name,Role,Module,Activity Type,Description,Reference Number,Amount,Status");
            for (UserActivityLog log : activityPage.getContent()) {
                writer.printf("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\"%n",
                        log.getId(),
                        log.getTimestamp() != null ? log.getTimestamp().format(dtf) : "",
                        escapeCsv(log.getUsername()),
                        escapeCsv(log.getUserFullName()),
                        escapeCsv(log.getUserRole()),
                        escapeCsv(log.getModule()),
                        log.getActivityType() != null ? log.getActivityType().name() : "",
                        escapeCsv(log.getDescription()),
                        escapeCsv(log.getReferenceNumber()),
                        log.getAmount() != null ? log.getAmount().toPlainString() : "",
                        escapeCsv(log.getStatus())
                );
            }
        }
    }

    private String escapeCsv(String input) {
        if (input == null) return "";
        return input.replace("\"", "\"\"");
    }
}

