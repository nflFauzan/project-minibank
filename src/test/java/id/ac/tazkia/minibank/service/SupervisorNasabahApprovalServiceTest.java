package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SupervisorNasabahApprovalService Integration Tests")
class SupervisorNasabahApprovalServiceTest extends BaseIntegrationTest {

    @Autowired private SupervisorNasabahApprovalService service;
    @Autowired private NasabahRepository nasabahRepository;

    private Long nasabahId;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9870001");
        n.setNik("9870001234567890");
        n.setNamaSesuaiIdentitas("Test Nasabah");
        n.setStatus(NasabahStatus.INACTIVE);
        n = nasabahRepository.save(n);
        nasabahId = n.getId();
    }

    @Test
    @DisplayName("pendingCount - mengembalikan jumlah nasabah INACTIVE dari DB")
    void pendingCount() {
        long count = service.pendingCount();
        assertTrue(count >= 1);
    }

    @Test
    @DisplayName("getByIdOrThrow - berhasil jika ditemukan di DB")
    void getByIdOrThrow_success() {
        Nasabah result = service.getByIdOrThrow(nasabahId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("getByIdOrThrow - throw jika tidak ditemukan")
    void getByIdOrThrow_shouldThrow_whenNotFound() {
        assertThrows(IllegalArgumentException.class, () -> service.getByIdOrThrow(999999L));
    }

    @Test
    @DisplayName("listByStatuses - return list kosong jika statuses kosong")
    void listByStatuses_withEmptyList() {
        var result = service.listByStatuses(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("listByStatuses - return list kosong jika statuses null")
    void listByStatuses_withNull() {
        var result = service.listByStatuses(null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("listByStatuses - INACTIVE saja dari DB")
    void listByStatuses_withInactiveOnly() {
        var result = service.listByStatuses(List.of(NasabahStatus.INACTIVE));
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("approve - set status ACTIVE di DB")
    void approve_success() {
        service.approve(nasabahId, "Supervisor", "Catatan");

        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.ACTIVE, after.getStatus());
        assertEquals("Supervisor", after.getApprovedBy());
    }

    @Test
    @DisplayName("reject - set status REJECTED dengan reason di DB")
    void reject_success() {
        service.reject(nasabahId, "Supervisor", "Data salah", "Catatan");

        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.REJECTED, after.getStatus());
        assertEquals("Data salah", after.getRejectionReason());
    }
}
