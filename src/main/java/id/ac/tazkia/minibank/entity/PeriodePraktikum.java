package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "periode_praktikum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodePraktikum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String kode;

    @Column(nullable = false, length = 100)
    private String nama;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PeriodeStatus status = PeriodeStatus.ACTIVE;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = false;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "tanggal_mulai")
    private LocalDate tanggalMulai;

    @Column(name = "tanggal_selesai")
    private LocalDate tanggalSelesai;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = PeriodeStatus.ACTIVE;
        if (isActive == null) isActive = false;
        if (isDefault == null) isDefault = false;
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Convenience aliases for UI templates & controllers
    public String getKodeTahun() {
        return kode;
    }

    public void setKodeTahun(String kodeTahun) {
        this.kode = kodeTahun;
    }

    public String getNamaPeriode() {
        return nama;
    }

    public void setNamaPeriode(String namaPeriode) {
        this.nama = namaPeriode;
    }
}
