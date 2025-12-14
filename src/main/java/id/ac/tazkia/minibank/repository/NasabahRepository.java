package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {

    Optional<Nasabah> findByCif(String cif);

    // Dashboard
    List<Nasabah> findTop5ByOrderByCreatedAtDesc();

    // Generate CIF (format CIF kamu fixed length, jadi max string aman)
    @Query("select max(n.cif) from Nasabah n")
    String findMaxCif();

    // Approval Queue (baru)
    List<Nasabah> findByStatusOrderByCreatedAtDesc(NasabahStatus status);
    long countByStatus(NasabahStatus status);

    // ====== COMPATIBILITY LAMA (BIAR FILE LAMA GAK ERROR) ======
    // kalau masih ada controller/service lama yang manggil approvedFalse, kita map ke status PENDING
    default List<Nasabah> findByApprovedFalseOrderByCreatedAtDesc() {
        return findByStatusOrderByCreatedAtDesc(NasabahStatus.PENDING);
    }
}
