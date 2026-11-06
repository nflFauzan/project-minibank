package id.ac.tazkia.minibank.dto.rbac;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionForm {

    private Long id;

    @NotBlank(message = "Kode permission wajib diisi")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Kode permission hanya boleh huruf, angka, dan underscore")
    @Size(min = 2, max = 50, message = "Kode permission antara 2 - 50 karakter")
    private String permissionCode;

    @NotBlank(message = "Nama permission wajib diisi")
    @Size(min = 2, max = 150, message = "Nama permission antara 2 - 150 karakter")
    private String permissionName;

    @NotBlank(message = "Kategori wajib dipilih")
    private String category;

    private String description;
}

