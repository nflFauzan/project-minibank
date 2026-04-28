package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdminUserService Unit Tests")
class AdminUserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;

    @InjectMocks
    private AdminUserService adminUserService;

    @Test
    @DisplayName("listPending - mengembalikan user yang belum approved")
    void listPending() {
        User u = new User();
        u.setUsername("newuser");
        when(userRepository.findByApprovedFalse()).thenReturn(List.of(u));

        List<User> result = adminUserService.listPending();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findById - berhasil jika ditemukan")
    void findById_success() {
        User u = new User();
        u.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        User result = adminUserService.findById(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("findById - throw jika tidak ditemukan")
    void findById_shouldThrow_whenNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> adminUserService.findById(999L));
    }

    @Test
    @DisplayName("approve - set approved, enabled, dan assign 3 roles")
    void approve_success() {
        User u = new User();
        u.setId(1L);
        u.setRoles(new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(roleRepository.findByName("ROLE_CS")).thenReturn(Optional.of(new Role(1L, "ROLE_CS")));
        when(roleRepository.findByName("ROLE_TELLER")).thenReturn(Optional.of(new Role(2L, "ROLE_TELLER")));
        when(roleRepository.findByName("ROLE_SUPERVISOR")).thenReturn(Optional.of(new Role(3L, "ROLE_SUPERVISOR")));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        adminUserService.approve(1L);

        assertTrue(u.isApproved());
        assertTrue(u.isEnabled());
        assertEquals(3, u.getRoles().size());
        verify(userRepository).save(u);
    }

    @Test
    @DisplayName("reject - hapus roles dan delete user")
    void reject_success() {
        User u = new User();
        u.setId(1L);
        u.setRoles(new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        adminUserService.reject(1L);

        assertTrue(u.getRoles().isEmpty());
        verify(userRepository).deleteById(1L);
    }
}
