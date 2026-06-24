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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TellerTransferService - Additional Integration Tests")
class TellerTransferServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private TellerTransferService transferService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private TransaksiRepository transaksiRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private NasabahRepository nasabahRepository;

    private Rekening seededSumber;
    private Rekening seededTujuan;
    private Nasabah nasabahSumber;
    private Nasabah nasabahTujuan;

    @BeforeEach
    void setUp() {
        nasabahSumber = new Nasabah();
        nasabahSumber.setCif("C9930001");
        nasabahSumber.setNik("9930001234567890");
        nasabahSumber.setNamaSesuaiIdentitas("Hasan Al-Banna");
        nasabahSumber.setStatus(NasabahStatus.ACTIVE);
        nasabahSumber = nasabahRepository.save(nasabahSumber);

        nasabahTujuan = new Nasabah();
        nasabahTujuan.setCif("C9930002");
        nasabahTujuan.setNik("9930002234567890");
        nasabahTujuan.setNamaSesuaiIdentitas("Fatimah Az-Zahra");
        nasabahTujuan.setStatus(NasabahStatus.ACTIVE);
        nasabahTujuan = nasabahRepository.save(nasabahTujuan);

        seededSumber = new Rekening();
        seededSumber.setNomorRekening("54399300101");
        seededSumber.setStatusActive(true);
        seededSumber.setSaldo(new BigDecimal("1000000"));
        seededSumber.setNasabah(nasabahSumber);
        seededSumber.setCifNasabah(nasabahSumber.getCif());
        seededSumber.setNamaNasabah(nasabahSumber.getNamaSesuaiIdentitas());
        seededSumber.setProduk("Tabungan Wadiah");
        seededSumber = rekeningRepository.save(seededSumber);

        seededTujuan = new Rekening();
        seededTujuan.setNomorRekening("54399300202");
        seededTujuan.setStatusActive(true);
        seededTujuan.setSaldo(new BigDecimal("500000"));
        seededTujuan.setNasabah(nasabahTujuan);
        seededTujuan.setCifNasabah(nasabahTujuan.getCif());
        seededTujuan.setNamaNasabah(nasabahTujuan.getNamaSesuaiIdentitas());
        seededTujuan.setProduk("Tabungan Mudharabah");
        seededTujuan = rekeningRepository.save(seededTujuan);
    }

    @Test
    @DisplayName("transfer - nominal sama dengan saldo sumber - berhasil (saldo sumber menjadi 0)")
    void transfer_nominalSameAsSaldo_success() {
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("1000000"), "Transfer Semua Saldo", null, "teller1");

        assertNotNull(groupId);

        Rekening sumber = rekeningRepository.findByNomorRekening("54399300101").orElseThrow();
        Rekening tujuan = rekeningRepository.findByNomorRekening("54399300202").orElseThrow();
        assertEquals(0, BigDecimal.ZERO.compareTo(sumber.getSaldo()));
        assertEquals(0, new BigDecimal("1500000").compareTo(tujuan.getSaldo()));

        // Verifikasi mutasi transaksi (debit & kredit)
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());

        Transaksi out = txList.stream().filter(t -> t.getNomorRekening().equals("54399300101")).findFirst().orElseThrow();
        Transaksi in = txList.stream().filter(t -> t.getNomorRekening().equals("54399300202")).findFirst().orElseThrow();

        assertEquals(TipeTransaksi.TRANSFER, out.getTipe());
        assertEquals(new BigDecimal("1000000"), out.getJumlah());
        assertEquals(new BigDecimal("1000000"), out.getSaldoSebelum());
        assertEquals(BigDecimal.ZERO, out.getSaldoSesudah());
        assertEquals("Transfer ke 54399300202 - Transfer Semua Saldo", out.getKeterangan());

        assertEquals(TipeTransaksi.TRANSFER, in.getTipe());
        assertEquals(new BigDecimal("1000000"), in.getJumlah());
        assertEquals(new BigDecimal("500000"), in.getSaldoSebelum());
        assertEquals(new BigDecimal("1500000"), in.getSaldoSesudah());
        assertEquals("Transfer dari 54399300101 - Transfer Semua Saldo", in.getKeterangan());
    }

    @Test
    @DisplayName("transfer - nominal sangat besar - berhasil")
    void transfer_nominalSangatBesar_success() {
        seededSumber.setSaldo(new BigDecimal("1000000000000")); // 1 Triliun
        rekeningRepository.save(seededSumber);

        BigDecimal largeAmount = new BigDecimal("700000000000"); // 700 Miliar
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                largeAmount, "Transfer Nominal Besar", null, "teller1");

        assertNotNull(groupId);
        Rekening sumber = rekeningRepository.findByNomorRekening("54399300101").orElseThrow();
        Rekening tujuan = rekeningRepository.findByNomorRekening("54399300202").orElseThrow();

        assertEquals(0, new BigDecimal("300000000000").compareTo(sumber.getSaldo()));
        assertEquals(0, new BigDecimal("700000500000").compareTo(tujuan.getSaldo()));
    }

    @Test
    @DisplayName("transfer - rekening sumber null - throw IllegalArgumentException")
    void transfer_rekeningSumberNull_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer(null, "54399300202", new BigDecimal("50000"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Rekening sumber wajib diisi."));
    }

    @Test
    @DisplayName("transfer - rekening tujuan null - throw IllegalArgumentException")
    void transfer_rekeningTujuanNull_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer("54399300101", null, new BigDecimal("50000"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Rekening tujuan wajib diisi."));
    }

    @Test
    @DisplayName("transfer - rekening tujuan kosong / blank - throw IllegalArgumentException")
    void transfer_rekeningTujuanBlank_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer("54399300101", "   ", new BigDecimal("50000"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Rekening tujuan wajib diisi."));
    }

    @Test
    @DisplayName("transfer - nominal negatif - throw IllegalArgumentException")
    void transfer_nominalNegatif_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer("54399300101", "54399300202", new BigDecimal("-50000"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Jumlah transfer harus lebih dari 0."));
    }

    @Test
    @DisplayName("transfer - nominal desimal melebihi 2 angka belakang koma - throw IllegalArgumentException")
    void transfer_nominalDecimalTooLong_shouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                transferService.transfer("54399300101", "54399300202", new BigDecimal("50000.123"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Jumlah tidak boleh lebih dari 2 angka desimal."));
    }

    @Test
    @DisplayName("transfer - rekening sumber tidak ditemukan - throw EntityNotFoundException")
    void transfer_rekeningSumberNotFound_shouldThrow() {
        assertThrows(EntityNotFoundException.class, () ->
                transferService.transfer("999999999", "54399300202", new BigDecimal("50000"), "Transfer", null, "teller1"));
    }

    @Test
    @DisplayName("transfer - rekening tujuan tidak ditemukan - throw EntityNotFoundException")
    void transfer_rekeningTujuanNotFound_shouldThrow() {
        assertThrows(EntityNotFoundException.class, () ->
                transferService.transfer("54399300101", "999999999", new BigDecimal("50000"), "Transfer", null, "teller1"));
    }

    @Test
    @DisplayName("transfer - rekening sumber tidak aktif - throw IllegalStateException")
    void transfer_rekeningSumberInactive_shouldThrow() {
        seededSumber.setStatusActive(false);
        rekeningRepository.save(seededSumber);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                transferService.transfer("54399300101", "54399300202", new BigDecimal("50000"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Rekening sumber tidak aktif."));
    }

    @Test
    @DisplayName("transfer - rekening tujuan tidak aktif - throw IllegalStateException")
    void transfer_rekeningTujuanInactive_shouldThrow() {
        seededTujuan.setStatusActive(false);
        rekeningRepository.save(seededTujuan);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                transferService.transfer("54399300101", "54399300202", new BigDecimal("50000"), "Transfer", null, "teller1"));
        assertTrue(ex.getMessage().contains("Rekening tujuan tidak aktif."));
    }

    @Test
    @DisplayName("transfer - saldo sumber null - treated as ZERO dan throw IllegalStateException (saldo tidak mencukupi)")
    void transfer_saldoSumberNull_shouldThrow() {
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(transferService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockSumber = new Rekening();
            ReflectionTestUtils.setField(mockSumber, "id", seededSumber.getId());
            mockSumber.setNomorRekening(seededSumber.getNomorRekening());
            mockSumber.setStatusActive(true);
            mockSumber.setSaldo(null); // Null
            mockSumber.setCifNasabah("C9930001");

            Rekening mockTujuan = new Rekening();
            ReflectionTestUtils.setField(mockTujuan, "id", seededTujuan.getId());
            mockTujuan.setNomorRekening(seededTujuan.getNomorRekening());
            mockTujuan.setStatusActive(true);
            mockTujuan.setSaldo(new BigDecimal("500000"));
            mockTujuan.setCifNasabah("C9930002");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399300101")).thenReturn(Optional.of(mockSumber));
            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399300202")).thenReturn(Optional.of(mockTujuan));

            IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                    transferService.transfer("54399300101", "54399300202", new BigDecimal("50000"), "Transfer", null, "teller1"));
            assertTrue(ex.getMessage().contains("Saldo tidak mencukupi."));
        } finally {
            ReflectionTestUtils.setField(transferService, "rekeningRepository", this.rekeningRepository);
        }
    }

    @Test
    @DisplayName("transfer - saldo tujuan null - treated as ZERO dan berhasil bertambah")
    void transfer_saldoTujuanNull_success() {
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(transferService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockSumber = new Rekening();
            ReflectionTestUtils.setField(mockSumber, "id", seededSumber.getId());
            mockSumber.setNomorRekening(seededSumber.getNomorRekening());
            mockSumber.setStatusActive(true);
            mockSumber.setSaldo(new BigDecimal("1000000"));
            mockSumber.setCifNasabah("C9930001");

            Rekening mockTujuan = new Rekening();
            ReflectionTestUtils.setField(mockTujuan, "id", seededTujuan.getId());
            mockTujuan.setNomorRekening(seededTujuan.getNomorRekening());
            mockTujuan.setStatusActive(true);
            mockTujuan.setSaldo(null); // Null
            mockTujuan.setCifNasabah("C9930002");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399300101")).thenReturn(Optional.of(mockSumber));
            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399300202")).thenReturn(Optional.of(mockTujuan));
            when(mockRekeningRepository.save(any(Rekening.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UUID groupId = transferService.transfer("54399300101", "54399300202", new BigDecimal("50000"), "Transfer", null, "teller1");

            assertNotNull(groupId);
            assertEquals(0, new BigDecimal("950000").compareTo(mockSumber.getSaldo()));
            assertEquals(0, new BigDecimal("50000").compareTo(mockTujuan.getSaldo()));
        } finally {
            ReflectionTestUtils.setField(transferService, "rekeningRepository", this.rekeningRepository);
        }
    }

    @Test
    @DisplayName("transfer - noReferensi null - berhasil disimpan sebagai null")
    void transfer_noReferensiNull_success() {
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("50000"), "Transfer Ref Null", null, "teller1");

        assertNotNull(groupId);
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());
        assertNull(txList.get(0).getNoReferensi());
        assertNull(txList.get(1).getNoReferensi());
    }

    @Test
    @DisplayName("transfer - noReferensi kosong / blank - berhasil disimpan sebagai null")
    void transfer_noReferensiKosong_success() {
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("50000"), "Transfer Ref Kosong", "   ", "teller1");

        assertNotNull(groupId);
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());
        assertNull(txList.get(0).getNoReferensi());
        assertNull(txList.get(1).getNoReferensi());
    }

    @Test
    @DisplayName("transfer - noReferensi trimming - berhasil disimpan setelah di-trim")
    void transfer_noReferensiTrim_success() {
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("50000"), "Transfer Ref Trim", "  REF999  ", "teller1");

        assertNotNull(groupId);
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());
        assertEquals("REF999", txList.get(0).getNoReferensi());
        assertEquals("REF999", txList.get(1).getNoReferensi());
    }

    @Test
    @DisplayName("transfer - keteranganTambahan null - sukses tersimpan tanpa suffix keterangan")
    void transfer_keteranganTambahanNull_success() {
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("50000"), null, null, "teller1");

        assertNotNull(groupId);
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());
        assertEquals("Transfer ke 54399300202", txList.get(0).getKeterangan());
        assertEquals("Transfer dari 54399300101", txList.get(1).getKeterangan());
    }

    @Test
    @DisplayName("transfer - usernameLogin null atau blank - processedBy diisi '-'")
    void transfer_usernameLoginNullOrBlank_success() {
        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("50000"), "Transfer No User", null, "   ");

        assertNotNull(groupId);
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());
        assertEquals("-", txList.get(0).getProcessedByUsername());
        assertEquals("-", txList.get(0).getProcessedByFullName());
        assertEquals("-", txList.get(0).getProcessedBy());
    }

    @Test
    @DisplayName("transfer - user ditemukan dengan fullName valid - menyimpan fullName ke transaksi")
    void transfer_userWithFullName_success() {
        User u = new User();
        u.setUsername("teller_hebat3");
        u.setPassword("password");
        u.setEmail("teller3@bank.com");
        u.setFullName("Petugas Teller Hebat 3");
        u.setApproved(true);
        u.setEnabled(true);
        u.setCreatedAt(LocalDateTime.now());
        userRepository.save(u);

        UUID groupId = transferService.transfer(
                "54399300101", "54399300202",
                new BigDecimal("50000"), "Transfer User FullName", null, "teller_hebat3");

        assertNotNull(groupId);
        List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
        assertEquals(2, txList.size());
        assertEquals("teller_hebat3", txList.get(0).getProcessedByUsername());
        assertEquals("Petugas Teller Hebat 3", txList.get(0).getProcessedByFullName());
    }

    @Test
    @DisplayName("transfer - user ditemukan dengan fullName null - menyimpan usernameLogin ke transaksi")
    void transfer_userWithFullNameNull_success() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(transferService, "userRepository", mockUserRepository);

        try {
            User u = new User();
            u.setUsername("teller_null_name3");
            u.setFullName(null);

            when(mockUserRepository.findByUsername("teller_null_name3")).thenReturn(Optional.of(u));

            UUID groupId = transferService.transfer(
                    "54399300101", "54399300202",
                    new BigDecimal("50000"), "Transfer User FullName Null", null, "teller_null_name3");

            assertNotNull(groupId);
            List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
            assertEquals(2, txList.size());
            assertEquals("teller_null_name3", txList.get(0).getProcessedByUsername());
            assertEquals("teller_null_name3", txList.get(0).getProcessedByFullName());
        } finally {
            ReflectionTestUtils.setField(transferService, "userRepository", this.userRepository);
        }
    }

    @Test
    @DisplayName("transfer - user ditemukan dengan fullName kosong / blank - menyimpan usernameLogin ke transaksi")
    void transfer_userWithFullNameBlank_success() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(transferService, "userRepository", mockUserRepository);

        try {
            User u = new User();
            u.setUsername("teller_blank_name3");
            u.setFullName("   ");

            when(mockUserRepository.findByUsername("teller_blank_name3")).thenReturn(Optional.of(u));

            UUID groupId = transferService.transfer(
                    "54399300101", "54399300202",
                    new BigDecimal("50000"), "Transfer User FullName Blank", null, "teller_blank_name3");

            assertNotNull(groupId);
            List<Transaksi> txList = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId);
            assertEquals(2, txList.size());
            assertEquals("teller_blank_name3", txList.get(0).getProcessedByUsername());
            assertEquals("teller_blank_name3", txList.get(0).getProcessedByFullName());
        } finally {
            ReflectionTestUtils.setField(transferService, "userRepository", this.userRepository);
        }
    }

    @Test
    @DisplayName("transfer - namaNasabah null dan produk null - namaRekening disimpan dengan format default ' - '")
    void transfer_namaNasabahAndProdukNull_success() {
        RekeningRepository mockRekeningRepository = mock(RekeningRepository.class);
        ReflectionTestUtils.setField(transferService, "rekeningRepository", mockRekeningRepository);

        try {
            Rekening mockSumber = new Rekening();
            ReflectionTestUtils.setField(mockSumber, "id", seededSumber.getId());
            mockSumber.setNomorRekening(seededSumber.getNomorRekening());
            mockSumber.setStatusActive(true);
            mockSumber.setSaldo(new BigDecimal("1000000"));
            mockSumber.setNamaNasabah(null);
            mockSumber.setProduk(null);
            mockSumber.setCifNasabah("C9930001");

            Rekening mockTujuan = new Rekening();
            ReflectionTestUtils.setField(mockTujuan, "id", seededTujuan.getId());
            mockTujuan.setNomorRekening(seededTujuan.getNomorRekening());
            mockTujuan.setStatusActive(true);
            mockTujuan.setSaldo(new BigDecimal("500000"));
            mockTujuan.setNamaNasabah(null);
            mockTujuan.setProduk(null);
            mockTujuan.setCifNasabah("C9930002");

            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399300101")).thenReturn(Optional.of(mockSumber));
            when(mockRekeningRepository.findByNomorRekeningForUpdate("54399300202")).thenReturn(Optional.of(mockTujuan));
            when(mockRekeningRepository.save(any(Rekening.class))).thenAnswer(invocation -> invocation.getArgument(0));

            UUID groupId = transferService.transfer("54399300101", "54399300202", new BigDecimal("50000"), "Transfer Nama Produk Null", null, "teller1");

            assertNotNull(groupId);
            List<Transaksi> txList = transaksiRepository.findAll().stream()
                    .filter(tx -> tx.getGroupId().equals(groupId))
                    .toList();
            assertEquals(2, txList.size());
            assertEquals(" - ", txList.get(0).getNamaRekening());
            assertEquals(" - ", txList.get(1).getNamaRekening());
        } finally {
            ReflectionTestUtils.setField(transferService, "rekeningRepository", this.rekeningRepository);
        }
    }
}
