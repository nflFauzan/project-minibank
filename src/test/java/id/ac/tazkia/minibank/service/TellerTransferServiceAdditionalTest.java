package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
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
@DisplayName("TellerTransferService - Additional Tests")
class TellerTransferServiceAdditionalTest {

    @Mock private RekeningRepository rekeningRepository;
    @Mock private TransaksiRepository transaksiRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private TellerTransferService transferService;

    private Rekening sumber;
    private Rekening tujuan;

    @BeforeEach
    void setUp() {
        // sumber > tujuan lexicographically sehingga order lock terbalik
        sumber = new Rekening();
        sumber.setNomorRekening("Z9999999901");
        sumber.setStatusActive(true);
        sumber.setSaldo(new BigDecimal("2000000"));
        sumber.setNamaNasabah("Budi");
        sumber.setCifNasabah("C0000001");
        sumber.setProduk("Tabungan Wadiah");

        tujuan = new Rekening();
        tujuan.setNomorRekening("A1111111101");
        tujuan.setStatusActive(true);
        tujuan.setSaldo(new BigDecimal("300000"));
        tujuan.setNamaNasabah("Aisyah");
        tujuan.setCifNasabah("C0000002");
        tujuan.setProduk("Tabungan Mudharabah");
    }

    @Test
    @DisplayName("transfer - berhasil saat sumber > tujuan lexicographically (lock order terbalik)")
    void transfer_success_reverseOrder() {
        // sumber "Z99..." > tujuan "A11...", jadi first=tujuan, second=sumber
        when(rekeningRepository.findByNomorRekeningForUpdate("A1111111101"))
                .thenReturn(Optional.of(tujuan));
        when(rekeningRepository.findByNomorRekeningForUpdate("Z9999999901"))
                .thenReturn(Optional.of(sumber));
        when(transaksiRepository.nextSeq()).thenReturn(10L, 11L);
        when(userRepository.findByUsername("teller1")).thenReturn(Optional.empty());
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transaksiRepository.save(any(Transaksi.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID groupId = transferService.transfer(
                "Z9999999901", "A1111111101",
                new BigDecimal("500000"), "bayar", "REF001", "teller1");

        assertNotNull(groupId);
        assertEquals(new BigDecimal("1500000"), sumber.getSaldo());
        assertEquals(new BigDecimal("800000"), tujuan.getSaldo());
        verify(transaksiRepository, times(2)).save(any(Transaksi.class));
    }

    @Test
    @DisplayName("transfer - berhasil dengan fullName dari userRepository")
    void transfer_withFullName() {
        sumber.setNomorRekening("54300000101");
        tujuan.setNomorRekening("54300000202");
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumber));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuan));

        sumber.setNomorRekening("54300000101");
        tujuan.setNomorRekening("54300000202");

        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumber));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuan));

        User u = new User();
        u.setUsername("teller1");
        u.setFullName("Teller Satu");
        when(userRepository.findByUsername("teller1")).thenReturn(Optional.of(u));
        when(transaksiRepository.nextSeq()).thenReturn(1L, 2L);
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transaksiRepository.save(any(Transaksi.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID groupId = transferService.transfer(
                "54300000101", "54300000202",
                new BigDecimal("100000"), null, null, "teller1");

        assertNotNull(groupId);
    }

    @Test
    @DisplayName("transfer - throw jika rekening first tidak ditemukan")
    void transfer_throwIfFirstNotFound() {
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> transferService.transfer(
                        "54300000101", "54300000202",
                        new BigDecimal("100000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - saldo sumber null dianggap 0")
    void transfer_sumberSaldoNull_treatedAsZero() {
        sumber.setNomorRekening("54300000101");
        tujuan.setNomorRekening("54300000202");
        sumber.setSaldo(null);

        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumber));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuan));

        // saldo null = 0, transfer 1000 akan gagal (saldo tidak cukup)
        assertThrows(IllegalStateException.class,
                () -> transferService.transfer(
                        "54300000101", "54300000202",
                        new BigDecimal("1000"), null, null, "teller1"));
    }

    @Test
    @DisplayName("transfer - saldo tujuan null dianggap 0")
    void transfer_tujuanSaldoNull_treatedAsZero() {
        sumber.setNomorRekening("54300000101");
        tujuan.setNomorRekening("54300000202");
        tujuan.setSaldo(null);

        when(rekeningRepository.findByNomorRekeningForUpdate("54300000101"))
                .thenReturn(Optional.of(sumber));
        when(rekeningRepository.findByNomorRekeningForUpdate("54300000202"))
                .thenReturn(Optional.of(tujuan));
        when(transaksiRepository.nextSeq()).thenReturn(1L, 2L);
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(rekeningRepository.save(any(Rekening.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transaksiRepository.save(any(Transaksi.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID groupId = transferService.transfer(
                "54300000101", "54300000202",
                new BigDecimal("500000"), "Test", "REF", "teller1");

        assertNotNull(groupId);
        // saldo tujuan: null (=0) + 500000 = 500000
        assertEquals(new BigDecimal("500000"), tujuan.getSaldo());
    }
}
