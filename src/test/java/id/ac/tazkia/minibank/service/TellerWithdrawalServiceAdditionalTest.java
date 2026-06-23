package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TellerWithdrawalService - Additional Integration Tests")
class TellerWithdrawalServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private TellerWithdrawalService withdrawalService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private TransaksiRepository transaksiRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private NasabahRepository nasabahRepository;

    private Rekening seededRekening;
    private Nasabah seededNasabah;

    @BeforeEach
    void setUp() {
        seededNasabah = new Nasabah();
        seededNasabah.setCif("C9920001");
        seededNasabah.setNik("9920001234567890");
        seededNasabah.setNamaSesuaiIdentitas("Aisyah Humaira");
        seededNasabah.setStatus(NasabahStatus.ACTIVE);
        seededNasabah = nasabahRepository.save(seededNasabah);

        seededRekening = new Rekening();
        seededRekening.setNomorRekening("54399200101");
        seededRekening.setStatusActive(true);
        seededRekening.setSaldo(new BigDecimal("1000000"));
        seededRekening.setNasabah(seededNasabah);
        seededRekening.setCifNasabah(seededNasabah.getCif());
        seededRekening.setNamaNasabah(seededNasabah.getNamaSesuaiIdentitas());
        seededRekening.setProduk("Tabungan Mudharabah");
        seededRekening = rekeningRepository.save(seededRekening);
    }

    @Test
    @DisplayName("withdraw - nominal sama dengan saldo - berhasil (saldo menjadi 0)")
    void withdraw_nominalSameAsSaldo_success() {
        var result = withdrawalService.withdraw("54399200101",
                new BigDecimal("1000000"), "Tarik Semua Saldo", null, "teller1");

        assertNotNull(result);
        assertEquals(0, BigDecimal.ZERO.compareTo(result.saldoBaru()));

        Rekening after = rekeningRepository.findByNomorRekening("54399200101").orElseThrow();
        assertEquals(0, BigDecimal.ZERO.compareTo(after.getSaldo()));
    }

    @Test
    @DisplayName("withdraw - nominal sangat besar - berhasil")
    void withdraw_nominalSangatBesar_success() {
        // Tambahkan saldo rekening terlebih dahulu
        seededRekening.setSaldo(new BigDecimal("1000000000000")); // 1 Triliun
        rekeningRepository.save(seededRekening);

        BigDecimal largeAmount = new BigDecimal("800000000000"); // 800 Miliar
        var result = withdrawalService.withdraw("54399200101",
                largeAmount, "Tarik Nominal Besar", null, "teller1");

        assertNotNull(result);
        assertEquals(0, new BigDecimal("200000000000").compareTo(result.saldoBaru()));
    }

    @Test
    @DisplayName("withdraw - nominal negatif - throw IllegalArgumentException")
    void withdraw_nominalNegatif_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                withdrawalService.withdraw("54399200101", new BigDecimal("-50000"), "Tarik Negatif", null, "teller1"));
        assertTrue(ex.getMessage().contains("Jumlah penarikan tidak boleh 0."));
    }

    @Test
    @DisplayName("withdraw - keterangan null - throw IllegalArgumentException")
    void withdraw_keteranganNull_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                withdrawalService.withdraw("54399200101", new BigDecimal("50000"), null, null, "teller1"));
        assertTrue(ex.getMessage().contains("Keterangan wajib diisi."));
    }

    @Test
    @DisplayName("withdraw - keterangan kosong (hanya whitespace) - throw IllegalArgumentException")
    void withdraw_keteranganKosongWhitespace_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                withdrawalService.withdraw("54399200101", new BigDecimal("50000"), "   ", null, "teller1"));
        assertTrue(ex.getMessage().contains("Keterangan wajib diisi."));
    }

    @Test
    @DisplayName("withdraw - keterangan tepat batas maksimum (500 karakter) - berhasil")
    void withdraw_keteranganBatasMaksimum_success() {
        String exact500 = "W".repeat(500);
        var result = withdrawalService.withdraw("54399200101",
                new BigDecimal("50000"), exact500, null, "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertEquals(exact500, t.getKeterangan());
    }

    @Test
    @DisplayName("withdraw - keterangan lebih dari 500 karakter - throw IllegalArgumentException")
    void withdraw_keteranganTooLong_shouldThrow() {
        String tooLong = "W".repeat(501);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                withdrawalService.withdraw("54399200101", new BigDecimal("50000"), tooLong, null, "teller1"));
        assertTrue(ex.getMessage().contains("Keterangan maksimal 500 karakter."));
    }

    @Test
    @DisplayName("withdraw - noReferensi null - berhasil disimpan sebagai null")
    void withdraw_noReferensiNull_success() {
        var result = withdrawalService.withdraw("54399200101",
                new BigDecimal("50000"), "Tarik Ref Null", null, "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertNull(t.getNoReferensi());
    }

    @Test
    @DisplayName("withdraw - noReferensi kosong / blank - berhasil disimpan sebagai null")
    void withdraw_noReferensiKosong_success() {
        var result = withdrawalService.withdraw("54399200101",
                new BigDecimal("50000"), "Tarik Ref Kosong", "   ", "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertNull(t.getNoReferensi());
    }

    @Test
    @DisplayName("withdraw - noReferensi tepat batas maksimum (100 karakter) - berhasil")
    void withdraw_noReferensiBatasMaksimum_success() {
        String exact100 = "R".repeat(100);
        var result = withdrawalService.withdraw("54399200101",
                new BigDecimal("50000"), "Tarik Ref Maksimum", exact100, "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertEquals(exact100, t.getNoReferensi());
    }

    @Test
    @DisplayName("withdraw - noReferensi lebih dari 100 karakter - throw IllegalArgumentException")
    void withdraw_noReferensiTooLong_shouldThrow() {
        String tooLong = "R".repeat(101);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                withdrawalService.withdraw("54399200101", new BigDecimal("50000"), "Tarik", tooLong, "teller1"));
        assertTrue(ex.getMessage().contains("Nomor referensi maksimal 100 karakter."));
    }

    @Test
    @DisplayName("withdraw - rekening tidak aktif - throw IllegalStateException")
    void withdraw_rekeningInactive_shouldThrow() {
        seededRekening.setStatusActive(false);
        rekeningRepository.save(seededRekening);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                withdrawalService.withdraw("54399200101", new BigDecimal("50000"), "Tarik Inactive", null, "teller1"));
        assertTrue(ex.getMessage().contains("Rekening tidak aktif"));
    }

    @Test
    @DisplayName("withdraw - saldo rekening null - treated as ZERO dan throw IllegalStateException (saldo tidak cukup)")
    void withdraw_saldoNull_shouldThrow() {
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(withdrawalService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockRekening = new Rekening();
            ReflectionTestUtils.setField(mockRekening, "id", seededRekening.getId());
            mockRekening.setNomorRekening(seededRekening.getNomorRekening());
            mockRekening.setStatusActive(true);
            mockRekening.setSaldo(null); // Explicitly null
            mockRekening.setNamaNasabah("Mock Nasabah");
            mockRekening.setProduk("Mock Tabungan");
            mockRekening.setCifNasabah("C9920001");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399200101"))
                    .thenReturn(Optional.of(mockRekening));

            IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                    withdrawalService.withdraw("54399200101", new BigDecimal("50000"), "Tarik Saldo Null", null, "teller1"));
            assertTrue(ex.getMessage().contains("Saldo tidak cukup."));
        } finally {
            ReflectionTestUtils.setField(withdrawalService, "rekeningRepository", this.rekeningRepository);
        }
    }

    @Test
    @DisplayName("withdraw - namaNasabah null dan produk null - namaRekening disimpan dengan format default ' - '")
    void withdraw_namaNasabahAndProdukNull_success() {
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(withdrawalService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockRekening = new Rekening();
            ReflectionTestUtils.setField(mockRekening, "id", seededRekening.getId());
            mockRekening.setNomorRekening(seededRekening.getNomorRekening());
            mockRekening.setStatusActive(true);
            mockRekening.setSaldo(new BigDecimal("100000"));
            mockRekening.setNamaNasabah(null);
            mockRekening.setProduk(null);
            mockRekening.setCifNasabah("C9920001");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399200101"))
                    .thenReturn(Optional.of(mockRekening));
            when(mockRekeningRepository.save(any(Rekening.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            var result = withdrawalService.withdraw("54399200101",
                    new BigDecimal("50000"), "Tarik Nama Produk Null", null, "teller1");

            assertNotNull(result);
            Transaksi t = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                    .findFirst().orElseThrow();
            assertEquals(" - ", t.getNamaRekening());
        } finally {
            ReflectionTestUtils.setField(withdrawalService, "rekeningRepository", this.rekeningRepository);
        }
    }

    @Test
    @DisplayName("withdraw - user ditemukan dengan fullName valid - menyimpan fullName ke transaksi")
    void withdraw_userWithFullName_success() {
        User u = new User();
        u.setUsername("teller_hebat2");
        u.setPassword("password");
        u.setEmail("teller2@bank.com");
        u.setFullName("Petugas Teller Hebat 2");
        u.setApproved(true);
        u.setEnabled(true);
        u.setCreatedAt(LocalDateTime.now());
        userRepository.save(u);

        var result = withdrawalService.withdraw("54399200101",
                new BigDecimal("50000"), "Tarik User FullName", null, "teller_hebat2");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertEquals("teller_hebat2", t.getProcessedByUsername());
        assertEquals("Petugas Teller Hebat 2", t.getProcessedByFullName());
    }

    @Test
    @DisplayName("withdraw - user ditemukan dengan fullName null - menyimpan usernameLogin ke transaksi")
    void withdraw_userWithFullNameNull_success() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(withdrawalService, "userRepository", mockUserRepository);

        try {
            User u = new User();
            u.setUsername("teller_null_name2");
            u.setFullName(null);

            when(mockUserRepository.findByUsername("teller_null_name2")).thenReturn(Optional.of(u));

            var result = withdrawalService.withdraw("54399200101",
                    new BigDecimal("50000"), "Tarik User FullName Null", null, "teller_null_name2");

            assertNotNull(result);
            Transaksi t = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                    .findFirst().orElseThrow();
            assertEquals("teller_null_name2", t.getProcessedByUsername());
            assertEquals("teller_null_name2", t.getProcessedByFullName());
        } finally {
            ReflectionTestUtils.setField(withdrawalService, "userRepository", this.userRepository);
        }
    }

    @Test
    @DisplayName("withdraw - user ditemukan dengan fullName kosong / blank - menyimpan usernameLogin ke transaksi")
    void withdraw_userWithFullNameBlank_success() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(withdrawalService, "userRepository", mockUserRepository);

        try {
            User u = new User();
            u.setUsername("teller_blank_name2");
            u.setFullName("   ");

            when(mockUserRepository.findByUsername("teller_blank_name2")).thenReturn(Optional.of(u));

            var result = withdrawalService.withdraw("54399200101",
                    new BigDecimal("50000"), "Tarik User FullName Blank", null, "teller_blank_name2");

            assertNotNull(result);
            Transaksi t = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                    .findFirst().orElseThrow();
            assertEquals("teller_blank_name2", t.getProcessedByUsername());
            assertEquals("teller_blank_name2", t.getProcessedByFullName());
        } finally {
            ReflectionTestUtils.setField(withdrawalService, "userRepository", this.userRepository);
        }
    }

    @Test
    @DisplayName("getActiveRekening - rekening tidak ditemukan - throw EntityNotFoundException")
    void getActiveRekening_notFound_shouldThrow() {
        assertThrows(EntityNotFoundException.class, () ->
                withdrawalService.getActiveRekening("999999999"));
    }

    @Test
    @DisplayName("getActiveRekening - rekening tidak aktif - throw IllegalStateException")
    void getActiveRekening_inactive_shouldThrow() {
        seededRekening.setStatusActive(false);
        rekeningRepository.save(seededRekening);

        assertThrows(IllegalStateException.class, () ->
                withdrawalService.getActiveRekening("54399200101"));
    }
}
