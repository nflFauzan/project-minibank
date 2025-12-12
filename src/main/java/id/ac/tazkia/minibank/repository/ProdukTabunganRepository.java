package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdukTabunganRepository extends JpaRepository<ProdukTabungan, Long> {

    // buat dropdown di form pembukaan rekening
    List<ProdukTabungan> findByAktifTrueOrderByNamaProdukAsc();
}
