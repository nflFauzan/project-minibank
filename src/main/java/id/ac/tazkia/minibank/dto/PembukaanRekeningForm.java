package id.ac.tazkia.minibank.dto;

import java.time.LocalDate;
import java.util.List;

public class PembukaanRekeningForm {

    // ===== Informasi Data Nasabah =====
    private String cif;
    private String namaLengkapNasabah;
    private String nik;
    private String alamatDomisili;
    private String nomorTelepon;
    private String email;

    // ===== Informasi Rekening Baru =====
    private String jenisRekening;          // Tabungan / Giro / dll (sementara bebas teks)
    private Long produkId;                 // relasi ke ProdukTabungan
    private String nominalSetoranAwal;
    private String sumberDanaAwal;
    private String tujuanPembukaan;
    private String jenisKartuAtm;

    private List<String> fasilitasEchannel;    // ATM, Net Banking, Mobile, dll
    private List<String> mediaKomunikasi;      // SMS/Email, WhatsApp, Telepon

    // ===== Informasi Tambahan =====
    private String cabangPembukaan;
    private String petugasCs;
    private LocalDate tanggalPembukaan;

    // ===== Persetujuan / Pernyataan =====
    private Boolean sudahVerifikasiData;
    private Boolean setujuKebijakanDataPribadi;
    private Boolean setujuPernyataanNasabah;
    private Boolean setujuSyaratUmum;

    // ===== GETTER SETTER (iya, panjang, tapi kamu butuh ini) =====

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNamaLengkapNasabah() {
        return namaLengkapNasabah;
    }

    public void setNamaLengkapNasabah(String namaLengkapNasabah) {
        this.namaLengkapNasabah = namaLengkapNasabah;
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

    public Long getProdukId() {
        return produkId;
    }

    public void setProdukId(Long produkId) {
        this.produkId = produkId;
    }

    public String getNominalSetoranAwal() {
        return nominalSetoranAwal;
    }

    public void setNominalSetoranAwal(String nominalSetoranAwal) {
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

    public List<String> getFasilitasEchannel() {
        return fasilitasEchannel;
    }

    public void setFasilitasEchannel(List<String> fasilitasEchannel) {
        this.fasilitasEchannel = fasilitasEchannel;
    }

    public List<String> getMediaKomunikasi() {
        return mediaKomunikasi;
    }

    public void setMediaKomunikasi(List<String> mediaKomunikasi) {
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

    public Boolean getSudahVerifikasiData() {
        return sudahVerifikasiData;
    }

    public void setSudahVerifikasiData(Boolean sudahVerifikasiData) {
        this.sudahVerifikasiData = sudahVerifikasiData;
    }

    public Boolean getSetujuKebijakanDataPribadi() {
        return setujuKebijakanDataPribadi;
    }

    public void setSetujuKebijakanDataPribadi(Boolean setujuKebijakanDataPribadi) {
        this.setujuKebijakanDataPribadi = setujuKebijakanDataPribadi;
    }

    public Boolean getSetujuPernyataanNasabah() {
        return setujuPernyataanNasabah;
    }

    public void setSetujuPernyataanNasabah(Boolean setujuPernyataanNasabah) {
        this.setujuPernyataanNasabah = setujuPernyataanNasabah;
    }

    public Boolean getSetujuSyaratUmum() {
        return setujuSyaratUmum;
    }

    public void setSetujuSyaratUmum(Boolean setujuSyaratUmum) {
        this.setujuSyaratUmum = setujuSyaratUmum;
    }
}
