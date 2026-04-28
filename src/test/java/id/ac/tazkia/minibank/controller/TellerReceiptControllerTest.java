package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.service.TellerReceiptPdfService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TellerReceiptController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("TellerReceiptController Unit Tests")
class TellerReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaksiRepository transaksiRepository;

    @MockBean
    private TellerReceiptPdfService receiptPdfService;

    @Test
    @DisplayName("GET /teller/transaction/receipt/{id} - should return pdf")
    void downloadReceipt_shouldReturnPdf() throws Exception {
        UUID id = UUID.randomUUID();
        Transaksi tx = new Transaksi();
        tx.setId(id);
        tx.setNomorTransaksi("TRX-123");

        byte[] pdfContent = "dummy pdf".getBytes();

        when(transaksiRepository.findById(id)).thenReturn(Optional.of(tx));
        when(receiptPdfService.generateReceipt(tx)).thenReturn(pdfContent);

        mockMvc.perform(get("/teller/transaction/receipt/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"receipt_TRX-123.pdf\""))
                .andExpect(content().bytes(pdfContent));
    }
}
