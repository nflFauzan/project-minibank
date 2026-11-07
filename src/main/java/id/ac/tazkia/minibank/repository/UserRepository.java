package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles", "branch"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles", "branch"})
    Optional<User> findById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // PENDING = approved false
    List<User> findByApprovedFalse();

    long countByApprovedFalse();
    long countByApprovedTrueAndEnabledTrue();
    long countByApprovedTrueAndEnabledFalse();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.branch WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.branch LEFT JOIN u.roles r WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           " LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR :status = 'ALL' OR " +
           " (:status = 'PENDING' AND u.approved = false) OR " +
           " (:status = 'ACTIVE' AND u.approved = true AND u.enabled = true) OR " +
           " (:status = 'INACTIVE' AND u.approved = true AND u.enabled = false) OR " +
           " (:status = 'LOCKED' AND u.locked = true)) AND " +
           "(:role IS NULL OR :role = '' OR :role = 'ALL' OR r.name = :role) AND " +
           "(:branchId IS NULL OR u.branch.id = :branchId)",
           countQuery = "SELECT COUNT(DISTINCT u) FROM User u LEFT JOIN u.roles r WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           " LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR :status = 'ALL' OR " +
           " (:status = 'PENDING' AND u.approved = false) OR " +
           " (:status = 'ACTIVE' AND u.approved = true AND u.enabled = true) OR " +
           " (:status = 'INACTIVE' AND u.approved = true AND u.enabled = false) OR " +
           " (:status = 'LOCKED' AND u.locked = true)) AND " +
           "(:role IS NULL OR :role = '' OR :role = 'ALL' OR r.name = :role) AND " +
           "(:branchId IS NULL OR u.branch.id = :branchId)")
    Page<User> searchUsersFiltered(
            @Param("search") String search,
            @Param("status") String status,
            @Param("role") String role,
            @Param("branchId") Long branchId,
            Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.branch WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<User> searchUsersList(@Param("search") String search);
}
