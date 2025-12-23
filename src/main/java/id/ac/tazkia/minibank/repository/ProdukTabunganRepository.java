package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdukTabunganRepository extends JpaRepository<ProdukTabungan, Long> {

    @Query("select p from ProdukTabungan p where p.aktif = true order by p.namaProduk asc")
    List<ProdukTabungan> findActiveProducts();
}
