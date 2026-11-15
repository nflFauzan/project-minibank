-- ==============================================================================
-- Flyway Migration V8: Dynamic Academic Period Master & Foreign Keys
-- Allows zero-code management of practical sessions (Tahun Ini, Tahun Depan, dst.)
-- ==============================================================================

-- 1. Create table periode_praktikum
CREATE TABLE IF NOT EXISTS periode_praktikum (
    id BIGSERIAL PRIMARY KEY,
    kode VARCHAR(30) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    is_active BOOLEAN NOT NULL DEFAULT false,
    is_default BOOLEAN NOT NULL DEFAULT false,
    tanggal_mulai DATE,
    tanggal_selesai DATE,
    deskripsi TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- 2. Seed initial academic periods (2025/2026 Archive & 2026/2027 Active)
INSERT INTO periode_praktikum (kode, nama, status, is_active, is_default, tanggal_mulai, tanggal_selesai, deskripsi)
VALUES 
    ('2025/2026', 'Praktikum Minibank 2025/2026 (Arsip)', 'ARCHIVED', false, false, '2025-11-01', '2026-01-31', 'Data historis hasil praktikum minibank tahun ajaran 2025/2026'),
    ('2026/2027', 'Praktikum Minibank 2026/2027 (Aktif)', 'ACTIVE', true, true, '2026-09-01', '2027-01-31', 'Periode praktikum minibank tahun ajaran aktif berjalan')
ON CONFLICT (kode) DO NOTHING;

-- 3. Add periode_id foreign keys to business entities
ALTER TABLE nasabah ADD COLUMN IF NOT EXISTS periode_id BIGINT REFERENCES periode_praktikum(id);
ALTER TABLE rekening ADD COLUMN IF NOT EXISTS periode_id BIGINT REFERENCES periode_praktikum(id);
ALTER TABLE transaksi ADD COLUMN IF NOT EXISTS periode_id BIGINT REFERENCES periode_praktikum(id);

-- 4. Set existing records (if any) to active period by default
UPDATE nasabah SET periode_id = (SELECT id FROM periode_praktikum WHERE kode = '2026/2027') WHERE periode_id IS NULL;
UPDATE rekening SET periode_id = (SELECT id FROM periode_praktikum WHERE kode = '2026/2027') WHERE periode_id IS NULL;
UPDATE transaksi SET periode_id = (SELECT id FROM periode_praktikum WHERE kode = '2026/2027') WHERE periode_id IS NULL;

-- 5. Add search indexes for fast period-based queries
CREATE INDEX IF NOT EXISTS idx_nasabah_periode ON nasabah(periode_id);
CREATE INDEX IF NOT EXISTS idx_rekening_periode ON rekening(periode_id);
CREATE INDEX IF NOT EXISTS idx_transaksi_periode ON transaksi(periode_id);
