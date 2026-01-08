package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.service.TellerDepositService;
import id.ac.tazkia.minibank.service.TellerWithdrawalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller/transaction")
public class TellerTransactionController {

    private final TransaksiRepository transaksiRepository;
    private final RekeningRepository rekeningRepository;
    private final TellerDepositService tellerDepositService;
    private final TellerWithdrawalService tellerWithdrawalService;

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) TipeTransaksi type,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "processedAt"));
        Page<Transaksi> result = transaksiRepository.search(q, type, pageable);

        model.addAttribute("active", "transaction");
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        model.addAttribute("type", type);
        return "teller/transaction/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable UUID id, Model model) {
        Transaksi t = transaksiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaksi tidak ditemukan"));

        List<Transaksi> group = transaksiRepository.findByGroupIdOrderByProcessedAtAsc(t.getGroupId());
        model.addAttribute("active", "transaction");
        model.addAttribute("tx", t);
        model.addAttribute("groupTx", group);

        return "teller/transaction/view";
    }

    // =========================
    // DEPOSIT: STEP 1 (SELECT)
    // =========================
    @GetMapping("/deposit")
    public String depositSelect(@RequestParam(required = false) String q,
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rekening> result = rekeningRepository.searchActiveForTeller(q, pageable);

        model.addAttribute("active", "deposit");
        model.addAttribute("q", q);
        model.addAttribute("page", result);
        return "teller/transaction/deposit_select";
    }

    // =========================
    // DEPOSIT: STEP 2 (FORM)
    // =========================
    @GetMapping("/deposit/{no}")
    public String depositForm(@PathVariable("no") String nomorRekening,
                              Model model,
                              RedirectAttributes ra) {
        try {
            Rekening r = tellerDepositService.getActiveRekening(nomorRekening);

            model.addAttribute("active", "deposit");
            model.addAttribute("rekening", r);
            model.addAttribute("rekeningNama",
                    (r.getNamaNasabah() == null ? "" : r.getNamaNasabah()) + " - " + (r.getProduk() == null ? "" : r.getProduk())
            );
            model.addAttribute("form", new DepositForm());
            return "teller/transaction/deposit_form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/deposit";
        }
    }

    // =========================
    // DEPOSIT: PROCESS
    // =========================
    @PostMapping("/deposit/{no}")
    public String depositProcess(@PathVariable("no") String nomorRekening,
                                 @ModelAttribute("form") DepositForm form,
                                 Authentication auth,
                                 RedirectAttributes ra) {

        try {
            String username = (auth == null) ? "-" : auth.getName();

            var result = tellerDepositService.deposit(
                    nomorRekening,
                    form.getJumlahSetoran(),
                    form.getKeterangan(),
                    form.getNoReferensi(),
                    username
            );

            ra.addFlashAttribute("success",
                    "Transaksi berhasil: " + result.nomorTransaksi() + ", Saldo baru: " + result.saldoBaru()
            );
            return "redirect:/teller/transaction/list";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/deposit/" + nomorRekening;
        }
    }

 // =========================
// WITHDRAWAL: STEP 1 (SELECT)
// =========================
@GetMapping("/withdrawal")
public String withdrawalSelect(@RequestParam(required = false) String q,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {
    Pageable pageable = PageRequest.of(page, 10);
    Page<Rekening> result = rekeningRepository.searchActiveForTeller(q, pageable);

    model.addAttribute("active", "withdrawal");
    model.addAttribute("q", q);
    model.addAttribute("page", result);
    return "teller/transaction/withdrawal_select";
}

// =========================
// WITHDRAWAL: STEP 2 (FORM)
// =========================
@GetMapping("/withdrawal/{no}")
public String withdrawalForm(@PathVariable("no") String nomorRekening,
                             Model model,
                             RedirectAttributes ra) {
    try {
        Rekening r = tellerWithdrawalService.getActiveRekening(nomorRekening);

        model.addAttribute("active", "withdrawal");
        model.addAttribute("rekening", r);
        model.addAttribute("rekeningNama",
                (r.getNamaNasabah() == null ? "" : r.getNamaNasabah()) + " - " + (r.getProduk() == null ? "" : r.getProduk())
        );
        model.addAttribute("form", new WithdrawalForm());
        return "teller/transaction/withdrawal_form";
    } catch (Exception e) {
        ra.addFlashAttribute("error", e.getMessage());
        return "redirect:/teller/transaction/withdrawal";
    }
}

// =========================
// WITHDRAWAL: PROCESS
// =========================
@PostMapping("/withdrawal/{no}")
public String withdrawalProcess(@PathVariable("no") String nomorRekening,
                                @ModelAttribute("form") WithdrawalForm form,
                                Authentication auth,
                                RedirectAttributes ra) {
    try {
        String username = (auth == null) ? "-" : auth.getName();

        var result = tellerWithdrawalService.withdraw(
                nomorRekening,
                form.getJumlahPenarikan(),
                form.getKeterangan(),
                form.getNoReferensi(),
                username
        );

        ra.addFlashAttribute("success",
                "Transaksi berhasil: " + result.nomorTransaksi() + ", Saldo baru: " + result.saldoBaru()
        );
        return "redirect:/teller/transaction/list";
    } catch (Exception e) {
        ra.addFlashAttribute("error", e.getMessage());
        return "redirect:/teller/transaction/withdrawal/" + nomorRekening;
    }
}

@Data
public static class WithdrawalForm {
    private BigDecimal jumlahPenarikan;
    private String keterangan = "Penarikan Tunai";
    private String noReferensi;
}


    @GetMapping("/transfer")
    public String transfer(Model model) {
        model.addAttribute("active", "transfer");
        return "teller/transaction/transfer";
    }

    @Data
    public static class DepositForm {
        private BigDecimal jumlahSetoran;
        private String keterangan = "Setoran Tunai";
        private String noReferensi;
    }
}
