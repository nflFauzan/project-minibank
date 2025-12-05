package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nasabah")
public class Nasabah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nama_sesuai_identitas", nullable = false)
    private String namaSesuaiIdentitas;

    @Column(name="nomor_identitas", length = 50)
    private String nomorIdentitas;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    public Nasabah() {}

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNamaSesuaiIdentitas() { return namaSesuaiIdentitas; }
    public void setNamaSesuaiIdentitas(String namaSesuaiIdentitas) { this.namaSesuaiIdentitas = namaSesuaiIdentitas; }

    public String getNomorIdentitas() { return nomorIdentitas; }
    public void setNomorIdentitas(String nomorIdentitas) { this.nomorIdentitas = nomorIdentitas; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
