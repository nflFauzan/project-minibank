package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Rekening;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RekeningRepository extends JpaRepository<Rekening, Long> {

    // status: "ALL" | "ACTIVE" | "CLOSED"
    @Query("""
        select r from Rekening r
        where (:status = 'ALL')
           or (:status = 'ACTIVE' and r.statusActive = true)
           or (:status = 'CLOSED' and r.statusActive = false)
        order by r.id desc
    """)
    List<Rekening> findByStatus(@Param("status") String status);

    @Query("""
        select r from Rekening r
        where (
            lower(r.nomorRekening) like lower(concat('%', :q, '%'))
            or lower(r.namaNasabah) like lower(concat('%', :q, '%'))
        )
        and (
            (:status = 'ALL')
            or (:status = 'ACTIVE' and r.statusActive = true)
            or (:status = 'CLOSED' and r.statusActive = false)
        )
        order by r.id desc
    """)
    List<Rekening> search(@Param("q") String q, @Param("status") String status);

    // buat nomor urut 6 digit dari id max (simple & cukup untuk tugas)
    @Query("select coalesce(max(r.id), 0) + 1 from Rekening r")
    Long nextIdValue();

    default String nextSequence6() {
        Long next = nextIdValue();
        return String.format("%06d", next);
    }

    @Query("select coalesce(sum(r.nominalSetoranAwal), 0) from Rekening r where r.statusActive = true")
    BigDecimal sumNominalSetoranAwalActive();

    // ===== LOCKING untuk transaksi (anti race condition) =====
    Optional<Rekening> findByNomorRekening(String nomorRekening);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Rekening r where r.nomorRekening = :no")
    Optional<Rekening> findByNomorRekeningForUpdate(@Param("no") String no);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Rekening r where r.nomorRekening in :nos")
    List<Rekening> findByNomorRekeningInForUpdate(@Param("nos") List<String> nos);

    // dashboard
    long countByStatusActive(boolean statusActive);
}
