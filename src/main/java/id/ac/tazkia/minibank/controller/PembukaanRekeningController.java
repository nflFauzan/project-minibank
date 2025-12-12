package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.PembukaanRekeningForm;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PembukaanRekeningController {

    private final ProdukTabunganRepository produkTabunganRepository;

    public PembukaanRekeningController(ProdukTabunganRepository produkTabunganRepository) {
        this.produkTabunganRepository = produkTabunganRepository;
    }

    // TAMPILKAN FORM
    @GetMapping("/cs/pembukaan-rekening")
    public String formPembukaanRekening(Model model) {

        // kalau belum ada "form" di model (misal setelah redirect), buat baru
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new PembukaanRekeningForm());
        }

        // dropdown produk
        model.addAttribute("produkList",
                produkTabunganRepository.findByAktifTrueOrderByNamaProdukAsc());

        return "cs/pembukaan_rekening";
    }

    // PROSES SUBMIT FORM
    @PostMapping("/cs/pembukaan-rekening")
    public String prosesPembukaanRekening(
            @ModelAttribute("form") PembukaanRekeningForm form,
            RedirectAttributes redirectAttributes) {

        // Di sini nanti kamu simpan ke entity "Rekening" / "PengajuanPembukaanRekening".
        // Untuk sementara, kita cuma kirim message dan redirect.

        // contoh "validasi minimal": wajib pilih produk
        if (form.getProdukId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Silakan pilih produk tabungan terlebih dahulu.");
            redirectAttributes.addFlashAttribute("form", form);
            return "redirect:/cs/pembukaan-rekening";
        }

        // TODO: simpan ke database di tahap berikutnya

        redirectAttributes.addFlashAttribute("successMessage",
                "Pengajuan pembukaan rekening berhasil direkam (dummy).");

        // kalau mau balik ke dashboard:
        // return "redirect:/cs/dashboard";

        // untuk sementara kembali ke form pembukaan rekening
        return "redirect:/cs/pembukaan-rekening";
    }
}
