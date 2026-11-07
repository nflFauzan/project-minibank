-- ==============================================================================
-- MINIBANK DEMO DATA SEED SCRIPT
-- Purpose: Bersihkan data uji coba & siapkan data riil profesional untuk demo klien
-- ==============================================================================

BEGIN;

-- 1. BERSIHKAN DATA TRANSAKSI & PASSBOOK
DELETE FROM transaksi;
DELETE FROM passbook;

-- 2. BERSIHKAN USER & ROLE SAMPAH SISA AUTOMATED TEST
DELETE FROM role_permissions WHERE role_id IN (SELECT id FROM roles WHERE name LIKE 'ROLE_PW_%');
DELETE FROM user_roles WHERE role_id IN (SELECT id FROM roles WHERE name LIKE 'ROLE_PW_%');
DELETE FROM user_roles WHERE user_id IN (
    SELECT id FROM users 
    WHERE username LIKE 'testuser_%' 
       OR username LIKE 'rbac_test_%' 
       OR username LIKE 'pending_test_user%' 
       OR username IN ('123', 'syarif')
);

DELETE FROM roles WHERE name LIKE 'ROLE_PW_%';
DELETE FROM users 
WHERE username LIKE 'testuser_%' 
   OR username LIKE 'rbac_test_%' 
   OR username LIKE 'pending_test_user%' 
   OR username IN ('123', 'syarif');

-- 3. BERSIHKAN PRODUK TESTING
DELETE FROM produk_tabungan WHERE kode_produk LIKE 'TAB_TEST_%';

-- 4. BERSIHKAN REKENING & NASABAH DUMMY
DELETE FROM rekening WHERE nomor_rekening LIKE '%MA' OR cif_nasabah IN ('C0000006', 'C0000007');
DELETE FROM nasabah WHERE cif IN ('C0000006', 'C0000007');

-- 5. PASTIKAN ROLES STANDAR TERSEDIA (TIDAK PAKAI ON CONFLICT KARENA roles.name TIDAK BER-UNIQUE)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN') THEN
        INSERT INTO roles (name, role_code, display_name, description, active) VALUES ('ROLE_ADMIN', 'ADMIN', 'Administrator', 'Akses penuh administrasi & konfigurasi sistem', true);
    ELSE
        UPDATE roles SET role_code = 'ADMIN', display_name = 'Administrator', description = 'Akses penuh administrasi & konfigurasi sistem', active = true WHERE name = 'ROLE_ADMIN';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_BRANCH_MANAGER') THEN
        INSERT INTO roles (name, role_code, display_name, description, active) VALUES ('ROLE_BRANCH_MANAGER', 'BM', 'Branch Manager', 'Kepala Cabang & Pengawasan Approval Operasional', true);
    ELSE
        UPDATE roles SET role_code = 'BM', display_name = 'Branch Manager', description = 'Kepala Cabang & Pengawasan Approval Operasional', active = true WHERE name = 'ROLE_BRANCH_MANAGER';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_SUPERVISOR') THEN
        INSERT INTO roles (name, role_code, display_name, description, active) VALUES ('ROLE_SUPERVISOR', 'SPV', 'Supervisor', 'Approval pembukaan nasabah & rekening baru', true);
    ELSE
        UPDATE roles SET role_code = 'SPV', display_name = 'Supervisor', description = 'Approval pembukaan nasabah & rekening baru', active = true WHERE name = 'ROLE_SUPERVISOR';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_CS') THEN
        INSERT INTO roles (name, role_code, display_name, description, active) VALUES ('ROLE_CS', 'CS', 'Customer Service', 'Layanan nasabah, registrasi CIF & pembukaan rekening', true);
    ELSE
        UPDATE roles SET role_code = 'CS', display_name = 'Customer Service', description = 'Layanan nasabah, registrasi CIF & pembukaan rekening', active = true WHERE name = 'ROLE_CS';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_TELLER') THEN
        INSERT INTO roles (name, role_code, display_name, description, active) VALUES ('ROLE_TELLER', 'TELLER', 'Teller', 'Pelayanan kas, setoran tunai, tarikan & transfer buku besar', true);
    ELSE
        UPDATE roles SET role_code = 'TELLER', display_name = 'Teller', description = 'Pelayanan kas, setoran tunai, tarikan & transfer buku besar', active = true WHERE name = 'ROLE_TELLER';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER') THEN
        INSERT INTO roles (name, role_code, display_name, description, active) VALUES ('ROLE_USER', 'USER', 'Nasabah / User Biasa', 'Akses portal nasabah', true);
    ELSE
        UPDATE roles SET role_code = 'USER', display_name = 'Nasabah / User Biasa', description = 'Akses portal nasabah', active = true WHERE name = 'ROLE_USER';
    END IF;
END $$;

-- 6. RESET PASSWORD HASH SEMUA AKUN DEMO KE: minibank123
DO $$
DECLARE
    bcrypt_pwd VARCHAR := '$2a$10$0arsw2cRFpHA4CfStXN/cOoWg.t4z69DyHS60pQYW/F1v0G1zJgVm';
