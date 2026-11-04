package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.AdminCreateUserDto;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AdminUserService Integration Tests")
class AdminUserServiceTest extends BaseIntegrationTest {

    @Autowired private AdminUserService adminUserService;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    private User pendingUser;

    @BeforeEach
    void setUp() {
        User u = new User();
        u.setUsername("pendinguser");
        u.setPassword("$2a$10$dummyhash");
        u.setEmail("pending@tazkia.ac.id");
        u.setFullName("Pending User");
        u.setApproved(false);
        u.setEnabled(false);
        pendingUser = userRepository.save(u);
    }

    @Test
    @DisplayName("listPending - mengembalikan user yang belum approved")
    void listPending() {
        var result = adminUserService.listPending();
        assertTrue(result.stream().anyMatch(u -> u.getUsername().equals("pendinguser")));
    }

    @Test
    @DisplayName("findById - berhasil menemukan user")
    void findById_success() {
        User found = adminUserService.findById(pendingUser.getId());
        assertEquals("pendinguser", found.getUsername());
    }

    @Test
    @DisplayName("findById - throw jika tidak ditemukan")
    void findById_notFound() {
        assertThrows(IllegalArgumentException.class, () -> adminUserService.findById(999999L));
    }

    @Test
    @DisplayName("approve - berhasil approve & set roles default")
    void approve_success() {
        adminUserService.approve(pendingUser.getId());

        User approved = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertTrue(approved.isApproved());
        assertTrue(approved.isEnabled());
        assertFalse(approved.getRoles().isEmpty());
    }

    @Test
    @DisplayName("approve - dengan pilihan role spesifik (hanya ROLE_TELLER)")
    void approve_withSpecificRoles() {
        adminUserService.approve(pendingUser.getId(), List.of("ROLE_TELLER"));

        User approved = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertTrue(approved.isApproved());
        assertTrue(approved.isEnabled());
        assertEquals(1, approved.getRoles().size());
        assertTrue(approved.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_TELLER")));
    }

    @Test
    @DisplayName("createUser - admin membuat user aktif langsung dengan role")
    void createUser_success() {
        AdminCreateUserDto dto = new AdminCreateUserDto();
        dto.setUsername("directuser");
        dto.setPassword("directpass");
        dto.setEmail("direct@tazkia.ac.id");
        dto.setFullName("Direct User");
        dto.setRoles(List.of("ROLE_BRANCH_MANAGER"));

        User created = adminUserService.createUser(dto);

        assertNotNull(created.getId());
        assertTrue(created.isApproved());
        assertTrue(created.isEnabled());
        assertTrue(created.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_BRANCH_MANAGER")));
    }

    @Test
    @DisplayName("updateRoles - admin mengubah role user yang sudah ada")
    void updateRoles_success() {
        adminUserService.approve(pendingUser.getId(), List.of("ROLE_CS"));
        adminUserService.updateRoles(pendingUser.getId(), List.of("ROLE_BRANCH_MANAGER", "ROLE_TELLER"));

        User updated = userRepository.findById(pendingUser.getId()).orElseThrow();
        assertEquals(2, updated.getRoles().size());
        assertTrue(updated.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_BRANCH_MANAGER")));
        assertTrue(updated.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_TELLER")));
    }

    @Test
    @DisplayName("reject - berhasil hapus user dari DB")
    void reject_success() {
        Long userId = pendingUser.getId();
        adminUserService.reject(userId);
        assertFalse(userRepository.findById(userId).isPresent());
    }
}
