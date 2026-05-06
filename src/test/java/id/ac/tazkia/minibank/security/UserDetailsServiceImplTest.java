package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserDetailsServiceImpl Integration Tests")
class UserDetailsServiceImplTest extends BaseIntegrationTest {

    @Autowired private UserDetailsServiceImpl service;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role roleCs = roleRepository.findByName("ROLE_CS")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_CS")));

        User u = new User();
        u.setUsername("testuser");
        u.setPassword("$2a$encoded");
        u.setEmail("test@test.com");
        u.setFullName("Test User");
        u.setApproved(true);
        u.setEnabled(true);
        u.setRoles(Set.of(roleCs));
        userRepository.save(u);
    }

    @Test
    @DisplayName("loadUser - berhasil dengan roles ter-mapping dari DB")
    void loadUser_success() {
        UserDetails ud = service.loadUserByUsername("testuser");
        assertEquals("testuser", ud.getUsername());
        assertTrue(ud.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CS")));
    }

    @Test
    @DisplayName("loadUser - throw jika user tidak ditemukan")
    void loadUser_shouldThrow_whenNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("unknown"));
    }

    @Test
    @DisplayName("loadUser - throw jika user belum approved")
    void loadUser_shouldThrow_whenNotApproved() {
        User u2 = new User();
        u2.setUsername("unapproved");
        u2.setPassword("$2a$encoded");
        u2.setEmail("unapp@test.com");
        u2.setFullName("Unapproved User");
        u2.setApproved(false);
        u2.setEnabled(true);
        userRepository.save(u2);

        assertThrows(DisabledException.class,
                () -> service.loadUserByUsername("unapproved"));
    }
}
