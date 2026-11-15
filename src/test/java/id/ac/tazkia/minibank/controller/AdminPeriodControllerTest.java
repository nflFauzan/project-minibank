package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.PeriodePraktikum;
import id.ac.tazkia.minibank.repository.PeriodePraktikumRepository;
import id.ac.tazkia.minibank.service.PeriodePraktikumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("AdminPeriodController Integration Tests")
class AdminPeriodControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PeriodePraktikumService periodeService;

    @Autowired
    private PeriodePraktikumRepository periodeRepo;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("GET /admin/periods - harus menampilkan halaman manajemen periode dengan model yang lengkap")
    void listPeriods_shouldRenderPage() throws Exception {
        mockMvc.perform(get("/admin/periods"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/periods"))
                .andExpect(model().attributeExists("periods"))
                .andExpect(model().attributeExists("activePeriod"))
                .andExpect(model().attributeExists("nasabahCounts"))
                .andExpect(model().attributeExists("rekeningCounts"))
                .andExpect(model().attributeExists("transaksiCounts"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("POST /admin/periods/create - harus berhasil membuat periode baru")
    void createPeriod_shouldSaveAndRedirect() throws Exception {
        String testKode = "ADM_" + System.currentTimeMillis();
        mockMvc.perform(post("/admin/periods/create")
                        .param("kode", testKode)
                        .param("nama", "Praktikum Angkatan Baru")
                        .param("tanggalMulai", "2027-09-01")
                        .param("tanggalSelesai", "2028-01-31")
                        .param("deskripsi", "Keterangan praktikum")
                        .param("setAsActive", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/periods"))
                .andExpect(flash().attributeExists("success"));

        assertTrue(periodeRepo.findByKode(testKode).isPresent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("POST /admin/periods/{id}/activate - harus mengaktifkan periode terpilih")
    void activatePeriod_shouldActivateAndRedirect() throws Exception {
        String testKode = "ACT_" + System.currentTimeMillis();
        PeriodePraktikum p = periodeService.createPeriod(testKode, "Periode Untuk Diaktifkan", null, null, null, false);
        assertFalse(p.getIsActive());

        mockMvc.perform(post("/admin/periods/" + p.getId() + "/activate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/periods"))
                .andExpect(flash().attributeExists("success"));

        PeriodePraktikum refreshed = periodeRepo.findById(p.getId()).orElseThrow();
        assertTrue(refreshed.getIsActive());
    }
}
