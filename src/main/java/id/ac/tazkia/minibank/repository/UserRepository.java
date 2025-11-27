package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByApprovedFalse(); // pending
}
