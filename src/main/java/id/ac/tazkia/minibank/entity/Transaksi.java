package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;

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

    @Column(nullable = false)
    private boolean printed = false;

    @Column(name = "printed_at")
    private LocalDateTime printedAt;

    @Column(name = "printed_page")
    private Integer printedPage;

    @Column(name = "printed_line")
    private Integer printedLine;

    @Column(name = "sandi_code", length = 10)
    private String sandiCode;

    public Transaksi() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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

    public String getNomorRekening() { return nomorRekening; }
    public void setNomorRekening(String nomorRekening) { this.nomorRekening = nomorRekening; }

    public String getNamaRekening() { return namaRekening; }
    public void setNamaRekening(String namaRekening) { this.namaRekening = namaRekening; }

    public String getCifNasabah() { return cifNasabah; }
    public void setCifNasabah(String cifNasabah) { this.cifNasabah = cifNasabah; }

    public String getProduk() { return produk; }
    public void setProduk(String produk) { this.produk = produk; }

    public BigDecimal getJumlah() { return jumlah; }
    public void setJumlah(BigDecimal jumlah) { this.jumlah = jumlah; }

    public BigDecimal getSaldoSebelum() { return saldoSebelum; }
    public void setSaldoSebelum(BigDecimal saldoSebelum) { this.saldoSebelum = saldoSebelum; }

    public BigDecimal getSaldoSesudah() { return saldoSesudah; }
    public void setSaldoSesudah(BigDecimal saldoSesudah) { this.saldoSesudah = saldoSesudah; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getNoReferensi() { return noReferensi; }
    public void setNoReferensi(String noReferensi) { this.noReferensi = noReferensi; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }

    public String getProcessedBy() { return processedBy; }
    public void setProcessedBy(String processedBy) { this.processedBy = processedBy; }

    public String getProcessedByUsername() { return processedByUsername; }
    public void setProcessedByUsername(String processedByUsername) { this.processedByUsername = processedByUsername; }

    public String getProcessedByFullName() { return processedByFullName; }
    public void setProcessedByFullName(String processedByFullName) { this.processedByFullName = processedByFullName; }

    public boolean isPrinted() { return printed; }
    public void setPrinted(boolean printed) { this.printed = printed; }

    public LocalDateTime getPrintedAt() { return printedAt; }
    public void setPrintedAt(LocalDateTime printedAt) { this.printedAt = printedAt; }

    public Integer getPrintedPage() { return printedPage; }
    public void setPrintedPage(Integer printedPage) { this.printedPage = printedPage; }

    public Integer getPrintedLine() { return printedLine; }
    public void setPrintedLine(Integer printedLine) { this.printedLine = printedLine; }

    public String getSandiCode() { return sandiCode; }
    public void setSandiCode(String sandiCode) { this.sandiCode = sandiCode; }
}
