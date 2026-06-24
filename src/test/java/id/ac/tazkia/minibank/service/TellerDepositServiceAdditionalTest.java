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

@DisplayName("TellerDepositService - Additional Integration Tests")
class TellerDepositServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private TellerDepositService depositService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private TransaksiRepository transaksiRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private NasabahRepository nasabahRepository;

    private Rekening seededRekening;
    private Nasabah seededNasabah;

    @BeforeEach
    void setUp() {
        seededNasabah = new Nasabah();
        seededNasabah.setCif("C9910001");
        seededNasabah.setNik("9910001234567890");
        seededNasabah.setNamaSesuaiIdentitas("Budi Setiawan");
        seededNasabah.setStatus(NasabahStatus.ACTIVE);
        seededNasabah = nasabahRepository.save(seededNasabah);

        seededRekening = new Rekening();
        seededRekening.setNomorRekening("54399100101");
        seededRekening.setStatusActive(true);
        seededRekening.setSaldo(new BigDecimal("1000000"));
        seededRekening.setNasabah(seededNasabah);
        seededRekening.setCifNasabah(seededNasabah.getCif());
        seededRekening.setNamaNasabah(seededNasabah.getNamaSesuaiIdentitas());
        seededRekening.setProduk("Tabungan Wadiah");
        seededRekening = rekeningRepository.save(seededRekening);
    }

    @Test
    @DisplayName("deposit - nominal minimum (tepat batas bawah IDR 10.000) - berhasil")
    void deposit_nominalMinimum_success() {
        var result = depositService.deposit("54399100101",
                new BigDecimal("10000"), "Setoran Tunai Batas Bawah", null, "teller1");

        assertNotNull(result);
        assertNotNull(result.nomorTransaksi());
        assertEquals(0, new BigDecimal("1010000").compareTo(result.saldoBaru()));
    }

    @Test
    @DisplayName("deposit - nominal sangat besar - berhasil")
    void deposit_nominalSangatBesar_success() {
        BigDecimal largeAmount = new BigDecimal("100000000000"); // 100 miliar
        var result = depositService.deposit("54399100101",
                largeAmount, "Setoran Tunai Besar", null, "teller1");

        assertNotNull(result);
        assertEquals(0, new BigDecimal("100001000000").compareTo(result.saldoBaru()));
    }

    @Test
    @DisplayName("deposit - nominal bernilai nol - throw IllegalArgumentException")
    void deposit_nominalNol_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                depositService.deposit("54399100101", BigDecimal.ZERO, "Setoran Nol", null, "teller1"));
        assertTrue(ex.getMessage().contains("Minimal setoran adalah IDR 10.000."));
    }

    @Test
    @DisplayName("deposit - nominal negatif - throw IllegalArgumentException")
    void deposit_nominalNegatif_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                depositService.deposit("54399100101", new BigDecimal("-50000"), "Setoran Negatif", null, "teller1"));
        assertTrue(ex.getMessage().contains("Minimal setoran adalah IDR 10.000."));
    }

    @Test
    @DisplayName("deposit - keterangan null - throw IllegalArgumentException")
    void deposit_keteranganNull_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                depositService.deposit("54399100101", new BigDecimal("50000"), null, null, "teller1"));
        assertTrue(ex.getMessage().contains("Keterangan wajib diisi."));
    }

    @Test
    @DisplayName("deposit - keterangan kosong (hanya whitespace) - throw IllegalArgumentException")
    void deposit_keteranganKosongWhitespace_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                depositService.deposit("54399100101", new BigDecimal("50000"), "   ", null, "teller1"));
        assertTrue(ex.getMessage().contains("Keterangan wajib diisi."));
    }

    @Test
    @DisplayName("deposit - keterangan tepat batas maksimum (500 karakter) - berhasil")
    void deposit_keteranganBatasMaksimum_success() {
        String exact500 = "K".repeat(500);
        var result = depositService.deposit("54399100101",
                new BigDecimal("50000"), exact500, null, "teller1");

        assertNotNull(result);
        
        // Verify transaction description is saved correctly
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertEquals(exact500, t.getKeterangan());
    }

    @Test
    @DisplayName("deposit - noReferensi null - berhasil disimpan sebagai null")
    void deposit_noReferensiNull_success() {
        var result = depositService.deposit("54399100101",
                new BigDecimal("50000"), "Setoran Ref Null", null, "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertNull(t.getNoReferensi());
    }

    @Test
    @DisplayName("deposit - noReferensi kosong / blank - berhasil disimpan sebagai null")
    void deposit_noReferensiKosong_success() {
        var result = depositService.deposit("54399100101",
                new BigDecimal("50000"), "Setoran Ref Kosong", "   ", "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertNull(t.getNoReferensi());
    }

    @Test
    @DisplayName("deposit - noReferensi tepat batas maksimum (100 karakter) - berhasil")
    void deposit_noReferensiBatasMaksimum_success() {
        String exact100 = "R".repeat(100);
        var result = depositService.deposit("54399100101",
                new BigDecimal("50000"), "Setoran Ref Maksimum", exact100, "teller1");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertEquals(exact100, t.getNoReferensi());
    }

    @Test
    @DisplayName("deposit - saldo rekening null - saldoSebelum dianggap 0 dan berhasil")
    void deposit_saldoNull_treatedAsZero() {
        // Mock RekeningRepository to return a Rekening with null balance
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(depositService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockRekening = new Rekening();
            ReflectionTestUtils.setField(mockRekening, "id", seededRekening.getId());
            mockRekening.setNomorRekening(seededRekening.getNomorRekening());
            mockRekening.setStatusActive(true);
            mockRekening.setSaldo(null); // Explicitly null
            mockRekening.setNamaNasabah("Mock Nasabah");
            mockRekening.setProduk("Mock Tabungan");
            mockRekening.setCifNasabah("C9910001");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399100101"))
                    .thenReturn(Optional.of(mockRekening));
            when(mockRekeningRepository.save(any(Rekening.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            var result = depositService.deposit("54399100101",
                    new BigDecimal("50000"), "Setoran Saldo Null", null, "teller1");

            assertNotNull(result);
            // Saldo sebelum (0) + jumlah (50000) = 50000
            assertEquals(0, new BigDecimal("50000").compareTo(result.saldoBaru()));
        } finally {
            ReflectionTestUtils.setField(depositService, "rekeningRepository", this.rekeningRepository);
        }
    }

    @Test
    @DisplayName("deposit - namaNasabah null dan produk null - namaRekening disimpan dengan format default ' - '")
    void deposit_namaNasabahAndProdukNull_success() {
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(depositService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockRekening = new Rekening();
            ReflectionTestUtils.setField(mockRekening, "id", seededRekening.getId());
            mockRekening.setNomorRekening(seededRekening.getNomorRekening());
            mockRekening.setStatusActive(true);
            mockRekening.setSaldo(BigDecimal.ZERO);
            mockRekening.setNamaNasabah(null); // Null
            mockRekening.setProduk(null); // Null
            mockRekening.setCifNasabah("C9910001");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399100101"))
                    .thenReturn(Optional.of(mockRekening));
            when(mockRekeningRepository.save(any(Rekening.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            var result = depositService.deposit("54399100101",
                    new BigDecimal("50000"), "Setoran Nama Produk Null", null, "teller1");

            assertNotNull(result);
            Transaksi t = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                    .findFirst().orElseThrow();
            assertEquals(" - ", t.getNamaRekening());
        } finally {
            ReflectionTestUtils.setField(depositService, "rekeningRepository", this.rekeningRepository);
        }
    }

    @Test
    @DisplayName("deposit - user ditemukan dengan fullName valid - menyimpan fullName ke transaksi")
    void deposit_userWithFullName_success() {
        // Seed a user in the userRepository
        User u = new User();
        u.setUsername("teller_hebat");
        u.setPassword("password");
        u.setEmail("teller@bank.com");
        u.setFullName("Petugas Teller Hebat");
        u.setApproved(true);
        u.setEnabled(true);
        u.setCreatedAt(LocalDateTime.now());
        userRepository.save(u);

        var result = depositService.deposit("54399100101",
                new BigDecimal("50000"), "Setoran User FullName", null, "teller_hebat");

        assertNotNull(result);
        Transaksi t = transaksiRepository.findAll().stream()
                .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                .findFirst().orElseThrow();
        assertEquals("teller_hebat", t.getProcessedByUsername());
        assertEquals("Petugas Teller Hebat", t.getProcessedByFullName());
    }

    @Test
    @DisplayName("deposit - user ditemukan dengan fullName null - menyimpan usernameLogin ke transaksi")
    void deposit_userWithFullNameNull_success() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(depositService, "userRepository", mockUserRepository);

        try {
            User u = new User();
            u.setUsername("teller_null_name");
            u.setFullName(null); // Mocked as null

            when(mockUserRepository.findByUsername("teller_null_name")).thenReturn(Optional.of(u));

            var result = depositService.deposit("54399100101",
                    new BigDecimal("50000"), "Setoran User FullName Null", null, "teller_null_name");

            assertNotNull(result);
            Transaksi t = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                    .findFirst().orElseThrow();
            assertEquals("teller_null_name", t.getProcessedByUsername());
            assertEquals("teller_null_name", t.getProcessedByFullName());
        } finally {
            ReflectionTestUtils.setField(depositService, "userRepository", this.userRepository);
        }
    }

    @Test
    @DisplayName("deposit - user ditemukan dengan fullName kosong / blank - menyimpan usernameLogin ke transaksi")
    void deposit_userWithFullNameBlank_success() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(depositService, "userRepository", mockUserRepository);

        try {
            User u = new User();
            u.setUsername("teller_blank_name");
            u.setFullName("   "); // Mocked as blank

            when(mockUserRepository.findByUsername("teller_blank_name")).thenReturn(Optional.of(u));

            var result = depositService.deposit("54399100101",
                    new BigDecimal("50000"), "Setoran User FullName Blank", null, "teller_blank_name");

            assertNotNull(result);
            Transaksi t = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getNomorTransaksi().equals(result.nomorTransaksi()))
                    .findFirst().orElseThrow();
            assertEquals("teller_blank_name", t.getProcessedByUsername());
            assertEquals("teller_blank_name", t.getProcessedByFullName());
        } finally {
            ReflectionTestUtils.setField(depositService, "userRepository", this.userRepository);
        }
    }

    @Test
    @DisplayName("getActiveRekening - rekening tidak ditemukan - throw EntityNotFoundException")
    void getActiveRekening_notFound_shouldThrow() {
        assertThrows(EntityNotFoundException.class, () ->
                depositService.getActiveRekening("999999999"));
    }
}
