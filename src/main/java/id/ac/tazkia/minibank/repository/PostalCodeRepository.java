package id.ac.tazkia.minibank.repository;

import id.ac.tazkia.minibank.entity.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {

    Optional<PostalCode> findFirstByKodePos(String kodePos);

    @Query("select distinct p.provinsi from PostalCode p order by p.provinsi")
    List<String> findDistinctProvinsi();

    @Query("select distinct p.kota from PostalCode p where p.provinsi = :prov order by p.kota")
    List<String> findDistinctKotaByProvinsi(@Param("prov") String prov);

    @Query("select distinct p.kecamatan from PostalCode p where p.provinsi = :prov and p.kota = :kota order by p.kecamatan")
    List<String> findDistinctKecamatan(@Param("prov") String prov, @Param("kota") String kota);

    @Query("select distinct p.kelurahan from PostalCode p where p.provinsi = :prov and p.kota = :kota and p.kecamatan = :kec order by p.kelurahan")
    List<String> findDistinctKelurahan(@Param("prov") String prov, @Param("kota") String kota, @Param("kec") String kec);

    @Query("select distinct p.kodePos from PostalCode p where p.provinsi = :prov and p.kota = :kota and p.kecamatan = :kec and p.kelurahan = :kel order by p.kodePos")
    List<String> findKodePosByHierarchy(@Param("prov") String prov,
                                        @Param("kota") String kota,
                                        @Param("kec") String kec,
                                        @Param("kel") String kel);
}
