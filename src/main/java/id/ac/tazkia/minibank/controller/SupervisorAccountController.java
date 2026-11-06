package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.RekeningStatus;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.SupervisorRekeningApprovalService;
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
@RequestMapping("/supervisor/account")
public class SupervisorAccountController {

    private final SupervisorRekeningApprovalService approvalService;
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

    // LIST (PENDING + HISTORY) + FILTER
    @GetMapping({"", "/", "/pending", "/history"})
    public String list(@RequestParam(name = "filter", required = false) String filterParam,
                       Model model,
                       Authentication auth) {

        Filter filter = Filter.of(filterParam);

        List<Rekening> data = switch (filter) {
            case PENDING -> approvalService.listByStatuses(List.of(RekeningStatus.PENDING));
            case APPROVED -> approvalService.listByStatuses(List.of(RekeningStatus.ACTIVE));
            case REJECTED -> approvalService.listByStatuses(List.of(RekeningStatus.REJECTED));
            case ALL -> approvalService.listAllCombined();
        };

        model.addAttribute("active", "supervisor-account");
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");

        model.addAttribute("filter", filter.name());
        model.addAttribute("pendingCount", approvalService.pendingCount());
        model.addAttribute("list", data);

        return "supervisor/account/list";
    }

    // DETAIL PAGE
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         Authentication auth) {

        Rekening r = approvalService.getByIdOrThrow(id);

        model.addAttribute("active", "supervisor-account");
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");
        model.addAttribute("account", r);
        model.addAttribute("rekening", r);
        model.addAttribute("nasabah", r.getNasabah());

        return "supervisor/account/detail";
    }

    // APPROVE
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

        ra.addFlashAttribute("success", "Rekening berhasil di-approve.");
        return "redirect:/supervisor/account";
    }

    // REJECT
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam("reason") String reason,
                         @RequestParam(name = "notes", required = false) String notes,
                         Authentication auth,
                         RedirectAttributes ra) {

        if (!StringUtils.hasText(reason)) {
            ra.addFlashAttribute("error", "Alasan reject wajib diisi.");
            return "redirect:/supervisor/account/" + id;
        }

        String username = (auth == null) ? "-" : auth.getName();
        String supervisorName = userRepository.findByUsername(username)
                .map(User::getFullName)
                .filter(StringUtils::hasText)
                .orElse(username);

        approvalService.reject(id, supervisorName, reason, notes);

        ra.addFlashAttribute("success", "Rekening berhasil di-reject.");
        return "redirect:/supervisor/account";
    }
}

