package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.security.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE) // jalan belakangan supaya kalau ada yang ngeset duluan, ini yang menang
public class TellerViewAdvice {

    private final UserRepository userRepository;

    @ModelAttribute
    public void injectTellerTopbar(Model model, HttpServletRequest request, Authentication auth) {
        if (request == null) return;

        // hanya untuk halaman teller
        String uri = request.getRequestURI();
        if (uri == null || !uri.startsWith("/teller/")) return;

        // hanya kalau module aktif = TELLER (bukan cuma karena user punya role)
        HttpSession session = request.getSession(false);
        if (session == null) return;

        Object m = session.getAttribute(LoginSuccessHandler.SESSION_ACTIVE_MODULE);
        if (m == null || !"TELLER".equalsIgnoreCase(m.toString())) return;

        String username = (auth == null) ? "-" : auth.getName();

        // 1) Topbar kanan: Teller | username
        model.addAttribute("roleLabel", "Teller");
        model.addAttribute("employeeId", username);

        // 2) Greeting: full_name
        String fullName = userRepository.findByUsername(username)
                .map(u -> (u.getFullName() == null || u.getFullName().isBlank()) ? username : u.getFullName())
                .orElse(username);

        model.addAttribute("currentFullName", fullName);

        // waktu
        model.addAttribute("nowText",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                        "EEEE, dd-MM-yyyy | HH:mm", new Locale("id", "ID")
                )));
    }
}
