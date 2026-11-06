package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_code", nullable = false, unique = true, length = 100)
    private String permissionCode;

    @Column(name = "permission_name", nullable = false, length = 200)
    private String permissionName;

    @Column(nullable = false, length = 50)
    private String category; // ACCOUNT, APPROVAL, AUDIT, BRANCH, CUSTOMER, PRODUCT, REPORT, TRANSACTION, USER

    @Column(length = 500)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Permission() {}

    public Permission(String permissionCode, String permissionName, String category, String description) {
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
        this.category = category;
        this.description = description;
    }

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPermissionCode() { return permissionCode; }
    public void setPermissionCode(String permissionCode) { this.permissionCode = permissionCode; }

    public String getPermissionName() { return permissionName; }
    public void setPermissionName(String permissionName) { this.permissionName = permissionName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

