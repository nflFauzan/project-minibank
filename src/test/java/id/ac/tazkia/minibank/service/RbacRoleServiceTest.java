package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.rbac.RoleForm;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.repository.PermissionRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RbacRoleService Integration Tests")
class RbacRoleServiceTest extends BaseIntegrationTest {

    @Autowired private RbacRoleService rbacRoleService;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;

    @Test
    @DisplayName("createRole & updateRole - berhasil membuat dan mengubah role")
    void createAndUpdateRole() {
        RoleForm form = new RoleForm();
        form.setRoleCode("AUDITOR_TEST");
        form.setRoleName("Auditor Test");
        form.setDescription("Peran audit");

        Role created = rbacRoleService.createRole(form);
        assertNotNull(created.getId());
        assertEquals("AUDITOR_TEST", created.getRoleCode());
        assertEquals("ROLE_AUDITOR_TEST", created.getName());
        assertTrue(created.isActive());

        // Update
        RoleForm updateForm = new RoleForm();
        updateForm.setId(created.getId());
        updateForm.setRoleCode("AUDITOR_UPDATED");
        updateForm.setRoleName("Auditor Senior");
        updateForm.setDescription("Peran audit senior");

        Role updated = rbacRoleService.updateRole(updateForm);
        assertEquals("AUDITOR_UPDATED", updated.getRoleCode());
        assertEquals("Auditor Senior", updated.getDisplayName());
    }

    @Test
    @DisplayName("assignPermission & removePermission - hubungkan permission ke role")
    void assignAndRemovePermission() {
        RoleForm form = new RoleForm();
        form.setRoleCode("OPERATOR_TEST");
        form.setRoleName("Operator Test");
        Role r = rbacRoleService.createRole(form);

        Permission p = permissionRepository.findByPermissionCode("CUSTOMER_VIEW")
                .orElseGet(() -> permissionRepository.save(new Permission("CUSTOMER_VIEW", "View Customer", "CUSTOMER", "Desc")));

        rbacRoleService.assignPermission(r.getId(), p.getId());
        Role reloaded = rbacRoleService.findById(r.getId());
        assertTrue(reloaded.getPermissions().stream().anyMatch(perm -> perm.getId().equals(p.getId())));

        rbacRoleService.removePermission(r.getId(), p.getId());
        Role reloaded2 = rbacRoleService.findById(r.getId());
        assertFalse(reloaded2.getPermissions().stream().anyMatch(perm -> perm.getId().equals(p.getId())));
    }
}

