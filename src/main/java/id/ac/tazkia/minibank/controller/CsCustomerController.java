package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.service.NasabahService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cs/customers")
public class CsCustomerController {

    private final NasabahService nasabahService;

    // LIST: GET /cs/customers
    @GetMapping
    public String customers(Model model) {
        model.addAttribute("customers", nasabahService.listAllCustomers());
        return "cs/customers";
    }

    // NEW FORM: GET /cs/customers/new -> pakai template yang sudah ada
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("nasabah", new Nasabah());
        return "cs/pendaftaran_nasabah";
    }

    // CREATE: POST /cs/customers atau /cs/customers/new
    @PostMapping({"", "/new"})
    public String create(@ModelAttribute("nasabah") Nasabah form,
                         RedirectAttributes ra) {
        try {
            nasabahService.createNasabah(form);
            ra.addFlashAttribute("success", "Nasabah berhasil didaftarkan.");
            return "redirect:/cs/customers";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal mendaftarkan nasabah: " + e.getMessage());
            return "redirect:/cs/customers/new";
        }
    }

    // VIEW: GET /cs/customers/{id}
    @GetMapping("/{id:\\d+}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Nasabah n = nasabahService.getById(id);
            model.addAttribute("nasabah", n);
            return "cs/customers/view";
        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("error", "Nasabah tidak ditemukan.");
            return "redirect:/cs/customers";
        }
    }

    // EDIT PAGE: GET /cs/customers/{id}/edit
    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Nasabah n = nasabahService.getById(id);
            model.addAttribute("nasabah", n);
            return "cs/customers/edit";
        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("error", "Nasabah tidak ditemukan.");
            return "redirect:/cs/customers";
        }
    }

    // UPDATE: POST /cs/customers/{id}
    @PostMapping("/{id:\\d+}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("nasabah") Nasabah form,
                         RedirectAttributes ra) {
        try {
            nasabahService.updateNasabah(id, form);
            ra.addFlashAttribute("success", "Nasabah berhasil diperbarui.");
        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("error", "Nasabah tidak ditemukan.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Terjadi kesalahan saat memperbarui data: " + e.getMessage());
        }
        return "redirect:/cs/customers";
    }
}
