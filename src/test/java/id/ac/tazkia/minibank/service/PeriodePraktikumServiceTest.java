package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.PeriodePraktikum;
import id.ac.tazkia.minibank.entity.PeriodeStatus;
import id.ac.tazkia.minibank.repository.PeriodePraktikumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PeriodePraktikumService Integration Tests")
class PeriodePraktikumServiceTest extends BaseIntegrationTest {

    @Autowired
    private PeriodePraktikumService periodeService;

    @Autowired
    private PeriodePraktikumRepository periodeRepo;

    @Test
    @DisplayName("getActivePeriod - harus mengembalikan periode yang aktif")
    void getActivePeriod_shouldReturnActivePeriod() {
        PeriodePraktikum active = periodeService.getActivePeriod();
        assertNotNull(active);
        assertTrue(active.getIsActive());
        assertEquals(PeriodeStatus.ACTIVE, active.getStatus());
    }

    @Test
    @DisplayName("createPeriod - harus berhasil membuat periode baru dan menolak kode duplikat")
    void createPeriod_shouldCreateAndRejectDuplicate() {
        String testKode = "TEST/" + System.currentTimeMillis();
        PeriodePraktikum baru = periodeService.createPeriod(
                testKode,
                "Praktikum Uji Coba",
                LocalDate.of(2028, 1, 1),
                LocalDate.of(2028, 6, 30),
                "Uji coba pembuatan periode",
                false
        );

        assertNotNull(baru.getId());
        assertEquals(testKode, baru.getKode());
        assertFalse(baru.getIsActive());
        assertEquals(PeriodeStatus.UPCOMING, baru.getStatus());

        // Duplicate rejection
        assertThrows(IllegalArgumentException.class, () -> {
            periodeService.createPeriod(testKode, "Duplikat", null, null, null, false);
        });
    }

    @Test
    @DisplayName("activatePeriod - harus menonaktifkan periode sebelumnya dan mengaktifkan yang baru")
    void activatePeriod_shouldSwitchActivePeriodAtomically() {
        String kode1 = "PER1_" + System.currentTimeMillis();
        String kode2 = "PER2_" + System.currentTimeMillis();

        PeriodePraktikum p1 = periodeService.createPeriod(kode1, "Periode 1", null, null, null, true);
        assertTrue(p1.getIsActive());

        PeriodePraktikum p2 = periodeService.createPeriod(kode2, "Periode 2", null, null, null, false);
        assertFalse(p2.getIsActive());

        // Aktifkan p2
        PeriodePraktikum activatedP2 = periodeService.activatePeriod(p2.getId());
        assertTrue(activatedP2.getIsActive());
        assertEquals(PeriodeStatus.ACTIVE, activatedP2.getStatus());

        // Pastikan p1 sekarang archived
        PeriodePraktikum refreshedP1 = periodeRepo.findById(p1.getId()).orElseThrow();
        assertFalse(refreshedP1.getIsActive());
        assertEquals(PeriodeStatus.ARCHIVED, refreshedP1.getStatus());
    }

    @Test
    @DisplayName("getAllPeriods & getDropdownPeriods - harus mengembalikan daftar periode terurut")
    void getDropdownPeriods_shouldReturnList() {
        periodeService.getActivePeriod(); // Pastikan ada periode aktif
        List<PeriodePraktikum> dropdown = periodeService.getDropdownPeriods();
        assertNotNull(dropdown);
        assertFalse(dropdown.isEmpty());
        // Elemen pertama harus yang sedang aktif
        assertTrue(dropdown.get(0).getIsActive());
    }
}
