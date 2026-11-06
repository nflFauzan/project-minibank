package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.rbac.UserCreateForm;
import id.ac.tazkia.minibank.dto.rbac.UserEditForm;
import id.ac.tazkia.minibank.entity.Branch;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.BranchRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RbacUserService Integration Tests")
class RbacUserServiceTest extends BaseIntegrationTest {

    @Autowired private RbacUserService rbacUserService;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BranchRepository branchRepository;

    private Branch testBranch;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testBranch = branchRepository.findByCode("HO001")
                .orElseGet(() -> branchRepository.save(new Branch("HO001", "Kantor Pusat")));

        testRole = roleRepository.findByName("ROLE_TELLER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_TELLER")));
    }

    @Test
    @DisplayName("createUser - sukses membuat user baru")
    void createUser_success() {
        UserCreateForm form = new UserCreateForm();
        form.setUsername("newuser_test");
        form.setFullName("New User Test");
        form.setEmail("newuser_test@tazkia.ac.id");
        form.setBranchId(testBranch.getId());
        form.setPassword("secret123");

        User created = rbacUserService.createUser(form);
        assertNotNull(created.getId());
        assertEquals("newuser_test", created.getUsername());
        assertTrue(created.isEnabled());
        assertFalse(created.isLocked());
        assertEquals("HO001", created.getBranch().getCode());
    }

    @Test
    @DisplayName("createUser - sukses membuat user dengan data akademik dan penugasan peran")
    void createUser_withAcademicFieldsAndRoles_success() {
        UserCreateForm form = new UserCreateForm();
        form.setUsername("mahasiswa_test");
        form.setFullName("Ahmad Mahasiswa");
        form.setEmail("ahmad_mhs@tazkia.ac.id");
        form.setNim("211011001");
        form.setProdi("Akuntansi Syariah");
        form.setDosenPembimbing("Dr. M. Syafii Antonio");
        form.setBranchId(testBranch.getId());
        form.setPassword("mhs12345");
        form.setRoles(List.of("ROLE_CS", "ROLE_TELLER"));

        User created = rbacUserService.createUser(form);
        assertNotNull(created.getId());
        assertEquals("211011001", created.getNim());
        assertEquals("Akuntansi Syariah", created.getProdi());
        assertEquals("Dr. M. Syafii Antonio", created.getDosenPembimbing());
        assertTrue(created.getRoles().stream().anyMatch(r -> "ROLE_CS".equals(r.getName())));
        assertTrue(created.getRoles().stream().anyMatch(r -> "ROLE_TELLER".equals(r.getName())));
    }

    @Test
    @DisplayName("createUser - gagal jika username duplikat")
    void createUser_duplicateUsername() {
        UserCreateForm form1 = new UserCreateForm();
        form1.setUsername("duplicate_test");
        form1.setFullName("User Test");
        form1.setEmail("u1@tazkia.ac.id");
        form1.setBranchId(testBranch.getId());
        rbacUserService.createUser(form1);

        UserCreateForm form2 = new UserCreateForm();
        form2.setUsername("duplicate_test");
        form2.setFullName("User Test 2");
        form2.setEmail("u2@tazkia.ac.id");
        form2.setBranchId(testBranch.getId());

        assertThrows(IllegalArgumentException.class, () -> rbacUserService.createUser(form2));
    }

    @Test
    @DisplayName("assignRole & removeRole - kelola role user")
    void assignAndRemoveRole() {
        UserCreateForm form = new UserCreateForm();
        form.setUsername("roleuser_test");
        form.setFullName("Role User Test");
        form.setEmail("roleuser@tazkia.ac.id");
        form.setBranchId(testBranch.getId());
        User u = rbacUserService.createUser(form);

        // Assign role
        rbacUserService.assignRole(u.getId(), testRole.getId());
        User reloaded = rbacUserService.findById(u.getId());
        assertTrue(reloaded.getRoles().stream().anyMatch(r -> r.getId().equals(testRole.getId())));

        // Remove role
        rbacUserService.removeRole(u.getId(), testRole.getId());
        User reloaded2 = rbacUserService.findById(u.getId());
        assertFalse(reloaded2.getRoles().stream().anyMatch(r -> r.getId().equals(testRole.getId())));
    }

    @Test
    @DisplayName("setPassword & toggleActive - ubah password dan status aktif")
    void setPasswordAndToggleActive() {
        UserCreateForm form = new UserCreateForm();
        form.setUsername("passuser_test");
        form.setFullName("Pass User");
        form.setEmail("passuser@tazkia.ac.id");
        form.setBranchId(testBranch.getId());
        User u = rbacUserService.createUser(form);

        // Update password
        assertDoesNotThrow(() -> rbacUserService.setPassword(u.getId(), "newsecret456"));

        // Toggle inactive
        rbacUserService.toggleActive(u.getId(), false);
        assertFalse(rbacUserService.findById(u.getId()).isEnabled());

        // Toggle active
        rbacUserService.toggleActive(u.getId(), true);
        assertTrue(rbacUserService.findById(u.getId()).isEnabled());
    }
}

