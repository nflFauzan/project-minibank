package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.audit.UserActivityFilterDto;
import id.ac.tazkia.minibank.dto.audit.UserActivitySummaryDto;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityService {

    private final UserActivityLogRepository userActivityLogRepository;
    private final NasabahRepository nasabahRepository;
    private final RekeningRepository rekeningRepository;
    private final TransaksiRepository transaksiRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserActivityLog log(String username, String userFullName, String userRole,
                               ActivityType activityType, String module, String description,
                               String referenceNumber, BigDecimal amount, String status) {
        try {
            UserActivityLog activityLog = new UserActivityLog(
                    username, userFullName, userRole, activityType, module,
                    description, referenceNumber, amount, status != null ? status : "SUCCESS"
            );
            return userActivityLogRepository.save(activityLog);
        } catch (Exception e) {
            log.error("Gagal mencatat audit log aktivitas: {}", e.getMessage(), e);
            return null;
        }
    }

    @Transactional
    public UserActivityLog logCurrent(ActivityType activityType, String module, String description,
                                      String referenceNumber, BigDecimal amount, String status) {
        String username = "system";
        String fullName = "System User";
        String role = "SYSTEM";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            username = auth.getName();
            Optional<User> uOpt = userRepository.findByUsername(username);
            if (uOpt.isPresent()) {
                User u = uOpt.get();
                fullName = u.getFullName();
                role = u.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "));
            } else {
                fullName = username;
                role = auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "));
            }
        }

        return log(username, fullName, role, activityType, module, description, referenceNumber, amount, status);
    }

    @Transactional(readOnly = true)
    public UserActivitySummaryDto getUserSummary(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        String fullName = userOpt.map(User::getFullName).orElse(username);
        LocalDateTime lastLogin = userOpt.map(User::getLastLoginAt).orElse(null);
        LocalDateTime createdAt = userOpt.map(User::getCreatedAt).orElse(null);

        long totalCustomers = nasabahRepository.countByCreatedBy(username);
        long totalAccounts = rekeningRepository.countByPetugasCs(username);
        long totalTransactions = transaksiRepository.countByProcessedByUsername(username);
        long totalDeposits = transaksiRepository.countByProcessedByUsernameAndTipe(username, TipeTransaksi.DEPOSIT);
        long totalWithdrawals = transaksiRepository.countByProcessedByUsernameAndTipe(username, TipeTransaksi.WITHDRAWAL);
        long totalTransfers = transaksiRepository.countByProcessedByUsernameAndTipe(username, TipeTransaksi.TRANSFER);
        BigDecimal totalNominal = transaksiRepository.sumTotalNominalByProcessedByUsername(username);
        if (totalNominal == null) {
            totalNominal = BigDecimal.ZERO;
        }

        long approvals = userActivityLogRepository.countByUsernameAndActivityType(username, ActivityType.APPROVAL_CUSTOMER)
                + userActivityLogRepository.countByUsernameAndActivityType(username, ActivityType.APPROVAL_ACCOUNT)
                + userActivityLogRepository.countByUsernameAndActivityType(username, ActivityType.APPROVAL_TRANSACTION);

        long totalLogs = userActivityLogRepository.countByUsername(username);

        return UserActivitySummaryDto.builder()
                .username(username)
                .fullName(fullName)
                .totalCustomersRegistered(totalCustomers)
                .totalAccountsOpened(totalAccounts)
                .totalTransactionsProcessed(totalTransactions)
                .totalTransactionAmount(totalNominal)
                .totalDeposits(totalDeposits)
                .totalWithdrawals(totalWithdrawals)
                .totalTransfers(totalTransfers)
                .totalApprovals(approvals)
                .totalActivitiesLogged(totalLogs)
                .lastLoginAt(lastLogin)
                .accountCreatedAt(createdAt)
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserActivityLog> getUserRecentActivities(String username, int limit) {
        if (username == null || username.isBlank()) {
            return Collections.emptyList();
        }
        return userActivityLogRepository.findByUsernameOrderByTimestampDesc(username, PageRequest.of(0, limit));
    }

    @Transactional(readOnly = true)
    public List<Nasabah> getUserCustomers(String username, Pageable pageable) {
        if (username == null || username.isBlank()) {
            return Collections.emptyList();
        }
        return nasabahRepository.findByCreatedByOrderByCreatedAtDesc(username, pageable);
    }

    @Transactional(readOnly = true)
    public List<Rekening> getUserAccounts(String username, Pageable pageable) {
        if (username == null || username.isBlank()) {
            return Collections.emptyList();
        }
        return rekeningRepository.findByPetugasCsOrderByCreatedAtDesc(username, pageable);
    }

    @Transactional(readOnly = true)
    public List<Transaksi> getUserTransactions(String username, Pageable pageable) {
        if (username == null || username.isBlank()) {
            return Collections.emptyList();
        }
        return transaksiRepository.findByProcessedByUsernameOrderByProcessedAtDesc(username, pageable);
    }

    @Transactional(readOnly = true)
    public Page<UserActivityLog> searchActivities(UserActivityFilterDto filter, Pageable pageable) {
        String username = (filter.getUsername() != null && !filter.getUsername().isBlank()) ? filter.getUsername().trim() : null;
        String module = (filter.getModule() != null && !filter.getModule().isBlank() && !"ALL".equalsIgnoreCase(filter.getModule())) ? filter.getModule().trim() : null;
        ActivityType activityType = filter.getActivityType();
        String keyword = (filter.getQ() != null && !filter.getQ().isBlank()) ? filter.getQ().trim() : null;

        LocalDateTime startDateTime = filter.getStartDate() != null ? filter.getStartDate().atStartOfDay() : null;
        LocalDateTime endDateTime = filter.getEndDate() != null ? filter.getEndDate().atTime(LocalTime.MAX) : null;

        return userActivityLogRepository.searchActivities(username, module, activityType, startDateTime, endDateTime, keyword, pageable);
    }
}

