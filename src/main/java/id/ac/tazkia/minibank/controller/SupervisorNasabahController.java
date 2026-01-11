package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.SupervisorNasabahApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supervisor/nasabah")
public class SupervisorNasabahController {

    private final SupervisorNasabahApprovalService approvalService;
    private final UserRepository userRepository;

    public enum Filter {
        ALL, PENDING, APPROVED, REJECTED;

        public static Filter of(String v) {
            try {
                return (v == null) ? PENDING : Filter.valueOf(v.toUpperCase());
            } catch (Exception e) {
                return PENDING;
            }
        }
    }

    // ✅ LIST (PENDING + HISTORY jadi satu) + FILTER
    @GetMapping({"", "/", "/pending", "/history"})
    public String list(@RequestParam(name = "filter", required = false) String filterParam,
                       Model model,
                       Authentication auth) {

        Filter filter = Filter.of(filterParam);

        List<Nasabah> data = switch (filter) {
            case PENDING -> approvalService.listByStatuses(List.of(NasabahStatus.INACTIVE));
            case APPROVED -> approvalService.listByStatuses(List.of(NasabahStatus.ACTIVE));
            case REJECTED -> approvalService.listByStatuses(List.of(NasabahStatus.REJECTED));
            case ALL -> approvalService.listAllCombined(); // pending dulu, baru history
        };

        model.addAttribute("active", "nasabah");
        model.addAttribute("roleLabel", "Supervisor");
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");

        model.addAttribute("filter", filter.name());
        model.addAttribute("pendingCount", approvalService.pendingCount());
        model.addAttribute("list", data);

        return "supervisor/nasabah/list";
    }

    // ✅ DETAIL PAGE (mirip cs/customers/view)
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         Authentication auth) {

        Nasabah n = approvalService.getByIdOrThrow(id);

        model.addAttribute("active", "nasabah");
        model.addAttribute("roleLabel", "Supervisor");
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");

        model.addAttribute("nasabah", n);
        return "supervisor/nasabah/detail";
    }

    // ✅ APPROVE
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id,
                          @RequestParam(name = "notes", required = false) String notes,
                          Authentication auth,
                          RedirectAttributes ra) {

        String username = (auth == null) ? "-" : auth.getName();
        String supervisorName = userRepository.findByUsername(username)
                .map(User::getFullName)
                .filter(StringUtils::hasText)
                .orElse(username);

        approvalService.approve(id, supervisorName, notes);

        ra.addFlashAttribute("success", "Nasabah berhasil di-approve.");
        return "redirect:/supervisor/nasabah";
    }

    // ✅ REJECT (reason wajib)
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam("reason") String reason,
                         @RequestParam(name = "notes", required = false) String notes,
                         Authentication auth,
                         RedirectAttributes ra) {

        if (!StringUtils.hasText(reason)) {
            ra.addFlashAttribute("error", "Alasan reject wajib diisi.");
            return "redirect:/supervisor/nasabah/" + id;
        }

        String username = (auth == null) ? "-" : auth.getName();
        String supervisorName = userRepository.findByUsername(username)
                .map(User::getFullName)
                .filter(StringUtils::hasText)
                .orElse(username);

        approvalService.reject(id, supervisorName, reason, notes);

        ra.addFlashAttribute("success", "Nasabah berhasil di-reject.");
        return "redirect:/supervisor/nasabah";
    }
}
