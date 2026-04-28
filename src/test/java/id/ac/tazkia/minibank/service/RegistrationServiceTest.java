package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.SignupForm;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegistrationService Unit Tests")
class RegistrationServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

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
    @DisplayName("register - berhasil menyimpan user baru")
    void register_success() {
        SignupForm form = createForm();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("$2a$encoded");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role(1L, "ROLE_USER")));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        registrationService.register(form);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();

        assertEquals("testuser", saved.getUsername());
        assertEquals("$2a$encoded", saved.getPassword());
        assertEquals("Test User", saved.getFullName());
        assertFalse(saved.isApproved());
        assertFalse(saved.isEnabled());
        assertEquals(1, saved.getRoles().size());
    }

    @Test
    @DisplayName("register - throw jika username sudah dipakai")
    void register_shouldThrow_whenUsernameExists() {
        SignupForm form = createForm();
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(form));
        verify(userRepository, never()).save(any());
    }
}
