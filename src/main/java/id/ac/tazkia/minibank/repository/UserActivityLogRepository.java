package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.ActivityType;
import id.ac.tazkia.minibank.entity.UserActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {

    List<UserActivityLog> findByUsernameOrderByTimestampDesc(String username, Pageable pageable);

    long countByUsername(String username);

    long countByUsernameAndActivityType(String username, ActivityType activityType);

    @Query("SELECT l FROM UserActivityLog l WHERE " +
           "(:username IS NULL OR :username = '' OR LOWER(l.username) = LOWER(:username)) AND " +
           "(:module IS NULL OR :module = '' OR l.module = :module) AND " +
           "(:activityType IS NULL OR l.activityType = :activityType) AND " +
           "(:startDate IS NULL OR l.timestamp >= :startDate) AND " +
           "(:endDate IS NULL OR l.timestamp <= :endDate) AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           " LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(l.referenceNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(l.userFullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "ORDER BY l.timestamp DESC")
    Page<UserActivityLog> searchActivities(@Param("username") String username,
                                          @Param("module") String module,
                                          @Param("activityType") ActivityType activityType,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate,
                                          @Param("keyword") String keyword,
                                          Pageable pageable);
}

