package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
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
@DisplayName("TellerWithdrawalService Unit Tests")
class TellerWithdrawalServiceTest {

    @Mock private RekeningRepository rekeningRepository;
    @Mock private TransaksiRepository transaksiRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private TellerWithdrawalService withdrawalService;

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
    @DisplayName("withdraw - berhasil mengurangi saldo")
    void withdraw_success() {
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(activeRekening));
        when(transaksiRepository.nextSeq()).thenReturn(1L);
        when(userRepository.findByUsername("teller1")).thenReturn(Optional.empty());
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transaksiRepository.save(any(Transaksi.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = withdrawalService.withdraw("54300000101",
                new BigDecimal("300000"), "Penarikan Tunai", null, "teller1");

        assertNotNull(result);
        assertEquals("T2000001", result.nomorTransaksi());
        assertEquals(new BigDecimal("700000"), result.saldoBaru());
        assertEquals(new BigDecimal("700000"), activeRekening.getSaldo());

        ArgumentCaptor<Transaksi> captor = ArgumentCaptor.forClass(Transaksi.class);
        verify(transaksiRepository).save(captor.capture());
        assertEquals(TipeTransaksi.WITHDRAWAL, captor.getValue().getTipe());
    }

    @Test
    @DisplayName("withdraw - throw jika jumlah null")
    void withdraw_shouldThrow_whenJumlahNull() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54300000101", null, "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika jumlah <= 0")
    void withdraw_shouldThrow_whenJumlahZeroOrNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54300000101",
                        BigDecimal.ZERO, "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika keterangan blank")
    void withdraw_shouldThrow_whenKeteranganBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54300000101",
                        new BigDecimal("50000"), "  ", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika keterangan > 500 karakter")
    void withdraw_shouldThrow_whenKeteranganTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalService.withdraw("54300000101",
                        new BigDecimal("50000"), "A".repeat(501), null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika saldo tidak cukup")
    void withdraw_shouldThrow_whenSaldoInsufficient() {
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(activeRekening));

        assertThrows(IllegalStateException.class,
                () -> withdrawalService.withdraw("54300000101",
                        new BigDecimal("2000000"), "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika rekening tidak ditemukan")
    void withdraw_shouldThrow_whenRekeningNotFound() {
        when(rekeningRepository.findByNomorRekeningForUpdate("99999"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> withdrawalService.withdraw("99999",
                        new BigDecimal("50000"), "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("withdraw - throw jika rekening tidak aktif")
    void withdraw_shouldThrow_whenRekeningInactive() {
        activeRekening.setStatusActive(false);
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(activeRekening));

        assertThrows(IllegalStateException.class,
                () -> withdrawalService.withdraw("54300000101",
                        new BigDecimal("50000"), "Tarik", null, "teller1"));
    }

    @Test
    @DisplayName("getActiveRekening - berhasil")
    void getActiveRekening_success() {
        when(rekeningRepository.findByNomorRekening("54300000101"))
                .thenReturn(Optional.of(activeRekening));
        Rekening r = withdrawalService.getActiveRekening("54300000101");
        assertNotNull(r);
        assertTrue(r.isStatusActive());
    }

    @Test
    @DisplayName("getActiveRekening - throw jika tidak aktif")
    void getActiveRekening_shouldThrow_whenInactive() {
        activeRekening.setStatusActive(false);
        when(rekeningRepository.findByNomorRekening("54300000101"))
                .thenReturn(Optional.of(activeRekening));
        assertThrows(IllegalStateException.class,
                () -> withdrawalService.getActiveRekening("54300000101"));
    }
}
