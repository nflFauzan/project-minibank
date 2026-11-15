package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {

    Optional<Nasabah> findByCif(String cif);

    @Query("""
        select n from Nasabah n
        where (:q is null or :q = '' or
              lower(n.namaLengkap) like lower(concat('%', :q, '%'))
           or lower(n.cif) like lower(concat('%', :q, '%'))
           or lower(n.nik) like lower(concat('%', :q, '%'))
           or lower(n.email) like lower(concat('%', :q, '%'))
           or lower(n.noHp) like lower(concat('%', :q, '%')))
        order by n.createdAt desc
    """)
    Page<Nasabah> searchCustomers(@Param("q") String q, Pageable pageable);

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
    long countByPeriodeId(Long periodeId);

    @Query("""
        select n from Nasabah n
        where n.createdAt >= :startDate and n.createdAt <= :endDate
          and (:status is null or n.status = :status)
          and (:q is null or :q = '' or
              lower(n.namaLengkap) like lower(concat('%', :q, '%'))
           or lower(n.cif) like lower(concat('%', :q, '%'))
           or lower(n.nik) like lower(concat('%', :q, '%'))
           or lower(n.email) like lower(concat('%', :q, '%'))
           or lower(n.noHp) like lower(concat('%', :q, '%')))
        order by n.createdAt desc
    """)
    Page<Nasabah> searchCustomersAllPeriods(
            @Param("q") String q,
            @Param("status") NasabahStatus status,
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate,
            Pageable pageable
    );

    @Query("""
        select n from Nasabah n
        where n.periode.id = :periodeId
          and n.createdAt >= :startDate and n.createdAt <= :endDate
          and (:status is null or n.status = :status)
          and (:q is null or :q = '' or
              lower(n.namaLengkap) like lower(concat('%', :q, '%'))
           or lower(n.cif) like lower(concat('%', :q, '%'))
           or lower(n.nik) like lower(concat('%', :q, '%'))
           or lower(n.email) like lower(concat('%', :q, '%'))
           or lower(n.noHp) like lower(concat('%', :q, '%')))
        order by n.createdAt desc
    """)
    Page<Nasabah> searchCustomersByPeriod(
            @Param("q") String q,
            @Param("status") NasabahStatus status,
            @Param("periodeId") Long periodeId,
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate,
            Pageable pageable
    );

    List<Nasabah> findTop5ByStatusOrderByCreatedAtDesc(NasabahStatus status);

    @Query("select max(n.cif) from Nasabah n where n.cif like 'C%' and length(n.cif) = 8")
    String findMaxCif();

    // kompatibilitas kalau masih ada yang manggil method lama
    default List<Nasabah> findByApprovedFalseOrderByCreatedAtDesc() {
        return findByStatusOrderByCreatedAtDesc(NasabahStatus.INACTIVE);
    }
       default List<Nasabah> findActiveCustomers() {
        return findByStatus(NasabahStatus.ACTIVE);
    }

    // ===== KOMPATIBILITAS METHOD LAMA (JANGAN DIHAPUS) =====
    default List<Nasabah> findByStatusActiveTrue() {
        return findByStatus(NasabahStatus.ACTIVE);
    }

    long countByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    List<Nasabah> findByCreatedByOrderByCreatedAtDesc(String createdBy, Pageable pageable);

    long countByCreatedBy(String createdBy);
}
