package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "postal_code")
public class PostalCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_pos", nullable = false, length = 10)
    private String kodePos;

    @Column(nullable = false, length = 200)
    private String provinsi;

    @Column(name = "kota", nullable = false, length = 200)
    private String kota;

    @Column(nullable = false, length = 200)
    private String kecamatan;

    @Column(nullable = false, length = 200)
    private String kelurahan;

    public Long getId() { return id; }

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