BEGIN
    -- Akun Admin Utama
    INSERT INTO users (username, password, full_name, email, approved, enabled, locked, created_at)
    VALUES ('admin', bcrypt_pwd, 'Administrator Utama', 'admin@tazkia.ac.id', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Administrator Utama', email = 'admin@tazkia.ac.id', approved = true, enabled = true, locked = false;

    -- Akun Supervisor / Branch Manager
    INSERT INTO users (username, password, full_name, email, approved, enabled, locked, created_at)
    VALUES ('spv', bcrypt_pwd, 'Supervisor Operasional', 'spv@tazkia.ac.id', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Supervisor Operasional', email = 'spv@tazkia.ac.id', approved = true, enabled = true, locked = false;

    -- Akun Customer Service
    INSERT INTO users (username, password, full_name, email, approved, enabled, locked, created_at)
    VALUES ('cs1', bcrypt_pwd, 'Fatimah Customer Service', 'cs1@tazkia.ac.id', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Fatimah Customer Service', email = 'cs1@tazkia.ac.id', approved = true, enabled = true, locked = false;

    -- Akun Teller
    INSERT INTO users (username, password, full_name, email, approved, enabled, locked, created_at)
    VALUES ('teller1', bcrypt_pwd, 'Hasan Teller Utama', 'teller1@tazkia.ac.id', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Hasan Teller Utama', email = 'teller1@tazkia.ac.id', approved = true, enabled = true, locked = false;

    -- Akun Zann (CS Cadangan)
    INSERT INTO users (username, password, full_name, email, approved, enabled, locked, created_at)
    VALUES ('zann', bcrypt_pwd, 'Zann Customer Service', 'zann@tazkia.ac.id', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Zann Customer Service', email = 'zann@tazkia.ac.id', approved = true, enabled = true, locked = false;

    -- 4 Akun Tim Mahasiswa Tazkia (Dirapikan profil dan role-nya)
    INSERT INTO users (username, password, full_name, email, nim, prodi, approved, enabled, locked, created_at)
    VALUES ('ainun', bcrypt_pwd, 'Ainun Zahra', '241572010014.ainun@student.stmik.tazkia.ac.id', '241572010014', 'Informatika', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Ainun Zahra', approved = true, enabled = true, locked = false;

    INSERT INTO users (username, password, full_name, email, nim, prodi, approved, enabled, locked, created_at)
    VALUES ('fauzan', bcrypt_pwd, 'Naufal Fauzan', '241572010008.naufal@student.stmik.tazkia.ac.id', '241572010008', 'Informatika', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Naufal Fauzan', approved = true, enabled = true, locked = false;

    INSERT INTO users (username, password, full_name, email, nim, prodi, approved, enabled, locked, created_at)
    VALUES ('akmal', bcrypt_pwd, 'Akmal Syarif', 'akmal.syarif@student.stmik.tazkia.ac.id', '241572010022', 'Informatika', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Akmal Syarif', email = 'akmal.syarif@student.stmik.tazkia.ac.id', approved = true, enabled = true, locked = false;

    INSERT INTO users (username, password, full_name, email, nim, prodi, approved, enabled, locked, created_at)
    VALUES ('zahra', bcrypt_pwd, 'Siti Tahtia Ainun Zahra', 'zahra.tahtia@student.stmik.tazkia.ac.id', '241572010025', 'Informatika', true, true, false, NOW())
    ON CONFLICT (username) DO UPDATE 
    SET password = bcrypt_pwd, full_name = 'Siti Tahtia Ainun Zahra', email = 'zahra.tahtia@student.stmik.tazkia.ac.id', approved = true, enabled = true, locked = false;
END $$;

-- 7. ATUR ROLE SETIAP PENGGUNA
DELETE FROM user_roles;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'admin' AND r.name IN ('ROLE_ADMIN', 'ROLE_BRANCH_MANAGER', 'ROLE_SUPERVISOR');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'spv' AND r.name IN ('ROLE_SUPERVISOR', 'ROLE_BRANCH_MANAGER');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username IN ('cs1', 'zann') AND r.name IN ('ROLE_CS');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'teller1' AND r.name IN ('ROLE_TELLER');

-- Beri 4 akun mahasiswa multi-role (CS, Teller, Supervisor) agar fleksibel digunakan saat demo fitur apapun
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username IN ('ainun', 'fauzan', 'akmal', 'zahra') 
  AND r.name IN ('ROLE_CS', 'ROLE_TELLER', 'ROLE_SUPERVISOR', 'ROLE_USER');

-- 8. PASTIKAN PRODUK TABUNGAN LENGKAP & AKTIF
INSERT INTO produk_tabungan (kode_produk, nama_produk, jenis_akad, deskripsi_singkat, setoran_awal_minimum, aktif)
VALUES
    ('TAB_UTAMA', 'Tabungan Utama', 'WADIAH', 'Tabungan utama dengan kemudahan transaksi dan tanpa biaya administrasi bulanan.', 100000, true),
    ('TAB001', 'Tabungan Wadiah Basic', 'WADIAH', 'Tabungan titipan murni sesuai prinsip syariah, aman dan berkah.', 50000, true),
    ('TAB002', 'Tabungan Mudharabah Premium', 'MUDHARABAH', 'Tabungan investasi bagi hasil kompetitif untuk perorangan dan profesional.', 500000, true),
    ('GIR001', 'Giro Wadiah Corporate', 'WADIAH', 'Rekening giro untuk kelancaran arus kas bisnis, didukung bilyet giro dan buku cek.', 1000000, true),
    ('DEP001', 'Deposito Mudharabah', 'MUDHARABAH', 'Simpanan berjangka dengan nisbah bagi hasil optimal 1, 3, 6, dan 12 bulan.', 1000000, true)
ON CONFLICT (kode_produk) DO UPDATE 
SET nama_produk = EXCLUDED.nama_produk,
    jenis_akad = EXCLUDED.jenis_akad,
    deskripsi_singkat = EXCLUDED.deskripsi_singkat,
    setoran_awal_minimum = EXCLUDED.setoran_awal_minimum,
    aktif = true;

-- 9. PERBAHARUI DAN TAMBAHKAN 5 NASABAH BERIKUT (DENGAN NAMA ELEGAN & TIDAK PASARAN)
-- Nasabah C0000001 (Budi Santoso) - dirapikan datanya (alamat <= 35 char)
INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, nama_ibu_kandung, pekerjaan, status, created_at, alamat_identitas, kota_identitas, provinsi_identitas, no_hp, email)
VALUES ('C0000001', '3201121004850001', 'Budi Santoso', 'Jakarta', '1985-04-10', 'L', 'ISLAM', 'Siti Aminah', 'Wiraswasta Retail', 'ACTIVE', NOW() - interval '30 days', 'Jl. Pajajaran No. 45', 'KOTA BOGOR', 'JAWA BARAT', '081234567890', 'budi.santoso@gmail.com')
ON CONFLICT (cif) DO UPDATE 
SET nama_sesuai_identitas = EXCLUDED.nama_sesuai_identitas, status = 'ACTIVE', kota_identitas = EXCLUDED.kota_identitas;

-- Nasabah C0000002 (Dewi Lestari) - sengaja status INACTIVE untuk demo fitur approval supervisor
INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, nama_ibu_kandung, pekerjaan, status, created_at, alamat_identitas, kota_identitas, provinsi_identitas, no_hp, email)
VALUES ('C0000002', '3273155507900002', 'Dewi Lestari', 'Bandung', '1990-07-15', 'P', 'ISLAM', 'Ratna Sari', 'Konsultan Keuangan', 'INACTIVE', NOW() - interval '1 day', 'Jl. Dago Asri No. 12', 'KOTA BANDUNG', 'JAWA BARAT', '081298765432', 'dewi.lestari@gmail.com')
ON CONFLICT (cif) DO UPDATE 
SET status = 'INACTIVE';

-- Nasabah C0000005 (Andi Hermawan)
INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, nama_ibu_kandung, pekerjaan, status, created_at, alamat_identitas, kota_identitas, provinsi_identitas, no_hp, email)
VALUES ('C0000005', '3578012002870003', 'Andi Hermawan', 'Surabaya', '1987-02-20', 'L', 'ISLAM', 'Dewi Sartika', 'Manager Logistik', 'ACTIVE', NOW() - interval '20 days', 'Jl. Pemuda No. 88', 'KOTA SURABAYA', 'JAWA TIMUR', '081345678901', 'andi.hermawan@gmail.com')
ON CONFLICT (cif) DO UPDATE 
SET status = 'ACTIVE';

-- 5 NASABAH BARU DENGAN NAMA ELEGAN & TIDAK PASARAN:
-- 1. Raden Mas Daniswara Pradipta (Pengusaha Agribisnis Organik)
INSERT INTO nasabah (
    cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, status_pernikahan, 
    nama_ibu_kandung, status, pekerjaan, nama_perusahaan, jabatan, penghasilan_per_bulan,
    alamat_identitas, rt_identitas, rw_identitas, kelurahan_identitas, kecamatan_identitas, kota_identitas, provinsi_identitas, kode_pos_identitas,
    alamat_domisili, rt_domisili, rw_domisili, kelurahan_domisili, kecamatan_domisili, kota_domisili, provinsi_domisili, kode_pos_domisili,
    no_hp, email, created_at, created_by
) VALUES (
    'C0000008', '3201151205880001', 'Raden Mas Daniswara Pradipta', 'Surakarta', '1988-05-12', 'L', 'ISLAM', 'MENIKAH',
    'Raden Ayu Kusumaningrum', 'ACTIVE', 'Wiraswasta / Pengusaha', 'PT Pradipta Agro Lestari', 'Direktur Utama', '> Rp 25.000.000',
    'Jl. Slamet Riyadi No. 142', '003', '007', 'Sriwedari', 'Laweyan', 'KOTA SURAKARTA', 'JAWA TENGAH', '57141',
    'Jl. Slamet Riyadi No. 142', '003', '007', 'Sriwedari', 'Laweyan', 'KOTA SURAKARTA', 'JAWA TENGAH', '57141',
    '081289110022', 'daniswara.pradipta@agropradipta.co.id', NOW() - interval '14 days', 'cs1'
) ON CONFLICT (cif) DO UPDATE SET nama_sesuai_identitas = EXCLUDED.nama_sesuai_identitas, status = 'ACTIVE';

-- 2. dr. Khadijah Al-Zahrawi Laksmana, Sp.A (Dokter Spesialis Anak & Pemilik Klinik)
INSERT INTO nasabah (
    cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, status_pernikahan, 
    nama_ibu_kandung, status, pekerjaan, nama_perusahaan, jabatan, penghasilan_per_bulan,
    alamat_identitas, rt_identitas, rw_identitas, kelurahan_identitas, kecamatan_identitas, kota_identitas, provinsi_identitas, kode_pos_identitas,
    alamat_domisili, rt_domisili, rw_domisili, kelurahan_domisili, kecamatan_domisili, kota_domisili, provinsi_domisili, kode_pos_domisili,
    no_hp, email, created_at, created_by
) VALUES (
    'C0000009', '3271014508920002', 'dr. Khadijah Al-Zahrawi Laksmana, Sp.A', 'Bogor', '1992-08-15', 'P', 'ISLAM', 'MENIKAH',
    'Hj. Maryam Iskandar', 'ACTIVE', 'Dokter Spesialis Anak', 'RS Sentra Medika & Klinik Al-Zahrawi', 'Konsultan Pediatri', '> Rp 25.000.000',
    'Jl. Pajajaran Indah V No. 18', '002', '005', 'Baranangsiang', 'Bogor Timur', 'KOTA BOGOR', 'JAWA BARAT', '16143',
    'Jl. Pajajaran Indah V No. 18', '002', '005', 'Baranangsiang', 'Bogor Timur', 'KOTA BOGOR', 'JAWA BARAT', '16143',
    '081377889901', 'khadijah.laksmana@alzahrawi-clinic.com', NOW() - interval '10 days', 'cs1'
) ON CONFLICT (cif) DO UPDATE SET nama_sesuai_identitas = EXCLUDED.nama_sesuai_identitas, status = 'ACTIVE';

-- 3. Syailendra Mahardika Wicaksana, S.T. (Principal Architect)
INSERT INTO nasabah (
    cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, status_pernikahan, 
    nama_ibu_kandung, status, pekerjaan, nama_perusahaan, jabatan, penghasilan_per_bulan,
    alamat_identitas, rt_identitas, rw_identitas, kelurahan_identitas, kecamatan_identitas, kota_identitas, provinsi_identitas, kode_pos_identitas,
    alamat_domisili, rt_domisili, rw_domisili, kelurahan_domisili, kecamatan_domisili, kota_domisili, provinsi_domisili, kode_pos_domisili,
    no_hp, email, created_at, created_by
) VALUES (
    'C0000010', '3174052309850003', 'Syailendra Mahardika Wicaksana, S.T.', 'Bandung', '1985-09-23', 'L', 'ISLAM', 'MENIKAH',
    'Endang Retnowati', 'ACTIVE', 'Principal Architect', 'Studio Rancang Nusantara', 'Managing Partner', '> Rp 25.000.000',
    'Jl. Gandaria Tengah III No. 8', '004', '002', 'Kramat Pela', 'Kebayoran Baru', 'KOTA JAKARTA SELATAN', 'DKI JAKARTA', '12130',
    'Jl. Gandaria Tengah III No. 8', '004', '002', 'Kramat Pela', 'Kebayoran Baru', 'KOTA JAKARTA SELATAN', 'DKI JAKARTA', '12130',
    '081122334455', 'syailendra@studiorancang.id', NOW() - interval '7 days', 'cs1'
) ON CONFLICT (cif) DO UPDATE SET nama_sesuai_identitas = EXCLUDED.nama_sesuai_identitas, status = 'ACTIVE';

-- 4. Anindya Maheswari Nataprawira, M.Sc. (Dosen & Peneliti Bioinformatika)
INSERT INTO nasabah (
    cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, status_pernikahan, 
    nama_ibu_kandung, status, pekerjaan, nama_perusahaan, jabatan, penghasilan_per_bulan,
    alamat_identitas, rt_identitas, rw_identitas, kelurahan_identitas, kecamatan_identitas, kota_identitas, provinsi_identitas, kode_pos_identitas,
    alamat_domisili, rt_domisili, rw_domisili, kelurahan_domisili, kecamatan_domisili, kota_domisili, provinsi_domisili, kode_pos_domisili,
    no_hp, email, created_at, created_by
) VALUES (
    'C0000011', '3204126101950004', 'Anindya Maheswari Nataprawira, M.Sc.', 'Malang', '1995-01-21', 'P', 'ISLAM', 'BELUM MENIKAH',
    'Retno Dyah Pitaloka', 'ACTIVE', 'Dosen & Peneliti Akademik', 'Institut Tazkia Sentul', 'Kepala Lab Riset', 'Rp 10.000.000 - Rp 25.000.000',
    'Komp. Bellanova No. B12', '001', '004', 'Citaringgul', 'Babakan Madang', 'KABUPATEN BOGOR', 'JAWA BARAT', '16810',
    'Komp. Bellanova No. B12', '001', '004', 'Citaringgul', 'Babakan Madang', 'KABUPATEN BOGOR', 'JAWA BARAT', '16810',
    '081599887766', 'anindya.nataprawira@tazkia.ac.id', NOW() - interval '5 days', 'cs1'
) ON CONFLICT (cif) DO UPDATE SET nama_sesuai_identitas = EXCLUDED.nama_sesuai_identitas, status = 'ACTIVE';

-- 5. Gibran Iskandar Quraishi (Head of Engineering Fintek - INACTIVE untuk live demo supervisor approval)
INSERT INTO nasabah (
    cif, nik, nama_sesuai_identitas, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, status_pernikahan, 
    nama_ibu_kandung, status, pekerjaan, nama_perusahaan, jabatan, penghasilan_per_bulan,
    alamat_identitas, rt_identitas, rw_identitas, kelurahan_identitas, kecamatan_identitas, kota_identitas, provinsi_identitas, kode_pos_identitas,
    alamat_domisili, rt_domisili, rw_domisili, kelurahan_domisili, kecamatan_domisili, kota_domisili, provinsi_domisili, kode_pos_domisili,
    no_hp, email, created_at, created_by
) VALUES (
    'C0000012', '3171031911900005', 'Gibran Iskandar Quraishi', 'Medan', '1990-11-19', 'L', 'ISLAM', 'MENIKAH',
    'Siti Nurhaliza Quraishi', 'INACTIVE', 'Head of Engineering', 'PT Fintek Berkah Solusindo', 'Direktur Teknologi', '> Rp 25.000.000',
    'Jl. Menteng Raya No. 45', '005', '008', 'Kebon Sirih', 'Menteng', 'KOTA JAKARTA PUSAT', 'DKI JAKARTA', '10340',
    'Jl. Menteng Raya No. 45', '005', '008', 'Kebon Sirih', 'Menteng', 'KOTA JAKARTA PUSAT', 'DKI JAKARTA', '10340',
    '081809911223', 'gibran.quraishi@fintekberkah.com', NOW() - interval '2 hours', 'cs1'
) ON CONFLICT (cif) DO UPDATE SET nama_sesuai_identitas = EXCLUDED.nama_sesuai_identitas, status = 'INACTIVE';

-- 10. REKENING BANK AKTIF & PENDING
-- Bersihkan seluruh rekening yang tidak terpakai
DELETE FROM rekening;

INSERT INTO rekening (
    nomor_rekening, nasabah_id, cif_nasabah, nama_nasabah, nik, nomor_telepon, email,
    produk, nominal_setoran_awal, saldo, status, status_active, tanggal_pembukaan, cabang_pembukaan, petugas_cs, tujuan_pembukaan, created_at
) VALUES
    -- Rekening 1: Raden Mas Daniswara Pradipta - Giro Wadiah Corporate
    ('54300000801', (SELECT id FROM nasabah WHERE cif = 'C0000008'), 'C0000008', 'Raden Mas Daniswara Pradipta', '3201151205880001', '081289110022', 'daniswara.pradipta@agropradipta.co.id',
     'Giro Wadiah Corporate', 25000000.00, 68500000.00, 'ACTIVE', true, CURRENT_DATE - 14, '543', 'cs1', 'Operasional Arus Kas Usaha Agribisnis', NOW() - interval '14 days'),

    -- Rekening 2: dr. Khadijah Al-Zahrawi Laksmana - Tabungan Mudharabah Premium
    ('54300000901', (SELECT id FROM nasabah WHERE cif = 'C0000009'), 'C0000009', 'dr. Khadijah Al-Zahrawi Laksmana, Sp.A', '3271014508920002', '081377889901', 'khadijah.laksmana@alzahrawi-clinic.com',
     'Tabungan Mudharabah Premium', 15000000.00, 32750000.00, 'ACTIVE', true, CURRENT_DATE - 10, '543', 'cs1', 'Investasi Dana Kesehatan & Jasa Medis', NOW() - interval '10 days'),

    -- Rekening 3: Syailendra Mahardika Wicaksana - Tabungan Utama
    ('54300001001', (SELECT id FROM nasabah WHERE cif = 'C0000010'), 'C0000010', 'Syailendra Mahardika Wicaksana, S.T.', '3174052309850003', '081122334455', 'syailendra@studiorancang.id',
     'Tabungan Utama', 10000000.00, 16800000.00, 'ACTIVE', true, CURRENT_DATE - 7, '543', 'cs1', 'Simpanan Pribadi & Arsitektur', NOW() - interval '7 days'),

    -- Rekening 4: Anindya Maheswari Nataprawira - Tabungan Wadiah Basic
    ('54300001101', (SELECT id FROM nasabah WHERE cif = 'C0000011'), 'C0000011', 'Anindya Maheswari Nataprawira, M.Sc.', '3204126101950004', '081599887766', 'anindya.nataprawira@tazkia.ac.id',
     'Tabungan Wadiah Basic', 5000000.00, 7950000.00, 'ACTIVE', true, CURRENT_DATE - 5, '543', 'cs1', 'Pengelolaan Dana Riset Bioinformatika', NOW() - interval '5 days'),

    -- Rekening 5: Budi Santoso - Tabungan Utama
    ('54300000301', (SELECT id FROM nasabah WHERE cif = 'C0000001'), 'C0000001', 'Budi Santoso', '3201121004850001', '081234567890', 'budi.santoso@gmail.com',
     'Tabungan Utama', 5000000.00, 14500000.00, 'ACTIVE', true, CURRENT_DATE - 20, '543', 'cs1', 'Tabungan Pribadi & Keluarga', NOW() - interval '20 days'),

    -- Rekening 6: Budi Santoso - Tabungan Wadiah Basic
    ('54300000401', (SELECT id FROM nasabah WHERE cif = 'C0000001'), 'C0000001', 'Budi Santoso', '3201121004850001', '081234567890', 'budi.santoso@gmail.com',
     'Tabungan Wadiah Basic', 2000000.00, 3250000.00, 'ACTIVE', true, CURRENT_DATE - 15, '543', 'cs1', 'Dana Darurat Titipan', NOW() - interval '15 days'),

    -- Rekening 7: Andi Hermawan - Tabungan Mudharabah Premium
    ('54300000101', (SELECT id FROM nasabah WHERE cif = 'C0000005'), 'C0000005', 'Andi Hermawan', '3578012002870003', '081345678901', 'andi.hermawan@gmail.com',
     'Tabungan Mudharabah Premium', 10000000.00, 8500000.00, 'ACTIVE', true, CURRENT_DATE - 18, '543', 'cs1', 'Tabungan Investasi Masa Depan', NOW() - interval '18 days'),

    -- Rekening 8: Gibran Iskandar Quraishi - PENDING untuk live demo approval supervisor!
    ('54300001201', (SELECT id FROM nasabah WHERE cif = 'C0000012'), 'C0000012', 'Gibran Iskandar Quraishi', '3171031911900005', '081809911223', 'gibran.quraishi@fintekberkah.com',
     'Tabungan Mudharabah Premium', 1000000.00, 0.00, 'PENDING', false, CURRENT_DATE, '543', 'cs1', 'Tabungan Fintek & Bisnis Digital', NOW() - interval '1 hour');

-- 11. BUKU TABUNGAN (PASSBOOK) SIAP PRINT
INSERT INTO passbook (
    id, rekening_id, nomor_rekening, passbook_number, current_page, last_printed_line, lines_per_page, status, created_at
) VALUES
    (gen_random_uuid(), (SELECT id FROM rekening WHERE nomor_rekening = '54300000801'), '54300000801', 'PB-543-00000801', 1, 0, 30, 'ACTIVE', NOW() - interval '14 days'),
    (gen_random_uuid(), (SELECT id FROM rekening WHERE nomor_rekening = '54300000901'), '54300000901', 'PB-543-00000901', 1, 0, 30, 'ACTIVE', NOW() - interval '10 days'),
    (gen_random_uuid(), (SELECT id FROM rekening WHERE nomor_rekening = '54300001001'), '54300001001', 'PB-543-00001001', 1, 0, 30, 'ACTIVE', NOW() - interval '7 days');

-- 12. RIWAYAT TRANSAKSI TELLER LENGKAP & KONSISTEN
-- Set sequence transaksi awal
ALTER SEQUENCE transaksi_no_seq RESTART WITH 3000001;

-- ================= AKUN 1: Raden Mas Daniswara Pradipta (54300000801) =================
-- Setoran Awal
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000001', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000801'), '54300000801', 'Raden Mas Daniswara Pradipta - Giro Wadiah Corporate', 'C0000008', 'Giro Wadiah Corporate',
    25000000.00, 0.00, 25000000.00, 'Setoran Awal Pembukaan Rekening Giro Bisnis', 'DEP-20261104-001',
    NOW() - interval '3 days 4 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Setoran Tunai Hasil Panen
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000002', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000801'), '54300000801', 'Raden Mas Daniswara Pradipta - Giro Wadiah Corporate', 'C0000008', 'Giro Wadiah Corporate',
    50000000.00, 25000000.00, 75000000.00, 'Setoran Tunai Hasil Penjualan Panen Organik', 'AGRO-INV-882',
    NOW() - interval '2 days 2 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Penarikan Tunai Logistik
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T2000001', 'WITHDRAWAL', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000801'), '54300000801', 'Raden Mas Daniswara Pradipta - Giro Wadiah Corporate', 'C0000008', 'Giro Wadiah Corporate',
    -10000000.00, 75000000.00, 65000000.00, 'Tarik Tunai Biaya Operasional Logistik Kebun', 'WD-20261106-01',
    NOW() - interval '1 day 5 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '20'
);

-- ================= AKUN 2: dr. Khadijah Al-Zahrawi Laksmana (54300000901) =================
-- Setoran Awal
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000003', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000901'), '54300000901', 'dr. Khadijah Al-Zahrawi Laksmana, Sp.A - Tabungan Mudharabah Premium', 'C0000009', 'Tabungan Mudharabah Premium',
    15000000.00, 0.00, 15000000.00, 'Setoran Awal Tabungan Mudharabah Premium', 'DEP-20261104-002',
    NOW() - interval '3 days 2 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Setoran Jasa Medis
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000004', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000901'), '54300000901', 'dr. Khadijah Al-Zahrawi Laksmana, Sp.A - Tabungan Mudharabah Premium', 'C0000009', 'Tabungan Mudharabah Premium',
    20000000.00, 15000000.00, 35000000.00, 'Setoran Tunai Jasa Layanan Konsultasi Medis', 'KLN-202611-09',
    NOW() - interval '2 days 1 hour', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Tarik Tunai Perlengkapan Medis
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T2000002', 'WITHDRAWAL', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000901'), '54300000901', 'dr. Khadijah Al-Zahrawi Laksmana, Sp.A - Tabungan Mudharabah Premium', 'C0000009', 'Tabungan Mudharabah Premium',
    -2250000.00, 35000000.00, 32750000.00, 'Tarik Tunai Pembelian Perlengkapan Medis Anak', 'WD-20261106-02',
    NOW() - interval '1 day 3 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '20'
);

