package id.ac.tazkia.minibank.integration;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("CS Customer - Integration Test")
class CsCustomerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private NasabahRepository nasabahRepository;

    @Test
    @WithMockUser(username = "cs_user", roles = {"CS"})
    @DisplayName("Functional: CS mendaftarkan nasabah baru via form POST")
    void testCreateNasabahFlow() throws Exception {
        long countBefore = nasabahRepository.count();

        // Simulasi CS mengisi form dan klik submit
        mockMvc.perform(post("/cs/customers/new")
                        .with(csrf())
                        .param("nik", "3201234567890001")
                        .param("namaLengkap", "Budi Santoso")
                        .param("tempatLahir", "Bogor")
                        .param("jenisKelamin", "Laki-laki")
                        .param("agama", "Islam")
                        .param("noHp", "08123456789")
                        .param("email", "budi@email.com")
                        .param("alamatIdentitas", "Jl. Merdeka No. 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/customers"));

        // Verifikasi data masuk ke database
        long countAfter = nasabahRepository.count();
        assertTrue(countAfter > countBefore, "Nasabah harus bertambah di database");

        List<Nasabah> all = nasabahRepository.findAll();
        Nasabah last = all.get(all.size() - 1);
        assertEquals("3201234567890001", last.getNik());
        assertEquals(NasabahStatus.INACTIVE, last.getStatus());
        assertNotNull(last.getCif(), "CIF harus auto-generated");
    }

    @Test
    @WithMockUser(username = "cs_user", roles = {"CS"})
    @DisplayName("Functional: CS melihat daftar nasabah")
    void testListNasabah() throws Exception {
        mockMvc.perform(get("/cs/customers"))
                .andExpect(status().isOk());
    }
}
