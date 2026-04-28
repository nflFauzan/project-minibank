package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NasabahApprovalService Unit Tests")
class NasabahApprovalServiceTest {

    @Mock private NasabahRepository nasabahRepository;

    @InjectMocks
    private NasabahApprovalService approvalService;

    private Nasabah createInactiveNasabah() {
        Nasabah n = new Nasabah();
        n.setId(1L);
        n.setStatus(NasabahStatus.INACTIVE);
        n.setNamaSesuaiIdentitas("Test Nasabah");
        return n;
    }

    @Test
    @DisplayName("approve - berhasil mengubah status ke ACTIVE")
    void approve_success() {
        Nasabah n = createInactiveNasabah();
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        approvalService.approve(1L, "Supervisor A", "OK");

        assertEquals(NasabahStatus.ACTIVE, n.getStatus());
        assertEquals("Supervisor A", n.getApprovedBy());
        assertNotNull(n.getApprovedAt());
        assertNull(n.getRejectionReason());
        verify(nasabahRepository).save(n);
    }

    @Test
    @DisplayName("approve - throw jika status bukan INACTIVE")
    void approve_shouldThrow_whenNotInactive() {
        Nasabah n = createInactiveNasabah();
        n.setStatus(NasabahStatus.ACTIVE); // sudah active
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));

        assertThrows(IllegalStateException.class,
                () -> approvalService.approve(1L, "Supervisor", "OK"));
    }

    @Test
    @DisplayName("approve - throw jika nasabah tidak ditemukan")
    void approve_shouldThrow_whenNotFound() {
        when(nasabahRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> approvalService.approve(999L, "Supervisor", "OK"));
    }

    @Test
    @DisplayName("reject - berhasil mengubah status ke REJECTED")
    void reject_success() {
        Nasabah n = createInactiveNasabah();
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));
        when(nasabahRepository.save(any(Nasabah.class))).thenAnswer(inv -> inv.getArgument(0));

        approvalService.reject(1L, "Supervisor B", "Data tidak lengkap");

        assertEquals(NasabahStatus.REJECTED, n.getStatus());
        assertEquals("Supervisor B", n.getApprovedBy());
        assertEquals("Data tidak lengkap", n.getRejectionReason());
        verify(nasabahRepository).save(n);
    }

    @Test
    @DisplayName("reject - throw jika status bukan INACTIVE")
    void reject_shouldThrow_whenNotInactive() {
        Nasabah n = createInactiveNasabah();
        n.setStatus(NasabahStatus.ACTIVE);
        when(nasabahRepository.findById(1L)).thenReturn(Optional.of(n));

        assertThrows(IllegalStateException.class,
                () -> approvalService.reject(1L, "Supervisor", "Alasan"));
    }
}
