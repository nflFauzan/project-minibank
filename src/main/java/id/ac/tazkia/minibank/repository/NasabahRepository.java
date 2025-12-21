package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {

    Optional<Nasabah> findByCif(String cif);

    List<Nasabah> findTop5ByOrderByCreatedAtDesc();

    List<Nasabah> findAllByOrderByCreatedAtDesc();

    List<Nasabah> findByStatusOrderByCreatedAtDesc(NasabahStatus status);

    List<Nasabah> findByStatusInOrderByApprovedAtDesc(List<NasabahStatus> statuses);

    long countByStatus(NasabahStatus status);
    List<Nasabah> findTop5ByStatusOrderByCreatedAtDesc(NasabahStatus status);

    @Query("select max(n.cif) from Nasabah n")
    String findMaxCif();

    // kompatibilitas kalau masih ada yang manggil method lama
    default List<Nasabah> findByApprovedFalseOrderByCreatedAtDesc() {
        return findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE);
    }
}
