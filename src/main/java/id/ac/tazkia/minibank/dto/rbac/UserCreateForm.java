package id.ac.tazkia.minibank.dto.rbac;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserCreateForm {

    @NotBlank(message = "Username wajib diisi")
    @Size(min = 3, max = 50, message = "Username harus antara 3 - 50 karakter")
    private String username;

    @NotBlank(message = "Nama lengkap wajib diisi")
    @Size(min = 2, max = 255, message = "Nama lengkap minimal 2 karakter")
    private String fullName;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    private String email;

    private Long branchId;

    private String password;

    private String nim;

    private String prodi;

    private String dosenPembimbing;

    private List<String> roles = new ArrayList<>();
}
