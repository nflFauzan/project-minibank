package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "produk_tabungan")
@Getter
@Setter
public class ProdukTabungan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, length = 36)
    private String uuid;

    // e.g. TAB001, TAB002, TAB_WAD
    @Column(name = "kode_produk", nullable = false, unique = true, length = 50)
    private String kodeProduk;

    @Column(name = "nama_produk", nullable = false, length = 150)
    private String namaProduk;

    // Type matching minibank.tazkia.ac.id: TABUNGAN_WADIAH, TABUNGAN_MUDHARABAH, DEPOSITO_MUDHARABAH, PEMBIAYAAN_MURABAHAH, etc.
    @Column(name = "product_type", length = 50)
    private String productType;

    // Category matching minibank.tazkia.ac.id: Tabungan Syariah, Deposito Syariah, Pembiayaan Syariah, Giro Syariah
    @Column(name = "product_category", length = 100)
    private String productCategory;

    @Column(name = "currency", length = 10)
    private String currency = "IDR";

    // WADIAH / MUDHARABAH / MUSYARAKAH / etc.
    @Column(name = "jenis_akad", length = 50)
    private String jenisAkad;

    @Column(name = "deskripsi_singkat", length = 1000)
    private String deskripsiSingkat;

    // Settings
    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "allow_overdraft")
    private Boolean allowOverdraft = false;

    @Column(name = "require_maintaining_balance")
    private Boolean requireMaintainingBalance = true;

    // Islamic Banking Financial Configuration
    @Column(name = "setoran_awal_minimum", precision = 38, scale = 2)
    private BigDecimal setoranAwalMinimum = BigDecimal.ZERO;

    @Column(name = "minimum_balance", precision = 38, scale = 2)
    private BigDecimal minimumBalance = BigDecimal.ZERO;

    @Column(name = "maximum_balance", precision = 38, scale = 2)
    private BigDecimal maximumBalance;

    @Column(name = "profit_sharing_ratio", precision = 10, scale = 4)
    private BigDecimal profitSharingRatio = BigDecimal.ZERO;

    // Profit Sharing Type: MUDHARABAH, MUSHARAKAH, WADIAH, MURABAHAH, IJARAH, SALAM, ISTISNA
    @Column(name = "profit_sharing_type", length = 50)
    private String profitSharingType;

    // Profit Distribution Frequency: DAILY, MONTHLY, QUARTERLY, ANNUALLY, ON_MATURITY
    @Column(name = "profit_distribution_frequency", length = 50)
    private String profitDistributionFrequency = "MONTHLY";

    @Column(name = "nisbah_customer", precision = 10, scale = 4)
    private BigDecimal nisbahCustomer;

    @Column(name = "nisbah_bank", precision = 10, scale = 4)
    private BigDecimal nisbahBank;

    @Column(name = "is_shariah_compliant")
    private Boolean isShariahCompliant = true;

    @Column(name = "shariah_board_approval_number", length = 100)
    private String shariahBoardApprovalNumber;

    @Column(name = "shariah_board_approval_date")
    private LocalDate shariahBoardApprovalDate;

    // Limits & Fees
    @Column(name = "daily_withdrawal_limit", precision = 38, scale = 2)
    private BigDecimal dailyWithdrawalLimit;

    @Column(name = "monthly_transaction_limit")
    private Integer monthlyTransactionLimit;

    @Column(name = "overdraft_limit", precision = 38, scale = 2)
    private BigDecimal overdraftLimit = BigDecimal.ZERO;

    @Column(name = "monthly_maintenance_fee", precision = 38, scale = 2)
    private BigDecimal monthlyMaintenanceFee = BigDecimal.ZERO;

    @Column(name = "atm_withdrawal_fee", precision = 38, scale = 2)
    private BigDecimal atmWithdrawalFee = BigDecimal.ZERO;

    @Column(name = "inter_bank_transfer_fee", precision = 38, scale = 2)
    private BigDecimal interBankTransferFee = BigDecimal.ZERO;

    // Transaction Settings
    @Column(name = "free_transactions_per_month")
    private Integer freeTransactionsPerMonth = 0;

    @Column(name = "excess_transaction_fee", precision = 38, scale = 2)
    private BigDecimal excessTransactionFee = BigDecimal.ZERO;

    @Column(name = "below_minimum_balance_fee", precision = 38, scale = 2)
    private BigDecimal belowMinimumBalanceFee = BigDecimal.ZERO;

    // Customer Eligibility
    @Column(name = "min_customer_age")
    private Integer minCustomerAge;

    @Column(name = "max_customer_age")
    private Integer maxCustomerAge;

    @Column(name = "allowed_customer_types", length = 100)
    private String allowedCustomerTypes = "PERSONAL,CORPORATE";

    @Column(name = "required_documents", length = 1000)
    private String requiredDocuments;

    // Lifecycle
    @Column(name = "launch_date")
    private LocalDate launchDate;

    @Column(name = "retirement_date")
    private LocalDate retirementDate;

    // Audit Info
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy = "SYSTEM";

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null || this.uuid.isBlank()) {
            this.uuid = UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.createdBy == null || this.createdBy.isBlank()) {
            this.createdBy = "SYSTEM";
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
        if (this.productType == null && this.jenisAkad != null) {
            // Default productType based on akad
            this.productType = "TABUNGAN_" + this.jenisAkad.toUpperCase();
        }
        if (this.productCategory == null) {
            this.productCategory = "Tabungan Syariah";
        }
        if (this.profitSharingType == null && this.jenisAkad != null) {
            this.profitSharingType = this.jenisAkad.toUpperCase();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Explicit compatibility getters/setters
    public Boolean getIsActive() {
        return aktif;
    }

    public void setIsActive(Boolean isActive) {
        this.aktif = isActive;
    }

    public BigDecimal getMinimumOpeningBalance() {
        return setoranAwalMinimum;
    }

    public void setMinimumOpeningBalance(BigDecimal minimumOpeningBalance) {
        this.setoranAwalMinimum = minimumOpeningBalance;
    }

    public String getDescription() {
        return deskripsiSingkat;
    }

    public void setDescription(String description) {
        this.deskripsiSingkat = description;
    }

    public String getProductCode() {
        return kodeProduk;
    }

    public void setProductCode(String productCode) {
        this.kodeProduk = productCode;
    }

    public String getProductName() {
        return namaProduk;
    }

    public void setProductName(String productName) {
        this.namaProduk = productName;
    }
}
