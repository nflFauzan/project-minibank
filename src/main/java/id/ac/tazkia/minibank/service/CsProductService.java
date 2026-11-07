package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.ProductFormDto;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CsProductService {

    private final ProdukTabunganRepository produkTabunganRepository;
    private final EntityManager em;

    private static final BigDecimal MAX_SETORAN = new BigDecimal("1000000000");

    public List<AkadOption> akadOptions() {
        return List.of(
                new AkadOption("WADIAH", "Wadiah"),
                new AkadOption("MUDHARABAH", "Mudharabah"),
                new AkadOption("MUSYARAKAH", "Musyarakah"),
                new AkadOption("MURABAHAH", "Murabahah"),
                new AkadOption("SALAM", "Salam"),
                new AkadOption("ISTISNA", "Istisna’"),
                new AkadOption("IJARAH", "Ijarah"),
                new AkadOption("QARDH", "Qardh")
        );
    }

    public List<String> productTypeOptions() {
        return List.of(
                "SAVINGS",
                "CHECKING",
                "DEPOSIT",
                "TABUNGAN_WADIAH",
                "TABUNGAN_MUDHARABAH",
                "DEPOSITO_MUDHARABAH",
                "PEMBIAYAAN_MURABAHAH",
                "PEMBIAYAAN_MUDHARABAH",
                "PEMBIAYAAN_MUSHARAKAH",
                "PEMBIAYAAN_IJARAH",
                "PEMBIAYAAN_SALAM",
                "PEMBIAYAAN_ISTISNA"
        );
    }

    public List<String> categoryOptions() {
        return List.of(
                "Tabungan Syariah",
                "Deposito Syariah",
                "Pembiayaan Syariah",
                "Giro Syariah"
        );
    }

    public List<String> profitSharingTypeOptions() {
        return List.of(
                "MUDHARABAH",
                "MUSHARAKAH",
                "WADIAH",
                "MURABAHAH",
                "IJARAH",
                "SALAM",
                "ISTISNA"
        );
    }

    public List<String> profitDistributionFrequencyOptions() {
        return List.of(
                "DAILY",
                "MONTHLY",
                "QUARTERLY",
                "ANNUALLY",
                "ON_MATURITY"
        );
    }

    public ProdukTabungan findByIdOrUuid(String idOrUuid) {
        return produkTabunganRepository.findByIdOrUuid(idOrUuid)
                .orElseThrow(() -> new EntityNotFoundException("Produk tidak ditemukan: " + idOrUuid));
    }

    public Page<ProdukTabungan> search(String q, Boolean aktif, Pageable pageable) {
        return search(q, null, null, aktif, pageable);
    }

    public Page<ProdukTabungan> search(String q, String productType, String category, Boolean aktif, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // data query
        CriteriaQuery<ProdukTabungan> cq = cb.createQuery(ProdukTabungan.class);
        Root<ProdukTabungan> root = cq.from(ProdukTabungan.class);

        List<Predicate> preds = new ArrayList<>();
        if (aktif != null) {
            preds.add(cb.equal(root.get("aktif"), aktif));
        }

        if (productType != null && !productType.trim().isBlank()) {
            preds.add(cb.equal(root.get("productType"), productType.trim()));
        }

        if (category != null && !category.trim().isBlank()) {
            preds.add(cb.equal(root.get("productCategory"), category.trim()));
        }

        if (q != null && !q.trim().isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            Predicate p1 = cb.like(cb.lower(root.get("kodeProduk")), like);
            Predicate p2 = cb.like(cb.lower(root.get("namaProduk")), like);
            Predicate p3 = cb.like(cb.lower(root.get("deskripsiSingkat")), like);
            Predicate p4 = cb.like(cb.lower(root.get("jenisAkad")), like);
            Predicate p5 = cb.like(cb.lower(root.get("productType")), like);
            Predicate p6 = cb.like(cb.lower(root.get("productCategory")), like);
            preds.add(cb.or(p1, p2, p3, p4, p5, p6));
        }

        cq.where(preds.toArray(new Predicate[0]));

        // Sorting
        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order o : pageable.getSort()) {
                String prop = o.getProperty();
                // Map frontend sort property names to entity field names
                String entityProp = switch (prop) {
                    case "productCode" -> "kodeProduk";
                    case "productName" -> "namaProduk";
                    case "productType" -> "productType";
                    case "category" -> "productCategory";
                    case "profitSharing" -> "profitSharingRatio";
                    case "status" -> "aktif";
                    default -> prop;
                };
                if (o.isAscending()) {
                    orders.add(cb.asc(root.get(entityProp)));
                } else {
                    orders.add(cb.desc(root.get(entityProp)));
                }
            }
            cq.orderBy(orders);
        } else {
            cq.orderBy(cb.asc(root.get("namaProduk")));
        }

        TypedQuery<ProdukTabungan> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<ProdukTabungan> content = query.getResultList();

        // count query
        CriteriaQuery<Long> countQ = cb.createQuery(Long.class);
        Root<ProdukTabungan> countRoot = countQ.from(ProdukTabungan.class);
        List<Predicate> countPreds = new ArrayList<>();

        if (aktif != null) {
            countPreds.add(cb.equal(countRoot.get("aktif"), aktif));
        }
        if (productType != null && !productType.trim().isBlank()) {
            countPreds.add(cb.equal(countRoot.get("productType"), productType.trim()));
        }
        if (category != null && !category.trim().isBlank()) {
            countPreds.add(cb.equal(countRoot.get("productCategory"), category.trim()));
        }
        if (q != null && !q.trim().isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            Predicate p1 = cb.like(cb.lower(countRoot.get("kodeProduk")), like);
            Predicate p2 = cb.like(cb.lower(countRoot.get("namaProduk")), like);
            Predicate p3 = cb.like(cb.lower(countRoot.get("deskripsiSingkat")), like);
            Predicate p4 = cb.like(cb.lower(countRoot.get("jenisAkad")), like);
            Predicate p5 = cb.like(cb.lower(countRoot.get("productType")), like);
            Predicate p6 = cb.like(cb.lower(countRoot.get("productCategory")), like);
            countPreds.add(cb.or(p1, p2, p3, p4, p5, p6));
        }

        countQ.select(cb.count(countRoot));
        countQ.where(countPreds.toArray(new Predicate[0]));
        long total = em.createQuery(countQ).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    @Transactional
    public ProdukTabungan create(ProductFormDto dto) {
        String kode = normalizeKode(dto.resolveCode());
        String name = dto.resolveName();
        String desc = dto.resolveDescription();
        String akad = normalizeAkad(dto.resolveAkad());
        BigDecimal minDeposit = dto.resolveOpeningBalance();

        validate(kode, name, desc, akad, minDeposit, null);

        ProdukTabungan p = new ProdukTabungan();
        mapDtoToEntity(dto, p);
        p.setKodeProduk(kode);
        p.setNamaProduk(name);
        p.setDeskripsiSingkat(desc);
        p.setJenisAkad(akad);
        p.setSetoranAwalMinimum(minDeposit);
        p.setAktif(dto.resolveActive());
        p.setCreatedAt(LocalDateTime.now());
        p.setCreatedBy("admin");

        return produkTabunganRepository.save(p);
    }

    @Transactional
    public ProdukTabungan update(String idOrUuid, ProductFormDto dto) {
        ProdukTabungan p = findByIdOrUuid(idOrUuid);

        String kode = normalizeKode(dto.resolveCode());
        String name = dto.resolveName();
        String desc = dto.resolveDescription();
        String akad = normalizeAkad(dto.resolveAkad());
        BigDecimal minDeposit = dto.resolveOpeningBalance();

        validate(kode, name, desc, akad, minDeposit, p.getId());

        mapDtoToEntity(dto, p);
        p.setKodeProduk(kode);
        p.setNamaProduk(name);
        p.setDeskripsiSingkat(desc);
        p.setJenisAkad(akad);
        p.setSetoranAwalMinimum(minDeposit);
        if (dto.getIsActive() != null) p.setAktif(dto.getIsActive());
        p.setUpdatedAt(LocalDateTime.now());
        p.setUpdatedBy("admin");

        return produkTabunganRepository.save(p);
    }

    @Transactional
    public ProdukTabungan create(String kodeProduk,
                                 String namaProduk,
                                 String deskripsiSingkat,
                                 String jenisAkad,
                                 BigDecimal setoranAwalMinimum) {

        String kode = normalizeKode(kodeProduk);
        String akad = normalizeAkad(jenisAkad);

        validate(kode, namaProduk, deskripsiSingkat, akad, setoranAwalMinimum, null);

        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk(kode);
        p.setNamaProduk(namaProduk.trim());
        p.setDeskripsiSingkat(deskripsiSingkat == null ? null : deskripsiSingkat.trim());
        p.setJenisAkad(akad);
        p.setProductType("TABUNGAN_" + akad);
        p.setProductCategory("Tabungan Syariah");
        p.setProfitSharingType(akad);
        p.setSetoranAwalMinimum(setoranAwalMinimum);
        p.setAktif(true);
        p.setCreatedAt(LocalDateTime.now());
        p.setCreatedBy("SYSTEM");

        return produkTabunganRepository.save(p);
    }

    @Transactional
    public ProdukTabungan update(Long id,
                                 String kodeProduk,
                                 String namaProduk,
                                 String deskripsiSingkat,
                                 String jenisAkad,
                                 BigDecimal setoranAwalMinimum) {

        ProdukTabungan p = produkTabunganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produk tidak ditemukan"));

        String kode = normalizeKode(kodeProduk);
        String akad = normalizeAkad(jenisAkad);

        validate(kode, namaProduk, deskripsiSingkat, akad, setoranAwalMinimum, id);

        p.setKodeProduk(kode);
        p.setNamaProduk(namaProduk.trim());
        p.setDeskripsiSingkat(deskripsiSingkat == null ? null : deskripsiSingkat.trim());
        p.setJenisAkad(akad);
        p.setSetoranAwalMinimum(setoranAwalMinimum);
        p.setUpdatedAt(LocalDateTime.now());

        return produkTabunganRepository.save(p);
    }

    @Transactional
    public void toggleAktif(Long id) {
        ProdukTabungan p = produkTabunganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produk tidak ditemukan"));
        p.setAktif(!Boolean.TRUE.equals(p.getAktif()));
        p.setUpdatedAt(LocalDateTime.now());
        produkTabunganRepository.save(p);
    }

    @Transactional
    public void toggleAktif(String idOrUuid) {
        ProdukTabungan p = findByIdOrUuid(idOrUuid);
        p.setAktif(!Boolean.TRUE.equals(p.getAktif()));
        p.setUpdatedAt(LocalDateTime.now());
        produkTabunganRepository.save(p);
    }

    @Transactional
    public void deactivate(String idOrUuid) {
        ProdukTabungan p = findByIdOrUuid(idOrUuid);
        p.setAktif(false);
        p.setUpdatedAt(LocalDateTime.now());
        produkTabunganRepository.save(p);
    }

    @Transactional
    public void activate(String idOrUuid) {
        ProdukTabungan p = findByIdOrUuid(idOrUuid);
        p.setAktif(true);
        p.setUpdatedAt(LocalDateTime.now());
        produkTabunganRepository.save(p);
    }

    private void mapDtoToEntity(ProductFormDto dto, ProdukTabungan p) {
        if (dto.getProductType() != null && !dto.getProductType().isBlank()) {
            p.setProductType(dto.getProductType().trim());
        }
        if (dto.getProductCategory() != null && !dto.getProductCategory().isBlank()) {
            p.setProductCategory(dto.getProductCategory().trim());
        }
        if (dto.getCurrency() != null && !dto.getCurrency().isBlank()) {
            p.setCurrency(dto.getCurrency().trim());
        }
        if (dto.getIsDefault() != null) p.setIsDefault(dto.getIsDefault());
        if (dto.getAllowOverdraft() != null) p.setAllowOverdraft(dto.getAllowOverdraft());
        if (dto.getRequireMaintainingBalance() != null) p.setRequireMaintainingBalance(dto.getRequireMaintainingBalance());

        if (dto.getMinimumBalance() != null) p.setMinimumBalance(dto.getMinimumBalance());
        p.setMaximumBalance(dto.getMaximumBalance());

        if (dto.getProfitSharingRatio() != null) p.setProfitSharingRatio(dto.getProfitSharingRatio());
        if (dto.getProfitSharingType() != null) p.setProfitSharingType(dto.getProfitSharingType());
        if (dto.getProfitDistributionFrequency() != null) p.setProfitDistributionFrequency(dto.getProfitDistributionFrequency());

        p.setNisbahCustomer(dto.getNisbahCustomer());
        p.setNisbahBank(dto.getNisbahBank());

        if (dto.getIsShariahCompliant() != null) p.setIsShariahCompliant(dto.getIsShariahCompliant());
        p.setShariahBoardApprovalNumber(dto.getShariahBoardApprovalNumber());
        p.setShariahBoardApprovalDate(dto.getShariahBoardApprovalDate());

        p.setDailyWithdrawalLimit(dto.getDailyWithdrawalLimit());
        p.setMonthlyTransactionLimit(dto.getMonthlyTransactionLimit());
        if (dto.getOverdraftLimit() != null) p.setOverdraftLimit(dto.getOverdraftLimit());

        if (dto.getMonthlyMaintenanceFee() != null) p.setMonthlyMaintenanceFee(dto.getMonthlyMaintenanceFee());
        if (dto.getAtmWithdrawalFee() != null) p.setAtmWithdrawalFee(dto.getAtmWithdrawalFee());
        if (dto.getInterBankTransferFee() != null) p.setInterBankTransferFee(dto.getInterBankTransferFee());

        if (dto.getFreeTransactionsPerMonth() != null) p.setFreeTransactionsPerMonth(dto.getFreeTransactionsPerMonth());
        if (dto.getExcessTransactionFee() != null) p.setExcessTransactionFee(dto.getExcessTransactionFee());
        if (dto.getBelowMinimumBalanceFee() != null) p.setBelowMinimumBalanceFee(dto.getBelowMinimumBalanceFee());

        p.setMinCustomerAge(dto.getMinCustomerAge());
        p.setMaxCustomerAge(dto.getMaxCustomerAge());
        if (dto.getAllowedCustomerTypes() != null) p.setAllowedCustomerTypes(dto.getAllowedCustomerTypes());
        if (dto.getRequiredDocuments() != null) p.setRequiredDocuments(dto.getRequiredDocuments());

        p.setLaunchDate(dto.getLaunchDate());
        p.setRetirementDate(dto.getRetirementDate());
    }

    private void validate(String kodeProduk,
                          String namaProduk,
                          String deskripsiSingkat,
                          String jenisAkad,
                          BigDecimal setoranAwalMinimum,
                          Long currentId) {

        if (kodeProduk == null || kodeProduk.isBlank()) {
            throw new IllegalArgumentException("Kode produk wajib diisi.");
        }
        if (namaProduk == null || namaProduk.trim().isBlank()) {
            throw new IllegalArgumentException("Nama produk wajib diisi.");
        }
        if (jenisAkad == null || jenisAkad.isBlank()) {
            throw new IllegalArgumentException("Jenis akad wajib dipilih.");
        }
        if (!akadAllowed(jenisAkad)) {
            throw new IllegalArgumentException("Jenis akad tidak valid.");
        }

        if (setoranAwalMinimum == null) {
            throw new IllegalArgumentException("Setoran awal minimum wajib diisi.");
        }
        if (setoranAwalMinimum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Setoran awal minimum harus lebih dari 0.");
        }
        if (setoranAwalMinimum.compareTo(MAX_SETORAN) > 0) {
            throw new IllegalArgumentException("Setoran awal minimum maksimal 1.000.000.000.");
        }
        if (setoranAwalMinimum.scale() > 2) {
            throw new IllegalArgumentException("Setoran awal minimum maksimal 2 angka desimal.");
        }

        if (kodeProdukExists(kodeProduk, currentId)) {
            throw new IllegalArgumentException("Kode produk sudah dipakai, harus unik.");
        }
    }

    private boolean kodeProdukExists(String kodeProduk, Long currentId) {
        Long cnt = em.createQuery("""
                select count(p) from ProdukTabungan p
                where lower(p.kodeProduk) = lower(:kode)
                  and (:id is null or p.id <> :id)
                """, Long.class)
                .setParameter("kode", kodeProduk)
                .setParameter("id", currentId)
                .getSingleResult();
        return cnt != null && cnt > 0;
    }

    private String normalizeKode(String kodeProduk) {
        if (kodeProduk == null) return null;
        return kodeProduk.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeAkad(String jenisAkad) {
        if (jenisAkad == null) return null;
        return jenisAkad.trim().toUpperCase(Locale.ROOT);
    }

    private boolean akadAllowed(String akad) {
        String a = normalizeAkad(akad);
        return a.equals("WADIAH")
                || a.equals("MUDHARABAH")
                || a.equals("MUSYARAKAH")
                || a.equals("MURABAHAH")
                || a.equals("SALAM")
                || a.equals("ISTISNA")
                || a.equals("IJARAH")
                || a.equals("QARDH");
    }

    public record AkadOption(String value, String label) {}
}
