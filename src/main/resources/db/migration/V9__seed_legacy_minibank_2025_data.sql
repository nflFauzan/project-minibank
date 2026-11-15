-- ==============================================================================
-- Flyway Migration V9: Seed Legacy Minibank 2025 Data (100% Comprehensive)
-- Linked to Periode '2025/2026' (ARCHIVED) with Sequence Synchronization
-- ==============================================================================

DO $$
DECLARE
    v_periode_2025_id BIGINT;
BEGIN
    SELECT id INTO v_periode_2025_id FROM periode_praktikum WHERE kode = '2025/2026';
    IF v_periode_2025_id IS NULL THEN
        INSERT INTO periode_praktikum (kode, nama, status, is_active, is_default, tanggal_mulai, tanggal_selesai, deskripsi)
        VALUES ('2025/2026', 'Praktikum Minibank 2025/2026 (Arsip)', 'ARCHIVED', false, false, '2025-11-01', '2026-01-31', 'Data historis praktikum minibank tahun ajaran 2025/2026')
        RETURNING id INTO v_periode_2025_id;
    END IF;

    -- 1. SEED 162 LEGACY CUSTOMERS
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000399') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000399', '3201000000000000', 'Tamamun Nadzir', '2000-01-01', 'Ibu Kandung', '085184633911', 'tamamunnadzirnadzir@gmail.com', 'Ds. Lebanisuko RT05/RW01', 'Gresik', 'ACTIVE', '2026-01-01 14:54', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000399' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000385') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000385', '3201000000000000', 'Muhammad Ikmal Zainurrasyid  Rosyadi', '2000-01-01', 'Ibu Kandung', '085862840947', 'zaynrasyeed@gmail.com', 'Perum. Graha Arradea Blok F No. 34', 'Bogor', 'ACTIVE', '2025-12-27 15:03', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000385' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000384') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000384', '3201000000000000', 'L. Ihwan Marzuki', '2000-01-01', 'Ibu Kandung', '081558692640', 'lrizkimaulana00@gmail.com', 'Munte lombok', 'Mataram', 'ACTIVE', '2025-12-27 15:03', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000384' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000383') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000383', '3201000000000000', 'Alya Amara Zahra', '2000-01-01', 'Ibu Kandung', '082122108441', 'alya.amara.zahra@gmail.com', 'KP.Cimanggis RT 002/009', 'Bogor', 'ACTIVE', '2025-12-27 14:59', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000383' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000375') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000375', '3201000000000000', 'Narapati brilian  anorawi', '2000-01-01', 'Ibu Kandung', '085777675677', 'alkan2377@gmail.com', 'jl insinyur hj djuanda no. 78', 'bogor', 'ACTIVE', '2025-12-27 14:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000375' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000372') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000372', '3201000000000000', 'MUHAMAD  ILHAM', '2000-01-01', 'Ibu Kandung', '081234578910', 'ILHAMAJALAH@GMAIL.COM', 'JALAN BARENG ASAL BAHAGIA NO 2', 'JOMBANG', 'ACTIVE', '2025-12-27 14:21', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000372' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000371') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000371', '3201000000000000', 'Toni  Rastafara', '2000-01-01', 'Ibu Kandung', '085773992010', 'avilaqowi@gmail.com', 'Jl.Swadaya II, RT 006/05, No 21A, T', 'Jakarta Selatan', 'ACTIVE', '2025-12-27 14:06', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000371' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000365') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000365', '3201000000000000', 'AUDRI STEFANOY', '2000-01-01', 'Ibu Kandung', '081389580844', 'AUDRISTEFANOY@GMAIL.COM', 'JL KYAI TAPA NO.34E', 'JAKARTA', 'ACTIVE', '2025-12-27 13:53', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000365' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000363') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000363', '3201000000000000', 'Mandu Chaironi S', '2000-01-01', 'Ibu Kandung', '088905563181', 'Mandu27@gmail.com', 'Sunter Jaya VII A No 37', 'Jakarta Utara', 'ACTIVE', '2025-12-27 13:43', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000363' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000360') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000360', '3201000000000000', 'muhammad  virzie', '2000-01-01', 'Ibu Kandung', '081388495827', 'muhammadvirziee@gmail.com', 'jl menteng barat no.34C rt,14 rw 11', 'jakarta pusat', 'ACTIVE', '2025-12-27 13:04', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000360' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000357') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000357', '3201000000000000', 'Syauqia Jannata', '2000-01-01', 'Ibu Kandung', '081298859279', 'qiasyari@gmail.com', 'Pondok Mitra Lestari JL. Akasia blo', 'Bekasi', 'ACTIVE', '2025-12-27 11:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000357' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000343') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000343', '3201000000000000', 'Pinarti Rahayu', '2000-01-01', 'Ibu Kandung', '081322555852', 'ryushio18@gmail.com', 'Villa Selecta Blok E No.3 RT 001 RW', 'Kota Tangerang Selatan', 'ACTIVE', '2025-12-27 10:53', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000343' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000342') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000342', '3201000000000000', 'Siti  marwah', '2000-01-01', 'Ibu Kandung', '083811667426', 'rittaamelia@gmail.com', 'Jl. Raya sirkuit sentul No 48', 'bogor', 'ACTIVE', '2025-12-27 10:43', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000342' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000339') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000339', '3201000000000000', 'Hendra Saputra', '2000-01-01', 'Ibu Kandung', '087895814455', 'hendrasaputra@gmail.com', 'Jl Raya Karanggan, Desa Puspasari, ', 'Kabupaten Bogor', 'ACTIVE', '2025-12-27 10:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000339' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000335') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000335', '3201000000000000', 'jodi stefen', '2000-01-01', 'Ibu Kandung', '082294531164', 'jodistefen@gmmailcom', 'jalan kembang sepatu 5 , no17A RT.1', 'JAKARTA sELATAN', 'ACTIVE', '2025-12-27 10:09', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000335' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000334') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000334', '3201000000000000', 'meilita azzahra putri', '2000-01-01', 'Ibu Kandung', '089501306264', 'meilitaputri35@gmail.com', 'Kp. Tlajung Desa. Wanaherang Kec. G', 'Kab. Bogor', 'ACTIVE', '2025-12-27 09:34', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000334' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000330') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000330', '3201000000000000', 'Azizi Shafa', '2000-01-01', 'Ibu Kandung', '089507781814', 'Riantyenta@gmail.com', 'KP. KADUMANGGU SIRKUIT SENTUL', 'Bogor', 'ACTIVE', '2025-12-27 09:16', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000330' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000322') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000322', '3201000000000000', 'Radawiah Radawiah', '2000-01-01', 'Ibu Kandung', '082251781557', 'iestiqamah123@gmail.com', 'KARUH NO. 17, RT. 003, RW. 000, KEL', 'Balangan', 'ACTIVE', '2025-12-27 01:47', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000322' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000312') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000312', '3201000000000000', 'Robertu Ndruru', '2000-01-01', 'Ibu Kandung', '083129771346', '2310101064.rafael@tazkia.student.ac.id', 'Desa Hilifalawu, Kec. Maniamolo, Ka', 'Nias Selatan', 'ACTIVE', '2025-12-27 00:26', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000312' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000311') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000311', '3201000000000000', 'Hamizan Suheil Ibnu Hadi', '2000-01-01', 'Ibu Kandung', '081381229451', 'hamizansuheil@gmail.com', 'Jl.H.Miun No.107 RT/RW 01/07, Sukma', 'Depok', 'ACTIVE', '2025-12-26 23:37', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000311' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000310') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000310', '3201000000000000', 'Sinshe Wahyu Hidayat', '2000-01-01', 'Ibu Kandung', '081219908252', '2310101074.naniek@student.tazkia.ac.id', 'perumahan bogor raya residence jl m', 'bogor', 'ACTIVE', '2025-12-26 23:30', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000310' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000307') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000307', '3201000000000000', 'syifa maulida', '2000-01-01', 'Ibu Kandung', '0813456673344', 'syifa24@gmail.com', 'jl. kiray rt02 rw 13', 'Depok', 'ACTIVE', '2025-12-26 23:23', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000307' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000304') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000304', '3201000000000000', 'Cut Mutia Meganova', '2000-01-01', 'Ibu Kandung', '088210726564', 'cmutia12@gmail.com', 'Bukit waringin Blok E 13/6', 'Bogor', 'ACTIVE', '2025-12-26 22:17', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000304' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000300') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000300', '3201000000000000', 'Winny Iftari', '2000-01-01', 'Ibu Kandung', '085297000245', 'faisalarieabadi@gmail.com', 'Jalan Pangeran Sogiri Perumahan Vin', 'KOTA BOGOR', 'ACTIVE', '2025-12-26 21:25', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000300' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000297') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000297', '3201000000000000', 'Fany  Wulandari', '2000-01-01', 'Ibu Kandung', '081378116876', 'oriflame.ac.id@gmail.com', 'Jl. Garuda bukit nenas', 'Dumai', 'ACTIVE', '2025-12-26 17:57', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000297' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000292') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000292', '3201000000000000', 'Fajar Aditya Pratama', '2000-01-01', 'Ibu Kandung', '081284064305', 'fajaradityapratama@gmail.com', 'jl. pelatnas PBSI gg. damai cipayun', 'jakarta timur', 'ACTIVE', '2025-12-26 17:38', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000292' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000288') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000288', '3201000000000000', 'Tri Suci Rachmadhani', '2000-01-01', 'Ibu Kandung', '0811604703', 'yudhanugrahaopsships@gmail.com', 'Jalan Gaperta LK1 no 259 D Medan', 'Medan', 'ACTIVE', '2025-12-26 16:10', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000288' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000285') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000285', '3201000000000000', 'Nuni  Indasari', '2000-01-01', 'Ibu Kandung', '082312427669', 'nuniindasari10@gmail.com', 'Kp.Curug Rt 02 Rw 09, Desa Bojong k', 'Bogor', 'ACTIVE', '2025-12-26 15:18', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000285' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000284') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000284', '3201000000000000', 'Alya Putri', '2000-01-01', 'Ibu Kandung', '081282131415', 'caalya12@gmail.com', 'Kp. Kaung luwuk Rt 03 Rw 09, Desa B', 'Bogor', 'ACTIVE', '2025-12-26 15:00', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000284' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000280') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000280', '3201000000000000', 'Afiifah Candy', '2000-01-01', 'Ibu Kandung', '085821316652', 'afiifahcandy@gmail.com', 'Cikarang Utara, Bekasi, Jawa Barat', 'Bekasi', 'ACTIVE', '2025-12-26 10:16', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000280' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000278') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000278', '3201000000000000', 'Andi Nadya Kusuma Hakim Nadya', '2000-01-01', 'Ibu Kandung', '0882146379154', 'nasifwear74@gmail.com', 'Jl Telaga Bodas Raya C 41', 'Bekasi', 'ACTIVE', '2025-12-25 22:46', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000278' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000277') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000277', '3201000000000000', 'nyai  inun', '2000-01-01', 'Ibu Kandung', '085167272882', 'inunnyai@gmail.com', 'jl sutomo baru', 'Bogor', 'ACTIVE', '2025-12-25 20:54', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000277' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000274') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000274', '3201000000000000', 'Hamidu Hamidu', '2000-01-01', 'Ibu Kandung', '085945710618', 'irmalahirmalah9@gmail.com', 'Kp.bunder RT 09/02 desa bunder, kec', 'Kabupaten Tangerang', 'ACTIVE', '2025-12-25 19:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000274' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000268') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000268', '3201000000000000', 'Devi  Destiana', '2000-01-01', 'Ibu Kandung', '089503587353', 'devidestiana0712@gmail.com', 'Lingkungan 01 Ciriung rt.04/rw.03', 'Kabupaten Bogor', 'ACTIVE', '2025-12-25 15:12', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000268' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000265') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000265', '3201000000000000', 'windi aulia', '2000-01-01', 'Ibu Kandung', '085211417735', 'windi.aulia2515@gmail.com', 'kp.pabuaran rt 003/007 cicadas gn p', 'bogor', 'ACTIVE', '2025-12-25 14:46', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000265' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000264') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000264', '3201000000000000', 'Pajur  Rohman', '2000-01-01', 'Ibu Kandung', '081382151763', 'pajurrohman1999@gmail.com', 'Kp. Padurenan Pabuaran - Cibinong', 'Kabupaten Bogor', 'ACTIVE', '2025-12-25 14:17', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000264' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000261') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000261', '3201000000000000', 'Fachry Firdaus', '2000-01-01', 'Ibu Kandung', '081317157679', 'rysnofficial@gmail.com', 'kp pabuaran rt 02/08 cicadas gn put', 'BOGOR', 'ACTIVE', '2025-12-25 14:10', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000261' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000260') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000260', '3201000000000000', 'Ani Maryani', '2000-01-01', 'Ibu Kandung', '081384901495', 'Aniaryani522@gmail.com', 'Ciawi, kabupaten Bogor', 'Bogor', 'ACTIVE', '2025-12-25 12:23', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000260' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000259') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000259', '3201000000000000', 'Muryani Muryani', '2000-01-01', 'Ibu Kandung', '081213034134', 'aisya.pede965@gmail.com', 'Griya Bukit Jaya Blok L1 No.26 RT 0', 'Bogor', 'ACTIVE', '2025-12-25 11:26', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000259' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000247') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000247', '3201000000000000', 'Shania Indira Natio', '2000-01-01', 'Ibu Kandung', '081574951949', 'sintarianty2@gmail.com', 'KP KADUMANGGU RT 001 RW 001', 'Bogor', 'ACTIVE', '2025-12-25 01:34', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000247' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000244') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000244', '3201000000000000', 'Anni  Syarah', '2000-01-01', 'Ibu Kandung', '087780223270', 'annisyarah@gmail.com', 'Cimanggis, Bojong Gede, Bogor', 'Bogor', 'ACTIVE', '2025-12-25 01:24', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000244' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000241') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000241', '3201000000000000', 'Afridah  Hutabarat', '2000-01-01', 'Ibu Kandung', '081399795282', 'Afridahutabarat@gmail.com', 'Cimanggis, Bojong Gede, Bogor, Indo', 'Bogor', 'ACTIVE', '2025-12-25 00:45', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000241' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000239') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000239', '3201000000000000', 'shania Indira', '2000-01-01', 'Ibu Kandung', '082332330314', 'sintarianty07@gmail.com', 'KP KADUMANGGU RT 001 RW 001', 'Bogor', 'ACTIVE', '2025-12-25 00:20', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000239' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000236') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000236', '3201000000000000', 'Natio Harland', '2000-01-01', 'Ibu Kandung', '089518852732', 'Ntarianty0704@gmail.com', 'KP KADUMANGGU RT 001 RW 001 KECAMAT', 'Bogor', 'ACTIVE', '2025-12-24 23:41', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000236' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000231') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000231', '3201000000000000', 'Riva Kanziya Hasan', '2000-01-01', 'Ibu Kandung', '081314552932', 'rivakanziya@gmail.com', 'Jl Pabuaran RT02/RW03 Kec. Tanah Sa', 'Kota Bogor', 'ACTIVE', '2025-12-24 19:21', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000231' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000226') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000226', '3201000000000000', 'Harsen Surya Permana', '2000-01-01', 'Ibu Kandung', '081280681458', 'restidania1721@gmail.com', 'Kp. dukuh Rt 03 rw 02 desa pasir mu', 'KAB. BOGOR', 'ACTIVE', '2025-12-24 15:53', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000226' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000222') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000222', '3201000000000000', 'balqis aulia', '2000-01-01', 'Ibu Kandung', '083804303150', 'balqisfida@gmail.com', 'Perum. Villa Bogor Indah', 'Bogor', 'ACTIVE', '2025-12-24 13:46', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000222' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000220') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000220', '3201000000000000', 'Nurul Imaroh', '2000-01-01', 'Ibu Kandung', '085695343220', 'rakafasya78@gmail.com', 'Perumahan IPB Alam Sinar Sari', 'Kota Bogor', 'ACTIVE', '2025-12-24 12:47', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000220' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000217') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000217', '3201000000000000', 'Zulkifli Qodir', '2000-01-01', 'Ibu Kandung', '081282609432', 'Zulkifli40@gmail.com', 'Kebon Jeruk, Jakarta', 'Jakarta', 'ACTIVE', '2025-12-24 12:25', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000217' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000215') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000215', '3201000000000000', 'Syifa Fauziah Hasanah', '2000-01-01', 'Ibu Kandung', '0895332427257', 'syifafauziahhasanah@gmail.com', 'Sidamukti, Desa Sukamaju, Kecamatan', 'Depok', 'ACTIVE', '2025-12-24 10:25', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000215' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000213') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000213', '3201000000000000', 'Putri  Agnesia', '2000-01-01', 'Ibu Kandung', '081336617495', 'pagnesia30@gmail.com', 'Kecamatan Citeureup, Rt 002 rw 004', 'Bogor', 'ACTIVE', '2025-12-24 05:32', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000213' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000211') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000211', '3201000000000000', 'Rizwar Adnanbyantara', '2000-01-01', 'Ibu Kandung', '087825901834', 'rizwaradnan@gmail.com', 'Jl. Pantai Sanur V No. 7, RT.7/RW.1', 'Jakarta', 'ACTIVE', '2025-12-24 01:43', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000211' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000210') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000210', '3201000000000000', 'Melda Saripah', '2000-01-01', 'Ibu Kandung', '081284812276', 'MeldaSaripah124@gmail.com', 'Kp.Cimanggurang Rt01/Rw02', 'Bogor', 'ACTIVE', '2025-12-23 22:46', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000210' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000205') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000205', '3201000000000000', 'Zessika Permana', '2000-01-01', 'Ibu Kandung', '089618216572', 'restidania17@gmail.com', 'Kp. dukuh rt 03 rw 02', 'KAB. BOGOR', 'ACTIVE', '2025-12-23 22:14', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000205' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000195') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000195', '3201000000000000', 'Melda Saripa', '2000-01-01', 'Ibu Kandung', '083199828762', 'MeldaSaripah123@gmail.com', 'Kp. Cimanggurang Rt01/Rw01', 'Bogor', 'ACTIVE', '2025-12-23 21:52', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000195' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000186') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000186', '3201000000000000', 'Siti Fauziah', '2000-01-01', 'Ibu Kandung', '085180772764', 'fauziya05@gmail.com', 'Kp.Curug Rt 02 Rw 09, Desa Bojong k', 'Bogor', 'ACTIVE', '2025-12-23 15:01', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000186' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000183') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000183', '3201000000000000', 'Siti  Fauziya', '2000-01-01', 'Ibu Kandung', '081282134526', 'alyacaal12@gmail.com', 'Kp.Curug Rt 02 Rw 09, Desa Bojong k', 'Bogor', 'ACTIVE', '2025-12-23 09:02', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000183' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000181') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000181', '3201000000000000', 'Jilan Sajida', '2000-01-01', 'Ibu Kandung', '082125098897', 'jilansajida3@gmail.com', 'Perumahan Cibinong City Blok S/8', 'Bogor', 'ACTIVE', '2025-12-20 13:45', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000181' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000180') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000180', '3201000000000000', 'mohammad harits dzakwan ristyawan', '2000-01-01', 'Ibu Kandung', '081324546907', 'mozari2005@gmail.com', 'jalan Ir. H. Djuanda No. 78 Sentul ', 'Bogorr', 'ACTIVE', '2025-12-20 13:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000180' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000175') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000175', '3201000000000000', 'Muhammad Arifin', '2000-01-01', 'Ibu Kandung', '081317028026', 'muhammadarifien080601@gmail.com', 'Sungai aur pasaman barat Sumatra ba', 'Padang', 'ACTIVE', '2025-12-20 11:21', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000175' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000174') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000174', '3201000000000000', 'Zahwa  Nabila', '2000-01-01', 'Ibu Kandung', '082210012915', 'nabilazahwa654@gmail.com', 'Jalan swadaya perumahan puri kencan', 'KOTA DEPOK', 'ACTIVE', '2025-12-20 10:32', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000174' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000173') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000173', '3201000000000000', 'Muhammad Hidayatullah  Hadif', '2000-01-01', 'Ibu Kandung', '0895602729018', 'mhidayatullahhadif@gmail.com', 'Jl.Banjarmasin No 17', 'Padang', 'ACTIVE', '2025-12-20 10:25', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000173' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000172') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000172', '3201000000000000', 'Siti Nisrina Az Zahra', '2000-01-01', 'Ibu Kandung', '085921659395', '2310101158.nisrina@student.tazkia.ac.id', 'Jln. Raya Cibanteng Proyek, Desa Ci', 'Bogor', 'ACTIVE', '2025-12-20 09:09', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000172' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000171') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000171', '3201000000000000', 'Sahla Asila', '2000-01-01', 'Ibu Kandung', '089519780480', 'asilasahla8@gmail.com', 'Jl. Ir. H. Juanda No.78 Sentul, Cit', 'Bogor', 'ACTIVE', '2025-12-20 09:07', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000171' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000164') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000164', '3201000000000000', 'Dean Ardika', '2000-01-01', 'Ibu Kandung', '085703465334', 'deanardika23@gmail.com', '3202162312040003', 'KABUPATEN SUKABUMI', 'ACTIVE', '2025-12-20 08:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000164' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000163') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000163', '3201000000000000', 'tes tes', '2000-01-01', 'Ibu Kandung', '0876543211', 'tes@gmail.com', 'City, Jl. Ir. H. Djuanda No. 78 Sen', 'bogor', 'ACTIVE', '2025-12-14 14:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000163' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000156') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000156', '3201000000000000', 'Mulya  Darma', '2000-01-01', 'Ibu Kandung', '082256236338', 'mulyadarma57@gmail.com', 'Jl.makmur Rt. 007 kel. Basilam baru', 'Dumai', 'ACTIVE', '2025-12-06 08:21', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000156' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000155') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000155', '3201000000000000', 'Alfat surya saputra', '2000-01-01', 'Ibu Kandung', '082269557342', 'alfatsurya46@gmail.com', 'Griya Alam', 'Bogor', 'ACTIVE', '2025-11-29 09:52', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000155' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000145') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000145', '3201000000000000', 'Teuku Ahmad Thaffan', '2000-01-01', 'Ibu Kandung', '081286061713', 'Ahmadthaffan@gmail.com', 'Gandaria timur bojonggede', 'Bogor', 'ACTIVE', '2025-11-28 20:58', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000145' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000144') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000144', '3201000000000000', 'Arie Yudha Nugraha', '2000-01-01', 'Ibu Kandung', '08116153143', '2510101010.arie@student.tazkia.ac.id', 'Vinca Village Jalan Vinca V no Tana', 'KOTA BOGOR', 'ACTIVE', '2025-11-28 20:55', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000144' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000142') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000142', '3201000000000000', 'Rafdy Adrian Ilham Ramadan', '2000-01-01', 'Ibu Kandung', '083161936680', 'rafdyadrian2005@gmail.com', 'Jl. Gurita 1, Gg. Ulam Sari No. 4 ,', 'Denpasar', 'ACTIVE', '2025-11-25 23:49', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000142' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000141') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000141', '3201000000000000', 'Mohamad ilham jaya kusumah Jaya kusumah', '2000-01-01', 'Ibu Kandung', '085781371702', 'kusumahi386@gmail.com', 'Jalan Insinyur, RT.4/RW.2, Kampung ', 'Tangerang', 'ACTIVE', '2025-11-20 11:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000141' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000135') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000135', '3201000000000000', 'Muhammad Rizki Fauzi', '2000-01-01', 'Ibu Kandung', '082127338351', 'rizkifauzi3108@gmail.com', 'Jln.Otista Gang Warriors no.150 Kun', 'Kuningan', 'ACTIVE', '2025-11-19 18:11', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000135' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000133') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000133', '3201000000000000', 'sabitah Agnissa Padmarini', '2000-01-01', 'Ibu Kandung', '082311334005', 'sabitahagnissa@gmail.com', 'GDC SEKTOR GARDENIA BLOK N4 NO 6', 'Depok', 'ACTIVE', '2025-11-19 18:06', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000133' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000132') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000132', '3201000000000000', 'Nabilah Zahrah Pulungan', '2000-01-01', 'Ibu Kandung', '085697344163', 'Nabilahzhrap06@gmail.com', 'Jalan smp 135 no.38a rt17/rw07', 'Jakarta Timur', 'ACTIVE', '2025-11-19 17:58', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000132' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000131') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000131', '3201000000000000', 'Muhammad Latif Hafidz', '2000-01-01', 'Ibu Kandung', '088973639319', 'muhhlatif@gmail.com', 'Jl Nuri 3  Griya Alam Sentosa Cileu', 'Bogor', 'ACTIVE', '2025-11-19 17:47', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000131' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000130') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000130', '3201000000000000', 'Alya  Ledwina Gozali', '2000-01-01', 'Ibu Kandung', '085777666088', 'alyaledwinaghozali@gmail.com', 'Jl batara kampung rambay rt 02/06', 'Bogor', 'ACTIVE', '2025-11-19 17:40', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000130' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000129') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000129', '3201000000000000', 'Narapati Brilian Anorawi', '2000-01-01', 'Ibu Kandung', '085707609831', '2310101138.narapati@student.tazkia.ac.id', 'Jln.Lasap no.10 RT01/RW01 Menala, K', 'Taliwang', 'ACTIVE', '2025-11-15 13:48', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000129' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000127') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000127', '3201000000000000', 'Muhammad Ikmal  Zainurrasyid Rosyadi', '2000-01-01', 'Ibu Kandung', '085862840937', 'Zaynrasyeed@gmail.com', 'Perumahan Graha Arradea, Blok.F No.', 'Bogor', 'ACTIVE', '2025-11-15 13:40', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000127' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000126') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000126', '3201000000000000', 'Ahmad  Syamil Muzzakkir', '2000-01-01', 'Ibu Kandung', '085219669655', '2310101133.syamil@tazkia.ac.id', 'Jl Mulawarman Kel Teritip', 'Balikpapan', 'ACTIVE', '2025-11-15 13:39', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000126' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000125') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000125', '3201000000000000', 'Anisa Maharani', '2000-01-01', 'Ibu Kandung', '088742609585', 'anisamaharani1020@gmail.com', 'JL. SRIWEDARI', 'Dumai', 'ACTIVE', '2025-11-15 13:39', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000125' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000124') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000124', '3201000000000000', 'Syifa Nurfiyani Arfan', '2000-01-01', 'Ibu Kandung', '0881011649879', 'syifanurfiyani@gmail.com', 'Kp Kaum Rt 006/Rw 003', 'Purwakarta', 'ACTIVE', '2025-11-15 13:39', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000124' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000120') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000120', '3201000000000000', 'Ervina Icha Marliani', '2000-01-01', 'Ibu Kandung', '082215768331', 'pina.ervina05@gmail.com', 'Perum keledang mas baru Blok BA No ', 'Samarinda', 'ACTIVE', '2025-11-15 13:37', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000120' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000119') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000119', '3201000000000000', 'Yasmin Zafira', '2000-01-01', 'Ibu Kandung', '085210312634', '2310101164.yasmin@student.tazkia.ac.co.if', 'Jl alternatif cibubur km4 citra gra', 'Bekasi', 'ACTIVE', '2025-11-15 13:37', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000119' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000118') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000118', '3201000000000000', 'Khalisa Alayya', '2000-01-01', 'Ibu Kandung', '082299926722', '2310101149.khalisa@student.tazkia.ac.id', 'Pemda, Cibinong', 'Bogor', 'ACTIVE', '2025-11-15 13:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000118' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000117') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000117', '3201000000000000', 'Yasminnasywa Iskandar', '2000-01-01', 'Ibu Kandung', '081371834786', '2310101146.yasminnasywa@student.tazkia.ac.id', 'Jl. Flamboyan', 'Pekanbaru', 'ACTIVE', '2025-11-15 13:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000117' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000116') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000116', '3201000000000000', 'Siti  Rosdianti', '2000-01-01', 'Ibu Kandung', '081345002356', 'dhiyan282@gmail.com', 'Jl.Nusantara', 'Berau', 'ACTIVE', '2025-11-15 13:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000116' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000115') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000115', '3201000000000000', 'Raihan ichsan Effendy', '2000-01-01', 'Ibu Kandung', '087880705448', 'raihan.ichsan77@gmail.com', 'Taman pegangsaan indah O/30, jakart', 'Kelapa gading', 'ACTIVE', '2025-11-15 13:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000115' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000114') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000114', '3201000000000000', 'Arya Satria  Wiraguna', '2000-01-01', 'Ibu Kandung', '085157766847', 'wiragunaaryasatria@gmail.com', 'JL. POROS DESA SUKAMAJU KECAMATAN T', 'KUTAI KARTANEGARA', 'ACTIVE', '2025-11-15 13:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000114' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000113') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000113', '3201000000000000', 'Qonna&#39;atu  Mulhimma', '2000-01-01', 'Ibu Kandung', '0895704300390', 'qonnaatumulhimma@gmail.com', 'Jl. Kapal layar 5 loktuan Bontang K', 'Kota Bontang', 'ACTIVE', '2025-11-15 13:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000113' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000112') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000112', '3201000000000000', 'Khairunnisa Abdurrahman', '2000-01-01', 'Ibu Kandung', '082299333202', 'khrnnisa.abd@gmail.com', 'Jl. Betung VIII no. 407 Rt008/Rw008', 'Jakarta Timur', 'ACTIVE', '2025-11-15 13:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000112' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000111') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000111', '3201000000000000', 'mohammad ristyawan', '2000-01-01', 'Ibu Kandung', '08888483873', 'mozari2004@gmail.com', 'tanah Grogot, padang pengrapay di l', 'tanah Grogot', 'ACTIVE', '2025-11-15 13:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000111' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000110') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000110', '3201000000000000', 'Muhamad Farid Al Fathdry', '2000-01-01', 'Ibu Kandung', '081234500812', 'muhamadfaridalfathdry@gmail.com', 'Yasmin Raya Street Sector 3', 'Bogor', 'ACTIVE', '2025-11-15 13:35', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000110' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000104') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000104', '3201000000000000', 'Rafli Kurniawan', '2000-01-01', 'Ibu Kandung', '085365468970', 'raflikurniawan4677@gmail.com', 'Gg linggasari', 'Dumai', 'ACTIVE', '2025-11-15 10:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000104' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000096') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000096', '3201000000000000', 'Muhammad Naqib', '2000-01-01', 'Ibu Kandung', '083898301061', 'muhammadnaqib131@gmail.com', 'Jl. Ir. H. Djuanda No. 78 Sentul', 'Kota Dumai', 'ACTIVE', '2025-11-15 10:19', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000096' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000095') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000095', '3201000000000000', 'Intan Ayu Muzakir', '2000-01-01', 'Ibu Kandung', '081310084661', 'intanayumuzakirayu@gmail.com', 'KP.LEGOK GAOK 002/005 KADUMANGGU BA', 'Bogor', 'ACTIVE', '2025-11-15 10:18', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000095' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000094') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000094', '3201000000000000', 'M Hafizh Nur Ihsan', '2000-01-01', 'Ibu Kandung', '081258254648', '2310101113.hafizh@student.tazkia.ac.id', 'Babulu Darat', 'Balikpapan', 'ACTIVE', '2025-11-15 10:17', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000094' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000093') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000093', '3201000000000000', 'Zakiyah Azizah', '2000-01-01', 'Ibu Kandung', '085652119411', 'zakiyahnurazizah@gmail.com', 'perumahan muslim bukit azzikra sent', 'sentul', 'ACTIVE', '2025-11-15 10:17', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000093' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000092') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000092', '3201000000000000', 'Muhammad Fachry Fatahilla Anjarang', '2000-01-01', 'Ibu Kandung', '081398073969', 'anjarangmuhammadfachry@gmail.com', 'perm griya cendekia blok D1/47 002/', 'kabupaten bogor', 'ACTIVE', '2025-11-15 10:17', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000092' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000091') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000091', '3201000000000000', 'Sumayyah Nurussyahidah', '2000-01-01', 'Ibu Kandung', '081340535710', 'sumayyah160800@gmail.com', 'perumahan muslim bukit az-zikra, jl', 'bogor', 'ACTIVE', '2025-11-15 10:16', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000091' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000090') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000090', '3201000000000000', 'aulia rokhmatunnisa nursyahidah', '2000-01-01', 'Ibu Kandung', '081292816626', 'auliakoma11@gmail.com', 'jl. kh maisin no.25 rt.005/rw.016 k', 'jakarta timur', 'ACTIVE', '2025-11-15 10:16', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000090' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000089') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000089', '3201000000000000', 'Fillea Retia  Yuma', '2000-01-01', 'Ibu Kandung', '089604452435', 'fillearetiayuma@gmail.com', 'perumahan jatijajar blok e 6 no 1', 'Depok', 'ACTIVE', '2025-11-15 10:16', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000089' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000086') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000086', '3201000000000000', 'Rahmatul Fajriani', '2000-01-01', 'Ibu Kandung', '081360618177', 'fajrianihani13@gmail.com', 'Jl. Harapan no.18, kwalian.', 'Siak', 'ACTIVE', '2025-11-15 10:13', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000086' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000085') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000085', '3201000000000000', 'Hunaydah hunay', '2000-01-01', 'Ibu Kandung', '08818033037', 'hunaydah2102003@gmail.com', 'Griya Bogor Asri', 'Bogor', 'ACTIVE', '2025-11-15 10:13', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000085' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000075') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000075', '3201000000000000', 'wahyu afrianda', '2000-01-01', 'Ibu Kandung', '081232430569', 'afriandawahyu1@gmail.con', 'wonosalam,rt 10, rw 003, desa suka ', 'siak', 'ACTIVE', '2025-11-15 08:36', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000075' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000074') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000074', '3201000000000000', 'Fairuz Taqiyya', '2000-01-01', 'Ibu Kandung', '087847070041', 'taqiyyafairuz00@gmail.com', 'bogor citereup Kp.kamurang  desa pu', 'bogor', 'ACTIVE', '2025-11-15 08:34', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000074' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000073') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000073', '3201000000000000', 'Hasna Afifah', '2000-01-01', 'Ibu Kandung', '08131322655', 'hasnafifah.105@gmail.com', 'Permata Regensi Blok C1 No.11 jalan', 'Bekasi', 'ACTIVE', '2025-11-15 08:33', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000073' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000072') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000072', '3201000000000000', 'Abdul Fattah Fauzan Azzubair', '2000-01-01', 'Ibu Kandung', '081298214645', '2310101116.fattah@tazkia.student.ac.id', 'Tangerang, Banten', 'Tangerang', 'ACTIVE', '2025-11-15 08:32', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000072' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000071') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000071', '3201000000000000', 'Yusuf  Rizqi', '2000-01-01', 'Ibu Kandung', '082135070046', 'yarahmad2905@gmail.com', 'Kel Cilangkap kec tapos jl banjaran', 'Depok', 'ACTIVE', '2025-11-15 08:32', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000071' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000070') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000070', '3201000000000000', 'Rahma  Fitriani', '2000-01-01', 'Ibu Kandung', '081371966191', 'ftrianirhma@gmail.com', 'Jl. Sekolah km.8 RT 001 RW 003 Desa', 'Siak', 'ACTIVE', '2025-11-15 08:30', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000070' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000069') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000069', '3201000000000000', 'muhammad Ridho', '2000-01-01', 'Ibu Kandung', '082117332447', 'mridhoprasetyo03@gmail.com', 'Jalan batin galang, GG setia', 'Perawang', 'ACTIVE', '2025-11-15 08:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000069' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000068') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000068', '3201000000000000', 'Fath Hana Rosyada Putri Supraja', '2000-01-01', 'Ibu Kandung', '085724917672', 'fathhanarosyada@gmail.com', 'Pojok IV Sendangagung Minggir Slema', 'Yogyakarta', 'ACTIVE', '2025-11-15 08:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000068' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000067') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000067', '3201000000000000', 'Nashiruddin Hanif', '2000-01-01', 'Ibu Kandung', '081319934529', 'nashiruddinhanif020204@gmail.com', 'Perum. Telaga Harapan D2/27, Telaga', 'Kab. Bekasi', 'ACTIVE', '2025-11-15 08:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000067' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000066') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000066', '3201000000000000', 'AFIF ZAIN  AL GHOZI', '2000-01-01', 'Ibu Kandung', '085780187061', '2310101130.afif@student.tazkia.ac.id', 'Kp TEGALWARU rt4/rw5 ciampea Bogor', 'Bogor', 'ACTIVE', '2025-11-15 08:27', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000066' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000065') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000065', '3201000000000000', 'Laila Muzdalifah', '2000-01-01', 'Ibu Kandung', '081372482944', 'ellaeldya@gmail.com', 'RT 002, RW 003, Kampung Bandar Peda', 'Siak', 'ACTIVE', '2025-11-15 08:27', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000065' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000064') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000064', '3201000000000000', 'Mazidah Ulfah', '2000-01-01', 'Ibu Kandung', '082284781246', 'mrzea00@gmail.com', 'Jl utama sei tengah, kec. sabak auh', 'Siak Sri Indrapura', 'ACTIVE', '2025-11-15 08:27', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000064' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000063') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000063', '3201000000000000', 'Nabil Ali', '2000-01-01', 'Ibu Kandung', '085156208077', 'alisalaf70@gmail.com', 'Taman century 2 blok m no 25 pekayo', 'KOTA BEKASI', 'ACTIVE', '2025-11-15 08:27', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000063' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000062') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000062', '3201000000000000', 'Fatih Hibatillah', '2000-01-01', 'Ibu Kandung', '081387186677', 'fatih.hibatillah@gmail.com', 'KP. Sinarwangi, 002/006, kel/Desa s', 'Bogor', 'ACTIVE', '2025-11-15 08:27', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000062' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000061') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000061', '3201000000000000', 'Nurul Aini  Aini', '2000-01-01', 'Ibu Kandung', '085272722738', 'nurulaini290304@gmail.com', 'Desa seminai kecamatan Kerinci kana', 'Kota Pekan Baru', 'ACTIVE', '2025-11-15 08:26', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000061' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000060') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000060', '3201000000000000', 'Asnawati Asnawati', '2000-01-01', 'Ibu Kandung', '081374158830', 'watiasna981@gmail.com', 'Teluk mesjid kecamatan sungai apit ', 'Siak', 'ACTIVE', '2025-11-15 08:26', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000060' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000059') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000059', '3201000000000000', 'Nahida Laily  Mazidah', '2000-01-01', 'Ibu Kandung', '085817188461', 'nahidamazidah@gmail.com', 'Perumahan Depok Alam Lestari Blok c', 'Bogor', 'ACTIVE', '2025-11-15 08:25', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000059' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000058') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000058', '3201000000000000', 'Nadhrah Ilmina Dilsyaadi', '2000-01-01', 'Ibu Kandung', '087720050605', 'ilminanadhrah@gmail.com', 'Jl. Penggalang III No. 47 RT 29, Ke', 'Balikpapan', 'ACTIVE', '2025-11-15 08:23', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000058' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000057') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000057', '3201000000000000', 'Teguh Masykuri', '2000-01-01', 'Ibu Kandung', '082223775530', 'teguhmasykuri11@gmail.com', 'Sabak Permai RT/RW 004/002, kecamat', 'Siak', 'ACTIVE', '2025-11-15 08:23', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000057' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000056') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000056', '3201000000000000', 'Halwa Zandria  Abdilah', '2000-01-01', 'Ibu Kandung', '081997249507', 'dorrrjandor@gmail.com', 'Jl. Kenanga IV no.21 RT 004 RW 005', 'Depok', 'ACTIVE', '2025-11-15 08:22', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000056' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000055') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000055', '3201000000000000', 'Lutfia Nailul Autar', '2000-01-01', 'Ibu Kandung', '08881392547', 'lutlut195@gmail.com', 'Jln Kapten Dasuki Bakri Kp Cibatok ', 'Bogor', 'ACTIVE', '2025-11-15 08:21', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000055' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000054') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000054', '3201000000000000', 'Naurahusna Aqila', '2000-01-01', 'Ibu Kandung', '081779192760', 'nourah.aqilaa@gmail.com', 'Jl Pemuda No 29 Kav 09 Kranji', 'Bekasi', 'ACTIVE', '2025-11-15 08:12', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000054' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000053') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000053', '3201000000000000', 'Jihan Sabrina', '2000-01-01', 'Ibu Kandung', '083832352728', '2310101067.jihan@student.tazkia.ac.id', 'jl.kiray, rt 3/ 20', 'Depok', 'ACTIVE', '2025-11-15 00:24', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000053' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000051') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000051', '3201000000000000', 'Rafael Generasi Ndruru', '2000-01-01', 'Ibu Kandung', '082295900031', 'rafaelgenerasindruru02@gmail.com', 'Jalan Juana, RT.02/RW.08, Kebon Ped', 'Kota Bogor', 'ACTIVE', '2025-11-14 20:56', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000051' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000050') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000050', '3201000000000000', 'Shofa Audiya', '2000-01-01', 'Ibu Kandung', '085156817565', 'audiishofa@gmai.com', 'Jl. Baru raya', 'Bandung', 'ACTIVE', '2025-11-14 20:42', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000050' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000044') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000044', '3201000000000000', 'Shanum Ayesa Gasyari', '2000-01-01', 'Ibu Kandung', '082117363725', '2310101059.shanum@student.tazkia.ac.id', 'Pondok Mitra Lestari blok C6/11a Ja', 'Bekasi', 'ACTIVE', '2025-11-14 20:34', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000044' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000042') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000042', '3201000000000000', 'Resti Dania  Permana', '2000-01-01', 'Ibu Kandung', '08989247468', 'restii.dania17@gmail.com', 'Kp. Dukuh RT 03 RW 02', 'KAB. BOGOR', 'ACTIVE', '2025-11-14 20:34', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000042' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000041') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000041', '3201000000000000', 'rivaldi saputra', '2000-01-01', 'Ibu Kandung', '081389580847', 'rivaldisautra@gmail.com', 'JL. PETOJO BINATU  2 NO.35E RT.007 ', 'jakarta pusat', 'ACTIVE', '2025-11-14 20:34', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000041' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000040') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000040', '3201000000000000', 'bagas permana putra', '2000-01-01', 'Ibu Kandung', '083188622590', 'basa171003@gmail.com', 'Kp. Tlajung Desa. Wanaherang Kec. G', 'Kab. Bogor', 'ACTIVE', '2025-11-14 20:33', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000040' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000039') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000039', '3201000000000000', 'Nia Laili Ramadhani', '2000-01-01', 'Ibu Kandung', '085859545465', '2310101066.nia@student.tazkia.ac.id', 'Ds. Lebanisuko', 'Gresik', 'ACTIVE', '2025-11-14 20:33', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000039' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000038') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000038', '3201000000000000', 'Istiqamah Istiqamah', '2000-01-01', 'Ibu Kandung', '08152131036', '2310101104.istiqamah@student.tazkia.ac.id', 'KARUH NO. 17, RT. 003, RW. 000, KEL', 'Balangan', 'ACTIVE', '2025-11-14 20:33', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000038' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000037') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000037', '3201000000000000', 'Try Suci  Fitriyanti', '2000-01-01', 'Ibu Kandung', '081381508996', '2310101175.sucifitriyanti@student.tazkia.ac.id', 'Kp. Nyencle RT.02/RW.12 No.119 Kel.', 'Depok', 'ACTIVE', '2025-11-14 20:33', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000037' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000036') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000036', '3201000000000000', 'A.M. SYARIF HIDAYATULLAH HAKIM', '2000-01-01', 'Ibu Kandung', '088214264796', '2310101031.syarif@student.tazkia.ac.id', 'jl. Bogor no.25 C RT/RW. :002/008. ', 'kota bogor', 'ACTIVE', '2025-11-14 20:32', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000036' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000035') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000035', '3201000000000000', 'Aisya Puspita Dewi', '2000-01-01', 'Ibu Kandung', '082122638008', '2310101063.aisya@student.tazkia.ac.id', 'Griya Bukit Jaya Blok L1 No.26 RT 0', 'Bogor', 'ACTIVE', '2025-11-14 20:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000035' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000034') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000034', '3201000000000000', 'Muhammad Qowiy', '2000-01-01', 'Ibu Kandung', '085773992098', 'Muhammadqowi.avila@gmail.com', 'Jl.Swadaya II, RT 006/05, No 21A, T', 'Jakarta Selatan', 'ACTIVE', '2025-11-14 20:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000034' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000033') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000033', '3201000000000000', 'Dary Zhafran', '2000-01-01', 'Ibu Kandung', '087825211019', '2310101026.dary@student.tazkia.ac.id', 'Ciomas Hills Cluster Pangrango A4 N', 'Bogor', 'ACTIVE', '2025-11-14 20:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000033' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000032') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000032', '3201000000000000', 'Fanisa  Alzaira', '2000-01-01', 'Ibu Kandung', '085693149935', '2310101019.fanisa@student.tazkia.ac.id', 'Kp.Cimanggurang Rt01/Rw02', 'Bogor', 'ACTIVE', '2025-11-14 20:31', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000032' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000031') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000031', '3201000000000000', 'Naniek Nuraini', '2000-01-01', 'Ibu Kandung', '081546217107', '2310101074.naniek@student.ac.id', 'perumahan bogor raya residence jala', 'Bogor', 'ACTIVE', '2025-11-14 20:30', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000031' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000030') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000030', '3201000000000000', 'Tuty Awaliah Zaenudin Putri', '2000-01-01', 'Ibu Kandung', '081282131912', '2310101034.tuty@student.tazkia.ac.id', 'Kp.Curug Rt 02 Rw 09, Desa Bojong k', 'Bogor', 'ACTIVE', '2025-11-14 20:30', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000030' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000029') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000029', '3201000000000000', 'irmalah irmalah', '2000-01-01', 'Ibu Kandung', '087819982004', 'irmalah41@gmail.com', 'Kp, bunder', 'tangerang', 'ACTIVE', '2025-11-14 20:30', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000029' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000028') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000028', '3201000000000000', 'Rita Amelia', '2000-01-01', 'Ibu Kandung', '089652533697', '2310101036.rita@student.tazkia.ac.id', 'Jl. Raya Sirkuit Sentul', 'Bogor', 'ACTIVE', '2025-11-14 20:30', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000028' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000027') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000027', '3201000000000000', 'Muhammad Yusuf Satria', '2000-01-01', 'Ibu Kandung', '082169206209', 'tio.blogg@gmail.com', 'Jl pahlawan no.1', 'Kabupaten Bogor', 'ACTIVE', '2025-11-14 20:29', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000027' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000026') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000026', '3201000000000000', 'Adinda Hermoza', '2000-01-01', 'Ibu Kandung', '087770071446', 'adindamoza04@gmail.com', 'Taman Sari Persada Cluster Lotus B5', 'Bogor', 'ACTIVE', '2025-11-14 20:29', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000026' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000025') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000025', '3201000000000000', 'Muhammad Rafi Khoshib Syarif', '2000-01-01', 'Ibu Kandung', '081380899670', 'rafikhoshib439@gmail.com', 'Jl Pabuaran RT02/RW03 Kel. Cibadak ', 'Kota Bogor', 'ACTIVE', '2025-11-14 20:29', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000025' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000024') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000024', '3201000000000000', 'L. Rizki Maulana', '2000-01-01', 'Ibu Kandung', '083129385237', 'laluriskimaulana56@gmail.com', 'Depok cimanggis', 'Depok', 'ACTIVE', '2025-11-14 20:29', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000024' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000023') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000023', '3201000000000000', 'Muhammad Rakha', '2000-01-01', 'Ibu Kandung', '089602636015', '2310101051.rakha@student.tazkia.ac.id', 'Perumahan IPB Alam Sinar Sari
Jl.A', 'Kota Bogor', 'ACTIVE', '2025-11-14 20:29', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000023' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000022') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000022', '3201000000000000', 'Nurul  Khamilah', '2000-01-01', 'Ibu Kandung', '085716849737', '2310101077.nurul@student.tazkia.ac.id', 'Cimanggis, Bojong Gede, Bogor, Jawa', 'Bogor', 'ACTIVE', '2025-11-14 20:29', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000022' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000021') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000021', '3201000000000000', 'Sinta Rianti', '2000-01-01', 'Ibu Kandung', '0895324173935', '2310101027.sinta@student.tazkia.ac.id', 'KP.KADUMANGGU,rt 01/rw 01', 'kabupaten bogor', 'ACTIVE', '2025-11-14 20:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000021' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000020') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000020', '3201000000000000', 'Fahmi Muhammad', '2000-01-01', 'Ibu Kandung', '085186074050', '23101010@student.tazkia.ac.id', 'Sentul, Bogor', 'Bogor', 'ACTIVE', '2025-11-14 20:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000020' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000019') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000019', '3201000000000000', 'Eman Priyono', '2000-01-01', 'Ibu Kandung', '081573697559', 'eman.priyono@gmail.com', 'Villa Selecta Blok E No.3 RT 001 RW', 'Kota Tangerang Selatan', 'ACTIVE', '2025-11-14 20:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000019' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000018') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000018', '3201000000000000', 'Maulida  Rahmah', '2000-01-01', 'Ibu Kandung', '081388499935', '2310101052.maulida@student.tazkia.ac.id', 'kp pabuaran rt 02/08 cicadas gn put', 'BOGOR', 'ACTIVE', '2025-11-14 20:28', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000018' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000017') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000017', '3201000000000000', 'Fadil Muhammad Ar - Rasyid', '2000-01-01', 'Ibu Kandung', '081295676359', '1901.fadil.123@student.tazkia.ac.id', 'Bukit Waringin Jln. Dahlia II, Blok', 'Bogor', 'ACTIVE', '2025-11-14 20:27', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000017' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000016') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000016', '3201000000000000', 'Aulia Pratiwi', '2000-01-01', 'Ibu Kandung', '08979322460', '2310101078.aulia@student.tazkia.ac.id', 'Kos andara, Desa Citaringgul, Babak', 'Bogor', 'ACTIVE', '2025-11-14 20:26', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000016' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000015') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000015', '3201000000000000', 'Siti Najihah  Khoirunnisa', '2000-01-01', 'Ibu Kandung', '085823907870', '2310101024.najihah@student.tazkia.ac.id', 'KP Lio Baru, rt 02/04, Desa Sanja, ', 'Bogor', 'ACTIVE', '2025-11-14 20:25', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000015' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000014') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000014', '3201000000000000', 'Saniatun Nurhasanah', '2000-01-01', 'Ibu Kandung', '08520000023', 'saniatun@tazkia.ac.id', 'Blok Karang Anyar', 'pwt', 'ACTIVE', '2025-11-14 11:18', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000014' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000013') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000013', '3201000000000000', 'Melvina Syahnum', '2000-01-01', 'Ibu Kandung', '08111288115', 'melvina.bsm@gmail.com', 'The Grand Sentul Cluster Prime Rose', 'Bogor', 'ACTIVE', '2025-11-13 16:23', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000013' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000012') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000012', '3201000000000000', 'Dewi  Mayasari', '2000-01-01', 'Ibu Kandung', '085375221095', '23101010.dewi@student.tazkia.ac.id', 'jalan pelatnas PBSI gg. damai no.32', 'cipayung', 'ACTIVE', '2025-11-08 07:54', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000012' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = 'C1000011') THEN
        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)
        VALUES ('C1000011', '3201000000000000', 'Test Customer', '2000-01-01', 'Ibu Kandung', '081234567890', 'test@yopmail.com', 'Rain City, Bogor, Indonesia', 'Bogor', 'ACTIVE', '2025-11-08 06:47', v_periode_2025_id);
    ELSE
        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = 'C1000011' AND periode_id IS NULL;
    END IF;

    -- 2. SEED 366 LEGACY ACCOUNTS
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000402') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000402', 'Fadil Muhammad Ar - Rasyid - Business', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2026-01-02', '2026-01-02 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000402' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000401') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000401', 'Fadil Muhammad Ar - Rasyid - Pe', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2026-01-02', '2026-01-02 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000401' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000400') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000400', 'Fadil Muhammad Ar - Rasyid - Personal Saving', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2026-01-02', '2026-01-02 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000400' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000399') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000399', 'Tamamun Nadzir - Deposito', 'C1000399', (SELECT id FROM nasabah WHERE cif = 'C1000399' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2026-01-01', '2026-01-01 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000399' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000397') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000397', 'Tamamun Nadzir - Bisnis', 'C1000399', (SELECT id FROM nasabah WHERE cif = 'C1000399' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2026-01-01', '2026-01-01 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000397' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000396') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000396', 'Tamamun Nadzir - personal savings', 'C1000399', (SELECT id FROM nasabah WHERE cif = 'C1000399' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 900000.0, 'ACTIVE', true, '2026-01-01', '2026-01-01 08:00:00', 900000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000396' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000395') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000395', 'dean', 'C1000164', (SELECT id FROM nasabah WHERE cif = 'C1000164' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 200000.0, 'ACTIVE', true, '2025-12-30', '2025-12-30 08:00:00', 200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000395' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000394') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000394', 'Khalisa', 'C1000118', (SELECT id FROM nasabah WHERE cif = 'C1000118' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-30', '2025-12-30 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000394' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000393') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000393', 'AFIF ZAIN AL GHOZI', 'C1000066', (SELECT id FROM nasabah WHERE cif = 'C1000066' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-30', '2025-12-30 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000393' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000392') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000392', 'sahla', 'C1000171', (SELECT id FROM nasabah WHERE cif = 'C1000171' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-30', '2025-12-30 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000392' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000391') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000391', 'wahyu', 'C1000075', (SELECT id FROM nasabah WHERE cif = 'C1000075' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-30', '2025-12-30 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000391' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000390') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000390', 'L. Ihwan marzuki - deposito', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000390' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000387') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000387', 'L. Ihwan maulana - personal saving', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000387' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000386') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000386', 'L. Ihwan marzuki - personal saving', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1999900.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 1999900.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000386' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000385') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000385', 'L. Ihwan marzuki - personal saving', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000385' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000384') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000384', 'Hamizan', 'C1000311', (SELECT id FROM nasabah WHERE cif = 'C1000311' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 200000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000384' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000383') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000383', 'L. Ihwan marzuki - bisnis', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 4000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000383' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000382') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000382', 'L Ihwan Marzuki - pers', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000382' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000381') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000381', 'L Ihwan Marzuki - pers', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000381' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000380') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000380', 'L Ihwan Marzuki - pers', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000380' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000379') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000379', 'jawaaa', 'C1000037', (SELECT id FROM nasabah WHERE cif = 'C1000037' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000379' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000378') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000378', 'BISNIS SAMPINGAN', 'C1000372', (SELECT id FROM nasabah WHERE cif = 'C1000372' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 3550000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 3550000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000378' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000377') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000377', 'Narapati brilian anorawi', 'C1000129', (SELECT id FROM nasabah WHERE cif = 'C1000129' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 200000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000377' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000375') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000375', 'siti marwah - deposito', 'C1000342', (SELECT id FROM nasabah WHERE cif = 'C1000342' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 5000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000375' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000374') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000374', 'syamil', 'C1000126', (SELECT id FROM nasabah WHERE cif = 'C1000126' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000374' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000373') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000373', 'PERSONAL SAVING', 'C1000372', (SELECT id FROM nasabah WHERE cif = 'C1000372' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 700000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 700000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000373' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000372') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000372', 'PERSONAL SAVING', 'C1000372', (SELECT id FROM nasabah WHERE cif = 'C1000372' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1300000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 1300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000372' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000371') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000371', 'Toni Rastafara-Personal Savings', 'C1000371', (SELECT id FROM nasabah WHERE cif = 'C1000371' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 9000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 9000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000371' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000370') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000370', 'Toni Rastafara-Personal Savings', 'C1000371', (SELECT id FROM nasabah WHERE cif = 'C1000371' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 9000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 9000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000370' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000369') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000369', 'Toni Rastafara-Personal Savings', 'C1000371', (SELECT id FROM nasabah WHERE cif = 'C1000371' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 9000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 9000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000369' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000368') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000368', 'DEPOSITO MUDHARABAH', 'C1000365', (SELECT id FROM nasabah WHERE cif = 'C1000365' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000368' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000367') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000367', 'TABUNGAN MUDHARABAH', 'C1000365', (SELECT id FROM nasabah WHERE cif = 'C1000365' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000367' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000366') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000366', 'PERSONAL SAVING', 'C1000365', (SELECT id FROM nasabah WHERE cif = 'C1000365' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 15500000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 15500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000366' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000365') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000365', 'Mandu Chaironi - Personal Savings', 'C1000363', (SELECT id FROM nasabah WHERE cif = 'C1000363' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000365' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000364') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000364', 'Mandu Chaironi - Personal Savings', 'C1000363', (SELECT id FROM nasabah WHERE cif = 'C1000363' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2500000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 2500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000364' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000362') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000362', 'personal saving', 'C1000360', (SELECT id FROM nasabah WHERE cif = 'C1000360' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000362' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000361') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000361', 'Siti Marwah - Bisnis', 'C1000342', (SELECT id FROM nasabah WHERE cif = 'C1000342' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000361' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000360') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000360', 'Siti Marwah - Personal Saving', 'C1000342', (SELECT id FROM nasabah WHERE cif = 'C1000342' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000360' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000359') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000359', 'personal saving', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000359' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000357') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000357', 'personal', 'C1000335', (SELECT id FROM nasabah WHERE cif = 'C1000335' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000357' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000356') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000356', 'Syauqia Jannata - Deposito Mudarabah', 'C1000357', (SELECT id FROM nasabah WHERE cif = 'C1000357' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000356' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000355') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000355', 'Syauqia Jannata - Business', 'C1000357', (SELECT id FROM nasabah WHERE cif = 'C1000357' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 49000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 49000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000355' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000354') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000354', 'Syauqia Jannat - Personal Savings', 'C1000357', (SELECT id FROM nasabah WHERE cif = 'C1000357' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 900000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 900000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000354' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000352') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000352', 'Pinarti Rahayu-Deposito', 'C1000343', (SELECT id FROM nasabah WHERE cif = 'C1000343' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 5000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000352' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000351') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000351', 'Siti Marwah - Personal Saving', 'C1000342', (SELECT id FROM nasabah WHERE cif = 'C1000342' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000351' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000350') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000350', 'Pinarti Rahayu-Business', 'C1000343', (SELECT id FROM nasabah WHERE cif = 'C1000343' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000350' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000349') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000349', 'Hendra Saputra - Deposito', 'C1000339', (SELECT id FROM nasabah WHERE cif = 'C1000339' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000349' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000348') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000348', 'Pinarti Rahayu-Personal Saving', 'C1000343', (SELECT id FROM nasabah WHERE cif = 'C1000343' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000348' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000347') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000347', 'Hunaydah', 'C1000085', (SELECT id FROM nasabah WHERE cif = 'C1000085' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000347' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000345') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000345', 'Siti Marwah - Personal Saving', 'C1000342', (SELECT id FROM nasabah WHERE cif = 'C1000342' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000345' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000344') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000344', 'Hendra Saputra - Business', 'C1000339', (SELECT id FROM nasabah WHERE cif = 'C1000339' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000344' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000343') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000343', 'Hendra Saputra - Personal Savings', 'C1000339', (SELECT id FROM nasabah WHERE cif = 'C1000339' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 4000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000343' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000341') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000341', 'Azizi Shafa - Deposito', 'C1000330', (SELECT id FROM nasabah WHERE cif = 'C1000330' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 17000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 17000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000341' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000340') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000340', 'Azizi Shafa - Bussiness', 'C1000330', (SELECT id FROM nasabah WHERE cif = 'C1000330' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 120000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 120000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000340' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000338') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000338', 'Azizi Shafa -Personal saving', 'C1000330', (SELECT id FROM nasabah WHERE cif = 'C1000330' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 10300000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 10300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000338' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000335') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000335', 'Radawiah - Deposito', 'C1000322', (SELECT id FROM nasabah WHERE cif = 'C1000322' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 300000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000335' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000334') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000334', 'Radawiah - Bisnis', 'C1000322', (SELECT id FROM nasabah WHERE cif = 'C1000322' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000334' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000333') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000333', 'Radawiah - Personal Saving', 'C1000322', (SELECT id FROM nasabah WHERE cif = 'C1000322' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000333' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000332') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000332', 'Robertus-Deposio', 'C1000312', (SELECT id FROM nasabah WHERE cif = 'C1000312' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 100000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000332' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000331') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000331', 'Robertus', 'C1000312', (SELECT id FROM nasabah WHERE cif = 'C1000312' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000331' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000330') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000330', 'Robertus', 'C1000312', (SELECT id FROM nasabah WHERE cif = 'C1000312' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 10000.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 10000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000330' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000328') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000328', 'syifa - deposito', 'C1000307', (SELECT id FROM nasabah WHERE cif = 'C1000307' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4000000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000328' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000327') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000327', 'syifa - bisnis', 'C1000307', (SELECT id FROM nasabah WHERE cif = 'C1000307' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 1500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000327' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000326') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000326', 'Syifa - personal savings', 'C1000307', (SELECT id FROM nasabah WHERE cif = 'C1000307' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 1500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000326' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000325') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000325', 'Cut Mutia Meganova-personal Savings', 'C1000304', (SELECT id FROM nasabah WHERE cif = 'C1000304' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000325' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000324') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000324', 'Winny Iftari', 'C1000300', (SELECT id FROM nasabah WHERE cif = 'C1000300' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 19000000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 19000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000324' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000323') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000323', 'Winny Iftari', 'C1000300', (SELECT id FROM nasabah WHERE cif = 'C1000300' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 9500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 9500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000323' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000322') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000322', 'Winny Iftari', 'C1000300', (SELECT id FROM nasabah WHERE cif = 'C1000300' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 9500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 9500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000322' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000321') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000321', 'Teuku Ahmad Thaffan-Personal Savings', 'C1000145', (SELECT id FROM nasabah WHERE cif = 'C1000145' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000321' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000320') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000320', 'Deposito Mudharabah', 'C1000297', (SELECT id FROM nasabah WHERE cif = 'C1000297' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000320' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000319') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000319', 'Tabungan Mudharabah', 'C1000297', (SELECT id FROM nasabah WHERE cif = 'C1000297' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000319' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000318') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000318', 'Personal Saving', 'C1000297', (SELECT id FROM nasabah WHERE cif = 'C1000297' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000318' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000317') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000317', 'Nuni Indasari - Deposito', 'C1000285', (SELECT id FROM nasabah WHERE cif = 'C1000285' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 2500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000317' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000316') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000316', 'Nuni Indasari - Bisnis', 'C1000285', (SELECT id FROM nasabah WHERE cif = 'C1000285' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000316' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000315') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000315', 'Nuni Indasari - Personal Savings', 'C1000285', (SELECT id FROM nasabah WHERE cif = 'C1000285' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000315' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000314') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000314', 'Nuni indasari - personal savings', 'C1000285', (SELECT id FROM nasabah WHERE cif = 'C1000285' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000314' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000313') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000313', 'Personal Saving', 'C1000297', (SELECT id FROM nasabah WHERE cif = 'C1000297' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000313' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000312') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000312', 'Personal Saving', 'C1000297', (SELECT id FROM nasabah WHERE cif = 'C1000297' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2000000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000312' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000311') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000311', 'Personal Saving', 'C1000297', (SELECT id FROM nasabah WHERE cif = 'C1000297' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000311' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000310') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000310', 'Afiifah Candy - Deposito', 'C1000280', (SELECT id FROM nasabah WHERE cif = 'C1000280' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 1500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 1500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000310' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000309') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000309', 'Afiifah Candy - Personal Saving', 'C1000280', (SELECT id FROM nasabah WHERE cif = 'C1000280' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2000000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000309' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000308') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000308', 'Andi Nadya - Bisnis', 'C1000278', (SELECT id FROM nasabah WHERE cif = 'C1000278' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 500000.0, 'ACTIVE', true, '2025-12-26', '2025-12-26 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000308' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000307') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000307', 'Nadya-Bisnis', 'C1000278', (SELECT id FROM nasabah WHERE cif = 'C1000278' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000307' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000306') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000306', 'Nadya -personal saving', 'C1000278', (SELECT id FROM nasabah WHERE cif = 'C1000278' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000306' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000305') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000305', 'Shania Indira Natio - Deposito', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 12000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 12000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000305' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000303') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000303', 'Muryani - Deposito', 'C1000259', (SELECT id FROM nasabah WHERE cif = 'C1000259' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 30000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 30000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000303' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000302') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000302', 'Muryani - Business', 'C1000259', (SELECT id FROM nasabah WHERE cif = 'C1000259' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000302' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000298') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000298', 'Shania Indira Natio - Bussiness', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 87200000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 87200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000298' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000297') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000297', 'nyai inun_ Deposito', 'C1000277', (SELECT id FROM nasabah WHERE cif = 'C1000277' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 80000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 80000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000297' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000296') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000296', 'Inun_ Bisnis', 'C1000277', (SELECT id FROM nasabah WHERE cif = 'C1000277' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 60000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 60000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000296' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000295') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000295', 'Nyai inun_ Personal savings', 'C1000277', (SELECT id FROM nasabah WHERE cif = 'C1000277' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 60000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 60000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000295' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000294') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000294', 'hamidu-bisis', 'C1000274', (SELECT id FROM nasabah WHERE cif = 'C1000274' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 50000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000294' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000293') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000293', 'hamidu-personal', 'C1000274', (SELECT id FROM nasabah WHERE cif = 'C1000274' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000293' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000292') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000292', 'Melda Saripah - Deposito', 'C1000210', (SELECT id FROM nasabah WHERE cif = 'C1000210' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 6000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 6000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000292' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000291') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000291', 'Melda Saripah - Bisnis', 'C1000210', (SELECT id FROM nasabah WHERE cif = 'C1000210' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 4000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000291' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000290') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000290', 'Melda Saripah - Personal Savings', 'C1000210', (SELECT id FROM nasabah WHERE cif = 'C1000210' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 3000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 3000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000290' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000289') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000289', 'Devi Destiana - Deposito', 'C1000268', (SELECT id FROM nasabah WHERE cif = 'C1000268' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 12000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 12000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000289' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000288') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000288', 'Devi Destiana - Bisnis', 'C1000268', (SELECT id FROM nasabah WHERE cif = 'C1000268' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000288' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000287') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000287', 'Devi Destiana - Personal Saving', 'C1000268', (SELECT id FROM nasabah WHERE cif = 'C1000268' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 400000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 400000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000287' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000286') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000286', 'windi aulia - deposito', 'C1000265', (SELECT id FROM nasabah WHERE cif = 'C1000265' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 9999999.99, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 9999999.99, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000286' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000285') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000285', 'windi aulia - bisnis', 'C1000265', (SELECT id FROM nasabah WHERE cif = 'C1000265' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000285' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000284') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000284', 'windi aulia - personal saving', 'C1000265', (SELECT id FROM nasabah WHERE cif = 'C1000265' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 4000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000284' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000280') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000280', 'Fachry Firdaus - personal saving', 'C1000261', (SELECT id FROM nasabah WHERE cif = 'C1000261' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000280' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000279') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000279', 'Ani Maryani- Deposito', 'C1000260', (SELECT id FROM nasabah WHERE cif = 'C1000260' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000279' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000278') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000278', 'Ani Maryani- Bisnis', 'C1000260', (SELECT id FROM nasabah WHERE cif = 'C1000260' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000278' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000277') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000277', 'Ani Maryani- Personal Saving', 'C1000260', (SELECT id FROM nasabah WHERE cif = 'C1000260' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 4000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000277' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000273') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000273', 'Syifa Fauziah Hasanah- Bisnis', 'C1000215', (SELECT id FROM nasabah WHERE cif = 'C1000215' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000273' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000272') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000272', 'Muryani - Personal Savings', 'C1000259', (SELECT id FROM nasabah WHERE cif = 'C1000259' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 20000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 20000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000272' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000270') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000270', 'Shania Indira Natio - Personal savings', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2950000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 2950000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000270' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000269') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000269', 'Anni Syarah - Personal Account', 'C1000244', (SELECT id FROM nasabah WHERE cif = 'C1000244' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 7000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 7000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000269' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000267') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000267', 'Syifa Fauziah Hasanah- Personal Saving', 'C1000215', (SELECT id FROM nasabah WHERE cif = 'C1000215' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 5000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000267' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000266') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000266', 'Anni Syarah - Personal Account', 'C1000244', (SELECT id FROM nasabah WHERE cif = 'C1000244' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000266' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000264') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000264', 'Anni Syarah - Personal Account', 'C1000244', (SELECT id FROM nasabah WHERE cif = 'C1000244' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000264' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000263') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000263', 'Anni Syarah - Personal Account', 'C1000244', (SELECT id FROM nasabah WHERE cif = 'C1000244' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 4000000.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000263' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000261') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000261', 'personal saving', 'C1000215', (SELECT id FROM nasabah WHERE cif = 'C1000215' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000261' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000259') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000259', 'Harsen Surya permana - DEPOSITO', 'C1000226', (SELECT id FROM nasabah WHERE cif = 'C1000226' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 300000000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 300000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000259' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000255') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000255', 'balqis aulia - deposito', 'C1000222', (SELECT id FROM nasabah WHERE cif = 'C1000222' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2000007.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 2000007.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000255' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000254') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000254', 'Riva Kanziya Hasan - Deposito', 'C1000231', (SELECT id FROM nasabah WHERE cif = 'C1000231' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 100000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000254' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000253') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000253', 'Riva Kanziya Hasan - Bisnis', 'C1000231', (SELECT id FROM nasabah WHERE cif = 'C1000231' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000253' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000252') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000252', 'Riva Kanziya Hasan - Personal Savings', 'C1000231', (SELECT id FROM nasabah WHERE cif = 'C1000231' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000252' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000250') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000250', 'Harsen Surya permana - Personal Saving', 'C1000226', (SELECT id FROM nasabah WHERE cif = 'C1000226' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50050000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 50050000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000250' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000249') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000249', 'balqis aulia - bisnis', 'C1000222', (SELECT id FROM nasabah WHERE cif = 'C1000222' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000249' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000248') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000248', 'balqis aulia - personal sacing', 'C1000222', (SELECT id FROM nasabah WHERE cif = 'C1000222' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000248' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000247') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000247', 'Nurul Imaroh-Deposito', 'C1000220', (SELECT id FROM nasabah WHERE cif = 'C1000220' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 50000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000247' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000246') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000246', 'Nurul Imaroh-Business', 'C1000220', (SELECT id FROM nasabah WHERE cif = 'C1000220' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 50000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000246' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000245') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000245', 'Nurul Imaroh-Personal', 'C1000220', (SELECT id FROM nasabah WHERE cif = 'C1000220' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000245' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000244') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000244', 'Zulkifli - Deposito', 'C1000217', (SELECT id FROM nasabah WHERE cif = 'C1000217' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 1300000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 1300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000244' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000243') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000243', 'Zulkifli - Bisnis', 'C1000217', (SELECT id FROM nasabah WHERE cif = 'C1000217' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1800000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 1800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000243' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000242') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000242', 'Zulkifli - Personal Savings', 'C1000217', (SELECT id FROM nasabah WHERE cif = 'C1000217' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2000000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000242' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000241') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000241', 'Rizwar Adnanbyantara - Deposito', 'C1000211', (SELECT id FROM nasabah WHERE cif = 'C1000211' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 5000000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000241' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000240') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000240', 'Rizwar Adnanbyantara - Business', 'C1000211', (SELECT id FROM nasabah WHERE cif = 'C1000211' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000240' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000239') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000239', 'Rizwar Adnanbyantara - Personal Savings', 'C1000211', (SELECT id FROM nasabah WHERE cif = 'C1000211' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-12-24', '2025-12-24 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000239' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000238') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000238', 'Personal-Savings', 'C1000210', (SELECT id FROM nasabah WHERE cif = 'C1000210' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-23', '2025-12-23 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000238' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000237') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000237', 'Siti Fauziya - Personal Savings', 'C1000183', (SELECT id FROM nasabah WHERE cif = 'C1000183' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-12-23', '2025-12-23 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000237' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000236') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000236', 'Jilan Sajida', 'C1000181', (SELECT id FROM nasabah WHERE cif = 'C1000181' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000236' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000235') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000235', 'Arifin', 'C1000175', (SELECT id FROM nasabah WHERE cif = 'C1000175' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 20000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 20000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000235' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000234') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000234', 'Mulya darma', 'C1000156', (SELECT id FROM nasabah WHERE cif = 'C1000156' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000234' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000233') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000233', 'Zahwa Nabila', 'C1000174', (SELECT id FROM nasabah WHERE cif = 'C1000174' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 700000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 700000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000233' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000232') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000232', 'Muhammad Hidayatullah Hadif', 'C1000173', (SELECT id FROM nasabah WHERE cif = 'C1000173' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000232' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000231') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000231', 'Sabitah Agnissa Padmarini', 'C1000133', (SELECT id FROM nasabah WHERE cif = 'C1000133' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 700000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 700000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000231' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000230') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000230', 'Naqib', 'C1000096', (SELECT id FROM nasabah WHERE cif = 'C1000096' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000230' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000229') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000229', 'Nabilah', 'C1000132', (SELECT id FROM nasabah WHERE cif = 'C1000132' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000229' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000228') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000228', 'Rizki', 'C1000135', (SELECT id FROM nasabah WHERE cif = 'C1000135' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 900000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 900000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000228' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000227') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000227', 'Rafdy', 'C1000142', (SELECT id FROM nasabah WHERE cif = 'C1000142' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000227' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000226') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000226', 'Alya ledwina gozali', 'C1000130', (SELECT id FROM nasabah WHERE cif = 'C1000130' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2500000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 2500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000226' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000225') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000225', 'Rafli Kurniawan', 'C1000104', (SELECT id FROM nasabah WHERE cif = 'C1000104' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 200000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000225' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000224') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000224', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000224' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000223') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000223', 'Muhammad Latif Hafidz', 'C1000131', (SELECT id FROM nasabah WHERE cif = 'C1000131' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000223' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000194') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000194', 'Alfat Surya Saputra', 'C1000155', (SELECT id FROM nasabah WHERE cif = 'C1000155' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000194' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000193') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000193', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000193' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000192') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000192', 'siti nisrina az zahra', 'C1000172', (SELECT id FROM nasabah WHERE cif = 'C1000172' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000192' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000191') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000191', 'AFIF ZAIN AL GHOZI', 'C1000066', (SELECT id FROM nasabah WHERE cif = 'C1000066' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000191' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000190') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000190', 'TEGUH MASYKURI', 'C1000057', (SELECT id FROM nasabah WHERE cif = 'C1000057' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1800000.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 1800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000190' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000189') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000189', 'Nadhrah Ilmina Dilsyaadi', 'C1000011', (SELECT id FROM nasabah WHERE cif = 'C1000011' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000189' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000188') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000188', 'rivaldi saputra - deposito', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 100000000.0, 'ACTIVE', true, '2025-12-13', '2025-12-13 08:00:00', 100000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000188' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000187') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000187', 'nanik-deposito', 'C1000031', (SELECT id FROM nasabah WHERE cif = 'C1000031' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 11000148.0, 'ACTIVE', true, '2025-12-12', '2025-12-12 08:00:00', 11000148.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000187' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000186') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000186', 'Nia Laili - Deposito', 'C1000039', (SELECT id FROM nasabah WHERE cif = 'C1000039' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4000000.0, 'ACTIVE', true, '2025-12-06', '2025-12-06 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000186' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000185') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000185', 'Fattah', 'C1000072', (SELECT id FROM nasabah WHERE cif = 'C1000072' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-12-06', '2025-12-06 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000185' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000184') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000184', 'Sinta Rianti - Deposito', 'C1000021', (SELECT id FROM nasabah WHERE cif = 'C1000021' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 7700000.0, 'ACTIVE', true, '2025-12-06', '2025-12-06 08:00:00', 7700000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000184' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000183') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000183', 'Deposito - L. Rizki Maulana', 'C1000024', (SELECT id FROM nasabah WHERE cif = 'C1000024' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 5000011.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 5000011.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000183' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000182') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000182', 'Resti Dania Permana - Deposito', 'C1000042', (SELECT id FROM nasabah WHERE cif = 'C1000042' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 25000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 25000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000182' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000181') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000181', 'Resti Dania Permana - Deposito', 'C1000042', (SELECT id FROM nasabah WHERE cif = 'C1000042' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 50000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 50000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000181' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000180') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000180', 'Istiqamah - Deposito', 'C1000038', (SELECT id FROM nasabah WHERE cif = 'C1000038' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 500000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000180' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000179') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000179', 'Shofa Audiya _ Deposito', 'C1000050', (SELECT id FROM nasabah WHERE cif = 'C1000050' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 510055.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 510055.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000179' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000178') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000178', 'Tuty Awaliah Zaenudin Putri - Deposito', 'C1000030', (SELECT id FROM nasabah WHERE cif = 'C1000030' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 6000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 6000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000178' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000177') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000177', 'Aisya Puspita Dewi - Deposito', 'C1000035', (SELECT id FROM nasabah WHERE cif = 'C1000035' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 5200000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 5200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000177' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000176') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000176', 'bagas permana putra - personal saving', 'C1000040', (SELECT id FROM nasabah WHERE cif = 'C1000040' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4800000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000176' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000175') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000175', 'Adinda Putri Hermoza - Deposito', 'C1000026', (SELECT id FROM nasabah WHERE cif = 'C1000026' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4000008.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4000008.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000175' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000174') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000174', 'Maulida Rahmah - Deposit', 'C1000018', (SELECT id FROM nasabah WHERE cif = 'C1000018' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000174' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000173') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000173', 'A.M. SYARIF HIDAYATULLAH HAKIM- Bisnis', 'C1000036', (SELECT id FROM nasabah WHERE cif = 'C1000036' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 300005.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 300005.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000173' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000172') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000172', 'Try Suci FItriyanti - Deposito', 'C1000037', (SELECT id FROM nasabah WHERE cif = 'C1000037' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4400000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4400000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000172' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000171') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000171', 'jihan - deposito', 'C1000053', (SELECT id FROM nasabah WHERE cif = 'C1000053' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 11500000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 11500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000171' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000170') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000170', 'Muhammad Rafi Khoshib Syarif - Deposito', 'C1000025', (SELECT id FROM nasabah WHERE cif = 'C1000025' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 200000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000170' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000169') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000169', 'Eman Priyono-Deposito', 'C1000019', (SELECT id FROM nasabah WHERE cif = 'C1000019' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 8488150.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 8488150.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000169' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000168') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000168', 'Fadil Muhammad Ar - Rasyid - Deposito', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 120000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 120000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000168' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000167') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000167', 'Rafael Generasi Ndruru- Deposito', 'C1000051', (SELECT id FROM nasabah WHERE cif = 'C1000051' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 500004.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 500004.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000167' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000166') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000166', 'Rita Amelia - Deposito', 'C1000028', (SELECT id FROM nasabah WHERE cif = 'C1000028' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4299999.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4299999.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000166' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000165') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000165', 'Arie Yudha Nugraha - Deposito', 'C1000144', (SELECT id FROM nasabah WHERE cif = 'C1000144' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000200.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 10000200.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000165' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000164') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000164', 'Shanum Ayesa Gasyari - Deposito Mudharabah', 'C1000044', (SELECT id FROM nasabah WHERE cif = 'C1000044' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 5000050.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 5000050.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000164' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000163') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000163', 'Muhammad Qowiy - Personal Saving', 'C1000034', (SELECT id FROM nasabah WHERE cif = 'C1000034' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2010000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 2010000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000163' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000162') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000162', 'Muhammad Yusuf Satria - Deposito', 'C1000027', (SELECT id FROM nasabah WHERE cif = 'C1000027' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000162' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000161') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000161', 'Shofa Audiya _ Deposito', 'C1000050', (SELECT id FROM nasabah WHERE cif = 'C1000050' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 250005.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 250005.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000161' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000160') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000160', 'Deposito Mudharabah', 'C1000012', (SELECT id FROM nasabah WHERE cif = 'C1000012' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000160' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000159') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000159', 'Fanisa Alzaira - Deposito', 'C1000032', (SELECT id FROM nasabah WHERE cif = 'C1000032' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2500000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 2500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000159' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000158') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000158', 'Siti Najihah Khoirunnisa - Deposito', 'C1000015', (SELECT id FROM nasabah WHERE cif = 'C1000015' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2750000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 2750000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000158' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000157') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000157', 'Dary Zhafran - Deposito', 'C1000033', (SELECT id FROM nasabah WHERE cif = 'C1000033' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 14000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 14000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000157' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000156') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000156', 'Fahmi - Deposito', 'C1000020', (SELECT id FROM nasabah WHERE cif = 'C1000020' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4600000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4600000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000156' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000155') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000155', 'Nurul Khamilah - Deposito', 'C1000022', (SELECT id FROM nasabah WHERE cif = 'C1000022' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 6000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 6000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000155' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000154') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000154', 'irmalah - deposito', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 202005050.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 202005050.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000154' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000153') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000153', 'Muhammad Rakha - Deposito', 'C1000023', (SELECT id FROM nasabah WHERE cif = 'C1000023' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 210000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 210000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000153' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000152') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000152', 'Aulia Hardiyanti Pratiwi - Deposito', 'C1000016', (SELECT id FROM nasabah WHERE cif = 'C1000016' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 4300000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 4300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000152' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000151') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000151', 'Teuku Ahmad Thaffan- personal savings', 'C1000145', (SELECT id FROM nasabah WHERE cif = 'C1000145' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000151' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000150') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000150', 'Saniatun Nurhasanah - Deposito', 'C1000014', (SELECT id FROM nasabah WHERE cif = 'C1000014' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-12-05', '2025-12-05 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000150' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000149') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000149', 'Melvina', 'C1000013', (SELECT id FROM nasabah WHERE cif = 'C1000013' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-12-02', '2025-12-02 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000149' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000148') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000148', 'bisnis', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 2500000.0, 'ACTIVE', true, '2025-11-29', '2025-11-29 08:00:00', 2500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000148' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000147') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000147', 'Rizki', 'C1000135', (SELECT id FROM nasabah WHERE cif = 'C1000135' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 5000000.0, 'ACTIVE', true, '2025-11-29', '2025-11-29 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000147' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000146') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000146', 'Ilham', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2000000.0, 'ACTIVE', true, '2025-11-29', '2025-11-29 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000146' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000145') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000145', 'Nabil', 'C1000063', (SELECT id FROM nasabah WHERE cif = 'C1000063' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-11-29', '2025-11-29 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000145' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000144') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000144', 'Arie Yudha Nugraha', 'C1000144', (SELECT id FROM nasabah WHERE cif = 'C1000144' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 14500000.0, 'ACTIVE', true, '2025-11-28', '2025-11-28 08:00:00', 14500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000144' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000143') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000143', 'Teuku Ahmad Thaffan -Personal Saving', 'C1000145', (SELECT id FROM nasabah WHERE cif = 'C1000145' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 11000000.0, 'ACTIVE', true, '2025-11-28', '2025-11-28 08:00:00', 11000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000143' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000142') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000142', 'personal saving', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-11-28', '2025-11-28 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000142' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000140') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000140', 'personal', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1500000.0, 'ACTIVE', true, '2025-11-28', '2025-11-28 08:00:00', 1500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000140' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000139') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000139', 'personal', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-11-28', '2025-11-28 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000139' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000138') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000138', 'Nabilah', 'C1000132', (SELECT id FROM nasabah WHERE cif = 'C1000132' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 550000.0, 'ACTIVE', true, '2025-11-19', '2025-11-19 08:00:00', 550000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000138' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000137') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000137', 'Muhamad Farid Al Fathdry', 'C1000110', (SELECT id FROM nasabah WHERE cif = 'C1000110' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000137' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000136') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000136', 'Anisa Maharani', 'C1000125', (SELECT id FROM nasabah WHERE cif = 'C1000125' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2242650000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 2242650000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000136' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000135') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000135', 'Yasminnasywa Iskandar', 'C1000117', (SELECT id FROM nasabah WHERE cif = 'C1000117' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 650000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 650000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000135' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000134') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000134', 'Syifa Nurfiyani', 'C1000124', (SELECT id FROM nasabah WHERE cif = 'C1000124' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 200000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000134' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000133') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000133', 'Khalisa Alayya', 'C1000118', (SELECT id FROM nasabah WHERE cif = 'C1000118' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000133' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000132') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000132', 'Qonna&#39;atu Mulhimma', 'C1000113', (SELECT id FROM nasabah WHERE cif = 'C1000113' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000132' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000131') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000131', 'Arya Satria Wiraguna', 'C1000114', (SELECT id FROM nasabah WHERE cif = 'C1000114' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 102550000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 102550000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000131' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000130') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000130', 'Ahmad Syamil Muzzakkir - Daily Use', 'C1000126', (SELECT id FROM nasabah WHERE cif = 'C1000126' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2050000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 2050000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000130' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000129') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000129', 'Dzakwan', 'C1000111', (SELECT id FROM nasabah WHERE cif = 'C1000111' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000000801110.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1000000000801110.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000129' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000128') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000128', 'Siti Rosdianti', 'C1000116', (SELECT id FROM nasabah WHERE cif = 'C1000116' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 25200000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 25200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000128' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000127') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000127', 'daily use', 'C1000120', (SELECT id FROM nasabah WHERE cif = 'C1000120' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000127' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000126') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000126', 'Daily Use', 'C1000119', (SELECT id FROM nasabah WHERE cif = 'C1000119' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 200100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 200100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000126' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000125') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000125', 'Pati', 'C1000129', (SELECT id FROM nasabah WHERE cif = 'C1000129' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000125' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000124') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000124', 'Ikmal', 'C1000127', (SELECT id FROM nasabah WHERE cif = 'C1000127' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 6500000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 6500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000124' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000123') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000123', 'Raihan ichsan effendy', 'C1000115', (SELECT id FROM nasabah WHERE cif = 'C1000115' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 506000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 506000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000123' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000122') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000122', 'M Hafizh Nur Ihsan', 'C1000094', (SELECT id FROM nasabah WHERE cif = 'C1000094' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 600000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 600000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000122' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000121') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000121', 'Rafli', 'C1000104', (SELECT id FROM nasabah WHERE cif = 'C1000104' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 100000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000121' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000120') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000120', 'Fillea', 'C1000089', (SELECT id FROM nasabah WHERE cif = 'C1000089' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000120' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000119') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000119', 'Fachry', 'C1000092', (SELECT id FROM nasabah WHERE cif = 'C1000092' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 30500000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 30500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000119' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000118') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000118', 'zakiyah azizah', 'C1000093', (SELECT id FROM nasabah WHERE cif = 'C1000093' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 6050000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 6050000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000118' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000117') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000117', 'sumayyah', 'C1000091', (SELECT id FROM nasabah WHERE cif = 'C1000091' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 10000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000117' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000116') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000116', 'aulia', 'C1000090', (SELECT id FROM nasabah WHERE cif = 'C1000090' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 20000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 20000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000116' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000115') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000115', 'Fillea', 'C1000089', (SELECT id FROM nasabah WHERE cif = 'C1000089' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000115' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000114') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000114', 'Muhammad Naqib', 'C1000096', (SELECT id FROM nasabah WHERE cif = 'C1000096' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 25000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 25000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000114' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000113') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000113', 'Intan Ayu Muzakir', 'C1000095', (SELECT id FROM nasabah WHERE cif = 'C1000095' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 6400000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 6400000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000113' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000112') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000112', 'Hunaydah.hunay', 'C1000085', (SELECT id FROM nasabah WHERE cif = 'C1000085' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000112' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000111') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000111', 'Rahmatul Fajriani', 'C1000086', (SELECT id FROM nasabah WHERE cif = 'C1000086' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 600000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 600000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000111' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000110') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000110', 'Naurahusna Aqila', 'C1000054', (SELECT id FROM nasabah WHERE cif = 'C1000054' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 450000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 450000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000110' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000109') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000109', 'Yusuf Ahmad Rizqi', 'C1000071', (SELECT id FROM nasabah WHERE cif = 'C1000071' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000109' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000108') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000108', 'Fairuz taqiyya', 'C1000074', (SELECT id FROM nasabah WHERE cif = 'C1000074' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 250000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 250000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000108' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000107') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000107', 'Nashiruddin Hanif', 'C1000067', (SELECT id FROM nasabah WHERE cif = 'C1000067' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000107' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000106') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000106', 'Nahida Laily Mazidah', 'C1000059', (SELECT id FROM nasabah WHERE cif = 'C1000059' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000106' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000105') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000105', 'Fauzan Azzubair', 'C1000072', (SELECT id FROM nasabah WHERE cif = 'C1000072' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50500.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 50500.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000105' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000104') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000104', 'Rahma Fitriani', 'C1000070', (SELECT id FROM nasabah WHERE cif = 'C1000070' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 300000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000104' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000103') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000103', 'Fath Hana Rosyada', 'C1000068', (SELECT id FROM nasabah WHERE cif = 'C1000068' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 3300000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 3300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000103' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000102') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000102', 'Asnawati', 'C1000060', (SELECT id FROM nasabah WHERE cif = 'C1000060' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 20200000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 20200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000102' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000101') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000101', 'Laila Muzdalifah', 'C1000065', (SELECT id FROM nasabah WHERE cif = 'C1000065' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 300000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000101' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000100') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000100', 'Asnawati', 'C1000060', (SELECT id FROM nasabah WHERE cif = 'C1000060' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 10000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000100' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000099') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000099', 'Lutfia Nailul', 'C1000055', (SELECT id FROM nasabah WHERE cif = 'C1000055' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000099' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000098') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000098', 'Halwa Zandria Abdilah', 'C1000056', (SELECT id FROM nasabah WHERE cif = 'C1000056' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000098' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000097') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000097', 'Nadhrah Ilmina Dilsyaadi', 'C1000058', (SELECT id FROM nasabah WHERE cif = 'C1000058' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000097' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000096') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000096', 'Ridho', 'C1000069', (SELECT id FROM nasabah WHERE cif = 'C1000069' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1200000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000096' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000095') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000095', 'Nurul Aini', 'C1000061', (SELECT id FROM nasabah WHERE cif = 'C1000061' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000095' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000094') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000094', 'Mazidah Ulfah', 'C1000064', (SELECT id FROM nasabah WHERE cif = 'C1000064' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000094' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000093') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000093', 'wahyu afrianda', 'C1000075', (SELECT id FROM nasabah WHERE cif = 'C1000075' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 500000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000093' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000092') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000092', 'Fatih hibatillah', 'C1000062', (SELECT id FROM nasabah WHERE cif = 'C1000062' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000092' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000091') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000091', 'Hasna Afifah', 'C1000073', (SELECT id FROM nasabah WHERE cif = 'C1000073' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000091' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000090') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000090', 'Teguh Masykuri', 'C1000057', (SELECT id FROM nasabah WHERE cif = 'C1000057' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 50000200000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 50000200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000090' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000089') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000089', 'AFIF ZAIN AL GHOZI', 'C1000066', (SELECT id FROM nasabah WHERE cif = 'C1000066' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000089' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000088') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000088', 'Nabil Ali', 'C1000063', (SELECT id FROM nasabah WHERE cif = 'C1000063' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000088' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000087') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000087', 'Jihan - Bisnis', 'C1000053', (SELECT id FROM nasabah WHERE cif = 'C1000053' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000087' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000086') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000086', 'Jihan Sabrina - Personal Savings', 'C1000053', (SELECT id FROM nasabah WHERE cif = 'C1000053' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2650000.0, 'ACTIVE', true, '2025-11-15', '2025-11-15 08:00:00', 2650000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000086' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000085') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000085', 'Muhammad Qowiy - Bisnis', 'C1000034', (SELECT id FROM nasabah WHERE cif = 'C1000034' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000085' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000084') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000084', 'Rafael Generasi Ndruru-Bisnis', 'C1000051', (SELECT id FROM nasabah WHERE cif = 'C1000051' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 31200000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 31200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000084' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000083') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000083', 'A.M. SYARIF HIDAYATULLAH HAKIM- Bisnis', 'C1000036', (SELECT id FROM nasabah WHERE cif = 'C1000036' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000083' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000082') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000082', 'Aulia Pratiwi - Business', 'C1000016', (SELECT id FROM nasabah WHERE cif = 'C1000016' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1750000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1750000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000082' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000081') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000081', 'bisnis', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1500000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000081' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000080') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000080', 'Eman Priyono-Business', 'C1000019', (SELECT id FROM nasabah WHERE cif = 'C1000019' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000080' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000079') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000079', 'nanik-bisnis', 'C1000031', (SELECT id FROM nasabah WHERE cif = 'C1000031' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000079' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000078') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000078', 'Aisya Puspita Dewi - Business', 'C1000035', (SELECT id FROM nasabah WHERE cif = 'C1000035' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000078' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000077') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000077', 'Muhammad Rakha - Business', 'C1000023', (SELECT id FROM nasabah WHERE cif = 'C1000023' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000077' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000076') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000076', 'bagas permana putra - bisnis', 'C1000040', (SELECT id FROM nasabah WHERE cif = 'C1000040' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000076' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000075') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000075', 'Nia Laili Ramadhani - Bisnis', 'C1000039', (SELECT id FROM nasabah WHERE cif = 'C1000039' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000075' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000074') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000074', 'Irmalah - bisnis', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000074' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000073') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000073', 'Dary Zhafran - Business', 'C1000033', (SELECT id FROM nasabah WHERE cif = 'C1000033' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 2000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 2000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000073' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000072') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000072', 'Istiqamah - Bisnis', 'C1000038', (SELECT id FROM nasabah WHERE cif = 'C1000038' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000072' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000071') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000071', 'Muhammad Rafi Khoshib Syarif - Bisnis', 'C1000025', (SELECT id FROM nasabah WHERE cif = 'C1000025' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000071' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000070') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000070', 'Irmalah-bisnis', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000070' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000069') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000069', 'Try Suci FItriyanti - Bisnis', 'C1000037', (SELECT id FROM nasabah WHERE cif = 'C1000037' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000069' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000068') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000068', 'Maulida Rahmah - Bisnis', 'C1000018', (SELECT id FROM nasabah WHERE cif = 'C1000018' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000068' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000067') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000067', 'Resti Dania Permana - Bisnis', 'C1000042', (SELECT id FROM nasabah WHERE cif = 'C1000042' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 10000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 10000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000067' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000066') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000066', 'Irmalah-bisnis', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000066' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000064') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000064', 'Rafael Generasi Ndruru', 'C1000051', (SELECT id FROM nasabah WHERE cif = 'C1000051' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000064' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000063') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000063', 'Sinta Rianti - Business', 'C1000021', (SELECT id FROM nasabah WHERE cif = 'C1000021' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000063' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000062') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000062', 'Siti Najihah Khoirunnisa - Bisnis', 'C1000015', (SELECT id FROM nasabah WHERE cif = 'C1000015' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000062' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000061') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000061', 'Muhammad Yusuf Satria - Business', 'C1000027', (SELECT id FROM nasabah WHERE cif = 'C1000027' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 5000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000061' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000060') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000060', 'Shofa Audiya - Bisnis', 'C1000050', (SELECT id FROM nasabah WHERE cif = 'C1000050' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 900000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 900000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000060' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000059') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000059', 'Adinda Putri Hermoza - Bisnis', 'C1000026', (SELECT id FROM nasabah WHERE cif = 'C1000026' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000059' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000058') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000058', 'Irmalah-bisnis', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000058' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000057') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000057', 'Irmalah-bisnis', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000057' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000056') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000056', 'Muhammad Rafi Khoshib Syarif - Bisnis', 'C1000025', (SELECT id FROM nasabah WHERE cif = 'C1000025' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000056' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000055') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000055', 'Fanisa Alzaira - Bisnis', 'C1000032', (SELECT id FROM nasabah WHERE cif = 'C1000032' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000055' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000054') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000054', 'Tuty Awaliah Zaenudin Putri - Bisnis', 'C1000030', (SELECT id FROM nasabah WHERE cif = 'C1000030' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000054' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000053') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000053', 'Dewi Mayasari- Bisnis', 'C1000012', (SELECT id FROM nasabah WHERE cif = 'C1000012' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000053' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000052') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000052', 'Nurul Khamilah - Business', 'C1000022', (SELECT id FROM nasabah WHERE cif = 'C1000022' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 5000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 5000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000052' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000051') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000051', 'Fadil Muhammad Ar - Rasyid - Business', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1011111.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1011111.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000051' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000050') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000050', 'L. Rizki Maulana - bisnis', 'C1000024', (SELECT id FROM nasabah WHERE cif = 'C1000024' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000050' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000049') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000049', 'Rita Amelia - Bisnis', 'C1000028', (SELECT id FROM nasabah WHERE cif = 'C1000028' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000049' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000048') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000048', 'Shanum Ayesa Gasyari - Business', 'C1000044', (SELECT id FROM nasabah WHERE cif = 'C1000044' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000048' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000047') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000047', 'Fahmi - Bisnis', 'C1000020', (SELECT id FROM nasabah WHERE cif = 'C1000020' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000047' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000046') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000046', 'Muhammad Qowiy - Personal Saving', 'C1000034', (SELECT id FROM nasabah WHERE cif = 'C1000034' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 960000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 960000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000046' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000045') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000045', 'Shofa Audiya - Personal Saving', 'C1000050', (SELECT id FROM nasabah WHERE cif = 'C1000050' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000045' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000044') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000044', 'Fadil Muhammad Ar - Rasyid - Personal Saving', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 55555.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 55555.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000044' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000043') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000043', 'Muhammad Rafi Khoshib Syarif - Personal Savings', 'C1000025', (SELECT id FROM nasabah WHERE cif = 'C1000025' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000043' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000042') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000042', 'Nurul Khamilah - Personal Saving', 'C1000022', (SELECT id FROM nasabah WHERE cif = 'C1000022' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 4000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 4000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000042' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000041') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000041', 'Aisya Puspita Dewi - Personal Savings', 'C1000035', (SELECT id FROM nasabah WHERE cif = 'C1000035' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 3000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 3000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000041' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000039') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000039', 'nanik-personal saving', 'C1000031', (SELECT id FROM nasabah WHERE cif = 'C1000031' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 800000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000039' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000038') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000038', 'Resti Dania Permana - Personal Saving', 'C1000042', (SELECT id FROM nasabah WHERE cif = 'C1000042' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 240000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 240000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000038' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000036') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000036', 'Eman Priyono-Personal Saving', 'C1000019', (SELECT id FROM nasabah WHERE cif = 'C1000019' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 12000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 12000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000036' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000035') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000035', 'Muhammad Yusuf Satria - Personal Saving', 'C1000027', (SELECT id FROM nasabah WHERE cif = 'C1000027' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1800000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1800000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000035' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000034') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000034', 'Fadil Muhammad Ar - Rasyid - Personal Saving', 'C1000017', (SELECT id FROM nasabah WHERE cif = 'C1000017' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 550000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 550000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000034' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000033') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000033', 'rita amelia - Personal Saving', 'C1000028', (SELECT id FROM nasabah WHERE cif = 'C1000028' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000033' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000032') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000032', 'Istiqamah - Personal Saving', 'C1000038', (SELECT id FROM nasabah WHERE cif = 'C1000038' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000032' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000031') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000031', 'Tuty Awaliah Zaenudin Putri - Personal savings', 'C1000030', (SELECT id FROM nasabah WHERE cif = 'C1000030' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000031' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000030') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000030', 'Dary Zhafran - Personal Saving', 'C1000033', (SELECT id FROM nasabah WHERE cif = 'C1000033' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1500000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000030' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000029') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000029', 'Try Suci FItriyanti - Personal Saving', 'C1000037', (SELECT id FROM nasabah WHERE cif = 'C1000037' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 150000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 150000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000029' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000028') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000028', 'L Rizki Maulana - personal saving', 'C1000024', (SELECT id FROM nasabah WHERE cif = 'C1000024' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 51150000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 51150000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000028' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000026') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000026', 'Adinda Putri Hermoza - Personal Saving', 'C1000026', (SELECT id FROM nasabah WHERE cif = 'C1000026' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 550000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 550000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000026' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000025') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000025', 'A.M. SYARIF HIDAYATULLAH HAKIM- PERSONAL SAVING', 'C1000036', (SELECT id FROM nasabah WHERE cif = 'C1000036' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 60000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 60000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000025' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000024') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000024', 'Muhammad Rakha - Personal Savings', 'C1000023', (SELECT id FROM nasabah WHERE cif = 'C1000023' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000024' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000023') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000023', 'Sinta Rianti - Personal saving', 'C1000021', (SELECT id FROM nasabah WHERE cif = 'C1000021' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 7000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 7000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000023' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000022') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000022', 'Irmalah-personal saving', 'C1000029', (SELECT id FROM nasabah WHERE cif = 'C1000029' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000022' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000021') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000021', 'Fanisa Alzaira - Saving', 'C1000032', (SELECT id FROM nasabah WHERE cif = 'C1000032' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 1200000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 1200000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000021' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000020') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000020', 'Maulida Rahmah - personal saving', 'C1000018', (SELECT id FROM nasabah WHERE cif = 'C1000018' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000020' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000019') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000019', 'Siti Najihah Khoirunnisa - Personal Saving', 'C1000015', (SELECT id FROM nasabah WHERE cif = 'C1000015' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 2050000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 2050000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000019' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000018') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000018', 'Dewi Mayasari- Personal Saving', 'C1000012', (SELECT id FROM nasabah WHERE cif = 'C1000012' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 3550000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 3550000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000018' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000017') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000017', 'Istiqamah - Personal Saving', 'C1000038', (SELECT id FROM nasabah WHERE cif = 'C1000038' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 100000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 100000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000017' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000015') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000015', 'personal', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 500000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000015' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000014') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000014', 'Shanum Ayesa Gasyari - Personal Savings', 'C1000044', (SELECT id FROM nasabah WHERE cif = 'C1000044' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 7000000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 7000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000014' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000013') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000013', 'bagas permana putra - personal saving', 'C1000040', (SELECT id FROM nasabah WHERE cif = 'C1000040' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 3050000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 3050000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000013' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000012') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000012', 'Fahmi - Personal Savings', 'C1000020', (SELECT id FROM nasabah WHERE cif = 'C1000020' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 4600000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 4600000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000012' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000011') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000011', 'Aulia Pratiwi - Personal Savings', 'C1000016', (SELECT id FROM nasabah WHERE cif = 'C1000016' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 13250000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 13250000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000011' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000010') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000010', 'Nia Laili Ramadhani - Personal Savings', 'C1000039', (SELECT id FROM nasabah WHERE cif = 'C1000039' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000010' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000009') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000009', 'Saniatun Nurhasanah - Pesonal Saving', 'C1000014', (SELECT id FROM nasabah WHERE cif = 'C1000014' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 3500000.0, 'ACTIVE', true, '2025-11-14', '2025-11-14 08:00:00', 3500000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000009' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000008') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000008', 'Melvina', 'C1000013', (SELECT id FROM nasabah WHERE cif = 'C1000013' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1400000.0, 'ACTIVE', true, '2025-11-13', '2025-11-13 08:00:00', 1400000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000008' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000007') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000007', 'Tabungan Test', 'C1000011', (SELECT id FROM nasabah WHERE cif = 'C1000011' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 300000.0, 'ACTIVE', true, '2025-11-08', '2025-11-08 08:00:00', 300000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000007' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000398') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000398', 'Tamamun Nadzir - Bisnis', 'C1000399', (SELECT id FROM nasabah WHERE cif = 'C1000399' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'CLOSED', false, '2026-01-01', '2026-01-01 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000398' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000389') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000389', 'L. R', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000389' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000388') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000388', 'L. Ihwan marzuki - p', 'C1000384', (SELECT id FROM nasabah WHERE cif = 'C1000384' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000388' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000358') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000358', 'personal', 'C1000335', (SELECT id FROM nasabah WHERE cif = 'C1000335' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000358' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000329') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000329', 'S.Wahyu-Wadiah', 'C1000310', (SELECT id FROM nasabah WHERE cif = 'C1000310' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-26', '2025-12-26 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000329' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000304') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000304', 'Shania Indira Natio - Deposito', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000304' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000301') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000301', 'Muryani - Business', 'C1000259', (SELECT id FROM nasabah WHERE cif = 'C1000259' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000301' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000300') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000300', 'Shania Indira Natio - Deposito', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000300' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000299') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000299', 'Shania Indira Natio - Deposita', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000299' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000283') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000283', 'Pajur Rohman - Bisnis', 'C1000264', (SELECT id FROM nasabah WHERE cif = 'C1000264' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000283' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000282') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000282', 'fachry firdaus - personal saving', 'C1000261', (SELECT id FROM nasabah WHERE cif = 'C1000261' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000282' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000281') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000281', 'Pajur Rohman - Personal Savings', 'C1000264', (SELECT id FROM nasabah WHERE cif = 'C1000264' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000281' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000275') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000275', 'Shania Indira Natio - Personal Savings', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000275' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000271') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000271', 'Muryani - Personal Savings', 'C1000259', (SELECT id FROM nasabah WHERE cif = 'C1000259' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000271' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000268') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000268', 'Syifa Fauziah Hasanah- Personal Saving', 'C1000215', (SELECT id FROM nasabah WHERE cif = 'C1000215' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000268' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000262') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000262', 'Afridah Hutabarat - Personal Saving', 'C1000241', (SELECT id FROM nasabah WHERE cif = 'C1000241' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000262' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000260') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000260', 'Syifa Fauziah Hasanah- Personal Saving', 'C1000215', (SELECT id FROM nasabah WHERE cif = 'C1000215' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000260' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000258') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000258', 'Harsen Surya permana - Personal Saving', 'C1000226', (SELECT id FROM nasabah WHERE cif = 'C1000226' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000258' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000257') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000257', 'Harsen Surya permana - Personal Saving', 'C1000226', (SELECT id FROM nasabah WHERE cif = 'C1000226' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000257' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000256') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000256', 'Harsen Surya permana - Personal Saving', 'C1000226', (SELECT id FROM nasabah WHERE cif = 'C1000226' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000256' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000251') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000251', 'Harsen Surya permana - Personal Saving', 'C1000226', (SELECT id FROM nasabah WHERE cif = 'C1000226' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-24', '2025-12-24 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000251' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000222') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000222', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000222' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000221') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000221', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000221' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000141') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000141', 'personal saving', 'C1000041', (SELECT id FROM nasabah WHERE cif = 'C1000041' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'CLOSED', false, '2025-11-28', '2025-11-28 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000141' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000065') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000065', 'Aulia Pratiwi - Business', 'C1000016', (SELECT id FROM nasabah WHERE cif = 'C1000016' LIMIT 1), 'Tabungan Mudharabah Premium TABUNGAN_MUDHARABAH', 1000000.0, 'CLOSED', false, '2025-11-14', '2025-11-14 08:00:00', 1000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000065' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000040') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000040', 'Muhammad Qowiy - Personal Saving', 'C1000034', (SELECT id FROM nasabah WHERE cif = 'C1000034' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'CLOSED', false, '2025-11-14', '2025-11-14 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000040' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000037') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000037', 'Muhammad Qowiy - Personal Saving', 'C1000034', (SELECT id FROM nasabah WHERE cif = 'C1000034' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000.0, 'CLOSED', false, '2025-11-14', '2025-11-14 08:00:00', 50000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000037' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000027') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000027', 'Nurul Khamilah - Personal Saving', 'C1000022', (SELECT id FROM nasabah WHERE cif = 'C1000022' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 3000000.0, 'CLOSED', false, '2025-11-14', '2025-11-14 08:00:00', 3000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000027' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000016') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000016', 'Shofa Audiya - Personal Saving', 'C1000050', (SELECT id FROM nasabah WHERE cif = 'C1000050' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 50000000.0, 'CLOSED', false, '2025-11-14', '2025-11-14 08:00:00', 50000000.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000016' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000376') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000376', 'anisa', 'C1000125', (SELECT id FROM nasabah WHERE cif = 'C1000125' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000376' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000363') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000363', 'zahwa nabila', 'C1000174', (SELECT id FROM nasabah WHERE cif = 'C1000174' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000363' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000353') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000353', 'Mazidah Ulfah', 'C1000059', (SELECT id FROM nasabah WHERE cif = 'C1000059' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000353' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000346') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000346', 'Hasna Afifah', 'C1000073', (SELECT id FROM nasabah WHERE cif = 'C1000073' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000346' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000342') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000342', 'Halwa Zandria Abdillah', 'C1000056', (SELECT id FROM nasabah WHERE cif = 'C1000056' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000342' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000339') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000339', 'fauzan azzubair', 'C1000072', (SELECT id FROM nasabah WHERE cif = 'C1000072' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000339' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000337') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000337', 'hanif', 'C1000067', (SELECT id FROM nasabah WHERE cif = 'C1000067' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000337' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000336') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000336', 'fatih hibatillah', 'C1000062', (SELECT id FROM nasabah WHERE cif = 'C1000062' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-27', '2025-12-27 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000336' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000276') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000276', 'Shania Indira Natio - Personal Savings', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000276' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000274') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000274', 'Syifa Fauziah Hasanah- Deposito', 'C1000215', (SELECT id FROM nasabah WHERE cif = 'C1000215' LIMIT 1), 'Deposito Mudharabah DEPOSITO_MUDHARABAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000274' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000265') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000265', 'Shania Indira Natio - Personal Savings', 'C1000247', (SELECT id FROM nasabah WHERE cif = 'C1000247' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-25', '2025-12-25 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000265' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000220') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000220', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000220' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000219') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000219', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000219' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000218') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000218', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000218' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000217') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000217', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000217' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000216') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000216', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000216' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000215') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000215', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000215' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000214') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000214', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000214' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000213') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000213', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000213' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000212') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000212', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000212' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000211') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000211', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000211' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000210') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000210', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000210' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000209') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000209', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000209' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000208') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000208', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000208' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000207') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000207', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000207' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000206') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000206', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000206' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000205') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000205', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000205' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000204') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000204', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000204' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000203') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000203', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000203' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000202') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000202', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000202' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000201') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000201', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000201' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000200') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000200', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000200' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000199') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000199', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000199' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000198') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000198', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000198' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000197') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000197', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000197' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000196') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000196', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000196' AND periode_id IS NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = 'A2000195') THEN
        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)
        VALUES ('A2000195', 'Mohamad ilham jaya kusumah', 'C1000141', (SELECT id FROM nasabah WHERE cif = 'C1000141' LIMIT 1), 'Tabungan Wadiah Basic TABUNGAN_WADIAH', 0.0, 'ACTIVE', true, '2025-12-20', '2025-12-20 08:00:00', 0.0, v_periode_2025_id);
    ELSE
        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = 'A2000195' AND periode_id IS NULL;
    END IF;

    -- 3. SEED 583 LEGACY TRANSACTIONS
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000588') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '32a77599-93b7-4c68-b9e0-18b6f24f745e', '2b01f880-7303-45a9-be51-768b8fd4792d', 'T3000588', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 200000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2026-01-04 20:12:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000007';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000587') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5f05fe1f-3efd-44bf-bea8-4a18dcbeb831', '8dc87c2d-7a4e-4f06-b535-517274a1bef3', 'T3000587', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 100000.0, 200000.0, 'Setoran Tunai Oleh: teller1', '2026-01-04 20:12:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000007';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000586') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '27de36f1-f3ff-4be5-81db-79c2dfc35c82', '11cc49b4-2533-4445-8c49-3bacb6c1929d', 'T3000586', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 911111.0, 1011111.0, 'Oleh: manager1', '2026-01-02 12:22:57', 'manager1', 'manager1', 'Manager1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000051';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000585') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '008c2ee2-e0a6-453b-92db-891bc865865f', '6bccef42-e6c9-44ff-a770-1fa0771c3d7b', 'T3000585', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 450000.0, 550000.0, 'Setoran Tunai Oleh: manager1', '2026-01-02 12:21:11', 'manager1', 'manager1', 'Manager1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000034';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000584') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '462cbc4b-dfb1-4b2b-9c78-177dbe689fa7', '465b3845-1099-4bb2-b971-3dc910607962', 'T3000584', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 350000.0, 450000.0, 'Setoran Tunai Oleh: teller1', '2026-01-02 12:16:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000034';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000583') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fcd705b9-e01c-4d51-8b84-f293aac57bbf', 'dc8eacad-c878-4cca-81ff-6a9da7945fc3', 'T3000583', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2026-01-01 15:07:29', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000399';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000582') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eb2b2d34-1492-4df8-a1fa-872b733e2a07', '48b0d7e8-1e8e-4d4a-b20b-f22e0867d995', 'T3000582', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 800000.0, 900000.0, 'Tarik tunai Oleh: teller1', '2026-01-01 15:03:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000396';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000581') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bc48a929-22dc-4c2f-8a40-0089a6495e04', '9bb2731f-1b82-4abc-9262-ef6d30211a81', 'T3000581', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2026-01-01 15:03:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000396';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000580') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2f82b833-1892-4a50-a2d0-d0f689067a37', '8c6f1677-3671-4ef0-9b41-6099c1fa4cc9', 'T3000580', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 300000.0, 500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-31 10:49:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000149';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000579') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0546b8a3-4530-4a26-bbf9-51a5d8c39530', '7b68e5e6-3833-4090-ae0a-8d7bb28da29a', 'T3000579', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-31 10:45:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000149';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000578') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a30d0bb7-8044-41ce-bd6c-8f65542228bc', '659aa6a3-141d-44fc-98c7-80daeae081ae', 'T3000578', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1200000.0, 1400000.0, 'Setoran Tunai Oleh: teller1', '2025-12-31 10:42:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000008';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000577') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a3e4ebbd-4d72-484b-a4b6-dbb83c8a1331', 'b31db9df-5cad-46ef-98de-e5349f7c4b99', 'T3000577', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1000000.0, 1200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-31 10:41:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000008';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000576') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '62f30184-6da5-4b24-94b8-7fbfd2641854', 'dc2167c4-1e84-4865-abe7-67a662e4d111', 'T3000576', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 18:13:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000104';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000575') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9cbdd8d9-b23c-4863-b099-04aecedc9138', 'ae4284b2-a369-4c68-bec3-0ba6a9cd5606', 'T3000575', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1000000.0, 1200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 18:08:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000096';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000574') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ec37a5d2-2d41-48a9-bf71-8dacf143f642', 'efef3c1d-6ac4-4a0d-8808-dd03c7485b18', 'T3000574', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 800000.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 17:56:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000106';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000573') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '202d536b-5e9b-4f54-a372-736971c239a3', '4000db2b-a6d2-4c77-9dd4-07c0aab800b9', 'T3000573', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 600000.0, 800000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 17:49:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000106';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000572') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '559ac901-3f0f-488e-bc94-c211fc1cb315', '3d021b80-d4d7-466f-b5fd-4c7000915504', 'T3000572', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 17:43:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000101';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000571') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4a2ee826-b97e-43a5-ad40-b9ac7df8ffe3', 'db194f8b-89c5-41ed-9df3-f7b787c5e037', 'T3000571', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 0.0, 200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 17:38:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000395';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000570') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f870d202-3a8e-42e6-8221-8c45283cb84f', 'b1f4768e-5746-4b97-82b5-493b208c9a6d', 'T3000570', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 100000.0, 600000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 17:03:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000106';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000569') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f6c04b43-c33e-4b66-ad1f-3c46a511cdbf', '52431d73-3e94-4cd8-932c-c737930cd6bb', 'T3000569', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 102500000.0, 102550000.0, 'Setoran Tunai Oleh: teller1', '2025-12-30 15:27:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000568') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fc746ef6-131a-436b-8799-5f38aa26c601', '38e7c44e-c0a1-4715-ab3e-bda792669509', 'T3000568', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 300000.0, 500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 16:11:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000127';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000567') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cc5bcfd5-b165-4652-9acc-c2104614b2da', 'cf0d9f54-bfa3-4a2e-a343-c15b8b46625e', 'T3000567', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 2242450000.0, 2242650000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 16:07:24', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000566') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b62ea03e-5ee7-481c-a375-b79fd56f44a2', 'b4ca156b-dea4-4c46-aaa2-2b3ee6c75d02', 'T3000566', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 0.0, 200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 16:01:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000377';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000565') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8cf69d2f-3089-4d07-b6f1-ca8dff05669b', 'adb2e22e-1a11-4788-b34e-59f00de8237c', 'T3000565', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 2242250000.0, 2242450000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:55:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000564') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a89d331c-69e1-4ac8-9c82-8070d4401ecc', 'a1fb8af5-ccd5-4b3a-af00-98d2a7e65ea0', 'T3000564', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Tariktunai Oleh: teller1', '2025-12-27 15:51:29', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000390';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000563') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '989d2351-a5fd-44fc-89e0-1a59317b4d94', 'cf85a45b-2000-4b7a-a253-0a8e75f36328', 'T3000563', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:50:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000390';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000562') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8fa28c74-9e8b-45b5-bc05-c98b64f3df2e', '05ea4b56-9010-499a-97a7-08186cadd68a', 'T3000562', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 504000000.0, 506000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:49:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000123';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000561') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3b7cd013-aff6-4620-9b93-992c3e5df7e4', 'e3211150-f0dd-45cc-b31f-136a13db9984', 'T3000561', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 102300000.0, 102500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:42:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000560') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3bafd74b-6ee1-4417-971f-d20e25ead2fd', '4f7fa9a7-9b0f-4211-8a3d-babc040704f0', 'T3000560', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 3000000.0, 4000000.0, 'Tarik tunai Oleh: teller1', '2025-12-27 15:39:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000383';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000559') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1965d232-3cb9-40c6-ac65-707082d61444', 'c1e193f9-1655-4b19-bc26-4fd0b5933583', 'T3000559', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setor tunai Oleh: teller1', '2025-12-27 15:38:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000383';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000558') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd8b2f09f-b766-4ad7-9343-010207638180', '33e54a1f-9355-4027-b191-8cafa77ba497', 'T3000558', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 25000000.0, 25200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:36:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000128';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000557') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0b5ce830-c1a5-419b-b381-001118add109', '2a5b842c-690e-4cbb-bd1f-b3ab4b79d758', 'T3000557', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100.0, 1999800.0, 1999900.0, 'Tarik tunai Oleh: teller1', '2025-12-27 15:35:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000386';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000556') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '37acf8d5-7727-422c-b069-271efdade03d', '4794425e-ba33-4cb5-ad8d-1d45f9d40727', 'T3000556', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:33:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000386';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000555') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0bc7e0a6-fbfc-4848-b801-71b8f2080649', 'bc89849a-65cd-44e0-a36b-1e4b4500addd', 'T3000555', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 450000.0, 650000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:31:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000135';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000554') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f87fd1fb-7069-4a68-873e-8859ae53f762', '6e6f3b9b-eb5b-4b40-bd68-018f58874b5b', 'T3000554', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 2242050000.0, 2242250000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:27:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000553') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'babf90fb-46fe-4216-a522-dd69ae237ed0', 'dfef721a-42af-40b8-b054-f42b58418062', 'T3000553', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 2240050000.0, 2242050000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:26:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000552') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7afc6ebb-afcf-42b9-bd50-91e36be34316', '054ff3c3-43d4-4268-8d7b-326f26364b9d', 'T3000552', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 0.0, 200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 15:20:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000384';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000551') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b42858e3-d34b-48d7-ad3d-ce7437582839', '6df45236-077a-4a02-9201-cf3518e4222a', 'T3000551', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 4550000.0, 4600000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:48:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000012';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000550') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7b4480df-a4da-49cb-87a7-35ea1ead87c8', '8dfb7d73-c4c2-4f5e-9a7b-f6afe231d75c', 'T3000550', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3550000.0, 0.0, 3550000.0, 'Setoran Tunai DEPOSITO MUDHARABAH Oleh: teller1', '2025-12-27 14:45:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000378';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000549') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fb11bcab-fcf6-4834-aff3-4886632a1d15', 'ac9378a5-be85-4a72-b416-7eb1c79dffc0', 'T3000549', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 102100000.0, 102300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:39:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000548') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1b7882f2-8a8d-44fc-9b8b-400ecb0323fd', 'c72c2c57-f856-49c8-b576-46eed5c4a10f', 'T3000548', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 200000.0, 700000.0, 'TARIK TUNAI MUDHARABAH PREMIUM Oleh: teller1', '2025-12-27 14:39:08', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000373';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000547') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c1d4565f-7741-433c-a030-35196ed86c34', '4505ebd7-c416-4124-87dc-ff3b1263617d', 'T3000547', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1300000.0, 0.0, 1300000.0, 'Setoran Tunai WADIAH BASIC Oleh: teller1', '2025-12-27 14:37:28', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000372';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000546') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '93ef6b50-da46-4fa7-bf05-3051a6844752', '4c9d50aa-710c-4d53-9b6e-f6e6adebad62', 'T3000546', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 101900000.0, 102100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:36:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000545') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fa11d05a-f44c-409a-ae56-108a902d5c24', '55773135-dd16-4148-aa8b-410bcdc511dc', 'T3000545', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 101700000.0, 101900000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:33:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000544') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8d3f0843-d0ae-4159-a0e4-b0c3937bb30a', '4c6b5e32-3f4c-4710-bf4c-e815b9da249f', 'T3000544', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1200000.0, 0.0, 1200000.0, 'Setoran Tunai MUDHARABAH Oleh: teller1', '2025-12-27 14:32:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000373';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000543') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd82c9b87-0de4-41eb-b03b-392393ee12a6', 'eea914cf-3b7c-48d0-887d-f597d4975d8c', 'T3000543', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 3000000.0, 5000000.0, 'tarik tunak Oleh: teller1', '2025-12-27 14:31:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000375';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000542') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '94bc1a47-e210-4066-b809-bb6a6caf0d2c', '675ed2a8-1447-43d2-9c45-0e453336752f', 'T3000542', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 0.0, 7000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:30:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000375';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000541') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6cb61bf1-1622-4e64-9365-7eaa90391d5f', 'cc8714ad-e190-4a47-9a76-2405f98be7b2', 'T3000541', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 8000000.0, 9000000.0, 'penarikan Oleh: teller1', '2025-12-27 14:22:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000371';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000540') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6b7e41b2-416d-462a-9ad0-07fd74ead348', '8e352fee-d5dc-4f64-bb4a-b9e3a7807439', 'T3000540', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:22:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000371';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000539') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ce5aa84e-9e69-45e3-9c4b-b86f60664083', '0388d78e-8360-4d8a-8b56-035890478210', 'T3000539', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 8000000.0, 9000000.0, 'penarikan Oleh: teller1', '2025-12-27 14:21:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000370';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000538') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2e9abb4b-6305-4cf8-8f00-f49ff6113a78', '46562f80-b3dc-4e8f-ade7-aaded1d2af0c', 'T3000538', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:21:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000370';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000537') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '650a0b49-21da-4229-801b-c14e30f95273', 'a4b45b3b-2c64-49c3-bfff-76c6eac93ffc', 'T3000537', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 8000000.0, 9000000.0, 'penarikan tunai Oleh: teller1', '2025-12-27 14:20:50', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000369';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000536') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '305df10f-f34b-426a-a35c-d37368e72fc2', '3d489cb7-b731-44b2-877e-d9b51166fd46', 'T3000536', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:19:45', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000369';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000535') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2275cea8-ec49-4de9-8fe9-3c8862abbb9d', '0201c4fe-f623-4f96-bb85-e9b70fb621d0', 'T3000535', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 15000000.0, 500000.0, 15500000.0, 'Setoran Tunai DEPOSITO Oleh: teller1', '2025-12-27 14:09:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000366';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000534') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3bc51cac-313f-4c2c-97c9-a33a807a7343', '23817af0-eb99-408e-80f7-b60897da5353', 'T3000534', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1500000.0, 0.0, 500000.0, 'TARIK TUNAI Oleh: teller1', '2025-12-27 14:07:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000366';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000533') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '34cf2ad8-8a3b-43f0-96a9-a765d1fb8b1e', 'eabecdb5-57c6-4d20-8c7d-dc278dfecdc7', 'T3000533', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 14:05:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000366';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000532') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2f8de2ac-7da9-40b9-bb9b-86c2722c774a', 'f2a71572-6d9e-4bbb-a7d3-e59255586333', 'T3000532', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:59:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000365';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000531') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ba969498-45c2-46b1-be8d-515ded1d39d3', '374c4f6c-c431-461c-b29a-aa10c691c1a1', 'T3000531', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 0.0, 2500000.0, 'Pearikan tunai Oleh: teller1', '2025-12-27 13:54:28', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000364';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000530') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4842aaf0-a0e4-4d2d-bee5-117e744d5e52', '264f752a-969f-4d39-a1ce-c0f0db382017', 'T3000530', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:53:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000364';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000529') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '020dc30d-335a-49e6-88c4-7533d46ad779', '954b5f55-6f9a-4b3e-913c-7eb878f515c6', 'T3000529', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 4500000.0, 6500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:53:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000124';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000528') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b0b84e5c-1d58-4e95-b5a8-c1cd53c660b2', '79e8f4ef-dbce-40db-a1c5-3f9d6647c2c8', 'T3000528', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 101500000.0, 101700000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:50:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000527') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '643f5951-e3e4-4ae2-acf3-92997cd1104a', 'e864eeb7-76a5-43c5-af39-3aca52fca367', 'T3000527', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 101000000.0, 101500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:43:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000526') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1e3bedd5-34d2-4800-bc3a-ca3a93f317ef', '1592c743-b03e-4e74-9c0a-8cf881cb4a41', 'T3000526', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 2000000.0, 'tarik tunai Oleh: teller1', '2025-12-27 13:28:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000361';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000525') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4e3928d3-a0f1-491f-94b1-20eaa8b5ab08', '5fbb1d5d-606b-4b54-92c5-568fcdf99951', 'T3000525', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:27:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000361';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000524') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '09a7a354-5692-43ed-b77f-fa74dc7c932c', 'b52cd45b-fe3d-4e5c-8543-6c556787bf2f', 'T3000524', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200001.0, 599999.0, 800000.0, 'penarikan Oleh: teller1', '2025-12-27 13:26:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000345';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000523') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4be5f2d4-1c6f-42df-b211-9a8eab112cea', 'a9611e02-5651-4c81-9bfd-b31e8538d6a2', 'T3000523', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000001.0, 0.0, 1000001.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:25:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000345';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000522') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9042718f-5fb4-416c-8167-46b58d7c145e', 'f60d07d6-8768-47f3-be10-bb013f57e4f0', 'T3000522', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 503000000.0, 504000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:08:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000123';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000521') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '137cebe8-7c67-4b7a-bd95-53ef55eb3ebc', 'd70b3315-3ce1-4c55-882d-ee63851a3143', 'T3000521', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100800000.0, 101000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:05:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000520') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '27422744-b55d-4148-b178-bdbde95527fa', 'ea967a27-d191-474e-825f-13424b160616', 'T3000520', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000000.0, 3000000.0, 503000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:03:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000123';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000519') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6791dff9-c3a2-4c26-bb5b-89c7d6e864b3', '629f14eb-6989-48ff-8afa-6eabae7b9785', 'T3000519', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 2000000.0, 4500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 13:02:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000124';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000518') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '149ae474-afab-44da-a03d-20e8353ea396', 'b5e4f3a1-18c4-4de5-a73f-15ce8a73ff2f', 'T3000518', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 600000.0, 800000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:50:17', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000112';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000517') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '12584a75-0d5e-4da8-aedf-f56874f3499a', '858ca53e-a3a9-42e3-bf74-6327a3ad5981', 'T3000517', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 500000.0, 700000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:44:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000233';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000516') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b52fe898-18ca-4028-8123-626158982a31', 'dc96033a-66d3-4910-9de9-b490e6f8d882', 'T3000516', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 500000.0, 2500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:39:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000226';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000515') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'de1e62ed-b145-4927-a77a-7339633e46f4', '33267dda-f136-49c4-857f-497c1a47cd59', 'T3000515', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 20000.0, 0.0, 20000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:31:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000235';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000514') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7438dfc0-1df6-4a0f-b1a9-b24005a47c4b', '67d8213a-95cf-4152-a616-6af67e9a00d8', 'T3000514', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 700000.0, 900000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:24:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000228';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000513') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a9273dcd-67b5-4126-a391-811e6b69c00b', '0f35c828-83f7-4307-be0e-5c73b049cfbc', 'T3000513', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:16:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000227';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000512') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '821a5459-923d-4fcf-947f-554b03439351', 'e6936d8e-95b3-4eb7-a8db-29046230cfac', 'T3000512', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 12:07:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000230';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000511') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6105f7b1-9122-42ce-9bc3-79bd60944cc7', 'a69d5b23-bbb3-4f89-928a-cafa95679cf0', 'T3000511', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 400000.0, 600000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:53:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000122';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000510') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '09057d50-8d17-44ce-82c4-72e80aa0b928', 'c631e120-d62c-4b47-ada9-da928dc6c103', 'T3000510', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:48:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000356';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000509') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'aa559011-9182-4001-8723-9a97f66e9984', '5fbeb64a-c13e-4c73-8adf-e67dac609204', 'T3000509', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 48000000.0, 49000000.0, 'Oleh: teller1', '2025-12-27 11:45:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000355';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000508') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '88b32793-245f-4c08-a3f0-80af0f81209d', 'da91c555-bc67-4db9-9ba5-2ead0d12bd3e', 'T3000508', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000.0, 0.0, 50000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:44:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000355';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000507') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bf21fade-a696-4ad1-b19d-de4d4f1a86c0', 'cda87616-825e-4852-b903-fa7902d1c68a', 'T3000507', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 800000.0, 900000.0, 'Oleh: teller1', '2025-12-27 11:43:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000354';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000506') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0c5a22f4-edf6-475c-9e93-177304961479', 'f1824889-5ddc-4741-83a5-67e1b40f1579', 'T3000506', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:43:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000354';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000505') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fb211206-a810-41ce-bc6f-94ea8f78fe9d', 'cefe021e-a373-4ebe-9eda-3f50465d4586', 'T3000505', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:37:28', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000352';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000504') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7ac5ba63-0ea5-4d51-a09d-ffab9561dbe3', '23b64a7d-8e87-4e7d-8ebc-abb9c6259244', 'T3000504', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 600000.0, 800000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-27 11:31:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000348';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000503') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '87f82a65-172f-40e3-a5cc-ec6d6c1409cc', '1f553440-4713-4912-9faf-92ac7b943d53', 'T3000503', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:29:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000348';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000502') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e55d7aac-fe8f-41bb-a532-a755c256195d', 'b5ccd20a-fd6c-4126-a57c-d1e5883fd9f9', 'T3000502', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:26:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000349';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000501') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '29015000-6f28-47ff-b211-1a6f65a862a3', 'c06b3894-4617-492f-b144-e651545b0ae1', 'T3000501', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 3000000.0, 4000000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-27 11:15:17', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000343';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000500') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8ac2f02c-39a7-4161-977f-019502df8ccd', '1af49015-670b-419a-a8d3-c473867f4796', 'T3000500', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 11:13:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000343';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000499') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '478f8739-ee0f-44cc-b1d4-975850c8be3c', '1153366e-5cfb-4c27-bbf8-a81999ad62ba', 'T3000499', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1600000.0, 1800000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 10:30:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000190';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000498') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '21d65a55-76c6-4283-adf2-ce3fe4053579', '30c2e99f-2bd7-4656-8513-45bf9de30347', 'T3000498', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 10:25:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000109';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000497') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '22d262c1-0717-40d4-b06b-0f50e3f871c5', '0473fd0f-0db7-4bf6-aefd-ec2db9339cc5', 'T3000497', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 250000.0, 450000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 10:20:08', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000110';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000496') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a8d30a4b-800c-45b3-b1d8-55b9384739a5', '94f0d392-8c93-489f-86f9-a151d42aee11', 'T3000496', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 50000.0, 250000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 10:11:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000110';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000495') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '72dc3f6d-c512-4433-85c8-898020a976d7', 'e2142539-a626-4e85-82a7-a8edae5817d0', 'T3000495', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 50000.0, 250000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 10:05:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000108';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000494') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '76305866-1e5f-486e-bae5-c1e1c04d4d4a', '613ba06f-051e-4fb7-9882-9643c95e2df7', 'T3000494', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 1300000.0, 3300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 09:59:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000103';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000493') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '38d05681-1324-45f1-b3fe-e2a91e95ff23', 'a79e79b8-fa5e-4b90-9901-9fae23ab9ff0', 'T3000493', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1100000.0, 1300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 09:55:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000103';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000492') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '99637b90-02ed-4ea1-8d2f-3c0f4d4014d2', '443c767d-0d00-4d37-834d-3bf1968f47a0', 'T3000492', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 20000000.0, 20200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 09:48:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000102';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000491') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4f40f7af-8563-4fd7-8067-6daf00b6d280', '12ca4cf0-b3bb-4fc9-b07f-f378bfc34c29', 'T3000491', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 17000000.0, 0.0, 17000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 09:31:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000341';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000490') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bf2874fb-0639-4ae0-a4a3-f1e36fdc81dc', 'f2b60b74-52b7-4e89-a343-ec472c202bc4', 'T3000490', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 120000000.0, 0.0, 120000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 09:28:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000340';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000489') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'aa59b713-3aa1-44cd-ac1c-c055198cfdc7', '15d4f3cf-732b-419f-b868-a4cf580a9107', 'T3000489', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 700000.0, 9600000.0, 10300000.0, 'Oleh: teller1', '2025-12-27 09:23:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000338';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000488') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '09c70851-e92c-43c4-be76-e7c0062ea12d', '3f89d9df-8a97-47e8-9029-18a3677c7d64', 'T3000488', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 11000000.0, 0.0, 11000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 09:22:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000338';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000487') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '40dc98e9-8d2a-4064-8b81-7cfb78fa2dca', 'fa5ac9c3-559a-4e10-8fbb-54b5a4d1d254', 'T3000487', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 300000.0, 0.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 05:54:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000335';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000486') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd3e14aca-420e-4eb7-9120-bd96fcb14bba', 'f994ccb3-ea9e-454e-9eb2-1c73f858f3de', 'T3000486', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-27 05:40:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000333';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000485') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5f735362-2fd5-4aa4-b19c-a8cd0bd7564b', '68be9d4b-882d-476f-84c5-a4505f9ff2aa', 'T3000485', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 0.0, 200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 05:38:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000333';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000484') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5a02eb91-2b90-45e8-8cde-2221b3c56411', 'f2371bff-7c68-43bc-abc7-c2eabbf211a9', 'T3000484', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 900000.0, 0.0, 100000.0, 'Robertus - penarikan deposito Oleh: teller1', '2025-12-27 01:02:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000332';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000483') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ed51e8b8-7131-472f-bdbd-4a1bd1051421', '73cce862-9728-4959-ae68-e94feb0a0987', 'T3000483', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 40000.0, 0.0, 10000.0, 'Robertus- Penarikan Oleh: teller1', '2025-12-27 01:01:24', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000330';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000482') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ec45f3ef-3e5c-4c91-9416-d725b8aff321', '48f11df0-16a0-436b-8b85-a70ee8a3f4a4', 'T3000482', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 99000000.0, 0.0, 1000000.0, 'Robertus-Penarikan Oleh: teller1', '2025-12-27 01:00:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000331';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000481') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '64b9d747-f2c3-4348-a1a8-34e87435cf82', 'd9375c03-8be5-4968-9412-57242ddc89d2', 'T3000481', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 00:55:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000332';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000480') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c5fa6f22-13d8-4d08-a25f-27c55300640a', 'a363f015-2b23-49d5-9e6d-bcbeebf37bfe', 'T3000480', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000000.0, 0.0, 100000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 00:54:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000331';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000479') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '392abf5e-2328-4dcb-89db-78f079bebe03', 'bbca2429-fa67-4139-acde-aee4e72d3823', 'T3000479', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Setoran Tunai Oleh: teller1', '2025-12-27 00:53:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000330';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000478') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '149861ac-ae1b-46dd-ba51-18cdcfacdca5', 'dad19e6f-8f2b-4169-a204-752783a2afe8', 'T3000478', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 4000000.0, 0.0, 4000000.0, 'PENARIKAN TUNAI Oleh: teller1', '2025-12-26 23:40:50', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000328';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000477') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fe683edc-5fed-4c28-93f3-316178cac303', '45ec100b-0c15-4857-8e32-c9fbdf22c584', 'T3000477', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 8000000.0, 0.0, 8000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:40:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000328';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000476') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7ad8dedc-9639-43fb-b6cf-5f0ea13d736e', 'c9b167ac-d52d-456e-bd5d-1f3e449d5c8d', 'T3000476', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 890000000.0, 1350050000.0, 2240050000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:36:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000475') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a83916b3-0a19-4826-81f9-78ef00d32ab0', 'ea9045fc-305b-4c77-a228-827e25c8d5b1', 'T3000475', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 500000.0, 1500000.0, 'penarikan tunai Oleh: teller1', '2025-12-26 23:35:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000327';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000474') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '185b7983-2320-4630-b424-252ff15bca13', 'a64bc1c2-2dfa-424d-bb7f-15de2a88b58b', 'T3000474', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 0.0, 2500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:35:06', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000327';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000472') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e68c2ac6-bd37-474d-acf1-ac03b9d59861', '409b8b5d-52cc-47b3-99af-f1bec803011b', 'T3000472', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 450000000.0, 900050000.0, 1350050000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:34:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000470') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bcd8933c-ec16-42fd-a625-ff0b66bef585', '9dbee817-4def-4384-85f7-aa812b66f220', 'T3000470', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1000000.0, 1500000.0, 'penarikan tunai Oleh: teller1', '2025-12-26 23:31:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000326';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000469') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6d98611c-9779-4b5a-ac4a-f63a3ebc0af1', '00e3ae9d-6406-41f3-83d8-9baba185375c', 'T3000469', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000000.0, 400050000.0, 900050000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:31:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000468') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b60682d9-bee9-4902-94f7-32ae2ff6bf88', '9aaa14e1-53eb-4d1a-8625-52f92b57f4e2', 'T3000468', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:31:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000326';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000467') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3d689616-57f5-4ad5-b6fa-8df88d7c5ab1', '728b4259-abb6-4a89-86c8-b5a63773610a', 'T3000467', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1000000000601110.0, 1000000000801110.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 23:09:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000129';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000466') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5ae610aa-7776-4ae4-aaf9-b1d67f8c4e74', 'e2a5e9fd-de59-4788-96bc-b51ca62263d4', 'T3000466', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1000000000101110.0, 1000000000601110.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 22:14:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000129';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000465') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '51a25005-6e32-4e96-9fe6-ad2d4fd4e0d4', '0ce76bcd-9406-432b-8524-a588f52f9e84', 'T3000465', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 18000000.0, 19000000.0, 'penarikan tunai Oleh: teller1', '2025-12-26 22:08:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000324';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000464') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2f17982b-fbce-4d61-8fc7-c2a46d15a23f', '40bc6535-54f4-41bf-b49a-c2b90c08444c', 'T3000464', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 20000000.0, 0.0, 20000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 22:07:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000324';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000463') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd9bd0b70-9edb-4dcd-8829-92fecc7e6d65', 'c0cfc72f-3d72-4d06-815c-c6047df611ab', 'T3000463', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 9000000.0, 9500000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-26 22:04:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000323';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000462') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bc0224ab-70d9-4707-b686-3ab5fe691160', '446264f3-592b-40e8-9c61-e6b183efae99', 'T3000462', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 22:03:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000323';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000461') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '959bf99c-f91c-4024-ac54-272b8913f3fa', '09d5aa96-737c-4eee-9127-8004602dc079', 'T3000461', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 9000000.0, 9500000.0, 'Penarikan tunai Oleh: teller1', '2025-12-26 21:58:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000322';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000460') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '40635b56-dd35-4090-8bca-18890ef24649', 'c95b6abc-a011-4a90-ae48-407ed9a77ccd', 'T3000460', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 21:56:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000322';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000459') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1abfc7d4-ede1-4aa0-aec0-2a1585439757', '28a89db8-cc68-43cc-bcf7-882d2f4c46a2', 'T3000459', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Deposito Oleh: teller1', '2025-12-26 20:07:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000320';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000458') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '88adba66-982e-47b9-b621-d552d8fcb543', '23928217-9248-4b0e-820b-2a6ad0757568', 'T3000458', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 2000000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-26 19:58:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000312';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000457') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e2d65938-c0a5-43b8-9b7d-9f2e781b3f2f', 'c79f9224-2362-45fa-bd2e-3cbffecf3dd7', 'T3000457', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 19:49:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000312';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000456') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ef4f4e1e-e746-4219-9532-bc48e98182d5', '8facfde8-361d-4c66-b33e-76241c0f671f', 'T3000456', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 2000000.0, 2500000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-26 19:26:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000317';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000455') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6fa506f2-c970-4a56-a9f8-2628aa84bad3', 'b2216ea5-6973-479b-9ab4-214e42d26a7a', 'T3000455', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 19:26:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000317';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000454') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b21f8a82-952b-421d-a594-d5519c56ff14', '59176c56-8bf9-4761-9ce8-11578c1de62e', 'T3000454', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 1000000.0, 2000000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-26 19:25:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000316';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000453') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd204b86d-8093-43d3-bb38-04a66bf52ff3', '728a4daf-99e8-4fbb-9ccb-fcd301af9283', 'T3000453', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 19:24:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000316';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000452') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7cf159b3-158e-4fd5-a5f4-0fbf176a63ef', 'e1f38bf9-a36d-46f0-80da-b63bb0510b5e', 'T3000452', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 600000.0, 800000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-26 19:24:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000314';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000451') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2a5c6cb5-2b5d-4ed6-9790-bee99c2b72eb', 'e0835e0e-ebd1-42f7-a173-60dac8fb55fa', 'T3000451', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 19:23:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000314';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000450') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6200b319-e4ab-4b86-94e2-9430998d327d', '5c3de14f-1d50-4c17-9c8c-52e8d2c910f1', 'T3000450', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 100000.0, 1100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 14:49:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000103';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000449') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9f7c4c03-c667-4dd3-9421-6540928a79ab', 'da1799ef-33cf-42ec-ab00-25ebaf579a4f', 'T3000449', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 10000000.0, 20000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 14:46:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000102';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000448') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a602d8bf-393e-4c01-ad30-e409fd213c50', '14c64b8a-072d-4a37-9718-5b4a6f5c7729', 'T3000448', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 100700000.0, 100800000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 12:34:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000447') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd632b44b-b53c-4f1d-8dca-ae14a82757d0', 'ee7bbd28-b94f-40fd-b76a-10273edd55af', 'T3000447', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1500000.0, 0.0, 1500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 11:17:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000310';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000446') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5885a493-0e4d-4f3e-81f0-629196801718', 'e88042b9-df27-43a8-b22a-50742943d9ad', 'T3000446', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 1900000.0, 2000000.0, 'Tarik tunai Oleh: teller1', '2025-12-26 10:56:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000309';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000445') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ee5df557-64a1-407c-9ed9-463dfa361ab0', '36fe4d77-3e58-42ef-b4c2-fefe25047820', 'T3000445', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2100000.0, 0.0, 2100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 10:55:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000309';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000444') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c3441527-99a2-4cdc-9100-59f831435f78', 'ccffeb32-95d2-4a8c-a8b8-5518184ca504', 'T3000444', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, '- Oleh: teller1', '2025-12-26 00:15:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000308';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000443') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd7f32c7e-0643-43ed-a2da-0f2ccba35439', 'c6ed1f44-8099-473f-b38c-9941268934b2', 'T3000443', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-26 00:10:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000308';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000442') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a47e1b54-0aa3-41e8-b173-9ad0d4a69939', '6ccd75b4-a77b-4528-a7ef-34b59b03fdf5', 'T3000442', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 2000000.0, 12000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 23:03:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000305';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000441') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'dcd1320f-b706-49dd-9433-c69dc1fdcd6f', '959b5f20-2806-4f0c-a520-63d3c0b02cf2', 'T3000441', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 1000000.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 23:03:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000305';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000440') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '34b500bf-bd4b-44e1-b23e-33da8d51a5f3', '31e0b08b-ec01-4bb5-bc9d-89835546ce34', 'T3000440', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 23:00:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000305';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000439') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e4beb8c5-98aa-4ac6-8a97-423b10adc5d8', '5ed26b44-f7d7-4738-b4ae-f7d1a71310a0', 'T3000439', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 600000.0, 800000.0, 'buat alya Oleh: teller1', '2025-12-25 22:57:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000237';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000438') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '32e9fdde-27fa-4874-a957-c1c3d89ed45f', '6c890850-610d-4097-a986-3e19647bf0fd', 'T3000438', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 0.0, 'Oleh: teller1', '2025-12-25 22:54:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000304';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000437') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b68c7e4a-0c2e-47e6-a76a-5a42280a9791', '8965e014-d737-46de-9c81-5882c4f9cba1', 'T3000437', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 22:50:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000304';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000436') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '175b980c-85e6-4cb8-bd24-cfc7a97f186d', 'f18aa7e3-624d-4a3d-8c72-8a525622daad', 'T3000436', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'setoran tunai alya Oleh: teller1', '2025-12-25 22:43:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000237';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000435') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7366f1e1-d5d4-47f2-8337-4966335d5caf', 'a83f7b42-8f9b-49bb-88ad-a68db8b31264', 'T3000435', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 0.0, 'Oleh: teller1', '2025-12-25 22:43:01', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000300';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000434') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '48c073c2-8f8b-4bfd-83b8-e65c6156259a', 'f812fb09-4b28-4c95-a65d-de3abf21c295', 'T3000434', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 30000000.0, 0.0, 30000000.0, 'Setoran Tunai Deposito Oleh: manager1', '2025-12-25 22:39:50', 'manager1', 'manager1', 'Manager1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000303';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000433') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fdaa01ac-2417-41fe-93ae-084f679fde18', 'c4800814-0a67-4c08-b06a-9923acf9c549', 'T3000433', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 15000000.0, 20000000.0, 'Belanja Kebutuhan Pokok Oleh: teller1', '2025-12-25 22:33:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000272';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000432') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c7342ee7-b19f-41b4-9056-19236723f937', 'c7c307f5-950d-4b31-bd16-bf0ecd0fc1ca', 'T3000432', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 25000000.0, 0.0, 25000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 22:32:06', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000272';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000431') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2f42d55d-33a7-4aae-8ad2-bca4218ae42c', 'fe39985a-0ddd-4cad-8a2c-34179dec2302', 'T3000431', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 22:19:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000300';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000430') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b57a6d78-0f10-4f0e-8a1f-3a9e7a1d4efa', 'b829f491-111c-4a0d-aab4-bf407442d7ae', 'T3000430', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 87100000.0, 87200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:56:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000298';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000429') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '562ce337-87e9-4000-9f9f-15c4ea5ff7a8', '435c35b1-66ff-4104-8f6e-ebdcb5b8105d', 'T3000429', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 87000000.0, 87100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:54:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000298';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000428') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5d477299-fb62-4133-8a8e-457a254e54dc', '7cdeac1d-3004-481e-847d-ba64acca768f', 'T3000428', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 80000000.0, 87000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:46:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000298';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000427') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a51930a5-1313-478b-8fbb-5bd6ab3fbde3', '6339aabc-a07b-4b6f-bfc9-1040ca7d029b', 'T3000427', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 70000000.0, 10000000.0, 80000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:46:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000298';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000426') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '749b8fa8-2aa8-4f66-84c9-547b657e8fd5', 'd1254869-8606-42db-a9fc-cb3dfc073d3d', 'T3000426', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:41:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000298';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000425') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0667665f-405b-4003-ab01-8615632fb1cc', '7b495eab-258e-46cf-9ffe-f6cc034fc485', 'T3000425', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 20000.0, 60000.0, 80000.0, 'Oleh: teller1', '2025-12-25 21:21:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000297';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000424') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '827f5b40-8abc-4035-9a3f-bbca8dc5bcd0', '8a39a2ff-9334-4047-9cb0-8d711bfe1b63', 'T3000424', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:21:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000297';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000423') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b70d8801-6139-4fff-abe3-25d2e64725ef', '0ee4b032-b68b-49e5-a618-395734d17039', 'T3000423', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 40000.0, 20000.0, 60000.0, 'Oleh: teller1', '2025-12-25 21:09:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000296';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000422') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ca5a9131-aa33-43ae-94be-80733000fb02', '1f569a99-d933-4289-b14a-2d50932cbe10', 'T3000422', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:09:17', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000296';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000421') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ac685b1a-cd7b-4839-abb8-ea0ec33e1c86', '85c3382e-fd97-4f3f-9b79-75541e4af74a', 'T3000421', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 40000.0, 20000.0, 60000.0, 'Oleh: teller1', '2025-12-25 21:07:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000295';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000420') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f2d3dfb4-08c4-4eba-b2b8-d0049862a413', '88c7391f-20cf-4a9e-bfbc-065688f51449', 'T3000420', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 21:06:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000295';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000419') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'da5bdf98-7cd4-4343-be6e-f937d2ba79a7', '0714d51a-49f8-404c-a4ef-51a377a23200', 'T3000419', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Penarikan tunai Oleh: teller1', '2025-12-25 20:20:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000294';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000418') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7551be01-576f-408d-81d8-336dbd62e97d', 'f2875976-9d04-4951-a617-21d33748c458', 'T3000418', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 20:20:06', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000294';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000417') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd06e6dd8-4a76-4af2-97cf-608e1a748da8', 'fd5e54e2-90a5-4e99-a8cf-8c8000b41da5', 'T3000417', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Pnarikan tunai Oleh: teller1', '2025-12-25 20:18:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000293';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000416') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6ef5f865-8078-4ad6-b452-4a56a5b6f4dd', '5fba056d-c6a0-4522-b638-3f4a22ea1b67', 'T3000416', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 20:17:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000293';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000415') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c4b916f7-ff93-4a84-b0d9-c3d54932a691', '151103e0-8a46-4117-80c0-403af06c57cf', 'T3000415', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 1000000.0, 6000000.0, 'Penarikan Oleh: teller1', '2025-12-25 19:39:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000292';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000414') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0f96c6d3-9c66-4961-9ac5-63c3b871f02d', 'f568fff9-92c8-49ca-bff4-afd48a0fca95', 'T3000414', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 1000000.0, 11000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 19:38:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000292';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000412') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '96b00d9d-32b9-413a-975d-fc4e525b1c35', '4ccfa8dc-ad3f-4392-a09b-d705717b06fa', 'T3000412', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 19:31:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000292';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000411') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9127ae41-b0d4-403f-8ecb-aa7dc47c8163', 'e60e05e9-0bc3-41c5-9bbd-5b32dc527e0f', 'T3000411', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 4000000.0, 0.0, 4000000.0, 'Penarikan Oleh: teller1', '2025-12-25 19:31:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000291';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000410') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '448f4720-01b4-44b3-8e82-d958b1a9c495', '6ec07ad0-adc5-445c-afbe-21325679ac66', 'T3000410', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 8000000.0, 0.0, 8000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 19:30:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000291';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000409') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b6057ec8-be3e-48bc-a228-25fec4e2aeef', '8d12812f-5d40-4e27-a2d0-01a062b451f7', 'T3000409', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 1000000.0, 3000000.0, 'Penarikan Oleh: teller1', '2025-12-25 19:29:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000290';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000408') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1ab4be7d-abbe-4e9b-9e72-1788e25f3502', '21fb799b-a8f5-4cec-bd1a-62f426d4d820', 'T3000408', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 19:23:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000290';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000407') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '07c64760-ce8e-400f-897b-dbc6c4de7f81', '7806d38e-3893-40d8-96f5-d097083fdc9f', 'T3000407', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 12000000.0, 0.0, 12000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 15:33:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000289';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000406') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '934d7e7c-8be2-4c61-801c-809befc6dccc', '722f8bdc-7393-4a87-ab1d-b19e0818de35', 'T3000406', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 800000.0, 1000000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-25 15:31:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000288';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000405') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3c288a24-7676-42bd-b7bb-3efd8df8800e', 'a661bbe3-ba04-4e9d-af3e-65816037de5c', 'T3000405', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1200000.0, 0.0, 1200000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 15:31:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000288';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000404') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6bd92ee1-b063-4f6b-8a71-e1b5166f19d3', '999416e1-eb5d-42b3-9bf2-cfc08d191262', 'T3000404', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 300000.0, 400000.0, 'Penarikan tunai Oleh: teller1', '2025-12-25 15:19:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000287';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000403') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a89c7be9-cff5-4039-b74b-e0d0f4fb4c84', '653149e4-cea1-4711-ab41-8a7486be36f3', 'T3000403', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 15:18:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000287';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000402') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '974a5c78-3efc-4c26-a218-809d219a919a', '94f27bbd-9e48-49b5-bbfb-b6ee47fd6e6c', 'T3000402', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 9999999.99, 0.0, 9999999.99, 'Setoran Tunai Oleh: teller1', '2025-12-25 15:04:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000286';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000401') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b5ca11cb-4664-49c6-afa4-983d7477f61c', '121ba134-52f4-441d-87ab-392180dc4866', 'T3000401', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 3000000.0, 4000000.0, 'penarikan tunai Oleh: teller1', '2025-12-25 14:59:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000284';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000400') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0d5ad3d5-bb37-4a85-b8ba-91a329fd46e5', '228faf03-02f6-4f8f-a374-3098fea1bc66', 'T3000400', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 14:57:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000284';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000399') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'dbbd0753-7aba-4bf3-9c4e-d2912febfa6b', '6cc76977-ddb7-4a99-a5be-27d8a7021658', 'T3000399', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 15000000.0, 0.0, 10000000.0, 'tarik tunai Oleh: teller1', '2025-12-25 12:52:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000279';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000398') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1c6d9934-c584-4f34-9676-a7eb4c7fb4a1', 'a7d6fe2a-3641-4cf0-9769-03e08d5ffd00', 'T3000398', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 25000000.0, 0.0, 25000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 12:51:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000279';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000397') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ace9f423-0daa-489d-847f-a5961e582698', '280149b0-6963-471c-aefe-c347ec4206f9', 'T3000397', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 8000000.0, 10000000.0, 'tarik tunai Oleh: teller1', '2025-12-25 12:51:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000278';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000396') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b24e7737-a9e9-46a7-8fd4-02a0d9b17e64', '43da20f3-6459-4ac6-9d27-d3fb2f0d9392', 'T3000396', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 12000000.0, 0.0, 12000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 12:50:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000278';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000395') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6c95e9d1-353c-4fa5-bc42-18ab07ad5edc', '7b5aaf54-e693-4fb0-a0a5-9578266402a6', 'T3000395', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 1000000.0, 4000000.0, 'tarik tunai Oleh: teller1', '2025-12-25 12:48:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000277';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000394') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '29f81aa0-dd05-4b49-8357-7ca40e8f195a', 'ffe4d451-21a6-4a92-97db-5a1b01779367', 'T3000394', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 0.0, 7000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 12:47:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000277';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000393') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd3b30c07-337f-43b9-8ac6-aaa6021949ad', 'fd4290ef-b1dd-4826-a305-85dd8b12d71e', 'T3000393', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 2900000.0, 2950000.0, 'Oleh: teller1', '2025-12-25 12:07:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000270';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000392') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '24075b7d-1ef2-42e2-932e-65cd49e45e36', '74ed4bcb-0441-48a0-a94a-31ff95eb544e', 'T3000392', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 240000.0, 2760000.0, 3000000.0, 'Jajan Oleh: teller1', '2025-12-25 12:04:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000270';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000391') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4b74259e-ed45-48bb-adc6-29177018ce18', '9ce3d790-b8f2-443f-995f-f43689ef2603', 'T3000391', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 770000.0, 2470000.0, 3240000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:58:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000270';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000390') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6df488f8-858d-4016-965c-0cc7a9dd92cc', '58c03d22-c96b-4c93-93ae-cc2f3ab91f3a', 'T3000390', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 600000.0, 800000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:53:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000194';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000389') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3c9cd10c-4234-4b1b-9bcd-51235dd929c9', '68d730ce-051a-43b0-a685-ededf459de16', 'T3000389', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 770000.0, 1700000.0, 2470000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:53:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000270';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000388') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8682db4a-0f83-444a-af7b-9a4902cfa379', '51984266-149e-4ec8-98be-961f0e84e210', 'T3000388', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 400000.0, 600000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:52:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000194';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000387') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e51f4b53-a6bc-426c-95b3-4ac9854fca04', '9348acc8-fd35-4863-b3d7-7338eabddd35', 'T3000387', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 700000.0, 1700000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:47:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000270';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000386') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cc152dbd-1dd6-42c2-a11b-9d5434ccb53f', 'f5c0ac3e-7f3b-47bc-bd8e-a434e19c0b10', 'T3000386', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 700000.0, 0.0, 700000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:42:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000270';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000385') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fbebada9-c087-4941-8fdc-b9b6ec4a5121', '43dfcf18-425e-4ade-ab09-996f847050b4', 'T3000385', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 6200000.0, 6400000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:33:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000113';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000384') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '46582f5b-68db-411e-b060-8215c60c0f22', 'c8124613-db53-4985-a99c-d3dd5748a191', 'T3000384', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 500000.0, 700000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:27:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000231';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000383') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '70dfb92c-863d-4a3f-9e3c-d87d9c1c1b7b', '86272cdd-3767-499c-a472-dc6e31cb5766', 'T3000383', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 200000.0, 400000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:20:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000194';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000382') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0cf0b57d-603c-4a9b-a5b0-571e06c084fc', 'c571ec2e-3eb4-4b94-ae68-10e75cef9973', 'T3000382', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:14:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000120';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000381') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '93569392-6550-4a92-91fd-133a680ac468', '4712b420-5dfb-4d76-9088-1740c5c7d101', 'T3000381', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000000.0, 5550000000.0, 6050000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 11:09:28', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000118';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000380') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ba64e9ad-08fb-483c-943e-77a330bd62df', 'd5e2b65d-18e6-4df1-bd4f-e346f1147dc0', 'T3000380', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 0.0, 7000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 08:46:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000269';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000379') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5c8dc11f-d271-4cca-b508-5b47b7987d18', 'dca46a77-dd75-46a7-999e-d1a6569568c5', 'T3000379', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 3000000.0, 4000000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-25 08:36:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000263';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000378') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '823d899d-f300-421e-aafb-3120958ce440', '0b4963b0-dbb9-4da4-a353-485b659b6219', 'T3000378', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 08:36:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000267';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000375') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '88818f12-7be3-4714-9cc6-d20414aa2c55', '1b864af7-e8fe-4a47-a025-ef93602f8945', 'T3000375', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-25 08:33:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000263';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000374') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6a393028-78f0-4055-8feb-3b243d762a03', '6780108e-c83e-41dd-856d-995d86fb537f', 'T3000374', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000000.0, 100000000.0, 300000000.0, 'Tarik tunai bisnis Oleh: teller1', '2025-12-24 21:58:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000259';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000373') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '80589e36-aa0a-4c4c-99f6-e602665fe52a', '36eb407d-7fba-4fe0-874b-f80d2598cd27', 'T3000373', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000000.0, 0.0, 500000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 21:57:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000259';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000372') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '51988730-7b97-4a31-8591-f62a0fdfb441', '26626649-2160-460b-b258-8c9b004262f4', 'T3000372', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000.0, 50000.0, 50050000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 21:36:01', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000250';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000371') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2b669a26-f1e6-40ee-9ae7-889959e01086', 'bf6a5f52-8bc7-41eb-a3f5-acd704225945', 'T3000371', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Setoran Tunai Oleh: manager1', '2025-12-24 21:31:16', 'manager1', 'manager1', 'Manager1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000250';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000370') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0cf0541f-5247-495f-b88d-c36edd06862c', '3bcdab08-781e-481d-9628-abb32014b006', 'T3000370', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000007.0, 0.0, 2000007.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 20:30:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000255';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000369') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd42f2ff1-5025-488a-ab2c-5418b3e37d72', '1caa3bb5-f874-4085-8ca9-c71216cae9fa', 'T3000369', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'tarik tunai Oleh: teller1', '2025-12-24 20:23:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000248';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000368') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0bca8e96-8b4a-4534-90bb-808abc87cac0', 'c9b2fde5-3b3f-40f0-8cbc-9bb9da72d3c2', 'T3000368', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 20:19:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000248';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000367') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5948ed82-815a-4c92-aa8f-05a22039143b', 'fd8e547a-a343-4706-9614-738b746d71fa', 'T3000367', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 20:14:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000254';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000366') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fc152271-5e9e-4924-a7b2-2afb48125957', 'e0d5fd1a-f796-4641-8485-252f3e3ba99f', 'T3000366', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Penarikan Tunai Oleh: teller1', '2025-12-24 19:35:45', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000252';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000365') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ebb782af-edce-4cd2-a14e-6af171fe0a18', '43af4b79-8e39-4fe9-8b6a-875aa05dc14e', 'T3000365', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 19:34:24', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000252';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000364') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e393998d-22ce-4de7-bc4c-8988fe5289f7', 'bed2d951-4b36-40a2-936b-31d53e6ecd9a', 'T3000364', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Oleh: teller1', '2025-12-24 13:07:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000247';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000363') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'acd0366b-12f4-4eed-8610-c69e81e3fda9', 'd33e07b6-0c14-450f-9d15-460078c27490', 'T3000363', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 13:07:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000247';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000362') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c42718e8-f40f-4708-ade3-c7f818c99c9e', '8441fdec-293b-4502-a1fc-1f43e4155d41', 'T3000362', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Oleh: teller1', '2025-12-24 13:06:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000246';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000361') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f83c03b8-6075-4727-83ed-a2a7fb52ad43', '02f2bed2-b65d-4747-917b-e6e45f75a00d', 'T3000361', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 13:06:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000246';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000360') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e4c530cb-429b-442e-9022-8488a31edd74', '565f5227-25af-422e-a025-2fa591fa3d6e', 'T3000360', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Oleh: teller1', '2025-12-24 12:59:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000245';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000359') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5eba6f3c-689a-4f48-9a58-f0b1678ca26f', '643e0325-2d9a-45c1-b24c-dddda017eec1', 'T3000359', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 12:57:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000245';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000358') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f9ce98c7-d4e9-4497-94a2-8f08f9a40233', '58c10835-878a-4f3b-8584-4d08ba43bebe', 'T3000358', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 300000.0, 1300000.0, 'Penarikan Oleh: teller1', '2025-12-24 12:49:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000244';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000357') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e0415110-9bfd-404a-8f2c-3811a524f460', '52ac4335-57fc-4bfc-8430-035b92ee1545', 'T3000357', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2300000.0, 0.0, 2300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 12:47:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000244';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000356') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6c28cd97-964c-48e4-890e-e6002812aa44', '8bdc1c6f-3663-4205-b048-4efdf2a61319', 'T3000356', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1300000.0, 1800000.0, 'Penarikan Oleh: teller1', '2025-12-24 12:43:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000243';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000355') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6a9875f3-43e7-4c04-bf88-fae71f3d1f14', '5ba63dce-1525-4def-a7b9-420d31f73513', 'T3000355', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2300000.0, 0.0, 2300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 12:42:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000243';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000354') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5e1b3ef0-d7ec-4224-95fd-f05fcecfc719', '43f9190f-84a4-4827-99c0-909565e36190', 'T3000354', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 300000.0, 1700000.0, 2000000.0, 'Penarikan Oleh: teller1', '2025-12-24 12:41:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000242';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000353') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '45aa9d52-e387-4a46-a421-d76b1f15c6b0', 'd865e173-46ed-428f-b3b6-66c7ba2ccaba', 'T3000353', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2300000.0, 0.0, 2300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 12:39:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000242';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000352') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'aa39a0eb-6126-4383-bc5e-5c97923d97c4', '103c39c6-c071-4bcd-99cb-cb80289c908e', 'T3000352', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 12:36:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000097';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000351') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '22a45075-81c9-4d4a-94e2-f5ca11439c75', '7ce63e99-33b8-4153-ad91-58df1aaf0d4e', 'T3000351', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Setoran Deposito Oleh: teller1', '2025-12-24 05:48:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000241';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000350') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9df5e3a8-8a3f-4532-a0f8-fd1b16fad3a7', '8f8a86d5-59a0-4553-b47a-1363323cd02a', 'T3000350', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'untuk belanja bulanan Oleh: teller1', '2025-12-24 01:56:50', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000239';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000349') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bcb8f94f-8404-42f5-a00f-aecf2179f4d3', '0ae31acc-7fe5-46f0-8fb7-1bae350c7de0', 'T3000349', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai Oleh: teller1', '2025-12-24 01:55:44', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000239';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000348') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2703cbf7-1514-4978-b2fe-b5f1653094ec', 'c37dba7c-8d1c-409c-9013-013027ad76d1', 'T3000348', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 30000000.0, 1200000.0, 31200000.0, 'Setoran Tunai', '2025-12-23 15:53:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000084';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000347') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9a1a2c7a-52cc-47a9-b7ad-25987cab39cf', 'e9dfdfd5-1757-4ee1-98bd-5bf4e698cb37', 'T3000347', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100500000.0, 100700000.0, 'Setoran Tunai', '2025-12-20 14:40:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000346') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fe55c4dd-5263-448b-88b0-697b739f75c1', '56368289-d720-4da4-bf29-aa6afec0bf06', 'T3000346', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 250000.0, 450000.0, 'Setoran Tunai', '2025-12-20 14:33:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000135';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000345') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6c3466d5-487d-4936-aeb4-fb934311d794', 'bdf93034-8a77-4a23-be09-6217438a1565', 'T3000345', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 15000000.0, 25000000.0, 'Setoran Tunai', '2025-12-20 14:27:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000128';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000344') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e4df3cf2-3b90-40d8-9f00-abbb147d8697', '7be74524-2ec6-4da4-ac13-a2c1adc34132', 'T3000344', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 5000000.0, 15000000.0, 'Setoran Tunai', '2025-12-20 14:23:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000128';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000343') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0b13bc28-7462-4f92-9f37-769ca5cd09a5', '86611da1-a79e-4929-8254-c703db46e997', 'T3000343', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000000.0, 200050000.0, 400050000.0, 'Setoran Tunai', '2025-12-20 14:18:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000342') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ecdd42f2-3162-4f17-a17f-88c2dc07cab9', '4bfaebc2-ea65-4c3d-b383-6a91483224f7', 'T3000342', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000000.0, 50000.0, 200050000.0, 'Tabungan', '2025-12-20 14:17:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000341') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3a783024-a3de-4106-b60b-0be8286a4e87', '44efe648-e6fe-4b1c-8c40-cf8f8fbefc35', 'T3000341', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000000.0, 100000.0, 200100000.0, 'Setoran Tunai', '2025-12-20 14:16:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000126';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000340') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a6646d65-eee8-4786-a9ef-08893d475e2c', '318b04bf-9b31-41bb-a1da-ae6881e312c6', 'T3000340', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 100000.0, 300000.0, 'tabungan', '2025-12-20 14:14:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000127';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000339') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7dce8d84-7154-4893-9536-13270e94e78e', '06483e53-f612-459e-bc05-149113845ffe', 'T3000339', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 150000.0, 50000.0, 200000.0, 'Tabungan', '2025-12-20 14:12:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000134';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000338') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '338daff4-f457-426c-9f81-b9b89a57cad9', '63f94d3c-aeb9-43e9-894a-e24961cc951c', 'T3000338', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 50000.0, 250000.0, 'Setoran Tunai', '2025-12-20 14:07:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000135';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000337') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ae9559df-18a4-4cca-8f88-e27a684509d7', '31bff717-b680-4cdd-8f1b-ba705362307a', 'T3000337', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 1000000.0, 2000000.0, 'Setoran Tunai', '2025-12-20 13:56:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000124';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000336') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd646fd3f-0772-4142-9aed-5ed263e098be', '38926e39-c9d3-482a-b56c-3f718bc0585d', 'T3000336', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 51111.0, 1000000000049999.0, 1000000000101110.0, 'Setoran Tunai', '2025-12-20 13:56:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000129';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000335') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fb07b52d-afa9-4a6d-8938-1a34fdcb9c3a', '99bb9449-6056-4604-83a9-0d1b77cb5546', 'T3000335', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 50000.0, 2050000.0, 'Setoran Tunai', '2025-12-20 13:54:50', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000130';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000334') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '068a8536-323e-4bbc-854b-f93cbdde4ad0', 'da6f91cb-eda1-4f9c-8dde-fed70d2600d1', 'T3000334', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 1000000.0, 3000000.0, 'Setoran Tunai', '2025-12-20 13:53:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000123';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000333') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2220d63c-d930-4561-9f2b-e9a8a2982868', 'b21e9394-e46b-47a1-9ed1-575a06f3f90a', 'T3000333', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 100000000.0, 100500000.0, 'Setoran Tunai', '2025-12-20 13:47:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000332') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9acb8a9f-2a0d-426b-ad9b-dee8e2529eda', '414de712-1c20-4ba6-84b0-bbcc1aad1547', 'T3000332', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 999999999999999.0, 50000.0, 1000000000049999.0, 'Setoran Tunai', '2025-12-20 13:46:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000129';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000331') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd755e0d4-8a91-4280-88f8-6dfedbbbf208', 'f1fdf080-0237-4d58-a34f-157cc6cb1322', 'T3000331', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 6000000.0, 6200000.0, 'Setoran Tunai', '2025-12-20 11:52:24', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000113';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000330') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eae88478-556f-488a-802e-bc8936143792', '501851b7-1cd6-4bfa-a984-648af5b607a8', 'T3000330', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 200000.0, 400000.0, 'Setoran Tunai', '2025-12-20 11:42:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000122';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000329') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '96c66190-3662-4a83-be06-2acddb64372e', '8ffcb253-2e1a-4edc-9457-5c81bc40bea4', 'T3000329', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 5000000.0, 10000000.0, 'Setoran Tunai', '2025-12-20 11:36:24', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000117';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000328') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f770d5f1-ed39-4b32-af2f-9896178bd322', '77319cba-470d-4bb3-be21-032d732256bd', 'T3000328', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 550000000.0, 5000000000.0, 5550000000.0, 'investasi dan nikah', '2025-12-20 11:31:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000118';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000327') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '98af1f04-fd10-43bc-815d-cf05a836c983', '82e1ad03-e0e3-48e0-9cf7-219678a1320a', 'T3000327', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 100000.0, 600000.0, 'Setoran Tunai', '2025-12-20 11:28:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000111';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000326') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7ca1626f-bdbf-452e-857b-dbb454776eab', '10787005-cc29-446d-8143-5f4d1c8151a4', 'T3000326', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Setoran Tunai', '2025-12-20 11:27:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000226';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000325') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ae7a1597-8eee-4c06-bcc2-b9a477db0903', '5bf49c28-ca1e-4552-8758-140bf298129d', 'T3000325', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 50000.0, 550000.0, 'Setoran Tunai', '2025-12-20 11:25:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000138';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000324') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8c3a6d50-2f89-479b-b203-8dd29efe630d', 'bb6b9ce1-b0a6-4663-b439-fbcf492207e2', 'T3000324', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 100000.0, 600000.0, 'Setoran Tunai', '2025-12-20 11:23:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000112';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000323') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1e9d8cda-b2d0-4d03-ad92-cccec331216e', '6c5024ce-6e55-4bda-9b48-51d4bc94189f', 'T3000323', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai', '2025-12-20 11:18:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000234';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000322') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '560cf6f4-cab1-4e13-a473-404d99523d4c', 'b3f78f67-e318-4a7c-b58b-ccd6e7c23fab', 'T3000322', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai', '2025-12-20 11:15:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000230';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000321') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6007e049-b274-4100-a2c1-d35d75cda061', '445f4979-5f47-4bcb-992c-f068bc7a625b', 'T3000321', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai', '2025-12-20 11:13:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000224';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000320') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9a9d476b-2f2d-4cb8-a3ce-6cf5585e7390', 'abb72224-a76d-411c-a4fd-dff7842f430d', 'T3000320', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai', '2025-12-20 11:10:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000223';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000319') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '76a6ed94-3c6d-4220-9364-fb07204d42ab', 'afa0df2a-0d8a-4870-93ad-8e280df8092c', 'T3000319', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Setoran Tunai', '2025-12-20 11:08:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000227';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000318') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '52873f07-2fc1-4d36-9ac4-66601376d364', '5b05fb65-96a6-4e48-afdb-bdfefc42af6e', 'T3000318', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 700000.0, 0.0, 700000.0, 'Setoran Tunai', '2025-12-20 11:07:24', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000228';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000317') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6f050156-fb29-4174-bb11-af7638b5e32f', '2d55cbdc-e384-403d-ad57-d7e48bc9af56', 'T3000317', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 30000000.0, 30500000.0, 'Setoran Tunai', '2025-12-20 11:05:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000119';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000316') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '704b9dd4-5c9c-4047-af06-11f59366d667', '1d43a343-e143-46fc-b863-7c10dd226e01', 'T3000316', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 1000000.0, 6000000.0, 'Setoran Tunai', '2025-12-20 11:01:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000113';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000315') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '21cdd8a3-e533-43b8-8c99-59c6d72c9386', 'f82b6e30-5ee2-4172-b5eb-30ef50b7a6d8', 'T3000315', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Setoran Tunai', '2025-12-20 11:00:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000233';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000314') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5fd50810-59d0-48bb-8a23-e487edeee1e0', 'f2e64466-3f6a-4a6d-86f2-5d83250fb669', 'T3000314', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 15000000.0, 5000000.0, 20000000.0, 'Setoran Tunai', '2025-12-20 10:58:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000116';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000313') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '75586947-7e31-4807-b04b-79d2d57b89ff', '2683bbfe-acb8-4bfd-be68-34abc5544397', 'T3000313', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Setoran Tunai', '2025-12-20 10:57:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000231';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000312') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7076c69e-c700-4709-bb86-f10501fb68c5', 'c665dbd3-240b-4446-b4a6-82e4b78ad046', 'T3000312', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Setoran Tunai', '2025-12-20 10:49:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000232';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000311') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '51d0221b-08f3-4964-a2df-974e177685e3', '479989e5-aa67-4e96-baab-23cfcdddfd71', 'T3000311', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 100000.0, 200000.0, 'Setoran Tunai', '2025-12-20 10:47:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000122';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000310') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b4b3a11d-ab80-4133-8982-c0c0e18cbe4b', '77869d9a-ce67-4117-96aa-c178c5c0b885', 'T3000310', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 0.0, 200000.0, 'Setoran Tunai', '2025-12-20 10:46:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000194';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000309') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eb35c00e-41eb-4654-8f9b-86b36783ae69', '8bab096a-091c-4996-88f1-994b67ff3313', 'T3000309', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 0.0, 200000.0, 'Setoran Tunai', '2025-12-20 10:44:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000225';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000308') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '74e42515-c37d-459f-82e9-de6af90a788e', '6038b60d-9cea-491d-a6fe-a5ca7813985e', 'T3000308', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 50000000000.0, 50000200000.0, 'Setoran Tunai', '2025-12-20 09:41:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000090';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000307') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '52163d0c-1e83-4da6-a4e7-a40c854a110d', '6a7ad417-7072-4ca2-9e36-bedf5c528595', 'T3000307', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1400000.0, 1600000.0, 'Setoran Tunai', '2025-12-20 09:33:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000190';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000306') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7619be26-960f-427b-ac48-d17c8a2e79ae', '58552d4b-d972-49b2-8f92-dfc210051b90', 'T3000306', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1200000.0, 1400000.0, 'Setoran Tunai', '2025-12-20 09:25:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000190';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000305') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '75a8e6e1-5840-4a6f-8912-29dd78fc5ce3', '3468eead-3ceb-420c-a104-f55c324ae779', 'T3000305', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1000000.0, 1200000.0, 'Setoran Tunai', '2025-12-20 09:18:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000190';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000304') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '229f8ba5-57e9-4f11-a6a1-ac2265f17023', '5ea6a002-349f-431f-a77b-56bea2384fe4', 'T3000304', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai', '2025-12-20 09:12:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000190';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000303') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8958e24c-c220-4ad7-b90d-4b8e41ceb644', '0a45470c-ee4c-4404-87b3-2d744e6657e8', 'T3000303', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1000000.0, 1200000.0, 'Setoran Tunai', '2025-12-20 08:32:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000084';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000302') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8dc28255-263b-4f43-95bc-9ca610324131', '2f638b80-8d28-4771-beaa-133410660808', 'T3000302', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Setoran Tunai', '2025-12-20 08:23:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000142';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000301') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8a685092-1d39-49ab-9dec-fac27a5b3ede', '75b3e8e7-4700-4b3a-bcf8-32f59f1f8a5e', 'T3000301', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000.0, 1150000.0, 51150000.0, 'Setoran Tunai', '2025-12-20 08:17:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000028';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000300') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '13df7743-88d1-436d-92cf-531a25d57081', '16379a40-6f78-4997-823a-183f28f67af6', 'T3000300', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000.0, 50000000.0, 100000000.0, 'Setoran Tunai deposito', '2025-12-13 16:48:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000188';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000299') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bf070aaf-4559-4344-9de5-b4d370c5c736', '51ab07b5-fe44-4fda-90f9-44ff7aa5354a', 'T3000299', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000.0, 0.0, 50000000.0, 'Initial deposit for account opening', '2025-12-13 16:42:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000188';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000298') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ca41021c-7ace-41db-9435-343aaec40dcb', 'dd0350fb-9676-4c2f-8d7e-4eb374b55043', 'T3000298', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 1000000.0, 2000000.0, 'Setoran Tunai', '2025-12-13 08:10:17', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000162';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000297') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '95e0752d-dc3f-48d0-b206-4913d214fde9', 'a5e6c018-ead7-49a9-99d8-b029f00aa893', 'T3000297', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000074.0, 10000074.0, 11000148.0, 'Setoran Tunai', '2025-12-12 22:02:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000187';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000296') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2ee319e4-2907-4120-b1ea-b73f188f3b26', 'd1141379-cc07-4d5b-990a-585aed33c8d0', 'T3000296', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000074.0, 0.0, 10000074.0, 'Initial deposit for account opening', '2025-12-12 21:15:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000187';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000295') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a9384dc2-502a-4877-9a7c-f9adbdfcd02e', 'ba8deea6-46a9-461f-8ffa-a0233da0fb43', 'T3000295', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 2000000.0, 4000000.0, 'Setoran Tunai', '2025-12-06 08:50:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000186';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000294') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a1e1ac66-731f-4372-b295-a3aa5694101b', '77a49788-dd37-4560-9a1a-de90c0367e06', 'T3000294', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-12-06 08:47:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000186';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000293') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6ebaf19e-eb6b-4278-8798-7f238fa6c26e', '161b7226-9cc0-476e-9b2c-2291905275c5', 'T3000293', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-12-06 08:23:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000185';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000292') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4a53fe7f-d268-46c7-9392-0c5f1b00bf11', '2e82515b-5f0d-41b2-afb5-9393c27dde67', 'T3000292', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7700000.0, 0.0, 7700000.0, 'Initial deposit for account opening', '2025-12-06 08:13:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000184';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000291') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8d0b44af-5e69-4855-914b-cce782ed73af', '0888e727-fd5a-4ab0-8ddd-4b87d4309bb1', 'T3000291', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000002.0, 4000009.0, 5000011.0, 'Setoran Tunai', '2025-12-05 22:13:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000183';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000290') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ecd64932-2799-4aae-ba9d-1f571d884172', 'a73bd16d-41fc-472c-8471-ffcdd89a0d21', 'T3000290', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 4000009.0, 0.0, 4000009.0, 'Initial deposit for account opening', '2025-12-05 21:58:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000183';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000289') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'edcc520a-9be2-417d-affb-1793450d73ad', '055c3a01-9ae4-4c74-aae5-04a02cac6ec9', 'T3000289', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 900000.0, 250000.0, 1150000.0, 'tarik tunai', '2025-12-05 21:35:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000028';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000288') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '961d45b6-0cb4-4c54-9e1a-73ed066f2c63', 'fe6648e0-0f38-4612-b4ed-e32055804f9b', 'T3000288', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000.0, 110000.0, 120000.0, 'Setoran Tunai', '2025-12-05 21:13:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000168';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000287') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e1d5a2ff-847e-451b-8af8-feaecbcd414d', 'd29ee9aa-6a71-470c-858e-2035a7b6731f', 'T3000287', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 250000.0, 250000.0, 500000.0, 'Setoran Tunai', '2025-12-05 21:01:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000180';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000286') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '528df538-546a-4ec3-a2ed-2f9cd4b48ee0', '812d75d0-da94-41d6-a248-f68528d9f32f', 'T3000286', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5500500.0, 2987650.0, 8488150.0, 'Setoran Tunai', '2025-12-05 21:01:06', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000169';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000285') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c071a3c7-ff11-42fa-b370-ebcc145e5929', '3157411a-f49f-47f7-8f34-a73fad0cc1ea', 'T3000285', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 25000000.0, 25000000.0, 50000000.0, 'Setoran Tunai', '2025-12-05 20:59:44', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000181';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000284') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a0a1b658-cd8a-45f1-832a-32c022408846', 'e33256fd-ddb6-48d4-b3a8-494aec4648a6', 'T3000284', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5750000.0, 5750000.0, 11500000.0, 'Setoran Tunai', '2025-12-05 20:58:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000171';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000283') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '086e7fbc-a711-4de4-8bf1-51dc8f112f03', '0baaaace-947a-45bb-8f93-55e5df614cf6', 'T3000283', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2150000.0, 2150000.0, 4300000.0, 'Setoran Tunai', '2025-12-05 20:58:45', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000152';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000282') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1b1ce6f4-2021-4636-af96-bea28b28488d', 'fd88a97f-f4cf-4e9a-90db-0204000540b1', 'T3000282', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 2000000.0, 4000000.0, 'Setoran Tunai', '2025-12-05 20:58:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000160';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000281') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b094fd72-f0e9-4fff-a4ed-aa8ca373ebdc', '7bbc9390-ea51-406f-8cc5-f2e88475d0c1', 'T3000281', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3500000.0, 0.0, 500004.0, '', '2025-12-05 20:58:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000167';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000280') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd1c24615-c96b-47d8-9614-5f14339aad3d', 'd774436b-4191-41db-853f-0ce37a648053', 'T3000280', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 3000000.0, 6000000.0, 'Setoran Tunai', '2025-12-05 20:57:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000178';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000279') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1a215e19-0b9e-44d9-adb4-2582e471489e', 'ea5e231a-a7c1-4c99-936f-3f6bc1d21305', 'T3000279', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 2000000.0, 2500000.0, 'Setoran Tunai', '2025-12-05 20:57:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000159';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000278') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'af8cec20-36f9-4bcc-9445-6d0ca9156749', '614c55a0-b867-4ad0-adb4-f6cce028df3a', 'T3000278', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2150000.0, 11100000.0, 13250000.0, 'Deposito', '2025-12-05 20:57:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000011';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000277') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '21c028b1-94f1-495e-a5b1-95dca8b6f304', '79c1bc8b-2091-45e3-9220-81e110dac339', 'T3000277', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000004.0, 2000004.0, 4000008.0, 'Setoran Tunai', '2025-12-05 20:57:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000175';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000276') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6c64721f-072d-4948-9d38-9a937c6c8994', 'dbf9369d-f418-4972-96a2-b4d4cd46cb4d', 'T3000276', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 259999.0, 250056.0, 510055.0, 'Setoran Tunai', '2025-12-05 20:56:50', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000179';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000275') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '82d46fc4-c785-4fe5-bdf5-a6638f10a733', '958619ea-b14b-47bd-990c-85e2f3d151d5', 'T3000275', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 110000.0, 210000.0, 'Setoran Tunai', '2025-12-05 20:56:44', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000153';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000274') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9155fc1d-60c9-44ea-b77b-d2419cc18557', 'e119d752-c336-4f9b-b51d-2d1cc4e8b070', 'T3000274', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 2000000.0, 4000000.0, 'Setoran Tunai', '2025-12-05 20:56:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000174';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000273') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '733aacfe-1186-4d5c-9fe6-4b923717e749', 'ea5691db-8f3d-4e8e-8c53-ef6e4288371e', 'T3000273', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000100.0, 5000100.0, 10000200.0, 'Setoran Tunai', '2025-12-05 20:56:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000165';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000272') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ce03ee9e-c90f-4093-adbc-df9968fec89f', '9817e334-b7bd-4890-a254-fa1e311525f8', 'T3000272', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 7000000.0, 14000000.0, 'Setoran Deposito', '2025-12-05 20:56:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000157';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000271') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2621b35d-f4fa-40fb-a974-62d36cbd3d67', '03004605-8e76-4fde-b2a9-de7ed982149c', 'T3000271', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1700000.0, 2599999.0, 4299999.0, 'Setoran Tunai', '2025-12-05 20:56:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000166';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000270') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2e8149e4-163a-4cbf-83f7-19be28341420', 'd6fcac9f-6b57-4120-be81-280dc48196e9', 'T3000270', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 200005.0, 300005.0, 'Setoran Tunai', '2025-12-05 20:56:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000173';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000269') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5aabfd5f-fe2f-4cbd-b9ff-22ba43c0f853', '680721e5-e301-43bc-90c9-b1d0ced6c8a8', 'T3000269', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500025.0, 2500025.0, 5000050.0, 'Setoran Tunai', '2025-12-05 20:56:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000164';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000268') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b2c545d4-4cd2-4684-9faa-f05672e214cf', '231596b3-8865-44a5-9251-f19253df0e0d', 'T3000268', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2300000.0, 2300000.0, 4600000.0, 'Setoran Deposito', '2025-12-05 20:56:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000156';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000267') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '387da8e0-cb0c-4079-b4ed-58cb935c109f', 'ed4a6a95-4fef-4408-802d-40a7e58c8875', 'T3000267', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000002.0, 2000002.0, 4000004.0, 'Setoran Tunai', '2025-12-05 20:55:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000167';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000266') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e1542004-3bc4-49ee-a415-26102a51f25e', 'db95c394-bea7-47a2-ae92-958ebe7d5482', 'T3000266', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 100000.0, 200000.0, 'Setoran Tunai', '2025-12-05 20:55:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000170';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000265') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '611590ac-fe47-4548-8223-ce791aa0e574', 'd6ce1add-ed8b-479c-ae0a-5cc8b33ef883', 'T3000265', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2600000.0, 2600000.0, 5200000.0, 'Setoran Tunai Deposito', '2025-12-05 20:55:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000177';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000264') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8963d20d-5d28-48d5-b343-027538f85cc5', '3c826ae8-d789-4720-bc18-006c35d46b4b', 'T3000264', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1005000.0, 1005000.0, 2010000.0, 'Setoran Tunai', '2025-12-05 20:55:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000163';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000263') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b613f5b9-33d1-4224-94d1-0d6a206ef1b5', 'c9b69f36-dc1d-4394-8b83-1677b0265137', 'T3000263', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 500000.0, 1000000.0, 'Setoran Tunai', '2025-12-05 20:55:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000162';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000262') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '002a040d-f28a-4bf3-8c8e-e268fa8fcadd', 'a568506c-2fc3-49db-a87b-5ec6de24bb84', 'T3000262', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 9000000.0, 11000000.0, 'Setoran Tunai', '2025-12-05 20:55:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000143';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000261') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ea7d2b1a-4538-4e7a-8c16-7b9732e5b3ac', 'fa833eb0-fea1-4117-872d-b46b69bbb4cd', 'T3000261', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 3000000.0, 6000000.0, 'Setoran Tunai', '2025-12-05 20:55:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000155';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000260') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '94cf7279-2d86-46ee-a821-fc7d293f1839', 'cafb1cd8-0b22-4dce-b032-e1a66366c7ea', 'T3000260', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 250000.0, 2750000.0, 'Setoran Tunai', '2025-12-05 20:54:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000158';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000259') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b6a7ee04-e446-4a77-a3de-54a91db7ee0c', 'd3dd2df9-0059-4558-a02c-c0e76b6e85f7', 'T3000259', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2200000.0, 2200000.0, 4400000.0, 'Setoran Tunai', '2025-12-05 20:54:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000172';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000258') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3c6b9bf8-34f0-4846-b599-2985ca9b5624', '9209a677-e591-49ea-ac0e-5fddcc1b2ac7', 'T3000258', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 25000000.0, 0.0, 25000000.0, 'Initial deposit for account opening', '2025-12-05 20:54:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000182';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000257') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7c46eebd-6c27-46c1-838a-c579b65051af', '8941914a-1334-40be-abbc-d74bfbc6e3cd', 'T3000257', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2400000.0, 2400000.0, 4800000.0, 'Setoran Tunai', '2025-12-05 20:54:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000176';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000256') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd9d2ecd9-6225-4756-b44d-7ddc5e71791a', '726410ec-47a1-4601-80c4-4ee657047aa7', 'T3000256', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200005000.0, 2000050.0, 202005050.0, 'Setoran Tunai', '2025-12-05 20:54:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000154';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000255') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'dcf0533e-a44b-4b97-8877-191a7aa8cf29', 'ee3e6e33-a2fa-47e1-a459-629c9c2066b3', 'T3000255', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 25000000.0, 0.0, 25000000.0, 'Initial deposit for account opening', '2025-12-05 20:51:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000181';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000254') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '99e7a821-a6ae-45fc-b4cf-f59bbc7f5d5d', '7018dff5-7dfa-469f-9809-e74b8d8848b2', 'T3000254', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 250000.0, 0.0, 250000.0, 'Initial deposit for account opening', '2025-12-05 20:50:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000180';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000253') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b65f92ca-d8fd-4683-a5bc-d9f8bbc01f57', '1b7863f4-b48e-45fe-8353-e8439829e98d', 'T3000253', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 250056.0, 0.0, 250056.0, 'Initial deposit for account opening', '2025-12-05 20:49:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000179';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000252') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'dd49800c-9a91-4700-9cb9-d90fe5e4e5cc', '92844e78-1c6e-4bf6-b4d1-0600fde00b3a', 'T3000252', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Initial deposit for account opening', '2025-12-05 20:48:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000178';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000251') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '33886bd1-eec8-4e2b-b401-ee337c27fc56', '62b5d283-5af5-4510-acfe-46a0c8bdbb3f', 'T3000251', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2600000.0, 0.0, 2600000.0, 'Initial deposit for account opening', '2025-12-05 20:47:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000177';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000250') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '753bdfd4-522a-47ba-b0f3-650cb5ad6196', 'f9ce894a-0c8a-45e2-91d9-dd3db685041f', 'T3000250', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2400000.0, 0.0, 2400000.0, 'Initial deposit for account opening', '2025-12-05 20:47:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000176';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000249') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '15ea73a1-524c-4888-843e-632c3e4b2254', '18c77d88-c069-40d8-8a7b-97b41d74559c', 'T3000249', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000004.0, 0.0, 2000004.0, 'Initial deposit for account opening', '2025-12-05 20:47:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000175';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000248') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd54f05a2-a0c1-4585-b5d4-58cb6f664cc3', '220a65d1-1655-4c0d-a529-3336ad483a2b', 'T3000248', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-12-05 20:46:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000174';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000247') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '38501c1f-f4f8-4ab7-8b4d-0f37b5e4e185', '636abce7-2223-44bd-9ce2-03ac92f80b60', 'T3000247', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200005.0, 0.0, 200005.0, 'Initial deposit for account opening', '2025-12-05 20:46:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000173';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000246') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd2b3cd9d-66ec-4732-ad6d-a25ac4fd760a', '783077b1-ebe0-4c71-83fa-b79b612694b5', 'T3000246', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2200000.0, 0.0, 2200000.0, 'Initial deposit for account opening', '2025-12-05 20:46:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000172';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000245') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9ee1bff7-9ba7-4159-862f-21aadf796737', 'ad1f0434-3942-40a3-bc12-80106fdecfb1', 'T3000245', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5750000.0, 0.0, 5750000.0, 'Initial deposit for account opening', '2025-12-05 20:46:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000171';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000244') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4f4d012c-ac69-4e3a-b008-e4e7df083e04', '0ee1a741-7fb5-4ef8-8db1-393e2292d992', 'T3000244', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-12-05 20:46:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000170';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000243') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '65d5ef5b-32de-44d5-b5da-677813501717', '4c58ca3b-334c-497d-a93d-47e294e516e9', 'T3000243', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2987650.0, 0.0, 2987650.0, 'Initial deposit for account opening', '2025-12-05 20:46:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000169';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000242') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c3d505a9-4763-491b-b110-6d0bb1f23ab1', 'bc221d8b-2f75-4143-bc18-a0749966378d', 'T3000242', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 110000.0, 0.0, 110000.0, 'Initial deposit for account opening', '2025-12-05 20:46:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000168';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000241') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '04b7fa5e-9318-4a0d-83e8-49b9904de376', 'd78a2b67-cd8e-4801-811e-17ec83fb58cf', 'T3000241', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000002.0, 0.0, 2000002.0, 'Initial deposit for account opening', '2025-12-05 20:45:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000167';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000240') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a8a1abb7-c35c-4cdf-b79d-6de059735b16', '2d1fbfb1-745d-4b6f-ae3a-14bbc68f1bc6', 'T3000240', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2599999.0, 0.0, 2599999.0, 'Initial deposit for account opening', '2025-12-05 20:45:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000166';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000239') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e540832f-49c0-4d49-9ce6-9d952ff23585', '1405c6de-fa39-4f85-8005-a37136da2eb5', 'T3000239', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000100.0, 0.0, 5000100.0, 'Initial deposit for account opening', '2025-12-05 20:45:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000165';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000238') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '60f71a26-9806-4a00-b0ab-98205e9c151c', '7c1dbfd2-9297-43b0-b291-002dd63fa8ea', 'T3000238', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500025.0, 0.0, 2500025.0, 'Initial deposit for account opening', '2025-12-05 20:45:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000164';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000237') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '45c9f284-26bf-4f98-bbe6-08751e1b023f', 'a03db82c-f12d-4d51-ad96-3de894d14e78', 'T3000237', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1005000.0, 0.0, 1005000.0, 'Initial deposit for account opening', '2025-12-05 20:45:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000163';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000236') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '79948747-a29f-4f6d-9e6c-1da7cceb8272', 'aeb73032-807f-4be0-8864-fc149bf63b83', 'T3000236', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Initial deposit for account opening', '2025-12-05 20:45:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000162';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000235') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fd78f9a8-21dd-426e-8cf3-05bd8097b530', '06217a8b-0d3e-4626-a25c-8ac5b27ca03d', 'T3000235', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 250005.0, 0.0, 250005.0, 'Initial deposit for account opening', '2025-12-05 20:44:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000161';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000234') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6a242910-5c1c-4eb7-87df-7773fb08fb81', '2e94b305-cf01-43a2-aa5e-bfeafe9a030d', 'T3000234', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-12-05 20:44:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000160';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000233') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd1dac214-118b-4aa7-8477-859c223a7792', '68ed9430-b7ae-4158-818f-bcebdf737a9b', 'T3000233', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-12-05 20:44:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000159';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000232') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '115f6190-121e-4680-9037-bffcec8dfded', '3a6144cf-4173-4db8-a830-06654f95c899', 'T3000232', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 250000.0, 0.0, 250000.0, 'Initial deposit for account opening', '2025-12-05 20:44:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000158';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000231') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7ea6b137-e2fd-4aa0-8013-69d132311a19', '3649fb61-337b-4478-90c1-7eee8ee0b6c1', 'T3000231', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 0.0, 7000000.0, 'Initial deposit for account opening', '2025-12-05 20:44:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000157';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000230') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7976477f-304d-4826-9a0e-5e0727d92204', 'bc392e3b-a24e-41a0-96cd-7c1ebaee63d0', 'T3000230', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2300000.0, 0.0, 2300000.0, 'Initial deposit for account opening', '2025-12-05 20:43:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000156';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000229') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8c4b60b3-d1f7-4ae4-8a7a-ede61c925b10', '2035fbdd-47b4-4231-9112-03685a3fd74d', 'T3000229', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Initial deposit for account opening', '2025-12-05 20:43:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000155';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000228') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '05103d25-2351-48ee-a330-a57b5bfe7826', '868c5d4c-f0b8-4070-a918-0886c16059d1', 'T3000228', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000050.0, 0.0, 2000050.0, 'Initial deposit for account opening', '2025-12-05 20:42:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000154';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000227') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e8d08a09-7762-4821-92ce-a57c33f1a663', '88a00740-d287-48fb-823e-e8c8cadf8f63', 'T3000227', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 110000.0, 0.0, 110000.0, 'Initial deposit for account opening', '2025-12-05 20:41:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000153';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000226') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c60a4bae-1b59-48ae-8182-5860f922d154', 'd8670cb5-bb32-4c68-8e34-45ab5ec7e73b', 'T3000226', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2150000.0, 0.0, 2150000.0, 'Initial deposit for account opening', '2025-12-05 20:41:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000152';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000225') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7b3f3ec5-12ec-43eb-8aff-07293184b61e', '46c39c39-606d-4bc5-ba61-75d15f0abb50', 'T3000225', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-12-05 20:41:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000151';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000224') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3446950e-a3b7-4aba-9ead-32e9fa465606', '9aa9dd3d-7e8f-4cb8-9754-617102ab7644', 'T3000224', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-12-05 15:13:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000150';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000223') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1eb2e6d9-44f1-48aa-b46a-c01ee64f6e56', '37875b1e-7cdb-486d-9baa-0773de374159', 'T3000223', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-12-02 13:46:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000149';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000222') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '61c7855f-39c6-42ff-90ca-ab2ff7263b9e', 'aa6a4741-c6bf-4e25-b766-9a2283a074d5', 'T3000222', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 0.0, 2500000.0, 'Initial deposit for account opening', '2025-11-29 21:38:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000148';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000221') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '876e974c-bdd8-41cb-94aa-79eded48016a', '64ba0ed7-c63e-4180-ad74-55ec847c6760', 'T3000221', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-29 11:07:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000147';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000220') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f0f6d10f-430f-48d9-b7d8-877b53c6aed9', 'cc5154f8-65b4-4cbc-84cf-019c8e4423ce', 'T3000220', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-11-29 10:33:49', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000146';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000219') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fd43e00a-2350-4d7e-99e1-6c09dff2a333', 'f52dab80-71db-4ade-8493-b012e6605c87', 'T3000219', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Initial deposit for account opening', '2025-11-29 08:59:17', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000145';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000218') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0add7c91-c840-4143-910e-95683814bd94', 'bb1469a2-5d6a-4d35-82f2-24c5e9eea767', 'T3000218', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2800000.0, 0.0, 0.0, '', '2025-11-28 21:35:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000142';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000217') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '889398a8-5940-47f8-b600-e8b05ec6d613', '85377b41-d09d-4194-8aa8-b768be9e90f1', 'T3000217', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1700000.0, 0.0, 0.0, '', '2025-11-28 21:33:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000141';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000216') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0d4d8fd0-6b99-44f2-8f9c-55b8de454129', '22957fa8-9e53-407f-9748-216eb4a0d07f', 'T3000216', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1500000.0, 1700000.0, 'Setoran Tunai', '2025-11-28 21:33:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000141';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000215') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '173a8a72-b352-40ee-acc3-71baff18ecec', 'c6ea40be-b83e-416a-aba2-5695fb04a92e', 'T3000215', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 50000.0, 2050000.0, 'Setoran Tunai', '2025-11-28 21:21:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000028';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000214') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7edd5a18-a4fc-4369-b956-2da4549806fc', '44c08738-bcff-441d-a205-8fe08cc38b4a', 'T3000214', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 8000000.0, 9000000.0, 'cicilan', '2025-11-28 21:17:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000143';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000213') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '480b31a2-a8cd-47ad-96ae-3b4b72188c9b', '0f1bdd63-f3a6-43d2-a825-6fe8ed3f0fc3', 'T3000213', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 14000000.0, 14500000.0, 'Uang Konsumsi', '2025-11-28 21:16:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000144';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000212') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9f4f456d-5e25-403b-b686-45a65ce8dd03', '49633f1b-a78f-4634-b8e3-1515557b9fb8', 'T3000212', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 10000000.0, 15000000.0, 'Setoran Tunai', '2025-11-28 21:16:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000144';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000211') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd838c0a5-049b-4108-a028-df4b10c38850', 'd4853438-ca9c-4623-89cf-e4ac5b3dfa3b', 'T3000211', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 5000000.0, 10000000.0, 'Setoran Tunai', '2025-11-28 21:14:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000143';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000210') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f164c6c4-6df5-4780-a46e-2014e8cadd78', '293d2f2b-d8c1-4466-b78c-35b5a5a898f0', 'T3000210', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-28 21:11:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000144';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000209') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5deba334-2763-42cf-accb-513becd4c2a0', '2accb099-4c5a-4d2e-8fd1-9beddc228ce8', 'T3000209', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-28 21:08:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000143';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000208') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2e782508-566e-4fb0-aa12-9de91273093b', 'dd1b334d-452d-46b3-a5ad-43ba1294e897', 'T3000208', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 800000.0, 2000000.0, 2800000.0, 'tarik tunai', '2025-11-28 21:07:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000142';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000207') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f39bb757-f7ef-4b63-8caa-614aa8ecbbd2', '61883fd2-43f8-4ff5-a9da-cd0614675976', 'T3000207', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 1600000.0, 3600000.0, 'Setoran Tunai', '2025-11-28 21:06:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000142';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000206') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '893f365c-6670-4be6-b01f-d92202f64a1a', 'bc82a586-8acb-4fde-9527-af6496365b9c', 'T3000206', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 1600000.0, 1800000.0, 'Penarikan Tunai', '2025-11-28 21:03:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000035';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000205') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8d13b47e-309a-4a75-8be8-a9395ccc7143', '7598cb38-9945-46f9-8d9a-4bf28140f04e', 'T3000205', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Penarikan Tunai', '2025-11-28 21:02:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000020';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000204') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9a7a72df-5d5b-4a87-8162-fc6e04d47ca4', '6f95b6da-87de-445d-bb64-8581fe26a310', 'T3000204', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 50000.0, 100000.0, 'penarikan tunai', '2025-11-28 21:02:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000045';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000203') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b49dede2-f1b3-4345-9753-6f4b4ba1a3e7', '6b152cd4-0a77-4483-93f1-baf3a28a0eb3', 'T3000203', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1600000.0, 0.0, 1600000.0, 'Initial deposit for account opening', '2025-11-28 21:02:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000142';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000202') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '22730ec4-21c8-4498-a7aa-1bd574b5de92', 'af678a97-77dc-4cad-bd30-d6ebcf1cd258', 'T3000202', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1500000.0, 0.0, 1500000.0, 'Initial deposit for account opening', '2025-11-28 20:59:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000141';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000201') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '507c4110-9e52-4d80-bab2-184705dcb20c', '25dc3711-a8a7-46a0-b7f6-191065609e46', 'T3000201', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 150000.0, 50000.0, 200000.0, 'Setoran Tunai', '2025-11-28 20:59:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000020';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000200') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '07d82d34-9616-4e37-8532-3b6d4ea7afa6', 'bd542ad0-3214-4c47-b5fd-19a120262046', 'T3000200', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000.0, 230000.0, 240000.0, 'Tarik tunai', '2025-11-28 20:58:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000038';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000199') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '81298d44-f625-48d9-8795-75e41030b2b0', 'eff17d7e-5cee-4899-814a-4844bab74ac8', 'T3000199', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 700000.0, 100000.0, 800000.0, 'penarikan tunai', '2025-11-28 20:57:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000039';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000198') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b2eb8eb5-25a4-4285-854f-39648ac1927d', 'd081a2a6-22b7-4c3e-9c3b-40f8f6dfa98e', 'T3000198', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 200000.0, 50000.0, 250000.0, 'Setoran Tunai', '2025-11-28 20:56:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000038';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000197') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '460963ac-6e4f-4d81-b7e9-00a8f6ae8a10', 'a30fd8c9-1395-47f6-ae2e-798f079558af', 'T3000197', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1500000.0, 0.0, 1500000.0, 'Initial deposit for account opening', '2025-11-28 20:53:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000140';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000196') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f839f253-3b6e-4b32-a630-3c9d4ff30d4b', '40f1a178-e114-4313-817a-63a0498be3cb', 'T3000196', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 11500000.0, 12000000.0, 'Tarik Tunai', '2025-11-28 20:51:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000036';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000195') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '04f199b0-0e79-49e8-8fe0-2de0e25d45bb', 'a90da30d-3c47-4b54-99dd-b56270f0d3d0', 'T3000195', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-28 20:50:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000139';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000194') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '709d326c-8bec-4a82-aaa0-5799d16f5bf3', 'aef07bde-36cb-484d-8802-59630084c630', 'T3000194', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000.0, 40000.0, 50000.0, 'Tarik tunai', '2025-11-28 20:49:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000010';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000193') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '97b85422-484f-4856-9aa2-b044c045153a', 'fca12d99-96ce-4fd9-af13-d2c4e482a04b', 'T3000193', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Jajan dan Konser', '2025-11-28 20:48:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000041';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000192') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'abc1738a-6057-41a1-9586-d268f4bd9409', '91d1d849-70c5-41de-91f1-a7f92778eaef', 'T3000192', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 50000.0, 'Penarikan Tunai', '2025-11-28 20:47:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000064';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000191') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6a3d3fa9-2281-4cdf-9372-1040b9d52a37', '7d627000-ef4b-406f-823c-4cc769db00d3', 'T3000191', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 10000000.0, 12500000.0, 'Setoran Tunai', '2025-11-28 20:47:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000036';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000190') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bd8131bd-05e3-45cc-92b2-a7a38f3d467a', '8c15cce9-2139-451f-bf1e-201f67602c64', 'T3000190', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 350000.0, 150000.0, 500000.0, 'Penarikan Tunai', '2025-11-28 20:46:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000033';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000189') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '76b4e7d3-e207-4d47-ad24-3f301834c668', '237af806-271a-4994-bb8c-b9ed528c08db', 'T3000189', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1000000.0, 1500000.0, 'Setoran Tunai', '2025-11-28 20:45:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000039';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000188') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '28fd82b8-92b3-4d60-8ce7-3a999074507b', '29513825-b897-49c3-b5ff-dd48fa398550', 'T3000188', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1000000.0, 1500000.0, 'tarik tunai', '2025-11-28 20:45:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000030';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000187') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '97686f2a-a708-47ac-b2f8-92329fd5053c', '6dc3ee57-ab8f-4a42-bc0b-2abf8592ded9', 'T3000187', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 200000.0, 300000.0, 'Untuk Belanja bulanan', '2025-11-28 20:44:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000031';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000186') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '52300b73-96f7-49e3-b62e-1a6b98fcbf5b', '14d3d227-c12b-44d9-8c61-2fed8a49a0d9', 'T3000186', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 40000.0, 20000.0, 60000.0, 'Penarikan', '2025-11-28 20:43:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000025';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000185') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c36aee23-4505-4aba-a563-258eb47137f8', '9dc09244-a34e-4a9f-8ed2-3b424a81d01c', 'T3000185', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 50000.0, 550000.0, 'Setoran Tunai', '2025-11-28 20:43:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000064';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000184') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b47910de-8c6b-4d99-827d-74da7d3d6d99', '6a0f17ad-3498-4949-8650-8a800561e755', 'T3000184', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 800000.0, 900000.0, 'penarikan tunai', '2025-11-28 20:43:22', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000060';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000183') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8090fc50-d76b-4d18-a974-6d461564b833', '3c942c0f-c793-467e-8293-3af617db98ab', 'T3000183', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 2550000.0, 3550000.0, 'Penarikan untuk pembelian', '2025-11-28 20:43:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000018';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000182') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ad44da97-6812-4355-8125-30631da33907', 'f84aa558-bb23-4fd5-a26f-042bbb95a5a1', 'T3000182', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 300000.0, 900000.0, 1200000.0, 'penarikan', '2025-11-28 20:43:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000021';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000181') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd9c0b936-3eeb-40e4-a92b-575a954a58f8', 'e0ed1426-97ea-45de-aa01-efb7c0b32030', 'T3000181', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 50000.0, 100000.0, 'Penarikan Tunai', '2025-11-28 20:42:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000017';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000180') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c9a39d0d-c5ce-4dff-ad30-9fafc166b5f6', 'ed64f389-4c05-4402-b042-981c87f5074a', 'T3000180', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 400000.0, 2250000.0, 2650000.0, 'penarikan tunai', '2025-11-28 20:42:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000086';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000179') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a571ede8-9e5b-4c84-9481-710d0d4eb0c4', 'dfd4ff0e-e91d-48f7-ab66-58040d5d1af3', 'T3000179', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 1050000.0, 3050000.0, 'kebutuhun tak terduga', '2025-11-28 20:42:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000013';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000178') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e62730de-0e83-4035-bff8-dad1a479a3a1', 'dd1a82d3-2299-42ac-99d3-541cbba1babf', 'T3000178', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1500000.0, 0.0, 550000.0, 'tarik tunai', '2025-11-28 20:42:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000026';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000177') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3d0f4216-e999-4500-a9e0-275ddb7fb084', '33a606c4-0b99-4b26-96ff-c5d5f9182805', 'T3000177', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 50000.0, 150000.0, '', '2025-11-28 20:41:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000029';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000176') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'abf06da9-f0da-458d-a704-6f05650f3f0c', '0094017f-cc3d-426c-94c6-22094fd3beaf', 'T3000176', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 50000.0, 100000.0, 'Penarikan Tunai', '2025-11-28 20:41:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000043';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000175') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eb957fcd-afcb-46e4-b2c7-2db7228cf1d7', '7ddd9a75-9666-4580-8e1c-a1a67f0e51cc', 'T3000175', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 860000.0, 960000.0, 'Penarikan Tunai', '2025-11-28 20:41:29', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000046';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000174') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '22780860-ae99-4da4-ae5b-b17b054a96fe', '3135b610-0bf7-4f17-8589-c039c0b80ead', 'T3000174', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 800000.0, 50000.0, 850000.0, 'Setoran Tunai', '2025-11-28 20:40:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000033';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000173') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8ab07432-7ff1-4eef-a28f-0c7592c17b17', '35287e1a-6fda-4237-a8c8-19dc3835ac5a', 'T3000173', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5700000.0, 300000.0, 6000000.0, 'Setoran Tunai', '2025-11-28 20:40:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000041';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000172') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2370a396-d912-4920-8f55-734422ac2e11', 'd23bc4c9-5c32-488c-9434-c9b2579189ce', 'T3000172', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000.0, 50000.0, 60000.0, 'Setoran Tunai', '2025-11-28 20:40:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000010';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000171') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bbe24d19-4a9b-4405-869f-e6c1f64544ab', '4f39c60f-9181-480b-ac8b-7932da43fb34', 'T3000171', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 50000.0, 3050000.0, 'Setoran Tunai', '2025-11-28 20:40:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000086';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000170') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '92551b2b-300c-409f-9a38-2eadd9349a14', '842af8ed-7cca-4984-9a89-bfdd063ce035', 'T3000170', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 50000.0, 100000.0, 'Setoran Tunai', '2025-11-28 20:39:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000025';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000169') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cb17d1b5-a5e2-4c27-b54b-8023278a1af7', 'b277784b-c16d-4725-88d4-2d03d7022aa8', 'T3000169', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 10100000.0, 11100000.0, 'Tarik tunai', '2025-11-28 20:39:29', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000011';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000168') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cd2a15af-c7c9-483a-a491-81e27d4251f1', 'd194284c-03cc-457a-bdb0-3ccdb5fd2e3b', 'T3000168', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 1000000.0, 2000000.0, '', '2025-11-28 20:39:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000035';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000167') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a322c66d-241d-4d92-9cd3-5cb5ed091813', '33a7d1e7-63d4-42c2-9648-1f0d4093a08e', 'T3000167', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 60000.0, 1060000.0, 'Setoran Tunai', '2025-11-28 20:39:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000046';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000166') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8fb66f22-3e20-4915-ba51-4a2fc75ea1d3', '9d662aae-26bd-42bd-9292-0cc62988e1f3', 'T3000166', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 100000.0, 150000.0, 'Setoran Tunai', '2025-11-28 20:39:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000045';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000165') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0633b2a2-eb94-484d-9e5f-db75c9dff68d', '8bcb0bd2-368d-44db-90e2-c04ff51245f5', 'T3000165', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 50000.0, 2050000.0, 'Setoran Tunai', '2025-11-28 20:38:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000026';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000164') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '59fa0e71-0296-475a-81a4-4743aeddfb6e', '76711b86-e50d-4a90-b6e0-6779d95d3df0', 'T3000164', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 50000.0, 150000.0, 'Setoran Tunai', '2025-11-28 20:38:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000017';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000163') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd8afc420-cb8e-43da-8bd8-6446dd6c27a6', 'e731e0c2-644a-4be7-9f69-004e5d575c64', 'T3000163', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 150000.0, 100000.0, 250000.0, 'Setoran Tunai', '2025-11-28 20:38:45', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000029';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000162') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '67e2e8df-635c-4b16-98a5-38d68f7d9025', '202ed86c-9b18-485c-9b61-2f0ada0ace0a', 'T3000162', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 4500000.0, 50000.0, 4550000.0, 'Setoran Tunai', '2025-11-28 20:38:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000018';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000161') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3555def3-6293-475f-864a-019974eb2181', 'a679aa46-fec3-470c-a937-0260e8417665', 'T3000161', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000.0, 50000.0, 60000.0, 'Setoran Tunai', '2025-11-28 20:38:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000046';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000160') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4ab1fb0e-0014-4425-88dd-78051a65fa05', '981803df-9835-436b-a398-74a7cc6ec54c', 'T3000160', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 50000.0, 150000.0, 'Setoran Tunai', '2025-11-28 20:38:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000043';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000159') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cbbce23d-aaed-425d-8c41-2e16e0612e19', 'd703c483-47ed-48d8-a336-58881a58f4e0', 'T3000159', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 1000000.0, 2000000.0, 'Setoran Tunai', '2025-11-28 20:38:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000030';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000158') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fbaafbb5-c01c-46b6-8e15-54bebbf5529a', '40eb8f42-e1f5-4fc7-aaa7-608b2e9a5faf', 'T3000158', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 50000.0, 5050000.0, 'Setoran Tunai', '2025-11-28 20:38:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000013';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000157') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4060e966-546a-4acd-b638-bb1f1ef55087', '5d6f3861-f4c2-4b6f-b68e-2f6d4025efa8', 'T3000157', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 1000000.0, 3000000.0, 'Setoran Tunai', '2025-11-28 20:37:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000035';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000156') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'be55d8a0-7402-432b-baf3-e8c65359f3b8', '34057277-f16d-42ae-b452-5f21ca430888', 'T3000156', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 350000.0, 50000.0, 400000.0, 'Setoran Tunai untuk menabung', '2025-11-28 20:37:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000031';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000155') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1e733d28-e263-45ba-b477-1cad34f374bc', 'd06f8f88-4f94-42e9-8a08-d09ecae5bf44', 'T3000155', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 6950000.0, 7000000.0, 'Tarik Tunai', '2025-11-28 20:37:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000023';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000154') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8fd121d4-0277-4354-b563-02dd16f168d9', '1f30b2d7-7693-4d8c-9935-77501f1ef102', 'T3000154', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 300000.0, 350000.0, 'Butuh uang buat beli obat nyamuk', '2025-11-28 20:37:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000034';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000153') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ed8322bd-7591-4a83-8c0c-2ce678a9f5fc', '55bd8005-0c5a-4635-9103-f5c937c89030', 'T3000153', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 6900000.0, 7000000.0, 'tarik tunai', '2025-11-28 20:37:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000014';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000152') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '697890ca-e530-4d9c-8d5b-d98f29ac3fcd', '2a3e1c0f-d349-4936-8d8e-ce4b5d5015eb', 'T3000152', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1000000.0, 1500000.0, 'Setoran Tunai', '2025-11-28 20:37:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000021';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000151') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd175bf44-6525-45a7-a4b9-23ff4714a5ef', 'da32cc12-a6b7-4ac8-9ee7-447db0ff499d', 'T3000151', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2100000.0, 10000000.0, 12100000.0, 'Setoran Tunai', '2025-11-28 20:36:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000011';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000150') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6a2ce1a0-e6d7-4bd0-8bd6-2b4c4fac432c', '16c76630-37fe-40c9-b8c9-6e3dfe6220fc', 'T3000150', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 3000000.0, 4000000.0, 'penarikan', '2025-11-28 20:36:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000042';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000149') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd948538b-5e35-4df1-bc16-0ce6b645cc74', '80d9c2d8-3989-4eee-8c39-9739be9eb95b', 'T3000149', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7050000.0, 50000.0, 7100000.0, 'Setoran Tunai', '2025-11-28 20:36:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000014';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000148') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '75bfafe2-829a-457d-838b-9d14cffc1021', 'e48d7b42-4846-4a44-97e4-4bc2a5dee7d9', 'T3000148', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 4050000.0, 4550000.0, 'Penarikan', '2025-11-28 20:36:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000012';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000147') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd348a20e-e5db-4b2d-b0ff-fe99849c9447', '32080b7b-80fe-46ff-b31d-8cb937a789ed', 'T3000147', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 250000.0, 150000.0, 400000.0, 'Setoran Tunai', '2025-11-28 20:36:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000034';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000146') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b872f8a2-4b74-44c5-9169-e789cd69ee4e', '2db0f063-207c-4a28-a022-17751ad6d0c6', 'T3000146', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 50000.0, 'Penarikan tunai', '2025-11-28 20:36:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000022';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000145') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b3df07eb-3c11-4661-9628-9052d15d7243', '73b2f075-3406-4ebb-93e9-5aa663db30f8', 'T3000145', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 2050000.0, 'penarikan tunai', '2025-11-28 20:35:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000019';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000144') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '00fc2475-c10a-47fc-b814-47032d703e21', '37d39b06-9f2a-4315-b008-7d582ed1a6db', 'T3000144', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 50000.0, 5050000.0, 'Setoran Tunai', '2025-11-28 20:35:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000012';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000143') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '05b84be5-a937-4be8-9cc4-4c222bdf0d8a', '8abf7b4d-2fa2-4230-bb3c-4e73f577ecd0', 'T3000143', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 50000.0, 150000.0, 'Setoran Tunai', '2025-11-28 20:35:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000022';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000142') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a8c5bd18-f80c-46fc-8ff5-348b92c4b6bb', 'c04e4fdb-6711-4248-b626-fa3968fa2e6b', 'T3000142', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 50000.0, 7050000.0, 'Setoran Tunai', '2025-11-28 20:35:01', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000019';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000141') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fadd6ee7-612d-46f8-9660-10cebde5b28a', '569bda3e-acb3-48d9-a3ba-d703757a51f6', 'T3000141', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 7000000.0, 50000.0, 7050000.0, 'Setoran Tunai', '2025-11-28 20:34:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000023';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000140') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'aa823752-832c-4c6e-bc92-f13c21e3a4f3', '7cb3caa2-15d0-45e7-9b82-eb5c243c6e66', 'T3000140', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 3000000.0, 5000000.0, 'Setoran Tunai', '2025-11-28 20:34:28', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000042';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000139') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '16d26efc-f1c0-444f-9cc3-974f26cf31c6', '2a9ef473-ca17-4399-b330-f89228d02635', 'T3000139', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, '', '2025-11-28 20:33:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000024';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000138') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fd3c3637-7018-4b41-8b94-8def98409759', 'e612251b-7550-4908-8981-eb8ef3055e1c', 'T3000138', 'WITHDRAWAL', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 3000000.0, 3500000.0, 'Penarikan', '2025-11-28 20:32:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '201', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000009';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000137') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7ab0b604-38cf-46d3-91fd-e59591883043', '52c3f67f-d099-473e-ae51-9c2bfe392556', 'T3000137', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 150000.0, 200000.0, 'Setoran Tunai', '2025-11-28 20:31:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000024';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000136') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'efb4e3f5-4dd7-43a2-8415-9e06ac55a7d8', 'b75abf6d-7ccf-4d30-9fac-009734775f98', 'T3000136', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 100000.0, 150000.0, 'Setoran Tunai', '2025-11-28 20:31:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000024';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000135') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f6cf5470-8323-43da-b7e7-58f1e4b429b3', '3810c8c3-f653-43bd-9350-e9349bc5d28a', 'T3000135', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2500000.0, 1500000.0, 4000000.0, 'Setoran Tunai', '2025-11-28 20:29:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000009';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000134') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '635105ec-2074-4281-9325-23aab2294c11', '65a3f8f9-7952-4df4-b91b-d1737c574190', 'T3000134', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 1000000.0, 1500000.0, 'Setoran Tunai', '2025-11-21 17:47:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000009';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000133') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6f600b3d-a37d-4bcf-b4d7-0b39bde37497', '2f5ffed6-7584-40a6-9a37-f7c4355b6382', 'T3000133', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-19 18:07:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000138';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000132') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '71d86470-2cb8-4be5-a362-60d6464b920d', '9e7b7a0e-0ea9-4a2a-a9e0-1aba2d68f32e', 'T3000132', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 50000.0, 150000.0, 'Setoran Tunai', '2025-11-17 15:34:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000034';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000131') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1200fb6d-5d6e-4b83-9e6c-354dc082e94b', '4b63fff5-7c4b-40ab-b32e-1c8e2a86831f', 'T3000131', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 14:08:45', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000137';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000130') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '547b3c8f-0486-49f0-a9f9-cc0dcd72e212', '276887d4-e6fa-45be-9c75-22e09ffc4212', 'T3000130', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:58:04', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000136';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000129') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4b1a6a9c-6763-476a-9db0-7232f9f01d54', '0864fd72-4f20-45bb-b9d5-a161e8cfe1b3', 'T3000129', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:58:01', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000135';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000128') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1ed9fab1-aad5-4973-8d31-f1a69cb691fe', '74f64c22-e41a-4867-9f78-2c175f23008f', 'T3000128', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:57:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000134';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000127') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '83f42931-4fca-45bf-a08d-c4cc583c9c6a', 'c21af2ca-7b06-4ebc-8e95-2738945d5050', 'T3000127', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:57:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000133';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000126') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'aab2b6e3-10e4-451b-b5f7-3135bc1fa61d', '7667f721-d87f-4c7e-aaec-04ea3b72aab8', 'T3000126', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:57:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000132';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000125') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f811efae-5f6c-4d95-b9b5-d7d9f4f2ce12', '6e012a43-51ca-4758-a8f3-ec6827dce187', 'T3000125', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000000.0, 0.0, 100000000.0, 'Initial deposit for account opening', '2025-11-15 13:57:37', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000131';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000124') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b4652d17-5902-40a4-ae14-1958a1255747', '436cfa31-3e40-4611-a30d-f0f2350bc314', 'T3000124', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:56:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000130';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000123') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9573c5e5-1ffb-455a-a30b-445f6adafe5f', '12d3a0d8-1ed4-4d2c-89fc-375e89f76ac4', 'T3000123', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 13:55:52', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000129';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000122') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'af2673a6-8b09-49b5-86ce-225f8e6ef4c8', '3416cedb-90f2-4a57-b052-8b53584c507a', 'T3000122', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-15 13:55:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000128';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000121') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5276d628-49c5-45c4-8974-1ba416c998a5', 'a4e5193f-ae53-4af7-b769-e00f5f5e8525', 'T3000121', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 13:55:25', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000127';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000120') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2dabd0ab-3650-4857-a7e5-081fde23c282', '1198b51c-5c4f-46cd-8200-1c895a5e2902', 'T3000120', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 13:55:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000126';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000119') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '27864a6a-6264-4a15-9766-487634b184c9', '35bbf3ab-cb24-4119-90b4-021b788ec44d', 'T3000119', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 13:55:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000125';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000118') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'be0f38b1-c77d-4da6-80e9-febc517bfe90', '29eba442-14f5-44b7-bda2-406f207fb6d7', 'T3000118', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 13:54:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000124';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000117') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '56d69157-dd41-4eee-aec7-ea1358eb7655', '5bb4f6c1-e34e-438c-91d0-bcdf18e1bd6b', 'T3000117', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 13:54:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000123';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000116') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '39b05a10-539d-429d-bd1f-992084615872', '5e4aaf08-8716-4896-a06a-3736682bdb89', 'T3000116', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 10:47:28', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000122';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000115') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '151c778c-b8cb-403f-913b-64f0da406b65', '73dbe0b6-aa02-4585-98f3-37f9ef90a7ac', 'T3000115', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000000.0, 0.0, 100000000.0, 'Initial deposit for account opening', '2025-11-15 10:47:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000121';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000114') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '742318ca-ae63-4cf8-8100-73785611a6fa', 'bc8cc2fa-12a6-4bec-ad79-96674f4e35ed', 'T3000114', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 10:47:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000120';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000113') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a6fc8c06-b166-4fc5-be6b-17c6e3b6ac72', 'ec24709f-8052-4f96-b8d9-e2e516874568', 'T3000113', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 30000000.0, 0.0, 30000000.0, 'Initial deposit for account opening', '2025-11-15 10:44:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000119';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000112') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '30266ab4-996e-4eb9-9484-18ad304f8a87', '6de95dd1-da39-4be1-83fc-241d696f8e91', 'T3000112', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000000.0, 0.0, 5000000000.0, 'Initial deposit for account opening', '2025-11-15 10:42:01', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000118';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000111') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fd6c4bd5-41f4-4bfe-b797-374d274e5dca', '4b98a05a-88d7-4ffe-a1af-b7f4f90557f5', 'T3000111', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-15 10:41:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000117';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000110') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '435fa494-f809-4a1b-96fc-d18d9493e84f', 'a1e8d850-3443-481b-909e-0dcd47caf060', 'T3000110', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-15 10:41:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000116';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000109') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '62e8d9ff-88d1-4151-b550-8d5a59cfec61', '2198bf44-e039-473a-916e-a5fba34e4800', 'T3000109', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 10:41:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000115';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000108') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b4e8a502-6573-465d-acbe-8f3881521325', '13247f90-02f3-4fdb-9cc6-3d9da4dcfd22', 'T3000108', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 25000000.0, 0.0, 25000000.0, 'Initial deposit for account opening', '2025-11-15 10:41:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000114';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000107') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6fb0b60c-48fd-4dde-b3cf-dc943accd422', '26499d14-0d35-472c-83ac-c44403e660e3', 'T3000107', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 10:40:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000113';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000106') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd697b26c-0d3f-48e7-9779-a284cb59d1e8', 'd9b43908-2a6c-43c0-bf1c-57bfc9bfa7fe', 'T3000106', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 10:39:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000112';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000105') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eea389ed-9405-4b99-ac15-5de012a91c90', 'f5567889-024a-45d3-aa0a-b48fbe368ccf', 'T3000105', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 10:39:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000111';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000104') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1efbd5df-ff52-48a9-8b5d-2a7e8530849e', '24b22858-d6ee-4963-9e7a-ceb9272fbcab', 'T3000104', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 08:57:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000110';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000103') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '23fc8b2e-ea7f-4a9b-8a33-410833c2ecca', '99941abc-1b46-4d42-a957-4716c156a042', 'T3000103', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:56:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000109';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000102') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f560858d-0dfe-478d-b158-c6fdf49591a2', '67766741-c0d4-4372-92eb-c7f6602e6281', 'T3000102', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 08:55:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000108';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000101') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c5a80836-b670-4e1a-865b-06c1963bd64b', 'cee96b8c-5f51-42f1-8d2b-39aef4a43a13', 'T3000101', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:55:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000107';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000100') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3035ce26-264e-4e4a-8a26-59909706c595', 'c85f31fc-6b93-4176-a904-7f7b2938961a', 'T3000100', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:55:38', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000106';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000099') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '380a7e5c-f2da-4fbd-b40e-6624c9e4eb54', 'ffc839f9-7b19-4268-8075-92dc91c8cf3f', 'T3000099', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50500.0, 0.0, 50500.0, 'Initial deposit for account opening', '2025-11-15 08:55:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000105';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000098') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e3923ee6-baa6-494e-a800-d93bc2a3938b', 'dc8a9ff4-6377-4f1f-8026-7486d1e5f52a', 'T3000098', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:55:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000104';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000097') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '14df04e1-4191-4b01-aa9c-1c044aeee1c9', 'bc55ccac-4186-4785-a188-5be73e78202b', 'T3000097', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:55:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000103';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000096') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '3e7b87a2-a54a-4d67-9b7f-a56f74d8ed6e', '05c1642d-44e8-4e31-8a00-07f69ec03d92', 'T3000096', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-15 08:55:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000102';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000095') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1abdbbc3-4bcf-46d9-801b-9a12be9996c4', '071ce163-f7b5-4bf8-b6c0-5a6aa4b7e1fa', 'T3000095', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:55:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000101';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000094') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eaafb690-9c15-4a81-a9ab-50bbfff71a78', 'a87e0b89-9bde-46e5-81a9-572fedcb0317', 'T3000094', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-15 08:55:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000100';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000093') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '48f60d3f-2eba-4ede-a73a-52c94be75fdf', '0378965d-619f-499f-8464-2be86bb84e2c', 'T3000093', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:54:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000099';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000092') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd64c2d03-8793-4567-a828-2afc5ff392d4', 'f0781ed9-77f4-48e8-9872-00302a6a87a5', 'T3000092', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:54:55', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000098';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000091') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a4ea3b56-d6c3-497e-9757-fe58c752cd27', '3a3534e3-2348-4df7-9838-c51e38c5082a', 'T3000091', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:54:41', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000097';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000090') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '399885f7-321d-4393-98ec-594823fece82', 'dfeadd1d-e844-42ca-96e0-84902b6d48a2', 'T3000090', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 08:54:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000096';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000089') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f1dea24f-8818-4828-88e9-7bf1c769a4bb', 'cee3405c-438b-4d11-8e16-426c79adc777', 'T3000089', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:54:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000095';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000088') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '66a06e90-9ccc-404a-b320-316a21b98662', 'a57be289-08da-4b9d-b0e3-488568d220a4', 'T3000088', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:54:29', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000094';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000087') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '25db8230-3aeb-44b9-a70c-21e233258af0', '45d4f3c2-3c08-4867-84b3-39346ea70ccf', 'T3000087', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Initial deposit for account opening', '2025-11-15 08:54:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000093';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000086') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'caf2f741-9e5e-4eac-9600-dac10c3b96af', '5b9272ec-2ed5-4f4b-adfd-e5a1fef457d5', 'T3000086', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 08:54:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000092';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000085') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'fdc278df-37b0-4062-ac38-ba9bc9ba8d7e', '7d36dec4-6b01-4ad6-bf7f-4376fc47fcc8', 'T3000085', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 08:54:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000091';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000084') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7b601b6f-b6d0-4c09-afa6-1f7322ea7714', 'feffa64b-5af9-4b7e-8a27-cfef0e792f6a', 'T3000084', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000000.0, 0.0, 50000000000.0, 'Initial deposit for account opening', '2025-11-15 08:54:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000090';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000083') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '424d9815-ebc6-42ab-bf2d-444bec572d93', '68ae40e7-25b0-468f-9aec-afc3fc2b8800', 'T3000083', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-15 08:53:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000089';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000082') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'aa24c959-36e7-4149-bad6-6eeadf678e39', '8fd7978f-a053-4683-b4fe-b862892eae30', 'T3000082', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-11-15 08:53:40', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000088';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000081') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5988f347-79bf-4f97-bbfd-9fc2a33e8f91', 'b6d0b355-c55d-4135-b006-1c4155061be5', 'T3000081', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-15 00:47:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000087';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000080') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c268db17-1515-4dd0-be30-0267c2102914', '966ca199-9cc8-4651-88eb-50e031a1a502', 'T3000080', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-15 00:37:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000086';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000079') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8618e652-1e88-4891-96e8-8015fb86895e', '0c34d76d-fb72-4be3-a714-039fe74b5b49', 'T3000079', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:14:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000085';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000078') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bdc73f79-3c9f-45f6-a7d9-714d441d1545', 'f984c7b9-9128-4336-b57a-77e22165b22d', 'T3000078', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:13:16', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000084';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000077') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '532ad245-93fa-4f1d-a89b-3b0f30ba5a4b', 'b15668dd-0176-4898-ae8a-29a78e69d4d9', 'T3000077', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:12:05', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000083';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000076') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd35605f7-8cd0-4238-a64e-cff9bbcae357', '259cccfd-ba1e-4345-b03c-50e56540e75d', 'T3000076', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1750000.0, 0.0, 1750000.0, 'Initial deposit for account opening', '2025-11-14 21:10:58', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000082';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000075') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '04f1c257-74fd-46a8-93b1-ca331bacbbdd', 'c9a67f21-595b-461c-878d-18af0668fbf7', 'T3000075', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1500000.0, 0.0, 1500000.0, 'Initial deposit for account opening', '2025-11-14 21:10:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000081';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000074') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6eb268b5-552f-45a0-bc16-ba6d24fb1761', 'bb804566-8fe1-4f35-8bb7-2407ff5f1081', 'T3000074', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-14 21:10:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000080';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000073') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b3cfdd2e-1c32-4c96-878a-48e7f54990f0', '5fe31f86-d412-453b-96a5-34d447540bc3', 'T3000073', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:10:29', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000079';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000072') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cc9bfafe-769b-4745-8e49-438f361bb2c2', 'd1080017-6c38-46a9-a057-d3b55be92042', 'T3000072', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:10:21', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000078';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000071') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '230d7599-0efc-4402-b6a7-0289e5da4c37', 'd81fd1b5-2a3b-486e-8148-00055f1b062a', 'T3000071', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:10:01', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000077';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000070') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1dc615b0-46ff-4972-8de3-0f859526bd9b', '5ec493b0-2d48-42f9-b920-39a3716906ba', 'T3000070', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000076';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000069') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '79abf830-1897-4682-b9ce-c91cd4cab6fd', '7d52c031-7519-496f-b434-fe679f9549e9', 'T3000069', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:36', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000075';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000068') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '42e84ad1-f4dd-4fad-b490-45445908e256', 'd86e6d5d-d128-46fa-84a5-e18964ebece4', 'T3000068', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000074';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000067') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '65dc7e1b-b0f6-458a-af7a-a5fe5f97baa3', '7008289b-f2c6-4de4-b188-7ea2d9e31ff6', 'T3000067', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 2000000.0, 0.0, 2000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:18', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000073';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000066') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '5573f7ff-a48d-4970-875f-2fcdbdd64062', '2c211a12-a5da-459e-8f77-e5676f69cea7', 'T3000066', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000072';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000065') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '7dafd15c-7f24-411a-b778-8714bc536a42', '4bf1f48a-14d1-41ca-b09f-d490c798f882', 'T3000065', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:07', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000071';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000064') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '654441be-86ae-43ca-b126-ad3bf34bc809', '6b4c3263-f1b5-4eb0-9620-9c730ffa738d', 'T3000064', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:06', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000070';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000063') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '68957c9e-559d-4836-be53-e6a3da344948', 'bdd5fafd-af37-424d-81bc-dc46f7cefb97', 'T3000063', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000069';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000062') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '638ac5af-32c9-4584-9096-997befcca1fb', '69eb6e42-ccf8-43ed-86df-5d9cdffec549', 'T3000062', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:08:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000068';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000061') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e811b3be-ed27-47f2-b44a-08d7dc3f9064', '7e7193ed-679d-436e-a44d-43838da46495', 'T3000061', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000067';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000060') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8a58eaf4-e26c-4492-887b-072f27b89d4c', 'db12c77b-2f1d-4ae0-85a1-7de6b0fe0880', 'T3000060', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000066';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000059') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '64601fff-48b7-497b-8a0e-c22aece4a905', '5dcaa6ed-992e-4e74-a53d-16608c5d431e', 'T3000059', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000065';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000058') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '9bf3752e-7c3a-4089-8fac-8bb4ccc5109b', '9b97313b-5b86-4723-aa72-bded842a7894', 'T3000058', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 21:07:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000064';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000057') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c7387d1e-c4a6-4cd0-aedd-023b5a1d33bb', 'c001e3a5-2efd-4d2c-9260-9a09a39a7049', 'T3000057', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000063';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000056') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b1fda96f-e7ac-4fd1-84ff-403190b163a1', '7fdce2a2-be0e-43d0-93f6-2d5de9a16d4f', 'T3000056', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000062';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000055') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e6556837-80d6-4ff8-9e0e-78f682f379a9', '66182e86-8d08-4c08-8f45-4bb6c995b71a', 'T3000055', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000061';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000054') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0d0042dc-538f-43cf-8ffa-16fd478434a3', 'f9766aa7-1179-4d3c-97bd-5c59d9401cf7', 'T3000054', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000060';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000053') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2e2c9b1f-301c-401f-90df-d15a777e396a', 'b0f7223e-2e50-4538-9555-72580ace9f68', 'T3000053', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000059';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000052') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'dc2f88dc-2eb9-4a80-a529-e955c6872922', 'c09dd764-9f5a-4d5b-a931-53a7af656933', 'T3000052', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000058';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000051') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4d3956ca-2303-4a41-8fd1-6263d71c908f', '7af93eb3-7a73-4569-bad1-6c32e7349e87', 'T3000051', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000057';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000050') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'eb8d73dd-9574-4b56-98d2-d5ce0591aab1', '6ba1e7fe-11d2-432c-a3ad-b7a42d2f067e', 'T3000050', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:08', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000056';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000049') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '71d15c79-bb09-4a6b-b974-1a4e54c34efe', 'ded9f1c1-4129-45f6-93d8-d040a55bdeed', 'T3000049', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:06', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000055';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000048') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '085a5cec-7576-4510-a02d-e8101d67700a', 'fde9d472-af99-4b27-9e56-ee7b905da947', 'T3000048', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:07:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000054';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000047') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cad2d39d-3e0a-4a81-af77-4eaaae35bc06', 'fe2adccb-da4b-4b43-b6d0-4e344fd51e93', 'T3000047', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:06:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000053';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000046') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '745a2b53-bd16-4377-9e33-2e053578b419', '18c048e3-8c54-4593-8bd4-52b9d635b647', 'T3000046', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 5000000.0, 0.0, 5000000.0, 'Initial deposit for account opening', '2025-11-14 21:06:46', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000052';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000045') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '06a8dd83-96cb-477f-860c-441f51ba1141', 'f85d5dae-4814-4a25-b167-fd625b77ed51', 'T3000045', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1111111.0, 0.0, 1111111.0, 'Initial deposit for account opening', '2025-11-14 21:06:39', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000051';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000044') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '00a2c556-ebc5-4fe5-ace6-f187d2523095', '21f901cb-c9b0-420b-a85f-0f63b5cf5269', 'T3000044', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:06:33', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000050';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000043') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cb9996e0-8c08-4608-9eca-87f5d6f48fd9', 'ada538b9-4e70-4efd-94b9-281ab13b8c3d', 'T3000043', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:06:23', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000049';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000042') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ed472d1f-bd2d-4905-8f66-876c5e4ff3ff', 'acf3d703-37a6-49af-8e6f-329cbc61fce7', 'T3000042', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:06:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000048';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000041') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd273c46b-e671-4e6a-872a-16a14e11256e', 'bbed2047-7f13-4da0-a66a-a19d791f2700', 'T3000041', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 21:06:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000047';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000040') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e7067cf2-b968-4d95-b4ad-7a63e11b6a95', 'e1c38cf9-7029-4f2d-a174-46d57b6e0963', 'T3000040', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 21:05:02', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000046';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000039') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd8b70deb-0342-42ec-9d5b-c9915450c9c9', 'e746e995-3587-401c-92f3-bd8741ab7eab', 'T3000039', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-14 21:03:43', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000045';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000038') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2896fe5a-e44d-49d9-9580-9dc47d55a3cc', 'ec280bec-19a6-41b9-8712-67781e6e36a6', 'T3000038', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 55555.0, 0.0, 55555.0, 'Initial deposit for account opening', '2025-11-14 20:58:34', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000044';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000037') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd5fdc600-8b11-4494-b5e3-d376e192da79', 'deaa17af-8238-4c0e-aba1-20b49c3dfd12', 'T3000037', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:56:57', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000043';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000036') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '97e3d786-aa5f-4fb7-84ac-f828a26b4128', '952f03ea-6cc2-4dd7-8c1d-520656eb3d17', 'T3000036', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Initial deposit for account opening', '2025-11-14 20:56:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000042';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000035') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1ef0b4e6-c6c0-4a05-b752-6cd2800b6720', '1067a135-8d37-48a6-9d9b-6a973c19195b', 'T3000035', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 300000.0, 0.0, 300000.0, 'Initial deposit for account opening', '2025-11-14 20:52:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000041';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000034') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'c61d3530-3725-4ae8-835a-09227bbbce3f', 'c5d5c4b9-dda9-4f53-a6a7-487062fc4558', 'T3000034', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:51:59', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000040';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000033') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b3d10bd8-e492-4d6e-8a04-f1ad5791888c', '2de25088-61c1-489a-bf71-5875cfc866fb', 'T3000033', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 20:51:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000039';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000032') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'da8b1682-1243-46da-87b3-e976a82bd6be', 'da26e742-04cd-44a4-8ea0-3a852286e446', 'T3000032', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:50:31', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000038';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000031') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'f8463625-958c-47bd-8959-fbbc00ab1175', '81bcd70f-c69b-4b56-9731-93a7c33c884d', 'T3000031', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:49:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000037';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000030') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '15ddeb89-914f-4463-a040-e095e3018aaf', 'a937ed2d-1557-4fe2-8ba7-4c68e3e224b0', 'T3000030', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-14 20:49:47', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000036';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000029') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b66eaab9-10cf-4bb3-bb80-10cce9184312', 'ca2fcecb-5c4f-4eb2-bc7d-e7ee62e86457', 'T3000029', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 20:49:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000035';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000028') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a13d5cb5-abb7-4395-a78a-8e17f8f85484', 'c9917fea-303f-4ff1-ab86-3604f2540c8b', 'T3000028', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:51', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000034';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000027') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'efa59047-f31b-439e-a965-f34e661860cb', '86566ea9-2e34-48a0-8f58-c1858e580087', 'T3000027', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:50', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000033';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000026') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'b93fa498-b5fa-49ee-b970-7c66f0b03994', '9476cf19-ccf1-4fa3-8037-34af41b674b9', 'T3000026', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:48', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000032';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000025') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '6d587033-018b-48a2-b088-bbe08473e859', 'a9ebd1c4-22ce-43f2-9a8e-8dddefdf924b', 'T3000025', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:32', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000031';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000024') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '14364e0a-6fe3-44aa-8add-7b6864a6f22d', '0fdcefbf-f818-4bf3-8b20-c13f1239fee9', 'T3000024', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 20:48:30', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000030';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000023') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '893711d9-1d8e-4136-b720-0f1c71327d77', '103c5961-127d-45fc-a81a-54a84d343f27', 'T3000023', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-14 20:48:20', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000029';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000022') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'db8f7b5e-26c9-46ac-b26e-dd30dc624513', 'c990560e-d567-412b-bb9a-dd70916fae42', 'T3000022', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:19', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000028';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000021') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '917480c1-2e5d-4608-825d-3354cf822c24', '1231ba6b-b68d-487e-8717-2329cf166b18', 'T3000021', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 3000000.0, 0.0, 3000000.0, 'Initial deposit for account opening', '2025-11-14 20:48:15', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000027';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000020') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'd6f24155-d0d6-4025-890e-3d3f37cbdc85', 'f48eacc5-a27d-4289-8b73-f7cd805ca6ae', 'T3000020', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:14', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000026';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000019') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'e8363435-63ec-4a55-aa34-f44de1333ea9', '8772dc53-ddfa-4d6b-898e-1b62c5983da1', 'T3000019', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:13', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000025';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000018') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '4c4622d6-2b70-46d9-8703-8fe219479379', '31aa9445-9d31-430b-86a0-7d93fec1ab2c', 'T3000018', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-14 20:48:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000024';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000017') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'bcdcd224-f370-46d5-a4ff-2a49fe51aec8', 'ccb474da-f129-4ec5-9e28-ad492920a92e', 'T3000017', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:11', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000023';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000016') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'a640cd40-41ca-42b6-b96b-baada16f8fa6', 'ffcd0142-0fd8-4a45-aa7f-a0b909a6dfdf', 'T3000016', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000022';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000015') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0dc62613-75be-4876-9fe9-34f507729818', '193d76de-1bf9-4fdc-88f0-f5a13b4ac66e', 'T3000015', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 20:48:09', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000021';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000014') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '221d3c6a-f155-4458-b4cf-a6fe0a81d11c', '5302b759-87d7-46a1-9398-e19b168cc0dd', 'T3000014', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:08', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000020';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000013') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '1c634b20-4f5f-4da3-8253-5b5755031dd9', '08712b37-33fa-4bf5-bf36-b34ee9fb644b', 'T3000013', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:08', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000019';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000012') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '65bb4952-817c-42cc-8a50-296ebf708c23', '3d2de52c-4b3c-481a-afe3-72748ad04dd4', 'T3000012', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:03', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000018';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000011') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '57e56926-0351-49ff-8acd-653a8ae60f0f', '7639872a-b683-4d23-9072-33fd0b29829e', 'T3000011', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:48:00', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000017';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000010') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '145ea3fc-7323-41e0-91d6-2c2ae81503d3', '1fba006b-b653-4986-a372-bf6a4fa719f2', 'T3000010', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000000.0, 0.0, 50000000.0, 'Initial deposit for account opening', '2025-11-14 20:47:54', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000016';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000009') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '84e54db6-f8b1-430c-a988-2e3ee21e757d', '2ea525f9-0ed8-4556-bfe2-3d304dad3750', 'T3000009', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 500000.0, 0.0, 500000.0, 'Initial deposit for account opening', '2025-11-14 20:47:53', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000015';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000008') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '824835e1-b0cc-4e4b-ac6a-035ff64621d8', '386afe19-9b25-45e8-8523-f1b45640215f', 'T3000008', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:47:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000014';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000007') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '8710c14f-0a78-4ad0-b696-a449faa2a8c5', 'aaf9063f-7a29-4af3-9e47-68f2290b6598', 'T3000007', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:47:42', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000013';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000006') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'ec47c921-f8f4-4e53-be15-0e6244977964', '899db9d3-6cd1-47ac-adeb-c4c7124577bf', 'T3000006', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:47:27', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000012';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000005') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT 'cad2d5f2-cc27-4e3a-8d88-f0476000e7a2', '16233107-baa6-4539-9e5e-77fc3025ebea', 'T3000005', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 10000000.0, 0.0, 10000000.0, 'Initial deposit for account opening', '2025-11-14 20:47:12', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000011';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000004') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2653c12b-aeb6-44c6-b0b2-1fa825985717', '704e860a-6748-4ad5-942b-0f10a0e9c0a1', 'T3000004', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 50000.0, 0.0, 50000.0, 'Initial deposit for account opening', '2025-11-14 20:46:26', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000010';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000003') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0cb7c095-d357-46a3-b530-62895e6dad54', 'a267062b-bfcb-4a11-839b-6d01b7f49140', 'T3000003', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-14 11:19:35', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000009';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000002') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '0858e342-fd0b-42e5-86f9-d44c6a8c210c', '1c4d65a4-a16f-41e3-ac57-8491943a7198', 'T3000002', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 1000000.0, 0.0, 1000000.0, 'Initial deposit for account opening', '2025-11-13 16:32:10', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000008';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = 'T3000001') THEN
        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)
        SELECT '2802812d-0282-418a-8637-2900816465ae', 'b49db5bd-5460-4408-b6a4-3217ec0ac54a', 'T3000001', 'DEPOSIT', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, 100000.0, 0.0, 100000.0, 'Initial deposit for account opening', '2025-11-08 06:48:56', 'teller1', 'teller1', 'Teller1', 'SUCCESS', '101', false, v_periode_2025_id
        FROM rekening r WHERE r.nomor_rekening = 'A2000007';
    END IF;

    -- 4. SYNCHRONIZE POSTGRESQL SEQUENCES TO AVOID DUPLICATE IDS IN FUTURE
    PERFORM setval('nasabah_id_seq', COALESCE((SELECT MAX(id) FROM nasabah), 1));
    PERFORM setval('rekening_id_seq', COALESCE((SELECT MAX(id) FROM rekening), 1));
    PERFORM setval('trx_seq', 2000);
END $$;
