package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TellerTransactionView Tests - Sorting & Columns")
class TellerTransactionViewTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TransaksiRepository transaksiRepository;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;

    private Transaksi buildTransaksi(String noTx, Rekening r, LocalDateTime time) {
        Transaksi t = new Transaksi();
        t.setId(UUID.randomUUID());
        t.setGroupId(UUID.randomUUID());
        t.setNomorTransaksi(noTx);
        t.setRekening(r);
        t.setNomorRekening(r.getNomorRekening());
        t.setNamaRekening(r.getNamaNasabah());
        t.setChannel("TELLER");
        t.setTipe(TipeTransaksi.DEPOSIT);
        t.setJumlah(new BigDecimal("500000"));
        t.setSaldoSebelum(BigDecimal.ZERO);
        t.setSaldoSesudah(new BigDecimal("500000"));
        t.setKeterangan("Setoran tunai " + noTx);
        t.setProcessedAt(time);
        t.setProcessedBy("teller1");
        t.setProcessedByUsername("teller1");
        t.setProcessedByFullName("Teller Satu");
        t.setPrinted(false);
        return t;
    }

    @Test
    @DisplayName("GET /teller/transaction/list should sort ascending (oldest first)")
    void testListChronologicalAscending() throws Exception {
        Nasabah n = new Nasabah();
        n.setCif("C9990001");
        n.setNik("9990001111111111");
        n.setNamaSesuaiIdentitas("Sort Test Customer");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("54399900101");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setProduk("Tabungan Wadiah");
        r = rekeningRepository.save(r);

        Transaksi t1 = buildTransaksi("T999001", r, LocalDateTime.now().minusHours(2));
        transaksiRepository.save(t1);

        Transaksi t2 = buildTransaksi("T999002", r, LocalDateTime.now().minusHours(1));
        transaksiRepository.save(t2);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("teller1", "password", java.util.List.of(() -> "ROLE_TELLER"));

        MvcResult result = mockMvc.perform(get("/teller/transaction/list").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        @SuppressWarnings("unchecked")
        Page<Transaksi> page = (Page<Transaksi>) result.getModelAndView().getModel().get("page");
        assertNotNull(page);
        assertTrue(page.getTotalElements() >= 2);

        // Verify oldest appears before newest when sorting ascending
        int idx1 = -1;
        int idx2 = -1;
        for (int i = 0; i < page.getContent().size(); i++) {
            if ("T999001".equals(page.getContent().get(i).getNomorTransaksi())) idx1 = i;
            if ("T999002".equals(page.getContent().get(i).getNomorTransaksi())) idx2 = i;
        }
        if (idx1 != -1 && idx2 != -1) {
            assertTrue(idx1 < idx2, "Transaksi lama (T999001) harus berada di atas transaksi baru (T999002)");
        }
    }
}

