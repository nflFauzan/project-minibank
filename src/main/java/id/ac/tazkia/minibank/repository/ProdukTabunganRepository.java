package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdukTabunganRepository extends JpaRepository<ProdukTabungan, Long> {

    @Query("select p from ProdukTabungan p where p.aktif = true order by p.namaProduk asc")
    List<ProdukTabungan> findActiveProducts();

    Optional<ProdukTabungan> findByUuid(String uuid);

    Optional<ProdukTabungan> findByKodeProduk(String kodeProduk);

    Optional<ProdukTabungan> findByKodeProdukIgnoreCase(String kodeProduk);

    boolean existsByKodeProdukIgnoreCase(String kodeProduk);

    default Optional<ProdukTabungan> findByIdOrUuid(String idOrUuid) {
        if (idOrUuid == null || idOrUuid.isBlank()) {
            return Optional.empty();
        }
        try {
            Long numericId = Long.parseLong(idOrUuid.trim());
            Optional<ProdukTabungan> byId = findById(numericId);
            if (byId.isPresent()) {
                return byId;
            }
        } catch (NumberFormatException ignored) {
            // Not a numeric ID, fallback to uuid
        }
        return findByUuid(idOrUuid.trim());
    }
}
