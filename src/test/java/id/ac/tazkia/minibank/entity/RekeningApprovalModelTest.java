package id.ac.tazkia.minibank.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RekeningApprovalModelTest {
    @Test
    void testRekeningStatusAndApprovalFields() {
        Rekening r = new Rekening();
        r.setStatus(RekeningStatus.PENDING);
        r.setStatusActive(false);
        r.setApprovedBy("spv");
        r.setApprovedAt(LocalDateTime.now());
        r.setApprovalNotes("Catatan verifikasi");
        r.setRejectionReason("Dokumen tidak sesuai");

        assertEquals(RekeningStatus.PENDING, r.getStatus());
        assertFalse(r.isStatusActive());
        assertEquals("spv", r.getApprovedBy());
        assertEquals("Catatan verifikasi", r.getApprovalNotes());
        assertEquals("Dokumen tidak sesuai", r.getRejectionReason());
    }

    @Test
    void testIsStatusActiveConsistency() {
        Rekening r = new Rekening();
        r.setStatus(RekeningStatus.PENDING);
        r.setStatusActive(true); // jika boolean true tapi status masih PENDING, harus false
        assertFalse(r.isStatusActive());

        r.setStatus(RekeningStatus.ACTIVE);
        r.setStatusActive(true);
        assertTrue(r.isStatusActive());
    }
}

