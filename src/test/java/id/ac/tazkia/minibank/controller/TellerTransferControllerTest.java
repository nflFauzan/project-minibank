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

@DisplayName("TellerTransferController Integration Tests")
class TellerTransferControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    @BeforeEach
    void setUp() {
        Nasabah n1 = new Nasabah();
        n1.setCif("C9950001");
        n1.setNik("9950001111111111");
        n1.setNamaSesuaiIdentitas("Budi");
        n1.setStatus(NasabahStatus.ACTIVE);
        n1 = nasabahRepository.save(n1);

        Nasabah n2 = new Nasabah();
        n2.setCif("C9950002");
        n2.setNik("9950002222222222");
        n2.setNamaSesuaiIdentitas("Aisyah");
        n2.setStatus(NasabahStatus.ACTIVE);
        n2 = nasabahRepository.save(n2);

        Rekening r1 = new Rekening();
        r1.setNomorRekening("54399500101");
        r1.setStatusActive(true);
        r1.setSaldo(new BigDecimal("1000000"));
        r1.setNasabah(n1);
        r1.setCifNasabah(n1.getCif());
        r1.setNamaNasabah(n1.getNamaSesuaiIdentitas());
        r1.setProduk("Tabungan Wadiah");
        rekeningRepository.save(r1);

        Rekening r2 = new Rekening();
        r2.setNomorRekening("54399500202");
        r2.setStatusActive(true);
        r2.setSaldo(new BigDecimal("500000"));
        r2.setNasabah(n2);
        r2.setCifNasabah(n2.getCif());
        r2.setNamaNasabah(n2.getNamaSesuaiIdentitas());
        r2.setProduk("Tabungan Mudharabah");
        rekeningRepository.save(r2);
    }

    @Test
    @DisplayName("GET /teller/transfer - list")
    void listRekening() throws Exception {
        mockMvc.perform(get("/teller/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transfer/list"));
    }

    @Test
    @DisplayName("GET /teller/transfer/form - show form")
    void formTransfer() throws Exception {
        mockMvc.perform(get("/teller/transfer/form").param("sumber", "54399500101"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transfer/form"))
                .andExpect(model().attribute("rekeningSumber", "54399500101"));
    }

    @Test
    @DisplayName("POST /teller/transfer - submit success")
    void submitTransfer_success() throws Exception {
        mockMvc.perform(post("/teller/transfer")
                        .param("rekeningSumber", "54399500101")
                        .param("rekeningTujuan", "54399500202")
                        .param("jumlah", "50000")
                        .param("keterangan", "Bayar hutang")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("success"));

        // Verifikasi saldo berubah di database — compareTo handles scale differences
        Rekening sumber = rekeningRepository.findByNomorRekening("54399500101").orElseThrow();
        Rekening tujuan = rekeningRepository.findByNomorRekening("54399500202").orElseThrow();
        assertEquals(0, new BigDecimal("950000").compareTo(sumber.getSaldo()));
        assertEquals(0, new BigDecimal("550000").compareTo(tujuan.getSaldo()));
    }

    @Test
    @DisplayName("POST /teller/transfer - submit fail (saldo tidak cukup)")
    void submitTransfer_fail() throws Exception {
        mockMvc.perform(post("/teller/transfer")
                        .param("rekeningSumber", "54399500101")
                        .param("rekeningTujuan", "54399500202")
                        .param("jumlah", "99999999")
                        .param("keterangan", "Transfer besar")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transfer/form?sumber=54399500101"))
                .andExpect(flash().attributeExists("error"));
    }
}
