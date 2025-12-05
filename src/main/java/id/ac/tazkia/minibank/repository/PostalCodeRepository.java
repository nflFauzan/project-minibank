package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {
    Optional<PostalCode> findFirstByKodePos(String kodePos);
}
