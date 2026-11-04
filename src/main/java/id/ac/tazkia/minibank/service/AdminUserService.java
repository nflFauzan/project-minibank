package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.AdminCreateUserDto;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> listPending() {
        return userRepository.findByApprovedFalse();
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Transactional
    public void approve(Long userId) {
        approve(userId, List.of("ROLE_CS"));
    }

    @Transactional
    public void approve(Long userId, List<String> roleNames) {
        User u = findById(userId);

        u.setApproved(true);
        u.setEnabled(true);
        u.getRoles().clear();

        List<String> targetRoles = (roleNames != null && !roleNames.isEmpty())
                ? roleNames
                : List.of("ROLE_CS");

        for (String roleName : targetRoles) {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            u.getRoles().add(role);
        }

        userRepository.save(u);
    }

    @Transactional
    public User createUser(AdminCreateUserDto dto) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setEmail(dto.getEmail());
        u.setFullName(dto.getFullName());
        u.setNim(dto.getNim());
        u.setProdi(dto.getProdi());
        u.setApproved(true);
        u.setEnabled(true);

        List<String> roles = (dto.getRoles() != null && !dto.getRoles().isEmpty())
                ? dto.getRoles()
                : List.of("ROLE_CS");

        for (String roleName : roles) {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            u.getRoles().add(role);
        }

        return userRepository.save(u);
    }

    @Transactional
    public User updateRoles(Long userId, List<String> roleNames) {
        User u = findById(userId);
        u.getRoles().clear();
        if (roleNames != null) {
            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
                u.getRoles().add(role);
            }
        }
        return userRepository.save(u);
    }

    @Transactional
    public void reject(Long userId) {
        User u = findById(userId);
        u.getRoles().clear();
        userRepository.save(u);
        userRepository.deleteById(userId);
    }
}
