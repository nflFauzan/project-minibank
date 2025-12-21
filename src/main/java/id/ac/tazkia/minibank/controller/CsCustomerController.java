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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", nasabahService.listAllCustomers());
        return "cs/customers/index";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Nasabah n = nasabahService.getById(id);
        model.addAttribute("nasabah", n);
        return "cs/customers/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Nasabah n = nasabahService.getById(id);
        model.addAttribute("nasabah", n);
        return "cs/customers/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Nasabah form,
                         RedirectAttributes ra) {
        try {
            nasabahService.updateNasabah(id, form);
            ra.addFlashAttribute("success", "Nasabah berhasil diperbarui.");
        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("error", "Nasabah tidak ditemukan.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Terjadi kesalahan saat memperbarui data.");
        }
        return "redirect:/cs/customers";
    }
}
