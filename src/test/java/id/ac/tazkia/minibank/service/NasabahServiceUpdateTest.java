package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NasabahService - updateNasabah Integration Tests")
class NasabahServiceUpdateTest extends BaseIntegrationTest {

    @Autowired private NasabahService nasabahService;
    @Autowired private NasabahRepository nasabahRepository;

    private Long nasabahId;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9720001");
        n.setNik("9720001111111111");
        n.setNamaSesuaiIdentitas("Lama");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);
        nasabahId = n.getId();
    }

    @Test
    @DisplayName("updateNasabah - berhasil update semua field di DB")
    void updateNasabah_success() {
        Nasabah form = new Nasabah();
        form.setNik("9720002222222222");
        form.setNamaSesuaiIdentitas("Baru");
        form.setNamaIbuKandung("Ibu Baru");
        form.setEmail("baru@email.com");
        form.setNoHp("08111222333");
        form.setAlamatIdentitas("Jl Identitas");
        form.setAlamatDomisili("Jl Domisili");

        nasabahService.updateNasabah(nasabahId, form);

        Nasabah updated = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals("9720002222222222", updated.getNik());
        assertEquals("Baru", updated.getNamaSesuaiIdentitas());
        assertEquals(NasabahStatus.ACTIVE, updated.getStatus());
    }

    @Test
    @DisplayName("updateNasabah - throw EntityNotFoundException jika tidak ditemukan")
    void updateNasabah_shouldThrow_whenNotFound() {
        assertThrows(EntityNotFoundException.class,
                () -> nasabahService.updateNasabah(999999L, new Nasabah()));
    }

    @Test
    @DisplayName("createNasabah - CIF auto-generate dari DB")
    void createNasabah_shouldGenerateCif() {
        Nasabah form = new Nasabah();
        form.setNik("9720003333333333");
        form.setNamaSesuaiIdentitas("New User");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);

        assertNotNull(result.getCif());
        assertTrue(result.getCif().startsWith("C"));
        assertEquals(8, result.getCif().length());
    }

    @Test
    @DisplayName("createNasabah - CIF incremented dari max CIF di DB")
    void createNasabah_shouldIncrementCif() {
        Nasabah form = new Nasabah();
        form.setNik("9720004444444444");
        form.setNamaSesuaiIdentitas("Second User");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);

        assertNotNull(result.getCif());
        assertTrue(result.getCif().startsWith("C"));
    }
}
