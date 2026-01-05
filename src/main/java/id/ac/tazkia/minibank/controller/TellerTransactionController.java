package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teller/transaction")
public class TellerTransactionController {

    private final TransaksiRepository transaksiRepository;

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) TipeTransaksi type,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "processedAt"));
        Page<Transaksi> result = transaksiRepository.search(q, type, pageable);

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
        model.addAttribute("tx", t);
        model.addAttribute("groupTx", group);

        return "teller/transaction/view";
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("active", "deposit");
        return "teller/transaction/deposit";
    }

    @GetMapping("/withdrawal")
    public String withdrawal(Model model) {
        model.addAttribute("active", "withdrawal");
        return "teller/transaction/withdrawal";
    }

    @GetMapping("/transfer")
    public String transfer(Model model) {
        model.addAttribute("active", "transfer");
        return "teller/transaction/transfer";
    }
}
