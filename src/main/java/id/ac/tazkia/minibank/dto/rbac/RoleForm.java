package id.ac.tazkia.minibank.dto.rbac;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleForm {

    private Long id;

    @NotBlank(message = "Kode role wajib diisi")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Kode role hanya boleh huruf, angka, dan underscore")
    @Size(min = 2, max = 50, message = "Kode role antara 2 - 50 karakter")
    private String roleCode;

    @NotBlank(message = "Nama role wajib diisi")
    @Size(min = 2, max = 100, message = "Nama role antara 2 - 100 karakter")
    private String roleName;

    private String description;
}

