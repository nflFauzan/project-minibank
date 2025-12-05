package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Nasabah;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {
    List<Nasabah> findTop5ByOrderByCreatedAtDesc();
}
