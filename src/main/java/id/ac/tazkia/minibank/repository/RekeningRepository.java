package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RekeningRepository extends JpaRepository<Rekening, Long> {
    long countByStatusActive(boolean active);
}