-- ================= AKUN 3: Syailendra Mahardika Wicaksana (54300001001) =================
-- Setoran Awal
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000005', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300001001'), '54300001001', 'Syailendra Mahardika Wicaksana, S.T. - Tabungan Utama', 'C0000010', 'Tabungan Utama',
    10000000.00, 0.00, 10000000.00, 'Setoran Awal Tabungan Utama Arsitektur', 'DEP-20261105-001',
    NOW() - interval '2 days 6 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Setoran Honor Desain
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000006', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300001001'), '54300001001', 'Syailendra Mahardika Wicaksana, S.T. - Tabungan Utama', 'C0000010', 'Tabungan Utama',
    8000000.00, 10000000.00, 18000000.00, 'Setoran Honor Desain Rumah Tinggal Sentul', 'SRN-PROJ-104',
    NOW() - interval '1 day 4 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Tarik Tunai Workshop
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T2000003', 'WITHDRAWAL', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300001001'), '54300001001', 'Syailendra Mahardika Wicaksana, S.T. - Tabungan Utama', 'C0000010', 'Tabungan Utama',
    -1200000.00, 18000000.00, 16800000.00, 'Tarik Tunai Kebutuhan Workshop Lapangan', 'WD-20261107-01',
    NOW() - interval '4 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '20'
);

