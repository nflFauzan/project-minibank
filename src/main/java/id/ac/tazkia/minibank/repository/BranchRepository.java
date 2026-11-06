package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByCode(String code);
    List<Branch> findByActiveTrueOrderByCodeAsc();
}

