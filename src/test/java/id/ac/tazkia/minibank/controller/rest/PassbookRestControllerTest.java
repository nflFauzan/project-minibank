package id.ac.tazkia.minibank.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.passbook.PrintResultRequest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.service.PassbookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("PassbookRestController Integration Tests")
class PassbookRestControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private PassbookService passbookService;
    @Autowired private ObjectMapper objectMapper;

    private Rekening testRekening;
    private Transaksi testTx1;

    @BeforeEach
    void setUpData() {
        Nasabah nasabah = new Nasabah();
        nasabah.setCif("C1000014");
        nasabah.setNik("3201010101010001");
        nasabah.setNamaLengkap("Saniatun Nurhasanah");
        nasabah.setStatus(NasabahStatus.ACTIVE);
        nasabah = nasabahRepository.save(nasabah);

        testRekening = new Rekening();
        testRekening.setNomorRekening("A2000009");
        testRekening.setNamaNasabah("Saniatun Nurhasanah - Personal Saving");
        testRekening.setCifNasabah("C1000014");
        testRekening.setNasabah(nasabah);
        testRekening.setProduk("Tabungan Wadiah Basic");
        testRekening.setSaldo(new BigDecimal("3500000.00"));
        testRekening.setTanggalPembukaan(LocalDate.now());
        testRekening.setStatusActive(true);
        testRekening = rekeningRepository.save(testRekening);

        testTx1 = new Transaksi();
        testTx1.setId(UUID.randomUUID());
        testTx1.setGroupId(UUID.randomUUID());
        testTx1.setNomorTransaksi("T3000001");
        testTx1.setTipe(TipeTransaksi.DEPOSIT);
        testTx1.setChannel("TELLER");
        testTx1.setRekening(testRekening);
        testTx1.setNomorRekening(testRekening.getNomorRekening());
        testTx1.setNamaRekening(testRekening.getNamaNasabah());
        testTx1.setJumlah(new BigDecimal("3500000.00"));
        testTx1.setSaldoSebelum(BigDecimal.ZERO);
        testTx1.setSaldoSesudah(new BigDecimal("3500000.00"));
        testTx1.setKeterangan("Setoran Awal");
        testTx1.setProcessedAt(LocalDateTime.now().minusDays(1));
        testTx1.setProcessedBy("Teller 1");
        testTx1.setProcessedByUsername("teller1");
        testTx1.setProcessedByFullName("Teller 1");
        testTx1 = transaksiRepository.save(testTx1);
    }

    @Test
    @DisplayName("GET /api/passbook/{accountId}/status - should return passbook status JSON")
    void getStatus_shouldReturnStatusJson() throws Exception {
        mockMvc.perform(get("/api/passbook/" + testRekening.getNomorRekening() + "/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasPassbook", is(true)))
                .andExpect(jsonPath("$.passbookNumber", notNullValue()))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.lastPrintedLine", is(0)))
                .andExpect(jsonPath("$.unprintedTransactionCount", is(1)))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }

    @Test
    @DisplayName("GET /api/passbook/{accountId}/print-data - should return full print data matching live schema")
    void getPrintData_shouldReturnPrintData() throws Exception {
        mockMvc.perform(get("/api/passbook/" + testRekening.getNomorRekening() + "/print-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account.accountNumber", is("A2000009")))
                .andExpect(jsonPath("$.customer.customerNumber", is("C1000014")))
                .andExpect(jsonPath("$.passbook.passbookNumber", notNullValue()))
                .andExpect(jsonPath("$.config.linesPerPage", is(20)))
                .andExpect(jsonPath("$.transactions", hasSize(1)))
                .andExpect(jsonPath("$.transactions[0].transactionNumber", is("T3000001")))
                .andExpect(jsonPath("$.transactions[0].credit", is(3500000.00)));
    }

    @Test
    @DisplayName("POST /api/passbook/{accountId}/next-page - should increment current page")
    void advanceNextPage_shouldAdvancePage() throws Exception {
        mockMvc.perform(post("/api/passbook/" + testRekening.getNomorRekening() + "/next-page"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.currentPage", is(2)));
    }

    @Test
    @DisplayName("POST /api/passbook/print-result - should succeed with valid transaction IDs")
    void reportPrintResult_valid_shouldSucceed() throws Exception {
        PrintResultRequest req = new PrintResultRequest(
                testRekening.getNomorRekening(),
                "SUCCESS",
                List.of(testTx1.getId().toString()),
                "ESC-POS Printer",
                "ESC-POS",
                null
        );

        mockMvc.perform(post("/api/passbook/print-result")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        Transaksi reloaded = transaksiRepository.findById(testTx1.getId()).orElseThrow();
        org.junit.jupiter.api.Assertions.assertTrue(reloaded.isPrinted());
    }

    @Test
    @DisplayName("POST /api/passbook/print-result - should return 400 when no valid transaction IDs provided")
    void reportPrintResult_empty_shouldReturn400() throws Exception {
        PrintResultRequest req = new PrintResultRequest(
                testRekening.getNomorRekening(),
                "SUCCESS",
                List.of(),
                "ESC-POS Printer",
                "ESC-POS",
                null
        );

        mockMvc.perform(post("/api/passbook/print-result")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("No valid transaction IDs provided")));
    }
}

