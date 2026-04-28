package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TellerDepositService Unit Tests")
class TellerDepositServiceTest {

    @Mock private RekeningRepository rekeningRepository;
    @Mock private TransaksiRepository transaksiRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private TellerDepositService depositService;

    private Rekening activeRekening;

    @BeforeEach
    void setUp() {
        activeRekening = new Rekening();
        activeRekening.setNomorRekening("54300000101");
        activeRekening.setStatusActive(true);
        activeRekening.setSaldo(new BigDecimal("1000000"));
        activeRekening.setNamaNasabah("Budi Santoso");
        activeRekening.setCifNasabah("C0000001");
        activeRekening.setProduk("Tabungan Wadiah");
    }

    @Test
    @DisplayName("deposit - berhasil menambah saldo dan menyimpan transaksi")
    void deposit_success() {
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(activeRekening));
        when(transaksiRepository.nextSeq()).thenReturn(1L);
        when(userRepository.findByUsername("teller1")).thenReturn(Optional.empty());
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transaksiRepository.save(any(Transaksi.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = depositService.deposit("54300000101",
                new BigDecimal("500000"), "Setoran Tunai", null, "teller1");

        assertNotNull(result);
        assertEquals("T1000001", result.nomorTransaksi());
        assertEquals(new BigDecimal("1500000"), result.saldoBaru());
        assertEquals(new BigDecimal("1500000"), activeRekening.getSaldo());

        ArgumentCaptor<Transaksi> captor = ArgumentCaptor.forClass(Transaksi.class);
        verify(transaksiRepository).save(captor.capture());
        Transaksi saved = captor.getValue();
        assertEquals(TipeTransaksi.DEPOSIT, saved.getTipe());
        assertEquals("TELLER", saved.getChannel());
        assertEquals(new BigDecimal("500000"), saved.getJumlah());
        assertEquals(new BigDecimal("1000000"), saved.getSaldoSebelum());
        assertEquals(new BigDecimal("1500000"), saved.getSaldoSesudah());
    }

    @Test
    @DisplayName("deposit - throw exception jika jumlah null")
    void deposit_shouldThrow_whenJumlahNull() {
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54300000101", null, "Setoran", null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika jumlah < 10.000")
    void deposit_shouldThrow_whenJumlahBelowMinimum() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54300000101",
                        new BigDecimal("5000"), "Setoran", null, "teller1"));
        assertTrue(ex.getMessage().contains("10.000"));
    }

    @Test
    @DisplayName("deposit - throw exception jika keterangan blank")
    void deposit_shouldThrow_whenKeteranganBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54300000101",
                        new BigDecimal("50000"), "", null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika keterangan > 500 karakter")
    void deposit_shouldThrow_whenKeteranganTooLong() {
        String longText = "A".repeat(501);
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54300000101",
                        new BigDecimal("50000"), longText, null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika noReferensi > 100 karakter")
    void deposit_shouldThrow_whenNoReferensiTooLong() {
        String longRef = "R".repeat(101);
        assertThrows(IllegalArgumentException.class,
                () -> depositService.deposit("54300000101",
                        new BigDecimal("50000"), "Setoran", longRef, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika rekening tidak ditemukan")
    void deposit_shouldThrow_whenRekeningNotFound() {
        when(rekeningRepository.findByNomorRekeningForUpdate("99999"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> depositService.deposit("99999",
                        new BigDecimal("50000"), "Setoran", null, "teller1"));
    }

    @Test
    @DisplayName("deposit - throw exception jika rekening tidak aktif")
    void deposit_shouldThrow_whenRekeningInactive() {
        activeRekening.setStatusActive(false);
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(activeRekening));

        assertThrows(IllegalStateException.class,
                () -> depositService.deposit("54300000101",
                        new BigDecimal("50000"), "Setoran", null, "teller1"));
    }

    @Test
    @DisplayName("getActiveRekening - berhasil jika rekening aktif")
    void getActiveRekening_success() {
        when(rekeningRepository.findByNomorRekening("54300000101"))
                .thenReturn(Optional.of(activeRekening));

        Rekening r = depositService.getActiveRekening("54300000101");
        assertNotNull(r);
        assertTrue(r.isStatusActive());
    }

    @Test
    @DisplayName("getActiveRekening - throw jika rekening tidak aktif")
    void getActiveRekening_shouldThrow_whenInactive() {
        activeRekening.setStatusActive(false);
        when(rekeningRepository.findByNomorRekening("54300000101"))
                .thenReturn(Optional.of(activeRekening));

        assertThrows(IllegalStateException.class,
                () -> depositService.getActiveRekening("54300000101"));
    }
}
