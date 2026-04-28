package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.SupervisorNasabahApprovalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SupervisorNasabahController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("SupervisorNasabahController Unit Tests")
class SupervisorNasabahControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupervisorNasabahApprovalService approvalService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /supervisor/nasabah - list pending")
    void list_shouldReturnPending() throws Exception {
        when(approvalService.listByStatuses(anyList())).thenReturn(List.of());
        when(approvalService.pendingCount()).thenReturn(0L);

        mockMvc.perform(get("/supervisor/nasabah").param("filter", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/nasabah/list"))
                .andExpect(model().attributeExists("list", "filter", "pendingCount"));
    }

    @Test
    @DisplayName("GET /supervisor/nasabah/{id} - detail view")
    void detail_shouldReturnView() throws Exception {
        Nasabah nasabah = new Nasabah();
        when(approvalService.getByIdOrThrow(1L)).thenReturn(nasabah);

        mockMvc.perform(get("/supervisor/nasabah/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/nasabah/detail"))
                .andExpect(model().attributeExists("nasabah"));
    }

    @Test
    @DisplayName("POST /supervisor/nasabah/{id}/approve - should redirect")
    void approve_shouldRedirect() throws Exception {
        when(userRepository.findByUsername("-")).thenReturn(Optional.empty());

        mockMvc.perform(post("/supervisor/nasabah/1/approve").param("notes", "ok"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah"))
                .andExpect(flash().attributeExists("success"));

        verify(approvalService).approve(1L, "-", "ok");
    }

    @Test
    @DisplayName("POST /supervisor/nasabah/{id}/reject - without reason should fail")
    void reject_withoutReason() throws Exception {
        mockMvc.perform(post("/supervisor/nasabah/1/reject").param("reason", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah/1"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /supervisor/nasabah/{id}/reject - with reason should redirect")
    void reject_withReason() throws Exception {
        when(userRepository.findByUsername("-")).thenReturn(Optional.empty());

        mockMvc.perform(post("/supervisor/nasabah/1/reject")
                        .param("reason", "bad")
                        .param("notes", "nope"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah"))
                .andExpect(flash().attributeExists("success"));

        verify(approvalService).reject(1L, "-", "bad", "nope");
    }
}
