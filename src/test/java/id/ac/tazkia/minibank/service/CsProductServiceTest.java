package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CsProductService Unit Tests")
class CsProductServiceTest {

    @Mock private ProdukTabunganRepository produkTabunganRepository;
    @Mock private EntityManager em;

    @InjectMocks
    private CsProductService csProductService;

    private ProdukTabungan existingProduk;

    @BeforeEach
    void setUp() {
        existingProduk = new ProdukTabungan();
        existingProduk.setId(1L);
        existingProduk.setKodeProduk("TAB01");
        existingProduk.setNamaProduk("Tabungan Wadiah");
        existingProduk.setDeskripsiSingkat("Tabungan berbasis Wadiah");
        existingProduk.setJenisAkad("WADIAH");
        existingProduk.setSetoranAwalMinimum(new BigDecimal("100000"));
        existingProduk.setAktif(true);
    }

    // ==================== akadOptions ====================

    @Test
    @DisplayName("akadOptions - mengembalikan 8 pilihan akad")
    void akadOptions_shouldReturn8Options() {
        List<CsProductService.AkadOption> opts = csProductService.akadOptions();
        assertEquals(8, opts.size());
        assertTrue(opts.stream().anyMatch(o -> o.value().equals("WADIAH")));
        assertTrue(opts.stream().anyMatch(o -> o.value().equals("MUDHARABAH")));
        assertTrue(opts.stream().anyMatch(o -> o.value().equals("MUSYARAKAH")));
    }

    // ==================== create ====================

    @SuppressWarnings("unchecked")
    private void mockKodeProdukNotExists() {
        TypedQuery<Long> countQ = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(countQ);
        when(countQ.setParameter(anyString(), any())).thenReturn(countQ);
        when(countQ.getSingleResult()).thenReturn(0L);
    }

    @Test
    @DisplayName("create - berhasil membuat produk baru")
    void create_success() {
        mockKodeProdukNotExists();
        when(produkTabunganRepository.save(any(ProdukTabungan.class))).thenAnswer(inv -> inv.getArgument(0));

        ProdukTabungan result = csProductService.create(
                "NEWPROD", "Produk Baru", "Deskripsi", "WADIAH", new BigDecimal("50000"));

        assertNotNull(result);
        assertEquals("NEWPROD", result.getKodeProduk());
        assertEquals("Produk Baru", result.getNamaProduk());
        assertEquals("WADIAH", result.getJenisAkad());
        assertTrue(result.getAktif());
        verify(produkTabunganRepository).save(any(ProdukTabungan.class));
    }

    @Test
    @DisplayName("create - kode produk di-normalize ke uppercase")
    void create_normalizeKode() {
        mockKodeProdukNotExists();
        when(produkTabunganRepository.save(any(ProdukTabungan.class))).thenAnswer(inv -> inv.getArgument(0));

        ProdukTabungan result = csProductService.create(
                "  tabwad  ", "Tabungan Wadiah", "Desc", "wadiah", new BigDecimal("50000"));

        assertEquals("TABWAD", result.getKodeProduk());
        assertEquals("WADIAH", result.getJenisAkad());
    }

