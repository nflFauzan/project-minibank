package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PostalCodeImporter - Additional Integration Tests")
class PostalCodeImporterAdditionalTest extends BaseIntegrationTest {

    @Autowired private PostalCodeImporter postalCodeImporter;
    @Autowired private PostalCodeRepository originalPostalCodeRepository;

    private PostalCodeRepository mockPostalCodeRepository;

    @BeforeEach
    void setUpMocks() {
        mockPostalCodeRepository = mock(PostalCodeRepository.class);
        ReflectionTestUtils.setField(postalCodeImporter, "postalCodeRepository", mockPostalCodeRepository);
    }

    @AfterEach
    void restoreOriginals() {
        ReflectionTestUtils.setField(postalCodeImporter, "postalCodeRepository", originalPostalCodeRepository);
    }

    @Test
    @DisplayName("importIfEmpty - jika tabel sudah ada data (count > 0) - harus skip import")
    void importIfEmpty_tableNotEmpty_shouldSkip() {
        when(mockPostalCodeRepository.count()).thenReturn(100L);

        postalCodeImporter.importIfEmpty();

        verify(mockPostalCodeRepository, times(1)).count();
        verify(mockPostalCodeRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("importIfEmpty - data valid & batching import - sukses")
    void importIfEmpty_validData_success() {
        when(mockPostalCodeRepository.count()).thenReturn(0L);

        try (MockedConstruction<BufferedReader> mocked = Mockito.mockConstruction(BufferedReader.class,
                (mock, context) -> {
                    when(mock.readLine())
                            .thenReturn("kode_pos,provinsi,kota,kecamatan,kelurahan") // Header
                            .thenReturn("10110,DKI Jakarta,Jakarta Pusat,Gambir,Gambir") // Row 1
                            .thenReturn("10120,DKI Jakarta,Jakarta Pusat,Gambir,Kebon Kelapa") // Row 2
                            .thenReturn(null); // EOF
                })) {

            postalCodeImporter.importIfEmpty();

            verify(mockPostalCodeRepository, times(1)).saveAll(anyList());
        }
    }

    @Test
    @DisplayName("importIfEmpty - row kosong & malformed row (< 5 kolom) - baris dilewati dan import lanjut")
    void importIfEmpty_malformedAndEmptyRow_shouldSkipRowsAndContinue() {
        when(mockPostalCodeRepository.count()).thenReturn(0L);

        try (MockedConstruction<BufferedReader> mocked = Mockito.mockConstruction(BufferedReader.class,
                (mock, context) -> {
                    when(mock.readLine())
                            .thenReturn("header")
                            .thenReturn("10110,DKI Jakarta,Jakarta Pusat") // Malformed (3 kolom)
                            .thenReturn("   ") // Empty row
                            .thenReturn("10120,DKI Jakarta,Jakarta Pusat,Gambir,Kebon Kelapa") // Valid
                            .thenReturn(null);
                })) {

            postalCodeImporter.importIfEmpty();

            verify(mockPostalCodeRepository, times(1)).saveAll(argThat((List<PostalCode> list) -> {
                assertEquals(1, list.size());
                assertEquals("10120", list.get(0).getKodePos());
                return true;
            }));
        }
    }

    @Test
    @DisplayName("importIfEmpty - terjadi IOException saat readLine - ditangkap dan diselesaikan tanpa crash")
    void importIfEmpty_ioExceptionOnRead_shouldCatchAndNotCrash() {
        when(mockPostalCodeRepository.count()).thenReturn(0L);

        try (MockedConstruction<BufferedReader> mocked = Mockito.mockConstruction(BufferedReader.class,
                (mock, context) -> {
                    when(mock.readLine())
                            .thenReturn("header")
                            .thenThrow(new IOException("Disk read error"));
                })) {

            assertDoesNotThrow(() -> postalCodeImporter.importIfEmpty());
            verify(mockPostalCodeRepository, never()).saveAll(anyList());
        }
    }

    @Test
    @DisplayName("importIfEmpty - saveAll melempar eksepsi - ditangkap dan tidak crash")
    void importIfEmpty_saveAllThrowsException_shouldCatchAndNotCrash() {
        when(mockPostalCodeRepository.count()).thenReturn(0L);
        doThrow(new RuntimeException("Database down")).when(mockPostalCodeRepository).saveAll(anyList());

        try (MockedConstruction<BufferedReader> mocked = Mockito.mockConstruction(BufferedReader.class,
                (mock, context) -> {
                    when(mock.readLine())
                            .thenReturn("header")
                            .thenReturn("10110,DKI Jakarta,Jakarta Pusat,Gambir,Gambir")
                            .thenReturn(null);
                })) {

            assertDoesNotThrow(() -> postalCodeImporter.importIfEmpty());
        }
    }
}
