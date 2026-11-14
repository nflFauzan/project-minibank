package id.ac.tazkia.minibank.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DatabaseSequenceInitializer Tests")
class DatabaseSequenceInitializerTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DatabaseSequenceInitializer initializer;

    @Test
    @DisplayName("Successfully executes creation of trx_seq and passbook_no_seq")
    void testRun_executesSequences() {
        initializer.run();

        verify(jdbcTemplate).execute(contains("trx_seq"));
        verify(jdbcTemplate).execute(contains("passbook_no_seq"));
    }

    @Test
    @DisplayName("Handles database exceptions gracefully without failing startup")
    void testRun_handlesExceptionsGracefully() {
        doThrow(new RuntimeException("Simulated SQL error")).when(jdbcTemplate).execute(anyString());

        assertDoesNotThrow(() -> initializer.run());
    }
}
