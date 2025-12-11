package id.ac.tazkia.minibank.repository;

import java.util.List;
import id.ac.tazkia.minibank.entity.Nasabah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {

    @Query("select max(n.cif) from Nasabah n")
    String findMaxCif();
    List<Nasabah> findTop5ByOrderByCreatedAtDesc();

}
