package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Pastikan roles ikut ter-load saat login (hindari LazyInitializationException)
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);

    List<User> findByApprovedFalse(); // pending
}
