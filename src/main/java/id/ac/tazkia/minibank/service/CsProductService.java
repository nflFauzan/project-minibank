package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CsProductService {

    private final ProdukTabunganRepository produkTabunganRepository;

    @PersistenceContext
    private EntityManager em;

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

    public Page<ProdukTabungan> search(String q, Boolean aktif, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // data query
        CriteriaQuery<ProdukTabungan> cq = cb.createQuery(ProdukTabungan.class);
        Root<ProdukTabungan> root = cq.from(ProdukTabungan.class);

        List<Predicate> preds = new ArrayList<>();
        if (aktif != null) {
            preds.add(cb.equal(root.get("aktif"), aktif));
        }

        if (q != null && !q.trim().isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            Predicate p1 = cb.like(cb.lower(root.get("kodeProduk")), like);
            Predicate p2 = cb.like(cb.lower(root.get("namaProduk")), like);
            Predicate p3 = cb.like(cb.lower(root.get("deskripsiSingkat")), like);
            Predicate p4 = cb.like(cb.lower(root.get("jenisAkad")), like);
            preds.add(cb.or(p1, p2, p3, p4));
        }

        cq.where(preds.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("namaProduk")));

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
        if (q != null && !q.trim().isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            Predicate p1 = cb.like(cb.lower(countRoot.get("kodeProduk")), like);
            Predicate p2 = cb.like(cb.lower(countRoot.get("namaProduk")), like);
            Predicate p3 = cb.like(cb.lower(countRoot.get("deskripsiSingkat")), like);
            Predicate p4 = cb.like(cb.lower(countRoot.get("jenisAkad")), like);
            countPreds.add(cb.or(p1, p2, p3, p4));
        }

        countQ.select(cb.count(countRoot));
        countQ.where(countPreds.toArray(new Predicate[0]));
        long total = em.createQuery(countQ).getSingleResult();

        return new PageImpl<>(content, pageable, total);
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
        p.setSetoranAwalMinimum(setoranAwalMinimum);
        p.setAktif(true);

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

        return produkTabunganRepository.save(p);
    }

    @Transactional
    public void toggleAktif(Long id) {
        ProdukTabungan p = produkTabunganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produk tidak ditemukan"));
        p.setAktif(!p.getAktif());
        produkTabunganRepository.save(p);
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