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

@DisplayName("TellerTransactionController Integration Tests")
class TellerTransactionControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    private Rekening activeRekening;
    private Rekening targetRekening;

    @BeforeEach
    void setUp() {
        Nasabah n1 = new Nasabah();
        n1.setCif("C9960001");
        n1.setNik("9960001111111111");
        n1.setNamaSesuaiIdentitas("Budi Santoso");
        n1.setStatus(NasabahStatus.ACTIVE);
        n1 = nasabahRepository.save(n1);

        Nasabah n2 = new Nasabah();
        n2.setCif("C9960002");
        n2.setNik("9960002222222222");
        n2.setNamaSesuaiIdentitas("Aisyah");
        n2.setStatus(NasabahStatus.ACTIVE);
        n2 = nasabahRepository.save(n2);

        activeRekening = new Rekening();
        activeRekening.setNomorRekening("54399600101");
        activeRekening.setStatusActive(true);
        activeRekening.setSaldo(new BigDecimal("1000000"));
        activeRekening.setNasabah(n1);
        activeRekening.setCifNasabah(n1.getCif());
        activeRekening.setNamaNasabah(n1.getNamaSesuaiIdentitas());
        activeRekening.setProduk("Tabungan Wadiah");
        activeRekening = rekeningRepository.save(activeRekening);

        targetRekening = new Rekening();
        targetRekening.setNomorRekening("54399600202");
        targetRekening.setStatusActive(true);
        targetRekening.setSaldo(new BigDecimal("500000"));
        targetRekening.setNasabah(n2);
        targetRekening.setCifNasabah(n2.getCif());
        targetRekening.setNamaNasabah(n2.getNamaSesuaiIdentitas());
        targetRekening.setProduk("Tabungan Mudharabah");
        targetRekening = rekeningRepository.save(targetRekening);
    }

    // ==================== TRANSACTION LIST ====================

    @Test
    @DisplayName("GET /teller/transaction/list - menampilkan daftar transaksi")
    void list_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"))
                .andExpect(model().attributeExists("page", "active"));
    }

    @Test
    @DisplayName("GET /teller/transaction/list - dengan parameter query dan type")
    void list_withQueryAndType() throws Exception {
        mockMvc.perform(get("/teller/transaction/list")
                        .param("q", "budi")
                        .param("type", "DEPOSIT")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"));
    }

    // ==================== DEPOSIT ====================

    @Test
    @DisplayName("GET /teller/transaction/deposit - list rekening untuk deposit")
    void depositSelect_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction/deposit"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/deposit_select"))
                .andExpect(model().attributeExists("page", "active"));
    }

    @Test
    @DisplayName("GET /teller/transaction/deposit/{no} - form deposit berhasil")
    void depositForm_success() throws Exception {
        mockMvc.perform(get("/teller/transaction/deposit/54399600101"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/deposit_form"))
                .andExpect(model().attributeExists("rekening", "form"));
    }

    @Test
    @DisplayName("POST /teller/transaction/deposit/{no} - deposit berhasil & saldo bertambah")
    void depositProcess_success() throws Exception {
        mockMvc.perform(post("/teller/transaction/deposit/54399600101")
                        .param("jumlahSetoran", "500000")
                        .param("keterangan", "Setoran Tunai")
                        .param("noReferensi", "REF001")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"))
                .andExpect(flash().attributeExists("success"));

        Rekening after = rekeningRepository.findByNomorRekening("54399600101").orElseThrow();
        assertEquals(0, new BigDecimal("1500000").compareTo(after.getSaldo()));
    }

    @Test
    @DisplayName("POST /teller/transaction/deposit/{no} - deposit gagal redirect ke form")
    void depositProcess_fail_invalidAmount() throws Exception {
        mockMvc.perform(post("/teller/transaction/deposit/54399600101")
                        .param("jumlahSetoran", "100")
                        .param("keterangan", "Setoran kecil")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/deposit/54399600101"))
                .andExpect(flash().attributeExists("error"));
    }

    // ==================== WITHDRAWAL ====================

    @Test
    @DisplayName("GET /teller/transaction/withdrawal - list rekening untuk withdrawal")
    void withdrawalSelect_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction/withdrawal"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/withdrawal_select"));
    }

    @Test
    @DisplayName("GET /teller/transaction/withdrawal/{no} - form withdrawal berhasil")
    void withdrawalForm_success() throws Exception {
        mockMvc.perform(get("/teller/transaction/withdrawal/54399600101"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/withdrawal_form"))
                .andExpect(model().attributeExists("rekening", "form"));
    }

    @Test
    @DisplayName("POST /teller/transaction/withdrawal/{no} - withdrawal berhasil & saldo berkurang")
    void withdrawalProcess_success() throws Exception {
        mockMvc.perform(post("/teller/transaction/withdrawal/54399600101")
                        .param("jumlahPenarikan", "300000")
                        .param("keterangan", "Penarikan Tunai")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"))
                .andExpect(flash().attributeExists("success"));

        Rekening after = rekeningRepository.findByNomorRekening("54399600101").orElseThrow();
        assertEquals(0, new BigDecimal("700000").compareTo(after.getSaldo()));
    }

    @Test
    @DisplayName("POST /teller/transaction/withdrawal/{no} - withdrawal gagal (saldo tidak cukup)")
    void withdrawalProcess_fail() throws Exception {
        mockMvc.perform(post("/teller/transaction/withdrawal/54399600101")
                        .param("jumlahPenarikan", "9999999")
                        .param("keterangan", "Penarikan besar")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/withdrawal/54399600101"))
                .andExpect(flash().attributeExists("error"));
    }

    // ==================== TRANSFER ====================

    @Test
    @DisplayName("GET /teller/transaction/transfer - step 1 pilih rekening sumber")
    void transferSelectSource_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/transfer_select_source"));
    }

    @Test
    @DisplayName("GET /teller/transaction/transfer/{sourceNo} - step 2 pilih rekening tujuan")
    void transferSelectTarget_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction/transfer/54399600101"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/transfer_select_target"))
                .andExpect(model().attributeExists("sourceRekening", "page"));
    }

    @Test
    @DisplayName("GET /teller/transaction/transfer/{sourceNo}/{targetNo} - step 3 form transfer")
    void transferForm_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/transaction/transfer/54399600101/54399600202"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/transfer_form"))
                .andExpect(model().attributeExists("sourceRekening", "targetRekening", "form"));
    }

    @Test
    @DisplayName("POST /teller/transaction/transfer/{sourceNo}/{targetNo} - berhasil & saldo berubah di DB")
    void transferProcess_success() throws Exception {
        mockMvc.perform(post("/teller/transaction/transfer/54399600101/54399600202")
                        .param("jumlah", "200000")
                        .param("keteranganTambahan", "Transfer dana")
                        .param("noReferensi", "REF001")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"))
                .andExpect(flash().attributeExists("success"));

        Rekening sumber = rekeningRepository.findByNomorRekening("54399600101").orElseThrow();
        Rekening tujuan = rekeningRepository.findByNomorRekening("54399600202").orElseThrow();
        assertEquals(0, new BigDecimal("800000").compareTo(sumber.getSaldo()));
        assertEquals(0, new BigDecimal("700000").compareTo(tujuan.getSaldo()));
    }

    @Test
    @DisplayName("POST /teller/transaction/transfer/{sourceNo}/{targetNo} - gagal redirect")
    void transferProcess_fail() throws Exception {
        mockMvc.perform(post("/teller/transaction/transfer/54399600101/54399600202")
                        .param("jumlah", "99999999")
                        .param("keteranganTambahan", "Transfer besar")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/transfer/54399600101/54399600202"))
                .andExpect(flash().attributeExists("error"));
    }
}
