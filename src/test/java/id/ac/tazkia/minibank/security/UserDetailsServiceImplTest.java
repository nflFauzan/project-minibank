package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserDetailsServiceImpl Unit Tests")
class UserDetailsServiceImplTest {

    @Mock private UserRepository userRepo;
    @InjectMocks private UserDetailsServiceImpl service;

    private User createApprovedUser() {
        User u = new User();
        u.setUsername("testuser");
        u.setPassword("$2a$encoded");
        u.setEmail("test@test.com");
        u.setFullName("Test User");
        u.setApproved(true);
        u.setEnabled(true);
        Role r = new Role(1L, "ROLE_CS");
        u.setRoles(Set.of(r));
        return u;
    }

    @Test
    @DisplayName("loadUser - berhasil dengan roles ter-mapping")
    void loadUser_success() {
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(createApprovedUser()));
        UserDetails ud = service.loadUserByUsername("testuser");
        assertEquals("testuser", ud.getUsername());
        assertTrue(ud.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CS")));
    }

    @Test
    @DisplayName("loadUser - throw jika user tidak ditemukan")
    void loadUser_shouldThrow_whenNotFound() {
        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("unknown"));
    }

    @Test
    @DisplayName("loadUser - throw jika user belum approved")
    void loadUser_shouldThrow_whenNotApproved() {
        User u = createApprovedUser();
        u.setApproved(false);
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(u));
        assertThrows(DisabledException.class,
                () -> service.loadUserByUsername("testuser"));
    }

    @Test
    @DisplayName("loadUser - role tanpa prefix ROLE_ ditambahkan otomatis")
    void loadUser_shouldAddRolePrefix() {
        User u = createApprovedUser();
        Role r = new Role(2L, "ADMIN"); // tanpa ROLE_
        u.setRoles(Set.of(r));
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(u));
        UserDetails ud = service.loadUserByUsername("testuser");
        assertTrue(ud.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("loadUser - roles kosong default ke ROLE_USER")
    void loadUser_emptyRoles_shouldDefaultToRoleUser() {
        User u = createApprovedUser();
        u.setRoles(Set.of());
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(u));
        UserDetails ud = service.loadUserByUsername("testuser");
        assertTrue(ud.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }
}
