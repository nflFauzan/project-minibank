package id.ac.tazkia.minibank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupForm {
    @NotBlank private String username;
    @NotBlank private String password;
    @NotBlank @Email private String email;
    @NotBlank private String fullName;

    private String nim;
    private String prodi;
    private String dosenPembimbing;
}
