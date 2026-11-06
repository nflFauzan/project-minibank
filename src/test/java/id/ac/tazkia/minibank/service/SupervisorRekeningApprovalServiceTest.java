package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.RekeningStatus;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupervisorRekeningApprovalServiceTest {

    @Mock
    private RekeningRepository rekeningRepository;

    @InjectMocks
    private SupervisorRekeningApprovalService approvalService;

    private Rekening rekening;

    @BeforeEach
    void setUp() {
        rekening = new Rekening();
        rekening.setNomorRekening("54300000101");
        rekening.setStatus(RekeningStatus.PENDING);
        rekening.setStatusActive(false);
    }

    @Test
    void testApproveSuccess() {
        when(rekeningRepository.findById(1L)).thenReturn(Optional.of(rekening));

        approvalService.approve(1L, "Supervisor Test", "Disetujui");

        assertEquals(RekeningStatus.ACTIVE, rekening.getStatus());
        assertTrue(rekening.isStatusActive());
        assertEquals("Supervisor Test", rekening.getApprovedBy());
        assertEquals("Disetujui", rekening.getApprovalNotes());
        assertNull(rekening.getRejectionReason());
        assertNotNull(rekening.getApprovedAt());
        verify(rekeningRepository).save(rekening);
    }

    @Test
    void testRejectSuccess() {
        when(rekeningRepository.findById(1L)).thenReturn(Optional.of(rekening));

        approvalService.reject(1L, "Supervisor Test", "Data tidak valid", "Tolak");

        assertEquals(RekeningStatus.REJECTED, rekening.getStatus());
        assertFalse(rekening.isStatusActive());
        assertEquals("Supervisor Test", rekening.getApprovedBy());
        assertEquals("Data tidak valid", rekening.getRejectionReason());
        verify(rekeningRepository).save(rekening);
    }

    @Test
    void testRejectWithoutReasonThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            approvalService.reject(1L, "Supervisor Test", "   ", null)
        );
    }
}

