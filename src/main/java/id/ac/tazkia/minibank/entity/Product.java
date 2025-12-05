package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(length = 1000)
    private String deskripsi;

    @Column(name = "status_active")
    private boolean statusActive = true;

    public Product() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public boolean isStatusActive() { return statusActive; }
    public void setStatusActive(boolean statusActive) { this.statusActive = statusActive; }
}
