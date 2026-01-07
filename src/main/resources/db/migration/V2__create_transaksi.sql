create sequence if not exists trx_seq start 1;

create table if not exists transaksi (
  id uuid primary key,
  group_id uuid not null,

  nomor_transaksi varchar(32) not null unique,
  tipe varchar(20) not null,          -- DEPOSIT/WITHDRAWAL/TRANSFER
  channel varchar(20) not null,       -- TELLER

  nomor_rekening varchar(50) not null,
  nama_rekening varchar(255) not null,
  cif_nasabah varchar(64),
  produk varchar(255),

  jumlah numeric(38,2) not null,
  saldo_sebelum numeric(38,2) not null,
  saldo_sesudah numeric(38,2) not null,

  keterangan varchar(500) not null,
  no_referensi varchar(100),

  processed_at timestamp without time zone not null,
  processed_by_username varchar(100) not null,
  processed_by_full_name varchar(255) not null
);

create index if not exists idx_transaksi_processed_at on transaksi(processed_at desc);
create index if not exists idx_transaksi_tipe on transaksi(tipe);
create index if not exists idx_transaksi_nomor on transaksi(nomor_transaksi);
