package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.BaseIntegrationTest;
import id.ac.tazkia.minibank.dto.rbac.PermissionForm;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.repository.PermissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RbacPermissionService Integration Tests")
class RbacPermissionServiceTest extends BaseIntegrationTest {

    @Autowired private RbacPermissionService rbacPermissionService;
    @Autowired private PermissionRepository permissionRepository;

    @Test
    @DisplayName("createPermission & searchPermissions - berhasil membuat dan memfilter permission")
    void createAndSearchPermission() {
        PermissionForm form = new PermissionForm();
        form.setPermissionCode("TAX_REPORT_VIEW");
        form.setPermissionName("View Tax Reports");
        form.setCategory("REPORT");
        form.setDescription("Lihat laporan pajak bank");

        Permission created = rbacPermissionService.createPermission(form);
        assertNotNull(created.getId());
        assertEquals("TAX_REPORT_VIEW", created.getPermissionCode());
        assertEquals("REPORT", created.getCategory());

        Page<Permission> page = rbacPermissionService.searchPermissions("REPORT", "tax", PageRequest.of(0, 10));
        assertTrue(page.getContent().stream().anyMatch(p -> p.getPermissionCode().equals("TAX_REPORT_VIEW")));
    }
}

