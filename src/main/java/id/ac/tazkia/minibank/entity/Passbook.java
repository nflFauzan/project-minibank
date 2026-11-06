package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "passbook",
    indexes = {
        @Index(name = "idx_passbook_rekening_id", columnList = "rekening_id"),
        @Index(name = "idx_passbook_nomor_rekening", columnList = "nomor_rekening"),
        @Index(name = "idx_passbook_number", columnList = "passbook_number")
    })
public class Passbook {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rekening_id", nullable = false)
    private Rekening rekening;

    @Column(name = "nomor_rekening", nullable = false, length = 50)
    private String nomorRekening;

    @Column(name = "passbook_number", nullable = false, unique = true, length = 50)
    private String passbookNumber;

    @Column(name = "current_page", nullable = false)
    private Integer currentPage = 1;

    @Column(name = "last_printed_line", nullable = false)
    private Integer lastPrintedLine = 0;

    @Column(name = "lines_per_page", nullable = false)
    private Integer linesPerPage = 30;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "last_print_date")
    private LocalDateTime lastPrintDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Passbook() {}

    public Passbook(UUID id, Rekening rekening, String nomorRekening, String passbookNumber,
                    Integer currentPage, Integer lastPrintedLine, Integer linesPerPage,
                    String status, LocalDateTime lastPrintDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rekening = rekening;
        this.nomorRekening = nomorRekening;
        this.passbookNumber = passbookNumber;
        this.currentPage = currentPage != null ? currentPage : 1;
        this.lastPrintedLine = lastPrintedLine != null ? lastPrintedLine : 0;
        this.linesPerPage = linesPerPage != null ? linesPerPage : 30;
        this.status = status != null ? status : "ACTIVE";
        this.lastPrintDate = lastPrintDate;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void onPrePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (currentPage == null) {
            currentPage = 1;
        }
        if (lastPrintedLine == null) {
            lastPrintedLine = 0;
        }
        if (linesPerPage == null) {
            linesPerPage = 30;
        }
        if (status == null) {
            status = "ACTIVE";
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getRemainingLines() {
        int lines = linesPerPage != null ? linesPerPage : 30;
        int lastLine = lastPrintedLine != null ? lastPrintedLine : 0;
        return Math.max(0, lines - lastLine);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Rekening getRekening() { return rekening; }
    public void setRekening(Rekening rekening) { this.rekening = rekening; }

    public String getNomorRekening() { return nomorRekening; }
    public void setNomorRekening(String nomorRekening) { this.nomorRekening = nomorRekening; }

    public String getPassbookNumber() { return passbookNumber; }
    public void setPassbookNumber(String passbookNumber) { this.passbookNumber = passbookNumber; }

    public Integer getCurrentPage() { return currentPage != null ? currentPage : 1; }
    public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }

    public Integer getLastPrintedLine() { return lastPrintedLine != null ? lastPrintedLine : 0; }
    public void setLastPrintedLine(Integer lastPrintedLine) { this.lastPrintedLine = lastPrintedLine; }

    public Integer getLinesPerPage() { return linesPerPage != null ? linesPerPage : 30; }
    public void setLinesPerPage(Integer linesPerPage) { this.linesPerPage = linesPerPage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastPrintDate() { return lastPrintDate; }
    public void setLastPrintDate(LocalDateTime lastPrintDate) { this.lastPrintDate = lastPrintDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
