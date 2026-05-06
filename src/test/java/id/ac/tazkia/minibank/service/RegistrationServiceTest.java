package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.SignupForm;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RegistrationService Integration Tests")
class RegistrationServiceTest extends BaseIntegrationTest {

    @Autowired private RegistrationService registrationService;
    @Autowired private UserRepository userRepository;

    private SignupForm createForm() {
        SignupForm form = new SignupForm();
        form.setFullName("Test User");
        form.setDosenPembimbing("Dr. Test");
        form.setEmail("test@tazkia.ac.id");
        form.setUsername("testuser");
        form.setProdi("Informatika");
        form.setPassword("password123");
        form.setNim("12345");
        return form;
    }

    @Test
    @DisplayName("register - berhasil menyimpan user baru di DB")
    void register_success() {
        SignupForm form = createForm();
        long countBefore = userRepository.count();

        registrationService.register(form);

        assertTrue(userRepository.count() > countBefore);
        var saved = userRepository.findByUsername("testuser").orElseThrow();
        assertEquals("Test User", saved.getFullName());
        assertFalse(saved.isApproved());
        assertFalse(saved.isEnabled());
        assertFalse(saved.getRoles().isEmpty());
    }

    @Test
    @DisplayName("register - throw jika username sudah dipakai")
    void register_shouldThrow_whenUsernameExists() {
        registrationService.register(createForm());

        SignupForm dup = createForm();
        dup.setEmail("dup@tazkia.ac.id");
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(dup));
    }
}
