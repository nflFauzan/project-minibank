package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.service.CsProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cs/product")
public class CsProductController {

    private final ProdukTabunganRepository produkTabunganRepository;
    private final CsProductService csProductService;

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(defaultValue = "ALL") String status,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        Boolean aktif = switch (status == null ? "ALL" : status.toUpperCase()) {
            case "ACTIVE", "AKTIF" -> Boolean.TRUE;
            case "INACTIVE", "NONAKTIF", "NONAKTIVE", "NONAKTIFKAN" -> Boolean.FALSE;
            default -> null; // ALL
        };

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "namaProduk"));
        Page<ProdukTabungan> result = csProductService.search(q, aktif, pageable);

        model.addAttribute("active", "product");
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        model.addAttribute("status", (status == null ? "ALL" : status.toUpperCase()));
        return "cs/product/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("active", "product");
        model.addAttribute("mode", "CREATE");
        model.addAttribute("akadOptions", csProductService.akadOptions());
        model.addAttribute("form", new ProductForm());
        model.addAttribute("actionUrl", "/cs/product/new");
        model.addAttribute("submitLabel", "Simpan Produk");
        return "cs/product/form";
    }

    @PostMapping("/new")
    public String createProcess(@ModelAttribute("form") ProductForm form,
                                RedirectAttributes ra) {
        try {
            csProductService.create(
                    form.getKodeProduk(),
                    form.getNamaProduk(),
                    form.getDeskripsiSingkat(),
                    form.getJenisAkad(),
                    form.getSetoranAwalMinimum()
            );
            ra.addFlashAttribute("success", "Produk berhasil dibuat.");
            return "redirect:/cs/product";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/product/new";
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ProdukTabungan p = produkTabunganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produk tidak ditemukan"));
        model.addAttribute("active", "product");
        model.addAttribute("p", p);
        return "cs/product/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model,
                           RedirectAttributes ra) {
        try {
            ProdukTabungan p = produkTabunganRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produk tidak ditemukan"));

            ProductForm form = new ProductForm();
            form.setKodeProduk(p.getKodeProduk());
            form.setNamaProduk(p.getNamaProduk());
            form.setDeskripsiSingkat(p.getDeskripsiSingkat());
            form.setJenisAkad(p.getJenisAkad());
            form.setSetoranAwalMinimum(p.getSetoranAwalMinimum());

            model.addAttribute("active", "product");
            model.addAttribute("mode", "EDIT");
            model.addAttribute("akadOptions", csProductService.akadOptions());
            model.addAttribute("form", form);
            model.addAttribute("p", p);
            model.addAttribute("actionUrl", "/cs/product/" + id + "/edit");
            model.addAttribute("submitLabel", "Simpan Perubahan");
            return "cs/product/form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/product";
        }
    }

    @PostMapping("/{id}/edit")
    public String editProcess(@PathVariable Long id,
                              @ModelAttribute("form") ProductForm form,
                              RedirectAttributes ra) {
        try {
            csProductService.update(
                    id,
                    form.getKodeProduk(),
                    form.getNamaProduk(),
                    form.getDeskripsiSingkat(),
                    form.getJenisAkad(),
                    form.getSetoranAwalMinimum()
            );
            ra.addFlashAttribute("success", "Produk berhasil diupdate.");
            return "redirect:/cs/product/" + id;
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/product/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id,
                         @RequestParam(required = false) String q,
                         @RequestParam(required = false, defaultValue = "ALL") String status,
                         @RequestParam(required = false, defaultValue = "0") int page,
                         RedirectAttributes ra) {
        try {
            csProductService.toggleAktif(id);
            ra.addFlashAttribute("success", "Status produk berhasil diubah.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cs/product?q=" + (q == null ? "" : q) + "&status=" + (status == null ? "ALL" : status) + "&page=" + page;
    }

    @Data
    public static class ProductForm {
        private String kodeProduk;
        private String namaProduk;
        private String deskripsiSingkat;
        private String jenisAkad;
        private BigDecimal setoranAwalMinimum;
    }
}