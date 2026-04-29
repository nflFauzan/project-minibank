package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.TellerTransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TellerTransferController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("TellerTransferController Unit Tests")
class TellerTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TellerTransferService tellerTransferService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /teller/transfer - list")
    void listRekening() throws Exception {
        mockMvc.perform(get("/teller/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transfer/list"));
    }

    @Test
    @DisplayName("GET /teller/transfer/form - show form")
    void formTransfer() throws Exception {
        mockMvc.perform(get("/teller/transfer/form").param("sumber", "SRC-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transfer/form"))
                .andExpect(model().attribute("rekeningSumber", "SRC-123"));
    }

    @Test
    @DisplayName("POST /teller/transfer - submit success")
    void submitTransfer_success() throws Exception {
        UUID groupId = UUID.randomUUID();
        when(tellerTransferService.transfer(any(), any(), any(), any(), any(), any())).thenReturn(groupId);

        mockMvc.perform(post("/teller/transfer")
                        .param("rekeningSumber", "SRC-123")
                        .param("rekeningTujuan", "DST-123")
                        .param("jumlah", "50000")
                        .principal(new UsernamePasswordAuthenticationToken("user", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/" + groupId))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /teller/transfer - submit fail")
    void submitTransfer_fail() throws Exception {
        doThrow(new RuntimeException("Error")).when(tellerTransferService)
                .transfer(any(), any(), any(), any(), any(), any());

        mockMvc.perform(post("/teller/transfer")
                        .param("rekeningSumber", "SRC-123")
                        .param("rekeningTujuan", "DST-123")
                        .param("jumlah", "50000")
                        .principal(new UsernamePasswordAuthenticationToken("user", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transfer/form?sumber=SRC-123"))
                .andExpect(flash().attributeExists("error"));
    }
}
