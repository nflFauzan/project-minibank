package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.audit.UserActivityFilterDto;
import id.ac.tazkia.minibank.dto.audit.UserActivitySummaryDto;
import id.ac.tazkia.minibank.entity.ActivityType;
import id.ac.tazkia.minibank.entity.UserActivityLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserActivityServiceTest extends BaseIntegrationTest {

    @Autowired
    private UserActivityService userActivityService;

    @Test
    @DisplayName("Pencatatan aktivitas langsung (log) berhasil disimpan")
    void testDirectLog() {
        UserActivityLog saved = userActivityService.log(
                "cs_test", "CS Test User", "ROLE_CS",
                ActivityType.CUSTOMER_REGISTER, "CUSTOMER", "Daftar nasabah",
                "C0009999", null, "SUCCESS"
        );

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("cs_test", saved.getUsername());
        assertEquals(ActivityType.CUSTOMER_REGISTER, saved.getActivityType());
    }

    @Test
    @WithMockUser(username = "teller_test", roles = {"TELLER"})
    @DisplayName("Pencatatan aktivitas dengan SecurityContext saat ini (logCurrent)")
    void testLogCurrent() {
        UserActivityLog saved = userActivityService.logCurrent(
                ActivityType.TRANSACTION_DEPOSIT, "TELLER", "Setoran tunai",
                "TRX-TEST-001", new BigDecimal("250000"), "SUCCESS"
        );

        assertNotNull(saved);
        assertEquals("teller_test", saved.getUsername());
        assertEquals("TELLER", saved.getModule());
        assertEquals(new BigDecimal("250000"), saved.getAmount());
    }

    @Test
    @DisplayName("Summary aktivitas pengguna dan pencarian aktivitas")
    void testSummaryAndSearch() {
        userActivityService.log(
                "user_audit", "Audit User", "ROLE_CS",
                ActivityType.CUSTOMER_REGISTER, "CUSTOMER", "Registrasi nasabah Budi",
                "C0001234", null, "SUCCESS"
        );
        userActivityService.log(
                "user_audit", "Audit User", "ROLE_CS",
                ActivityType.ACCOUNT_OPEN, "ACCOUNT", "Buka tabungan Wadiah",
                "54312345601", new BigDecimal("100000"), "SUCCESS"
        );

        UserActivitySummaryDto summary = userActivityService.getUserSummary("user_audit");
        assertNotNull(summary);
        assertEquals("user_audit", summary.getUsername());

        UserActivityFilterDto filter = new UserActivityFilterDto();
        filter.setUsername("user_audit");
        filter.setModule("CUSTOMER");

        Page<UserActivityLog> results = userActivityService.searchActivities(filter, PageRequest.of(0, 10));
        assertEquals(1, results.getTotalElements());
        assertEquals("C0001234", results.getContent().get(0).getReferenceNumber());

        List<UserActivityLog> recent = userActivityService.getUserRecentActivities("user_audit", 5);
        assertEquals(2, recent.size());
    }
}

