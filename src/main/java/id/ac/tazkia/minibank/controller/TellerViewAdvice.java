package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice(assignableTypes = {TellerDashboardController.class, TellerTransactionController.class})
@RequiredArgsConstructor
public class TellerViewAdvice {

    private final UserRepository userRepository;

    @ModelAttribute("roleLabel")
    public String roleLabel() {
        return "Teller";
    }

    @ModelAttribute("employeeId")
    public String employeeId(Authentication auth) {
        if (auth == null) return "-";
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .map(u -> (u.getFullName() == null || u.getFullName().isBlank()) ? username : u.getFullName())
                .orElse(username);
    }

    @ModelAttribute("nowText")
    public String nowText() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy | HH:mm", new Locale("id", "ID")));
    }
}
