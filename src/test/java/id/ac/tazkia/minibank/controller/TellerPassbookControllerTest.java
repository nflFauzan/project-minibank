package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.service.PassbookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TellerPassbookController Integration Tests")
class TellerPassbookControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private PassbookService passbookService;

    private Rekening testRekening;
    private Transaksi testTx1;
    private Transaksi testTx2;

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
        testTx1.setJumlah(new BigDecimal("4000000.00"));
        testTx1.setSaldoSebelum(BigDecimal.ZERO);
        testTx1.setSaldoSesudah(new BigDecimal("4000000.00"));
        testTx1.setKeterangan("Setoran Awal");
        testTx1.setProcessedAt(LocalDateTime.now().minusDays(2));
        testTx1.setProcessedBy("Teller Admin");
        testTx1.setProcessedByUsername("teller1");
        testTx1.setProcessedByFullName("Teller 1");
        testTx1 = transaksiRepository.save(testTx1);

        testTx2 = new Transaksi();
        testTx2.setId(UUID.randomUUID());
        testTx2.setGroupId(UUID.randomUUID());
        testTx2.setNomorTransaksi("T3000002");
        testTx2.setTipe(TipeTransaksi.WITHDRAWAL);
        testTx2.setChannel("TELLER");
        testTx2.setRekening(testRekening);
        testTx2.setNomorRekening(testRekening.getNomorRekening());
        testTx2.setNamaRekening(testRekening.getNamaNasabah());
        testTx2.setJumlah(new BigDecimal("500000.00"));
        testTx2.setSaldoSebelum(new BigDecimal("4000000.00"));
        testTx2.setSaldoSesudah(new BigDecimal("3500000.00"));
        testTx2.setKeterangan("Penarikan Tunai");
        testTx2.setProcessedAt(LocalDateTime.now().minusDays(1));
        testTx2.setProcessedBy("Teller Admin");
        testTx2.setProcessedByUsername("teller1");
        testTx2.setProcessedByFullName("Teller 1");
        testTx2 = transaksiRepository.save(testTx2);
    }

    @Test
    @DisplayName("GET /teller/passbook/select-account - should return view and list active accounts")
    void selectAccount_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/passbook/select-account"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/select-account"))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(model().attribute("accounts", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("GET /teller/passbook/select-account with search query - should filter accounts")
    void selectAccount_withSearch_shouldFilter() throws Exception {
        mockMvc.perform(get("/teller/passbook/select-account").param("search", "A2000009"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/select-account"))
                .andExpect(model().attribute("search", "A2000009"))
                .andExpect(model().attribute("accounts", hasSize(1)));
    }

    @Test
    @DisplayName("GET /teller/passbook/preview/{accountId} - should return preview view with transactions")
    void preview_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/passbook/preview/" + testRekening.getNomorRekening()))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/preview"))
                .andExpect(model().attributeExists("account", "passbook", "recentTransactions"))
                .andExpect(model().attribute("recentTransactions", hasSize(2)));
    }

    @Test
    @DisplayName("GET /passbook/preview/{accountId} - alias should work seamlessly")
    void previewAlias_shouldReturnView() throws Exception {
        mockMvc.perform(get("/passbook/preview/" + testRekening.getNomorRekening()))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/preview"))
                .andExpect(model().attributeExists("account", "passbook"));
    }

    @Test
    @DisplayName("GET /teller/passbook/initialize/{accountId} - should return initialize form")
    void initializeForm_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/passbook/initialize/" + testRekening.getNomorRekening()))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/initialize"))
                .andExpect(model().attributeExists("account", "passbook", "allTransactions"));
    }

    @Test
    @DisplayName("POST /teller/passbook/initialize/{accountId} - should update passbook state and redirect")
    void doInitialize_shouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/teller/passbook/initialize/" + testRekening.getNomorRekening())
                        .param("currentPage", "2")
                        .param("lastPrintedLine", "5")
                        .param("lastPrintedTransactionId", testTx1.getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/passbook/preview/" + testRekening.getNomorRekening()))
                .andExpect(flash().attributeExists("success"));

        Passbook pb = passbookService.getOrCreatePassbook(testRekening);
        org.junit.jupiter.api.Assertions.assertEquals(2, pb.getCurrentPage());
        org.junit.jupiter.api.Assertions.assertEquals(5, pb.getLastPrintedLine());
    }

    @Test
    @DisplayName("GET /teller/passbook/direct-print/{accountId} - should return direct-print view")
    void directPrint_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/passbook/direct-print/" + testRekening.getNomorRekening()))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/direct-print"))
                .andExpect(model().attributeExists("account", "passbook"));
    }

    @Test
    @DisplayName("GET /teller/passbook/print/{accountId} - should return printable view with all transactions")
    void printView_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/passbook/print/" + testRekening.getNomorRekening()))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/print"))
                .andExpect(model().attributeExists("account", "transactions", "printDate"))
                .andExpect(model().attribute("transactions", hasSize(2)));
    }
}
