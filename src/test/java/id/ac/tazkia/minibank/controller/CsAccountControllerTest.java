package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.service.RekeningService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CsAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("CsAccountController Unit Tests")
class CsAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RekeningService rekeningService;

    @Test
    @DisplayName("GET /cs/account - list accounts")
    void index_shouldReturnView() throws Exception {
        when(rekeningService.listAccounts(null, "ACTIVE")).thenReturn(List.of());

        mockMvc.perform(get("/cs/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account"))
                .andExpect(model().attributeExists("accounts", "status"));
    }

    @Test
    @DisplayName("GET /cs/account/open - select customer")
    void openSelectCustomer_shouldReturnView() throws Exception {
        when(rekeningService.listEligibleCustomers(NasabahStatus.ACTIVE, null)).thenReturn(List.of());

        mockMvc.perform(get("/cs/account/open"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account/open"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    @DisplayName("GET /cs/account/open/{id} - form open account success")
    void openForm_shouldReturnView() throws Exception {
        when(rekeningService.getNasabahActiveById(1L)).thenReturn(new Nasabah());
        when(rekeningService.listActiveProducts()).thenReturn(List.of());

        mockMvc.perform(get("/cs/account/open/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account/open_form"))
                .andExpect(model().attributeExists("nasabah", "products", "form"));
    }

    @Test
    @DisplayName("GET /cs/account/open/{id} - form open account not found")
    void openForm_notFound() throws Exception {
        when(rekeningService.getNasabahActiveById(1L)).thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(get("/cs/account/open/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account/open"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /cs/account/open/{id} - do open success")
    void doOpen_success() throws Exception {
        mockMvc.perform(post("/cs/account/open/1")
                        .param("produkId", "1")
                        .param("nominalSetoranAwal", "10000")
                        .param("tujuanPembukaan", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /cs/account/open/{id} - do open fail")
    void doOpen_fail() throws Exception {
        doThrow(new RuntimeException("Error")).when(rekeningService)
                .openAccount(eq(1L), any(), any(), any());

        mockMvc.perform(post("/cs/account/open/1")
                        .param("produkId", "1")
                        .param("nominalSetoranAwal", "10000")
                        .param("tujuanPembukaan", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account/open/1"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("GET /cs/account/{id} - view account")
    void view_shouldReturnView() throws Exception {
        when(rekeningService.getAccountById(1L)).thenReturn(new Rekening());

        mockMvc.perform(get("/cs/account/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account/view"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    @DisplayName("GET /cs/account/{id} - view account fail")
    void view_shouldFail() throws Exception {
        when(rekeningService.getAccountById(1L)).thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(get("/cs/account/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /cs/account/{id}/close - close account")
    void close_success() throws Exception {
        mockMvc.perform(post("/cs/account/1/close"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /cs/account/{id}/close - close account fail")
    void close_fail() throws Exception {
        doThrow(new RuntimeException("Fail")).when(rekeningService).closeAccount(1L);

        mockMvc.perform(post("/cs/account/1/close"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/account"))
                .andExpect(flash().attributeExists("error"));
    }
}
