package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo; this.roleRepo = roleRepo; this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(String username, String rawPassword, String fullName) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setFullName(fullName);
        // default role = CS (customer service) OR basic role; change per policy
        Role r = roleRepo.findByName("ROLE_CS").orElseThrow(() -> new IllegalStateException("ROLE_CS missing"));
        u.getRoles().add(r);
        return userRepo.save(u);
    }
}
