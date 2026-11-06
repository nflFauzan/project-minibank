package id.ac.tazkia.minibank.dto.rbac;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordForm {

    @NotNull
    private Long userId;

    private String username;
    private String fullName;
    private String email;
    private boolean active;

    @NotBlank(message = "Password baru wajib diisi")
    @Size(min = 6, message = "Password minimal 6 karakter")
    private String newPassword;

    @NotBlank(message = "Konfirmasi password wajib diisi")
    private String confirmPassword;
}

