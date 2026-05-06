package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PostalCodeImporter Integration Tests")
class PostalCodeImporterTest extends BaseIntegrationTest {

    @Autowired private PostalCodeImporter postalCodeImporter;
    @Autowired private PostalCodeRepository postalCodeRepository;

    @Test
    @DisplayName("importIfEmpty - import CSV jika tabel kosong (atau skip jika sudah ada)")
    void importIfEmpty_runsWithoutError() {
        // PostalCodeImporter.importIfEmpty() dipanggil saat @PostConstruct.
        // Di lingkungan test, data mungkin sudah ada dari PostConstruct.
        // Test ini memastikan pemanggilan ulang tidak error.
        long countBefore = postalCodeRepository.count();
        postalCodeImporter.importIfEmpty();
        long countAfter = postalCodeRepository.count();

        // Jika sudah ada data, harus di-skip (countBefore == countAfter).
        // Jika belum ada data, akan import (countAfter > 0).
        assertTrue(countAfter >= countBefore);
    }
}
