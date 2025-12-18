package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // PENDING = approved false
    List<User> findByApprovedFalse();
}
