package id.ac.tazkia.minibank.dto.report;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReportTransactionItemDto {
    private String nomorTransaksi;
    private LocalDateTime processedAt;
    private String tipe;
    private String nomorRekening;
    private String namaRekening;
    private BigDecimal debit;
    private BigDecimal kredit;
    private String processedBy;
    private String keterangan;
}

