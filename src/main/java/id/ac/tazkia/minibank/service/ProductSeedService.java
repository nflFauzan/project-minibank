package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductSeedService {

    private final ProdukTabunganRepository produkRepo;
    private final Environment env;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (!env.acceptsProfiles(Profiles.of("test"))) {
            seedDefaultProducts();
        }
    }

    @Transactional
    public void seedDefaultProducts() {
        // Seed TAB_UTAMA for legacy compatibility
        if (produkRepo.findByKodeProduk("TAB_UTAMA").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("00000000-0000-0000-0000-000000000001");
            p.setKodeProduk("TAB_UTAMA");
            p.setNamaProduk("Tabungan Utama");
            p.setProductType("TABUNGAN_WADIAH");
            p.setProductCategory("Tabungan Syariah");
            p.setJenisAkad("WADIAH");
            p.setDeskripsiSingkat("Tabungan utama minibank");
            p.setSetoranAwalMinimum(new BigDecimal("100000"));
            p.setAktif(true);
            p.setIsDefault(true);
            produkRepo.save(p);
        }

        // 1. TAB001
        if (produkRepo.findByKodeProduk("TAB001").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("aad6cc28-62c3-43d6-bb8b-6bdfb339baea");
            p.setKodeProduk("TAB001");
            p.setNamaProduk("Tabungan Wadiah Basic");
            p.setProductType("TABUNGAN_WADIAH");
            p.setProductCategory("Tabungan Syariah");
            p.setCurrency("IDR");
            p.setJenisAkad("WADIAH");
            p.setDeskripsiSingkat("Tabungan dengan akad wadiah untuk nasabah perorangan");
            p.setAktif(true);
            p.setIsDefault(true);
            p.setAllowOverdraft(false);
            p.setRequireMaintainingBalance(true);
            p.setSetoranAwalMinimum(new BigDecimal("50000.00"));
            p.setMinimumBalance(new BigDecimal("10000.00"));
            p.setProfitSharingRatio(new BigDecimal("0.0275"));
            p.setProfitSharingType("WADIAH");
            p.setProfitDistributionFrequency("MONTHLY");
            p.setIsShariahCompliant(true);
            p.setDailyWithdrawalLimit(new BigDecimal("5000000.00"));
            p.setMonthlyTransactionLimit(50);
            p.setOverdraftLimit(BigDecimal.ZERO);
            p.setMonthlyMaintenanceFee(new BigDecimal("2500.00"));
            p.setAtmWithdrawalFee(new BigDecimal("5000.00"));
            p.setInterBankTransferFee(new BigDecimal("7500.00"));
            p.setFreeTransactionsPerMonth(10);
            p.setExcessTransactionFee(new BigDecimal("2500.00"));
            p.setBelowMinimumBalanceFee(new BigDecimal("10000.00"));
            p.setMinCustomerAge(17);
            p.setAllowedCustomerTypes("PERSONAL");
            p.setRequiredDocuments("KTP, NPWP (optional)");
            p.setLaunchDate(LocalDate.of(2025, 11, 8));
            p.setCreatedAt(LocalDateTime.of(2025, 11, 8, 6, 44));
            p.setCreatedBy("SYSTEM");
            produkRepo.save(p);
        }

        // 2. TAB002
        if (produkRepo.findByKodeProduk("TAB002").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("5a0866c8-300c-4baa-ae0a-f06ba80baf6a");
            p.setKodeProduk("TAB002");
            p.setNamaProduk("Tabungan Mudharabah Premium");
            p.setProductType("TABUNGAN_MUDHARABAH");
            p.setProductCategory("Tabungan Syariah");
            p.setCurrency("IDR");
            p.setJenisAkad("MUDHARABAH");
            p.setDeskripsiSingkat("Tabungan mudharabah dengan nisbah bagi hasil yang menarik");
            p.setAktif(true);
            p.setIsDefault(false);
            p.setAllowOverdraft(false);
            p.setRequireMaintainingBalance(true);
            p.setSetoranAwalMinimum(new BigDecimal("1000000.00"));
            p.setMinimumBalance(new BigDecimal("500000.00"));
            p.setProfitSharingRatio(new BigDecimal("0.0350"));
            p.setNisbahCustomer(new BigDecimal("0.7000"));
            p.setNisbahBank(new BigDecimal("0.3000"));
            p.setProfitSharingType("MUDHARABAH");
            p.setProfitDistributionFrequency("MONTHLY");
            p.setIsShariahCompliant(true);
            p.setDailyWithdrawalLimit(new BigDecimal("10000000.00"));
            p.setMonthlyTransactionLimit(100);
            p.setOverdraftLimit(BigDecimal.ZERO);
            p.setMonthlyMaintenanceFee(BigDecimal.ZERO);
            p.setAtmWithdrawalFee(BigDecimal.ZERO);
            p.setInterBankTransferFee(new BigDecimal("5000.00"));
            p.setFreeTransactionsPerMonth(25);
            p.setExcessTransactionFee(new BigDecimal("2500.00"));
            p.setBelowMinimumBalanceFee(new BigDecimal("25000.00"));
            p.setMinCustomerAge(21);
            p.setAllowedCustomerTypes("PERSONAL");
            p.setRequiredDocuments("KTP, NPWP, Slip Gaji");
            p.setLaunchDate(LocalDate.of(2025, 11, 8));
            p.setCreatedAt(LocalDateTime.of(2025, 11, 8, 6, 44));
            p.setCreatedBy("SYSTEM");
            produkRepo.save(p);
        }

        // 3. DEP001
        if (produkRepo.findByKodeProduk("DEP001").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("75d4e53a-978e-4db1-820c-4eaef76fd885");
            p.setKodeProduk("DEP001");
            p.setNamaProduk("Deposito Mudharabah");
            p.setProductType("DEPOSITO_MUDHARABAH");
            p.setProductCategory("Deposito Syariah");
            p.setCurrency("IDR");
            p.setJenisAkad("MUDHARABAH");
            p.setDeskripsiSingkat("Deposito berjangka dengan akad mudharabah");
            p.setAktif(true);
            p.setIsDefault(false);
            p.setAllowOverdraft(true);
            p.setRequireMaintainingBalance(true);
            p.setSetoranAwalMinimum(new BigDecimal("100000.00"));
            p.setMinimumBalance(new BigDecimal("50000.00"));
            p.setProfitSharingRatio(new BigDecimal("0.0100"));
            p.setNisbahCustomer(new BigDecimal("0.7000"));
            p.setNisbahBank(new BigDecimal("0.3000"));
            p.setProfitSharingType("MUDHARABAH");
            p.setProfitDistributionFrequency("ON_MATURITY");
            p.setIsShariahCompliant(true);
            p.setDailyWithdrawalLimit(new BigDecimal("20000000.00"));
            p.setMonthlyTransactionLimit(100);
            p.setOverdraftLimit(BigDecimal.ZERO);
            p.setMonthlyMaintenanceFee(new BigDecimal("5000.00"));
            p.setAtmWithdrawalFee(new BigDecimal("5000.00"));
            p.setInterBankTransferFee(new BigDecimal("7500.00"));
            p.setFreeTransactionsPerMonth(20);
            p.setExcessTransactionFee(new BigDecimal("3000.00"));
            p.setBelowMinimumBalanceFee(new BigDecimal("15000.00"));
            p.setMinCustomerAge(18);
            p.setAllowedCustomerTypes("PERSONAL");
            p.setRequiredDocuments("KTP, NPWP, Slip Gaji");
            p.setLaunchDate(LocalDate.of(2025, 11, 8));
            p.setCreatedAt(LocalDateTime.of(2025, 11, 8, 6, 44));
            p.setCreatedBy("SYSTEM");
            produkRepo.save(p);
        }

        // 4. PEM001
        if (produkRepo.findByKodeProduk("PEM001").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("67d240f1-1eea-420e-8cd9-30e7b9b773e3");
            p.setKodeProduk("PEM001");
            p.setNamaProduk("Pembiayaan Murabahah");
            p.setProductType("PEMBIAYAAN_MURABAHAH");
            p.setProductCategory("Pembiayaan Syariah");
            p.setCurrency("IDR");
            p.setJenisAkad("MURABAHAH");
            p.setDeskripsiSingkat("Pembiayaan dengan akad murabahah untuk kebutuhan konsumtif");
            p.setAktif(true);
            p.setIsDefault(false);
            p.setAllowOverdraft(false);
            p.setRequireMaintainingBalance(true);
            p.setSetoranAwalMinimum(new BigDecimal("5000000.00"));
            p.setMinimumBalance(new BigDecimal("1000000.00"));
            p.setProfitSharingRatio(new BigDecimal("0.0300"));
            p.setProfitSharingType("MURABAHAH");
            p.setProfitDistributionFrequency("MONTHLY");
            p.setIsShariahCompliant(true);
            p.setDailyWithdrawalLimit(new BigDecimal("50000000.00"));
            p.setMonthlyTransactionLimit(200);
            p.setOverdraftLimit(BigDecimal.ZERO);
            p.setMonthlyMaintenanceFee(new BigDecimal("15000.00"));
            p.setAtmWithdrawalFee(new BigDecimal("5000.00"));
            p.setInterBankTransferFee(new BigDecimal("5000.00"));
            p.setFreeTransactionsPerMonth(50);
            p.setExcessTransactionFee(new BigDecimal("5000.00"));
            p.setBelowMinimumBalanceFee(new BigDecimal("50000.00"));
            p.setAllowedCustomerTypes("CORPORATE");
            p.setRequiredDocuments("Akta Pendirian, SIUP, TDP, NPWP");
            p.setLaunchDate(LocalDate.of(2025, 11, 8));
            p.setCreatedAt(LocalDateTime.of(2025, 11, 8, 6, 44));
            p.setCreatedBy("SYSTEM");
            produkRepo.save(p);
        }

        // 5. PEM002
        if (produkRepo.findByKodeProduk("PEM002").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("61382207-742f-446b-b8ae-cbe0ecbbdba2");
            p.setKodeProduk("PEM002");
            p.setNamaProduk("Pembiayaan Musharakah");
            p.setProductType("PEMBIAYAAN_MUSHARAKAH");
            p.setProductCategory("Pembiayaan Syariah");
            p.setCurrency("IDR");
            p.setJenisAkad("MUSHARAKAH");
            p.setDeskripsiSingkat("Pembiayaan dengan akad musharakah untuk modal usaha");
            p.setAktif(true);
            p.setIsDefault(false);
            p.setAllowOverdraft(true);
            p.setRequireMaintainingBalance(true);
            p.setSetoranAwalMinimum(new BigDecimal("2000000.00"));
            p.setMinimumBalance(new BigDecimal("1000000.00"));
            p.setProfitSharingRatio(new BigDecimal("0.0150"));
            p.setNisbahCustomer(new BigDecimal("0.6000"));
            p.setNisbahBank(new BigDecimal("0.4000"));
            p.setProfitSharingType("MUSHARAKAH");
            p.setProfitDistributionFrequency("QUARTERLY");
            p.setIsShariahCompliant(true);
            p.setDailyWithdrawalLimit(new BigDecimal("50000000.00"));
            p.setMonthlyTransactionLimit(200);
            p.setOverdraftLimit(BigDecimal.ZERO);
            p.setMonthlyMaintenanceFee(BigDecimal.ZERO);
            p.setAtmWithdrawalFee(BigDecimal.ZERO);
            p.setInterBankTransferFee(new BigDecimal("5000.00"));
            p.setFreeTransactionsPerMonth(50);
            p.setExcessTransactionFee(new BigDecimal("3000.00"));
            p.setBelowMinimumBalanceFee(new BigDecimal("25000.00"));
            p.setMinCustomerAge(25);
            p.setAllowedCustomerTypes("PERSONAL");
            p.setRequiredDocuments("KTP, NPWP, Slip Gaji, Rekening Koran");
            p.setLaunchDate(LocalDate.of(2025, 11, 8));
            p.setCreatedAt(LocalDateTime.of(2025, 11, 8, 6, 44));
            p.setCreatedBy("SYSTEM");
            produkRepo.save(p);
        }

        // 6. GIR001
        if (produkRepo.findByKodeProduk("GIR001").isEmpty()) {
            ProdukTabungan p = new ProdukTabungan();
            p.setUuid("505d05a8-a448-44a7-a43a-5c8060c1fdfa");
            p.setKodeProduk("GIR001");
            p.setNamaProduk("Giro Wadiah Corporate");
            p.setProductType("TABUNGAN_WADIAH");
            p.setProductCategory("Giro Syariah");
            p.setCurrency("IDR");
            p.setJenisAkad("WADIAH");
            p.setDeskripsiSingkat("Rekening giro dengan akad wadiah untuk kebutuhan operasional perusahaan");
            p.setAktif(true);
            p.setIsDefault(false);
            p.setAllowOverdraft(false);
            p.setRequireMaintainingBalance(true);
            p.setSetoranAwalMinimum(new BigDecimal("1000000.00"));
            p.setMinimumBalance(new BigDecimal("500000.00"));
            p.setProfitSharingRatio(new BigDecimal("0.0250"));
            p.setProfitSharingType("WADIAH");
            p.setProfitDistributionFrequency("MONTHLY");
            p.setIsShariahCompliant(true);
            p.setDailyWithdrawalLimit(new BigDecimal("50000000.00"));
            p.setMonthlyTransactionLimit(500);
            p.setOverdraftLimit(BigDecimal.ZERO);
            p.setMonthlyMaintenanceFee(new BigDecimal("15000.00"));
            p.setAtmWithdrawalFee(new BigDecimal("2500.00"));
            p.setInterBankTransferFee(new BigDecimal("5000.00"));
            p.setFreeTransactionsPerMonth(50);
            p.setExcessTransactionFee(new BigDecimal("5000.00"));
            p.setBelowMinimumBalanceFee(new BigDecimal("25000.00"));
            p.setAllowedCustomerTypes("CORPORATE");
            p.setRequiredDocuments("Akta Pendirian, SIUP, TDP, NPWP, Surat Kuasa");
            p.setLaunchDate(LocalDate.of(2025, 11, 8));
            p.setCreatedAt(LocalDateTime.of(2025, 11, 8, 6, 44));
            p.setCreatedBy("SYSTEM");
            produkRepo.save(p);
        }
    }
}
