package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Optional<Role> findByRoleCode(String roleCode);

    List<Role> findByActiveTrueOrderByRoleCodeAsc();

    @Query("SELECT r FROM Role r WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(r.roleCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(r.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(r.displayName) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Role> searchRoles(@Param("search") String search);
}
