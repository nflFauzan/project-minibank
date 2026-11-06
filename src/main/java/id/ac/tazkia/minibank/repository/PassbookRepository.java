package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Passbook;
import id.ac.tazkia.minibank.entity.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PassbookRepository extends JpaRepository<Passbook, UUID> {

    Optional<Passbook> findByRekening(Rekening rekening);

    Optional<Passbook> findByNomorRekening(String nomorRekening);

    Optional<Passbook> findByPassbookNumber(String passbookNumber);

    @Query(value = "select nextval('passbook_no_seq')", nativeQuery = true)
    long nextPassbookSeq();

    default String formatNextPassbookNumber() {
        long seq = nextPassbookSeq();
        return String.format("PB%07d", seq);
    }
}

