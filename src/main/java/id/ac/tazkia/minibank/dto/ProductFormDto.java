package id.ac.tazkia.minibank.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductFormDto {

    private String id;

    // Both English and Indonesian property names for 100% compatibility
    private String productCode;
    private String kodeProduk;

    private String productName;
    private String namaProduk;

    private String productType;
    private String productCategory;

    private String currency = "IDR";

    private String description;
    private String deskripsiSingkat;

    private String jenisAkad;

    private Boolean isActive = true;
    private Boolean aktif = true;

    private Boolean isDefault = false;
    private Boolean allowOverdraft = false;
    private Boolean requireMaintainingBalance = true;

    private BigDecimal minimumOpeningBalance;
    private BigDecimal setoranAwalMinimum;

    private BigDecimal minimumBalance = BigDecimal.ZERO;
    private BigDecimal maximumBalance;

    private BigDecimal profitSharingRatio = BigDecimal.ZERO;
    private String profitSharingType;
    private String profitDistributionFrequency = "MONTHLY";

    private BigDecimal nisbahCustomer;
    private BigDecimal nisbahBank;

    private Boolean isShariahCompliant = true;
    private String shariahBoardApprovalNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate shariahBoardApprovalDate;

    private BigDecimal dailyWithdrawalLimit;
    private Integer monthlyTransactionLimit;
    private BigDecimal overdraftLimit = BigDecimal.ZERO;

    private BigDecimal monthlyMaintenanceFee = BigDecimal.ZERO;
    private BigDecimal atmWithdrawalFee = BigDecimal.ZERO;
    private BigDecimal interBankTransferFee = BigDecimal.ZERO;

    private Integer freeTransactionsPerMonth = 0;
    private BigDecimal excessTransactionFee = BigDecimal.ZERO;
    private BigDecimal belowMinimumBalanceFee = BigDecimal.ZERO;

    private Integer minCustomerAge;
    private Integer maxCustomerAge;

    private String allowedCustomerTypes = "PERSONAL,CORPORATE";
    private String requiredDocuments;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate launchDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate retirementDate;

    // Unified getters that resolve between English / Indonesian names
    public String resolveCode() {
        if (productCode != null && !productCode.isBlank()) return productCode.trim();
        if (kodeProduk != null && !kodeProduk.isBlank()) return kodeProduk.trim();
        return null;
    }

    public String resolveName() {
        if (productName != null && !productName.isBlank()) return productName.trim();
        if (namaProduk != null && !namaProduk.isBlank()) return namaProduk.trim();
        return null;
    }

    public String resolveDescription() {
        if (description != null && !description.isBlank()) return description.trim();
        if (deskripsiSingkat != null && !deskripsiSingkat.isBlank()) return deskripsiSingkat.trim();
        return null;
    }

    public BigDecimal resolveOpeningBalance() {
        if (minimumOpeningBalance != null) return minimumOpeningBalance;
        if (setoranAwalMinimum != null) return setoranAwalMinimum;
        return BigDecimal.ZERO;
    }

    public Boolean resolveActive() {
        if (isActive != null) return isActive;
        if (aktif != null) return aktif;
        return true;
    }

    public String resolveAkad() {
        if (jenisAkad != null && !jenisAkad.isBlank()) return jenisAkad.trim();
        if (profitSharingType != null && !profitSharingType.isBlank()) return profitSharingType.trim();
        if (productType != null) {
            String pt = productType.toUpperCase();
            if (pt.contains("WADIAH")) return "WADIAH";
            if (pt.contains("MUDHARABAH")) return "MUDHARABAH";
            if (pt.contains("MUSHARAKAH") || pt.contains("MUSYARAKAH")) return "MUSYARAKAH";
            if (pt.contains("MURABAHAH")) return "MURABAHAH";
            if (pt.contains("IJARAH")) return "IJARAH";
            if (pt.contains("SALAM")) return "SALAM";
            if (pt.contains("ISTISNA")) return "ISTISNA";
        }
        return "WADIAH";
    }

    public String resolveProductType() {
        if (productType != null && !productType.isBlank()) return productType.trim();
        String akad = resolveAkad();
        return "TABUNGAN_" + akad.toUpperCase();
    }

    public String resolveCategory() {
        if (productCategory != null && !productCategory.isBlank()) return productCategory.trim();
        return "Tabungan Syariah";
    }
}