-- ================= AKUN 4: Anindya Maheswari Nataprawira (54300001101) =================
-- Setoran Awal
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000007', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300001101'), '54300001101', 'Anindya Maheswari Nataprawira, M.Sc. - Tabungan Wadiah Basic', 'C0000011', 'Tabungan Wadiah Basic',
    5000000.00, 0.00, 5000000.00, 'Setoran Awal Tabungan Wadiah Basic Riset', 'DEP-20261105-002',
    NOW() - interval '2 days 5 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Setoran Dana Riset
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000008', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300001101'), '54300001101', 'Anindya Maheswari Nataprawira, M.Sc. - Tabungan Wadiah Basic', 'C0000011', 'Tabungan Wadiah Basic',
    3500000.00, 5000000.00, 8500000.00, 'Setoran Dana Hibah Publikasi Bioinformatika', 'HIBAH-TZK-2026',
    NOW() - interval '1 day 1 hour', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Tarik Tunai Buku Referensi
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T2000004', 'WITHDRAWAL', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300001101'), '54300001101', 'Anindya Maheswari Nataprawira, M.Sc. - Tabungan Wadiah Basic', 'C0000011', 'Tabungan Wadiah Basic',
    -550000.00, 8500000.00, 7950000.00, 'Tarik Tunai Pembelian Buku Referensi Akademik', 'WD-20261107-02',
    NOW() - interval '3 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '20'
);

