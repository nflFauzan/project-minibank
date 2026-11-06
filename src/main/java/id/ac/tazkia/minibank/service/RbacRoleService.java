package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.rbac.RoleForm;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.repository.PermissionRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RbacRoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<Role> listRoles(String search) {
        if (search != null && !search.isBlank()) {
            return roleRepository.searchRoles(search);
        }
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role tidak ditemukan: " + id));
    }

    public List<Permission> listAllPermissions() {
        return permissionRepository.findAll();
    }

    @Transactional
    public Role createRole(RoleForm form) {
        String cleanCode = form.getRoleCode().trim().toUpperCase();
        if (roleRepository.findByRoleCode(cleanCode).isPresent()) {
            throw new IllegalArgumentException("Kode role '" + cleanCode + "' sudah terdaftar.");
        }

        String securityName = cleanCode.startsWith("ROLE_") ? cleanCode : "ROLE_" + cleanCode;
        if (roleRepository.findByName(securityName).isPresent()) {
            throw new IllegalArgumentException("Role dengan nama '" + securityName + "' sudah terdaftar.");
        }

        Role role = new Role();
        role.setRoleCode(cleanCode);
        role.setName(securityName);
        role.setDisplayName(form.getRoleName().trim());
        role.setDescription(form.getDescription());
        role.setActive(true);

        return roleRepository.save(role);
    }

    @Transactional
    public Role updateRole(RoleForm form) {
        Role role = findById(form.getId());
        String cleanCode = form.getRoleCode().trim().toUpperCase();

        if (!cleanCode.equalsIgnoreCase(role.getRoleCode())) {
            roleRepository.findByRoleCode(cleanCode).ifPresent(other -> {
                if (!other.getId().equals(role.getId())) {
                    throw new IllegalArgumentException("Kode role '" + cleanCode + "' sudah digunakan.");
                }
            });
            role.setRoleCode(cleanCode);
            role.setName(cleanCode.startsWith("ROLE_") ? cleanCode : "ROLE_" + cleanCode);
        }

        role.setDisplayName(form.getRoleName().trim());
        role.setDescription(form.getDescription());

        return roleRepository.save(role);
    }

    @Transactional
    public void assignPermission(Long roleId, Long permissionId) {
        Role role = findById(roleId);
        Permission perm = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission tidak ditemukan: " + permissionId));
        role.getPermissions().add(perm);
        roleRepository.save(role);
    }

    @Transactional
    public void removePermission(Long roleId, Long permissionId) {
        Role role = findById(roleId);
        role.getPermissions().removeIf(p -> p.getId().equals(permissionId));
        roleRepository.save(role);
    }

    @Transactional
    public void toggleActive(Long roleId, boolean active) {
        Role role = findById(roleId);
        role.setActive(active);
        roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(Long roleId) {
        Role role = findById(roleId);
        if ("ROLE_ADMIN".equals(role.getName())) {
            throw new IllegalStateException("ROLE_ADMIN tidak dapat dihapus.");
        }
        role.getPermissions().clear();
        roleRepository.save(role);
        roleRepository.deleteById(roleId);
    }
}

