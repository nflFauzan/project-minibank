package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name; // e.g. ROLE_ADMIN, ROLE_CS

    @Column(name = "role_code", length = 100)
    private String roleCode; // e.g. ADMIN, CUSTOMER_SERVICE, TELLER, BRANCH_MANAGER

    @Column(name = "display_name", length = 150)
    private String displayName; // e.g. System Administrator, Customer Service

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Role() {
    }

    public Role(Long id, String name) {   // dipakai di DataSeedConfig
        this.id = id;
        this.name = name;
        this.active = true;
    }

    public Role(String name, String roleCode, String displayName, String description) {
        this.name = name;
        this.roleCode = roleCode;
        this.displayName = displayName;
        this.description = description;
        this.active = true;
    }

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (roleCode == null && name != null) {
            roleCode = name.startsWith("ROLE_") ? name.substring(5) : name;
        }
        if (displayName == null && roleCode != null) {
            displayName = roleCode;
        }
    }

    // getter & setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
