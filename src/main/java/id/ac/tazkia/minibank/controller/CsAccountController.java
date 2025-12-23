package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.Nasabah;
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
                        @RequestParam(value = "status", required = false, defaultValue = "ACTIVE") String status,
                        Model model) {
        model.addAttribute("accounts", rekeningService.listAccounts(search, status));
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        return "cs/account";
    }

    // /cs/account/open -> list nasabah eligible (ACTIVE)
@GetMapping("/open")
public String openSelectCustomer(@RequestParam(required = false) String q, Model model) {
    List<Nasabah> customers = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, q);
    model.addAttribute("customers", customers);
    model.addAttribute("q", q);
    return "cs/account/open";
}



    // /cs/account/open/{id} -> form open account untuk nasabah id
    @GetMapping("/open/{id}")
    public String openForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Nasabah nasabah = rekeningService.getNasabahActiveById(id);
            model.addAttribute("nasabah", nasabah);
            model.addAttribute("products", rekeningService.listActiveProducts());
            model.addAttribute("form", new RekeningService.OpenAccountForm());
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
            rekeningService.openAccount(id, form.getProdukId(), form.getNominalSetoranAwal(), form.getTujuanPembukaan());
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
            model.addAttribute("acc", rekeningService.getAccountById(id));
            return "cs/account/view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/account";
        }
    }

    // close account
    @PostMapping("/{id}/close")
    public String close(@PathVariable Long id, RedirectAttributes ra) {
        try {
            rekeningService.closeAccount(id);
            ra.addFlashAttribute("success", "Rekening berhasil ditutup.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cs/account";
    }
}
