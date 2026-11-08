package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.BaseIntegrationTest;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("RBAC Controllers Integration Tests")
class RbacControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BranchRepository branchRepository;

    private User sampleUser;
    private Branch sampleBranch;

    @BeforeEach
    void setUp() {
        sampleBranch = branchRepository.findByCode("HO001")
                .orElseGet(() -> branchRepository.save(new Branch("HO001", "Kantor Pusat")));

        sampleUser = userRepository.findByUsername("testrbacuser")
                .orElseGet(() -> {
                    User u = new User();
                    u.setUsername("testrbacuser");
                    u.setPassword("$2a$10$dummy");
                    u.setFullName("Test RBAC User");
                    u.setEmail("testrbacuser@tazkia.ac.id");
                    u.setBranch(sampleBranch);
                    u.setEnabled(true);
                    u.setApproved(true);
                    return userRepository.save(u);
                });
    }

    @Test
    @DisplayName("GET /rbac/users/list - renders users list")
    void getUsersList() throws Exception {
        mockMvc.perform(get("/rbac/users/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @DisplayName("GET /rbac/users/create - renders create form")
    void getCreateUserForm() throws Exception {
        mockMvc.perform(get("/rbac/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/create"))
                .andExpect(model().attributeExists("branches"))
                .andExpect(model().attributeExists("availableRoles"))
                .andExpect(model().attributeExists("studyPrograms"));
    }

    @Test
    @DisplayName("GET /rbac/users/view/{id} - renders user details and activity tracking summary")
    void getViewUser() throws Exception {
        mockMvc.perform(get("/rbac/users/view/" + sampleUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/view"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("activitySummary"))
                .andExpect(model().attributeExists("recentActivities"))
                .andExpect(model().attributeExists("userCustomers"))
                .andExpect(model().attributeExists("userAccounts"))
                .andExpect(model().attributeExists("userTransactions"));
    }

    @Test
    @DisplayName("GET /rbac/roles/list - renders roles list")
    void getRolesList() throws Exception {
        mockMvc.perform(get("/rbac/roles/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/roles/list"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    @DisplayName("GET /rbac/permissions/list - renders permissions list")
    void getPermissionsList() throws Exception {
        mockMvc.perform(get("/rbac/permissions/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/permissions/list"))
                .andExpect(model().attributeExists("permissions"));
    }

    @Test
    @DisplayName("GET /rbac/users/list with filter status=PENDING - renders filtered users list")
    void getUsersList_withPendingFilter() throws Exception {
        mockMvc.perform(get("/rbac/users/list").param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(view().name("rbac/users/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("status", "PENDING"));
    }

    @Test
    @DisplayName("POST /rbac/users/{id}/approve - approves user and redirects to pending list")
    void approveUser_redirectsToUsersList() throws Exception {
        User pendingUser = new User();
        pendingUser.setUsername("pending_user_" + System.currentTimeMillis());
        pendingUser.setPassword("$2a$10$dummy");
        pendingUser.setFullName("Pending User");
        pendingUser.setEmail(pendingUser.getUsername() + "@tazkia.ac.id");
        pendingUser.setApproved(false);
        pendingUser.setEnabled(false);
        pendingUser = userRepository.save(pendingUser);

        mockMvc.perform(post("/rbac/users/" + pendingUser.getId() + "/approve"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rbac/users/list?status=PENDING"));
    }
}

