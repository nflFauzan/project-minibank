package id.ac.tazkia.minibank.config;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeedConfig {

    @Bean
    CommandLineRunner seed(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            try {
if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) roleRepo.save(new Role(null, "ROLE_ADMIN"));
if (roleRepo.findByName("ROLE_CS").isEmpty()) roleRepo.save(new Role(null, "ROLE_CS"));
if (roleRepo.findByName("ROLE_TELLER").isEmpty()) roleRepo.save(new Role(null, "ROLE_TELLER"));
if (roleRepo.findByName("ROLE_SUPERVISOR").isEmpty()) roleRepo.save(new Role(null, "ROLE_SUPERVISOR"));

                // create an admin account if missing
                if (userRepo.findByUsername("admin").isEmpty()) {
                    Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseThrow();
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword(encoder.encode("admin123")); // change this before demo
                    admin.setFullName("Super Admin");
                    admin.setEmail("admin@tazkia.ac.id");
                    admin.setApproved(true);
                    admin.setEnabled(true);
                    admin.getRoles().add(adminRole);
                    userRepo.save(admin);
                }
            } catch (Exception ex) {
                System.err.println("Seeding error (non fatal): " + ex.getMessage());
            }
        };
    }
}
