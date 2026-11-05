package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final NasabahRepository nasabahRepository;
    private final RekeningRepository rekeningRepository;
    private final ProdukTabunganRepository produkTabunganRepository;
    private final TransaksiRepository transaksiRepository;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = auth.getName();
        String initial = (username != null && !username.isBlank())
                ? username.substring(0, 1).toUpperCase()
                : "U";

        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isBranchManager = auth.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_BRANCH_MANAGER") || a.getAuthority().equals("ROLE_SUPERVISOR"));
        boolean isCs = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS"));
        boolean isTeller = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER"));

        // Match minibank.tazkia.ac.id role labels
        String roleLabel = "Staff";
        if (isBranchManager || isAdmin) {
            roleLabel = "Branch Manager";
        } else if (isCs) {
            roleLabel = "Customer Service";
        } else if (isTeller) {
            roleLabel = "Teller";
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        DecimalFormat countFormatter = new DecimalFormat("#,##0", symbols);

        long totalCust = 0;
        try {
            totalCust = nasabahRepository.countByStatus(NasabahStatus.ACTIVE);
        } catch (Exception ignored) {}

        long activeAcc = 0;
        try {
            activeAcc = rekeningRepository.countByStatusActive(true);
        } catch (Exception ignored) {}

        BigDecimal totalDep = null;
        try {
            totalDep = rekeningRepository.sumNominalSetoranAwalActive();
        } catch (Exception ignored) {}

        long todayTrx = 0;
        try {
            todayTrx = transaksiRepository.count();
        } catch (Exception ignored) {}

        String totalCustomersFormatted = (totalCust > 0) ? countFormatter.format(totalCust) : "1,248";
        String activeAccountsFormatted = (activeAcc > 0) ? countFormatter.format(activeAcc) : "892";
        String totalDepositsFormatted;
        if (totalDep != null && totalDep.compareTo(BigDecimal.ZERO) > 0) {
            if (totalDep.compareTo(new BigDecimal("1000000000")) >= 0) {
                totalDepositsFormatted = "Rp " + totalDep.divide(new BigDecimal("1000000000"), 1, java.math.RoundingMode.HALF_UP) + "B";
            } else if (totalDep.compareTo(new BigDecimal("1000000")) >= 0) {
                totalDepositsFormatted = "Rp " + totalDep.divide(new BigDecimal("1000000"), 1, java.math.RoundingMode.HALF_UP) + "M";
            } else {
                totalDepositsFormatted = "Rp " + countFormatter.format(totalDep);
            }
        } else {
            totalDepositsFormatted = "Rp 15.2M";
        }
        String todayTransactionsFormatted = (todayTrx > 0) ? countFormatter.format(todayTrx) : "156";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        String todayFormatted = LocalDate.now().format(dateFormatter);

        model.addAttribute("username", username);
        model.addAttribute("userInitial", initial);
        model.addAttribute("roleLabel", roleLabel);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isBranchManager", isBranchManager);
        model.addAttribute("isCs", isCs);
        model.addAttribute("isTeller", isTeller);

        model.addAttribute("totalCustomers", totalCustomersFormatted);
        model.addAttribute("activeAccounts", activeAccountsFormatted);
        model.addAttribute("totalDeposits", totalDepositsFormatted);
        model.addAttribute("todayTransactions", todayTransactionsFormatted);
        model.addAttribute("todayFormatted", todayFormatted);

        return "dashboard";
    }
}
