package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.StatusTransaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.service.SupervisorNasabahApprovalService;
import id.ac.tazkia.minibank.service.SupervisorRekeningApprovalService;
import id.ac.tazkia.minibank.service.SupervisorTransactionApprovalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupervisorTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupervisorTransactionApprovalService approvalService;

    @MockBean
    private SupervisorRekeningApprovalService rekeningApprovalService;

    @MockBean
    private SupervisorNasabahApprovalService nasabahApprovalService;

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testListTransactions() throws Exception {
        Transaksi tx = new Transaksi();
        tx.setId(UUID.randomUUID());
        tx.setNomorTransaksi("T1000001");
        tx.setTipe(TipeTransaksi.DEPOSIT);
        tx.setNomorRekening("54300000101");
        tx.setNamaRekening("Ahmad Nasabah");
        tx.setJumlah(new BigDecimal("10000000.00"));
        tx.setProcessedAt(LocalDateTime.now());
        tx.setProcessedBy("teller1");
        tx.setStatus(StatusTransaksi.PENDING_APPROVAL);

        when(approvalService.listPendingTransactions()).thenReturn(List.of(tx));
        when(approvalService.pendingCount()).thenReturn(1L);

        mockMvc.perform(get("/supervisor/transactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/transaction/list"))
                .andExpect(model().attributeExists("pendingTransactions"))
                .andExpect(model().attribute("pendingTransactionCount", 1L));
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testDetailTransaction() throws Exception {
        UUID id = UUID.randomUUID();
        Transaksi tx = new Transaksi();
        tx.setId(id);
        tx.setNomorTransaksi("T1000001");
        tx.setTipe(TipeTransaksi.DEPOSIT);
        tx.setNomorRekening("54300000101");
        tx.setNamaRekening("Ahmad Nasabah");
        tx.setJumlah(new BigDecimal("10000000.00"));
        tx.setProcessedAt(LocalDateTime.now());
        tx.setProcessedBy("teller1");
        tx.setStatus(StatusTransaksi.PENDING_APPROVAL);

        when(approvalService.getByIdOrThrow(id)).thenReturn(tx);

        mockMvc.perform(get("/supervisor/transactions/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/transaction/detail"))
                .andExpect(model().attributeExists("tx"));
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testApproveTransaction() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/supervisor/transactions/" + id + "/approve")
                        .with(csrf())
                        .param("notes", "Approve ok"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/transactions"))
                .andExpect(flash().attributeExists("success"));

        verify(approvalService).approve(id, "spv", "Approve ok");
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    void testRejectTransaction() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/supervisor/transactions/" + id + "/reject")
                        .with(csrf())
                        .param("reason", "Suspicious amount")
                        .param("notes", "Rejected by SPV"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/transactions"))
                .andExpect(flash().attributeExists("success"));

        verify(approvalService).reject(id, "spv", "Suspicious amount", "Rejected by SPV");
    }
}

