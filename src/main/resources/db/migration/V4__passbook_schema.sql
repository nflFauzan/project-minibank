-- V4: Passbook Printing Schema and Transaction Print Tracking

CREATE SEQUENCE IF NOT EXISTS passbook_no_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS passbook (
    id UUID PRIMARY KEY,
    rekening_id BIGINT NOT NULL REFERENCES rekening(id) ON DELETE CASCADE,
    nomor_rekening VARCHAR(50) NOT NULL,
    passbook_number VARCHAR(50) NOT NULL UNIQUE,
    current_page INT NOT NULL DEFAULT 1,
    last_printed_line INT NOT NULL DEFAULT 0,
    lines_per_page INT NOT NULL DEFAULT 30,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    last_print_date TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_passbook_rekening_id ON passbook(rekening_id);
CREATE INDEX IF NOT EXISTS idx_passbook_nomor_rekening ON passbook(nomor_rekening);
CREATE INDEX IF NOT EXISTS idx_passbook_number ON passbook(passbook_number);

ALTER TABLE transaksi ADD COLUMN IF NOT EXISTS printed BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE transaksi ADD COLUMN IF NOT EXISTS printed_at TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE transaksi ADD COLUMN IF NOT EXISTS printed_page INT;
ALTER TABLE transaksi ADD COLUMN IF NOT EXISTS printed_line INT;
ALTER TABLE transaksi ADD COLUMN IF NOT EXISTS sandi_code VARCHAR(10);

CREATE INDEX IF NOT EXISTS idx_transaksi_printed ON transaksi(printed);

