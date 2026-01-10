package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.service.TellerDepositService;
import id.ac.tazkia.minibank.service.TellerWithdrawalService;
import id.ac.tazkia.minibank.service.TellerTransferService;
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
    private final TellerTransferService tellerTransferService;

    // ================= TRANSACTION LIST =================
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) TipeTransaksi type,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("processedAt").descending());
        Page<Transaksi> result = transaksiRepository.search(q, type, pageable);

        model.addAttribute("active", "transaction");
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        model.addAttribute("type", type);
        return "teller/transaction/list";
    }

    // ================= DEPOSIT =================
    @GetMapping("/deposit")
    public String depositSelect(@RequestParam(required = false) String q,
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rekening> result = rekeningRepository.searchActiveForTeller(q, pageable);

        model.addAttribute("active", "deposit");
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        return "teller/transaction/deposit_select";
    }

    @GetMapping("/deposit/{no}")
    public String depositForm(@PathVariable String no,
                              Model model,
                              RedirectAttributes ra) {
        try {
            Rekening r = tellerDepositService.getActiveRekening(no);
            model.addAttribute("active", "deposit");
            model.addAttribute("rekening", r);
            model.addAttribute("form", new DepositForm());
            return "teller/transaction/deposit_form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/deposit";
        }
    }

    @PostMapping("/deposit/{no}")
    public String depositProcess(@PathVariable String no,
                                 @ModelAttribute("form") DepositForm form,
                                 Authentication auth,
                                 RedirectAttributes ra) {
        try {
            String username = auth == null ? "-" : auth.getName();
            var result = tellerDepositService.deposit(
                    no,
                    form.getJumlahSetoran(),
                    form.getKeterangan(),
                    form.getNoReferensi(),
                    username
            );
            ra.addFlashAttribute("success", "Deposit berhasil: " + result.nomorTransaksi());
            return "redirect:/teller/transaction/list";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/deposit/" + no;
        }
    }

    // ================= WITHDRAWAL =================
    @GetMapping("/withdrawal")
    public String withdrawalSelect(@RequestParam(required = false) String q,
                                   @RequestParam(defaultValue = "0") int page,
                                   Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rekening> result = rekeningRepository.searchActiveForTeller(q, pageable);

        model.addAttribute("active", "withdrawal");
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        return "teller/transaction/withdrawal_select";
    }

    @GetMapping("/withdrawal/{no}")
    public String withdrawalForm(@PathVariable String no,
                                 Model model,
                                 RedirectAttributes ra) {
        try {
            Rekening r = tellerWithdrawalService.getActiveRekening(no);
            model.addAttribute("active", "withdrawal");
            model.addAttribute("rekening", r);
            model.addAttribute("form", new WithdrawalForm());
            return "teller/transaction/withdrawal_form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/withdrawal";
        }
    }

    @PostMapping("/withdrawal/{no}")
    public String withdrawalProcess(@PathVariable String no,
                                    @ModelAttribute("form") WithdrawalForm form,
                                    Authentication auth,
                                    RedirectAttributes ra) {
        try {
            String username = auth == null ? "-" : auth.getName();
            var result = tellerWithdrawalService.withdraw(
                    no,
                    form.getJumlahPenarikan(),
                    form.getKeterangan(),
                    form.getNoReferensi(),
                    username
            );
            ra.addFlashAttribute("success", "Withdrawal berhasil: " + result.nomorTransaksi());
            return "redirect:/teller/transaction/list";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/withdrawal/" + no;
        }
    }

    // ================= TRANSFER STEP 1 =================
    @GetMapping("/transfer")
    public String transferSelectSource(@RequestParam(required = false) String q,
                                       @RequestParam(defaultValue = "0") int page,
                                       Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rekening> result = rekeningRepository.searchActiveForTeller(q, pageable);

        model.addAttribute("active", "transfer");
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        return "teller/transaction/transfer_select_source";
    }

    // ================= TRANSFER STEP 2 =================
    @GetMapping("/transfer/{sourceNo}")
    public String transferSelectTarget(@PathVariable String sourceNo,
                                       @RequestParam(required = false) String q,
                                       @RequestParam(defaultValue = "0") int page,
                                       Model model) {

        Rekening source = rekeningRepository.findByNomorRekening(sourceNo)
                .orElseThrow(() -> new EntityNotFoundException("Rekening sumber tidak ditemukan"));

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rekening> result =
                rekeningRepository.searchActiveForTellerExclude(sourceNo, q, pageable);

        model.addAttribute("active", "transfer");
        model.addAttribute("sourceRekening", source);
        model.addAttribute("page", result);
        model.addAttribute("q", q);
        return "teller/transaction/transfer_select_target";
    }

    // ================= TRANSFER STEP 3 =================
    @GetMapping("/transfer/{sourceNo}/{targetNo}")
    public String transferForm(@PathVariable String sourceNo,
                               @PathVariable String targetNo,
                               Model model) {

        Rekening source = rekeningRepository.findByNomorRekening(sourceNo)
                .orElseThrow(() -> new EntityNotFoundException("Rekening sumber tidak ditemukan"));

        Rekening target = rekeningRepository.findByNomorRekening(targetNo)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tujuan tidak ditemukan"));

        model.addAttribute("active", "transfer");
        model.addAttribute("sourceRekening", source);
        model.addAttribute("targetRekening", target);
        model.addAttribute("form", new TransferForm());
        return "teller/transaction/transfer_form";
    }

    // ================= TRANSFER PROCESS =================
    @PostMapping("/transfer/{sourceNo}/{targetNo}")
    public String transferProcess(@PathVariable String sourceNo,
                                  @PathVariable String targetNo,
                                  @ModelAttribute("form") TransferForm form,
                                  Authentication auth,
                                  RedirectAttributes ra) {

        try {
            String username = auth == null ? "-" : auth.getName();
            UUID groupId = tellerTransferService.transfer(
                    sourceNo,
                    targetNo,
                    form.getJumlah(),
                    form.getKeteranganTambahan(),
                    form.getNoReferensi(),
                    username
            );
            ra.addFlashAttribute("success", "Transfer berhasil. Group ID: " + groupId);
            return "redirect:/teller/transaction/list";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teller/transaction/transfer/" + sourceNo + "/" + targetNo;
        }
    }

    // ================= DTO =================
    @Data
    public static class DepositForm {
        private BigDecimal jumlahSetoran;
        private String keterangan = "Setoran Tunai";
        private String noReferensi;
    }

    @Data
    public static class WithdrawalForm {
        private BigDecimal jumlahPenarikan;
        private String keterangan = "Penarikan Tunai";
        private String noReferensi;
    }

    @Data
    public static class TransferForm {
        private BigDecimal jumlah;
        private String keteranganTambahan;
        private String noReferensi;
    }
}