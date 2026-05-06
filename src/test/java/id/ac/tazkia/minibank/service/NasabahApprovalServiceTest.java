package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NasabahApprovalService Integration Tests")
class NasabahApprovalServiceTest extends BaseIntegrationTest {

    @Autowired private NasabahApprovalService approvalService;
    @Autowired private NasabahRepository nasabahRepository;

    private Long nasabahId;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9860001");
        n.setNik("9860001234567890");
        n.setNamaSesuaiIdentitas("Test Nasabah");
        n.setStatus(NasabahStatus.INACTIVE);
        n = nasabahRepository.save(n);
        nasabahId = n.getId();
    }

    @Test
    @DisplayName("approve - berhasil mengubah status ke ACTIVE di DB")
    void approve_success() {
        approvalService.approve(nasabahId, "Supervisor A", "OK");

        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.ACTIVE, after.getStatus());
        assertEquals("Supervisor A", after.getApprovedBy());
        assertNotNull(after.getApprovedAt());
        assertNull(after.getRejectionReason());
    }

    @Test
    @DisplayName("approve - throw jika status bukan INACTIVE")
    void approve_shouldThrow_whenNotInactive() {
        approvalService.approve(nasabahId, "Supervisor", "OK");
        assertThrows(IllegalStateException.class,
                () -> approvalService.approve(nasabahId, "Supervisor", "OK"));
    }

    @Test
    @DisplayName("approve - throw jika nasabah tidak ditemukan")
    void approve_shouldThrow_whenNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> approvalService.approve(999999L, "Supervisor", "OK"));
    }

    @Test
    @DisplayName("reject - berhasil mengubah status ke REJECTED di DB")
    void reject_success() {
        approvalService.reject(nasabahId, "Supervisor B", "Data tidak lengkap");

        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.REJECTED, after.getStatus());
        assertEquals("Supervisor B", after.getApprovedBy());
        assertEquals("Data tidak lengkap", after.getRejectionReason());
    }

    @Test
    @DisplayName("reject - throw jika status bukan INACTIVE")
    void reject_shouldThrow_whenNotInactive() {
        approvalService.approve(nasabahId, "Supervisor", "OK");
        assertThrows(IllegalStateException.class,
                () -> approvalService.reject(nasabahId, "Supervisor", "Alasan"));
    }
}
