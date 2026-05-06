package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TellerReceiptController Integration Tests")
class TellerReceiptControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TransaksiRepository transaksiRepository;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    private UUID transaksiId;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C0000001");
        n.setNik("1234567890123456");
        n.setNamaSesuaiIdentitas("Budi");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r = rekeningRepository.save(r);

        transaksiId = UUID.randomUUID();
        Transaksi t = new Transaksi();
        t.setId(transaksiId);
        t.setGroupId(UUID.randomUUID());
        t.setNomorTransaksi("T1000001");
        t.setTipe(TipeTransaksi.DEPOSIT);
        t.setChannel("TELLER");
        t.setRekening(r);
        t.setNomorRekening(r.getNomorRekening());
        t.setNamaRekening("Budi - Tabungan");
        t.setCifNasabah(n.getCif());
        t.setJumlah(new BigDecimal("500000"));
        t.setSaldoSebelum(new BigDecimal("500000"));
        t.setSaldoSesudah(new BigDecimal("1000000"));
        t.setKeterangan("Setoran Tunai");
        t.setProcessedAt(LocalDateTime.now());
        t.setProcessedBy("teller1");
        t.setProcessedByUsername("teller1");
        t.setProcessedByFullName("Teller Satu");
        transaksiRepository.save(t);
    }

    @Test
    @DisplayName("GET /teller/transaction/receipt/{id} - should return pdf")
    void downloadReceipt_shouldReturnPdf() throws Exception {
        mockMvc.perform(get("/teller/transaction/receipt/" + transaksiId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("receipt_T1000001.pdf")));
    }
}
