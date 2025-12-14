package id.ac.tazkia.minibank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(length = 50)
    private String nim;

    @Column(length = 100)
    private String prodi;

    @Column
    private Boolean approved = false;

    @Column
    private Boolean enabled = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "dosen_pembimbing")
    private String dosenPembimbing;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (approved == null) {
            approved = false;
        }
        if (enabled == null) {
            enabled = true;
        }
    }

    // ======== getter & setter =========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {           // dipakai banyak service
        return password;
    }

    public void setPassword(String password) {  // dipakai DataSeed & RegistrationService
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {        // dipakai DataSeed
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public boolean isApproved() {               // dipakai UserDetailsServiceImpl
        return Boolean.TRUE.equals(approved);
    }

    public void setApproved(boolean approved) { // dipakai AdminUserService & RegistrationService
        this.approved = approved;
    }

    public boolean isEnabled() {                // dipakai UserDetailsServiceImpl
        return Boolean.TRUE.equals(enabled);
    }

    public void setEnabled(boolean enabled) {   // dipakai AdminUserService & RegistrationService
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDosenPembimbing() {
        return dosenPembimbing;
    }

    public void setDosenPembimbing(String dosenPembimbing) {
        this.dosenPembimbing = dosenPembimbing;
    }

    public Set<Role> getRoles() {               // dipakai DataSeed & UserDetailsServiceImpl
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
