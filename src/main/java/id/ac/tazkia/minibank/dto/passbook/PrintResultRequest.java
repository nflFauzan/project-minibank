package id.ac.tazkia.minibank.dto.passbook;

import java.util.List;

public class PrintResultRequest {
    private String accountId;
    private String status;
    private List<String> printedTransactionIds;
    private String printerName;
    private String printerPort;
    private String errorMessage;

    public PrintResultRequest() {}

    public PrintResultRequest(String accountId, String status, List<String> printedTransactionIds, String printerName, String printerPort, String errorMessage) {
        this.accountId = accountId;
        this.status = status;
        this.printedTransactionIds = printedTransactionIds;
        this.printerName = printerName;
        this.printerPort = printerPort;
        this.errorMessage = errorMessage;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getPrintedTransactionIds() { return printedTransactionIds; }
    public void setPrintedTransactionIds(List<String> printedTransactionIds) { this.printedTransactionIds = printedTransactionIds; }

    public String getPrinterName() { return printerName; }
    public void setPrinterName(String printerName) { this.printerName = printerName; }

    public String getPrinterPort() { return printerPort; }
    public void setPrinterPort(String printerPort) { this.printerPort = printerPort; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
