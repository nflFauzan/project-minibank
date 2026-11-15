package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.PeriodePraktikum;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.service.PeriodePraktikumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/periods")
@PreAuthorize("hasAnyRole('ADMIN', 'BRANCH_MANAGER', 'SUPERVISOR')")
@RequiredArgsConstructor
@Slf4j
public class AdminPeriodController {

    private final PeriodePraktikumService periodeService;
    private final NasabahRepository nasabahRepository;
    private final RekeningRepository rekeningRepository;
    private final TransaksiRepository transaksiRepository;

    @GetMapping
    public String listPeriods(Model model) {
        List<PeriodePraktikum> periods = periodeService.getAllPeriods();
        PeriodePraktikum activePeriod = periodeService.getActivePeriod();

        Map<Long, Long> nasabahCounts = new HashMap<>();
        Map<Long, Long> rekeningCounts = new HashMap<>();
        Map<Long, Long> transaksiCounts = new HashMap<>();

        for (PeriodePraktikum p : periods) {
            nasabahCounts.put(p.getId(), nasabahRepository.countByPeriodeId(p.getId()));
            rekeningCounts.put(p.getId(), rekeningRepository.countByPeriodeId(p.getId()));
            transaksiCounts.put(p.getId(), transaksiRepository.countByPeriodeId(p.getId()));
        }

        model.addAttribute("periods", periods);
        model.addAttribute("activePeriod", activePeriod);
        model.addAttribute("nasabahCounts", nasabahCounts);
        model.addAttribute("rekeningCounts", rekeningCounts);
        model.addAttribute("transaksiCounts", transaksiCounts);

        return "admin/periods";
    }

    @PostMapping("/create")
    public String createPeriod(
            @RequestParam String kode,
            @RequestParam String nama,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggalMulai,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggalSelesai,
            @RequestParam(required = false) String deskripsi,
            @RequestParam(defaultValue = "false") boolean setAsActive,
            RedirectAttributes ra
    ) {
        try {
            PeriodePraktikum created = periodeService.createPeriod(
                    kode, nama, tanggalMulai, tanggalSelesai, deskripsi, setAsActive
            );
            ra.addFlashAttribute("success", "Periode praktikum '" + created.getNama() + "' (" + created.getKode() + ") berhasil ditambahkan.");
        } catch (Exception e) {
            log.error("Gagal membuat periode praktikum", e);
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/periods";
    }

    @PostMapping("/{id}/activate")
    public String activatePeriod(@PathVariable Long id, RedirectAttributes ra) {
        try {
            PeriodePraktikum activated = periodeService.activatePeriod(id);
            ra.addFlashAttribute("success", "Periode praktikum aktif berhasil disetel ke: " + activated.getNama() + " (" + activated.getKode() + ").");
        } catch (Exception e) {
            log.error("Gagal mengaktifkan periode praktikum", e);
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/periods";
    }
}
