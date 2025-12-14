package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cs")
public class CsPendaftaranController {

    @Autowired
    private NasabahService nasabahService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/pendaftaran")
    public String pendaftaranForm(Model model) {
        if (!model.containsAttribute("nasabah")) {
            model.addAttribute("nasabah", new Nasabah());
        }
        return "cs/pendaftaran_nasabah";
    }

    @PostMapping("/pendaftaran")
    public String prosesPendaftaran(
            @ModelAttribute("nasabah") Nasabah nasabah,
            Authentication auth,
            RedirectAttributes redirectAttributes
    ) {
        // ambil user login (CS)
        String username = auth.getName();
        User u = userRepository.findByUsername(username).orElseThrow();

        // set metadata approval
        nasabah.setCreatedBy(u.getFullName());
        nasabah.setStatus(NasabahStatus.PENDING);

        Nasabah nasabahBaru = nasabahService.createNasabahBaru(nasabah);

        redirectAttributes.addFlashAttribute("successMessage",
                "Nasabah " + nasabahBaru.getNamaLengkap() + " berhasil didaftarkan dengan CIF " + nasabahBaru.getCif());

        return "redirect:/cs/dashboard";
    }
}
