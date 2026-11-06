package id.ac.tazkia.minibank.dto.passbook;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class PassbookStatusResponse {
    private boolean hasPassbook;
    private String passbookNumber;
    private Integer currentPage;
    private Integer lastPrintedLine;
    private Integer remainingLines;
    private long unprintedTransactionCount;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime lastPrintDate;

    public PassbookStatusResponse() {}

    public PassbookStatusResponse(boolean hasPassbook, String passbookNumber, Integer currentPage,
                                  Integer lastPrintedLine, Integer remainingLines, long unprintedTransactionCount,
                                  String status, LocalDateTime lastPrintDate) {
        this.hasPassbook = hasPassbook;
        this.passbookNumber = passbookNumber;
        this.currentPage = currentPage;
        this.lastPrintedLine = lastPrintedLine;
        this.remainingLines = remainingLines;
        this.unprintedTransactionCount = unprintedTransactionCount;
        this.status = status;
        this.lastPrintDate = lastPrintDate;
    }

    public boolean isHasPassbook() { return hasPassbook; }
    public void setHasPassbook(boolean hasPassbook) { this.hasPassbook = hasPassbook; }

    public String getPassbookNumber() { return passbookNumber; }
    public void setPassbookNumber(String passbookNumber) { this.passbookNumber = passbookNumber; }

    public Integer getCurrentPage() { return currentPage; }
    public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }

    public Integer getLastPrintedLine() { return lastPrintedLine; }
    public void setLastPrintedLine(Integer lastPrintedLine) { this.lastPrintedLine = lastPrintedLine; }

    public Integer getRemainingLines() { return remainingLines; }
    public void setRemainingLines(Integer remainingLines) { this.remainingLines = remainingLines; }

    public long getUnprintedTransactionCount() { return unprintedTransactionCount; }
    public void setUnprintedTransactionCount(long unprintedTransactionCount) { this.unprintedTransactionCount = unprintedTransactionCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastPrintDate() { return lastPrintDate; }
    public void setLastPrintDate(LocalDateTime lastPrintDate) { this.lastPrintDate = lastPrintDate; }
}
