package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.DashboardSummaryDto;
import id.ac.tazkia.minibank.entity.Product;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private NasabahRepository nasabahRepository;

    @Autowired
    private RekeningRepository rekeningRepository;

    @Autowired
    private ProductRepository productRepository;

    public DashboardSummaryDto getSummary() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        dto.setTotalNasabah(nasabahRepository.count());
        dto.setTotalRekening(rekeningRepository.countByStatusActive(true));
        dto.setTotalProduk(productRepository.count());

        var recent = nasabahRepository.findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(n -> {
                    DashboardSummaryDto.NasabahSummary s = new DashboardSummaryDto.NasabahSummary();
                    s.setId(n.getId());
                    s.setNama(n.getNamaSesuaiIdentitas());
                    return s;
                })
                .collect(Collectors.toList());
        dto.setNasabahTerbaru(recent);
        return dto;
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByStatusActiveTrue();
    }
}
