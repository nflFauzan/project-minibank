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
        p.setSetoranAwalMinimum(new BigDecimal("100000"));
        p.setAktif(true);
        existingProduk = produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("GET /cs/product - menampilkan daftar produk")
    void list_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/product"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/list"))
                .andExpect(model().attributeExists("page"));
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
    @DisplayName("GET /cs/product/{id}/edit - form edit produk")
    void editForm_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/product/" + existingProduk.getId() + "/edit"))
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
    @DisplayName("POST /cs/product/{id}/toggle - toggle aktif status")
    void toggleAktif() throws Exception {
        assertTrue(existingProduk.getAktif());

        mockMvc.perform(post("/cs/product/" + existingProduk.getId() + "/toggle"))
                .andExpect(status().is3xxRedirection());

        ProdukTabungan toggled = produkTabunganRepository.findById(existingProduk.getId()).orElseThrow();
        assertFalse(toggled.getAktif());
    }
}
