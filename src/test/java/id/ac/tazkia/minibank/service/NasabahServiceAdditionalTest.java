package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("NasabahService - Additional Integration Tests")
class NasabahServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private NasabahService nasabahService;
    @Autowired private NasabahRepository nasabahRepository;

    private NasabahRepository mockNasabahRepository;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        ReflectionTestUtils.setField(nasabahService, "nasabahRepository", nasabahRepository);
    }

    @Test
    @DisplayName("createNasabah - auth null - createdBy tidak diset")
    void createNasabah_authNull_shouldNotSetCreatedBy() {
        SecurityContextHolder.clearContext();

        Nasabah form = new Nasabah();
        form.setNik("9750001234567890");
        form.setNamaSesuaiIdentitas("Hasan");
        form.setCif("C9750001");
        form.setCreatedBy(null);

        Nasabah result = nasabahService.createNasabah(form);
        assertNull(result.getCreatedBy());
    }

    @Test
    @DisplayName("createNasabah - auth anonymous - createdBy tidak diset")
    void createNasabah_authAnonymous_shouldNotSetCreatedBy() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("anonymousUser", "anonymous", Collections.emptyList())
        );

        Nasabah form = new Nasabah();
        form.setNik("9750002234567890");
        form.setNamaSesuaiIdentitas("Husein");
        form.setCif("C9750002");
        form.setCreatedBy(null);

        Nasabah result = nasabahService.createNasabah(form);
        assertNull(result.getCreatedBy());
    }

    @Test
    @DisplayName("createNasabah - createdBy sudah terisi - keep nilai createdBy tersebut")
    void createNasabah_createdByProvided_shouldKeepProvidedCreatedBy() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        Nasabah form = new Nasabah();
        form.setNik("9750003234567890");
        form.setNamaSesuaiIdentitas("Fatimah");
        form.setCif("C9750003");
        form.setCreatedBy("superadmin");

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals("superadmin", result.getCreatedBy());
    }

    @Test
    @DisplayName("createNasabah - status sudah terisi - keep status tersebut")
    void createNasabah_statusProvided_shouldKeepProvidedStatus() {
        Nasabah form = new Nasabah();
        form.setNik("9750004234567890");
        form.setNamaSesuaiIdentitas("Khadijah");
        form.setCif("C9750004");
        form.setStatus(NasabahStatus.ACTIVE);

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals(NasabahStatus.ACTIVE, result.getStatus());
    }

    @Test
    @DisplayName("generateNextCif - ketika DB kosong (findMaxCif return null) - fallback ke C0000001")
    void generateNextCif_dbEmpty_returnsC0000001() {
        mockNasabahRepository = mock(NasabahRepository.class);
        ReflectionTestUtils.setField(nasabahService, "nasabahRepository", mockNasabahRepository);

        when(mockNasabahRepository.findMaxCif()).thenReturn(null);
        when(mockNasabahRepository.save(any(Nasabah.class))).thenAnswer(i -> i.getArgument(0));

        Nasabah form = new Nasabah();
        form.setNik("9750005234567890");
        form.setNamaSesuaiIdentitas("Zainab");
        form.setCif(null); // Trigger generator

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals("C0000001", result.getCif());
    }

    @Test
    @DisplayName("generateNextCif - ketika CIF max tidak diawali C - fallback ke C0000001")
    void generateNextCif_dbCifNotStartWithC_returnsC0000001() {
        mockNasabahRepository = mock(NasabahRepository.class);
        ReflectionTestUtils.setField(nasabahService, "nasabahRepository", mockNasabahRepository);

        when(mockNasabahRepository.findMaxCif()).thenReturn("B0000002");
        when(mockNasabahRepository.save(any(Nasabah.class))).thenAnswer(i -> i.getArgument(0));

        Nasabah form = new Nasabah();
        form.setNik("9750006234567890");
        form.setNamaSesuaiIdentitas("Ruqayyah");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals("C0000001", result.getCif());
    }

    @Test
    @DisplayName("generateNextCif - ketika CIF max panjang tidak valid - fallback ke C0000001")
    void generateNextCif_dbCifLengthInvalid_returnsC0000001() {
        mockNasabahRepository = mock(NasabahRepository.class);
        ReflectionTestUtils.setField(nasabahService, "nasabahRepository", mockNasabahRepository);

        when(mockNasabahRepository.findMaxCif()).thenReturn("C00001"); // 6 char
        when(mockNasabahRepository.save(any(Nasabah.class))).thenAnswer(i -> i.getArgument(0));

        Nasabah form = new Nasabah();
        form.setNik("9750007234567890");
        form.setNamaSesuaiIdentitas("Ummu Kulsum");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals("C0000001", result.getCif());
    }

    @Test
    @DisplayName("generateNextCif - ketika CIF max unparsable (NumberFormatException) - fallback ke C0000001")
    void generateNextCif_dbCifUnparsable_returnsC0000001() {
        mockNasabahRepository = mock(NasabahRepository.class);
        ReflectionTestUtils.setField(nasabahService, "nasabahRepository", mockNasabahRepository);

        when(mockNasabahRepository.findMaxCif()).thenReturn("C0000XYZ");
        when(mockNasabahRepository.save(any(Nasabah.class))).thenAnswer(i -> i.getArgument(0));

        Nasabah form = new Nasabah();
        form.setNik("9750008234567890");
        form.setNamaSesuaiIdentitas("Ibrahim");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals("C0000001", result.getCif());
    }

    @Test
    @DisplayName("generateNextCif - ketika CIF max valid - mengembalikan nilai incremented")
    void generateNextCif_dbCifValid_returnsIncrementedCif() {
        mockNasabahRepository = mock(NasabahRepository.class);
        ReflectionTestUtils.setField(nasabahService, "nasabahRepository", mockNasabahRepository);

        when(mockNasabahRepository.findMaxCif()).thenReturn("C0000009");
        when(mockNasabahRepository.save(any(Nasabah.class))).thenAnswer(i -> i.getArgument(0));

        Nasabah form = new Nasabah();
        form.setNik("9750009234567890");
        form.setNamaSesuaiIdentitas("Ahmad");
        form.setCif("");

        Nasabah result = nasabahService.createNasabah(form);
        assertEquals("C0000010", result.getCif());
    }

    @Test
    @DisplayName("updateNasabah - nasabah ditemukan - sukses mengupdate seluruh field")
    void updateNasabah_success() {
        Nasabah saved = new Nasabah();
        saved.setNik("9751001234567890");
        saved.setNamaSesuaiIdentitas("Budi Santoso");
        saved.setCif("C9751001");
        saved.setStatus(NasabahStatus.ACTIVE);
        saved = nasabahRepository.save(saved);

        Nasabah form = new Nasabah();
        form.setNik("9751001234567899");
        form.setNamaSesuaiIdentitas("Budi Santoso Updated");
        form.setNamaIbuKandung("Siti");
        form.setJenisKelamin("L");
        form.setTempatLahir("Jakarta");
        form.setEmail("budi.up@email.com");
        form.setNoHp("08129999999");
        form.setAlamatIdentitas("Jl. Sudirman 10");
        form.setProvinsiIdentitas("DKI Jakarta");
        form.setAlamatDomisili("Jl. Gatot Subroto 20");
        form.setProvinsiDomisili("DKI Jakarta");

        nasabahService.updateNasabah(saved.getId(), form);

        Nasabah updated = nasabahRepository.findById(saved.getId()).orElseThrow();
        assertEquals("9751001234567899", updated.getNik());
        assertEquals("Budi Santoso Updated", updated.getNamaSesuaiIdentitas());
        assertEquals("Siti", updated.getNamaIbuKandung());
        assertEquals("L", updated.getJenisKelamin());
        assertEquals("Jakarta", updated.getTempatLahir());
        assertEquals("budi.up@email.com", updated.getEmail());
        assertEquals("08129999999", updated.getNoHp());
        assertEquals("Jl. Sudirman 10", updated.getAlamatIdentitas());
        assertEquals("Jl. Gatot Subroto 20", updated.getAlamatDomisili());
    }

    @Test
    @DisplayName("updateNasabah - nasabah tidak ditemukan - throw EntityNotFoundException")
    void updateNasabah_notFound_shouldThrowEntityNotFoundException() {
        Nasabah form = new Nasabah();
        form.setNik("9751002234567890");

        assertThrows(EntityNotFoundException.class, () ->
                nasabahService.updateNasabah(999999L, form));
    }
}
