package id.ac.tazkia.minibank.integration;

import id.ac.tazkia.minibank.entity.*;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Supervisor Approval - Integration Test")
class SupervisorApprovalIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private NasabahRepository nasabahRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    private Long nasabahId;

    @BeforeEach
    void setUp() {
        // Buat nasabah INACTIVE (pending approval)
        Nasabah n = new Nasabah();
        n.setCif("C8888001");
        n.setNik("8888000000000001");
        n.setNamaSesuaiIdentitas("Test Pending Nasabah");
        n.setStatus(NasabahStatus.INACTIVE);
        n = nasabahRepository.save(n);
        nasabahId = n.getId();

        // Pastikan user supervisor ada di DB untuk lookup fullName
        if (userRepository.findByUsername("supervisor1").isEmpty()) {
            // Pastikan role ada
            if (roleRepository.findByName("ROLE_SUPERVISOR").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_SUPERVISOR"));
            }
            User u = new User();
            u.setUsername("supervisor1");
            u.setPassword("$2a$10$dummy");
            u.setEmail("supervisor@tazkia.ac.id");
            u.setFullName("Supervisor Satu");
            u.setApproved(true);
            u.setEnabled(true);
            userRepository.save(u);
        }
    }

    @Test
    @WithMockUser(username = "supervisor1", roles = {"SUPERVISOR"})
    @DisplayName("Functional: Supervisor approve nasabah INACTIVE → ACTIVE")
    void testApproveNasabahFlow() throws Exception {
        // Verifikasi status awal = INACTIVE
        Nasabah before = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.INACTIVE, before.getStatus());

        // Supervisor klik tombol approve
        mockMvc.perform(post("/supervisor/nasabah/" + nasabahId + "/approve")
                        .with(csrf())
                        .param("notes", "Data lengkap, disetujui"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/supervisor/nasabah"));

        // Verifikasi status berubah di database
        Nasabah after = nasabahRepository.findById(nasabahId).orElseThrow();
        assertEquals(NasabahStatus.ACTIVE, after.getStatus());
        assertNotNull(after.getApprovedBy());
        assertNotNull(after.getApprovedAt());
    }
}
