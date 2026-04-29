package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.NasabahService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CsCustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("CsCustomerController Unit Tests")
class CsCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NasabahService nasabahService;

    @MockBean
    private UserRepository userRepository;

    // ==================== LIST ====================

    @Test
    @DisplayName("GET /cs/customers - menampilkan daftar nasabah")
    void customers_shouldReturnListView() throws Exception {
        Nasabah n1 = new Nasabah();
        n1.setNamaSesuaiIdentitas("Budi Santoso");
        when(nasabahService.listAllCustomers()).thenReturn(List.of(n1));

        mockMvc.perform(get("/cs/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    @DisplayName("GET /cs/customers - list kosong")
    void customers_emptyList() throws Exception {
        when(nasabahService.listAllCustomers()).thenReturn(List.of());

        mockMvc.perform(get("/cs/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers"));
    }

    // ==================== NEW FORM ====================

    @Test
    @DisplayName("GET /cs/customers/new - menampilkan form pendaftaran")
    void newForm_shouldReturnPendaftaranView() throws Exception {
        mockMvc.perform(get("/cs/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/pendaftaran_nasabah"))
                .andExpect(model().attributeExists("nasabah"));
    }

    // ==================== CREATE ====================

    @Test
    @DisplayName("POST /cs/customers - berhasil buat nasabah baru")
    void create_success() throws Exception {
        Nasabah saved = new Nasabah();
        saved.setNamaSesuaiIdentitas("Budi Santoso");
        when(nasabahService.createNasabah(any(Nasabah.class))).thenReturn(saved);

        mockMvc.perform(post("/cs/customers")
                        .param("namaSesuaiIdentitas", "Budi Santoso")
                        .param("nik", "1234567890123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /cs/customers/new - berhasil buat nasabah baru (endpoint alternatif)")
    void create_viaNewEndpoint_success() throws Exception {
        Nasabah saved = new Nasabah();
        when(nasabahService.createNasabah(any(Nasabah.class))).thenReturn(saved);

        mockMvc.perform(post("/cs/customers/new")
                        .param("namaSesuaiIdentitas", "Aisyah")
                        .param("nik", "9876543210123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /cs/customers - gagal saat create, redirect dengan error")
    void create_fail() throws Exception {
        doThrow(new RuntimeException("NIK sudah ada")).when(nasabahService)
                .createNasabah(any(Nasabah.class));

        mockMvc.perform(post("/cs/customers")
                        .param("namaSesuaiIdentitas", "Duplikat")
                        .param("nik", "1234567890123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers/new"))
                .andExpect(flash().attributeExists("error"));
    }

    // ==================== VIEW ====================

    @Test
    @DisplayName("GET /cs/customers/{id} - tampil detail nasabah")
    void view_success() throws Exception {
        Nasabah n = new Nasabah();
        n.setId(1L);
        n.setNamaSesuaiIdentitas("Budi Santoso");
        when(nasabahService.getById(1L)).thenReturn(n);

        mockMvc.perform(get("/cs/customers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers/view"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @DisplayName("GET /cs/customers/{id} - not found redirect")
    void view_notFound() throws Exception {
        when(nasabahService.getById(999L)).thenThrow(new EntityNotFoundException("Nasabah tidak ditemukan"));

        mockMvc.perform(get("/cs/customers/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }

    // ==================== EDIT ====================

    @Test
    @DisplayName("GET /cs/customers/{id}/edit - tampil form edit")
    void edit_success() throws Exception {
        Nasabah n = new Nasabah();
        n.setId(1L);
        n.setNamaSesuaiIdentitas("Budi");
        when(nasabahService.getById(1L)).thenReturn(n);

        mockMvc.perform(get("/cs/customers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers/edit"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @DisplayName("GET /cs/customers/{id}/edit - not found redirect")
    void edit_notFound() throws Exception {
        when(nasabahService.getById(999L)).thenThrow(new EntityNotFoundException("Nasabah tidak ditemukan"));

        mockMvc.perform(get("/cs/customers/999/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }

    // ==================== UPDATE ====================

    @Test
    @DisplayName("POST /cs/customers/{id} - update berhasil")
    void update_success() throws Exception {
        doNothing().when(nasabahService).updateNasabah(eq(1L), any(Nasabah.class));

        mockMvc.perform(post("/cs/customers/1")
                        .param("namaSesuaiIdentitas", "Budi Updated")
                        .param("nik", "1234567890123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /cs/customers/{id} - update fail EntityNotFoundException")
    void update_notFound() throws Exception {
        doThrow(new EntityNotFoundException("Nasabah tidak ditemukan"))
                .when(nasabahService).updateNasabah(eq(999L), any(Nasabah.class));

        mockMvc.perform(post("/cs/customers/999")
                        .param("namaSesuaiIdentitas", "Someone"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /cs/customers/{id} - update fail generic exception")
    void update_genericError() throws Exception {
        doThrow(new RuntimeException("DB error"))
                .when(nasabahService).updateNasabah(eq(1L), any(Nasabah.class));

        mockMvc.perform(post("/cs/customers/1")
                        .param("namaSesuaiIdentitas", "Someone"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"))
                .andExpect(flash().attributeExists("error"));
    }
}
