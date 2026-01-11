package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.DashboardSummaryDto;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final NasabahRepository nasabahRepository;
    private final RekeningRepository rekeningRepository;
    private final ProdukTabunganRepository produkTabunganRepository;

    public DashboardService(
            NasabahRepository nasabahRepository,
            RekeningRepository rekeningRepository,
            ProdukTabunganRepository produkTabunganRepository
    ) {
        this.nasabahRepository = nasabahRepository;
        this.rekeningRepository = rekeningRepository;
        this.produkTabunganRepository = produkTabunganRepository;
    }

    public DashboardSummaryDto getSummary() {
        DashboardSummaryDto dto = new DashboardSummaryDto();

        dto.setTotalNasabah(
                nasabahRepository.countByStatus(NasabahStatus.ACTIVE)
        );

        dto.setTotalRekening(
                rekeningRepository.countByStatusActive(true)
        );

        dto.setTotalProduk(
                produkTabunganRepository.count()
        );

        var nasabahTerbaru = nasabahRepository
                .findTop5ByStatusOrderByCreatedAtDesc(NasabahStatus.ACTIVE)
                .stream()
                .map(n -> {
                    DashboardSummaryDto.NasabahSummary s =
                            new DashboardSummaryDto.NasabahSummary();
                    s.setId(n.getId());
                    s.setNamaLengkap(n.getNamaSesuaiIdentitas());
                    return s;
                })
                .toList();

        dto.setNasabahTerbaru(nasabahTerbaru);
        return dto;
    }

    public List<ProdukTabungan> getActiveProdukTabungan() {
        return produkTabunganRepository.findActiveProducts();
    }
}