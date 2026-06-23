package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
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

    // ══════════════════════════════════════════════════════════════════════
    // Grup 1 – Normalisasi username (null & whitespace)
    // ══════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Normalisasi username sebelum query ke repository")
    class UsernameNormalization {

        @Test
        @DisplayName("username null → di-trim jadi '' → tidak ditemukan → UsernameNotFoundException")
        void nullUsername_treatedAsEmpty_throwsUsernameNotFoundException() {
            // username null dinormalisasi menjadi "", tidak ada user dengan username ""
            assertThrows(UsernameNotFoundException.class,
                    () -> service.loadUserByUsername(null));
        }

        @Test
        @DisplayName("username dengan whitespace '  testuser  ' → di-trim → user ditemukan")
        void usernameWithPaddingWhitespace_trimmed_userFound() {
            // testuser sudah diseed di setUp()
            UserDetails ud = service.loadUserByUsername("  testuser  ");
            assertEquals("testuser", ud.getUsername());
        }

        @ParameterizedTest(name = "username [{0}] → tidak ditemukan")
        @ValueSource(strings = {"  ", "\t", "TESTUSER"})
        @DisplayName("username tidak cocok setelah trim → UsernameNotFoundException")
        void usernameVariants_notFound_throwsUsernameNotFoundException(String username) {
            // "  " → trim → "", tidak ada user dengan username ""
            // "TESTUSER" → case-sensitive mismatch dengan "testuser"
            assertThrows(UsernameNotFoundException.class,
                    () -> service.loadUserByUsername(username));
        }

        @Test
        @DisplayName("username 'testuser ' (trailing space) → .trim() → 'testuser' → user DITEMUKAN")
        void usernameWithTrailingSpace_trimmed_userFound() {
            // "testuser ".trim() == "testuser" yang ada di DB → tidak throw, return UserDetails
            UserDetails ud = service.loadUserByUsername("testuser ");
            assertEquals("testuser", ud.getUsername());
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Grup 2 – Fallback authority ROLE_USER ketika roles kosong atau null
    // ══════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Fallback authority ROLE_USER ketika roles kosong")
    class RolesFallback {

        @Test
        @DisplayName("roles empty set → authority fallback ke ROLE_USER")
        void emptyRoles_fallsBackToRoleUser() {
            User u = new User();
            u.setUsername("noroles");
            u.setPassword("$2a$encoded");
            u.setEmail("noroles@test.com");
            u.setFullName("No Roles User");
            u.setApproved(true);
            u.setEnabled(true);
            u.setRoles(new HashSet<>()); // explicit kosong
            userRepository.save(u);

            UserDetails ud = service.loadUserByUsername("noroles");
            assertEquals(1, ud.getAuthorities().size());
            assertTrue(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        }

        @Test
        @DisplayName("roles null → authority fallback ke ROLE_USER")
        void nullRoles_fallsBackToRoleUser() {
            User u = new User();
            u.setUsername("nullroles");
            u.setPassword("$2a$encoded");
            u.setEmail("nullroles@test.com");
            u.setFullName("Null Roles User");
            u.setApproved(true);
            u.setEnabled(true);
            u.setRoles(null); // null roles
            userRepository.save(u);

            UserDetails ud = service.loadUserByUsername("nullroles");
            assertTrue(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Grup 3 – Normalisasi nama role (prefix ROLE_, trim)
    // ══════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Normalisasi nama role – prefix ROLE_ dan trim")
    class RoleNameNormalization {

        @Test
        @DisplayName("role sudah ada prefix ROLE_ → tidak di-prefix ulang")
        void roleWithRolePrefix_notDoubledPrefixed() {
            // testuser di setUp() pakai ROLE_CS — verifikasi tidak jadi ROLE_ROLE_CS
            UserDetails ud = service.loadUserByUsername("testuser");
            assertFalse(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().startsWith("ROLE_ROLE_")),
                    "Authority tidak boleh memiliki prefix ganda ROLE_ROLE_");
            assertTrue(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CS")));
        }

        @Test
        @DisplayName("role tanpa prefix ROLE_ → di-auto-prefix menjadi ROLE_xxx")
        void roleWithoutRolePrefix_getsAutoPrefixed() {
            Role rawRole = roleRepository.save(new Role(null, "ADMIN"));

            User u = new User();
            u.setUsername("adminraw");
            u.setPassword("$2a$encoded");
            u.setEmail("adminraw@test.com");
            u.setFullName("Admin Raw");
            u.setApproved(true);
            u.setEnabled(true);
            u.setRoles(Set.of(rawRole));
            userRepository.save(u);

            UserDetails ud = service.loadUserByUsername("adminraw");
            // "ADMIN" harus menjadi "ROLE_ADMIN"
            assertTrue(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")),
                    "Role 'ADMIN' seharusnya di-prefix menjadi 'ROLE_ADMIN'");
            // tidak ada authority 'ADMIN' tanpa prefix
            assertFalse(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        }

        @Test
        @DisplayName("role name dengan whitespace '  TELLER  ' → di-trim → ROLE_TELLER")
        void roleNameWithWhitespace_trimmedBeforePrefixCheck() {
            Role trimRole = roleRepository.save(new Role(null, "  TELLER  "));

            User u = new User();
            u.setUsername("tellertrim");
            u.setPassword("$2a$encoded");
            u.setEmail("tellertrim@test.com");
            u.setFullName("Teller Trim");
            u.setApproved(true);
            u.setEnabled(true);
            u.setRoles(Set.of(trimRole));
            userRepository.save(u);

            UserDetails ud = service.loadUserByUsername("tellertrim");
            // '  TELLER  '.trim() = 'TELLER', tidak startsWith 'ROLE_' → 'ROLE_TELLER'
            assertTrue(ud.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_TELLER")));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Grup 4 – Multiple roles
    // ══════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("User dengan multiple roles")
    class MultipleRoles {

        @Test
        @DisplayName("user dengan 3 role → semua authority ada di UserDetails")
        void multipleRoles_allMappedToAuthorities() {
            Role roleCs = roleRepository.findByName("ROLE_CS")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_CS")));
            Role roleTeller = roleRepository.findByName("ROLE_TELLER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_TELLER")));
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

            User u = new User();
            u.setUsername("multirole");
            u.setPassword("$2a$encoded");
            u.setEmail("multi@test.com");
            u.setFullName("Multi Role User");
            u.setApproved(true);
            u.setEnabled(true);
            u.setRoles(Set.of(roleCs, roleTeller, roleAdmin));
            userRepository.save(u);

            UserDetails ud = service.loadUserByUsername("multirole");
            assertEquals(3, ud.getAuthorities().size());
            assertTrue(ud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS")));
            assertTrue(ud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER")));
            assertTrue(ud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }

        @Test
        @DisplayName("user dengan 2 role termasuk SUPERVISOR → semua authority ada")
        void twoRoles_includingSupervisor_allMappedCorrectly() {
            Role roleSupervisor = roleRepository.findByName("ROLE_SUPERVISOR")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_SUPERVISOR")));
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

            User u = new User();
            u.setUsername("superadmin");
            u.setPassword("$2a$encoded");
            u.setEmail("superadmin@test.com");
            u.setFullName("Super Admin");
            u.setApproved(true);
            u.setEnabled(true);
            u.setRoles(Set.of(roleSupervisor, roleAdmin));
            userRepository.save(u);

            UserDetails ud = service.loadUserByUsername("superadmin");
            assertEquals(2, ud.getAuthorities().size());
            assertTrue(ud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR")));
            assertTrue(ud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Grup 5 – enabled=false (berbeda dari approved=false)
    // ══════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Field enabled=false – tidak throw exception, tapi UserDetails.isEnabled()==false")
    class EnabledFlag {

        @Test
        @DisplayName("approved=true & enabled=false → UserDetails berhasil dibuat, isEnabled()==false")
        void approvedButDisabled_returnsUserDetailsWithEnabledFalse() {
            Role roleCs = roleRepository.findByName("ROLE_CS")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_CS")));

            User u = new User();
            u.setUsername("disabled");
            u.setPassword("$2a$encoded");
            u.setEmail("dis@test.com");
            u.setFullName("Disabled User");
            u.setApproved(true);   // approved
            u.setEnabled(false);   // tapi non-aktif
            u.setRoles(Set.of(roleCs));
            userRepository.save(u);

            // Tidak throw — loadUserByUsername hanya cek isApproved(), bukan isEnabled()
            UserDetails ud = service.loadUserByUsername("disabled");
            assertNotNull(ud);
            assertFalse(ud.isEnabled(),
                    "UserDetails.isEnabled() harus false karena user.isEnabled()==false");
        }

        @Test
        @DisplayName("approved=true & enabled=true → UserDetails.isEnabled()==true")
        void approvedAndEnabled_userDetailsIsEnabled() {
            // testuser dari setUp() adalah approved=true, enabled=true
            UserDetails ud = service.loadUserByUsername("testuser");
            assertTrue(ud.isEnabled());
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Grup 6 – Verifikasi exception message
    // ══════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Verifikasi pesan exception")
    class ExceptionMessages {

        @Test
        @DisplayName("UsernameNotFoundException memiliki pesan 'User not found'")
        void usernameNotFound_exceptionMessageIsCorrect() {
            UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                    () -> service.loadUserByUsername("nonexistent"));
            assertEquals("User not found", ex.getMessage());
        }

        @Test
        @DisplayName("DisabledException memiliki pesan 'User not approved'")
        void notApproved_exceptionMessageIsCorrect() {
            User u = new User();
            u.setUsername("unapproved2");
            u.setPassword("$2a$encoded");
            u.setEmail("u2@test.com");
            u.setFullName("Unapproved2");
            u.setApproved(false);
            u.setEnabled(true);
            userRepository.save(u);

            DisabledException ex = assertThrows(DisabledException.class,
                    () -> service.loadUserByUsername("unapproved2"));
            assertEquals("User not approved", ex.getMessage());
        }
    }
}
