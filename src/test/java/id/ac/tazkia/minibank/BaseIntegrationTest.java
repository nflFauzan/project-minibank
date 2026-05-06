package id.ac.tazkia.minibank;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Kelas abstrak dasar untuk semua Integration Test.
 *
 * <p>Lifecycle JUnit 5 lengkap yang dipakai:
 * <ol>
 *   <li>{@code @BeforeAll}  — Dijalankan SEKALI sebelum semua test di kelas (static).</li>
 *   <li>{@code @BeforeEach}  — Dijalankan SEBELUM setiap method test (seeding data).
 *       Didefinisikan di masing-masing subclass.</li>
 *   <li>{@code @Test}         — Method test itu sendiri.</li>
 *   <li>{@code @AfterEach}   — Dijalankan SETELAH setiap method test (cleanup/log).
 *       {@code @Transactional} sudah otomatis rollback,
 *       tapi kita tetap jalankan untuk demonstrasi lifecycle.</li>
 *   <li>{@code @AfterAll}    — Dijalankan SEKALI setelah semua test di kelas selesai (static).</li>
 * </ol>
 *
 * <p>Anotasi kunci:
 * <ul>
 *   <li>{@code @SpringBootTest} — Boot penuh Spring context.</li>
 *   <li>{@code @AutoConfigureMockMvc(addFilters = false)} — MockMvc tanpa security filter.</li>
 *   <li>{@code @ActiveProfiles("test")} — Pakai {@code application-test.properties} (H2).</li>
 *   <li>{@code @Transactional} — Setiap test di-rollback otomatis setelah selesai.</li>
 * </ul>
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(BaseIntegrationTest.class);

    // ==================== LIFECYCLE 1: @BeforeAll ====================
    @BeforeAll
    static void initSuite(TestInfo testInfo) {
        log.info("═══════════════════════════════════════════════════════════════");
        log.info("▶ SUITE START: {}", testInfo.getDisplayName());
        log.info("═══════════════════════════════════════════════════════════════");
    }

    // ==================== LIFECYCLE 2: @BeforeEach ====================
    // → Didefinisikan oleh masing-masing subclass sebagai setUp()
    //   untuk seeding data ke database H2.

    // ==================== LIFECYCLE 3: @Test ====================
    // → Didefinisikan oleh masing-masing subclass.

    // ==================== LIFECYCLE 4: @AfterEach ====================
    @AfterEach
    void tearDownBase(TestInfo testInfo) {
        log.info("  ✔ SELESAI: {} → data di-rollback oleh @Transactional",
                testInfo.getDisplayName());
    }

    // ==================== LIFECYCLE 5: @AfterAll ====================
    @AfterAll
    static void tearDownSuite(TestInfo testInfo) {
        log.info("═══════════════════════════════════════════════════════════════");
        log.info("■ SUITE END: {}", testInfo.getDisplayName());
        log.info("═══════════════════════════════════════════════════════════════");
    }
}
