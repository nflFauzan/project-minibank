package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {

    Optional<Nasabah> findByCif(String cif);

    // Dashboard
    List<Nasabah> findTop5ByOrderByCreatedAtDesc();
    List<Nasabah> findAllByOrderByCreatedAtDesc();

    @Query("select max(n.cif) from Nasabah n")
    String findMaxCif();

    // Pending queue (INACTIVE)
    List<Nasabah> findByStatusOrderByCreatedAtDesc(NasabahStatus status);
    long countByStatus(NasabahStatus status);

    // History (ACTIVE + REJECTED)
    List<Nasabah> findByStatusInOrderByApprovedAtDesc(Collection<NasabahStatus> statuses);

    // Compatibility lama (kalau masih ada yang manggil approvedFalse)
    default List<Nasabah> findByApprovedFalseOrderByCreatedAtDesc() {
        return findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE);
    }
}
