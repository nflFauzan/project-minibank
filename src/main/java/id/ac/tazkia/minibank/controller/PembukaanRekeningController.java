package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.RekeningForm;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
public class PembukaanRekeningController {

    private final UserRepository userRepository;

    // Sample produk dulu (biar dropdown nggak kosong)
    public record ProdukOption(String kode, String nama) {}

    private List<ProdukOption> sampleProduk() {
        return List.of(
                new ProdukOption("WADIAH", "Tabungan Wadiah"),
                new ProdukOption("MUDHARABAH", "Tabungan Mudharabah"),
                new ProdukOption("HAJI", "Tabungan Haji"),
                new ProdukOption("PENDIDIKAN", "Tabungan Pendidikan"),
                new ProdukOption("GIRO_WADIAH", "Giro Wadiah")
        );
    }

    @GetMapping("/pembukaan-rekening")
    public String page(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        RekeningForm form = new RekeningForm();
        form.setTanggalPembukaan(LocalDate.now());

        // Ambil full_name dari tabel users berdasarkan username login
        if (userDetails != null) {
            User u = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (u != null) {
                form.setPetugasCs(u.getFullName());
            }
        }

        // ini penting: selalu kirim attribute yang dipakai template
        model.addAttribute("form", form);
        model.addAttribute("produkList", sampleProduk());

        return "cs/pembukaan-rekening"; // pastikan file html-nya ada
    }
}
