package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.passbook.NextPageResponse;
import id.ac.tazkia.minibank.dto.passbook.PassbookPrintDataResponse;
import id.ac.tazkia.minibank.dto.passbook.PassbookStatusResponse;
import id.ac.tazkia.minibank.dto.passbook.PrintResultRequest;
import id.ac.tazkia.minibank.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("PassbookService Business Logic Tests")
class PassbookServiceTest extends BaseIntegrationTest {

    @Autowired private PassbookService passbookService;

    private Rekening rekening;
    private Transaksi tx1;
    private Transaksi tx2;

    @BeforeEach
    void setUp() {
        Nasabah nasabah = new Nasabah();
        nasabah.setCif("C1000099");
        nasabah.setNik("3201010101019999");
        nasabah.setNamaLengkap("Test Nasabah");
        nasabah.setStatus(NasabahStatus.ACTIVE);
        nasabah = nasabahRepository.save(nasabah);

        rekening = new Rekening();
        rekening.setNomorRekening("A2000099");
        rekening.setNamaNasabah("Test Nasabah");
        rekening.setCifNasabah("C1000099");
        rekening.setNasabah(nasabah);
        rekening.setProduk("Tabungan Wadiah Basic");
        rekening.setSaldo(new BigDecimal("1000000.00"));
        rekening.setTanggalPembukaan(LocalDate.now());
        rekening.setStatusActive(true);
        rekening = rekeningRepository.save(rekening);

        tx1 = new Transaksi();
        tx1.setId(UUID.randomUUID());
        tx1.setGroupId(UUID.randomUUID());
        tx1.setNomorTransaksi("T3000091");
        tx1.setTipe(TipeTransaksi.DEPOSIT);
        tx1.setChannel("TELLER");
        tx1.setRekening(rekening);
        tx1.setNomorRekening(rekening.getNomorRekening());
        tx1.setNamaRekening(rekening.getNamaNasabah());
        tx1.setJumlah(new BigDecimal("1000000.00"));
        tx1.setSaldoSebelum(BigDecimal.ZERO);
        tx1.setSaldoSesudah(new BigDecimal("1000000.00"));
        tx1.setKeterangan("Setoran Awal");
        tx1.setProcessedAt(LocalDateTime.now().minusHours(2));
        tx1.setProcessedBy("Teller");
        tx1.setProcessedByUsername("teller1");
        tx1.setProcessedByFullName("Teller 1");
        tx1 = transaksiRepository.save(tx1);

        tx2 = new Transaksi();
        tx2.setId(UUID.randomUUID());
        tx2.setGroupId(UUID.randomUUID());
        tx2.setNomorTransaksi("T3000092");
        tx2.setTipe(TipeTransaksi.WITHDRAWAL);
        tx2.setChannel("TELLER");
        tx2.setRekening(rekening);
        tx2.setNomorRekening(rekening.getNomorRekening());
        tx2.setNamaRekening(rekening.getNamaNasabah());
        tx2.setJumlah(new BigDecimal("200000.00"));
        tx2.setSaldoSebelum(new BigDecimal("1000000.00"));
        tx2.setSaldoSesudah(new BigDecimal("800000.00"));
        tx2.setKeterangan("Tarik Tunai");
        tx2.setProcessedAt(LocalDateTime.now().minusHours(1));
        tx2.setProcessedBy("Teller");
        tx2.setProcessedByUsername("teller1");
        tx2.setProcessedByFullName("Teller 1");
        tx2 = transaksiRepository.save(tx2);
    }

    @Test
    @DisplayName("getOrCreatePassbook - creates passbook with initial state")
    void testGetOrCreatePassbook() {
        Passbook pb = passbookService.getOrCreatePassbook(rekening);
        Assertions.assertNotNull(pb);
        Assertions.assertNotNull(pb.getPassbookNumber());
        Assertions.assertEquals(1, pb.getCurrentPage());
        Assertions.assertEquals(0, pb.getLastPrintedLine());
        Assertions.assertEquals(30, pb.getRemainingLines());
        Assertions.assertEquals("ACTIVE", pb.getStatus());
    }

    @Test
    @DisplayName("findRekening - finds by nomor, numeric ID, or passbook UUID")
    void testFindRekening() {
        Rekening byNomor = passbookService.findRekening("A2000099");
        Assertions.assertEquals(rekening.getId(), byNomor.getId());

        Rekening byId = passbookService.findRekening(rekening.getId().toString());
        Assertions.assertEquals(rekening.getNomorRekening(), byId.getNomorRekening());

        Passbook pb = passbookService.getOrCreatePassbook(rekening);
        Rekening byPb = passbookService.findRekening(pb.getId().toString());
        Assertions.assertEquals(rekening.getId(), byPb.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> passbookService.findRekening("INVALID-REK"));
    }

    @Test
    @DisplayName("advanceToNextPage - increments page and resets line number")
    void testAdvanceToNextPage() {
        passbookService.initializePassbook(rekening.getNomorRekening(), 1, 15, null);
        NextPageResponse resp = passbookService.advanceToNextPage(rekening.getNomorRekening());

        Assertions.assertTrue(resp.isSuccess());
        Assertions.assertEquals(2, resp.getCurrentPage());

        Passbook pb = passbookService.getOrCreatePassbook(rekening);
        Assertions.assertEquals(2, pb.getCurrentPage());
        Assertions.assertEquals(0, pb.getLastPrintedLine());
    }

    @Test
    @DisplayName("reportPrintResult - marks transactions as printed and updates passbook lines")
    void testReportPrintResult() {
        PrintResultRequest req = new PrintResultRequest(
                rekening.getNomorRekening(),
                "SUCCESS",
                List.of(tx1.getId().toString(), tx2.getId().toString()),
                "PLQ-20",
                "USB001",
                null
        );

        boolean result = passbookService.reportPrintResult(req);
        Assertions.assertTrue(result);

        Passbook pb = passbookService.getOrCreatePassbook(rekening);
        Assertions.assertEquals(2, pb.getLastPrintedLine());

        PassbookStatusResponse status = passbookService.getStatus(rekening.getNomorRekening());
        Assertions.assertEquals(0, status.getUnprintedTransactionCount());
    }

    @Test
    @DisplayName("initializePassbook - sets page, line, and printed flag boundary")
    void testInitializePassbook() {
        // Initialize with tx1 as last printed
        passbookService.initializePassbook(rekening.getNomorRekening(), 3, 10, tx1.getId().toString());

        Passbook pb = passbookService.getOrCreatePassbook(rekening);
        Assertions.assertEquals(3, pb.getCurrentPage());
        Assertions.assertEquals(10, pb.getLastPrintedLine());

        Transaksi reloaded1 = transaksiRepository.findById(tx1.getId()).orElseThrow();
        Transaksi reloaded2 = transaksiRepository.findById(tx2.getId()).orElseThrow();

        Assertions.assertTrue(reloaded1.isPrinted());
        Assertions.assertFalse(reloaded2.isPrinted());
    }
}

