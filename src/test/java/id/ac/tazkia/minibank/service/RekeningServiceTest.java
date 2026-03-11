package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@DisplayName("RekeningService Unit Tests")
class RekeningServiceTest {

    @Mock
    private RekeningRepository rekeningRepository;

    @Mock
    private NasabahRepository nasabahRepository;

    @Mock
    private ProdukTabunganRepository produkTabunganRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RekeningService rekeningService;

    private Nasabah activeNasabah;
    private ProdukTabungan activeProduk;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();

        // Siapkan nasabah ACTIVE standar
        activeNasabah = new Nasabah();
        activeNasabah.setId(1L);
        activeNasabah.setCif("C0000001");
        activeNasabah.setNik("1234567890123456");
        activeNasabah.setNamaSesuaiIdentitas("Budi Santoso");
        activeNasabah.setEmail("budi@email.com");
        activeNasabah.setNoHp("08123456789");
        activeNasabah.setAlamatDomisili("Jl. Merdeka No. 1");
        activeNasabah.setStatus(NasabahStatus.ACTIVE);

        // Siapkan produk tabungan aktif standar
        activeProduk = new ProdukTabungan();
        activeProduk.setId(1L);
        activeProduk.setKodeProduk("01");
        activeProduk.setNamaProduk("Tabungan Wadiah");
        activeProduk.setAktif(true);
    }

    // ========== OPEN ACCOUNT ==========

    @Test
    @DisplayName("openAccount - harus buat rekening dengan nomor rekening yang benar")
    void openAccount_shouldCreateRekening_withCorrectNomorRekening() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(activeNasabah));
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(activeProduk));
        when(rekeningRepository.nextSequence6()).thenReturn("000001");
        lenient().when(userRepository.findByUsername("cs_user")).thenReturn(Optional.empty());
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Rekening result = rekeningService.openAccount(1L, 1L,
                new BigDecimal("500000"), "Menabung");

        // Assert
        assertNotNull(result);
        assertEquals("54300000101", result.getNomorRekening(),
                "Format nomor rekening: 543 + 000001 + 01");
        assertEquals("C0000001", result.getCifNasabah());
        assertEquals("Budi Santoso", result.getNamaNasabah());
        assertEquals("Tabungan Wadiah", result.getProduk());
        assertEquals(new BigDecimal("500000"), result.getNominalSetoranAwal());
        assertEquals("Menabung", result.getTujuanPembukaan());
        assertTrue(result.isStatusActive(), "Rekening baru harus aktif");
        assertEquals("543", result.getCabangPembukaan());
        verify(rekeningRepository).save(any(Rekening.class));
    }

    @Test
    @DisplayName("openAccount - harus throw exception jika nasabah belum ACTIVE")
    void openAccount_shouldThrow_whenNasabahNotActive() {
        // Arrange: nasabah masih INACTIVE
        Nasabah inactiveNasabah = new Nasabah();
        inactiveNasabah.setId(2L);
        inactiveNasabah.setStatus(NasabahStatus.INACTIVE);

        when(nasabahRepository.findById(2L)).thenReturn(Optional.of(inactiveNasabah));

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
                () -> rekeningService.openAccount(2L, 1L,
                        new BigDecimal("500000"), "Menabung"));

        assertTrue(thrown.getMessage().toLowerCase().contains("active"),
                "Pesan error harus menyebutkan bahwa nasabah belum ACTIVE");
        verify(rekeningRepository, never()).save(any());
    }

    @Test
    @DisplayName("openAccount - harus throw exception jika produk tidak ditemukan")
    void openAccount_shouldThrow_whenProdukNotFound() {
        // Arrange
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(activeNasabah));
        when(produkTabunganRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> rekeningService.openAccount(1L, 999L,
                        new BigDecimal("500000"), "Menabung"));

        verify(rekeningRepository, never()).save(any());
    }

    @Test
    @DisplayName("openAccount - harus throw exception jika produk tidak aktif")
    void openAccount_shouldThrow_whenProdukNotAktif() {
        // Arrange: produk yang tidak aktif
        ProdukTabungan inactiveProduk = new ProdukTabungan();
        inactiveProduk.setId(2L);
        inactiveProduk.setKodeProduk("02");
        inactiveProduk.setNamaProduk("Tabungan Mudharabah");
        inactiveProduk.setAktif(false); // produk tidak aktif

        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(activeNasabah));
        when(produkTabunganRepository.findById(2L)).thenReturn(Optional.of(inactiveProduk));

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> rekeningService.openAccount(1L, 2L,
                        new BigDecimal("500000"), "Menabung"));

        assertTrue(thrown.getMessage().toLowerCase().contains("tidak aktif"),
                "Pesan error harus menyebutkan produk tidak aktif");
        verify(rekeningRepository, never()).save(any());
    }

    // ========== CLOSE ACCOUNT ==========

    @Test
    @DisplayName("closeAccount - harus set statusActive menjadi false")
    void closeAccount_shouldSetStatusActiveFalse() {
        // Arrange
        Rekening rekening = new Rekening();
        rekening.setNomorRekening("54300000101");
        rekening.setStatusActive(true);

        when(rekeningRepository.findById(1L)).thenReturn(Optional.of(rekening));
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        rekeningService.closeAccount(1L);

        // Assert
        assertFalse(rekening.isStatusActive(),
                "Setelah close, statusActive harus false");
        verify(rekeningRepository).save(rekening);
    }

    // ========== GET ACCOUNT BY ID ==========

    @Test
    @DisplayName("getAccountById - harus throw exception jika tidak ditemukan")
    void getAccountById_shouldThrow_whenNotFound() {
        // Arrange
        when(rekeningRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> rekeningService.getAccountById(999L));
    }
}
