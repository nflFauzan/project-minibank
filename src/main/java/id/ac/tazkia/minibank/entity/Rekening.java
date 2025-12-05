package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rekening")
public class Rekening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomor_rekening", unique = true)
    private String nomorRekening;

    @ManyToOne
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;

    @Column(name = "status_active")
    private boolean statusActive = true;

    // other fields can be added later (produk, setoranAwal, dll)

    public Rekening() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomorRekening() { return nomorRekening; }
    public void setNomorRekening(String nomorRekening) { this.nomorRekening = nomorRekening; }

    public Nasabah getNasabah() { return nasabah; }
    public void setNasabah(Nasabah nasabah) { this.nasabah = nasabah; }

    public boolean isStatusActive() { return statusActive; }
    public void setStatusActive(boolean statusActive) { this.statusActive = statusActive; }
}
