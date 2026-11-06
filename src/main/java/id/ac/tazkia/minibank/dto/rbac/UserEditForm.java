package id.ac.tazkia.minibank.dto.rbac;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditForm {

    @NotNull
    private Long id;

    @NotBlank(message = "Username wajib diisi")
    @Size(min = 3, max = 50, message = "Username harus antara 3 - 50 karakter")
    private String username;

    @NotBlank(message = "Nama lengkap wajib diisi")
    @Size(min = 2, max = 255, message = "Nama lengkap minimal 2 karakter")
    private String fullName;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotNull(message = "Cabang wajib dipilih")
    private Long branchId;

    private boolean enabled = true;
    private boolean locked = false;
}

