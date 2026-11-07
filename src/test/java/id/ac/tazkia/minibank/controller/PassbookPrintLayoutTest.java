package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.passbook.PassbookPrintDataResponse;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.service.PassbookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("PassbookPrintLayout Tests - Physical Blanko Codes & Alignment")
class PassbookPrintLayoutTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private PassbookService passbookService;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private TransaksiRepository transaksiRepository;

    @Test
    @DisplayName("PassbookPrintDataResponse should map TRANSFER to sandi 213 per physical book standard")
    void testPassbookSandiCodes() {
        Nasabah n = new Nasabah();
        n.setCif("C8880001");
        n.setNik("8880001111111111");
        n.setNamaSesuaiIdentitas("Passbook Test Customer");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54388800101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setProduk("Tabungan Wadiah");
        r.setTanggalPembukaan(LocalDate.now());
        r = rekeningRepository.save(r);

        Transaksi tTransfer = new Transaksi();
        tTransfer.setId(UUID.randomUUID());
        tTransfer.setGroupId(UUID.randomUUID());
        tTransfer.setNomorTransaksi("T888001");
        tTransfer.setRekening(r);
        tTransfer.setNomorRekening(r.getNomorRekening());
        tTransfer.setNamaRekening(r.getNamaNasabah());
        tTransfer.setChannel("TELLER");
        tTransfer.setTipe(TipeTransaksi.TRANSFER);
        tTransfer.setJumlah(new BigDecimal("100000"));
        tTransfer.setSaldoSebelum(new BigDecimal("1000000"));
        tTransfer.setSaldoSesudah(new BigDecimal("900000"));
        tTransfer.setKeterangan("Transfer ke 54388800202");
        tTransfer.setProcessedAt(LocalDateTime.now());
        tTransfer.setProcessedBy("teller1");
        tTransfer.setProcessedByUsername("teller1");
        tTransfer.setProcessedByFullName("Teller Satu");
        tTransfer.setPrinted(false);
        transaksiRepository.save(tTransfer);

        PassbookPrintDataResponse response = passbookService.getPrintData(r.getId().toString());
        assertEquals(1, response.getTransactions().size());
        assertEquals("213", response.getTransactions().get(0).getSandiCode(),
                "Sandi transaksi Transfer harus 213 sesuai blanko buku tabungan Tazkia");
    }

    @Test
    @DisplayName("GET /teller/passbook/print/{id} should return print template")
    void testPrintPassbookView() throws Exception {
        Nasabah n = new Nasabah();
        n.setCif("C8880002");
        n.setNik("8880002222222222");
        n.setNamaSesuaiIdentitas("Print View Customer");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54388800202");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setProduk("Tabungan Wadiah");
        r.setTanggalPembukaan(LocalDate.now());
        r = rekeningRepository.save(r);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("teller1", "password", java.util.List.of(() -> "ROLE_TELLER"));

        mockMvc.perform(get("/teller/passbook/print/" + r.getId()).principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/print"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("transactions"));
    }
}