-- ================= AKUN 5: Budi Santoso (54300000301) =================
-- Setoran Awal
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000009', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000301'), '54300000301', 'Budi Santoso - Tabungan Utama', 'C0000001', 'Tabungan Utama',
    5000000.00, 0.00, 5000000.00, 'Setoran Awal Tabungan Utama', 'DEP-20261104-003',
    NOW() - interval '3 days 6 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Setoran Dagang
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000010', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000301'), '54300000301', 'Budi Santoso - Tabungan Utama', 'C0000001', 'Tabungan Utama',
    10000000.00, 5000000.00, 15000000.00, 'Setoran Tunai Hasil Dagang Toko Sembako', 'DAG-202611-01',
    NOW() - interval '2 days', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Tarik Tunai
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T2000005', 'WITHDRAWAL', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000301'), '54300000301', 'Budi Santoso - Tabungan Utama', 'C0000001', 'Tabungan Utama',
    -500000.00, 15000000.00, 14500000.00, 'Tarik Tunai Kebutuhan Rumah Tangga', 'WD-20261106-03',
    NOW() - interval '1 day 2 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '20'
);

-- ================= AKUN 6: Budi Santoso (54300000401) - Wadiah =================
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000011', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000401'), '54300000401', 'Budi Santoso - Tabungan Wadiah Basic', 'C0000001', 'Tabungan Wadiah Basic',
    2000000.00, 0.00, 2000000.00, 'Setoran Awal Tabungan Titipan Wadiah', 'DEP-20261105-003',
    NOW() - interval '2 days 4 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000012', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000401'), '54300000401', 'Budi Santoso - Tabungan Wadiah Basic', 'C0000001', 'Tabungan Wadiah Basic',
    1500000.00, 2000000.00, 3500000.00, 'Setoran Tambahan Titipan Hasil Usaha', 'DEP-20261106-04',
    NOW() - interval '1 day 6 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T2000006', 'WITHDRAWAL', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000401'), '54300000401', 'Budi Santoso - Tabungan Wadiah Basic', 'C0000001', 'Tabungan Wadiah Basic',
    -250000.00, 3500000.00, 3250000.00, 'Tarik Tunai Transportasi Harian', 'WD-20261107-03',
    NOW() - interval '2 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '20'
);

