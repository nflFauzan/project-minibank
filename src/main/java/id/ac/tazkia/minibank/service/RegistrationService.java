package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.SignupForm;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(SignupForm form) {

        if (userRepository.findByUsername(form.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username sudah dipakai");
        }

        User u = new User();
        u.setFullName(form.getFullName());
        u.setDosenPembimbing(form.getDosenPembimbing());
        u.setEmail(form.getEmail());
        u.setUsername(form.getUsername());
        u.setProdi(form.getProdi());
        u.setNim(form.getNim());

        u.setPassword(passwordEncoder.encode(form.getPassword()));

        // PENDING
        u.setApproved(false);
        u.setEnabled(false);

        // optional: ROLE_USER biar ada baseline
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role ROLE_USER not found"));
        u.getRoles().add(roleUser);

        userRepository.save(u);
    }
}
