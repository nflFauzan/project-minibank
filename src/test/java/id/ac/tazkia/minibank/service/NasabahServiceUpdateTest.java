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
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NasabahService - updateNasabah Tests")
class NasabahServiceUpdateTest {

    @Mock
    private NasabahRepository nasabahRepository;

    @InjectMocks
    private NasabahService nasabahService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("updateNasabah - berhasil update semua field")
    void updateNasabah_success() {
        Nasabah existing = new Nasabah();
        existing.setId(1L);
        existing.setNik("1111111111111111");
        existing.setNamaSesuaiIdentitas("Lama");
        existing.setStatus(NasabahStatus.ACTIVE);

        Nasabah form = new Nasabah();
        form.setNik("2222222222222222");
        form.setNamaSesuaiIdentitas("Baru");
        form.setNamaIbuKandung("Ibu Baru");
        form.setJenisKelamin("L");
        form.setTempatLahir("Jakarta");
        form.setAgama("Islam");
        form.setPenduduk("WNI");
        form.setStatusPernikahan("Menikah");
        form.setNegara("Indonesia");
        form.setEmail("baru@email.com");
        form.setNoHp("08111222333");
        form.setPekerjaan("Karyawan");
        form.setNamaPerusahaan("PT Baru");
        form.setJabatan("Staff");
        form.setPenghasilanPerBulan("5000000");
        form.setAlamatIdentitas("Jl Identitas");
        form.setProvinsiIdentitas("Jawa Barat");
        form.setKotaIdentitas("Bandung");
        form.setKecamatanIdentitas("Cibiru");
        form.setKelurahanIdentitas("Palasari");
        form.setRtIdentitas("001");
        form.setRwIdentitas("002");
        form.setKodePosIdentitas("40614");
        form.setAlamatDomisili("Jl Domisili");
        form.setProvinsiDomisili("DKI Jakarta");
        form.setKotaDomisili("Jakarta Selatan");
        form.setKecamatanDomisili("Kebayoran");
        form.setKelurahanDomisili("Gandaria");
        form.setRtDomisili("003");
        form.setRwDomisili("004");
        form.setKodePosDomisili("12210");

        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        nasabahService.updateNasabah(1L, form);

        assertEquals("2222222222222222", existing.getNik());
        assertEquals("Baru", existing.getNamaSesuaiIdentitas());
        assertEquals("Ibu Baru", existing.getNamaIbuKandung());
        assertEquals("baru@email.com", existing.getEmail());
        assertEquals("08111222333", existing.getNoHp());
        assertEquals("Jl Identitas", existing.getAlamatIdentitas());
        assertEquals("Jl Domisili", existing.getAlamatDomisili());
        // Status tidak berubah
        assertEquals(NasabahStatus.ACTIVE, existing.getStatus());
        verify(nasabahRepository).save(existing);
    }

    @Test
    @DisplayName("updateNasabah - throw EntityNotFoundException jika tidak ditemukan")
    void updateNasabah_shouldThrow_whenNotFound() {
        when(nasabahRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> nasabahService.updateNasabah(999L, new Nasabah()));

        verify(nasabahRepository, never()).save(any());
    }

    @Test
    @DisplayName("createNasabah - CIF null menghasilkan generate dari null max")
    void createNasabah_shouldGenerateCif_whenMaxCifNull() {
        Nasabah form = new Nasabah();
        form.setNik("1234567890123456");
        form.setNamaSesuaiIdentitas("Test User");
        form.setCif(""); // CIF kosong

        when(nasabahRepository.findMaxCif()).thenReturn(null); // tidak ada data
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        Nasabah result = nasabahService.createNasabah(form);

        assertEquals("C0000001", result.getCif(), "Jika max CIF null, harus generate C0000001");
    }

    @Test
    @DisplayName("createNasabah - CIF tidak sesuai format fallback ke next=1")
    void createNasabah_shouldFallback_whenMaxCifInvalidFormat() {
        Nasabah form = new Nasabah();
        form.setNik("1234567890123456");
        form.setNamaSesuaiIdentitas("Test User");
        form.setCif(""); // CIF kosong

        when(nasabahRepository.findMaxCif()).thenReturn("INVALID"); // format tidak sesuai
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        Nasabah result = nasabahService.createNasabah(form);

        assertEquals("C0000001", result.getCif(), "Jika max CIF format tidak valid, fallback ke C0000001");
    }

    @Test
    @DisplayName("createNasabah - CIF yang hanya 5 karakter (bukan 8) fallback ke next=1")
    void createNasabah_shouldFallback_whenMaxCifWrongLength() {
        Nasabah form = new Nasabah();
        form.setNik("1234567890123456");
        form.setNamaSesuaiIdentitas("Test User");
        form.setCif(""); // CIF kosong

        when(nasabahRepository.findMaxCif()).thenReturn("C0001"); // panjang != 8
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        Nasabah result = nasabahService.createNasabah(form);

        assertEquals("C0000001", result.getCif(), "CIF dengan panjang != 8 harus fallback ke C0000001");
    }
}
