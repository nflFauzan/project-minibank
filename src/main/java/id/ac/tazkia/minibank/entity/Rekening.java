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

    @Column(name = "nomor_rekening", nullable = false, unique = true, length = 255)
    private String nomorRekening;

    @Column(name = "status_active")
    private boolean statusActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;

    @Column(name = "cif_nasabah", length = 8)
    private String cifNasabah;

    @Column(name = "nik", length = 16)
    private String nik;

    @Column(name = "nama_nasabah", length = 200)
    private String namaNasabah;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "nomor_telepon", length = 50)
    private String nomorTelepon;

    @Column(name = "alamat_domisili", length = 255)
    private String alamatDomisili;

    @Column(name = "cabang_pembukaan", length = 255)
    private String cabangPembukaan;

    @Column(name = "petugas_cs", length = 255)
    private String petugasCs;

    @Column(name = "produk", length = 255)
    private String produk;

    @Column(name = "nominal_setoran_awal", precision = 38, scale = 2)
    private BigDecimal nominalSetoranAwal;

    @Column(name = "tanggal_pembukaan")
    private LocalDate tanggalPembukaan;

    @Column(name = "tujuan_pembukaan", length = 255)
    private String tujuanPembukaan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }

    public String getNomorRekening() { return nomorRekening; }
    public void setNomorRekening(String nomorRekening) { this.nomorRekening = nomorRekening; }

    public boolean isStatusActive() { return statusActive; }
    public void setStatusActive(boolean statusActive) { this.statusActive = statusActive; }

    public Nasabah getNasabah() { return nasabah; }
    public void setNasabah(Nasabah nasabah) { this.nasabah = nasabah; }

    public String getCifNasabah() { return cifNasabah; }
    public void setCifNasabah(String cifNasabah) { this.cifNasabah = cifNasabah; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getNamaNasabah() { return namaNasabah; }
    public void setNamaNasabah(String namaNasabah) { this.namaNasabah = namaNasabah; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }

    public String getAlamatDomisili() { return alamatDomisili; }
    public void setAlamatDomisili(String alamatDomisili) { this.alamatDomisili = alamatDomisili; }

    public String getCabangPembukaan() { return cabangPembukaan; }
    public void setCabangPembukaan(String cabangPembukaan) { this.cabangPembukaan = cabangPembukaan; }

    public String getPetugasCs() { return petugasCs; }
    public void setPetugasCs(String petugasCs) { this.petugasCs = petugasCs; }

    public String getProduk() { return produk; }
    public void setProduk(String produk) { this.produk = produk; }

    public BigDecimal getNominalSetoranAwal() { return nominalSetoranAwal; }
    public void setNominalSetoranAwal(BigDecimal nominalSetoranAwal) { this.nominalSetoranAwal = nominalSetoranAwal; }

    public LocalDate getTanggalPembukaan() { return tanggalPembukaan; }
    public void setTanggalPembukaan(LocalDate tanggalPembukaan) { this.tanggalPembukaan = tanggalPembukaan; }

    public String getTujuanPembukaan() { return tujuanPembukaan; }
    public void setTujuanPembukaan(String tujuanPembukaan) { this.tujuanPembukaan = tujuanPembukaan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
