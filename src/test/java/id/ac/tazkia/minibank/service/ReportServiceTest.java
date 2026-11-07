package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.report.ReportSummaryDto;
import id.ac.tazkia.minibank.entity.StatusTransaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private TransaksiRepository transaksiRepository;

    @Mock
    private RekeningRepository rekeningRepository;

    @Mock
    private NasabahRepository nasabahRepository;

    @InjectMocks
    private ReportService reportService;

    private Transaksi depositTx;
    private Transaksi withdrawTx;
    private Transaksi transferOutTx;
    private Transaksi transferInTx;
    private Transaksi rejectedTx;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.of(2026, 11, 8, 10, 0);

        depositTx = new Transaksi();
        depositTx.setId(UUID.randomUUID());
        depositTx.setNomorTransaksi("T1000001");
        depositTx.setTipe(TipeTransaksi.DEPOSIT);
        depositTx.setJumlah(new BigDecimal("5000000.00"));
        depositTx.setNomorRekening("1001");
        depositTx.setNamaRekening("Nasabah A");
        depositTx.setStatus(StatusTransaksi.SUCCESS);
        depositTx.setProcessedAt(now);

        withdrawTx = new Transaksi();
        withdrawTx.setId(UUID.randomUUID());
        withdrawTx.setNomorTransaksi("T2000001");
        withdrawTx.setTipe(TipeTransaksi.WITHDRAWAL);
        withdrawTx.setJumlah(new BigDecimal("1000000.00"));
        withdrawTx.setNomorRekening("1001");
        withdrawTx.setNamaRekening("Nasabah A");
        withdrawTx.setStatus(StatusTransaksi.SUCCESS);
        withdrawTx.setProcessedAt(now.plusHours(1));

        transferOutTx = new Transaksi();
        transferOutTx.setId(UUID.randomUUID());
        transferOutTx.setNomorTransaksi("T3000001");
        transferOutTx.setTipe(TipeTransaksi.TRANSFER);
        transferOutTx.setJumlah(new BigDecimal("2000000.00"));
        transferOutTx.setNomorRekening("1001");
        transferOutTx.setNamaRekening("Nasabah A");
        transferOutTx.setKeterangan("Transfer ke 1002");
        transferOutTx.setStatus(StatusTransaksi.SUCCESS);
        transferOutTx.setProcessedAt(now.plusHours(2));

        transferInTx = new Transaksi();
        transferInTx.setId(UUID.randomUUID());
        transferInTx.setNomorTransaksi("T3000002");
        transferInTx.setTipe(TipeTransaksi.TRANSFER);
        transferInTx.setJumlah(new BigDecimal("2000000.00"));
        transferInTx.setNomorRekening("1002");
        transferInTx.setNamaRekening("Nasabah B");
        transferInTx.setKeterangan("Transfer dari 1001");
        transferInTx.setStatus(StatusTransaksi.SUCCESS);
        transferInTx.setProcessedAt(now.plusHours(2));

        rejectedTx = new Transaksi();
        rejectedTx.setId(UUID.randomUUID());
        rejectedTx.setNomorTransaksi("T1000099");
        rejectedTx.setTipe(TipeTransaksi.DEPOSIT);
        rejectedTx.setJumlah(new BigDecimal("9999999.00"));
        rejectedTx.setStatus(StatusTransaksi.REJECTED);
        rejectedTx.setProcessedAt(now.plusHours(3));
    }

    @Test
    void testGenerateDailyReport() {
        when(transaksiRepository.findByProcessedAtBetweenOrderByProcessedAtAsc(any(), any()))
                .thenReturn(List.of(depositTx, withdrawTx, transferOutTx, transferInTx, rejectedTx));
        when(rekeningRepository.countByCreatedAtBetween(any(), any())).thenReturn(3L);
        when(nasabahRepository.countByCreatedAtBetween(any(), any())).thenReturn(2L);

        ReportSummaryDto report = reportService.generateReport("DAILY", "2026-11-08", null, null);

        assertEquals("DAILY", report.getPeriodType());
        assertEquals("2026-11-08", report.getSelectedDate());
        assertEquals(4, report.getTotalTransactions()); // excludes rejected

        // Kredit: deposit 5M + transfer in 2M = 7M
        assertEquals(new BigDecimal("7000000.00"), report.getTotalKredit());
        // Debit: withdrawal 1M + transfer out 2M = 3M
        assertEquals(new BigDecimal("3000000.00"), report.getTotalDebit());
        // Net flow = 7M - 3M = 4M
        assertEquals(new BigDecimal("4000000.00"), report.getNetFlow());

        assertEquals(3L, report.getNewAccountsCount());
        assertEquals(2L, report.getNewCustomersCount());
    }

    @Test
    void testGenerateMonthlyReport() {
        when(transaksiRepository.findByProcessedAtBetweenOrderByProcessedAtAsc(any(), any()))
                .thenReturn(List.of(depositTx));
        when(rekeningRepository.countByCreatedAtBetween(any(), any())).thenReturn(5L);
        when(nasabahRepository.countByCreatedAtBetween(any(), any())).thenReturn(4L);

        ReportSummaryDto report = reportService.generateReport("MONTHLY", null, 11, 2026);

        assertEquals("MONTHLY", report.getPeriodType());
        assertEquals(11, report.getSelectedMonth());
        assertEquals(2026, report.getSelectedYear());
        assertTrue(report.getPeriodLabel().toLowerCase().contains("november"));
        assertEquals(1, report.getTotalTransactions());
    }

    @Test
    void testGenerateYearlyReport() {
        when(transaksiRepository.findByProcessedAtBetweenOrderByProcessedAtAsc(any(), any()))
                .thenReturn(List.of());
        when(rekeningRepository.countByCreatedAtBetween(any(), any())).thenReturn(0L);
        when(nasabahRepository.countByCreatedAtBetween(any(), any())).thenReturn(0L);

        ReportSummaryDto report = reportService.generateReport("YEARLY", null, null, 2026);

        assertEquals("YEARLY", report.getPeriodType());
        assertEquals(2026, report.getSelectedYear());
        assertTrue(report.getPeriodLabel().contains("2026"));
        assertEquals(0, report.getTotalTransactions());
        assertEquals(BigDecimal.ZERO, report.getTotalKredit());
        assertEquals(BigDecimal.ZERO, report.getTotalDebit());
    }
}

