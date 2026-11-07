package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("CsProductController Integration Tests")
class CsProductControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    private ProdukTabungan existingProduk;

    @BeforeEach
    void setUp() {
        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("TAB01");
        p.setNamaProduk("Tabungan Wadiah");
        p.setDeskripsiSingkat("Tabungan berbasis Wadiah");
        p.setJenisAkad("WADIAH");
        p.setProductType("TABUNGAN_WADIAH");
        p.setProductCategory("Tabungan Syariah");
        p.setSetoranAwalMinimum(new BigDecimal("100000"));
        p.setAktif(true);
        existingProduk = produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("GET /cs/product - menampilkan daftar produk")
    void list_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/product")
                        .param("search", "Wadiah")
                        .param("productType", "TABUNGAN_WADIAH")
                        .param("category", "Tabungan Syariah")
                        .param("sortBy", "productName")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/list"))
                .andExpect(model().attributeExists("page", "productTypeOptions", "categoryOptions"));
    }

    @Test
    @DisplayName("GET /cs/product/new - form buat produk baru")
    void newForm_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/form"))
                .andExpect(model().attributeExists("akadOptions"));
    }

    @Test
    @DisplayName("POST /cs/product/new - berhasil buat produk baru & tersimpan di DB")
    void create_success() throws Exception {
        long countBefore = produkTabunganRepository.count();

        mockMvc.perform(post("/cs/product/new")
                        .param("kodeProduk", "MUD01")
                        .param("namaProduk", "Tabungan Mudharabah")
                        .param("deskripsiSingkat", "Deskripsi")
                        .param("jenisAkad", "MUDHARABAH")
                        .param("setoranAwalMinimum", "50000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/product"));

        assertTrue(produkTabunganRepository.count() > countBefore);
    }

    @Test
    @DisplayName("POST /cs/product/create - berhasil buat produk dengan field lengkap")
    void create_fullFields_success() throws Exception {
        long countBefore = produkTabunganRepository.count();

        mockMvc.perform(post("/cs/product/create")
                        .param("productCode", "FULL01")
                        .param("productName", "Tabungan Mudharabah Full")
                        .param("productType", "TABUNGAN_MUDHARABAH")
                        .param("productCategory", "Tabungan Syariah")
                        .param("currency", "IDR")
                        .param("description", "Produk full fields testing")
                        .param("profitSharingRatio", "0.0350")
                        .param("profitSharingType", "MUDHARABAH")
                        .param("profitDistributionFrequency", "MONTHLY")
                        .param("nisbahCustomer", "0.7000")
                        .param("nisbahBank", "0.3000")
                        .param("minimumOpeningBalance", "1000000")
                        .param("minimumBalance", "500000")
                        .param("dailyWithdrawalLimit", "10000000")
                        .param("monthlyTransactionLimit", "100")
                        .param("monthlyMaintenanceFee", "0")
                        .param("atmWithdrawalFee", "0")
                        .param("interBankTransferFee", "5000")
                        .param("minCustomerAge", "21")
                        .param("allowedCustomerTypes", "PERSONAL")
                        .param("requiredDocuments", "KTP, NPWP"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/product"));

        assertTrue(produkTabunganRepository.count() > countBefore);
        ProdukTabungan created = produkTabunganRepository.findByKodeProduk("FULL01").orElseThrow();
        assertEquals("Tabungan Mudharabah Full", created.getNamaProduk());
        assertEquals("TABUNGAN_MUDHARABAH", created.getProductType());
        assertNotNull(created.getUuid());
    }

    @Test
    @DisplayName("GET /cs/product/{id} & /cs/product/{uuid} - detail produk")
    void detail_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/product/" + existingProduk.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/detail"))
                .andExpect(model().attributeExists("p"));

        assertNotNull(existingProduk.getUuid());
        mockMvc.perform(get("/cs/product/" + existingProduk.getUuid()))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/detail"))
                .andExpect(model().attributeExists("p"));
    }

    @Test
    @DisplayName("GET /cs/product/{id}/edit & /cs/product/{uuid}/edit - form edit produk")
    void editForm_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/product/" + existingProduk.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/form"))
                .andExpect(model().attributeExists("form", "akadOptions"));

        mockMvc.perform(get("/cs/product/" + existingProduk.getUuid() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/form"))
                .andExpect(model().attributeExists("form", "akadOptions"));
    }

    @Test
    @DisplayName("POST /cs/product/{id}/edit - update produk berhasil")
    void update_success() throws Exception {
        mockMvc.perform(post("/cs/product/" + existingProduk.getId() + "/edit")
                        .param("kodeProduk", "TAB02")
                        .param("namaProduk", "Tabungan Updated")
                        .param("jenisAkad", "MUDHARABAH")
                        .param("setoranAwalMinimum", "200000"))
                .andExpect(status().is3xxRedirection());

        ProdukTabungan updated = produkTabunganRepository.findById(existingProduk.getId()).orElseThrow();
        assertEquals("Tabungan Updated", updated.getNamaProduk());
    }

    @Test
    @DisplayName("POST /cs/product/{uuid}/edit - update via UUID")
    void updateUuid_success() throws Exception {
        mockMvc.perform(post("/cs/product/" + existingProduk.getUuid() + "/edit")
                        .param("productCode", existingProduk.getKodeProduk())
                        .param("productName", "Tabungan Wadiah Updated")
                        .param("productType", "TABUNGAN_WADIAH")
                        .param("productCategory", "Tabungan Syariah")
                        .param("minimumOpeningBalance", "150000")
                        .param("profitSharingType", "WADIAH"))
                .andExpect(status().is3xxRedirection());

        ProdukTabungan updated = produkTabunganRepository.findById(existingProduk.getId()).orElseThrow();
        assertEquals("Tabungan Wadiah Updated", updated.getNamaProduk());
    }

    @Test
    @DisplayName("POST /cs/product/{id}/toggle - toggle aktif status")
    void toggleAktif() throws Exception {
        assertTrue(existingProduk.getAktif());

        mockMvc.perform(post("/cs/product/" + existingProduk.getId() + "/toggle"))
                .andExpect(status().is3xxRedirection());

        ProdukTabungan toggled = produkTabunganRepository.findById(existingProduk.getId()).orElseThrow();
        assertFalse(toggled.getAktif());
    }

    @Test
    @DisplayName("POST /cs/product/{uuid}/delete - delete/toggle status via UUID")
    void deleteUuid_deactivates() throws Exception {
        assertTrue(existingProduk.getAktif());

        mockMvc.perform(post("/cs/product/" + existingProduk.getUuid() + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/cs/product*"));

        ProdukTabungan deactivated = produkTabunganRepository.findById(existingProduk.getId()).orElseThrow();
        assertFalse(deactivated.getAktif());
    }
}
