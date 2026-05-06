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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NasabahService Integration Tests")
class NasabahServiceTest extends BaseIntegrationTest {

    @Autowired private NasabahService nasabahService;
    @Autowired private NasabahRepository nasabahRepository;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("createNasabah - harus set status INACTIVE jika status null")
    void createNasabah_shouldSetDefaultStatusInactive() {
        Nasabah form = new Nasabah();
        form.setNik("9710001234567890");
        form.setNamaSesuaiIdentitas("Budi Santoso");
        form.setCif("C9710001");
        form.setStatus(null);

        Nasabah result = nasabahService.createNasabah(form);

        assertEquals(NasabahStatus.INACTIVE, result.getStatus());
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("createNasabah - harus auto-generate CIF jika CIF kosong")
    void createNasabah_shouldAutoGenerateCif_whenCifIsBlank() {
        Nasabah form = new Nasabah();
        form.setNik("9710003234567890");
        form.setNamaSesuaiIdentitas("Aisyah Putri");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);

        // CIF di-generate otomatis: format C + 7 digit (max CIF di DB + 1)
        assertNotNull(result.getCif());
        assertTrue(result.getCif().startsWith("C"), "CIF harus dimulai dengan 'C'");
        assertEquals(8, result.getCif().length(), "CIF harus 8 karakter (C + 7 digit)");
    }

    @Test
    @DisplayName("createNasabah - harus pertahankan CIF yang sudah diisi")
    void createNasabah_shouldKeepExistingCif_whenProvided() {
        Nasabah form = new Nasabah();
        form.setNik("9710004234567890");
        form.setNamaSesuaiIdentitas("Ahmad Fauzi");
        form.setCif("C9710099");

        Nasabah result = nasabahService.createNasabah(form);

        assertEquals("C9710099", result.getCif());
    }

    @Test
    @DisplayName("createNasabah - harus set createdBy dari user login")
    void createNasabah_shouldSetCreatedByFromLoggedInUser() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        Nasabah form = new Nasabah();
        form.setNik("9710005234567890");
        form.setNamaSesuaiIdentitas("Test User");
        form.setCif("C9710010");

        Nasabah result = nasabahService.createNasabah(form);

        assertEquals("cs_user", result.getCreatedBy());
    }

    @Test
    @DisplayName("getById - harus return nasabah jika ditemukan di DB")
    void getById_shouldReturnNasabah_whenExists() {
        Nasabah saved = new Nasabah();
        saved.setNik("9710006234567890");
        saved.setNamaSesuaiIdentitas("Budi Santoso");
        saved.setCif("C9710020");
        saved.setStatus(NasabahStatus.ACTIVE);
        saved = nasabahRepository.save(saved);

        Nasabah actual = nasabahService.getById(saved.getId());

        assertNotNull(actual);
        assertEquals("Budi Santoso", actual.getNamaSesuaiIdentitas());
    }

    @Test
    @DisplayName("getById - harus throw EntityNotFoundException jika tidak ditemukan")
    void getById_shouldThrowException_whenNotFound() {
        assertThrows(EntityNotFoundException.class, () -> nasabahService.getById(999L));
    }

    @Test
    @DisplayName("listAllCustomers - harus return semua nasabah dari DB")
    void listAllCustomers_shouldReturnAll() {
        Nasabah n1 = new Nasabah();
        n1.setNik("9710007111111111");
        n1.setNamaSesuaiIdentitas("Nasabah 1");
        n1.setCif("C9710030");
        n1.setStatus(NasabahStatus.ACTIVE);
        nasabahRepository.save(n1);

        Nasabah n2 = new Nasabah();
        n2.setNik("9710008222222222");
        n2.setNamaSesuaiIdentitas("Nasabah 2");
        n2.setCif("C9710031");
        n2.setStatus(NasabahStatus.ACTIVE);
        nasabahRepository.save(n2);

        List<Nasabah> result = nasabahService.listAllCustomers();

        assertTrue(result.size() >= 2);
    }
}
