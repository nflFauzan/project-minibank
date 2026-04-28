package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocationApiController.class)
@DisplayName("LocationApiController Unit Tests")
@WithMockUser
class LocationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostalCodeRepository postalCodeRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /api/postal-code/{kodePos} - return data")
    void byKodePos_shouldReturnData() throws Exception {
        PostalCode pc = new PostalCode();
        pc.setKodePos("16810");
        pc.setProvinsi("Jawa Barat");
        pc.setKota("Bogor");
        pc.setKecamatan("Babakan Madang");
        pc.setKelurahan("Sentul");
        when(postalCodeRepository.findFirstByKodePos("16810")).thenReturn(Optional.of(pc));

        mockMvc.perform(get("/api/postal-code/16810"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kodePos").value("16810"))
                .andExpect(jsonPath("$.provinsi").value("Jawa Barat"));
    }

    @Test
    @DisplayName("GET /api/postal-code/provinces - return list of provinces")
    void provinces_shouldReturnList() throws Exception {
        when(postalCodeRepository.findDistinctProvinsi()).thenReturn(List.of("Jawa Barat", "DKI Jakarta"));

        mockMvc.perform(get("/api/postal-code/provinces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Jawa Barat"))
                .andExpect(jsonPath("$[1]").value("DKI Jakarta"));
    }

    @Test
    @DisplayName("GET /api/postal-code/cities - return list of cities")
    void cities_shouldReturnList() throws Exception {
        when(postalCodeRepository.findDistinctKotaByProvinsi("Jawa Barat")).thenReturn(List.of("Bogor", "Bandung"));

        mockMvc.perform(get("/api/postal-code/cities").param("prov", "Jawa Barat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Bogor"))
                .andExpect(jsonPath("$[1]").value("Bandung"));
    }

    @Test
    @DisplayName("GET /api/postal-code/districts - return list of districts")
    void districts_shouldReturnList() throws Exception {
        when(postalCodeRepository.findDistinctKecamatan("Jawa Barat", "Bogor")).thenReturn(List.of("Babakan Madang"));

        mockMvc.perform(get("/api/postal-code/districts")
                        .param("prov", "Jawa Barat")
                        .param("kota", "Bogor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Babakan Madang"));
    }

    @Test
    @DisplayName("GET /api/postal-code/villages - return list of villages")
    void villages_shouldReturnList() throws Exception {
        when(postalCodeRepository.findDistinctKelurahan("Jawa Barat", "Bogor", "Babakan Madang")).thenReturn(List.of("Sentul"));

        mockMvc.perform(get("/api/postal-code/villages")
                        .param("prov", "Jawa Barat")
                        .param("kota", "Bogor")
                        .param("kec", "Babakan Madang"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Sentul"));
    }
}
