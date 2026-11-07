package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.rbac.UserCreateForm;
import id.ac.tazkia.minibank.dto.rbac.UserEditForm;
import id.ac.tazkia.minibank.entity.Branch;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.BranchRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RbacUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> searchUsers(String search, Pageable pageable) {
        return userRepository.searchUsers(search, pageable);
    }

    public Page<User> searchUsersFiltered(String search, String status, String role, Long branchId, Pageable pageable) {
        return userRepository.searchUsersFiltered(search, status, role, branchId, pageable);
    }

    public long countPending() {
        return userRepository.countByApprovedFalse();
    }

    public long countActive() {
        return userRepository.countByApprovedTrueAndEnabledTrue();
    }

    public long countInactive() {
        return userRepository.countByApprovedTrueAndEnabledFalse();
    }

    public List<User> searchUsersList(String search) {
        return userRepository.searchUsersList(search);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan: " + id));
    }

    public List<Branch> listActiveBranches() {
        return branchRepository.findByActiveTrueOrderByCodeAsc();
    }

    public List<Role> listActiveRoles() {
        return roleRepository.findByActiveTrueOrderByRoleCodeAsc();
    }

    @Transactional
    public User createUser(UserCreateForm form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("Username '" + form.getUsername() + "' sudah terdaftar.");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email '" + form.getEmail() + "' sudah terdaftar.");
        }

        Branch branch = null;
        if (form.getBranchId() != null) {
            branch = branchRepository.findById(form.getBranchId()).orElse(null);
        }
        if (branch == null) {
            branch = branchRepository.findByCode("HO001")
                    .orElseGet(() -> branchRepository.findByActiveTrueOrderByCodeAsc().stream()
                            .findFirst()
                            .orElse(null));
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());
        user.setNim(form.getNim());
        user.setProdi(form.getProdi());
        user.setDosenPembimbing(form.getDosenPembimbing());
        user.setBranch(branch);
        user.setApproved(true);
        user.setEnabled(true);
        user.setLocked(false);

        String rawPassword = (form.getPassword() != null && !form.getPassword().isBlank())
                ? form.getPassword()
                : "minibank123";
        user.setPassword(passwordEncoder.encode(rawPassword));

        List<String> roleNames = (form.getRoles() != null && !form.getRoles().isEmpty())
                ? form.getRoles()
                : List.of("ROLE_CS");

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            user.getRoles().add(role);
        }

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserEditForm form) {
        User user = findById(form.getId());

        // Check if username changed and collision
        if (!user.getUsername().equalsIgnoreCase(form.getUsername()) &&
                userRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("Username '" + form.getUsername() + "' sudah digunakan.");
        }

        // Check if email changed and collision
        if (!user.getEmail().equalsIgnoreCase(form.getEmail()) &&
                userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email '" + form.getEmail() + "' sudah digunakan.");
        }

        Branch branch = branchRepository.findById(form.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Cabang tidak valid"));

        user.setUsername(form.getUsername());
        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());
        user.setBranch(branch);
        user.setEnabled(form.isEnabled());
        user.setLocked(form.isLocked());

        return userRepository.save(user);
    }

    @Transactional
    public void setPassword(Long userId, String newPassword) {
        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void assignRole(Long userId, Long roleId) {
        User user = findById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role tidak ditemukan: " + roleId));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeRole(Long userId, Long roleId) {
        User user = findById(userId);
        user.getRoles().removeIf(r -> r.getId().equals(roleId));
        userRepository.save(user);
    }

    @Transactional
    public void toggleActive(Long userId, boolean active) {
        User user = findById(userId);
        user.setEnabled(active);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findById(userId);
        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(userId);
    }
}

