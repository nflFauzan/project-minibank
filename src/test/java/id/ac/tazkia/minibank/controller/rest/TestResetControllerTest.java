package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TestResetController Integration Tests")
class TestResetControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdukTabunganRepository produkTabunganRepository;

    @MockitoSpyBean
    private TransaksiRepository transaksiRepositorySpy;

    @BeforeEach
    void setUpData() {
        // Clean up ProdukTabungan since it is not automatically cleaned up in BaseIntegrationTest
        produkTabunganRepository.deleteAll();

        // Seed dirty data before each test to verify the reset wipes it out
        Nasabah n = new Nasabah();
        n.setCif("C9990001");
        n.setNik("9999123456789012");
        n.setNamaSesuaiIdentitas("Dirty Nasabah");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        Rekening r = new Rekening();
        r.setNomorRekening("99900000001");
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("500000"));
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r = rekeningRepository.save(r);

        Transaksi t = new Transaksi();
        t.setId(UUID.randomUUID());
        t.setGroupId(UUID.randomUUID());
        t.setNomorTransaksi("T9990001");
        t.setTipe(TipeTransaksi.DEPOSIT);
        t.setChannel("TELLER");
        t.setRekening(r);
        t.setNomorRekening(r.getNomorRekening());
        t.setNamaRekening("Dirty Account");
        t.setCifNasabah(n.getCif());
        t.setJumlah(new BigDecimal("100000"));
        t.setSaldoSebelum(new BigDecimal("400000"));
        t.setSaldoSesudah(new BigDecimal("500000"));
        t.setKeterangan("Setoran Tunai");
        t.setProcessedAt(LocalDateTime.now());
        t.setProcessedBy("teller1");
        t.setProcessedByUsername("teller1");
        t.setProcessedByFullName("Teller Satu");
        transaksiRepositorySpy.save(t);
        
        // Save a non-default product to dirty state
        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("TAB_DIRTY");
        p.setNamaProduk("Tabungan Dirty");
        p.setJenisAkad("MUDHARABAH");
        p.setSetoranAwalMinimum(new BigDecimal("200000"));
        p.setAktif(true);
        produkTabunganRepository.save(p);
    }

    @Nested
    @DisplayName("Reset Execution")
    class ResetExecution {

        @Test
        @DisplayName("POST /api/test/reset - successfully wipes database and seeds default data")
        void reset_success() throws Exception {
            // Verify dirty data exists initially
            assertTrue(transaksiRepositorySpy.count() > 0);
            assertTrue(rekeningRepository.count() > 0);
            assertTrue(nasabahRepository.count() > 0);
            assertTrue(produkTabunganRepository.count() > 0);

            // Trigger reset
            mockMvc.perform(post("/api/test/reset"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Database reset successfully"));

            // Verify dirty data is deleted
            assertEquals(0, transaksiRepositorySpy.count());
            assertEquals(0, rekeningRepository.count());

            // Verify default data is seeded
            assertEquals(1, nasabahRepository.count());
            Nasabah defaultNasabah = nasabahRepository.findByCif("C0000001").orElseThrow();
            assertEquals("C0000001", defaultNasabah.getCif());
            assertEquals("Budi Santoso", defaultNasabah.getNamaLengkap());
            assertEquals(NasabahStatus.ACTIVE, defaultNasabah.getStatus());

            assertEquals(1, produkTabunganRepository.count());
            ProdukTabungan defaultProduct = produkTabunganRepository.findAll().get(0);
            assertEquals("TAB_UTAMA", defaultProduct.getKodeProduk());
            assertEquals("Tabungan Utama", defaultProduct.getNamaProduk());
        }

        @Test
        @Transactional(propagation = Propagation.NOT_SUPPORTED)
        @DisplayName("POST /api/test/reset - multiple consecutive calls run successfully without exception")
        void reset_multipleTimes_success() throws Exception {
            // Call 1
            mockMvc.perform(post("/api/test/reset"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"));

            // Call 2
            mockMvc.perform(post("/api/test/reset"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"));

            // Verify counts
            assertEquals(0, transaksiRepositorySpy.count());
            assertEquals(0, rekeningRepository.count());
            assertEquals(1, nasabahRepository.count());
            assertEquals(1, produkTabunganRepository.count());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        @DisplayName("POST /api/test/reset - catches exception and returns error response")
        void reset_databaseException_returnsErrorResponse() throws Exception {
            // Stub transactions repo spy to throw exception on delete
            doThrow(new RuntimeException("Database error during deletion"))
                    .when(transaksiRepositorySpy).deleteAll();

            mockMvc.perform(post("/api/test/reset"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("error"))
                    .andExpect(jsonPath("$.message").value("Database error during deletion"));
        }
    }
}
