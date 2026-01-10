package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.TellerTransferService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/teller/transfer")
@RequiredArgsConstructor
public class TellerTransferController {

    private final TellerTransferService tellerTransferService;

    /**
     * STEP 1
     * Page list rekening + search
     * (UI menyusul, controller sudah siap)
     */
    @GetMapping
    public String listRekening() {
        return "teller/transfer/list"; 
    }

    /**
     * STEP 2
     * Form transfer (sumber → tujuan)
     */
    @GetMapping("/form")
    public String formTransfer(
            @RequestParam("sumber") String rekeningSumber,
            Model model
    ) {
        model.addAttribute("rekeningSumber", rekeningSumber);
        return "teller/transfer/form";
    }

    /**
     * STEP 3
     * Submit transfer
     */
    @PostMapping
    public String submitTransfer(
            @RequestParam String rekeningSumber,
            @RequestParam String rekeningTujuan,
            @RequestParam BigDecimal jumlah,
            @RequestParam(required = false) String keterangan,
            @RequestParam(required = false) String noReferensi,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        try {
            UUID groupId = tellerTransferService.transfer(
                    rekeningSumber,
                    rekeningTujuan,
                    jumlah,
                    keterangan,
                    noReferensi,
                    principal.getName()
            );

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Transfer berhasil. ID: " + groupId
            );

            return "redirect:/teller/transaction/" + groupId;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
            return "redirect:/teller/transfer/form?sumber=" + rekeningSumber;
        }
    }
}