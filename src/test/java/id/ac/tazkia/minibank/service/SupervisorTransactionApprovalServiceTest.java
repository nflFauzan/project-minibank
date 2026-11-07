package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.StatusTransaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupervisorTransactionApprovalServiceTest {

    @Mock
    private TransaksiRepository transaksiRepository;

    @Mock
    private RekeningRepository rekeningRepository;

    @InjectMocks
    private SupervisorTransactionApprovalService approvalService;

    private Rekening rekening;
    private Transaksi depositTx;

    @BeforeEach
    void setUp() {
        rekening = new Rekening();
        rekening.setId(1L);
        rekening.setNomorRekening("1001");
        rekening.setSaldo(new BigDecimal("10000000.00"));

        depositTx = new Transaksi();
        depositTx.setId(UUID.randomUUID());
        depositTx.setGroupId(UUID.randomUUID());
        depositTx.setNomorRekening("1001");
        depositTx.setTipe(TipeTransaksi.DEPOSIT);
        depositTx.setJumlah(new BigDecimal("10000000.00"));
        depositTx.setStatus(StatusTransaksi.PENDING_APPROVAL);
    }

    @Test
    void testApproveDepositSuccess() {
        when(transaksiRepository.findById(depositTx.getId())).thenReturn(Optional.of(depositTx));
        when(rekeningRepository.findByNomorRekeningForUpdate("1001")).thenReturn(Optional.of(rekening));

        approvalService.approve(depositTx.getId(), "spv1", "Disetujui deposit besar");

        assertEquals(new BigDecimal("20000000.00"), rekening.getSaldo());
        assertEquals(StatusTransaksi.SUCCESS, depositTx.getStatus());
        assertEquals("spv1", depositTx.getApprovedBy());
        assertNotNull(depositTx.getApprovedAt());
        assertEquals("Disetujui deposit besar", depositTx.getApprovalNotes());

        verify(rekeningRepository).save(rekening);
        verify(transaksiRepository).save(depositTx);
    }

    @Test
    void testApproveWithdrawalSuccess() {
        Transaksi withdrawTx = new Transaksi();
        withdrawTx.setId(UUID.randomUUID());
        withdrawTx.setGroupId(UUID.randomUUID());
        withdrawTx.setNomorRekening("1001");
        withdrawTx.setTipe(TipeTransaksi.WITHDRAWAL);
        withdrawTx.setJumlah(new BigDecimal("6000000.00"));
        withdrawTx.setStatus(StatusTransaksi.PENDING_APPROVAL);

        when(transaksiRepository.findById(withdrawTx.getId())).thenReturn(Optional.of(withdrawTx));
        when(rekeningRepository.findByNomorRekeningForUpdate("1001")).thenReturn(Optional.of(rekening));

        approvalService.approve(withdrawTx.getId(), "spv1", "Disetujui tarik tunai");

        assertEquals(new BigDecimal("4000000.00"), rekening.getSaldo());
        assertEquals(StatusTransaksi.SUCCESS, withdrawTx.getStatus());
        assertEquals("spv1", withdrawTx.getApprovedBy());
        verify(rekeningRepository).save(rekening);
    }

    @Test
    void testApproveWithdrawalInsufficientBalance_ThrowsException() {
        Transaksi withdrawTx = new Transaksi();
        withdrawTx.setId(UUID.randomUUID());
        withdrawTx.setNomorRekening("1001");
        withdrawTx.setTipe(TipeTransaksi.WITHDRAWAL);
        withdrawTx.setJumlah(new BigDecimal("15000000.00"));
        withdrawTx.setStatus(StatusTransaksi.PENDING_APPROVAL);

        when(transaksiRepository.findById(withdrawTx.getId())).thenReturn(Optional.of(withdrawTx));
        when(rekeningRepository.findByNomorRekeningForUpdate("1001")).thenReturn(Optional.of(rekening));

        assertThrows(IllegalStateException.class, () ->
                approvalService.approve(withdrawTx.getId(), "spv1", "Test")
        );
    }

    @Test
    void testApproveTransferSuccess() {
        UUID groupId = UUID.randomUUID();

        Rekening rSumber = new Rekening();
        rSumber.setNomorRekening("1001");
        rSumber.setSaldo(new BigDecimal("20000000.00"));

        Rekening rTujuan = new Rekening();
        rTujuan.setNomorRekening("1002");
        rTujuan.setSaldo(new BigDecimal("5000000.00"));

        Transaksi out = new Transaksi();
        out.setId(UUID.randomUUID());
        out.setGroupId(groupId);
        out.setNomorRekening("1001");
        out.setTipe(TipeTransaksi.TRANSFER);
        out.setJumlah(new BigDecimal("10000000.00"));
        out.setKeterangan("Transfer ke 1002");
        out.setStatus(StatusTransaksi.PENDING_APPROVAL);

        Transaksi in = new Transaksi();
        in.setId(UUID.randomUUID());
        in.setGroupId(groupId);
        in.setNomorRekening("1002");
        in.setTipe(TipeTransaksi.TRANSFER);
        in.setJumlah(new BigDecimal("10000000.00"));
        in.setKeterangan("Transfer dari 1001");
        in.setStatus(StatusTransaksi.PENDING_APPROVAL);

        when(transaksiRepository.findById(out.getId())).thenReturn(Optional.of(out));
        when(transaksiRepository.findByGroupIdOrderByProcessedAtAsc(groupId)).thenReturn(List.of(out, in));
        when(rekeningRepository.findByNomorRekeningForUpdate("1001")).thenReturn(Optional.of(rSumber));
        when(rekeningRepository.findByNomorRekeningForUpdate("1002")).thenReturn(Optional.of(rTujuan));

        approvalService.approve(out.getId(), "spv1", "Disetujui transfer");

        assertEquals(new BigDecimal("10000000.00"), rSumber.getSaldo());
        assertEquals(new BigDecimal("15000000.00"), rTujuan.getSaldo());
        assertEquals(StatusTransaksi.SUCCESS, out.getStatus());
        assertEquals(StatusTransaksi.SUCCESS, in.getStatus());
    }

    @Test
    void testRejectSuccess() {
        when(transaksiRepository.findById(depositTx.getId())).thenReturn(Optional.of(depositTx));

        approvalService.reject(depositTx.getId(), "spv1", "Kecurigaan transaksi", "Ditolak");

        assertEquals(StatusTransaksi.REJECTED, depositTx.getStatus());
        assertEquals("spv1", depositTx.getApprovedBy());
        assertEquals("Kecurigaan transaksi", depositTx.getRejectionReason());
        verify(transaksiRepository).save(depositTx);
        verifyNoInteractions(rekeningRepository);
    }

    @Test
    void testListPendingTransactions_FiltersOutIncomingTransferLeg() {
        Transaksi dep = new Transaksi();
        dep.setTipe(TipeTransaksi.DEPOSIT);
        dep.setStatus(StatusTransaksi.PENDING_APPROVAL);

        Transaksi transferOut = new Transaksi();
        transferOut.setTipe(TipeTransaksi.TRANSFER);
        transferOut.setKeterangan("Transfer ke 1002");
        transferOut.setStatus(StatusTransaksi.PENDING_APPROVAL);

        Transaksi transferIn = new Transaksi();
        transferIn.setTipe(TipeTransaksi.TRANSFER);
        transferIn.setKeterangan("Transfer dari 1001");
        transferIn.setStatus(StatusTransaksi.PENDING_APPROVAL);

        when(transaksiRepository.findByStatusOrderByProcessedAtDesc(StatusTransaksi.PENDING_APPROVAL))
                .thenReturn(List.of(dep, transferOut, transferIn));

        List<Transaksi> result = approvalService.listPendingTransactions();

        assertEquals(2, result.size());
        assertEquals(2, approvalService.pendingCount());
    }
}

