package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
        order by t.processedAt desc
    """)
    Page<Transaksi> search(@Param("q") String q,
                           @Param("type") TipeTransaksi type,
                           Pageable pageable);

    List<Transaksi> findByGroupIdOrderByProcessedAtAsc(UUID groupId);

   @Query("select count(t) from Transaksi t where t.channel = :channel")
long countByChannel(@Param("channel") String channel);


}
