package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("LocationApiController Integration Tests")
class LocationApiControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private PostalCodeRepository postalCodeRepository;

    @BeforeEach
    void setUp() {
        PostalCode pc = new PostalCode();
        pc.setKodePos("99810");
        pc.setProvinsi("TEST PROVINSI");
        pc.setKota("Test Kota");
        pc.setKecamatan("Test Kecamatan");
        pc.setKelurahan("Test Kelurahan");
        postalCodeRepository.save(pc);

        PostalCode pc2 = new PostalCode();
        pc2.setKodePos("99820");
        pc2.setProvinsi("TEST PROVINSI 2");
        pc2.setKota("Test Kota 2");
        pc2.setKecamatan("Test Kecamatan 2");
        pc2.setKelurahan("Test Kelurahan 2");
        postalCodeRepository.save(pc2);
    }

    @Test
    @DisplayName("GET /api/postal-code/{kodePos} - return data")
    void byKodePos_shouldReturnData() throws Exception {
        mockMvc.perform(get("/api/postal-code/99810"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kodePos").value("99810"))
                .andExpect(jsonPath("$.provinsi").value("TEST PROVINSI"));
    }

    @Test
    @DisplayName("GET /api/postal-code/provinces - return list of provinces")
    void provinces_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/postal-code/provinces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/postal-code/cities - return list of cities")
    void cities_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/postal-code/cities").param("prov", "TEST PROVINSI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Test Kota"));
    }

    @Test
    @DisplayName("GET /api/postal-code/districts - return list of districts")
    void districts_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/postal-code/districts")
                        .param("prov", "TEST PROVINSI")
                        .param("kota", "Test Kota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Test Kecamatan"));
    }

    @Test
    @DisplayName("GET /api/postal-code/villages - return list of villages")
    void villages_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/postal-code/villages")
                        .param("prov", "TEST PROVINSI")
                        .param("kota", "Test Kota")
                        .param("kec", "Test Kecamatan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Test Kelurahan"));
    }
}
