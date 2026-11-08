package id.ac.tazkia.minibank.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivitySummaryDto {
    private String username;
    private String fullName;
    private long totalCustomersRegistered;
    private long totalAccountsOpened;
    private long totalTransactionsProcessed;
    private BigDecimal totalTransactionAmount;
    private long totalDeposits;
    private long totalWithdrawals;
    private long totalTransfers;
    private long totalApprovals;
    private long totalActivitiesLogged;
    private LocalDateTime lastLoginAt;
    private LocalDateTime accountCreatedAt;
}

