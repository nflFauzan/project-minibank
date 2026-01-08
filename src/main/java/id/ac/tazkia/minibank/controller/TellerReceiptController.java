package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.service.TellerReceiptPdfService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller/transaction")
public class TellerReceiptController {

    private final TransaksiRepository transaksiRepository;
    private final TellerReceiptPdfService receiptPdfService;

    @GetMapping("/receipt/{id}")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable UUID id) {
        Transaksi tx = transaksiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaksi tidak ditemukan"));

        byte[] pdf = receiptPdfService.generateReceipt(tx);

        String filename = "receipt_" + tx.getNomorTransaksi() + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(filename, StandardCharsets.UTF_8)
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}
