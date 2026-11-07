package id.ac.tazkia.minibank.dto.report;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReportSummaryDto {
    private String periodType;
    private String periodLabel;
    private String selectedDate;
    private Integer selectedMonth;
    private Integer selectedYear;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long totalTransactions;
    private BigDecimal totalDebit;
    private BigDecimal totalKredit;
    private BigDecimal netFlow;
    private long newAccountsCount;
    private long newCustomersCount;
    private List<ReportTransactionItemDto> transactions;
}

