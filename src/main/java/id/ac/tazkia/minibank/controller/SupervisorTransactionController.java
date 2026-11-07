package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.service.SupervisorNasabahApprovalService;
import id.ac.tazkia.minibank.service.SupervisorRekeningApprovalService;
import id.ac.tazkia.minibank.service.SupervisorTransactionApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supervisor/transactions")
public class SupervisorTransactionController {

    private final SupervisorTransactionApprovalService approvalService;
    private final SupervisorRekeningApprovalService rekeningApprovalService;
    private final SupervisorNasabahApprovalService nasabahApprovalService;

    @GetMapping({"", "/"})
    public String list(Model model, Authentication auth) {
        List<Transaksi> pendingTransactions = approvalService.listPendingTransactions();

        model.addAttribute("active", "supervisor-transaction");
        model.addAttribute("activeTab", "transaction");
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");
        model.addAttribute("pendingTransactions", pendingTransactions);
        model.addAttribute("pendingTransactionCount", approvalService.pendingCount());
        model.addAttribute("pendingRekening", rekeningApprovalService.pendingCount());
        model.addAttribute("pendingNasabah", nasabahApprovalService.pendingCount());

        return "supervisor/transaction/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable UUID id, Model model, Authentication auth) {
        Transaksi tx = approvalService.getByIdOrThrow(id);

        model.addAttribute("active", "supervisor-transaction");
        model.addAttribute("activeTab", "transaction");
        model.addAttribute("employeeId", auth != null ? auth.getName() : "-");
        model.addAttribute("tx", tx);
        model.addAttribute("pendingTransactionCount", approvalService.pendingCount());
        model.addAttribute("pendingRekening", rekeningApprovalService.pendingCount());
        model.addAttribute("pendingNasabah", nasabahApprovalService.pendingCount());

        return "supervisor/transaction/detail";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable UUID id,
                          @RequestParam(required = false, defaultValue = "") String notes,
                          Authentication auth,
                          RedirectAttributes ra) {
        try {
            String supervisor = auth != null ? auth.getName() : "supervisor";
            approvalService.approve(id, supervisor, notes);
            ra.addFlashAttribute("success", "Transaksi berhasil disetujui.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal menyetujui transaksi: " + e.getMessage());
        }
        return "redirect:/supervisor/transactions";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable UUID id,
                         @RequestParam(required = false, defaultValue = "Ditolak supervisor") String reason,
                         @RequestParam(required = false, defaultValue = "") String notes,
                         Authentication auth,
                         RedirectAttributes ra) {
        try {
            String supervisor = auth != null ? auth.getName() : "supervisor";
            approvalService.reject(id, supervisor, reason, notes);
            ra.addFlashAttribute("success", "Transaksi berhasil ditolak.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal menolak transaksi: " + e.getMessage());
        }
        return "redirect:/supervisor/transactions";
    }
}

