package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TellerDashboardService {

    private final NasabahRepository nasabahRepository;
    private final RekeningRepository rekeningRepository;
    private final ProdukTabunganRepository produkTabunganRepository;

    @Transactional(readOnly = true)
    public long totalNasabahAktif() {
        return nasabahRepository.countByStatus(NasabahStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public long totalRekeningAktif() {
    return rekeningRepository.countByStatusActive(true);
}

    @Transactional(readOnly = true)
    public BigDecimal totalDepositAwal() {
        // sesuai pilihan kamu: sinkronkan saldo awal dengan nominal_setoran_awal
        return rekeningRepository.sumNominalSetoranAwalActive();
    }

    @Transactional(readOnly = true)
    public long totalTransaksi() {
        // belum ada tabel transaksi -> 0 dulu
        return 0L;
    }

    @Transactional(readOnly = true)
    public List<ProdukTabungan> produkAktif() {
        return produkTabunganRepository.findActiveProducts();
    }
}
