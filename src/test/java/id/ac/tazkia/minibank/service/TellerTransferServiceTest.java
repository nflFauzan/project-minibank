package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TellerTransferService Unit Tests")
class TellerTransferServiceTest {

    @Mock private RekeningRepository rekeningRepository;
    @Mock private TransaksiRepository transaksiRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private TellerTransferService transferService;

    private Rekening sumberRekening;
    private Rekening tujuanRekening;

    @BeforeEach
    void setUp() {
        sumberRekening = new Rekening();
        sumberRekening.setNomorRekening("54300000101");
        sumberRekening.setStatusActive(true);
        sumberRekening.setSaldo(new BigDecimal("1000000"));
        sumberRekening.setNamaNasabah("Budi");
        sumberRekening.setCifNasabah("C0000001");
        sumberRekening.setProduk("Tabungan Wadiah");

        tujuanRekening = new Rekening();
        tujuanRekening.setNomorRekening("54300000202");
        tujuanRekening.setStatusActive(true);
        tujuanRekening.setSaldo(new BigDecimal("500000"));
        tujuanRekening.setNamaNasabah("Aisyah");
        tujuanRekening.setCifNasabah("C0000002");
        tujuanRekening.setProduk("Tabungan Mudharabah");
    }

    @Test
    @DisplayName("transfer - berhasil, saldo sumber berkurang dan tujuan bertambah")
    void transfer_success() {
        // "54300000101" < "54300000202", so first=sumber, second=tujuan
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumberRekening));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuanRekening));
        when(transaksiRepository.nextSeq()).thenReturn(1L, 2L);
        when(userRepository.findByUsername("teller1")).thenReturn(Optional.empty());
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transaksiRepository.save(any(Transaksi.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID groupId = transferService.transfer(
                "54300000101", "54300000202",
                new BigDecimal("200000"), "Bayar hutang", null, "teller1");

        assertNotNull(groupId);
        assertEquals(new BigDecimal("800000"), sumberRekening.getSaldo());
        assertEquals(new BigDecimal("700000"), tujuanRekening.getSaldo());
        verify(transaksiRepository, times(2)).save(any(Transaksi.class));
        verify(rekeningRepository, times(2)).save(any(Rekening.class));
    }

    @Test
    @DisplayName("transfer - throw jika rekening sumber blank")
    void transfer_shouldThrow_whenSumberBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("", "54300000202",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika rekening tujuan blank")
    void transfer_shouldThrow_whenTujuanBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54300000101", "",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika sumber sama dengan tujuan")
    void transfer_shouldThrow_whenSameAccount() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54300000101", "54300000101",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika jumlah null")
    void transfer_shouldThrow_whenJumlahNull() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54300000101", "54300000202",
                        null, null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika jumlah 0")
    void transfer_shouldThrow_whenJumlahZero() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54300000101", "54300000202",
                        BigDecimal.ZERO, null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika lebih dari 2 desimal")
    void transfer_shouldThrow_whenTooManyDecimals() {
        assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer("54300000101", "54300000202",
                        new BigDecimal("100.123"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika saldo tidak cukup")
    void transfer_shouldThrow_whenSaldoInsufficient() {
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumberRekening));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuanRekening));

        assertThrows(IllegalStateException.class,
                () -> transferService.transfer("54300000101", "54300000202",
                        new BigDecimal("5000000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika sumber tidak aktif")
    void transfer_shouldThrow_whenSumberInactive() {
        sumberRekening.setStatusActive(false);
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumberRekening));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuanRekening));

        assertThrows(IllegalStateException.class,
                () -> transferService.transfer("54300000101", "54300000202",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - throw jika tujuan tidak aktif")
    void transfer_shouldThrow_whenTujuanInactive() {
        tujuanRekening.setStatusActive(false);
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumberRekening));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuanRekening));

        assertThrows(IllegalStateException.class,
                () -> transferService.transfer("54300000101", "54300000202",
                        new BigDecimal("100000"), null, null, "teller1"));
    }
}
