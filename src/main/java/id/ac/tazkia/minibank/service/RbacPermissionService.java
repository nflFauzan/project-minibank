package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.rbac.PermissionForm;
import id.ac.tazkia.minibank.entity.Permission;
import id.ac.tazkia.minibank.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RbacPermissionService {

    private final PermissionRepository permissionRepository;

    public Page<Permission> searchPermissions(String category, String search, Pageable pageable) {
        return permissionRepository.searchPermissions(category, search, pageable);
    }

    public List<String> listCategories() {
        return permissionRepository.findDistinctCategories();
    }

    public Permission findById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission tidak ditemukan: " + id));
    }

    @Transactional
    public Permission createPermission(PermissionForm form) {
        String cleanCode = form.getPermissionCode().trim().toUpperCase();
        if (permissionRepository.findByPermissionCode(cleanCode).isPresent()) {
            throw new IllegalArgumentException("Permission dengan kode '" + cleanCode + "' sudah terdaftar.");
        }

        Permission p = new Permission();
        p.setPermissionCode(cleanCode);
        p.setPermissionName(form.getPermissionName().trim());
        p.setCategory(form.getCategory().trim().toUpperCase());
        p.setDescription(form.getDescription());

        return permissionRepository.save(p);
    }

    @Transactional
    public Permission updatePermission(PermissionForm form) {
        Permission p = findById(form.getId());
        String cleanCode = form.getPermissionCode().trim().toUpperCase();

        if (!cleanCode.equalsIgnoreCase(p.getPermissionCode())) {
            permissionRepository.findByPermissionCode(cleanCode).ifPresent(other -> {
                if (!other.getId().equals(p.getId())) {
                    throw new IllegalArgumentException("Permission dengan kode '" + cleanCode + "' sudah terdaftar.");
                }
            });
            p.setPermissionCode(cleanCode);
        }

        p.setPermissionName(form.getPermissionName().trim());
        p.setCategory(form.getCategory().trim().toUpperCase());
        p.setDescription(form.getDescription());

        return permissionRepository.save(p);
    }

    @Transactional
    public void deletePermission(Long permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}

