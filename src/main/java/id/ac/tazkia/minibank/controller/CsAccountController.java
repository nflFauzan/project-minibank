package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.service.RekeningService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cs/account")
public class CsAccountController {

    private final RekeningService rekeningService;

    // /cs/account
    @GetMapping("")
    public String index(@RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "status", required = false, defaultValue = "ALL") String status,
                        Model model) {
        String cleanStatus = (status == null || status.isBlank() || status.equalsIgnoreCase("All Statuses")) ? "ALL" : status;
        model.addAttribute("accounts", rekeningService.listAccounts(search, cleanStatus));
        model.addAttribute("search", search);
        model.addAttribute("status", cleanStatus);
        return "cs/account";
    }

    // /cs/account/open -> list nasabah eligible (ACTIVE)
    @GetMapping("/open")
    public String openSelectCustomer(@RequestParam(value = "search", required = false) String search,
                                     @RequestParam(value = "q", required = false) String q,
                                     Model model) {
        String keyword = (search != null && !search.isBlank()) ? search : q;
        List<Nasabah> customers = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, keyword);
        model.addAttribute("customers", customers);
        model.addAttribute("search", keyword);
        model.addAttribute("q", keyword);
        return "cs/account/open";
    }

    // /cs/account/open/{id} -> form open account untuk nasabah id
    @GetMapping("/open/{id}")
    public String openForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Nasabah nasabah = rekeningService.getNasabahActiveById(id);
            model.addAttribute("nasabah", nasabah);
            model.addAttribute("products", rekeningService.listActiveProducts());
            RekeningService.OpenAccountForm form = new RekeningService.OpenAccountForm();
            form.setNamaRekening(nasabah.getNamaSesuaiIdentitas() + " - Personal Savings");
            form.setNominalSetoranAwal(java.math.BigDecimal.ZERO);
            form.setTujuanPembukaan("Personal Savings");
            model.addAttribute("form", form);
            return "cs/account/open_form";
        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/account/open";
        }
    }

    // submit open account
    @PostMapping("/open/{id}")
    public String doOpen(@PathVariable Long id,
                         @ModelAttribute("form") RekeningService.OpenAccountForm form,
                         RedirectAttributes ra) {
        try {
            rekeningService.openAccount(id, form.getProdukId(), form.getNamaRekening(),
                    form.getNominalSetoranAwal(), form.getTujuanPembukaan());
            ra.addFlashAttribute("success", "Rekening berhasil dibuka.");
            return "redirect:/cs/account";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/account/open/" + id;
        }
    }

    // view account
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("account", rekeningService.getAccountById(id));
            return "cs/account/view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/account";
        }
    }

    // transactions / mutasi rekening
    @GetMapping("/{id}/transactions")
    public String transactions(@PathVariable Long id,
                               @RequestParam(value = "startDate", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
                               @RequestParam(value = "endDate", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate,
                               @RequestParam(value = "type", required = false) String type,
                               Model model, RedirectAttributes ra) {
        try {
            Rekening account = rekeningService.getAccountById(id);
            List<Transaksi> list = rekeningService.getTransactions(account, startDate, endDate, type);

            java.math.BigDecimal totalDebit = java.math.BigDecimal.ZERO;
            java.math.BigDecimal totalCredit = java.math.BigDecimal.ZERO;

            for (Transaksi t : list) {
                if (t.getSaldoSesudah() != null && t.getSaldoSebelum() != null) {
                    if (t.getSaldoSesudah().compareTo(t.getSaldoSebelum()) < 0) {
                        totalDebit = totalDebit.add(t.getJumlah() != null ? t.getJumlah() : java.math.BigDecimal.ZERO);
                    } else {
                        totalCredit = totalCredit.add(t.getJumlah() != null ? t.getJumlah() : java.math.BigDecimal.ZERO);
                    }
                } else if (t.getTipe() == TipeTransaksi.WITHDRAWAL) {
                    totalDebit = totalDebit.add(t.getJumlah() != null ? t.getJumlah() : java.math.BigDecimal.ZERO);
                } else {
                    totalCredit = totalCredit.add(t.getJumlah() != null ? t.getJumlah() : java.math.BigDecimal.ZERO);
                }
            }

            model.addAttribute("account", account);
            model.addAttribute("transactions", list);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("type", (type != null && !type.isBlank()) ? type : "ALL");
            model.addAttribute("totalDebit", totalDebit);
            model.addAttribute("totalCredit", totalCredit);
            return "cs/account/transactions";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/account";
        }
    }

    // close account page
    @GetMapping("/{id}/close")
    public String closePage(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Rekening account = rekeningService.getAccountById(id);
            model.addAttribute("account", account);
            return "cs/account/close";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/account";
        }
    }

    // close account action
    @PostMapping("/{id}/close")
    public String close(@PathVariable Long id,
                        @RequestParam(value = "reason", required = false) String reason,
                        RedirectAttributes ra) {
        try {
            Rekening r = rekeningService.getAccountById(id);
            rekeningService.closeAccount(id, reason);
            ra.addFlashAttribute("success", "Rekening " + r.getNomorRekening() + " berhasil ditutup.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cs/account";
    }
}
