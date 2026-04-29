package id.ac.tazkia.minibank.controller;
 
import id.ac.tazkia.minibank.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = DashboardController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("DashboardController Unit Tests")
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @org.springframework.boot.test.mock.mockito.MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /dashboard - admin role")
    void dashboard_admin() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        mockMvc.perform(get("/dashboard").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard-admin"));
    }

    @Test
    @DisplayName("GET /dashboard - cs role")
    void dashboard_cs() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority("ROLE_CS")));

        mockMvc.perform(get("/dashboard").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard-cs"));
    }

    @Test
    @DisplayName("GET /dashboard - teller role")
    void dashboard_teller() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority("ROLE_TELLER")));

        mockMvc.perform(get("/dashboard").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard-teller"));
    }

    @Test
    @DisplayName("GET /dashboard - manager role")
    void dashboard_manager() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));

        mockMvc.perform(get("/dashboard").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard-manager"));
    }

    @Test
    @DisplayName("GET /dashboard - no specific role")
    void dashboard_other() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "user", "pass", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        mockMvc.perform(get("/dashboard").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }
}
