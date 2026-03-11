package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NasabahService Unit Tests")
class NasabahServiceTest {

    @Mock
    private NasabahRepository nasabahRepository;

    @InjectMocks
    private NasabahService nasabahService;

    @BeforeEach
    void setUp() {
        // Clear SecurityContext sebelum setiap test
        SecurityContextHolder.clearContext();
    }

    // ========== CREATE NASABAH ==========

    @Test
    @DisplayName("createNasabah - harus set status INACTIVE jika status null")
    void createNasabah_shouldSetDefaultStatusInactive() {
        // Arrange
        Nasabah form = new Nasabah();
        form.setNik("1234567890123456");
        form.setNamaSesuaiIdentitas("Budi Santoso");
        form.setCif("C0000001");
        form.setStatus(null); // status belum di-set

        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Nasabah result = nasabahService.createNasabah(form);

        // Assert
        assertEquals(NasabahStatus.INACTIVE, result.getStatus(),
                "Status nasabah baru harus INACTIVE secara default");
        verify(nasabahRepository, times(1)).save(form);
    }

    @Test
    @DisplayName("createNasabah - harus auto-generate CIF jika CIF kosong")
    void createNasabah_shouldAutoGenerateCif_whenCifIsBlank() {
        // Arrange
        Nasabah form = new Nasabah();
        form.setNik("1234567890123456");
        form.setNamaSesuaiIdentitas("Aisyah Putri");
        form.setCif(""); // CIF kosong → harus di-generate

        when(nasabahRepository.findMaxCif()).thenReturn("C0000005");
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Nasabah result = nasabahService.createNasabah(form);

        // Assert
        assertEquals("C0000006", result.getCif(),
                "CIF harus di-auto-generate menjadi C0000006 (max saat ini C0000005)");
        verify(nasabahRepository).findMaxCif();
    }

    @Test
    @DisplayName("createNasabah - harus pertahankan CIF yang sudah diisi")
    void createNasabah_shouldKeepExistingCif_whenProvided() {
        // Arrange
        Nasabah form = new Nasabah();
        form.setNik("9876543210123456");
        form.setNamaSesuaiIdentitas("Ahmad Fauzi");
        form.setCif("C0000099"); // CIF sudah diisi

        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Nasabah result = nasabahService.createNasabah(form);

        // Assert
        assertEquals("C0000099", result.getCif(),
                "CIF yang sudah diisi tidak boleh di-overwrite");
        verify(nasabahRepository, never()).findMaxCif();
    }

    @Test
    @DisplayName("createNasabah - harus set createdBy dari user login")
    void createNasabah_shouldSetCreatedByFromLoggedInUser() {
        // Arrange: simulasi user yang sedang login
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        Nasabah form = new Nasabah();
        form.setNik("1111222233334444");
        form.setNamaSesuaiIdentitas("Test User");
        form.setCif("C0000010");

        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Nasabah result = nasabahService.createNasabah(form);

        // Assert
        assertEquals("cs_user", result.getCreatedBy(),
                "createdBy harus terisi dengan username user yang login");
    }

    // ========== GET BY ID ==========

    @Test
    @DisplayName("getById - harus return nasabah jika ditemukan")
    void getById_shouldReturnNasabah_whenExists() {
        // Arrange
        Nasabah expected = new Nasabah();
        expected.setId(1L);
        expected.setNik("1234567890123456");
        expected.setNamaSesuaiIdentitas("Budi Santoso");

        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(expected));

        // Act
        Nasabah actual = nasabahService.getById(1L);

        // Assert
        assertNotNull(actual);
        assertEquals("Budi Santoso", actual.getNamaSesuaiIdentitas());
        verify(nasabahRepository).findById(1L);
    }

    @Test
    @DisplayName("getById - harus throw EntityNotFoundException jika tidak ditemukan")
    void getById_shouldThrowException_whenNotFound() {
        // Arrange
        when(nasabahRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
                () -> nasabahService.getById(999L));

        assertTrue(thrown.getMessage().contains("999"),
                "Pesan error harus mengandung ID yang dicari");
    }

    // ========== LIST ALL ==========

    @Test
    @DisplayName("listAllCustomers - harus return semua nasabah dari repository")
    void listAllCustomers_shouldReturnAll() {
        // Arrange
        Nasabah n1 = new Nasabah();
        n1.setNamaSesuaiIdentitas("Nasabah 1");
        Nasabah n2 = new Nasabah();
        n2.setNamaSesuaiIdentitas("Nasabah 2");

        when(nasabahRepository.findAll()).thenReturn(Arrays.asList(n1, n2));

        // Act
        List<Nasabah> result = nasabahService.listAllCustomers();

        // Assert
        assertEquals(2, result.size(), "Harus mengembalikan 2 nasabah");
        verify(nasabahRepository).findAll();
    }
}
