package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RekeningService - Additional Tests")
class RekeningServiceAdditionalTest {

    @Mock private RekeningRepository rekeningRepository;
    @Mock private NasabahRepository nasabahRepository;
    @Mock private ProdukTabunganRepository produkTabunganRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private RekeningService rekeningService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    // ==================== listAccounts ====================

    @Test
    @DisplayName("listAccounts - tanpa search, kembalikan berdasarkan status")
    void listAccounts_noSearch() {
        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        when(rekeningRepository.findByStatus("ACTIVE")).thenReturn(List.of(r));

        List<Rekening> result = rekeningService.listAccounts(null, "ACTIVE");

        assertEquals(1, result.size());
        verify(rekeningRepository).findByStatus("ACTIVE");
    }

    @Test
    @DisplayName("listAccounts - dengan search, gunakan metode search")
    void listAccounts_withSearch() {
        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        when(rekeningRepository.search("budi", "ACTIVE")).thenReturn(List.of(r));

        List<Rekening> result = rekeningService.listAccounts("budi", "ACTIVE");

        assertEquals(1, result.size());
        verify(rekeningRepository).search("budi", "ACTIVE");
    }

    @Test
    @DisplayName("listAccounts - search blank string, fallback ke findByStatus")
    void listAccounts_blankSearch() {
        when(rekeningRepository.findByStatus("ACTIVE")).thenReturn(List.of());

        List<Rekening> result = rekeningService.listAccounts("  ", "ACTIVE");

        verify(rekeningRepository).findByStatus("ACTIVE");
        assertNotNull(result);
    }

    // ==================== listEligibleCustomers ====================

    @Test
    @DisplayName("listEligibleCustomers - tanpa query, kembalikan ACTIVE")
    void listEligibleCustomers_noQuery() {
        Nasabah n = new Nasabah();
        n.setStatus(NasabahStatus.ACTIVE);
        when(nasabahRepository.findByStatus(NasabahStatus.ACTIVE)).thenReturn(List.of(n));

        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, null);

        assertEquals(1, result.size());
        verify(nasabahRepository).findByStatus(NasabahStatus.ACTIVE);
    }

    @Test
    @DisplayName("listEligibleCustomers - dengan query, gunakan searchActive")
    void listEligibleCustomers_withQuery() {
        Nasabah n = new Nasabah();
        n.setNamaSesuaiIdentitas("Budi");
        when(nasabahRepository.searchActiveForAccountOpen(NasabahStatus.ACTIVE, "budi"))
                .thenReturn(List.of(n));

        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, "budi");

        assertEquals(1, result.size());
        verify(nasabahRepository).searchActiveForAccountOpen(NasabahStatus.ACTIVE, "budi");
    }

    @Test
    @DisplayName("listEligibleCustomers - query blank, fallback ke findByStatus")
    void listEligibleCustomers_blankQuery() {
        when(nasabahRepository.findByStatus(NasabahStatus.ACTIVE)).thenReturn(List.of());

        List<Nasabah> result = rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, "  ");

        verify(nasabahRepository).findByStatus(NasabahStatus.ACTIVE);
        assertNotNull(result);
    }

    // ==================== listActiveProducts ====================

    @Test
    @DisplayName("listActiveProducts - mengembalikan daftar produk aktif")
    void listActiveProducts_success() {
        ProdukTabungan p = new ProdukTabungan();
        p.setNamaProduk("Tabungan Wadiah");
        p.setAktif(true);
        when(produkTabunganRepository.findActiveProducts()).thenReturn(List.of(p));

        List<ProdukTabungan> result = rekeningService.listActiveProducts();

        assertEquals(1, result.size());
        verify(produkTabunganRepository).findActiveProducts();
    }
}
