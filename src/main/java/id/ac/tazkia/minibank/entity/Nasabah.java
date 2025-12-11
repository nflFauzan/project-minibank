package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "nasabah")
public class Nasabah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String cif;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ====== DATA NASABAH (IDENTITAS) ======
    @Column(name = "nomor_identitas", nullable = false, length = 16)
    private String nik;
    
    // mapping ke kolom lama di DB: nama_sesuai_identitas
    @Column(name = "nama_sesuai_identitas", nullable = false, length = 200)
    private String namaLengkap;

    private String tempatLahir;

    private LocalDate tanggalLahir;

    private String namaIbuKandung;

    private String jenisKelamin;        // "L" / "P"

    private String penduduk;            // WNI / WNA

    private String statusPernikahan;

    private String agama;

    private String negara;

    // ====== DATA KONTAK ======
    private String noHp;

    private String email;

    // ====== ALAMAT IDENTITAS ======
    private String kodePosIdentitas;
    private String provinsiIdentitas;
    private String kotaIdentitas;
    private String kecamatanIdentitas;
    private String kelurahanIdentitas;
    private String rtIdentitas;
    private String rwIdentitas;

    // ====== ALAMAT DOMISILI ======
    private String kodePosDomisili;
    private String provinsiDomisili;
    private String kotaDomisili;
    private String kecamatanDomisili;
    private String kelurahanDomisili;
    private String rtDomisili;
    private String rwDomisili;

    // ====== DATA PEKERJAAN SEDERHANA ======
    private String pekerjaan;
    private String namaPerusahaan;
    private String jabatan;
    private String penghasilanPerBulan;

    // ====== LIFECYCLE ======
    @PrePersist
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // ====== GETTER / SETTER ======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    // Kompatibilitas dengan kode lama yang memakai namaSesuaiIdentitas
    public String getNamaSesuaiIdentitas() {
        return this.namaLengkap;
    }

    public void setNamaSesuaiIdentitas(String namaSesuaiIdentitas) {
        this.namaLengkap = namaSesuaiIdentitas;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNamaIbuKandung() {
        return namaIbuKandung;
    }

    public void setNamaIbuKandung(String namaIbuKandung) {
        this.namaIbuKandung = namaIbuKandung;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getPenduduk() {
        return penduduk;
    }

    public void setPenduduk(String penduduk) {
        this.penduduk = penduduk;
    }

    public String getStatusPernikahan() {
        return statusPernikahan;
    }

    public void setStatusPernikahan(String statusPernikahan) {
        this.statusPernikahan = statusPernikahan;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKodePosIdentitas() {
        return kodePosIdentitas;
    }

    public void setKodePosIdentitas(String kodePosIdentitas) {
        this.kodePosIdentitas = kodePosIdentitas;
    }

    public String getProvinsiIdentitas() {
        return provinsiIdentitas;
    }

    public void setProvinsiIdentitas(String provinsiIdentitas) {
        this.provinsiIdentitas = provinsiIdentitas;
    }

    public String getKotaIdentitas() {
        return kotaIdentitas;
    }

    public void setKotaIdentitas(String kotaIdentitas) {
        this.kotaIdentitas = kotaIdentitas;
    }

    public String getKecamatanIdentitas() {
        return kecamatanIdentitas;
    }

    public void setKecamatanIdentitas(String kecamatanIdentitas) {
        this.kecamatanIdentitas = kecamatanIdentitas;
    }

    public String getKelurahanIdentitas() {
        return kelurahanIdentitas;
    }

    public void setKelurahanIdentitas(String kelurahanIdentitas) {
        this.kelurahanIdentitas = kelurahanIdentitas;
    }

    public String getRtIdentitas() {
        return rtIdentitas;
    }

    public void setRtIdentitas(String rtIdentitas) {
        this.rtIdentitas = rtIdentitas;
    }

    public String getRwIdentitas() {
        return rwIdentitas;
    }

    public void setRwIdentitas(String rwIdentitas) {
        this.rwIdentitas = rwIdentitas;
    }

    public String getKodePosDomisili() {
        return kodePosDomisili;
    }

    public void setKodePosDomisili(String kodePosDomisili) {
        this.kodePosDomisili = kodePosDomisili;
    }

    public String getProvinsiDomisili() {
        return provinsiDomisili;
    }

    public void setProvinsiDomisili(String provinsiDomisili) {
        this.provinsiDomisili = provinsiDomisili;
    }

    public String getKotaDomisili() {
        return kotaDomisili;
    }

    public void setKotaDomisili(String kotaDomisili) {
        this.kotaDomisili = kotaDomisili;
    }

    public String getKecamatanDomisili() {
        return kecamatanDomisili;
    }

    public void setKecamatanDomisili(String kecamatanDomisili) {
        this.kecamatanDomisili = kecamatanDomisili;
    }

    public String getKelurahanDomisili() {
        return kelurahanDomisili;
    }

    public void setKelurahanDomisili(String kelurahanDomisili) {
        this.kelurahanDomisili = kelurahanDomisili;
    }

    public String getRtDomisili() {
        return rtDomisili;
    }

    public void setRtDomisili(String rtDomisili) {
        this.rtDomisili = rtDomisili;
    }

    public String getRwDomisili() {
        return rwDomisili;
    }

    public void setRwDomisili(String rwDomisili) {
        this.rwDomisili = rwDomisili;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPenghasilanPerBulan() {
        return penghasilanPerBulan;
    }

    public void setPenghasilanPerBulan(String penghasilanPerBulan) {
        this.penghasilanPerBulan = penghasilanPerBulan;
    }
}
