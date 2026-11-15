package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.PeriodePraktikum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodePraktikumRepository extends JpaRepository<PeriodePraktikum, Long> {

    Optional<PeriodePraktikum> findByIsActiveTrue();

    Optional<PeriodePraktikum> findByKode(String kode);

    @Query("SELECT p FROM PeriodePraktikum p ORDER BY p.createdAt DESC")
    List<PeriodePraktikum> findAllOrderByCreatedAtDesc();

    @Query("SELECT p FROM PeriodePraktikum p ORDER BY p.isActive DESC, p.tanggalMulai DESC")
    List<PeriodePraktikum> findAllForDropdown();
}
