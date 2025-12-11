package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {
    Optional<PostalCode> findFirstByKodePos(String kodePos);
        @Query("select distinct p.provinsi from PostalCode p order by p.provinsi")
    List<String> findDistinctProvinsi();

    @Query("select distinct p.kota from PostalCode p " +
           "where p.provinsi = :provinsi order by p.kota")
    List<String> findDistinctKotaByProvinsi(@Param("provinsi") String provinsi);

    @Query("select distinct p.kecamatan from PostalCode p " +
           "where p.provinsi = :provinsi and p.kota = :kota order by p.kecamatan")
    List<String> findDistinctKecamatan(@Param("provinsi") String provinsi,
                                       @Param("kota") String kota);

    @Query("select distinct p.kelurahan from PostalCode p " +
           "where p.provinsi = :provinsi and p.kota = :kota and p.kecamatan = :kecamatan " +
           "order by p.kelurahan")
    List<String> findDistinctKelurahan(@Param("provinsi") String provinsi,
                                       @Param("kota") String kota,
                                       @Param("kecamatan") String kecamatan);

}
