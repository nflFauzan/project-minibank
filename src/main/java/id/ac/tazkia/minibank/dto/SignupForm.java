package id.ac.tazkia.minibank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignupForm {

    @NotBlank
    private String fullName;

    @NotBlank
    private String dosenPembimbing;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String prodi;

    @NotBlank
    private String password;

    @NotBlank
    private String nim;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDosenPembimbing() { return dosenPembimbing; }
    public void setDosenPembimbing(String dosenPembimbing) { this.dosenPembimbing = dosenPembimbing; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }
}
