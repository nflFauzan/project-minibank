package id.ac.tazkia.minibank.dto.passbook;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PassbookPrintDataResponse {

    private AccountDto account;
    private CustomerDto customer;
    private PassbookDto passbook;
    private ConfigDto config = new ConfigDto();
    private List<TransactionDto> transactions = new ArrayList<>();

    public PassbookPrintDataResponse() {}

    public PassbookPrintDataResponse(AccountDto account, CustomerDto customer, PassbookDto passbook, ConfigDto config, List<TransactionDto> transactions) {
        this.account = account;
        this.customer = customer;
        this.passbook = passbook;
        this.config = config != null ? config : new ConfigDto();
        this.transactions = transactions != null ? transactions : new ArrayList<>();
    }

    public AccountDto getAccount() { return account; }
    public void setAccount(AccountDto account) { this.account = account; }

    public CustomerDto getCustomer() { return customer; }
    public void setCustomer(CustomerDto customer) { this.customer = customer; }

    public PassbookDto getPassbook() { return passbook; }
    public void setPassbook(PassbookDto passbook) { this.passbook = passbook; }

    public ConfigDto getConfig() { return config; }
    public void setConfig(ConfigDto config) { this.config = config; }

    public List<TransactionDto> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionDto> transactions) { this.transactions = transactions; }

    public static class AccountDto {
        private String id;
        private String accountNumber;
        private String accountName;
        private String productName;
        private BigDecimal currentBalance;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate openedDate;
        private String status;

        public AccountDto() {}
        public AccountDto(String id, String accountNumber, String accountName, String productName, BigDecimal currentBalance, LocalDate openedDate, String status) {
            this.id = id;
            this.accountNumber = accountNumber;
            this.accountName = accountName;
            this.productName = productName;
            this.currentBalance = currentBalance;
            this.openedDate = openedDate;
            this.status = status;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public String getAccountName() { return accountName; }
        public void setAccountName(String accountName) { this.accountName = accountName; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public BigDecimal getCurrentBalance() { return currentBalance; }
        public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
        public LocalDate getOpenedDate() { return openedDate; }
        public void setOpenedDate(LocalDate openedDate) { this.openedDate = openedDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class CustomerDto {
        private String id;
        private String customerNumber;
        private String customerName;
        private String customerType;

        public CustomerDto() {}
        public CustomerDto(String id, String customerNumber, String customerName, String customerType) {
            this.id = id;
            this.customerNumber = customerNumber;
            this.customerName = customerName;
            this.customerType = customerType;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getCustomerNumber() { return customerNumber; }
        public void setCustomerNumber(String customerNumber) { this.customerNumber = customerNumber; }
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        public String getCustomerType() { return customerType; }
        public void setCustomerType(String customerType) { this.customerType = customerType; }
    }

    public static class PassbookDto {
        private String id;
        private String passbookNumber;
        private Integer currentPage;
        private Integer lastPrintedLine;
        private Integer linesPerPage;
        private Integer remainingLines;
        private String status;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        private LocalDateTime lastPrintDate;

        public PassbookDto() {}
        public PassbookDto(String id, String passbookNumber, Integer currentPage, Integer lastPrintedLine, Integer linesPerPage, Integer remainingLines, String status, LocalDateTime lastPrintDate) {
            this.id = id;
            this.passbookNumber = passbookNumber;
            this.currentPage = currentPage;
            this.lastPrintedLine = lastPrintedLine;
            this.linesPerPage = linesPerPage;
            this.remainingLines = remainingLines;
            this.status = status;
            this.lastPrintDate = lastPrintDate;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getPassbookNumber() { return passbookNumber; }
        public void setPassbookNumber(String passbookNumber) { this.passbookNumber = passbookNumber; }
        public Integer getCurrentPage() { return currentPage; }
        public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }
        public Integer getLastPrintedLine() { return lastPrintedLine; }
        public void setLastPrintedLine(Integer lastPrintedLine) { this.lastPrintedLine = lastPrintedLine; }
        public Integer getLinesPerPage() { return linesPerPage; }
        public void setLinesPerPage(Integer linesPerPage) { this.linesPerPage = linesPerPage; }
        public Integer getRemainingLines() { return remainingLines; }
        public void setRemainingLines(Integer remainingLines) { this.remainingLines = remainingLines; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDateTime getLastPrintDate() { return lastPrintDate; }
        public void setLastPrintDate(LocalDateTime lastPrintDate) { this.lastPrintDate = lastPrintDate; }
    }

    public static class ConfigDto {
        private int amountWidth = 13;
        private int balanceColumn = 52;
        private int charactersPerLine = 80;
        private int creditColumn = 38;
        private int dateColumn = 0;
        private String dateFormat = "dd/MM/yyyy";
        private int dateWidth = 10;
        private int debitColumn = 24;
        private int decimalPlaces = 2;
        private int lineSpacing = 1;
        private int linesPerPage = 20;
        private int sandiColumn = 11;
        private int sandiWidth = 12;
        private int startingLinePosition = 1;
        private int tellerColumn = 66;
        private int tellerWidth = 14;

        public ConfigDto() {}

        public int getAmountWidth() { return amountWidth; }
        public void setAmountWidth(int amountWidth) { this.amountWidth = amountWidth; }
        public int getBalanceColumn() { return balanceColumn; }
        public void setBalanceColumn(int balanceColumn) { this.balanceColumn = balanceColumn; }
        public int getCharactersPerLine() { return charactersPerLine; }
        public void setCharactersPerLine(int charactersPerLine) { this.charactersPerLine = charactersPerLine; }
        public int getCreditColumn() { return creditColumn; }
        public void setCreditColumn(int creditColumn) { this.creditColumn = creditColumn; }
        public int getDateColumn() { return dateColumn; }
        public void setDateColumn(int dateColumn) { this.dateColumn = dateColumn; }
        public String getDateFormat() { return dateFormat; }
        public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
        public int getDateWidth() { return dateWidth; }
        public void setDateWidth(int dateWidth) { this.dateWidth = dateWidth; }
        public int getDebitColumn() { return debitColumn; }
        public void setDebitColumn(int debitColumn) { this.debitColumn = debitColumn; }
        public int getDecimalPlaces() { return decimalPlaces; }
        public void setDecimalPlaces(int decimalPlaces) { this.decimalPlaces = decimalPlaces; }
        public int getLineSpacing() { return lineSpacing; }
        public void setLineSpacing(int lineSpacing) { this.lineSpacing = lineSpacing; }
        public int getLinesPerPage() { return linesPerPage; }
        public void setLinesPerPage(int linesPerPage) { this.linesPerPage = linesPerPage; }
        public int getSandiColumn() { return sandiColumn; }
        public void setSandiColumn(int sandiColumn) { this.sandiColumn = sandiColumn; }
        public int getSandiWidth() { return sandiWidth; }
        public void setSandiWidth(int sandiWidth) { this.sandiWidth = sandiWidth; }
        public int getStartingLinePosition() { return startingLinePosition; }
        public void setStartingLinePosition(int startingLinePosition) { this.startingLinePosition = startingLinePosition; }
        public int getTellerColumn() { return tellerColumn; }
        public void setTellerColumn(int tellerColumn) { this.tellerColumn = tellerColumn; }
        public int getTellerWidth() { return tellerWidth; }
        public void setTellerWidth(int tellerWidth) { this.tellerWidth = tellerWidth; }
    }

    public static class TransactionDto {
        private String id;
        private String transactionNumber;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        private LocalDateTime transactionDate;
        private String transactionType;
        private String description;
        private BigDecimal debit;
        private BigDecimal credit;
        private BigDecimal balance;
        private String sandiCode;
        private Integer lineNumber;
        private String tellerName;

        public TransactionDto() {}
        public TransactionDto(String id, String transactionNumber, LocalDateTime transactionDate, String transactionType,
                              String description, BigDecimal debit, BigDecimal credit, BigDecimal balance,
                              String sandiCode, Integer lineNumber, String tellerName) {
            this.id = id;
            this.transactionNumber = transactionNumber;
            this.transactionDate = transactionDate;
            this.transactionType = transactionType;
            this.description = description;
            this.debit = debit;
            this.credit = credit;
            this.balance = balance;
            this.sandiCode = sandiCode;
            this.lineNumber = lineNumber;
            this.tellerName = tellerName;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getTransactionNumber() { return transactionNumber; }
        public void setTransactionNumber(String transactionNumber) { this.transactionNumber = transactionNumber; }
        public LocalDateTime getTransactionDate() { return transactionDate; }
        public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
        public String getTransactionType() { return transactionType; }
        public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getDebit() { return debit; }
        public void setDebit(BigDecimal debit) { this.debit = debit; }
        public BigDecimal getCredit() { return credit; }
        public void setCredit(BigDecimal credit) { this.credit = credit; }
        public BigDecimal getBalance() { return balance; }
        public void setBalance(BigDecimal balance) { this.balance = balance; }
        public String getSandiCode() { return sandiCode; }
        public void setSandiCode(String sandiCode) { this.sandiCode = sandiCode; }
        public Integer getLineNumber() { return lineNumber; }
        public void setLineNumber(Integer lineNumber) { this.lineNumber = lineNumber; }
        public String getTellerName() { return tellerName; }
        public void setTellerName(String tellerName) { this.tellerName = tellerName; }
    }
}
