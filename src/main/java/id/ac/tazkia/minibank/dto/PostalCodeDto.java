package id.ac.tazkia.minibank.dto;

public class PostalCodeDto {
    private String kodePos;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String kelurahan;

    public PostalCodeDto() {}

    public PostalCodeDto(String kodePos, String provinsi, String kota, String kecamatan, String kelurahan) {
        this.kodePos = kodePos;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
    }

    // getters & setters
    public String getKodePos() { return kodePos; }
    public void setKodePos(String kodePos) { this.kodePos = kodePos; }
    public String getProvinsi() { return provinsi; }
    public void setProvinsi(String provinsi) { this.provinsi = provinsi; }
    public String getKota() { return kota; }
    public void setKota(String kota) { this.kota = kota; }
    public String getKecamatan() { return kecamatan; }
    public void setKecamatan(String kecamatan) { this.kecamatan = kecamatan; }
    public String getKelurahan() { return kelurahan; }
    public void setKelurahan(String kelurahan) { this.kelurahan = kelurahan; }
}
