-- V2__create_postal_code_table.sql
CREATE TABLE IF NOT EXISTS postal_code (
  id BIGSERIAL PRIMARY KEY,
  kode_pos VARCHAR(20) NOT NULL,
  provinsi VARCHAR(200),
  kota VARCHAR(200),
  kecamatan VARCHAR(200),
  kelurahan VARCHAR(200)
);

CREATE INDEX IF NOT EXISTS idx_postal_code_kodepos ON postal_code(kode_pos);