-- ================= AKUN 7: Andi Hermawan (54300000101) & TRANSFER =================
-- Setoran Awal Andi
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000013', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000101'), '54300000101', 'Andi Hermawan - Tabungan Mudharabah Premium', 'C0000005', 'Tabungan Mudharabah Premium',
    10000000.00, 0.00, 10000000.00, 'Setoran Awal Tabungan Mudharabah', 'DEP-20261104-004',
    NOW() - interval '3 days 1 hour', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- Setoran Tambahan Andi
INSERT INTO transaksi (
    id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
    jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
) VALUES (
    gen_random_uuid(), gen_random_uuid(), 'T1000014', 'DEPOSIT', 'TELLER',
    (SELECT id FROM rekening WHERE nomor_rekening = '54300000101'), '54300000101', 'Andi Hermawan - Tabungan Mudharabah Premium', 'C0000005', 'Tabungan Mudharabah Premium',
    2000000.00, 10000000.00, 12000000.00, 'Setoran Tambahan Saldo Tabungan', 'DEP-20261106-05',
    NOW() - interval '1 day 7 hours', 'teller1', 'teller1', 'Hasan Teller Utama', false, '10'
);

-- TRANSAKSI TRANSFER ANTAR REKENING (Andi Hermawan ke Raden Mas Daniswara Pradipta)
DO $$
DECLARE
    transfer_gid UUID := gen_random_uuid();
    rek_sumber_id BIGINT;
    rek_tujuan_id BIGINT;
