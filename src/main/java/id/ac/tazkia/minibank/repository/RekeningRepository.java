package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RekeningRepository extends JpaRepository<Rekening, Long> {

    // dipakai di DashboardService
    long countByStatusActive(boolean statusActive);
}
