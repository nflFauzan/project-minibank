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

import java.util.List;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
public class CsCustomerController {

    private final NasabahService nasabahService;
    private final UserRepository userRepository;

    // supaya /cs/dashboard jadi "Customer page"
    @GetMapping("/dashboard")
    public String dashboardRedirect() {
        return "redirect:/cs/customers";
    }

    @GetMapping("/customers")
    public String customers(@RequestParam(name = "status", required = false) String status,
                            @RequestParam(name = "new", required = false, defaultValue = "false") boolean showForm,
                            Model model) {

        List<Nasabah> list;
        NasabahStatus selected = null;

        if (status != null && !status.isBlank() && !"ALL".equalsIgnoreCase(status)) {
            selected = NasabahStatus.valueOf(status.toUpperCase());
            list = nasabahService.listByStatus(selected);
        } else {
            list = nasabahService.listAllCustomers();
        }

        model.addAttribute("list", list);
        model.addAttribute("selectedStatus", selected == null ? "ALL" : selected.name());
        model.addAttribute("showForm", showForm);

        // form backing
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new Nasabah());
        }

        return "cs/customer";
    }

    @PostMapping("/customers")
    public String createCustomer(@ModelAttribute("form") Nasabah form,
                                 Authentication auth,
                                 RedirectAttributes ra) {

        String username = auth.getName();
        String createdByName = userRepository.findByUsername(username)
                .map(User::getFullName)
                .orElse(username);

        // minimal guard
        if (form.getNik() == null || form.getNik().isBlank() ||
            form.getNamaLengkap() == null || form.getNamaLengkap().isBlank()) {
            ra.addFlashAttribute("errorMessage", "NIK dan Nama wajib diisi.");
            ra.addFlashAttribute("form", form);
            return "redirect:/cs/customers?new=true";
        }

        Nasabah saved = nasabahService.createCustomer(form, createdByName);
        ra.addFlashAttribute("successMessage",
                "Customer dibuat dengan CIF " + saved.getCif() + " (status INACTIVE menunggu approve).");

        return "redirect:/cs/customers";
    }
}
