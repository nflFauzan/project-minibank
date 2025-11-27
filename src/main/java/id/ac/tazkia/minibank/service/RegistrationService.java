package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User registerStudent(User incoming) {
        if (userRepo.findByUsername(incoming.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        incoming.setPassword(passwordEncoder.encode(incoming.getPassword()));
        incoming.setApproved(false);
        incoming.setEnabled(false);
        return userRepo.save(incoming);
    }
}
