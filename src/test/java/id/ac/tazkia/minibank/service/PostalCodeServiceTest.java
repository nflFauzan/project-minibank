package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.PostalCodeDto;
import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PostalCodeService Integration Tests")
class PostalCodeServiceTest extends BaseIntegrationTest {

    @Autowired private PostalCodeService postalCodeService;
    @Autowired private PostalCodeRepository postalCodeRepository;

    @BeforeEach
    void setUp() {
        PostalCode pc = new PostalCode();
        pc.setKodePos("99810");
        pc.setProvinsi("Test Provinsi");
        pc.setKota("Test Kota");
        pc.setKecamatan("Test Kecamatan");
        pc.setKelurahan("Test Kelurahan");
        postalCodeRepository.save(pc);
    }

    @Test
    @DisplayName("findByKodePos - mengembalikan PostalCodeDto jika ditemukan di DB")
    void findByKodePos_found() {
        Optional<PostalCodeDto> result = postalCodeService.findByKodePos("99810");
        assertTrue(result.isPresent());
        assertEquals("99810", result.get().getKodePos());
        assertEquals("Test Provinsi", result.get().getProvinsi());
    }

    @Test
    @DisplayName("findByKodePos - return empty jika tidak ditemukan")
    void findByKodePos_notFound() {
        Optional<PostalCodeDto> result = postalCodeService.findByKodePos("99999");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findByKodePos - return empty jika input null")
    void findByKodePos_null() {
        Optional<PostalCodeDto> result = postalCodeService.findByKodePos(null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getAllProvinces - mengembalikan list provinsi dari DB")
    void getAllProvinces() {
        List<String> result = postalCodeService.getAllProvinces();
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("getCitiesByProvince - mengembalikan list kota dari DB")
    void getCitiesByProvince() {
        List<String> result = postalCodeService.getCitiesByProvince("Test Provinsi");
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("getDistricts - mengembalikan list kecamatan dari DB")
    void getDistricts() {
        List<String> result = postalCodeService.getDistricts("Test Provinsi", "Test Kota");
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("getVillages - mengembalikan list kelurahan dari DB")
    void getVillages() {
        List<String> result = postalCodeService.getVillages("Test Provinsi", "Test Kota", "Test Kecamatan");
        assertFalse(result.isEmpty());
    }
}
