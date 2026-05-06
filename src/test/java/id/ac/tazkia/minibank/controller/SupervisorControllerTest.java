package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("SupervisorController Integration Tests")
class SupervisorControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private NasabahRepository nasabahRepository;

    @BeforeEach
    void setUp() {
        Nasabah n = new Nasabah();
        n.setCif("C9999001");
        n.setNik("9999000000000001");
        n.setNamaSesuaiIdentitas("Pending Nasabah");
        n.setStatus(NasabahStatus.INACTIVE);
        nasabahRepository.save(n);
    }

    @Test
    @DisplayName("GET /supervisor/dashboard - should return supervisor/dashboard view")
    void supervisorDashboard_shouldReturnView() throws Exception {
        mockMvc.perform(get("/supervisor/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/dashboard"))
                .andExpect(model().attributeExists("pendingCount", "products"));
    }
}
