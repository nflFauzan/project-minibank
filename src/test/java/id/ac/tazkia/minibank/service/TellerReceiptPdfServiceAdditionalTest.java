package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TellerReceiptPdfService - Additional Integration Tests")
class TellerReceiptPdfServiceAdditionalTest extends BaseIntegrationTest {

    @Autowired private TellerReceiptPdfService pdfService;

    private Transaksi createBaseTx() {
        Transaksi t = new Transaksi();
        t.setNomorTransaksi("T1000001");
        t.setTipe(TipeTransaksi.DEPOSIT);
        t.setChannel("TELLER");
        t.setNomorRekening("54300000101");
        t.setNamaRekening("Budi - Tabungan Wadiah");
        t.setJumlah(new BigDecimal("500000"));
        t.setSaldoSebelum(new BigDecimal("1000000"));
        t.setSaldoSesudah(new BigDecimal("1500000"));
        t.setKeterangan("Setoran Tunai");
        t.setProcessedAt(null); // Mulai dengan null untuk diuji
        t.setProcessedBy("teller1");
        t.setProcessedByUsername("teller1");
        t.setProcessedByFullName("Teller Satu");
        return t;
    }

    @Test
    @DisplayName("generateReceipt - processedAt bernilai null - berhasil dengan fallback '-'")
    void generateReceipt_processedAtNull_success() {
        Transaksi t = createBaseTx();
        t.setProcessedAt(null);

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
        assertEquals('%', (char) pdf[0]);
    }

    @Test
    @DisplayName("generateReceipt - jumlah bernilai null - berhasil dengan fallback ZERO")
    void generateReceipt_jumlahNull_success() {
        Transaksi t = createBaseTx();
        t.setJumlah(null);

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - saldoSesudah bernilai null - berhasil dengan fallback ZERO")
    void generateReceipt_saldoSesudahNull_success() {
        Transaksi t = createBaseTx();
        t.setSaldoSesudah(null);

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - processedByFullName bernilai null - berhasil dengan fallback '-'")
    void generateReceipt_processedByFullNameNull_success() {
        Transaksi t = createBaseTx();
        t.setProcessedByFullName(null);

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - processedByFullName kosong / blank - berhasil dengan fallback '-'")
    void generateReceipt_processedByFullNameBlank_success() {
        Transaksi t = createBaseTx();
        t.setProcessedByFullName("   ");

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - seluruh field string bernilai null - berhasil dengan fallback '-'")
    void generateReceipt_allStringFieldsNull_success() {
        Transaksi t = createBaseTx();
        t.setNomorTransaksi(null);
        t.setNomorRekening(null);
        t.setNamaRekening(null);
        t.setChannel(null);
        t.setKeterangan(null);

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - seluruh field string kosong / blank - berhasil dengan fallback '-'")
    void generateReceipt_allStringFieldsBlank_success() {
        Transaksi t = createBaseTx();
        t.setNomorTransaksi("   ");
        t.setNomorRekening("   ");
        t.setNamaRekening("   ");
        t.setChannel("   ");
        t.setKeterangan("   ");

        byte[] pdf = pdfService.generateReceipt(t);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }

    @Test
    @DisplayName("generateReceipt - transaksi null - throw IllegalStateException")
    void generateReceipt_txNull_shouldThrowIllegalStateException() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                pdfService.generateReceipt(null));
        assertTrue(ex.getMessage().contains("Gagal generate PDF"));
    }
}
