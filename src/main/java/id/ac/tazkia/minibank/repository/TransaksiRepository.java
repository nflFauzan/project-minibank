package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.StatusTransaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransaksiRepository extends JpaRepository<Transaksi, UUID> {

    @Query(value = "select nextval('trx_seq')", nativeQuery = true)
    long nextSeq();

    @Query("""
        select t from Transaksi t
        where (:type is null or t.tipe = :type)
          and (
               :q is null or :q = ''
               or lower(t.nomorTransaksi) like lower(concat('%', :q, '%'))
               or lower(t.keterangan) like lower(concat('%', :q, '%'))
          )
    """)
    Page<Transaksi> search(@Param("q") String q,
                           @Param("type") TipeTransaksi type,
                           Pageable pageable);

    List<Transaksi> findByGroupIdOrderByProcessedAtAsc(UUID groupId);

    List<Transaksi> findByStatusOrderByProcessedAtDesc(StatusTransaksi status);

    long countByStatus(StatusTransaksi status);

   @Query("select count(t) from Transaksi t where t.channel = :channel")
   long countByChannel(@Param("channel") String channel);

    List<id.ac.tazkia.minibank.entity.Transaksi> findByRekeningOrderByProcessedAtAsc(id.ac.tazkia.minibank.entity.Rekening rekening);

    List<id.ac.tazkia.minibank.entity.Transaksi> findByRekeningOrderByProcessedAtDesc(id.ac.tazkia.minibank.entity.Rekening rekening);

    List<id.ac.tazkia.minibank.entity.Transaksi> findByRekeningAndPrintedFalseOrderByProcessedAtAsc(id.ac.tazkia.minibank.entity.Rekening rekening);

    long countByRekeningAndPrintedFalse(id.ac.tazkia.minibank.entity.Rekening rekening);

    List<id.ac.tazkia.minibank.entity.Transaksi> findByRekeningAndProcessedAtBetweenOrderByProcessedAtAsc(
            id.ac.tazkia.minibank.entity.Rekening rekening, java.time.LocalDateTime start, java.time.LocalDateTime end);

    List<id.ac.tazkia.minibank.entity.Transaksi> findByRekeningAndProcessedAtGreaterThanEqualOrderByProcessedAtAsc(
            id.ac.tazkia.minibank.entity.Rekening rekening, java.time.LocalDateTime start);

    List<id.ac.tazkia.minibank.entity.Transaksi> findByRekeningAndProcessedAtLessThanEqualOrderByProcessedAtAsc(
            id.ac.tazkia.minibank.entity.Rekening rekening, java.time.LocalDateTime end);

    List<Transaksi> findByProcessedAtBetweenOrderByProcessedAtAsc(java.time.LocalDateTime start, java.time.LocalDateTime end);

    List<Transaksi> findByProcessedByUsernameOrderByProcessedAtDesc(String username, Pageable pageable);

    long countByProcessedByUsername(String username);

    long countByProcessedByUsernameAndTipe(String username, TipeTransaksi tipe);

    @Query("select coalesce(sum(t.jumlah), 0) from Transaksi t where t.processedByUsername = :username and (t.status is null or t.status = 'SUCCESS')")
    BigDecimal sumTotalNominalByProcessedByUsername(@Param("username") String username);
}
