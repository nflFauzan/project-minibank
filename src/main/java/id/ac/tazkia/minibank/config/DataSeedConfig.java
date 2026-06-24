package id.ac.tazkia.minibank.config;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataSeedConfig {
    
    private final Environment env;

    @Bean
    CommandLineRunner seed(RoleRepository roleRepo, 
                           UserRepository userRepo, 
                           NasabahRepository nasabahRepo,
                           ProdukTabunganRepository produkRepo,
                           PasswordEncoder encoder) {
        return args -> {
            try {
                // roles wajib ada
                if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) roleRepo.save(new Role(null, "ROLE_ADMIN"));
                if (roleRepo.findByName("ROLE_USER").isEmpty()) roleRepo.save(new Role(null, "ROLE_USER"));
                if (roleRepo.findByName("ROLE_CS").isEmpty()) roleRepo.save(new Role(null, "ROLE_CS"));
                if (roleRepo.findByName("ROLE_TELLER").isEmpty()) roleRepo.save(new Role(null, "ROLE_TELLER"));
                if (roleRepo.findByName("ROLE_SUPERVISOR").isEmpty()) roleRepo.save(new Role(null, "ROLE_SUPERVISOR"));

                // admin default
                if (userRepo.findByUsername("admin").isEmpty()) {
                    Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseThrow();
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword(encoder.encode("admin1234"));
                    admin.setFullName("Super Admin");
                    admin.setEmail("admin@tazkia.ac.id");
                    admin.setApproved(true);
                    admin.setEnabled(true);
                    admin.getRoles().add(adminRole);
                    userRepo.save(admin);
                }

                // zann (CS) default
                if (userRepo.findByUsername("zann").isEmpty()) {
                    Role csRole = roleRepo.findByName("ROLE_CS").orElseThrow();
                    User zann = new User();
                    zann.setUsername("zann");
                    zann.setPassword(encoder.encode("zann"));
                    zann.setFullName("Zann Customer Service");
                    zann.setEmail("zann@tazkia.ac.id");
                    zann.setApproved(true);
                    zann.setEnabled(true);
                    zann.getRoles().add(csRole);
                    userRepo.save(zann);
                }

                // spv (Supervisor) default
                if (userRepo.findByUsername("spv").isEmpty()) {
                    Role spvRole = roleRepo.findByName("ROLE_SUPERVISOR").orElseThrow();
                    User spv = new User();
                    spv.setUsername("spv");
                    spv.setPassword(encoder.encode("spv1234"));
                    spv.setFullName("Supervisor Default");
                    spv.setEmail("spv@tazkia.ac.id");
                    spv.setApproved(true);
                    spv.setEnabled(true);
                    spv.getRoles().add(spvRole);
                    userRepo.save(spv);
                }

                // teller (Teller) default
                if (userRepo.findByUsername("teller").isEmpty()) {
                    Role tellerRole = roleRepo.findByName("ROLE_TELLER").orElseThrow();
                    User teller = new User();
                    teller.setUsername("teller");
                    teller.setPassword(encoder.encode("teller1234"));
                    teller.setFullName("Teller Default");
                    teller.setEmail("teller@tazkia.ac.id");
                    teller.setApproved(true);
                    teller.setEnabled(true);
                    teller.getRoles().add(tellerRole);
                    userRepo.save(teller);
                }

                // Nasabah & Produk cuma kalau BUKAN profile test
                // Karena JUnit test pakai data yang sama
                if (!env.acceptsProfiles(Profiles.of("test"))) {
                    // Nasabah ACTIVE default untuk testing
                    nasabahRepo.findByCif("C0000001").ifPresentOrElse(
                        n -> {
                            if (n.getStatus() != NasabahStatus.ACTIVE) {
                                n.setStatus(NasabahStatus.ACTIVE);
                                nasabahRepo.save(n);
                            }
                        },
                        () -> {
                            Nasabah n = new Nasabah();
                            n.setCif("C0000001");
                            n.setNik("1234567890123456");
                            n.setNamaLengkap("Budi Santoso");
                            n.setStatus(NasabahStatus.ACTIVE);
                            n.setTempatLahir("Jakarta");
                            n.setNamaIbuKandung("Siti Aminah");
                            nasabahRepo.save(n);
                        }
                    );
    
                    // Produk default
                    if (produkRepo.findActiveProducts().isEmpty()) {
                        ProdukTabungan p = new ProdukTabungan();
                        p.setKodeProduk("TAB_UTAMA");
                        p.setNamaProduk("Tabungan Utama");
                        p.setJenisAkad("WADIAH");
                        p.setSetoranAwalMinimum(new BigDecimal("100000"));
                        p.setAktif(true);
                        produkRepo.save(p);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Seeding error (non fatal): " + ex.getMessage());
                ex.printStackTrace();
            }
        };
    }
}
