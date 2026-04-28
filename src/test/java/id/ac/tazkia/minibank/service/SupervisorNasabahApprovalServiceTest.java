package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SupervisorNasabahApprovalService Unit Tests")
class SupervisorNasabahApprovalServiceTest {

    @Mock private NasabahRepository nasabahRepository;

    @InjectMocks
    private SupervisorNasabahApprovalService service;

    @Test
    @DisplayName("pendingCount - mengembalikan jumlah nasabah INACTIVE")
    void pendingCount() {
        when(nasabahRepository.countByStatus(NasabahStatus.INACTIVE)).thenReturn(5L);
        assertEquals(5L, service.pendingCount());
    }

    @Test
    @DisplayName("getByIdOrThrow - berhasil jika ditemukan")
    void getByIdOrThrow_success() {
        Nasabah n = new Nasabah();
        n.setId(1L);
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));

        Nasabah result = service.getByIdOrThrow(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("getByIdOrThrow - throw jika tidak ditemukan")
    void getByIdOrThrow_shouldThrow_whenNotFound() {
        when(nasabahRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getByIdOrThrow(999L));
    }

    @Test
    @DisplayName("listByStatuses - return list kosong jika statuses kosong")
    void listByStatuses_withEmptyList() {
        List<Nasabah> result = service.listByStatuses(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("listByStatuses - return list kosong jika statuses null")
    void listByStatuses_withNull() {
        List<Nasabah> result = service.listByStatuses(null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("listByStatuses - INACTIVE saja menggunakan createdAt desc")
    void listByStatuses_withInactiveOnly() {
        Nasabah n = new Nasabah();
        when(nasabahRepository.findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE))
                .thenReturn(List.of(n));

        List<Nasabah> result = service.listByStatuses(List.of(NasabahStatus.INACTIVE));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("listAllCombined - gabungan pending dan history")
    void listAllCombined() {
        Nasabah pending = new Nasabah();
        pending.setStatus(NasabahStatus.INACTIVE);
        Nasabah approved = new Nasabah();
        approved.setStatus(NasabahStatus.ACTIVE);

        when(nasabahRepository.findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE))
                .thenReturn(List.of(pending));
        when(nasabahRepository.findByStatusInOrderByApprovedAtDesc(
                List.of(NasabahStatus.ACTIVE, NasabahStatus.REJECTED)))
                .thenReturn(List.of(approved));

        List<Nasabah> result = service.listAllCombined();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("approve - set status ACTIVE")
    void approve_success() {
        Nasabah n = new Nasabah();
        n.setId(1L);
        n.setStatus(NasabahStatus.INACTIVE);
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));
        when(nasabahRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.approve(1L, "Supervisor", "Catatan");
        assertEquals(NasabahStatus.ACTIVE, n.getStatus());
        assertEquals("Supervisor", n.getApprovedBy());
    }

    @Test
    @DisplayName("reject - set status REJECTED dengan reason")
    void reject_success() {
        Nasabah n = new Nasabah();
        n.setId(1L);
        n.setStatus(NasabahStatus.INACTIVE);
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));
        when(nasabahRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.reject(1L, "Supervisor", "Data salah", "Catatan");
        assertEquals(NasabahStatus.REJECTED, n.getStatus());
        assertEquals("Data salah", n.getRejectionReason());
    }
}
