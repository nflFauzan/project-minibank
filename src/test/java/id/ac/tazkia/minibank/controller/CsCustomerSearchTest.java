package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.service.NasabahService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CsCustomerController Search Tests")
class CsCustomerSearchTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NasabahService nasabahService;

    @Test
    @WithMockUser(roles = "CS")
    @DisplayName("GET /cs/customers?q=Fauzan should pass search keyword to service and return page")
    void testSearchCustomers() throws Exception {
        Nasabah n = new Nasabah();
        n.setId(10L);
        n.setCif("C0000001");
        n.setNamaLengkap("FAUZAN");

        when(nasabahService.searchCustomers(eq("Fauzan"), any()))
                .thenReturn(new PageImpl<>(List.of(n), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/cs/customers").param("q", "Fauzan"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/customers"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("q", "Fauzan"));
    }
}

