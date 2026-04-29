package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostalCodeImporter Unit Tests")
class PostalCodeImporterTest {

    @Mock
    private PostalCodeRepository postalCodeRepository;

    @InjectMocks
    private PostalCodeImporter postalCodeImporter;

    @Test
    @DisplayName("importIfEmpty - skip jika tabel sudah ada data")
    void importIfEmpty_skipsIfDataExists() {
        when(postalCodeRepository.count()).thenReturn(100L);

        postalCodeImporter.importIfEmpty();

        // Tidak ada saveAll yang dipanggil
        verify(postalCodeRepository, never()).saveAll(any());
    }

    @Test
    @DisplayName("importIfEmpty - import dari CSV jika tabel kosong")
    void importIfEmpty_importsCsvIfEmpty() {
        // Tabel kosong, akan membaca CSV
        when(postalCodeRepository.count()).thenReturn(0L).thenReturn(100L);
        when(postalCodeRepository.saveAll(any())).thenAnswer(inv -> inv.getArgument(0));

        postalCodeImporter.importIfEmpty();

        // Pastikan saveAll dipanggil minimal sekali (CSV ada dan tidak kosong)
        verify(postalCodeRepository, atLeastOnce()).saveAll(any());
    }
}
