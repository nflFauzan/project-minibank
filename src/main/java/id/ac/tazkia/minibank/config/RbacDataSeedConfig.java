package id.ac.tazkia.minibank.config;

import id.ac.tazkia.minibank.entity.Branch;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.BranchRepository;
import id.ac.tazkia.minibank.repository.PermissionRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class RbacDataSeedConfig {

    private final BranchRepository branchRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Bean
    @Order(2)
    @Transactional
    public CommandLineRunner seedRbacData() {
        return args -> {
            try {
                // 1. Seed Branches
                seedBranches();

                // 2. Seed Permissions
                seedPermissions();

                // 3. Update Roles with Metadata & Permissions
                seedRolesAndPermissions();

                // 4. Set default branch on default users
                seedUserBranches();

            } catch (Exception ex) {
                System.err.println("RBAC Seeding error: " + ex.getMessage());
                ex.printStackTrace();
            }
        };
    }

    private void seedBranches() {
        Map<String, String> branches = new LinkedHashMap<>();
        branches.put("HO001", "Kantor Pusat Jakarta");
        branches.put("BDG01", "Cabang Bandung");
        branches.put("JKT01", "Cabang Jakarta Timur");
        branches.put("SBY01", "Cabang Surabaya");
        branches.put("YGY01", "Cabang Yogyakarta");

        for (Map.Entry<String, String> entry : branches.entrySet()) {
            if (branchRepository.findByCode(entry.getKey()).isEmpty()) {
                branchRepository.save(new Branch(entry.getKey(), entry.getValue()));
            }
        }
    }

    private void seedPermissions() {
        List<PermissionRecord> records = List.of(
                // CUSTOMER
                new PermissionRecord("CUSTOMER_VIEW", "View Customer", "CUSTOMER", "View customer information"),
                new PermissionRecord("CUSTOMER_CREATE", "Create Customer", "CUSTOMER", "Register new customers"),
                new PermissionRecord("CUSTOMER_UPDATE", "Update Customer", "CUSTOMER", "Update customer information"),
                new PermissionRecord("CUSTOMER_ACTIVATE", "Activate/Deactivate Customer", "CUSTOMER", "Change customer active status"),

                // APPROVAL
                new PermissionRecord("CUSTOMER_APPROVE", "Approve Customer", "APPROVAL", "Approve customer registration"),
                new PermissionRecord("ACCOUNT_APPROVE", "Approve Account", "APPROVAL", "Approve bank account opening"),
                new PermissionRecord("TRANSACTION_APPROVE", "Approve Transaction", "APPROVAL", "Approve large transactions"),
                new PermissionRecord("APPROVAL_VIEW", "View Approval Queue", "APPROVAL", "View pending approval requests"),

                // ACCOUNT
                new PermissionRecord("ACCOUNT_VIEW", "View Account", "ACCOUNT", "View account information"),
                new PermissionRecord("ACCOUNT_CREATE", "Create Account", "ACCOUNT", "Open new accounts for customers"),
                new PermissionRecord("ACCOUNT_UPDATE", "Update Account", "ACCOUNT", "Update account information"),
                new PermissionRecord("BALANCE_VIEW", "View Balance", "ACCOUNT", "Check account balances"),

                // PRODUCT
                new PermissionRecord("PRODUCT_VIEW", "View Product", "PRODUCT", "View banking products"),

                // TRANSACTION
                new PermissionRecord("TRANSACTION_VIEW", "View Transaction", "TRANSACTION", "View transaction history"),
                new PermissionRecord("TRANSACTION_DEPOSIT", "Process Deposit", "TRANSACTION", "Process cash deposits"),
                new PermissionRecord("TRANSACTION_WITHDRAWAL", "Process Withdrawal", "TRANSACTION", "Process cash withdrawals"),
                new PermissionRecord("TRANSACTION_TRANSFER", "Process Transfer", "TRANSACTION", "Process inter-account transfers"),

                // REPORT
                new PermissionRecord("REPORT_VIEW", "View Reports", "REPORT", "Generate and view financial reports"),

                // AUDIT
                new PermissionRecord("AUDIT_VIEW", "View Audit Log", "AUDIT", "View security and system audit logs"),

                // USER
                new PermissionRecord("USER_VIEW", "View Users", "USER", "View staff user accounts"),
                new PermissionRecord("USER_CREATE", "Create User", "USER", "Create staff user accounts"),
                new PermissionRecord("USER_UPDATE", "Update User", "USER", "Update staff user information"),
                new PermissionRecord("USER_DEACTIVATE", "Deactivate User", "USER", "Deactivate staff user accounts"),

                // BRANCH
                new PermissionRecord("BRANCH_VIEW", "View Branch", "BRANCH", "View branch offices"),
                new PermissionRecord("BRANCH_CREATE", "Create Branch", "BRANCH", "Register new bank branch"),
                new PermissionRecord("BRANCH_UPDATE", "Update Branch", "BRANCH", "Update bank branch details"),
                new PermissionRecord("BRANCH_DELETE", "Delete Branch", "BRANCH", "Remove branch office")
        );

        for (PermissionRecord r : records) {
            if (permissionRepository.findByPermissionCode(r.code).isEmpty()) {
                permissionRepository.save(new Permission(r.code, r.name, r.category, r.description));
            }
        }
    }

    private void seedRolesAndPermissions() {
        List<Permission> allPermissions = permissionRepository.findAll();
        Map<String, Permission> permMap = new HashMap<>();
        for (Permission p : allPermissions) {
            permMap.put(p.getPermissionCode(), p);
        }

        // ROLE_ADMIN
        roleRepository.findByName("ROLE_ADMIN").ifPresent(adminRole -> {
            adminRole.setRoleCode("ADMIN");
            adminRole.setDisplayName("System Administrator");
            adminRole.setDescription("Full system administrative access and user management");
            adminRole.setActive(true);
            adminRole.getPermissions().clear();
            adminRole.getPermissions().addAll(allPermissions);
            roleRepository.save(adminRole);
        });

        // ROLE_BRANCH_MANAGER
        roleRepository.findByName("ROLE_BRANCH_MANAGER").ifPresent(bmRole -> {
            bmRole.setRoleCode("BRANCH_MANAGER");
            bmRole.setDisplayName("Branch Manager");
            bmRole.setDescription("Monitor operations and provide approvals");
            bmRole.setActive(true);
            assignPermissions(bmRole, permMap, List.of(
                    "CUSTOMER_VIEW", "CUSTOMER_APPROVE", "CUSTOMER_ACTIVATE",
                    "ACCOUNT_VIEW", "ACCOUNT_APPROVE",
                    "TRANSACTION_VIEW", "TRANSACTION_APPROVE",
                    "APPROVAL_VIEW", "REPORT_VIEW", "AUDIT_VIEW",
                    "PRODUCT_VIEW", "BRANCH_VIEW"
            ));
            roleRepository.save(bmRole);
        });

        // ROLE_CS
        roleRepository.findByName("ROLE_CS").ifPresent(csRole -> {
            csRole.setRoleCode("CUSTOMER_SERVICE");
            csRole.setDisplayName("Customer Service");
            csRole.setDescription("Handle customer registration and account opening");
            csRole.setActive(true);
            assignPermissions(csRole, permMap, List.of(
                    "CUSTOMER_VIEW", "CUSTOMER_CREATE", "CUSTOMER_UPDATE",
                    "ACCOUNT_VIEW", "ACCOUNT_CREATE", "ACCOUNT_UPDATE",
                    "PRODUCT_VIEW"
            ));
            roleRepository.save(csRole);
        });

        // ROLE_TELLER
        roleRepository.findByName("ROLE_TELLER").ifPresent(tellerRole -> {
            tellerRole.setRoleCode("TELLER");
            tellerRole.setDisplayName("Teller");
            tellerRole.setDescription("Handle financial transactions");
            tellerRole.setActive(true);
            assignPermissions(tellerRole, permMap, List.of(
                    "TRANSACTION_VIEW", "TRANSACTION_DEPOSIT", "TRANSACTION_WITHDRAWAL",
                    "TRANSACTION_TRANSFER", "BALANCE_VIEW", "ACCOUNT_VIEW"
            ));
            roleRepository.save(tellerRole);
        });
    }

    private void assignPermissions(Role role, Map<String, Permission> permMap, List<String> codes) {
        role.getPermissions().clear();
        for (String code : codes) {
            Permission p = permMap.get(code);
            if (p != null) {
                role.getPermissions().add(p);
            }
        }
    }

    private void seedUserBranches() {
        Branch ho = branchRepository.findByCode("HO001").orElse(null);
        Branch jkt = branchRepository.findByCode("JKT01").orElse(null);

        userRepository.findByUsername("admin").ifPresent(u -> {
            if (u.getBranch() == null && ho != null) {
                u.setBranch(ho);
                userRepository.save(u);
            }
        });

        userRepository.findByUsername("cs1").ifPresent(u -> {
            if (u.getBranch() == null && jkt != null) {
                u.setBranch(jkt);
                userRepository.save(u);
            }
        });

        userRepository.findByUsername("teller1").ifPresent(u -> {
            if (u.getBranch() == null && jkt != null) {
                u.setBranch(jkt);
                userRepository.save(u);
            }
        });

        userRepository.findByUsername("zann").ifPresent(u -> {
            if (u.getBranch() == null && jkt != null) {
                u.setBranch(jkt);
                userRepository.save(u);
            }
        });

        userRepository.findByUsername("teller").ifPresent(u -> {
            if (u.getBranch() == null && jkt != null) {
                u.setBranch(jkt);
                userRepository.save(u);
            }
        });

        userRepository.findByUsername("spv").ifPresent(u -> {
            if (u.getBranch() == null && ho != null) {
                u.setBranch(ho);
                userRepository.save(u);
            }
        });
    }

    private record PermissionRecord(String code, String name, String category, String description) {}
}

