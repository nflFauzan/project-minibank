package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.PeriodePraktikum;
import id.ac.tazkia.minibank.entity.PeriodeStatus;
import id.ac.tazkia.minibank.repository.PeriodePraktikumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeriodePraktikumService {

    private final PeriodePraktikumRepository periodeRepo;

    /**
     * Mendapatkan periode praktikum yang sedang aktif saat ini.
     * Jika belum ada sama sekali di database, buat default periode aktif 2026/2027.
     */
    @Transactional
    public PeriodePraktikum getActivePeriod() {
        return periodeRepo.findByIsActiveTrue()
                .orElseGet(() -> {
                    log.warn("Tidak ada periode praktikum aktif di DB. Membuat default aktif 2026/2027...");
                    PeriodePraktikum fallback = PeriodePraktikum.builder()
                            .kode("2026/2027")
                            .nama("Praktikum Minibank 2026/2027 (Aktif)")
                            .status(PeriodeStatus.ACTIVE)
                            .isActive(true)
                            .isDefault(true)
                            .tanggalMulai(LocalDate.of(2026, 9, 1))
                            .tanggalSelesai(LocalDate.of(2027, 1, 31))
                            .deskripsi("Periode praktikum minibank tahun ajaran aktif")
                            .build();
                    return periodeRepo.save(fallback);
                });
    }

    @Transactional(readOnly = true)
    public List<PeriodePraktikum> getAllPeriods() {
        return periodeRepo.findAllOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<PeriodePraktikum> getDropdownPeriods() {
        return periodeRepo.findAllForDropdown();
    }

    @Transactional(readOnly = true)
    public Optional<PeriodePraktikum> getPeriodById(Long id) {
        return periodeRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PeriodePraktikum> getPeriodByKode(String kode) {
        return periodeRepo.findByKode(kode);
    }

    /**
     * Mengaktifkan periode terpilih sebagai periode praktikum aktif.
     * Seluruh periode lain otomatis dinonaktifkan (is_active = false, status = ARCHIVED).
     */
    @Transactional
    public PeriodePraktikum activatePeriod(Long periodId) {
        PeriodePraktikum target = periodeRepo.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("Periode dengan ID " + periodId + " tidak ditemukan."));

        List<PeriodePraktikum> all = periodeRepo.findAll();
        for (PeriodePraktikum p : all) {
            if (p.getId().equals(periodId)) {
                p.setIsActive(true);
                p.setStatus(PeriodeStatus.ACTIVE);
            } else {
                p.setIsActive(false);
                if (p.getStatus() == PeriodeStatus.ACTIVE) {
                    p.setStatus(PeriodeStatus.ARCHIVED);
                }
            }
            p.setUpdatedAt(LocalDateTime.now());
        }
        periodeRepo.saveAll(all);
        log.info("Periode praktikum aktif berhasil diubah menjadi: {} ({})", target.getNama(), target.getKode());
        return target;
    }

    /**
     * Membuat periode praktikum baru (misal menjelang praktikum 2027/2028).
     */
    @Transactional
    public PeriodePraktikum createPeriod(String kode, String nama, LocalDate mulai, LocalDate selesai, String deskripsi, boolean setAsActive) {
        if (kode == null || kode.trim().isBlank()) {
            throw new IllegalArgumentException("Kode periode tidak boleh kosong.");
        }
        String cleanKode = kode.trim();
        if (periodeRepo.findByKode(cleanKode).isPresent()) {
            throw new IllegalArgumentException("Periode dengan kode '" + cleanKode + "' sudah terdaftar.");
        }

        PeriodePraktikum baru = PeriodePraktikum.builder()
                .kode(cleanKode)
                .nama(nama != null && !nama.trim().isBlank() ? nama.trim() : cleanKode)
                .tanggalMulai(mulai)
                .tanggalSelesai(selesai)
                .deskripsi(deskripsi)
                .status(setAsActive ? PeriodeStatus.ACTIVE : PeriodeStatus.UPCOMING)
                .isActive(setAsActive)
                .isDefault(false)
                .build();

        PeriodePraktikum saved = periodeRepo.save(baru);
        if (setAsActive) {
            activatePeriod(saved.getId());
        }
        return saved;
    }
}
