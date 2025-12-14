package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rekening")
public class Rekening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- info nasabah ---
    @Column(nullable = false, length = 8)
    private String cifNasabah;

    @Column(nullable = false, length = 200)
    private String namaNasabah;

    @Column(length = 16)
    private String nik;

    @Column(length = 255)
    private String alamatDomisili;

    @Column(length = 50)
    private String nomorTelepon;

    @Column(length = 255)
    private String email;

    // --- info rekening ---
    private String jenisRekening;             // Tabungan, Giro, dll
    private String produk;                    // Tabungan Wadiah, Tabungan Haji, dst

    private BigDecimal nominalSetoranAwal;
    private String sumberDanaAwal;
    private String tujuanPembukaan;
    private String jenisKartuAtm;

    private String fasilitasEchannel;         // simpan saja gabungan checkbox (string)
    private String mediaKomunikasi;           // gabungan checkbox juga

    // --- info tambahan ---
    private String cabangPembukaan;
    private String petugasCs;

    private LocalDate tanggalPembukaan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (tanggalPembukaan == null) {
            tanggalPembukaan = LocalDate.now();
        }
    }

    // ====== getter & setter ======

    public Long getId() {
        return id;
    }

    public String getCifNasabah() {
        return cifNasabah;
    }

    public void setCifNasabah(String cifNasabah) {
        this.cifNasabah = cifNasabah;
    }

    public String getNamaNasabah() {
        return namaNasabah;
    }

    public void setNamaNasabah(String namaNasabah) {
        this.namaNasabah = namaNasabah;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getAlamatDomisili() {
        return alamatDomisili;
    }

    public void setAlamatDomisili(String alamatDomisili) {
        this.alamatDomisili = alamatDomisili;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisRekening() {
        return jenisRekening;
    }

    public void setJenisRekening(String jenisRekening) {
        this.jenisRekening = jenisRekening;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public BigDecimal getNominalSetoranAwal() {
        return nominalSetoranAwal;
    }

    public void setNominalSetoranAwal(BigDecimal nominalSetoranAwal) {
        this.nominalSetoranAwal = nominalSetoranAwal;
    }

    public String getSumberDanaAwal() {
        return sumberDanaAwal;
    }

    public void setSumberDanaAwal(String sumberDanaAwal) {
        this.sumberDanaAwal = sumberDanaAwal;
    }

    public String getTujuanPembukaan() {
        return tujuanPembukaan;
    }

    public void setTujuanPembukaan(String tujuanPembukaan) {
        this.tujuanPembukaan = tujuanPembukaan;
    }

    public String getJenisKartuAtm() {
        return jenisKartuAtm;
    }

    public void setJenisKartuAtm(String jenisKartuAtm) {
        this.jenisKartuAtm = jenisKartuAtm;
    }

    public String getFasilitasEchannel() {
        return fasilitasEchannel;
    }

    public void setFasilitasEchannel(String fasilitasEchannel) {
        this.fasilitasEchannel = fasilitasEchannel;
    }

    public String getMediaKomunikasi() {
        return mediaKomunikasi;
    }

    public void setMediaKomunikasi(String mediaKomunikasi) {
        this.mediaKomunikasi = mediaKomunikasi;
    }

    public String getCabangPembukaan() {
        return cabangPembukaan;
    }

    public void setCabangPembukaan(String cabangPembukaan) {
        this.cabangPembukaan = cabangPembukaan;
    }

    public String getPetugasCs() {
        return petugasCs;
    }

    public void setPetugasCs(String petugasCs) {
        this.petugasCs = petugasCs;
    }

    public LocalDate getTanggalPembukaan() {
        return tanggalPembukaan;
    }

    public void setTanggalPembukaan(LocalDate tanggalPembukaan) {
        this.tanggalPembukaan = tanggalPembukaan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

        private boolean statusActive;

    public boolean isStatusActive() {
        return statusActive;
    }

    public void setStatusActive(boolean statusActive) {
        this.statusActive = statusActive;
    }
}