BEGIN
    SELECT id INTO rek_sumber_id FROM rekening WHERE nomor_rekening = '54300000101';
    SELECT id INTO rek_tujuan_id FROM rekening WHERE nomor_rekening = '54300000801';

    -- Sisi Rekening Sumber (Debit -Rp 3.500.000)
    INSERT INTO transaksi (
        id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
        jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
    ) VALUES (
        gen_random_uuid(), transfer_gid, 'T3000001', 'TRANSFER', 'TELLER',
        rek_sumber_id, '54300000101', 'Andi Hermawan - Tabungan Mudharabah Premium', 'C0000005', 'Tabungan Mudharabah Premium',
        -3500000.00, 12000000.00, 8500000.00, 'Transfer ke 54300000801 / Raden Mas Daniswara Pradipta - Pembayaran Pasokan Sayur Organik Resto', 'TRF-20261107-001',
        NOW() - interval '1 hour', 'teller1', 'teller1', 'Hasan Teller Utama', false, '30'
    );

    -- Sisi Rekening Tujuan (Kredit +Rp 3.500.000)
    INSERT INTO transaksi (
        id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk,
        jumlah, saldo_sebelum, saldo_sesudah, keterangan, no_referensi, processed_at, processed_by, processed_by_username, processed_by_full_name, printed, sandi_code
    ) VALUES (
        gen_random_uuid(), transfer_gid, 'T3000002', 'TRANSFER', 'TELLER',
        rek_tujuan_id, '54300000801', 'Raden Mas Daniswara Pradipta - Giro Wadiah Corporate', 'C0000008', 'Giro Wadiah Corporate',
        3500000.00, 65000000.00, 68500000.00, 'Transfer masuk dari 54300000101 / Andi Hermawan - Pembayaran Pasokan Sayur Organik Resto', 'TRF-20261107-001',
        NOW() - interval '1 hour', 'teller1', 'teller1', 'Hasan Teller Utama', false, '31'
    );
END $$;

-- Pastikan sequence transaksi berada di atas transaksi terakhir agar tidak bentrok saat ada transaksi baru
ALTER SEQUENCE transaksi_no_seq RESTART WITH 3000050;

COMMIT;
