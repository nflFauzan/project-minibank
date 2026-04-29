package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.CsProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CsProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("CsProductController Unit Tests")
class CsProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdukTabunganRepository produkTabunganRepository;

    @MockBean
    private CsProductService csProductService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /cs/product - list products")
    void list_shouldReturnView() throws Exception {
        when(csProductService.search(any(), any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/cs/product").param("status", "ACTIVE").param("q", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/list"))
                .andExpect(model().attributeExists("page", "active", "q", "status"));
    }

    @Test
    @DisplayName("GET /cs/product/new - create form")
    void createForm_shouldReturnView() throws Exception {
        when(csProductService.akadOptions()).thenReturn(List.of(new CsProductService.AkadOption("MUDHARABAH", "Mudharabah"), new CsProductService.AkadOption("WADIAH", "Wadiah")));

        mockMvc.perform(get("/cs/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/form"))
                .andExpect(model().attributeExists("form", "akadOptions", "mode", "actionUrl", "submitLabel"));
    }

    @Test
    @DisplayName("POST /cs/product/new - create success")
    void createProcess_success() throws Exception {
        mockMvc.perform(post("/cs/product/new")
                        .param("kodeProduk", "P01")
                        .param("namaProduk", "Test")
                        .param("deskripsiSingkat", "Desc")
                        .param("jenisAkad", "MUDHARABAH")
                        .param("setoranAwalMinimum", "10000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/product"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("GET /cs/product/{id} - detail view")
    void detail_shouldReturnView() throws Exception {
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(new ProdukTabungan()));

        mockMvc.perform(get("/cs/product/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/detail"))
                .andExpect(model().attributeExists("p"));
    }

    @Test
    @DisplayName("GET /cs/product/{id}/edit - edit form success")
    void editForm_shouldReturnView() throws Exception {
        when(produkTabunganRepository.findById(1L)).thenReturn(Optional.of(new ProdukTabungan()));
        when(csProductService.akadOptions()).thenReturn(List.of(new CsProductService.AkadOption("MUDHARABAH", "Mudharabah")));

        mockMvc.perform(get("/cs/product/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/product/form"))
                .andExpect(model().attributeExists("form", "p", "mode"));
    }

    @Test
    @DisplayName("POST /cs/product/{id}/edit - edit success")
    void editProcess_success() throws Exception {
        mockMvc.perform(post("/cs/product/1/edit")
                        .param("kodeProduk", "P01")
                        .param("namaProduk", "Test")
                        .param("deskripsiSingkat", "Desc")
                        .param("jenisAkad", "MUDHARABAH")
                        .param("setoranAwalMinimum", "10000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/product/1"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /cs/product/{id}/toggle - toggle status")
    void toggle_success() throws Exception {
        mockMvc.perform(post("/cs/product/1/toggle")
                        .param("q", "search")
                        .param("status", "ALL")
                        .param("page", "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/product?q=search&status=ALL&page=0"))
                .andExpect(flash().attributeExists("success"));
    }
}
