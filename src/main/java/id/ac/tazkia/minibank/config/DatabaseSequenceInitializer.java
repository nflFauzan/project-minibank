package id.ac.tazkia.minibank.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class DatabaseSequenceInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS trx_seq START WITH 1 INCREMENT BY 1");
            log.info("Initialized database sequence: trx_seq");
        } catch (Exception e) {
            log.warn("Sequence trx_seq creation notice: {}", e.getMessage());
        }

        try {
            jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS passbook_no_seq START WITH 1 INCREMENT BY 1");
            log.info("Initialized database sequence: passbook_no_seq");
        } catch (Exception e) {
            log.warn("Sequence passbook_no_seq creation notice: {}", e.getMessage());
        }
    }
}
