package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaksi",
    indexes = {
        @Index(name = "idx_transaksi_group_id", columnList = "group_id"),
        @Index(name = "idx_transaksi_nomor", columnList = "nomor_transaksi"),
        @Index(name = "idx_transaksi_rekening", columnList = "nomor_rekening")
    })
@Getter @Setter
public class Transaksi {

    @Id
    private UUID id;

    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @Column(name = "nomor_transaksi", nullable = false, unique = true)
    private String nomorTransaksi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipeTransaksi tipe;

    @Column(nullable = false)
    private String channel; // TELLER

    // ✅ INI YANG DB WAJIB: transaksi.rekening_id (FK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rekening_id", nullable = false)
    private Rekening rekening;

    @Column(name = "nomor_rekening", nullable = false)
    private String nomorRekening;

    @Column(name = "nama_rekening", nullable = false)
    private String namaRekening;

    @Column(name = "cif_nasabah")
    private String cifNasabah;

    private String produk;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal jumlah;

    @Column(name = "saldo_sebelum", nullable = false, precision = 38, scale = 2)
    private BigDecimal saldoSebelum;

    @Column(name = "saldo_sesudah", nullable = false, precision = 38, scale = 2)
    private BigDecimal saldoSesudah;

    @Column(nullable = false, length = 500)
    private String keterangan;

    @Column(name = "no_referensi", length = 100)
    private String noReferensi;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    // ✅ DB WAJIB: processed_by (varchar 200 not null)
    @Column(name = "processed_by", nullable = false, length = 200)
    private String processedBy;

    @Column(name = "processed_by_username", nullable = false)
    private String processedByUsername;

    @Column(name = "processed_by_full_name", nullable = false)
    private String processedByFullName;
}