    @Test
    @DisplayName("create - throw jika kode produk blank")
    void create_throwIfKodeBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("", "Produk", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika nama produk blank")
    void create_throwIfNamaBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "  ", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika jenis akad blank")
    void create_throwIfAkadBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika jenis akad tidak valid")
    void create_throwIfAkadInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "RIBA", new BigDecimal("50000")));
    }

    @Test
    @DisplayName("create - throw jika setoran awal null")
    void create_throwIfSetoranNull() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH", null));
    }

    @Test
    @DisplayName("create - throw jika setoran awal nol atau negatif")
    void create_throwIfSetoranZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH", BigDecimal.ZERO));
    }

    @Test
    @DisplayName("create - throw jika setoran awal lebih dari maksimum")
    void create_throwIfSetoranTooLarge() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH",
                        new BigDecimal("1000000001")));
    }

    @Test
    @DisplayName("create - throw jika setoran awal memiliki lebih dari 2 desimal")
    void create_throwIfSetoranTooManyDecimals() {
        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH",
                        new BigDecimal("50000.001")));
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("create - throw jika kode produk sudah ada")
    void create_throwIfKodeDuplikat() {
        TypedQuery<Long> countQ = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(countQ);
        when(countQ.setParameter(anyString(), any())).thenReturn(countQ);
        when(countQ.getSingleResult()).thenReturn(1L); // kode sudah ada

        assertThrows(IllegalArgumentException.class, () ->
                csProductService.create("KD01", "Produk", "Desc", "WADIAH", new BigDecimal("50000")));
    }

    // ==================== update ====================

    @Test
    @DisplayName("update - berhasil update produk")
    void update_success() {
        mockKodeProdukNotExists();
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(existingProduk));
        when(produkTabunganRepository.save(any(ProdukTabungan.class))).thenAnswer(inv -> inv.getArgument(0));

        ProdukTabungan result = csProductService.update(1L,
                "NEWKODE", "Nama Baru", "Deskripsi Baru", "MUDHARABAH", new BigDecimal("200000"));

        assertNotNull(result);
        assertEquals("NEWKODE", result.getKodeProduk());
        assertEquals("Nama Baru", result.getNamaProduk());
        assertEquals("MUDHARABAH", result.getJenisAkad());
    }

    @Test
    @DisplayName("update - throw jika produk tidak ditemukan")
    void update_throwIfNotFound() {
        when(produkTabunganRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                csProductService.update(999L, "KD01", "Nama", "Desc", "WADIAH",
                        new BigDecimal("50000")));
    }

    @Test
    @DisplayName("update - deskripsi null tidak error")
    void update_withNullDeskripsi() {
        mockKodeProdukNotExists();
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(existingProduk));
        when(produkTabunganRepository.save(any(ProdukTabungan.class))).thenAnswer(inv -> inv.getArgument(0));

        ProdukTabungan result = csProductService.update(1L,
                "NEWKODE", "Nama", null, "SALAM", new BigDecimal("50000"));

        assertNull(result.getDeskripsiSingkat());
    }

    // ==================== toggleAktif ====================

    @Test
    @DisplayName("toggleAktif - mengubah aktif dari true ke false")
    void toggleAktif_trueToFalse() {
        existingProduk.setAktif(true);
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(existingProduk));
        when(produkTabunganRepository.save(any(ProdukTabungan.class))).thenAnswer(inv -> inv.getArgument(0));

        csProductService.toggleAktif(1L);

        assertFalse(existingProduk.getAktif());
        verify(produkTabunganRepository).save(existingProduk);
    }

    @Test
    @DisplayName("toggleAktif - mengubah aktif dari false ke true")
    void toggleAktif_falseToTrue() {
        existingProduk.setAktif(false);
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(existingProduk));
        when(produkTabunganRepository.save(any(ProdukTabungan.class))).thenAnswer(inv -> inv.getArgument(0));

        csProductService.toggleAktif(1L);

        assertTrue(existingProduk.getAktif());
    }

    @Test
    @DisplayName("toggleAktif - throw jika produk tidak ditemukan")
    void toggleAktif_throwIfNotFound() {
        when(produkTabunganRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> csProductService.toggleAktif(999L));
    }

    // ==================== search ====================

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("search - kembalikan page hasil pencarian")
    void search_withQuery() {
        // Setup criteria builder mock chain
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<ProdukTabungan> cq = mock(CriteriaQuery.class);
        Root<ProdukTabungan> root = mock(Root.class);
        TypedQuery<ProdukTabungan> dataQuery = mock(TypedQuery.class);
        CriteriaQuery<Long> countQuery = mock(CriteriaQuery.class);
        Root<ProdukTabungan> countRoot = mock(Root.class);
        TypedQuery<Long> countTypedQuery = mock(TypedQuery.class);
        Predicate predicate = mock(Predicate.class);
        Path<Object> path = mock(Path.class);
        Expression<String> lowerExpr = mock(Expression.class);

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(ProdukTabungan.class)).thenReturn(cq);
        when(cq.from(ProdukTabungan.class)).thenReturn(root);
        when(cb.createQuery(Long.class)).thenReturn(countQuery);
        when(countQuery.from(ProdukTabungan.class)).thenReturn(countRoot);
        when(em.createQuery(cq)).thenReturn(dataQuery);
        when(em.createQuery(countQuery)).thenReturn(countTypedQuery);
        when(dataQuery.setFirstResult(anyInt())).thenReturn(dataQuery);
        when(dataQuery.setMaxResults(anyInt())).thenReturn(dataQuery);
        when(dataQuery.getResultList()).thenReturn(List.of(existingProduk));
        when(countTypedQuery.getSingleResult()).thenReturn(1L);

        when(root.get(anyString())).thenReturn(path);
        when(countRoot.get(anyString())).thenReturn(path);
        when(cb.lower(any())).thenReturn(lowerExpr);
        when(cb.like(any(Expression.class), anyString())).thenReturn(predicate);
        when(cb.or(any(Predicate[].class))).thenReturn(predicate);
        when(cb.equal(any(), eq(true))).thenReturn(predicate);
        when(cq.where(any(Predicate[].class))).thenReturn(cq);
        when(countQuery.where(any(Predicate[].class))).thenReturn(countQuery);
        when(cq.orderBy(any(Order.class))).thenReturn(cq);
        when(countQuery.select(any())).thenReturn(countQuery);
        when(cb.count(any())).thenReturn(mock(Expression.class));
        when(cb.asc(any())).thenReturn(mock(Order.class));

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdukTabungan> result = csProductService.search("wadiah", true, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("search - tanpa filter q dan aktif")
    void search_noFilter() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<ProdukTabungan> cq = mock(CriteriaQuery.class);
        Root<ProdukTabungan> root = mock(Root.class);
        TypedQuery<ProdukTabungan> dataQuery = mock(TypedQuery.class);
        CriteriaQuery<Long> countQuery = mock(CriteriaQuery.class);
        Root<ProdukTabungan> countRoot = mock(Root.class);
        TypedQuery<Long> countTypedQuery = mock(TypedQuery.class);
        Path<Object> path = mock(Path.class);

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(ProdukTabungan.class)).thenReturn(cq);
        when(cq.from(ProdukTabungan.class)).thenReturn(root);
        when(cb.createQuery(Long.class)).thenReturn(countQuery);
        when(countQuery.from(ProdukTabungan.class)).thenReturn(countRoot);
        when(em.createQuery(cq)).thenReturn(dataQuery);
        when(em.createQuery(countQuery)).thenReturn(countTypedQuery);
        when(dataQuery.setFirstResult(anyInt())).thenReturn(dataQuery);
        when(dataQuery.setMaxResults(anyInt())).thenReturn(dataQuery);
        when(dataQuery.getResultList()).thenReturn(List.of());
        when(countTypedQuery.getSingleResult()).thenReturn(0L);
        when(root.get(anyString())).thenReturn(path);
        when(cb.asc(any())).thenReturn(mock(Order.class));
        when(cq.orderBy(any(Order.class))).thenReturn(cq);
        when(cq.where(any(Predicate[].class))).thenReturn(cq);
        when(countQuery.where(any(Predicate[].class))).thenReturn(countQuery);
        when(countQuery.select(any())).thenReturn(countQuery);
        when(cb.count(any())).thenReturn(mock(Expression.class));

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdukTabungan> result = csProductService.search(null, null, pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }
}
