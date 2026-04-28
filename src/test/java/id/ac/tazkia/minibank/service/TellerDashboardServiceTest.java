package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TellerDashboardService Unit Tests")
class TellerDashboardServiceTest {

    @Mock private NasabahRepository nasabahRepository;
    @Mock private RekeningRepository rekeningRepository;
    @Mock private ProdukTabunganRepository produkTabunganRepository;
    @Mock private TransaksiRepository transaksiRepository;

    @InjectMocks
    private TellerDashboardService service;

    @Test
    void totalNasabahAktif() {
        when(nasabahRepository.countByStatus(NasabahStatus.ACTIVE)).thenReturn(10L);
        assertEquals(10L, service.totalNasabahAktif());
    }

    @Test
    void totalRekeningAktif() {
        when(rekeningRepository.countByStatusActive(true)).thenReturn(5L);
        assertEquals(5L, service.totalRekeningAktif());
    }

    @Test
    void totalDepositAwal() {
        when(rekeningRepository.sumNominalSetoranAwalActive()).thenReturn(new BigDecimal("5000000"));
        assertEquals(new BigDecimal("5000000"), service.totalDepositAwal());
    }

    @Test
    void totalTransaksi() {
        when(transaksiRepository.countByChannel("TELLER")).thenReturn(20L);
        assertEquals(20L, service.totalTransaksi());
    }

    @Test
    void produkAktif() {
        ProdukTabungan p = new ProdukTabungan();
        when(produkTabunganRepository.findActiveProducts()).thenReturn(List.of(p));
        assertEquals(1, service.produkAktif().size());
    }
}
