package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.service.NasabahService;
import id.ac.tazkia.minibank.service.PeriodePraktikumService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cs/customers")
public class CsCustomerController {

    private final NasabahService nasabahService;
    private final PeriodePraktikumService periodePraktikumService;

    // LIST: GET /cs/customers
    @GetMapping
    public String customers(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        Long periodeId = null;
        String selectedPeriod = period;

        if (period == null) {
            // Default ke periode aktif jika ada
            var active = periodePraktikumService.getActivePeriod();
            if (active != null) {
                periodeId = active.getId();
                selectedPeriod = String.valueOf(active.getId());
            }
        } else if ("all".equalsIgnoreCase(period) || period.isBlank()) {
            periodeId = null;
            selectedPeriod = "all";
        } else {
            try {
                periodeId = Long.parseLong(period);
            } catch (NumberFormatException e) {
                periodeId = null;
            }
        }

        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        Pageable pageable = PageRequest.of(page, 10);
        Page<Nasabah> customerPage = nasabahService.searchCustomersAdvanced(q, null, periodeId, startDateTime, endDateTime, pageable);

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("page", customerPage);
        model.addAttribute("q", q != null ? q : "");
        model.addAttribute("periods", periodePraktikumService.getDropdownPeriods());
        model.addAttribute("selectedPeriod", selectedPeriod);
        model.addAttribute("startDate", startDate != null ? startDate.toString() : "");
        model.addAttribute("endDate", endDate != null ? endDate.toString() : "");
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
        // Validasi kelengkapan data dasar nasabah
        if (form.getNik() == null || form.getNik().trim().isBlank() ||
            form.getNamaSesuaiIdentitas() == null || form.getNamaSesuaiIdentitas().trim().isBlank()) {
            ra.addFlashAttribute("error", "Data nasabah belum lengkap. NIK dan Nama sesuai identitas wajib diisi.");
            return "redirect:/cs/customers/new";
        }

        try {
            Nasabah saved = nasabahService.createNasabah(form);
            ra.addFlashAttribute("successMessage", "Data nasabah berhasil disimpan! Nomor CIF: " + saved.getCif());
            ra.addFlashAttribute("success", "Data nasabah berhasil disimpan!");
            ra.addFlashAttribute("registeredCif", saved.getCif());
            ra.addFlashAttribute("registeredName", saved.getNamaLengkap());
            return "redirect:/cs/customers/new";
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

    // DELETE: POST / GET /cs/customers/{id}/delete atau /cs/customers/delete/{id}
    @RequestMapping(value = {"/{id:\\d+}/delete", "/delete/{id:\\d+}"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            nasabahService.deleteNasabah(id);
            ra.addFlashAttribute("success", "Nasabah berhasil dihapus.");
        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("error", "Nasabah tidak ditemukan.");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal menghapus nasabah: " + e.getMessage());
        }
        return "redirect:/cs/customers";
    }
}
