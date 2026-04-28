package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.PostalCodeDto;
import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostalCodeService Unit Tests")
class PostalCodeServiceTest {

    @Mock private PostalCodeRepository postalCodeRepository;

    @InjectMocks
    private PostalCodeService postalCodeService;

    @Test
    @DisplayName("findByKodePos - mengembalikan PostalCodeDto jika ditemukan")
    void findByKodePos_found() {
        PostalCode pc = new PostalCode();
        pc.setKodePos("16810");
        pc.setProvinsi("Jawa Barat");
        pc.setKota("Bogor");
        pc.setKecamatan("Sukaraja");
        pc.setKelurahan("Babakan");
        when(postalCodeRepository.findFirstByKodePos("16810")).thenReturn(Optional.of(pc));

        Optional<PostalCodeDto> result = postalCodeService.findByKodePos("16810");

        assertTrue(result.isPresent());
        assertEquals("16810", result.get().getKodePos());
        assertEquals("Jawa Barat", result.get().getProvinsi());
    }

    @Test
    @DisplayName("findByKodePos - return empty jika tidak ditemukan")
    void findByKodePos_notFound() {
        when(postalCodeRepository.findFirstByKodePos("99999")).thenReturn(Optional.empty());
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
    @DisplayName("getAllProvinces - mengembalikan list provinsi")
    void getAllProvinces() {
        when(postalCodeRepository.findDistinctProvinsi())
                .thenReturn(List.of("Jawa Barat", "Jawa Tengah"));
        List<String> result = postalCodeService.getAllProvinces();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getCitiesByProvince - mengembalikan list kota")
    void getCitiesByProvince() {
        when(postalCodeRepository.findDistinctKotaByProvinsi("Jawa Barat"))
                .thenReturn(List.of("Bogor", "Bandung"));
        List<String> result = postalCodeService.getCitiesByProvince("Jawa Barat");
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getDistricts - mengembalikan list kecamatan")
    void getDistricts() {
        when(postalCodeRepository.findDistinctKecamatan("Jawa Barat", "Bogor"))
                .thenReturn(List.of("Sukaraja"));
        List<String> result = postalCodeService.getDistricts("Jawa Barat", "Bogor");
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("getVillages - mengembalikan list kelurahan")
    void getVillages() {
        when(postalCodeRepository.findDistinctKelurahan("Jawa Barat", "Bogor", "Sukaraja"))
                .thenReturn(List.of("Babakan", "Ciawi"));
        List<String> result = postalCodeService.getVillages("Jawa Barat", "Bogor", "Sukaraja");
        assertEquals(2, result.size());
    }
}
