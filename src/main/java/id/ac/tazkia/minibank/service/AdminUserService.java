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

    public List<User> listPending() {
        return userRepository.findByApprovedFalse();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Transactional
    public void approve(Long userId) {
        User u = findById(userId);

        u.setApproved(true);
        u.setEnabled(true);

        Role roleCs = roleRepository.findByName("ROLE_CS")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_CS not found"));
        Role roleTeller = roleRepository.findByName("ROLE_TELLER")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_TELLER not found"));
        Role roleSupervisor = roleRepository.findByName("ROLE_SUPERVISOR")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_SUPERVISOR not found"));

        u.getRoles().add(roleCs);
        u.getRoles().add(roleTeller);
        u.getRoles().add(roleSupervisor);

        userRepository.save(u);
    }

    @Transactional
public void reject(Long userId) {
    // paling aman: hapus relasi many-to-many dulu biar join table bersih
    User u = findById(userId);
    u.getRoles().clear();
    userRepository.save(u);

    // baru delete usernya
    userRepository.deleteById(userId);
}

}
