package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TellerReceiptPdfService {

    private static final String BANK_NAME = "MINIBANK TAZKIA";
    private static final String BANK_ADDRESS = "Jl. Ir. H. Djuanda No. 78 Sentul City, Bogor 16810 Indonesia";

    public byte[] generateReceipt(Transaksi tx) {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float margin = 60;
                float y = page.getMediaBox().getHeight() - margin;

                // helpers
                var fmt = rupiahFormatter();
                var dtFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("id", "ID"));

                // ===== Header =====
                y = writeCentered(cs, PDType1Font.HELVETICA_BOLD, 16, BANK_NAME, page, y);
                y = writeCentered(cs, PDType1Font.HELVETICA, 9, BANK_ADDRESS, page, y - 14);
                y = writeCentered(cs, PDType1Font.HELVETICA, 9, "===============================", page, y - 10);

                // ===== Title =====
                String title = switch (tx.getTipe()) {
                    case DEPOSIT -> "CASH DEPOSIT";
                    case WITHDRAWAL -> "CASH WITHDRAWAL";
                    case TRANSFER -> "TRANSFER";
                };
                y = writeCentered(cs, PDType1Font.HELVETICA_BOLD, 13, title, page, y - 22);
                y = writeCentered(cs, PDType1Font.HELVETICA, 10, "RECEIPT / STRUK", page, y - 14);

                // ===== Body fields =====
                y -= 30;
                y = writeKV(cs, "Receipt No:", tx.getNomorTransaksi(), margin, y);
                y = writeKV(cs, "Date & Time:", tx.getProcessedAt() == null ? "-" : tx.getProcessedAt().format(dtFmt), margin, y);
                y = writeKV(cs, "Account No:", tx.getNomorRekening(), margin, y);
                y = writeKV(cs, "Account Name:", tx.getNamaRekening(), margin, y);
                y = writeKV(cs, "Transaction:", title, margin, y);
                y = writeKV(cs, "Channel:", tx.getChannel(), margin, y);

                // amount display: withdrawal pakai minus
                BigDecimal amount = tx.getJumlah() == null ? BigDecimal.ZERO : tx.getJumlah();
                String amountText = "IDR " + fmt.format(amount);
                if (tx.getTipe() == TipeTransaksi.WITHDRAWAL) {
                    amountText = "-IDR " + fmt.format(amount);
                }

                y = writeKV(cs, "Amount:", amountText, margin, y);

                // Balance: pakai saldo_sesudah (lebih masuk akal utk struk)
                BigDecimal bal = tx.getSaldoSesudah() == null ? BigDecimal.ZERO : tx.getSaldoSesudah();
                y = writeKV(cs, "Balance:", "IDR " + fmt.format(bal), margin, y);

                y = writeKV(cs, "Description:", tx.getKeterangan(), margin, y);

                // ===== Footer =====
                y -= 18;
                y = writeLeft(cs, PDType1Font.HELVETICA, 9, "===============================", margin, y);
                y -= 18;

                y = writeCentered(cs, PDType1Font.HELVETICA, 10, "Thank you for banking with us", page, y);
                y = writeCentered(cs, PDType1Font.HELVETICA, 9, "Terima kasih telah menggunakan layanan kami", page, y - 12);

                y -= 24;
                String printedAt = (tx.getProcessedAt() == null) ? "-" : tx.getProcessedAt().format(dtFmt);
                y = writeCentered(cs, PDType1Font.HELVETICA, 9, "Printed: " + printedAt, page, y);

                // Customer Service = full_name teller saat itu
                y = writeCentered(cs, PDType1Font.HELVETICA, 9, "Customer Service: " + safe(tx.getProcessedByFullName()), page, y - 12);

                // optional: website line (kalau mau)
                // y = writeCentered(cs, PDType1Font.HELVETICA, 9, "www.minibank.tazkia.ac.id", page, y - 12);
            }

            doc.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Gagal generate PDF: " + e.getMessage(), e);
        }
    }

    private static String safe(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }

    private static DecimalFormat rupiahFormatter() {
        DecimalFormatSymbols sym = new DecimalFormatSymbols(Locale.US);
        sym.setGroupingSeparator(',');
        sym.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", sym);
        df.setGroupingUsed(true);
        return df;
    }

    private static float writeKV(PDPageContentStream cs, String k, String v, float x, float y) throws Exception {
        y = writeLeft(cs, PDType1Font.HELVETICA, 10, k, x, y);
        y = writeLeft(cs, PDType1Font.HELVETICA, 10, safe(v), x + 120, y + 12); // align value
        return y - 6;
    }

    private static float writeLeft(PDPageContentStream cs, PDType1Font font, int size, String text, float x, float y) throws Exception {
        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text == null ? "-" : text);
        cs.endText();
        return y - 14;
    }

    private static float writeCentered(PDPageContentStream cs, PDType1Font font, int size, String text, PDPage page, float y) throws Exception {
        String t = text == null ? "-" : text;
        float pageWidth = page.getMediaBox().getWidth();
        float textWidth = font.getStringWidth(t) / 1000f * size;
        float x = (pageWidth - textWidth) / 2f;

        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(t);
        cs.endText();
        return y;
    }
}
