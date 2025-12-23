package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {

    Optional<Nasabah> findByCif(String cif);

    List<Nasabah> findTop5ByOrderByCreatedAtDesc();

    List<Nasabah> findAllByOrderByCreatedAtDesc();

    List<Nasabah> findByStatusOrderByCreatedAtDesc(NasabahStatus status);
        @Query("""
        select n from Nasabah n
        where n.status = :status and (
             lower(n.namaLengkap) like lower(concat('%', :q, '%'))
          or lower(n.cif) like lower(concat('%', :q, '%'))
          or lower(n.nik) like lower(concat('%', :q, '%'))
          or lower(n.email) like lower(concat('%', :q, '%'))
        )
        order by n.createdAt desc
    """)
    List<Nasabah> searchActiveForAccountOpen(@Param("status") NasabahStatus status, @Param("q") String q);

    List<Nasabah> findByStatusInOrderByApprovedAtDesc(List<NasabahStatus> statuses);

    List<Nasabah> findByStatus(NasabahStatus status);

    long countByStatus(NasabahStatus status);
    List<Nasabah> findTop5ByStatusOrderByCreatedAtDesc(NasabahStatus status);

    @Query("select max(n.cif) from Nasabah n")
    String findMaxCif();

    // kompatibilitas kalau masih ada yang manggil method lama
    default List<Nasabah> findByApprovedFalseOrderByCreatedAtDesc() {
        return findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE);
    }
       default List<Nasabah> findActiveCustomers() {
        return findByStatus(NasabahStatus.ACTIVE);
    }
}
