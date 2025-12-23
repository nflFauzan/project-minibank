package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    // biar DashboardService kamu gak error lagi
    long countByStatusActive(boolean statusActive);
}
