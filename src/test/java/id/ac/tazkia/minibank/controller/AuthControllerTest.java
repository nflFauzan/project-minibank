package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("AuthController Integration Tests")
class AuthControllerTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("GET /login - menampilkan halaman login")
    void login_shouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("GET /signup - menampilkan halaman signup")
    void signup_shouldReturnSignupView() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("form"));
    }

    @Test
    @DisplayName("POST /signup - berhasil registrasi user baru & tersimpan di DB")
    void signup_success() throws Exception {
        long countBefore = userRepository.count();

        mockMvc.perform(post("/signup")
                        .param("username", "newuser")
                        .param("password", "password123")
                        .param("fullName", "New User")
                        .param("email", "new@tazkia.ac.id")
                        .param("dosenPembimbing", "Dr. Test")
                        .param("prodi", "Informatika")
                        .param("nim", "99999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        assertTrue(userRepository.count() > countBefore);
        assertTrue(userRepository.findByUsername("newuser").isPresent());
    }

    @Test
    @DisplayName("POST /signup - gagal jika username sudah ada")
    void signup_duplicateUsername() throws Exception {
        // Register pertama
        mockMvc.perform(post("/signup")
                .param("username", "dupuser")
                .param("password", "password123")
                .param("fullName", "Dup User")
                .param("email", "dup@tazkia.ac.id")
                .param("dosenPembimbing", "Dr. Test")
                .param("prodi", "Informatika")
                .param("nim", "88888"))
                .andExpect(status().is3xxRedirection());

        // Register kedua dengan username sama → controller tidak catch exception,
        // sehingga MockMvc melempar ServletException yang membungkus IllegalArgumentException
        Exception ex = assertThrows(Exception.class, () ->
                mockMvc.perform(post("/signup")
                        .param("username", "dupuser")
                        .param("password", "password456")
                        .param("fullName", "Dup User 2")
                        .param("email", "dup2@tazkia.ac.id")
                        .param("dosenPembimbing", "Dr. Test2")
                        .param("prodi", "Informatika")
                        .param("nim", "77777"))
        );
        assertTrue(ex.getMessage().contains("Username sudah dipakai")
                || ex.getCause().getMessage().contains("Username sudah dipakai"));
    }
}
