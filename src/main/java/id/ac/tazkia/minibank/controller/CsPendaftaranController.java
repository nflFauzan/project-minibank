package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.service.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cs")
public class CsPendaftaranController {

    @Autowired
    private NasabahService nasabahService;

    @GetMapping("/pendaftaran")
    public String pendaftaranForm(Model model) {
        if (!model.containsAttribute("nasabah")) {
            model.addAttribute("nasabah", new Nasabah());
        }
        return "cs/pendaftaran_nasabah"; // <<< PASTIKAN INI
    }

    @PostMapping("/pendaftaran")
public String prosesPendaftaran(@ModelAttribute("nasabah") Nasabah nasabah,
                                RedirectAttributes redirectAttributes) {
    Nasabah nasabahBaru = nasabahService.createNasabahBaru(nasabah);
    redirectAttributes.addFlashAttribute("successMessage",
            "Nasabah " + nasabahBaru.getNamaLengkap() + " berhasil didaftarkan dengan CIF " + nasabahBaru.getCif());
    return "redirect:/cs/dashboard";
}

}
