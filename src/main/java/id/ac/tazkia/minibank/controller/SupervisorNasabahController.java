package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.NasabahApprovalService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/supervisor/nasabah")
public class SupervisorNasabahController {

    private final NasabahRepository nasabahRepository;
    private final NasabahApprovalService nasabahApprovalService;
    private final UserRepository userRepository;

    public SupervisorNasabahController(NasabahRepository nasabahRepository,
                                       NasabahApprovalService nasabahApprovalService,
                                       UserRepository userRepository) {
        this.nasabahRepository = nasabahRepository;
        this.nasabahApprovalService = nasabahApprovalService;
        this.userRepository = userRepository;
    }

    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("list",
                nasabahRepository.findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE)
        );
        return "supervisor/nasabah-pending";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history",
    nasabahRepository.findByStatusInOrderByApprovedAtDesc(List.of(NasabahStatus.ACTIVE, NasabahStatus.REJECTED))
        );
        return "supervisor/nasabah-history";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id,
                          Authentication auth,
                          RedirectAttributes ra) {

        String username = auth.getName();
        String supervisorName = userRepository.findByUsername(username)
                .map(User::getFullName)
                .orElse(username);

        nasabahApprovalService.approve(id, supervisorName, "");
        ra.addFlashAttribute("successMessage", "Nasabah berhasil di-approve.");
        return "redirect:/supervisor/nasabah/pending";
    }

@PostMapping("/{id}/reject")
public String reject(@PathVariable Long id,
                     @RequestParam("reason") String reason,
                     Authentication auth,
                     RedirectAttributes ra) {

    String username = auth.getName();
    String supervisorName = userRepository.findByUsername(username)
            .map(User::getFullName)
            .orElse(username);

    nasabahApprovalService.reject(id, supervisorName, reason);
    ra.addFlashAttribute("successMessage", "Nasabah berhasil di-reject.");
    return "redirect:/supervisor/nasabah/pending";
}
}
