package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.service.DashboardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SupervisorController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("SupervisorController Unit Tests")
class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @MockBean
    private NasabahRepository nasabahRepository;

    @Test
    @DisplayName("GET /supervisor/dashboard - should return supervisor/dashboard view")
    void supervisorDashboard_shouldReturnView() throws Exception {
        when(nasabahRepository.countByStatus(NasabahStatus.INACTIVE)).thenReturn(5L);
        when(dashboardService.getActiveProdukTabungan()).thenReturn(List.of());

        mockMvc.perform(get("/supervisor/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/dashboard"))
                .andExpect(model().attributeExists("pendingCount", "products"));
    }
}
