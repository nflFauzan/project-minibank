package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TellerReceiptPdfService Integration Tests")
class TellerReceiptPdfServiceTest extends BaseIntegrationTest {

    @Autowired private TellerReceiptPdfService pdfService;

    private Transaksi createTx(TipeTransaksi tipe) {
        Transaksi t = new Transaksi();
        t.setNomorTransaksi("T1000001");
        t.setTipe(tipe);
        t.setChannel("TELLER");
        t.setNomorRekening("54300000101");
        t.setNamaRekening("Budi - Tabungan Wadiah");
        t.setJumlah(new BigDecimal("500000"));
        t.setSaldoSebelum(new BigDecimal("1000000"));
        t.setSaldoSesudah(new BigDecimal("1500000"));
        t.setKeterangan("Setoran Tunai");
        t.setProcessedAt(LocalDateTime.now());
        t.setProcessedBy("teller1");
        t.setProcessedByUsername("teller1");
        t.setProcessedByFullName("Teller Satu");
        return t;
    }

    @Test
    @DisplayName("generateReceipt - DEPOSIT menghasilkan PDF valid")
    void generateReceipt_deposit() {
        byte[] pdf = pdfService.generateReceipt(createTx(TipeTransaksi.DEPOSIT));
        assertNotNull(pdf);
        assertTrue(pdf.length > 0, "PDF byte array harus > 0");
        assertEquals('%', (char) pdf[0]);
    }

    @Test
    @DisplayName("generateReceipt - WITHDRAWAL menghasilkan PDF valid")
    void generateReceipt_withdrawal() {
        Transaksi t = createTx(TipeTransaksi.WITHDRAWAL);
        t.setSaldoSesudah(new BigDecimal("500000"));
        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - TRANSFER menghasilkan PDF valid")
    void generateReceipt_transfer() {
        byte[] pdf = pdfService.generateReceipt(createTx(TipeTransaksi.TRANSFER));
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }
}
