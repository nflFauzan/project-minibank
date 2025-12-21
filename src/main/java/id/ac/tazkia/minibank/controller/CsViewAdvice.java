package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class CsViewAdvice {

    private final UserRepository userRepository;

    @ModelAttribute
    public void injectCsHeader(Model model, Authentication auth, HttpServletRequest req) {
        String uri = req.getRequestURI();
        if (uri == null || !uri.startsWith("/cs")) return;

        // waktu (Indonesia)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy | HH:mm", new Locale("id", "ID"));
        model.addAttribute("nowText", LocalDateTime.now().format(fmt));

        // role label (CS)
        model.addAttribute("roleLabel", "Customer Service");

        // nama user
        if (auth != null) {
            String username = auth.getName();
            String fullName = userRepository.findByUsername(username)
                    .map(User::getFullName)
                    .orElse(username);
            model.addAttribute("currentFullName", fullName);

            // kalau kamu belum punya field employeeId, sementara pakai username
            model.addAttribute("employeeId", username);
        }


    }
}
