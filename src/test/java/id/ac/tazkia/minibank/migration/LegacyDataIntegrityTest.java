package id.ac.tazkia.minibank.migration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.PeriodePraktikum;
import id.ac.tazkia.minibank.entity.PeriodeStatus;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.PeriodePraktikumRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Legacy Data Migration Integrity Tests")
class LegacyDataIntegrityTest extends BaseIntegrationTest {

    @Autowired
    private PeriodePraktikumRepository periodeRepo;

    @Autowired
    private NasabahRepository nasabahRepo;

    @Autowired
    private RekeningRepository rekeningRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Integritas JSON Dump: 162 nasabah, 396 rekening, 583 transaksi live ter-dump lengkap")
    void testJsonDumpIntegrity() throws Exception {
        ClassPathResource resource = new ClassPathResource("data/legacy_minibank_full_dump.json");
        assertTrue(resource.exists(), "File legacy_minibank_full_dump.json harus ada di classpath");

        try (InputStream is = resource.getInputStream()) {
            JsonNode root = objectMapper.readTree(is);
            assertNotNull(root);

            JsonNode metadata = root.get("metadata");
            assertNotNull(metadata);
            assertEquals(162, metadata.get("total_customers").asInt());
            assertEquals(396, metadata.get("total_accounts").asInt());
            assertEquals(583, metadata.get("total_transactions").asInt());

            JsonNode customers = root.get("customers");
            assertEquals(162, customers.size());
            JsonNode firstCust = customers.get(0);
            assertTrue(firstCust.get("cif").asText().startsWith("C"));
            assertFalse(firstCust.get("name").asText().isBlank());

            JsonNode accounts = root.get("accounts");
            assertEquals(396, accounts.size());
            JsonNode firstAcc = accounts.get(0);
            assertTrue(firstAcc.get("accountNumber").asText().startsWith("A"));

            JsonNode transactions = root.get("transactions");
            assertEquals(583, transactions.size());
            JsonNode firstTrx = transactions.get(0);
            assertTrue(firstTrx.get("trxNumber").asText().startsWith("T"));
        }
    }

    @Test
    @DisplayName("Integritas File Migrasi Flyway V8 & V9: File SQL ada dan memuat instruksi yang lengkap")
    void testFlywayScriptsIntegrity() throws Exception {
        ClassPathResource v8Resource = new ClassPathResource("db/migration/V8__create_dynamic_academic_period_and_fk.sql");
        assertTrue(v8Resource.exists(), "Migration V8 harus ada di classpath");
        try (InputStream is = v8Resource.getInputStream()) {
            String v8Sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(v8Sql.contains("CREATE TABLE IF NOT EXISTS periode_praktikum"));
            assertTrue(v8Sql.contains("REFERENCES periode_praktikum(id)"));
        }

        ClassPathResource v9Resource = new ClassPathResource("db/migration/V9__seed_legacy_minibank_2025_data.sql");
        assertTrue(v9Resource.exists(), "Migration V9 harus ada di classpath");
        try (InputStream is = v9Resource.getInputStream()) {
            String v9Sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(v9Sql.contains("SEED 162 LEGACY CUSTOMERS"));
            assertTrue(v9Sql.contains("C1000399"));
            assertTrue(v9Sql.contains("A2000402"));
            assertTrue(v9Sql.contains("T3000588"));
            assertTrue(v9Sql.contains("nasabah_id_seq"));
        }
    }

    @Test
    @DisplayName("Persistensi JPA: Entitas Nasabah dan Rekening dapat ditautkan ke PeriodePraktikum")
    void testJpaPeriodLinking() {
        PeriodePraktikum p2025 = PeriodePraktikum.builder()
                .kode("2025/2026")
                .nama("Praktikum Minibank 2025/2026 (Arsip)")
                .status(PeriodeStatus.ARCHIVED)
                .isActive(false)
                .build();
        PeriodePraktikum savedPeriod = periodeRepo.save(p2025);

        Nasabah n = new Nasabah();
        n.setCif("C1000399");
        n.setNamaSesuaiIdentitas("Tamamun Nadzir");
        n.setNik("3525000000000000");
        n.setPeriode(savedPeriod);
        Nasabah savedNasabah = nasabahRepo.save(n);

        assertNotNull(savedNasabah.getId());
        assertEquals("C1000399", savedNasabah.getCif());
        assertEquals(savedPeriod.getId(), savedNasabah.getPeriode().getId());

        Rekening r = new Rekening();
        r.setNomorRekening("A2000402");
        r.setNamaRekening("Fadil Muhammad Ar - Rasyid - Business");
        r.setCifNasabah(savedNasabah.getCif());
        r.setNasabah(savedNasabah);
        r.setPeriode(savedPeriod);
        Rekening savedRekening = rekeningRepo.save(r);

        assertNotNull(savedRekening.getId());
        assertEquals("A2000402", savedRekening.getNomorRekening());
        assertEquals(savedPeriod.getId(), savedRekening.getPeriode().getId());
    }
}
