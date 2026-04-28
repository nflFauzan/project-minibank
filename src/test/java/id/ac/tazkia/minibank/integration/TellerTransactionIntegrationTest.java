package id.ac.tazkia.minibank.integration;

import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Teller Transaction - Integration Test")
class TellerTransactionIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private RekeningRepository rekeningRepository;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private UserRepository userRepository;

    private String nomorRekening;

    @BeforeEach
    void setUp() {
        // Buat nasabah aktif
        Nasabah n = new Nasabah();
        n.setCif("C9999001");
        n.setNik("9999000000000001");
        n.setNamaSesuaiIdentitas("Test Nasabah Teller");
        n.setStatus(NasabahStatus.ACTIVE);
        n = nasabahRepository.save(n);

        // Buat rekening aktif dengan saldo
        Rekening r = new Rekening();
        r.setNomorRekening("543TEST001");
        r.setNasabah(n);
        r.setCifNasabah(n.getCif());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r.setNik(n.getNik());
        r.setStatusActive(true);
        r.setSaldo(new BigDecimal("1000000"));
        r.setProduk("Tabungan Wadiah");
        r.setTanggalPembukaan(LocalDate.now());
        r.setNominalSetoranAwal(new BigDecimal("500000"));
        r = rekeningRepository.save(r);

        nomorRekening = r.getNomorRekening();
    }

    @Test
    @WithMockUser(username = "teller1", roles = {"TELLER"})
    @DisplayName("Functional: Teller setor tunai via form POST, saldo bertambah")
    void testDepositFlow() throws Exception {
        BigDecimal saldoBefore = rekeningRepository.findByNomorRekening(nomorRekening)
                .orElseThrow().getSaldo();

        mockMvc.perform(post("/teller/transaction/deposit/" + nomorRekening)
                        .with(csrf())
                        .param("jumlahSetoran", "200000")
                        .param("keterangan", "Setoran Tunai Test"))
                .andExpect(status().is3xxRedirection());

        BigDecimal saldoAfter = rekeningRepository.findByNomorRekening(nomorRekening)
                .orElseThrow().getSaldo();

        assertEquals(saldoBefore.add(new BigDecimal("200000")).compareTo(saldoAfter), 0,
                "Saldo harus bertambah 200.000");
    }
}
