package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.report.ReportSummaryDto;
import id.ac.tazkia.minibank.service.ReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    @DisplayName("GET /reports - displays daily report by default")
    void getReportsDefault_shouldRenderDaily() throws Exception {
        ReportSummaryDto dto = ReportSummaryDto.builder()
                .periodType("DAILY")
                .periodLabel("08 November 2026")
                .totalTransactions(5)
                .totalDebit(BigDecimal.ZERO)
                .totalKredit(BigDecimal.ZERO)
                .netFlow(BigDecimal.ZERO)
                .newAccountsCount(2)
                .newCustomersCount(1)
                .transactions(List.of())
                .build();

        when(reportService.generateReport(any(), any(), any(), any())).thenReturn(dto);

        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(view().name("reports/index"))
                .andExpect(model().attributeExists("report"))
                .andExpect(model().attribute("periodType", "DAILY"))
                .andExpect(model().attribute("active", "reports"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("GET /reports?period=MONTHLY - displays monthly report")
    void getReportsMonthly_shouldRenderMonthly() throws Exception {
        ReportSummaryDto dto = ReportSummaryDto.builder()
                .periodType("MONTHLY")
                .periodLabel("November 2026")
                .totalTransactions(10)
                .totalDebit(new BigDecimal("1000000"))
                .totalKredit(new BigDecimal("5000000"))
                .netFlow(new BigDecimal("4000000"))
                .newAccountsCount(8)
                .newCustomersCount(5)
                .transactions(List.of())
                .build();

        when(reportService.generateReport(any(), any(), any(), any())).thenReturn(dto);

        mockMvc.perform(get("/reports")
                        .param("period", "MONTHLY")
                        .param("month", "11")
                        .param("year", "2026"))
                .andExpect(status().isOk())
                .andExpect(view().name("reports/index"))
                .andExpect(model().attribute("periodType", "MONTHLY"));
    }
}

