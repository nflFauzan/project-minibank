package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusActiveTrue();
}
