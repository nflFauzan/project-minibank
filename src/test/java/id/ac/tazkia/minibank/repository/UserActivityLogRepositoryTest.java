package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.ActivityType;
import id.ac.tazkia.minibank.entity.UserActivityLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserActivityLogRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private UserActivityLogRepository userActivityLogRepository;

    @Test
    @DisplayName("Simpan dan temukan aktivitas berdasarkan username terurut waktu terbaru")
    void testSaveAndFindByUsername() {
        UserActivityLog log1 = new UserActivityLog("cs1", "CS One", "ROLE_CS",
                ActivityType.CUSTOMER_REGISTER, "CUSTOMER", "Daftar nasabah baru", "C0000001", null, "SUCCESS");
        log1.setTimestamp(LocalDateTime.now().minusHours(2));

        UserActivityLog log2 = new UserActivityLog("cs1", "CS One", "ROLE_CS",
                ActivityType.ACCOUNT_OPEN, "ACCOUNT", "Buka rekening baru", "54300000101", new BigDecimal("100000"), "SUCCESS");
        log2.setTimestamp(LocalDateTime.now().minusHours(1));

        userActivityLogRepository.save(log1);
        userActivityLogRepository.save(log2);

        List<UserActivityLog> results = userActivityLogRepository.findByUsernameOrderByTimestampDesc("cs1", PageRequest.of(0, 10));
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(ActivityType.ACCOUNT_OPEN, results.get(0).getActivityType());
        assertEquals("54300000101", results.get(0).getReferenceNumber());
    }

    @Test
    @DisplayName("Filter pencarian audit log berdasarkan username, module, dan keyword")
    void testSearchActivities() {
        UserActivityLog logTeller = new UserActivityLog("teller1", "Teller One", "ROLE_TELLER",
                ActivityType.TRANSACTION_DEPOSIT, "TELLER", "Setoran tunai Rp 500,000", "TRX-20261108-001", new BigDecimal("500000"), "SUCCESS");
        userActivityLogRepository.save(logTeller);

        Page<UserActivityLog> page = userActivityLogRepository.searchActivities(
                "teller1", "TELLER", ActivityType.TRANSACTION_DEPOSIT, null, null, "500,000", PageRequest.of(0, 10));

        assertEquals(1, page.getTotalElements());
        assertEquals("TRX-20261108-001", page.getContent().get(0).getReferenceNumber());
    }
}

