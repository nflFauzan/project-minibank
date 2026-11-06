package id.ac.tazkia.minibank.dto.passbook;

public class PassbookInitializeForm {
    private Integer currentPage = 1;
    private Integer lastPrintedLine = 0;
    private String lastPrintedTransactionId;

    public PassbookInitializeForm() {}

    public PassbookInitializeForm(Integer currentPage, Integer lastPrintedLine, String lastPrintedTransactionId) {
        this.currentPage = currentPage != null ? currentPage : 1;
        this.lastPrintedLine = lastPrintedLine != null ? lastPrintedLine : 0;
        this.lastPrintedTransactionId = lastPrintedTransactionId;
    }

    public Integer getCurrentPage() { return currentPage != null ? currentPage : 1; }
    public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }

    public Integer getLastPrintedLine() { return lastPrintedLine != null ? lastPrintedLine : 0; }
    public void setLastPrintedLine(Integer lastPrintedLine) { this.lastPrintedLine = lastPrintedLine; }

    public String getLastPrintedTransactionId() { return lastPrintedTransactionId; }
    public void setLastPrintedTransactionId(String lastPrintedTransactionId) { this.lastPrintedTransactionId = lastPrintedTransactionId; }
}
