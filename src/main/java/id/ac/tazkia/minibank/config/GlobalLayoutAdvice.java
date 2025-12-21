package id.ac.tazkia.minibank.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalLayoutAdvice {

    @ModelAttribute
    public void injectHeaderMeta(Model model, Authentication auth) {
        if (auth == null) return;

        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String roleLabel =
                roles.contains("ROLE_ADMIN") ? "Admin" :
                roles.contains("ROLE_SUPERVISOR") ? "Supervisor" :
                roles.contains("ROLE_TELLER") ? "Teller" :
                roles.contains("ROLE_CS") ? "Customer Service" :
                "User";

        model.addAttribute("roleLabel", roleLabel);

        // sementara pakai username sebagai "ID" biar konsisten muncul
        // kalau kamu punya field khusus ID (misal ID00010003), nanti kita ambil dari DB.
        model.addAttribute("userCode", auth.getName());
    }
}
