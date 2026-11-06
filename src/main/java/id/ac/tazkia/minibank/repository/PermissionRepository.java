package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermissionCode(String permissionCode);

    Page<Permission> findByCategory(String category, Pageable pageable);

    @Query("SELECT DISTINCT p.category FROM Permission p ORDER BY p.category ASC")
    List<String> findDistinctCategories();

    @Query("SELECT p FROM Permission p WHERE " +
           "(:category IS NULL OR :category = '' OR p.category = :category) AND " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(p.permissionCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.permissionName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Permission> searchPermissions(@Param("category") String category,
                                      @Param("search") String search,
                                      Pageable pageable);
}

