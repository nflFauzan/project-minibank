package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produk_tabungan")
public class ProdukTabungan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // misal: TAB_WAD, TAB_MUD, TAB_HAJI
    @Column(name = "kode_produk", nullable = false, unique = true, length = 20)
    private String kodeProduk;

    @Column(name = "nama_produk", nullable = false, length = 150)
    private String namaProduk;

    // WADIAH / MUDHARABAH / LAINNYA (biar fleksibel, pakai String dulu)
    @Column(name = "jenis_akad", length = 50)
    private String jenisAkad;

    @Column(name = "deskripsi_singkat", length = 255)
    private String deskripsiSingkat;

    // minimum setoran awal kalau mau dipakai di form pembukaan rekening
    @Column(name = "setoran_awal_minimum")
    private BigDecimal setoranAwalMinimum;

    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    // ===== GETTER SETTER =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getJenisAkad() {
        return jenisAkad;
    }

    public void setJenisAkad(String jenisAkad) {
        this.jenisAkad = jenisAkad;
    }

    public String getDeskripsiSingkat() {
        return deskripsiSingkat;
    }

    public void setDeskripsiSingkat(String deskripsiSingkat) {
        this.deskripsiSingkat = deskripsiSingkat;
    }

    public BigDecimal getSetoranAwalMinimum() {
        return setoranAwalMinimum;
    }

    public void setSetoranAwalMinimum(BigDecimal setoranAwalMinimum) {
        this.setoranAwalMinimum = setoranAwalMinimum;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}
