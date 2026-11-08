package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.audit.UserActivityFilterDto;
import id.ac.tazkia.minibank.entity.ActivityType;
import id.ac.tazkia.minibank.entity.UserActivityLog;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.UserActivityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserActivityService userActivityService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Admin dapat mengakses halaman audit activities")
    void testAdminAccessAuditActivities() throws Exception {
        UserActivityLog log = new UserActivityLog("cs1", "CS One", "ROLE_CS",
                ActivityType.CUSTOMER_REGISTER, "CUSTOMER", "Registrasi nasabah", "C0000001", null, "SUCCESS");

        when(userActivityService.searchActivities(any(UserActivityFilterDto.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(log)));
        when(userRepository.findAll(any(Sort.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/audit/activities"))
                .andExpect(status().isOk())
                .andExpect(view().name("audit/activities"))
                .andExpect(model().attributeExists("activities", "page", "usersList", "modulesList"));
    }

    @Test
    @WithMockUser(username = "spv", roles = {"SUPERVISOR"})
    @DisplayName("Supervisor dapat mengakses halaman audit activities")
    void testSupervisorAccessAuditActivities() throws Exception {
        when(userActivityService.searchActivities(any(UserActivityFilterDto.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        when(userRepository.findAll(any(Sort.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/audit/activities"))
                .andExpect(status().isOk())
                .andExpect(view().name("audit/activities"));
    }

    @Test
    @WithMockUser(username = "teller1", roles = {"TELLER"})
    @DisplayName("Teller dilarang mengakses halaman audit activities (403 Forbidden)")
    void testTellerForbiddenAccess() throws Exception {
        mockMvc.perform(get("/audit/activities"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Export CSV aktivitas berhasil dengan header yang tepat")
    void testExportCsv() throws Exception {
        UserActivityLog log = new UserActivityLog("cs1", "CS One", "ROLE_CS",
                ActivityType.CUSTOMER_REGISTER, "CUSTOMER", "Registrasi nasabah", "C0000001", null, "SUCCESS");

        when(userActivityService.searchActivities(any(UserActivityFilterDto.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(log)));

        mockMvc.perform(get("/audit/activities/export"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"audit_activities.csv\""))
                .andExpect(content().contentType("text/csv; charset=UTF-8"));
    }
}

