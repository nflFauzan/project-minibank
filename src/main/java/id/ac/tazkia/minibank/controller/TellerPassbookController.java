package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.Passbook;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.service.PassbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TellerPassbookController {

    private static final Logger log = LoggerFactory.getLogger(TellerPassbookController.class);

    private final PassbookService passbookService;

    public TellerPassbookController(PassbookService passbookService) {
        this.passbookService = passbookService;
    }

    // 1. SELECT ACCOUNT LIST
    @GetMapping("/teller/passbook/select-account")
    public String selectAccount(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Rekening> accounts = passbookService.listAccountsForPassbook(search);
        model.addAttribute("accounts", accounts);
        model.addAttribute("search", search != null ? search : "");
        return "teller/passbook/select-account";
    }

    // 2. PASSBOOK PREVIEW
    @GetMapping({"/teller/passbook/preview/{accountId}", "/passbook/preview/{accountId}"})
    public String preview(@PathVariable String accountId, Model model, RedirectAttributes ra) {
        try {
            Rekening rekening = passbookService.findRekening(accountId);
            Passbook passbook = passbookService.getOrCreatePassbook(rekening);
            List<Transaksi> recentTransactions = passbookService.getRecentTransactions(rekening, 10);

            model.addAttribute("account", rekening);
            model.addAttribute("passbook", passbook);
            model.addAttribute("customer", rekening.getNasabah());
            model.addAttribute("recentTransactions", recentTransactions);
            model.addAttribute("accountId", accountId);

            return "teller/passbook/preview";
        } catch (Exception e) {
            log.error("Failed to load passbook preview for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Rekening atau passbook tidak ditemukan: " + e.getMessage());
            return "redirect:/teller/passbook/select-account";
        }
    }

    // 3. PASSBOOK INITIALIZATION FORM (GET)
    @GetMapping({"/teller/passbook/initialize/{accountId}", "/passbook/initialize/{accountId}"})
    public String initializeForm(@PathVariable String accountId, Model model, RedirectAttributes ra) {
        try {
            Rekening rekening = passbookService.findRekening(accountId);
            Passbook passbook = passbookService.getOrCreatePassbook(rekening);
            List<Transaksi> allTransactions = passbookService.getTransactionsForPrint(rekening, null, null);

            model.addAttribute("account", rekening);
            model.addAttribute("passbook", passbook);
            model.addAttribute("customer", rekening.getNasabah());
            model.addAttribute("allTransactions", allTransactions);
            model.addAttribute("accountId", accountId);

            return "teller/passbook/initialize";
        } catch (Exception e) {
            log.error("Failed to load passbook initialization form for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Rekening tidak ditemukan: " + e.getMessage());
            return "redirect:/teller/passbook/select-account";
        }
    }

    // 4. PASSBOOK INITIALIZATION SUBMIT (POST)
    @PostMapping({"/teller/passbook/initialize/{accountId}", "/passbook/initialize/{accountId}"})
    public String doInitialize(@PathVariable String accountId,
                               @RequestParam(defaultValue = "1") int currentPage,
                               @RequestParam(defaultValue = "0") int lastPrintedLine,
                               @RequestParam(value = "lastPrintedTransactionId", required = false) String lastPrintedTransactionId,
                               RedirectAttributes ra) {
        try {
            passbookService.initializePassbook(accountId, currentPage, lastPrintedLine, lastPrintedTransactionId);
            ra.addFlashAttribute("success", "Passbook status successfully initialized.");
            return "redirect:/teller/passbook/preview/" + accountId;
        } catch (Exception e) {
            log.error("Failed to initialize passbook for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Gagal menginisialisasi passbook: " + e.getMessage());
            return "redirect:/teller/passbook/initialize/" + accountId;
        }
    }

    // 5. DIRECT PRINT (PLQ-20 / ESC-POS)
    @GetMapping({"/teller/passbook/direct-print/{accountId}", "/passbook/direct-print/{accountId}"})
    public String directPrint(@PathVariable String accountId, Model model, RedirectAttributes ra) {
        try {
            Rekening rekening = passbookService.findRekening(accountId);
            Passbook passbook = passbookService.getOrCreatePassbook(rekening);

            model.addAttribute("account", rekening);
            model.addAttribute("passbook", passbook);
            model.addAttribute("customer", rekening.getNasabah());
            model.addAttribute("accountId", accountId);

            return "teller/passbook/direct-print";
        } catch (Exception e) {
            log.error("Failed to load direct print for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Rekening tidak ditemukan: " + e.getMessage());
            return "redirect:/teller/passbook/select-account";
        }
    }

    // 6. BROWSER PRINT / PDF PRINT VIEW
    @GetMapping({"/teller/passbook/print/{accountId}", "/passbook/print/{accountId}"})
    public String printView(@PathVariable String accountId,
                            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                            @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                            Model model,
                            RedirectAttributes ra) {
        try {
            Rekening rekening = passbookService.findRekening(accountId);
            Passbook passbook = passbookService.getOrCreatePassbook(rekening);
            List<Transaksi> transactions = passbookService.getTransactionsForPrint(rekening, fromDate, toDate);

            model.addAttribute("account", rekening);
            model.addAttribute("passbook", passbook);
            model.addAttribute("customer", rekening.getNasabah());
            model.addAttribute("transactions", transactions);
            model.addAttribute("fromDate", fromDate);
            model.addAttribute("toDate", toDate);
            model.addAttribute("printDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            model.addAttribute("accountId", accountId);

            return "teller/passbook/print";
        } catch (Exception e) {
            log.error("Failed to load passbook print view for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Rekening tidak ditemukan: " + e.getMessage());
            return "redirect:/teller/passbook/select-account";
        }
    }

    // 7. PRINT IDENTITY PAGE (Depan_01)
    @GetMapping({"/teller/passbook/print-identity/{accountId}", "/passbook/print-identity/{accountId}"})
    public String printIdentity(@PathVariable String accountId, Model model, RedirectAttributes ra) {
        try {
            Rekening rekening = passbookService.findRekening(accountId);
            Passbook passbook = passbookService.getOrCreatePassbook(rekening);
            Nasabah nasabah = rekening.getNasabah();

            String address = nasabah != null && nasabah.getAlamatIdentitas() != null && !nasabah.getAlamatIdentitas().isBlank()
                    ? nasabah.getAlamatIdentitas()
                    : (nasabah != null && nasabah.getAlamatDomisili() != null ? nasabah.getAlamatDomisili() : "Bogor, Jawa Barat");
            String identityNumber = nasabah != null && nasabah.getNik() != null ? nasabah.getNik() : "-";
            String branch = rekening.getCabangPembukaan() != null && !rekening.getCabangPembukaan().isBlank()
                    ? rekening.getCabangPembukaan()
                    : "KCU Tazkia Sentul";
            String createdDate = rekening.getTanggalPembukaan() != null
                    ? rekening.getTanggalPembukaan().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            model.addAttribute("account", rekening);
            model.addAttribute("passbook", passbook);
            model.addAttribute("customer", nasabah);
            model.addAttribute("address", address);
            model.addAttribute("identityNumber", identityNumber);
            model.addAttribute("branch", branch);
            model.addAttribute("createdDate", createdDate);
            model.addAttribute("accountId", accountId);

            return "teller/passbook/print-identity";
        } catch (Exception e) {
            log.error("Failed to load passbook identity print view for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Rekening tidak ditemukan: " + e.getMessage());
            return "redirect:/teller/passbook/select-account";
        }
    }

    // 8. FULL BOOKLET VIEW (Cover, Depan, Isi, Belakang 100% Persis Gambar)
    @GetMapping({"/teller/passbook/booklet/{accountId}", "/passbook/booklet/{accountId}"})
    public String fullBooklet(@PathVariable String accountId, Model model, RedirectAttributes ra) {
        try {
            Rekening rekening = passbookService.findRekening(accountId);
            Passbook passbook = passbookService.getOrCreatePassbook(rekening);
            Nasabah nasabah = rekening.getNasabah();
            List<Transaksi> transactions = passbookService.getTransactionsForPrint(rekening, null, null);

            String address = nasabah != null && nasabah.getAlamatIdentitas() != null && !nasabah.getAlamatIdentitas().isBlank()
                    ? nasabah.getAlamatIdentitas()
                    : (nasabah != null && nasabah.getAlamatDomisili() != null ? nasabah.getAlamatDomisili() : "Bogor, Jawa Barat");
            String identityNumber = nasabah != null && nasabah.getNik() != null ? nasabah.getNik() : "-";
            String branch = rekening.getCabangPembukaan() != null && !rekening.getCabangPembukaan().isBlank()
                    ? rekening.getCabangPembukaan()
                    : "KCU Tazkia Sentul";
            String createdDate = rekening.getTanggalPembukaan() != null
                    ? rekening.getTanggalPembukaan().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            model.addAttribute("account", rekening);
            model.addAttribute("passbook", passbook);
            model.addAttribute("customer", nasabah);
            model.addAttribute("transactions", transactions);
            model.addAttribute("address", address);
            model.addAttribute("identityNumber", identityNumber);
            model.addAttribute("branch", branch);
            model.addAttribute("createdDate", createdDate);
            model.addAttribute("accountId", accountId);

            return "teller/passbook/booklet-full";
        } catch (Exception e) {
            log.error("Failed to load full passbook booklet for {}: {}", accountId, e.getMessage());
            ra.addFlashAttribute("error", "Rekening tidak ditemukan: " + e.getMessage());
            return "redirect:/teller/passbook/select-account";
        }
    }
}
