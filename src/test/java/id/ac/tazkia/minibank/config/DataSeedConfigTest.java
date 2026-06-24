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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DataSeedConfig – Startup Data Seeding Tests")
public class DataSeedConfigTest {

    @Mock
    private Environment env;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private NasabahRepository nasabahRepo;

    @Mock
    private ProdukTabunganRepository produkRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private DataSeedConfig dataSeedConfig;

    private CommandLineRunner runner;

    @BeforeEach
    void setUp() {
        runner = dataSeedConfig.seed(roleRepo, userRepo, nasabahRepo, produkRepo, encoder);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 1. Roles Seeding
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Role Initialization Seeds")
    class RoleInitialization {

        @Test
        @DisplayName("When roles do not exist – saves all 5 roles")
        void rolesNotExist_shouldSaveAll() throws Exception {
            when(roleRepo.findByName(anyString())).thenReturn(Optional.empty());
            when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            when(env.acceptsProfiles(any(Profiles.class))).thenReturn(true);

            runner.run(new String[]{});

            verify(roleRepo, times(5)).save(any(Role.class));
            verify(roleRepo).save(argThat(role -> role.getName().equals("ROLE_ADMIN")));
            verify(roleRepo).save(argThat(role -> role.getName().equals("ROLE_USER")));
            verify(roleRepo).save(argThat(role -> role.getName().equals("ROLE_CS")));
            verify(roleRepo).save(argThat(role -> role.getName().equals("ROLE_TELLER")));
            verify(roleRepo).save(argThat(role -> role.getName().equals("ROLE_SUPERVISOR")));
        }

        @Test
        @DisplayName("When roles already exist – saves nothing")
        void rolesExist_shouldNotSave() throws Exception {
            when(roleRepo.findByName(anyString())).thenReturn(Optional.of(new Role(1L, "ROLE")));
            when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            when(env.acceptsProfiles(any(Profiles.class))).thenReturn(true);

            runner.run(new String[]{});

            verify(roleRepo, never()).save(any(Role.class));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 2. Users Seeding (admin and zann)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Default User Seeds")
    class UserInitialization {

        @Test
        @DisplayName("When admin and zann do not exist – creates and saves both default users")
        void usersNotExist_shouldCreateAndSaveBoth() throws Exception {
            when(roleRepo.findByName(anyString())).thenReturn(Optional.empty());
            
            // Set up mock roles to return when findByName is invoked for saving users
            Role adminRole = new Role(1L, "ROLE_ADMIN");
            Role csRole = new Role(3L, "ROLE_CS");
            when(roleRepo.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
            when(roleRepo.findByName("ROLE_CS")).thenReturn(Optional.of(csRole));

            when(userRepo.findByUsername("admin")).thenReturn(Optional.empty());
            when(userRepo.findByUsername("zann")).thenReturn(Optional.empty());
            when(encoder.encode(anyString())).thenReturn("hashed_pass");
            when(env.acceptsProfiles(any(Profiles.class))).thenReturn(true);

            runner.run(new String[]{});

            // 3 roles saved (since ROLE_ADMIN and ROLE_CS already exist and are not saved)
            verify(roleRepo, times(3)).save(any(Role.class));
            // 2 default users saved
            verify(userRepo, times(2)).save(any(User.class));
            verify(userRepo).save(argThat(u -> u.getUsername().equals("admin") 
                    && u.getPassword().equals("hashed_pass") 
                    && u.getFullName().equals("Super Admin")
                    && u.getEmail().equals("admin@tazkia.ac.id")
                    && u.isApproved() && u.isEnabled()
                    && u.getRoles().contains(adminRole)));
            verify(userRepo).save(argThat(u -> u.getUsername().equals("zann") 
                    && u.getPassword().equals("hashed_pass") 
                    && u.getFullName().equals("Zann Customer Service")
                    && u.getEmail().equals("zann@tazkia.ac.id")
                    && u.isApproved() && u.isEnabled()
                    && u.getRoles().contains(csRole)));
        }

        @Test
        @DisplayName("When admin and zann already exist – ignores creation")
        void usersExist_shouldNotCreate() throws Exception {
            when(roleRepo.findByName(anyString())).thenReturn(Optional.of(new Role(1L, "ROLE")));
            when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            when(env.acceptsProfiles(any(Profiles.class))).thenReturn(true);

            runner.run(new String[]{});

            verify(userRepo, never()).save(any(User.class));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 3. Profiles / Environment Handling
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Profile Environment Handling")
    class ProfileHandling {

        @Test
        @DisplayName("Under 'test' profile – skips Nasabah and ProdukTabungan seeding completely")
        void testProfile_shouldSkipAdditionalSeeds() throws Exception {
            when(roleRepo.findByName(anyString())).thenReturn(Optional.of(new Role(1L, "ROLE")));
            when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            when(env.acceptsProfiles(any(Profiles.class))).thenReturn(true); // profile is "test"

            runner.run(new String[]{});

            verify(nasabahRepo, never()).findByCif(anyString());
            verify(produkRepo, never()).findActiveProducts();
        }

        @Test
        @DisplayName("Under non-'test' profile – runs Nasabah and ProdukTabungan seeding checks")
        void nonTestProfile_shouldExecuteAdditionalSeeds() throws Exception {
            when(roleRepo.findByName(anyString())).thenReturn(Optional.of(new Role(1L, "ROLE")));
            when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            when(env.acceptsProfiles(any(Profiles.class))).thenReturn(false); // profile is not "test"

            when(nasabahRepo.findByCif("C0000001")).thenReturn(Optional.of(new Nasabah()));
            when(produkRepo.findActiveProducts()).thenReturn(List.of(new ProdukTabungan()));

            runner.run(new String[]{});

            verify(nasabahRepo).findByCif("C0000001");
            verify(produkRepo).findActiveProducts();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 4. Nasabah Seeding (only on non-test profiles)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Nasabah Data Seeding")
    class NasabahSeeding {

        @BeforeEach
        void setupMocks() {
            lenient().when(roleRepo.findByName(anyString())).thenReturn(Optional.of(new Role(1L, "ROLE")));
            lenient().when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            lenient().when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            lenient().when(env.acceptsProfiles(any(Profiles.class))).thenReturn(false); // non-test
            lenient().when(produkRepo.findActiveProducts()).thenReturn(List.of(new ProdukTabungan()));
        }

        @Test
        @DisplayName("When Nasabah does not exist – creates and seeds new ACTIVE Nasabah")
        void nasabahNotExist_shouldCreateAndSave() throws Exception {
            when(nasabahRepo.findByCif("C0000001")).thenReturn(Optional.empty());

            runner.run(new String[]{});

            verify(nasabahRepo).save(argThat(n -> n.getCif().equals("C0000001") 
                    && n.getNik().equals("1234567890123456")
                    && n.getNamaLengkap().equals("Budi Santoso")
                    && n.getStatus() == NasabahStatus.ACTIVE
                    && n.getTempatLahir().equals("Jakarta")
                    && n.getNamaIbuKandung().equals("Siti Aminah")));
        }

        @Test
        @DisplayName("When Nasabah exists but is INACTIVE – updates status to ACTIVE and saves")
        void nasabahInactive_shouldUpdateToActiveAndSave() throws Exception {
            Nasabah existing = new Nasabah();
            existing.setCif("C0000001");
            existing.setStatus(NasabahStatus.INACTIVE);

            when(nasabahRepo.findByCif("C0000001")).thenReturn(Optional.of(existing));

            runner.run(new String[]{});

            verify(nasabahRepo).save(existing);
            assertEquals(NasabahStatus.ACTIVE, existing.getStatus());
        }

        @Test
        @DisplayName("When Nasabah exists and is already ACTIVE – does nothing (no save call)")
        void nasabahActive_shouldDoNothing() throws Exception {
            Nasabah existing = new Nasabah();
            existing.setCif("C0000001");
            existing.setStatus(NasabahStatus.ACTIVE);

            when(nasabahRepo.findByCif("C0000001")).thenReturn(Optional.of(existing));

            runner.run(new String[]{});

            verify(nasabahRepo, never()).save(existing);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 5. ProdukTabungan Seeding (only on non-test profiles)
    // ──────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("ProdukTabungan Data Seeding")
    class ProdukTabunganSeeding {

        @BeforeEach
        void setupMocks() {
            lenient().when(roleRepo.findByName(anyString())).thenReturn(Optional.of(new Role(1L, "ROLE")));
            lenient().when(userRepo.findByUsername("admin")).thenReturn(Optional.of(new User()));
            lenient().when(userRepo.findByUsername("zann")).thenReturn(Optional.of(new User()));
            lenient().when(userRepo.findByUsername("spv")).thenReturn(Optional.of(new User()));
            lenient().when(userRepo.findByUsername("teller")).thenReturn(Optional.of(new User()));
            lenient().when(env.acceptsProfiles(any(Profiles.class))).thenReturn(false); // non-test
            lenient().when(nasabahRepo.findByCif("C0000001")).thenReturn(Optional.of(new Nasabah()));
        }

        @Test
        @DisplayName("When no active products exist – seeds a default Wadiah product")
        void productsNotExist_shouldSeedWadiahProduct() throws Exception {
            when(produkRepo.findActiveProducts()).thenReturn(Collections.emptyList());

            runner.run(new String[]{});

            verify(produkRepo).save(argThat(p -> p.getKodeProduk().equals("TAB_UTAMA")
                    && p.getNamaProduk().equals("Tabungan Utama")
                    && p.getJenisAkad().equals("WADIAH")
                    && p.getSetoranAwalMinimum().compareTo(new BigDecimal("100000")) == 0
                    && p.getAktif()));
        }

        @Test
        @DisplayName("When active products already exist – skips product seeding")
        void productsExist_shouldSkipSeeding() throws Exception {
            when(produkRepo.findActiveProducts()).thenReturn(List.of(new ProdukTabungan()));

            runner.run(new String[]{});

            verify(produkRepo, never()).save(any(ProdukTabungan.class));
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 6. Exception Tolerant Execution
    // ──────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("When database triggers exception – execution catches it and continues without crashing")
    void dbExceptionOccurs_shouldCatchAndNotPropagate() {
        // Trigger exception in the very first statement inside runner try block
        when(roleRepo.findByName(anyString())).thenThrow(new RuntimeException("Simulated Database down"));

        assertDoesNotThrow(() -> runner.run(new String[]{}));
    }
}
