package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.AdminUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AdminUserController Unit Tests")
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminUserService adminService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /admin/dashboard - should return admin/dashboard view")
    void dashboard_shouldReturnView() throws Exception {
        User user = new User();
        user.setId(1L);
        when(adminService.listPending()).thenReturn(List.of(user));

        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @DisplayName("GET /admin/approval/{id} - should return admin/approval view")
    void approvalDetail_shouldReturnView() throws Exception {
        User user = new User();
        user.setId(1L);
        when(adminService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/admin/approval/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/approval"))
                .andExpect(model().attributeExists("user", "maskedPassword", "maskedConfirm"));
    }

    @Test
    @DisplayName("POST /admin/approve/{id} - should redirect to dashboard")
    void approve_shouldRedirect() throws Exception {
        mockMvc.perform(post("/admin/approve/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/dashboard?approved"));

        verify(adminService).approve(1L);
    }

    @Test
    @DisplayName("POST /admin/reject/{id} - should redirect to dashboard")
    void reject_shouldRedirect() throws Exception {
        mockMvc.perform(post("/admin/reject/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/dashboard?rejected"));

        verify(adminService).reject(1L);
    }
}
