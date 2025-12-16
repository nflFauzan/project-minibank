package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.SignupForm;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(SignupForm form) {
        userRepository.findByUsername(form.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists");
        });

        User u = new User();
        u.setUsername(form.getUsername().trim());
        u.setPassword(passwordEncoder.encode(form.getPassword()));
        u.setEmail(form.getEmail().trim());
        u.setFullName(form.getFullName().trim());
        u.setNim(form.getNim());
        u.setProdi(form.getProdi());
        u.setDosenPembimbing(form.getDosenPembimbing());

        u.setApproved(false);
        u.setEnabled(true);
        u.getRoles().clear(); // roles dikasih saat dosen approve

        userRepository.save(u);
    }
}
