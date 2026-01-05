package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransaksiRepository extends JpaRepository<Transaksi, UUID> {

    @Query(value = "select nextval('transaksi_no_seq')", nativeQuery = true)
    long nextNo();

    @Query("""
        select t from Transaksi t
        where
          (:q is null or :q = '' or
           lower(t.nomorTransaksi) like lower(concat('%', :q, '%')) or
           lower(coalesce(t.keterangan,'')) like lower(concat('%', :q, '%')))
        and (:tipe is null or t.tipe = :tipe)
        order by t.processedAt desc
    """)
    Page<Transaksi> search(@Param("q") String q,
                           @Param("tipe") TipeTransaksi tipe,
                           Pageable pageable);

    List<Transaksi> findByGroupIdOrderByProcessedAtAsc(UUID groupId);
}
