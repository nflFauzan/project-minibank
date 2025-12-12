INSERT INTO users (id, username, password, full_name, approved, enabled, created_at)
VALUES (
    1,
    'admin',
    '$2a$10$5POFt3XZYy5ce1zBvdgr1O1tRcZIdnpCfDw2ZAOsncJDN7xt8o.Xa',  -- admin123
    'Administrator', 
    true, 
    true,
    NOW()
)
ON CONFLICT DO NOTHING;

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1)
ON CONFLICT DO NOTHING;

-- ==== SAMPLE PRODUK TABUNGAN ====
INSERT INTO produk_tabungan
(kode_produk, nama_produk, jenis_akad, deskripsi_singkat, setoran_awal_minimum, aktif)
VALUES
('TAB_WADIAH',
 'Tabungan Wadiah',
 'WADIAH',
 'Tabungan dengan akad wadiah, tanpa bagi hasil, cocok untuk dana titipan.',
 50000,
 true),

('TAB_MUDHARABAH',
 'Tabungan Mudharabah',
 'MUDHARABAH',
 'Tabungan dengan bagi hasil, cocok untuk nasabah yang ingin imbal hasil.',
 100000,
 true),

('TAB_HAJI',
 'Tabungan Haji',
 'MUDHARABAH',
 'Tabungan khusus persiapan haji dengan setoran rutin.',
 500000,
 true),

('TAB_PELAJAR',
 'Tabungan Pelajar',
 'WADIAH',
 'Tabungan untuk pelajar/mahasiswa, setoran awal ringan.',
 10000,
 true),

('TAB_BISNIS',
 'Tabungan Bisnis',
 'MUDHARABAH',
 'Tabungan untuk kebutuhan transaksi usaha kecil/menengah.',
 1000000,
 true);
