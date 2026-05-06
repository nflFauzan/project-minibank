package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("CsCustomerController Integration Tests")
class CsCustomerControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private NasabahRepository nasabahRepository;

    private Nasabah existingNasabah;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C0000001");
        n.setNik("1234567890123456");
        n.setNamaSesuaiIdentitas("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        existingNasabah = nasabahRepository.save(n);
    }

    @Test
    @DisplayName("GET /cs/customers - menampilkan daftar nasabah")
    void customers_shouldReturnListView() throws Exception {
        mockMvc.perform(get("/cs/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers"))
                .andExpect(model().attributeExists("customers"));

        assertFalse(nasabahRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("GET /cs/customers/new - menampilkan form pendaftaran")
    void newForm_shouldReturnPendaftaranView() throws Exception {
        mockMvc.perform(get("/cs/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/pendaftaran_nasabah"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @DisplayName("POST /cs/customers - berhasil buat nasabah baru & data tersimpan di DB")
    void create_success() throws Exception {
        long countBefore = nasabahRepository.count();

        mockMvc.perform(post("/cs/customers")
                        .param("namaSesuaiIdentitas", "Aisyah Putri")
                        .param("nik", "9876543210123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("success"));

        assertTrue(nasabahRepository.count() > countBefore);
    }

    @Test
    @DisplayName("POST /cs/customers/new - berhasil buat nasabah baru (endpoint alternatif)")
    void create_viaNewEndpoint_success() throws Exception {
        long countBefore = nasabahRepository.count();

        mockMvc.perform(post("/cs/customers/new")
                        .param("namaSesuaiIdentitas", "Aisyah")
                        .param("nik", "1111222233334444"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("success"));

        assertTrue(nasabahRepository.count() > countBefore);
    }

    @Test
    @DisplayName("GET /cs/customers/{id} - tampil detail nasabah")
    void view_success() throws Exception {
        mockMvc.perform(get("/cs/customers/" + existingNasabah.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers/view"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @DisplayName("GET /cs/customers/{id} - not found redirect")
    void view_notFound() throws Exception {
        mockMvc.perform(get("/cs/customers/999999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("GET /cs/customers/{id}/edit - tampil form edit")
    void edit_success() throws Exception {
        mockMvc.perform(get("/cs/customers/" + existingNasabah.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers/edit"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @DisplayName("GET /cs/customers/{id}/edit - not found redirect")
    void edit_notFound() throws Exception {
        mockMvc.perform(get("/cs/customers/999999/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /cs/customers/{id} - update berhasil & data berubah di DB")
    void update_success() throws Exception {
        mockMvc.perform(post("/cs/customers/" + existingNasabah.getId())
                        .param("namaSesuaiIdentitas", "Budi Updated")
                        .param("nik", "1234567890123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("success"));

        Nasabah updated = nasabahRepository.findById(existingNasabah.getId()).orElseThrow();
        assertEquals("Budi Updated", updated.getNamaSesuaiIdentitas());
    }

    @Test
    @DisplayName("POST /cs/customers/{id} - update fail EntityNotFoundException")
    void update_notFound() throws Exception {
        mockMvc.perform(post("/cs/customers/999999")
                        .param("namaSesuaiIdentitas", "Someone"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }
}
