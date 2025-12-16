package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // ====== METHOD YANG DIPANGGIL CONTROLLER (BIAR COMPILE NORMAL) ======

    public List<User> listPending() {
        // pending = approved false
        return userRepository.findByApprovedFalse();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Transactional
    public void approve(Long userId) {
        approveUserAndGrantDefaultRoles(userId);
    }

    @Transactional
    public void reject(Long userId) {
        User u = findById(userId);

        // pilihan aman: nonaktifkan akun (jangan delete dulu biar ada jejak)
        u.setApproved(false);
        u.setEnabled(false);

        userRepository.save(u);
    }

    // ====== LOGIC UTAMA APPROVAL (Sesuai requirement kamu: 3 role) ======

    /**
     * Approve user + aktifkan akun + kasih ROLE_CS ROLE_TELLER ROLE_SUPERVISOR
     */
    @Transactional
    public void approveUserAndGrantDefaultRoles(Long userId) {
        User u = findById(userId);

        u.setApproved(true);
        u.setEnabled(true);

        Role roleCs = roleRepository.findByName("ROLE_CS")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_CS not found"));
        Role roleTeller = roleRepository.findByName("ROLE_TELLER")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_TELLER not found"));
        Role roleSupervisor = roleRepository.findByName("ROLE_SUPERVISOR")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_SUPERVISOR not found"));

        // gabung role (bukan replace) biar kalau ada role lain (misal ADMIN) tidak kehapus
        u.getRoles().add(roleCs);
        u.getRoles().add(roleTeller);
        u.getRoles().add(roleSupervisor);

        userRepository.save(u);
    }
}
