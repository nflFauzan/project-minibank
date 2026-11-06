package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.RekeningStatus;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.SupervisorRekeningApprovalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupervisorAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupervisorRekeningApprovalService approvalService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testListPendingAccounts() throws Exception {
        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        r.setStatus(RekeningStatus.PENDING);

        when(approvalService.listByStatuses(any())).thenReturn(List.of(r));
        when(approvalService.pendingCount()).thenReturn(1L);

        mockMvc.perform(get("/supervisor/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/account/list"))
                .andExpect(model().attributeExists("list", "pendingCount"));
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testApproveAccount() throws Exception {
        mockMvc.perform(post("/supervisor/account/1/approve")
                        .param("notes", "Disetujui")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/account"));

        verify(approvalService).approve(eq(1L), anyString(), eq("Disetujui"));
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testRejectAccountWithoutReasonRedirectsWithError() throws Exception {
        mockMvc.perform(post("/supervisor/account/1/reject")
                        .param("reason", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/account/1"))
                .andExpect(flash().attributeExists("error"));

        verify(approvalService, never()).reject(anyLong(), anyString(), anyString(), any());
    }
}

