package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity_logs",
    indexes = {
        @Index(name = "idx_activity_username", columnList = "username"),
        @Index(name = "idx_activity_timestamp", columnList = "timestamp"),
        @Index(name = "idx_activity_type", columnList = "activity_type"),
        @Index(name = "idx_activity_module", columnList = "module")
    })
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "user_full_name", length = 200)
    private String userFullName;

    @Column(name = "user_role", length = 50)
    private String userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, length = 50)
    private ActivityType activityType;

    @Column(nullable = false, length = 30)
    private String module;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "target_entity_id", length = 100)
    private String targetEntityId;

    @Column(precision = 38, scale = 2)
    private BigDecimal amount;

    @Column(length = 30)
    private String status = "SUCCESS";

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public UserActivityLog() {
    }

    public UserActivityLog(String username, String userFullName, String userRole,
                           ActivityType activityType, String module, String description,
                           String referenceNumber, BigDecimal amount, String status) {
        this.username = username;
        this.userFullName = userFullName;
        this.userRole = userRole;
        this.activityType = activityType;
        this.module = module;
        this.description = description;
        this.referenceNumber = referenceNumber;
        this.amount = amount;
        this.status = status != null ? status : "SUCCESS";
        this.timestamp = LocalDateTime.now();
    }

    @PrePersist
    public void onPrePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if (status == null) {
            status = "SUCCESS";
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getTargetEntityId() { return targetEntityId; }
    public void setTargetEntityId(String targetEntityId) { this.targetEntityId = targetEntityId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

