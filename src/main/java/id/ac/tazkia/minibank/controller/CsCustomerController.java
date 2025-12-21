package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.NasabahService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
public class CsCustomerController {

    private final NasabahService nasabahService;
    private final UserRepository userRepository;

    // LIST customers (default = ALL)
    @GetMapping("/customers")
    public String customers(@RequestParam(name = "status", required = false, defaultValue = "ALL") String status,
                            Model model) {

        if ("ALL".equalsIgnoreCase(status)) {
            model.addAttribute("list", nasabahService.listAllCustomers());
        } else {
            // INACTIVE / ACTIVE / REJECTED
            NasabahStatus st = NasabahStatus.valueOf(status.toUpperCase());
            model.addAttribute("list", nasabahService.listByStatus(st));
        }

        model.addAttribute("status", status.toUpperCase());
        return "cs/customers";
    }

    // FORM create nasabah
    @GetMapping("/customers/new")
    public String createForm(Model model) {
        if (!model.containsAttribute("nasabah")) {
            model.addAttribute("nasabah", new Nasabah());
        }
        return "cs/pendaftaran_nasabah";
    }

    // SUBMIT create nasabah
    @PostMapping("/customers")
public String create(@ModelAttribute("nasabah") Nasabah form,
                     Authentication auth,
                     RedirectAttributes ra) {

    String createdByName = (auth != null ? auth.getName() : "SYSTEM");
    nasabahService.createNasabah(form, createdByName);

    ra.addFlashAttribute("successMessage", "Nasabah berhasil dibuat (INACTIVE).");
    return "redirect:/cs/customers";
}

}
