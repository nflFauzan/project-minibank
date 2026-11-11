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
                if (roleRepo.findByName("ROLE_BRANCH_MANAGER").isEmpty()) roleRepo.save(new Role(null, "ROLE_BRANCH_MANAGER"));

                // admin default
                if (userRepo.findByUsername("admin").isEmpty()) {
                    Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseThrow();
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword(encoder.encode("minibank123"));
                    admin.setFullName("Super Admin");
                    admin.setEmail("admin@tazkia.ac.id");
                    admin.setApproved(true);
                    admin.setEnabled(true);
                    admin.getRoles().add(adminRole);
                    userRepo.save(admin);
                }

                // spv (Supervisor) default
                if (userRepo.findByUsername("spv").isEmpty()) {
                    Role spvRole = roleRepo.findByName("ROLE_SUPERVISOR").orElseThrow();
                    User spv = new User();
                    spv.setUsername("spv");
                    spv.setPassword(encoder.encode("minibank123"));
                    spv.setFullName("Supervisor");
                    spv.setEmail("spv@tazkia.ac.id");
                    spv.setApproved(true);
                    spv.setEnabled(true);
                    spv.getRoles().add(spvRole);
                    roleRepo.findByName("ROLE_BRANCH_MANAGER").ifPresent(spv.getRoles()::add);
                    userRepo.save(spv);
                }

                // zann & teller hanya dibuat jika profile dev/local (untuk Playwright CI lokal)
                if (env.acceptsProfiles(Profiles.of("dev", "local"))) {
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
                }

                // Nasabah & Produk & Live Minibank Accounts cuma kalau BUKAN profile test
                // Karena JUnit test pakai data yang sama
                if (!env.acceptsProfiles(Profiles.of("test"))) {
                    // Minibank Tazkia live credential seeding & role cleanup
                    Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElse(null);
                    Role bmRole = roleRepo.findByName("ROLE_BRANCH_MANAGER").orElse(null);
                    Role spvRole = roleRepo.findByName("ROLE_SUPERVISOR").orElse(null);
                    Role csRole = roleRepo.findByName("ROLE_CS").orElse(null);
                    Role tellerRole = roleRepo.findByName("ROLE_TELLER").orElse(null);

                    // Ensure admin password is minibank123 and has BRANCH_MANAGER
                    userRepo.findByUsername("admin").ifPresent(admin -> {
                        admin.setPassword(encoder.encode("minibank123"));
                        if (bmRole != null && !admin.getRoles().contains(bmRole)) {
                            admin.getRoles().add(bmRole);
                        }
                        userRepo.save(admin);
                    });

                    // Ensure spv password is minibank123 and has supervisor & branch manager roles
                    userRepo.findByUsername("spv").ifPresent(spv -> {
                        spv.setPassword(encoder.encode("minibank123"));
                        if (spvRole != null && !spv.getRoles().contains(spvRole)) {
                            spv.getRoles().add(spvRole);
                        }
                        if (bmRole != null && !spv.getRoles().contains(bmRole)) {
                            spv.getRoles().add(bmRole);
                        }
                        userRepo.save(spv);
                    });

                    // Ensure cs1 exists with minibank123
                    if (userRepo.findByUsername("cs1").isEmpty() && csRole != null) {
                        User cs1 = new User();
                        cs1.setUsername("cs1");
                        cs1.setPassword(encoder.encode("minibank123"));
                        cs1.setFullName("Customer Service 1");
                        cs1.setEmail("cs1@tazkia.ac.id");
                        cs1.setApproved(true);
                        cs1.setEnabled(true);
                        cs1.getRoles().add(csRole);
                        userRepo.save(cs1);
                    } else {
                        userRepo.findByUsername("cs1").ifPresent(cs1 -> {
                            cs1.setPassword(encoder.encode("minibank123"));
                            userRepo.save(cs1);
                        });
                    }

                    // Ensure teller1 exists with minibank123
                    if (userRepo.findByUsername("teller1").isEmpty() && tellerRole != null) {
                        User teller1 = new User();
                        teller1.setUsername("teller1");
                        teller1.setPassword(encoder.encode("minibank123"));
                        teller1.setFullName("Teller 1");
                        teller1.setEmail("teller1@tazkia.ac.id");
                        teller1.setApproved(true);
                        teller1.setEnabled(true);
                        teller1.getRoles().add(tellerRole);
                        userRepo.save(teller1);
                    } else {
                        userRepo.findByUsername("teller1").ifPresent(t1 -> {
                            t1.setPassword(encoder.encode("minibank123"));
                            userRepo.save(t1);
                        });
                    }

                    // Di profile prod, bersihkan akun testing non-utama jika ada
                    if (env.acceptsProfiles(Profiles.of("prod"))) {
                        userRepo.findByUsername("zann").ifPresent(userRepo::delete);
                        userRepo.findByUsername("teller").ifPresent(userRepo::delete);
                    }

                    // Nasabah ACTIVE default untuk dev/testing saja (bukan prod dan bukan test)
                    if (!env.acceptsProfiles(Profiles.of("prod", "test"))) {
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
                    }

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
