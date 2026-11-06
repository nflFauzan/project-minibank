package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RekeningService {
    
    private final RekeningRepository rekeningRepository;
    private final NasabahRepository nasabahRepository;
    private final ProdukTabunganRepository produkTabunganRepository;
    private final UserRepository userRepository;
    private final id.ac.tazkia.minibank.repository.TransaksiRepository transaksiRepository;

    @Data
    public static class OpenAccountForm {
        private Long produkId;
        private String namaRekening;
        private BigDecimal nominalSetoranAwal;
        private String tujuanPembukaan;
    }

    @Transactional(readOnly = true)
    public List<Rekening> listAccounts(String search, String status) {
        if (search != null && !search.isBlank()) {
            return rekeningRepository.search(search, status);
        }
        return rekeningRepository.findByStatus(status);
    }

@Transactional(readOnly = true)
public List<Nasabah> listEligibleCustomers(NasabahStatus status, String q) {
    if (q != null && !q.isBlank()) {
        return nasabahRepository.searchActiveForAccountOpen(status, q);
    }
   return nasabahRepository.findByStatus(NasabahStatus.ACTIVE);

}

    @Transactional(readOnly = true)
    public Nasabah getNasabahActiveById(Long id) {
        Nasabah n = nasabahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nasabah tidak ditemukan."));
        if (n.getStatus() != NasabahStatus.ACTIVE) {
            throw new EntityNotFoundException("Nasabah belum ACTIVE, tidak bisa buka rekening.");
        }
        return n;
    }

    @Transactional(readOnly = true)
    public List<ProdukTabungan> listActiveProducts() {
        return produkTabunganRepository.findActiveProducts();
    }

    @Transactional(readOnly = true)
    public Rekening getAccountById(Long id) {
        return rekeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan."));
    }

    @Transactional(readOnly = true)
    public Rekening getAccountByIdOrNumber(String idOrNumber) {
        try {
            Long id = Long.parseLong(idOrNumber);
            return rekeningRepository.findById(id)
                    .orElseGet(() -> rekeningRepository.findByNomorRekening(idOrNumber)
                            .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan: " + idOrNumber)));
        } catch (NumberFormatException e) {
            return rekeningRepository.findByNomorRekening(idOrNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Rekening tidak ditemukan: " + idOrNumber));
        }
    }

    @Transactional(readOnly = true)
    public List<id.ac.tazkia.minibank.entity.Transaksi> getTransactions(Rekening rekening, LocalDate startDate, LocalDate endDate, String type) {
        List<id.ac.tazkia.minibank.entity.Transaksi> list;
        if (startDate != null && endDate != null) {
            list = transaksiRepository.findByRekeningAndProcessedAtBetweenOrderByProcessedAtAsc(
                    rekening, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        } else if (startDate != null) {
            list = transaksiRepository.findByRekeningAndProcessedAtGreaterThanEqualOrderByProcessedAtAsc(
                    rekening, startDate.atStartOfDay());
        } else if (endDate != null) {
            list = transaksiRepository.findByRekeningAndProcessedAtLessThanEqualOrderByProcessedAtAsc(
                    rekening, endDate.atTime(23, 59, 59));
        } else {
            list = transaksiRepository.findByRekeningOrderByProcessedAtAsc(rekening);
        }

        if (type != null && !type.isBlank() && !type.equalsIgnoreCase("ALL")) {
            list = list.stream()
                    .filter(t -> t.getTipe() != null && t.getTipe().name().equalsIgnoreCase(type))
                    .toList();
        }
        return list;
    }

    @Transactional
    public Rekening openAccount(Long nasabahId, Long produkId, String namaRekening, BigDecimal setoranAwal, String tujuan) {

        Nasabah n = getNasabahActiveById(nasabahId);

        ProdukTabungan produk = produkTabunganRepository.findById(produkId)
                .orElseThrow(() -> new EntityNotFoundException("Product tidak ditemukan."));

        if (produk.getAktif() == null || !produk.getAktif()) {
            throw new IllegalStateException("Product tidak aktif.");
        }

        // nomor rekening: 543 + no_urut(6) + kode_produk(2) => kamu minta 01 dll
        String kodeProduk2 = produk.getKodeProduk();
        if (kodeProduk2 == null) throw new IllegalStateException("Kode produk null.");
        // kalau di DB kamu simpan "01" langsung, aman. kalau "TAB_WAD" dll, kamu harus mapping sendiri.
        // sementara: ambil 2 char terakhir kalau panjang > 2
        if (kodeProduk2.length() > 2) {
            kodeProduk2 = kodeProduk2.substring(kodeProduk2.length() - 2);
        }

        String noUrut6 = rekeningRepository.nextSequence6();

        Rekening r = new Rekening();
        r.setNasabah(n);
        r.setNomorRekening("543" + noUrut6 + kodeProduk2);

        r.setStatusActive(true);
        r.setCifNasabah(n.getCif());
        r.setNik(n.getNik());
        r.setNamaNasabah(n.getNamaSesuaiIdentitas());

        String accountName = (namaRekening != null && !namaRekening.isBlank())
                ? namaRekening
                : n.getNamaSesuaiIdentitas() + " - Personal Savings";
        r.setNamaRekening(accountName);

        r.setEmail(n.getEmail());
        r.setNomorTelepon(n.getNoHp());
        r.setAlamatDomisili(n.getAlamatDomisili());

        r.setProduk(produk.getNamaProduk());
        r.setNominalSetoranAwal(setoranAwal != null ? setoranAwal : BigDecimal.ZERO);
        r.setTujuanPembukaan((tujuan != null && !tujuan.isBlank()) ? tujuan : accountName);
        r.setTanggalPembukaan(LocalDate.now());

        r.setCabangPembukaan("Cabang Bandung");
        r.setPetugasCs(currentUserFullNameOrUsername());

        return rekeningRepository.save(r);
    }

    @Transactional
    public Rekening openAccount(Long nasabahId, Long produkId, BigDecimal setoranAwal, String tujuan) {
        return openAccount(nasabahId, produkId, null, setoranAwal, tujuan);
    }

    @Transactional
    public void closeAccount(Long rekeningId, String reason) {
        Rekening r = getAccountById(rekeningId);
        r.setStatusActive(false);
        if (reason != null && !reason.isBlank()) {
            r.setReasonForClosure(reason);
        }
        rekeningRepository.save(r);
    }

    @Transactional
    public void closeAccount(Long rekeningId) {
        closeAccount(rekeningId, null);
    }

    private String currentUserFullNameOrUsername() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) return "UNKNOWN";

    String username = auth.getName(); // ini pasti ada (karena login pakai username)
    return userRepository.findByUsername(username)
            .map(u -> (u.getFullName() != null && !u.getFullName().isBlank()) ? u.getFullName() : u.getUsername())
            .orElse(username);
}

}
