package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaksi")
public class Transaksi {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "group_id", nullable = false, columnDefinition = "uuid")
    private UUID groupId;

    @Column(name = "nomor_transaksi", nullable = false, length = 32)
    private String nomorTransaksi;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipe", nullable = false, length = 20)
    private TipeTransaksi tipe;

    @Column(name = "channel", nullable = false, length = 20)
    private String channel = "TELLER";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rekening_id", nullable = false)
    private Rekening rekening;

    @Column(name = "jumlah", nullable = false, precision = 38, scale = 2)
    private BigDecimal jumlah;

    @Column(name = "saldo_sebelum", nullable = false, precision = 38, scale = 2)
    private BigDecimal saldoSebelum;

    @Column(name = "saldo_sesudah", nullable = false, precision = 38, scale = 2)
    private BigDecimal saldoSesudah;

    @Column(name = "keterangan", length = 255)
    private String keterangan;

    @Column(name = "processed_by", nullable = false, length = 200)
    private String processedBy;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    @PrePersist
    void prePersist() {
        if (processedAt == null) processedAt = LocalDateTime.now();
        if (groupId == null) groupId = UUID.randomUUID();
        if (channel == null) channel = "TELLER";
        if (jumlah == null) jumlah = BigDecimal.ZERO;
        if (saldoSebelum == null) saldoSebelum = BigDecimal.ZERO;
        if (saldoSesudah == null) saldoSesudah = BigDecimal.ZERO;
    }

    // getters setters
    public UUID getId() { return id; }
    public UUID getGroupId() { return groupId; }
    public void setGroupId(UUID groupId) { this.groupId = groupId; }

    public String getNomorTransaksi() { return nomorTransaksi; }
    public void setNomorTransaksi(String nomorTransaksi) { this.nomorTransaksi = nomorTransaksi; }

    public TipeTransaksi getTipe() { return tipe; }
    public void setTipe(TipeTransaksi tipe) { this.tipe = tipe; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public Rekening getRekening() { return rekening; }
    public void setRekening(Rekening rekening) { this.rekening = rekening; }

    public BigDecimal getJumlah() { return jumlah; }
    public void setJumlah(BigDecimal jumlah) { this.jumlah = jumlah; }

    public BigDecimal getSaldoSebelum() { return saldoSebelum; }
    public void setSaldoSebelum(BigDecimal saldoSebelum) { this.saldoSebelum = saldoSebelum; }

    public BigDecimal getSaldoSesudah() { return saldoSesudah; }
    public void setSaldoSesudah(BigDecimal saldoSesudah) { this.saldoSesudah = saldoSesudah; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getProcessedBy() { return processedBy; }
    public void setProcessedBy(String processedBy) { this.processedBy = processedBy; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
