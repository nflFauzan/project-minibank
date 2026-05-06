package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("CsAccountController Integration Tests")
class CsAccountControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private ProdukTabunganRepository produkTabunganRepository;

    private Nasabah activeNasabah;
    private ProdukTabungan activeProduk;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9910001");
        n.setNik("9910001234567890");
        n.setNamaSesuaiIdentitas("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        activeNasabah = nasabahRepository.save(n);

        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("01");
        p.setNamaProduk("Tabungan Wadiah");
        p.setAktif(true);
        p.setSetoranAwalMinimum(new BigDecimal("100000"));
        activeProduk = produkTabunganRepository.save(p);
    }

    @Test
    @DisplayName("GET /cs/account - menampilkan daftar rekening")
    void listAccounts_shouldReturnView() throws Exception {
        mockMvc.perform(get("/cs/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account"))
                .andExpect(model().attributeExists("accounts"));
    }

    @Test
    @DisplayName("GET /cs/account - filter dengan search query")
    void listAccounts_withSearch() throws Exception {
        mockMvc.perform(get("/cs/account").param("search", "budi"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account"));
    }

    @Test
    @DisplayName("GET /cs/account/open - form pilih nasabah")
    void openForm_selectCustomer() throws Exception {
        mockMvc.perform(get("/cs/account/open"))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account/open"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    @DisplayName("GET /cs/account/open/{nasabahId} - form buka rekening")
    void openForm_withNasabah() throws Exception {
        mockMvc.perform(get("/cs/account/open/" + activeNasabah.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account/open_form"))
                .andExpect(model().attributeExists("nasabah", "products"));
    }

    @Test
    @DisplayName("POST /cs/account/open/{nasabahId} - berhasil buka rekening & data di DB")
    void openAccount_success() throws Exception {
        long countBefore = rekeningRepository.count();

        mockMvc.perform(post("/cs/account/open/" + activeNasabah.getId())
                        .param("produkId", activeProduk.getId().toString())
                        .param("nominalSetoranAwal", "200000")
                        .param("tujuanPembukaan", "Tabungan pribadi")
                        .principal(new UsernamePasswordAuthenticationToken("csuser", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"));

        assertTrue(rekeningRepository.count() > countBefore);
    }

    @Test
    @DisplayName("GET /cs/account/{id} - detail rekening")
    void viewAccount() throws Exception {
        Rekening r = new Rekening();
        r.setNomorRekening("54300099101");
        r.setStatusActive(true);
        r.setSaldo(BigDecimal.ZERO);
        r.setNasabah(activeNasabah);
        r.setCifNasabah(activeNasabah.getCif());
        r.setNamaNasabah(activeNasabah.getNamaSesuaiIdentitas());
        r = rekeningRepository.save(r);

        mockMvc.perform(get("/cs/account/" + r.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("cs/account/view"))
                .andExpect(model().attributeExists("account"));
    }
}
