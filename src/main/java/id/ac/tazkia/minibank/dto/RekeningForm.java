package id.ac.tazkia.minibank.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RekeningForm {

    // ==== Data nasabah (diisi lewat CIF) ====
    private String cif;
    private String namaLengkapNasabah;
    private String nik;
    private String alamatDomisili;
    private String noTelepon;
    private String email;

    // ==== Informasi rekening baru ====
    private String jenisRekening;
    private String produkKode; // dropdown (WADIAH/MUDHARABAH/HAJI dll)
    private BigDecimal nominalSetoranAwal;
    private String sumberDanaAwal;
    private String tujuanPembukaanRekening;
    private String jenisKartuAtm;

    // e-channel
    private boolean echannelAtm;
    private boolean echannelNetBanking;
    private boolean echannelMobileBanking;

    // media komunikasi
    private boolean mediaSmsEmail;
    private boolean mediaWhatsapp;
    private boolean mediaTelepon;

    // ==== Informasi tambahan ====
    private String cabangPembukaanRekening;
    private String petugasCs;
    private LocalDate tanggalPembukaan;

    // persetujuan
    private boolean setujuVerifikasiData;
    private String persetujuanDataPribadi;
    private boolean setujuPernyataanNasabah;
    private boolean setujuSyaratUmum;

    // ===== getter & setter (baru) =====
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }

    public String getNamaLengkapNasabah() { return namaLengkapNasabah; }
    public void setNamaLengkapNasabah(String namaLengkapNasabah) { this.namaLengkapNasabah = namaLengkapNasabah; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getAlamatDomisili() { return alamatDomisili; }
    public void setAlamatDomisili(String alamatDomisili) { this.alamatDomisili = alamatDomisili; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJenisRekening() { return jenisRekening; }
    public void setJenisRekening(String jenisRekening) { this.jenisRekening = jenisRekening; }

    public String getProdukKode() { return produkKode; }
    public void setProdukKode(String produkKode) { this.produkKode = produkKode; }

    public BigDecimal getNominalSetoranAwal() { return nominalSetoranAwal; }
    public void setNominalSetoranAwal(BigDecimal nominalSetoranAwal) { this.nominalSetoranAwal = nominalSetoranAwal; }

    public String getSumberDanaAwal() { return sumberDanaAwal; }
    public void setSumberDanaAwal(String sumberDanaAwal) { this.sumberDanaAwal = sumberDanaAwal; }

    public String getTujuanPembukaanRekening() { return tujuanPembukaanRekening; }
    public void setTujuanPembukaanRekening(String tujuanPembukaanRekening) { this.tujuanPembukaanRekening = tujuanPembukaanRekening; }

    public String getJenisKartuAtm() { return jenisKartuAtm; }
    public void setJenisKartuAtm(String jenisKartuAtm) { this.jenisKartuAtm = jenisKartuAtm; }

    public boolean isEchannelAtm() { return echannelAtm; }
    public void setEchannelAtm(boolean echannelAtm) { this.echannelAtm = echannelAtm; }

    public boolean isEchannelNetBanking() { return echannelNetBanking; }
    public void setEchannelNetBanking(boolean echannelNetBanking) { this.echannelNetBanking = echannelNetBanking; }

    public boolean isEchannelMobileBanking() { return echannelMobileBanking; }
    public void setEchannelMobileBanking(boolean echannelMobileBanking) { this.echannelMobileBanking = echannelMobileBanking; }

    public boolean isMediaSmsEmail() { return mediaSmsEmail; }
    public void setMediaSmsEmail(boolean mediaSmsEmail) { this.mediaSmsEmail = mediaSmsEmail; }

    public boolean isMediaWhatsapp() { return mediaWhatsapp; }
    public void setMediaWhatsapp(boolean mediaWhatsapp) { this.mediaWhatsapp = mediaWhatsapp; }

    public boolean isMediaTelepon() { return mediaTelepon; }
    public void setMediaTelepon(boolean mediaTelepon) { this.mediaTelepon = mediaTelepon; }

    public String getCabangPembukaanRekening() { return cabangPembukaanRekening; }
    public void setCabangPembukaanRekening(String cabangPembukaanRekening) { this.cabangPembukaanRekening = cabangPembukaanRekening; }

    public String getPetugasCs() { return petugasCs; }
    public void setPetugasCs(String petugasCs) { this.petugasCs = petugasCs; }

    public LocalDate getTanggalPembukaan() { return tanggalPembukaan; }
    public void setTanggalPembukaan(LocalDate tanggalPembukaan) { this.tanggalPembukaan = tanggalPembukaan; }

    public boolean isSetujuVerifikasiData() { return setujuVerifikasiData; }
    public void setSetujuVerifikasiData(boolean setujuVerifikasiData) { this.setujuVerifikasiData = setujuVerifikasiData; }

    public String getPersetujuanDataPribadi() { return persetujuanDataPribadi; }
    public void setPersetujuanDataPribadi(String persetujuanDataPribadi) { this.persetujuanDataPribadi = persetujuanDataPribadi; }

    public boolean isSetujuPernyataanNasabah() { return setujuPernyataanNasabah; }
    public void setSetujuPernyataanNasabah(boolean setujuPernyataanNasabah) { this.setujuPernyataanNasabah = setujuPernyataanNasabah; }

    public boolean isSetujuSyaratUmum() { return setujuSyaratUmum; }
    public void setSetujuSyaratUmum(boolean setujuSyaratUmum) { this.setujuSyaratUmum = setujuSyaratUmum; }


    // ==========================================================
    // ✅ ALIAS GETTER (kompatibilitas) biar PembukaanRekeningService
    //    yang lama tetap compile TANPA kamu ubah servicenya
    // ==========================================================

    // dulu: getCifNasabah()
    public String getCifNasabah() {
        return this.cif;
    }

    // dulu: getNamaNasabah()
    public String getNamaNasabah() {
        return this.namaLengkapNasabah;
    }

    // dulu: getNomorTelepon()
    public String getNomorTelepon() {
        return this.noTelepon;
    }

    // dulu: getProduk()
    public String getProduk() {
        return this.produkKode;
    }

    // dulu: getTujuanPembukaan()
    public String getTujuanPembukaan() {
        return this.tujuanPembukaanRekening;
    }

    // dulu: getCabangPembukaan()
    public String getCabangPembukaan() {
        return this.cabangPembukaanRekening;
    }

    // dulu: getFasilitasEchannel() -> DI SERVICE kamu ternyata dipakai sebagai String
public String getFasilitasEchannel() {
    StringBuilder sb = new StringBuilder();
    if (echannelAtm) sb.append("ATM, ");
    if (echannelNetBanking) sb.append("Net Banking, ");
    if (echannelMobileBanking) sb.append("Mobile Banking, ");
    if (sb.length() >= 2) sb.setLength(sb.length() - 2); // hapus ", " terakhir
    return sb.toString();
}

// dulu: getMediaKomunikasi() -> DI SERVICE kamu ternyata dipakai sebagai String
public String getMediaKomunikasi() {
    StringBuilder sb = new StringBuilder();
    if (mediaSmsEmail) sb.append("SMS/Email, ");
    if (mediaWhatsapp) sb.append("WhatsApp, ");
    if (mediaTelepon) sb.append("Telepon, ");
    if (sb.length() >= 2) sb.setLength(sb.length() - 2);
    return sb.toString();
}

}
