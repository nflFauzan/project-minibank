package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("RekeningService - Additional Integration Tests")
class RekeningServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private RekeningService rekeningService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;
    @Autowired private UserRepository userRepository;

    private Nasabah activeNasabah;
    private ProdukTabungan activeProduk;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();

        activeNasabah = new Nasabah();
        activeNasabah.setCif("C9990001");
        activeNasabah.setNik("9990001234567890");
        activeNasabah.setNamaSesuaiIdentitas("Hidayatullah");
        activeNasabah.setEmail("hidayat@email.com");
        activeNasabah.setNoHp("08123456789");
        activeNasabah.setAlamatDomisili("Jl. Raya Bogor No. 10");
        activeNasabah.setStatus(NasabahStatus.ACTIVE);
        activeNasabah = nasabahRepository.save(activeNasabah);

        activeProduk = new ProdukTabungan();
        activeProduk.setKodeProduk("REK99");
        activeProduk.setNamaProduk("Tabungan Wadiah Khusus");
        activeProduk.setAktif(true);
        activeProduk = produkTabunganRepository.save(activeProduk);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        ReflectionTestUtils.setField(rekeningService, "userRepository", userRepository);
        ReflectionTestUtils.setField(rekeningService, "produkTabunganRepository", produkTabunganRepository);
    }

    @Test
    @DisplayName("listAccounts - dengan parameter search - sukses")
    void listAccounts_withSearch_success() {
        List<Rekening> result = rekeningService.listAccounts("Hidayatullah", "ACTIVE");
        assertNotNull(result);
    }

    @Test
    @DisplayName("listAccounts - tanpa parameter search - sukses")
    void listAccounts_withoutSearch_success() {
        List<Rekening> result = rekeningService.listAccounts(null, "ACTIVE");
        assertNotNull(result);
    }

    @Test
    @DisplayName("listEligibleCustomers - dengan parameter search - sukses")
    void listEligibleCustomers_withQ_success() {
        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, "Hidayat");
        assertNotNull(result);
    }

    @Test
    @DisplayName("listEligibleCustomers - tanpa parameter search - sukses")
    void listEligibleCustomers_withoutQ_success() {
        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, null);
        assertNotNull(result);
    }

    @Test
    @DisplayName("getNasabahActiveById - nasabah tidak ditemukan - throw EntityNotFoundException")
    void getNasabahActiveById_notFound_shouldThrow() {
        assertThrows(EntityNotFoundException.class, () ->
                rekeningService.getNasabahActiveById(999999L));
    }

    @Test
    @DisplayName("listActiveProducts - sukses")
    void listActiveProducts_success() {
        List<ProdukTabungan> result = rekeningService.listActiveProducts();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("openAccount - produk aktif bernilai null - throw IllegalStateException")
    void openAccount_produkActiveNull_shouldThrow() {
        ProdukTabunganRepository mockProdukTabunganRepository = mock(ProdukTabunganRepository.class);
        ReflectionTestUtils.setField(rekeningService, "produkTabunganRepository", mockProdukTabunganRepository);

        ProdukTabungan nullActive = new ProdukTabungan();
        ReflectionTestUtils.setField(nullActive, "id", 9991L);
        nullActive.setKodeProduk("NA01");
        nullActive.setNamaProduk("Produk Null Active");
        nullActive.setAktif(null); // Explicit null

        when(mockProdukTabunganRepository.findById(9991L)).thenReturn(Optional.of(nullActive));

        assertThrows(IllegalStateException.class, () ->
                rekeningService.openAccount(activeNasabah.getId(), 9991L,
                        new BigDecimal("100000"), "Menabung"));
    }

    @Test
    @DisplayName("openAccount - kode produk bernilai null - throw IllegalStateException")
    void openAccount_produkKodeNull_shouldThrow() {
        ProdukTabunganRepository mockProdukTabunganRepository = mock(ProdukTabunganRepository.class);
        ReflectionTestUtils.setField(rekeningService, "produkTabunganRepository", mockProdukTabunganRepository);

        ProdukTabungan nullKode = new ProdukTabungan();
        ReflectionTestUtils.setField(nullKode, "id", 9992L);
        nullKode.setKodeProduk(null); // Explicit null
        nullKode.setNamaProduk("Produk Null Kode");
        nullKode.setAktif(true);

        when(mockProdukTabunganRepository.findById(9992L)).thenReturn(Optional.of(nullKode));

        assertThrows(IllegalStateException.class, () ->
                rekeningService.openAccount(activeNasabah.getId(), 9992L,
                        new BigDecimal("100000"), "Menabung"));
    }

    @Test
    @DisplayName("openAccount - kode produk pendek (<= 2 char) - sukses tanpa substring")
    void openAccount_produkKodeShort_success() {
        ProdukTabungan shortKode = new ProdukTabungan();
        shortKode.setKodeProduk("99"); // Tepat 2 char
        shortKode.setNamaProduk("Produk Kode Pendek");
        shortKode.setAktif(true);
        shortKode = produkTabunganRepository.save(shortKode);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_user", "password", Collections.emptyList())
        );

        Rekening r = rekeningService.openAccount(activeNasabah.getId(), shortKode.getId(),
                new BigDecimal("100000"), "Menabung");

        assertNotNull(r);
        assertTrue(r.getNomorRekening().endsWith("99"));
    }

    @Test
    @DisplayName("openAccount - auth bernilai null - CS diset 'UNKNOWN'")
    void openAccount_authNull_shouldSetCSUnknown() {
        SecurityContextHolder.clearContext(); // Auth null

        Rekening r = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("100000"), "Menabung");

        assertNotNull(r);
        assertEquals("UNKNOWN", r.getPetugasCs());
    }

    @Test
    @DisplayName("openAccount - CS tidak terdaftar di database - CS diset usernameLogin")
    void openAccount_userNotFoundInDb_shouldSetCSUsername() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("ghost_cs", "password", Collections.emptyList())
        );

        Rekening r = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("100000"), "Menabung");

        assertNotNull(r);
        assertEquals("ghost_cs", r.getPetugasCs());
    }

    @Test
    @DisplayName("openAccount - CS di database dengan fullName null - CS diset usernameLogin")
    void openAccount_userFullNameNull_shouldSetCSUsername() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(rekeningService, "userRepository", mockUserRepository);

        User u = new User();
        u.setUsername("cs_null_name");
        u.setFullName(null); // Explicit null

        when(mockUserRepository.findByUsername("cs_null_name")).thenReturn(Optional.of(u));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_null_name", "password", Collections.emptyList())
        );

        Rekening r = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("100000"), "Menabung");

        assertNotNull(r);
        assertEquals("cs_null_name", r.getPetugasCs());
    }

    @Test
    @DisplayName("openAccount - CS di database dengan fullName kosong / blank - CS diset usernameLogin")
    void openAccount_userFullNameBlank_shouldSetCSUsername() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(rekeningService, "userRepository", mockUserRepository);

        User u = new User();
        u.setUsername("cs_blank_name");
        u.setFullName("   "); // Blank

        when(mockUserRepository.findByUsername("cs_blank_name")).thenReturn(Optional.of(u));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_blank_name", "password", Collections.emptyList())
        );

        Rekening r = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("100000"), "Menabung");

        assertNotNull(r);
        assertEquals("cs_blank_name", r.getPetugasCs());
    }

    @Test
    @DisplayName("openAccount - CS di database dengan fullName valid - CS diset fullName")
    void openAccount_userFullNameValid_shouldSetCSFullName() {
        UserRepository mockUserRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(rekeningService, "userRepository", mockUserRepository);

        User u = new User();
        u.setUsername("cs_valid_name");
        u.setFullName("Petugas Customer Service Kece");

        when(mockUserRepository.findByUsername("cs_valid_name")).thenReturn(Optional.of(u));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("cs_valid_name", "password", Collections.emptyList())
        );

        Rekening r = rekeningService.openAccount(activeNasabah.getId(), activeProduk.getId(),
                new BigDecimal("100000"), "Menabung");

        assertNotNull(r);
        assertEquals("Petugas Customer Service Kece", r.getPetugasCs());
    }
}
