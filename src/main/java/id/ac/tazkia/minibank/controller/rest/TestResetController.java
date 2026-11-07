package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.RekeningStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import id.ac.tazkia.minibank.repository.PassbookRepository;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestResetController {

    private final TransaksiRepository transaksiRepo;
    private final PassbookRepository passbookRepo;
    private final RekeningRepository rekeningRepo;
    private final NasabahRepository nasabahRepo;
    private final ProdukTabunganRepository produkRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/reset")
    public Map<String, String> reset() {
        try {
            // Hapus data transaksi dulu (karena foreign key)
            transaksiRepo.deleteAll();
            
            // Hapus passbook sebelum rekening (foreign key)
            passbookRepo.deleteAll();
            
            // Hapus rekening
            rekeningRepo.deleteAll();
            
            // Hapus nasabah
            nasabahRepo.deleteAll();
            
            // Hapus produk
            produkRepo.deleteAll();

            // Seed ulang data wajib untuk test
            seedTestData();

            return Map.of("status", "success", "message", "Database reset successfully");
        } catch (Exception e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
    }

    private void seedTestData() {
        // ─── Nasabah ACTIVE default untuk testing ────────────────────────────
        Nasabah n = new Nasabah();
        n.setCif("C0000001");
        n.setNik("1234567890123456");
        n.setNamaLengkap("Budi Santoso");
        n.setStatus(NasabahStatus.ACTIVE);
        n.setTempatLahir("Jakarta");
        n.setNamaIbuKandung("Siti Aminah");
        nasabahRepo.save(n);

        // ─── Nasabah INACTIVE (pending) untuk Supervisor approval test ───────
        Nasabah n2 = new Nasabah();
        n2.setCif("C0000002");
        n2.setNik("9876543210123456");
        n2.setNamaLengkap("Dewi Lestari");
        n2.setStatus(NasabahStatus.INACTIVE);
        n2.setTempatLahir("Bandung");
        n2.setNamaIbuKandung("Ratna Sari");
        nasabahRepo.save(n2);

        // ─── Nasabah INACTIVE kedua untuk test approve + reject ──────────────
        Nasabah n3 = new Nasabah();
        n3.setCif("C0000003");
        n3.setNik("5678901234123456");
        n3.setNamaLengkap("Ahmad Fadillah");
        n3.setStatus(NasabahStatus.INACTIVE);
        n3.setTempatLahir("Bogor");
        n3.setNamaIbuKandung("Fatimah Az-Zahra");
        nasabahRepo.save(n3);

        // ─── Nasabah INACTIVE ketiga untuk test negative reject ──────────────
        Nasabah n4 = new Nasabah();
        n4.setCif("C0000004");
        n4.setNik("1122334455667788");
        n4.setNamaLengkap("Chandra Wijaya");
        n4.setStatus(NasabahStatus.INACTIVE);
        n4.setTempatLahir("Depok");
        n4.setNamaIbuKandung("Kartika");
        nasabahRepo.save(n4);

        // ─── Nasabah ACTIVE kedua untuk rekening test ────────────────────────
        Nasabah n5 = new Nasabah();
        n5.setCif("C0000005");
        n5.setNik("5566778899001122");
        n5.setNamaLengkap("Andi Hermawan");
        n5.setStatus(NasabahStatus.ACTIVE);
        n5.setTempatLahir("Surabaya");
        n5.setNamaIbuKandung("Dewi Sartika");
        nasabahRepo.save(n5);

        // ─── Produk default ──────────────────────────────────────────────────
        ProdukTabungan p = new ProdukTabungan();
        p.setKodeProduk("TAB_UTAMA");
        p.setNamaProduk("Tabungan Utama");
        p.setJenisAkad("WADIAH");
        p.setSetoranAwalMinimum(new BigDecimal("100000"));
        p.setAktif(true);
        produkRepo.save(p);

        // ─── Seed Rekening-rekening ACTIVE untuk CS & Teller ─────────────────
        // Rekening 3: untuk Teller deposit, withdrawal, dan transfer source (ID 1)
        Rekening r3 = new Rekening();
        r3.setNasabah(n);
        r3.setNomorRekening("54300000301");
        r3.setStatus(RekeningStatus.ACTIVE);
        r3.setStatusActive(true);
        r3.setCifNasabah(n.getCif());
        r3.setNik(n.getNik());
        r3.setNamaNasabah(n.getNamaSesuaiIdentitas());
        r3.setProduk(p.getNamaProduk());
        r3.setNominalSetoranAwal(new BigDecimal("500000"));
        r3.setSaldo(new BigDecimal("500000"));
        r3.setTanggalPembukaan(LocalDate.now());
        r3.setCabangPembukaan("543");
        r3.setPetugasCs("CS");
        rekeningRepo.save(r3);

        // Rekening 4: untuk Teller transfer target (ID 2)
        Rekening r4 = new Rekening();
        r4.setNasabah(n5);
        r4.setNomorRekening("54300000401");
        r4.setStatus(RekeningStatus.ACTIVE);
        r4.setStatusActive(true);
        r4.setCifNasabah(n5.getCif());
        r4.setNik(n5.getNik());
        r4.setNamaNasabah(n5.getNamaSesuaiIdentitas());
        r4.setProduk(p.getNamaProduk());
        r4.setNominalSetoranAwal(new BigDecimal("500000"));
        r4.setSaldo(new BigDecimal("500000"));
        r4.setTanggalPembukaan(LocalDate.now());
        r4.setCabangPembukaan("543");
        r4.setPetugasCs("CS");
        rekeningRepo.save(r4);

        // Rekening 1: untuk CS account close test 1 (ID 3)
        Rekening r1 = new Rekening();
        r1.setNasabah(n5);
        r1.setNomorRekening("54300000101");
        r1.setStatus(RekeningStatus.ACTIVE);
        r1.setStatusActive(true);
        r1.setCifNasabah(n5.getCif());
        r1.setNik(n5.getNik());
        r1.setNamaNasabah(n5.getNamaSesuaiIdentitas());
        r1.setProduk(p.getNamaProduk());
        r1.setNominalSetoranAwal(new BigDecimal("1000000"));
        r1.setSaldo(new BigDecimal("1000000"));
        r1.setTanggalPembukaan(LocalDate.now());
        r1.setCabangPembukaan("543");
        r1.setPetugasCs("CS");
        rekeningRepo.save(r1);

        // Rekening 2: untuk CS account close test 2 (ID 4)
        Rekening r2 = new Rekening();
        r2.setNasabah(n5);
        r2.setNomorRekening("54300000201");
        r2.setStatus(RekeningStatus.ACTIVE);
        r2.setStatusActive(true);
        r2.setCifNasabah(n5.getCif());
        r2.setNik(n5.getNik());
        r2.setNamaNasabah(n5.getNamaSesuaiIdentitas());
        r2.setProduk(p.getNamaProduk());
        r2.setNominalSetoranAwal(new BigDecimal("1000000"));
        r2.setSaldo(new BigDecimal("1000000"));
        r2.setTanggalPembukaan(LocalDate.now());
        r2.setCabangPembukaan("543");
        r2.setPetugasCs("CS");
        rekeningRepo.save(r2);

        // Rekening PENDING untuk Supervisor approval test
        Rekening rPending1 = new Rekening();
        rPending1.setNasabah(n);
        rPending1.setNomorRekening("54300000501");
        rPending1.setStatus(RekeningStatus.PENDING);
        rPending1.setStatusActive(false);
        rPending1.setCifNasabah(n.getCif());
        rPending1.setNik(n.getNik());
        rPending1.setNamaNasabah(n.getNamaSesuaiIdentitas());
        rPending1.setProduk(p.getNamaProduk());
        rPending1.setTanggalPembukaan(LocalDate.now());
        rPending1.setPetugasCs("CS");
        rekeningRepo.save(rPending1);

        Rekening rPending2 = new Rekening();
        rPending2.setNasabah(n5);
        rPending2.setNomorRekening("54300000601");
        rPending2.setStatus(RekeningStatus.PENDING);
        rPending2.setStatusActive(false);
        rPending2.setCifNasabah(n5.getCif());
        rPending2.setNik(n5.getNik());
        rPending2.setNamaNasabah(n5.getNamaSesuaiIdentitas());
        rPending2.setProduk(p.getNamaProduk());
        rPending2.setTanggalPembukaan(LocalDate.now());
        rPending2.setPetugasCs("CS");
        rekeningRepo.save(rPending2);

        // ─── Ensure admin user exists with correct password ──────────────────
        ensureAdminUser();

        // ─── User PENDING (approved=false) untuk Admin approval test ─────────
        if (userRepo.findByUsername("pending_test_user").isEmpty()) {
            User pendingUser = new User();
            pendingUser.setUsername("pending_test_user");
            pendingUser.setPassword(passwordEncoder.encode("test1234"));
            pendingUser.setEmail("pending@test.com");
            pendingUser.setFullName("Pending Test User");
            pendingUser.setNim("NIM999999");
            pendingUser.setProdi("Informatics");
            pendingUser.setDosenPembimbing("Dr. Test Dosen");
            pendingUser.setApproved(false);
            pendingUser.setEnabled(false);
            pendingUser.setCreatedAt(LocalDateTime.now());
            userRepo.save(pendingUser);
        }

        if (userRepo.findByUsername("pending_test_user2").isEmpty()) {
            User pendingUser2 = new User();
            pendingUser2.setUsername("pending_test_user2");
            pendingUser2.setPassword(passwordEncoder.encode("test1234"));
            pendingUser2.setEmail("pending2@test.com");
            pendingUser2.setFullName("Pending Test User 2");
            pendingUser2.setNim("NIM888888");
            pendingUser2.setProdi("Accounting");
            pendingUser2.setDosenPembimbing("Dr. Test Dosen 2");
            pendingUser2.setApproved(false);
            pendingUser2.setEnabled(false);
            pendingUser2.setCreatedAt(LocalDateTime.now());
            userRepo.save(pendingUser2);
        }
    }

    private void ensureAdminUser() {
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));
        Role roleBm = roleRepo.findByName("ROLE_BRANCH_MANAGER").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_BRANCH_MANAGER")));
        Role roleCs = roleRepo.findByName("ROLE_CS").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_CS")));
        Role roleTeller = roleRepo.findByName("ROLE_TELLER").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_TELLER")));

        // admin
        User admin = userRepo.findByUsername("admin").orElseGet(User::new);
        admin.setUsername("admin");
        admin.setEmail("admin@minibank.test");
        admin.setFullName("Administrator");
        admin.setPassword(passwordEncoder.encode("minibank123"));
        admin.setApproved(true);
        admin.setEnabled(true);
        admin.getRoles().clear();
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleBm);
        userRepo.save(admin);

        // cs1
        User cs1 = userRepo.findByUsername("cs1").orElseGet(User::new);
        cs1.setUsername("cs1");
        cs1.setEmail("cs1@tazkia.ac.id");
        cs1.setFullName("Customer Service 1");
        cs1.setPassword(passwordEncoder.encode("minibank123"));
        cs1.setApproved(true);
        cs1.setEnabled(true);
        cs1.getRoles().clear();
        cs1.getRoles().add(roleCs);
        userRepo.save(cs1);

        // teller1
        User teller1 = userRepo.findByUsername("teller1").orElseGet(User::new);
        teller1.setUsername("teller1");
        teller1.setEmail("teller1@tazkia.ac.id");
        teller1.setFullName("Teller 1");
        teller1.setPassword(passwordEncoder.encode("minibank123"));
        teller1.setApproved(true);
        teller1.setEnabled(true);
        teller1.getRoles().clear();
        teller1.getRoles().add(roleTeller);
        userRepo.save(teller1);

        // zann (legacy CS)
        User zann = userRepo.findByUsername("zann").orElseGet(User::new);
        zann.setUsername("zann");
        zann.setEmail("zann@tazkia.ac.id");
        zann.setFullName("Zann Customer Service");
        zann.setPassword(passwordEncoder.encode("zann"));
        zann.setApproved(true);
        zann.setEnabled(true);
        zann.getRoles().clear();
        zann.getRoles().add(roleCs);
        userRepo.save(zann);

        // spv (Supervisor)
        Role roleSpv = roleRepo.findByName("ROLE_SUPERVISOR").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_SUPERVISOR")));
        User spv = userRepo.findByUsername("spv").orElseGet(User::new);
        spv.setUsername("spv");
        spv.setEmail("spv@tazkia.ac.id");
        spv.setFullName("Supervisor Default");
        spv.setPassword(passwordEncoder.encode("spv1234"));
        spv.setApproved(true);
        spv.setEnabled(true);
        spv.getRoles().clear();
        spv.getRoles().add(roleSpv);
        spv.getRoles().add(roleBm);
        userRepo.save(spv);

        // teller
        User teller = userRepo.findByUsername("teller").orElseGet(User::new);
        teller.setUsername("teller");
        teller.setEmail("teller@tazkia.ac.id");
        teller.setFullName("Teller Default");
        teller.setPassword(passwordEncoder.encode("teller1234"));
        teller.setApproved(true);
        teller.setEnabled(true);
        teller.getRoles().clear();
        teller.getRoles().add(roleTeller);
        userRepo.save(teller);

        // fauzan (Multi-role demo user: BM, CS, TELLER, SUPERVISOR)
        User fauzan = userRepo.findByUsername("fauzan").orElseGet(User::new);
        fauzan.setUsername("fauzan");
        fauzan.setEmail("241572010008.naufal@student.stmik.tazkia.ac.id");
        fauzan.setFullName("Naufal Fauzan");
        fauzan.setPassword(passwordEncoder.encode("minibank123"));
        fauzan.setApproved(true);
        fauzan.setEnabled(true);
        fauzan.getRoles().clear();
        fauzan.getRoles().add(roleBm);
        fauzan.getRoles().add(roleCs);
        fauzan.getRoles().add(roleTeller);
        fauzan.getRoles().add(roleSpv);
        userRepo.save(fauzan);
    }
}
