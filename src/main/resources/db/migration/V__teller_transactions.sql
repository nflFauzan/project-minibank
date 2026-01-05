-- 1) tambah saldo di rekening
alter table rekening
  add column if not exists saldo numeric(38,2);

update rekening
set saldo = coalesce(nominal_setoran_awal, 0)
where saldo is null;

alter table rekening
  alter column saldo set not null;

-- 2) sequence nomor transaksi: T3000000 dst (biar mirip referensi)
create sequence if not exists transaksi_no_seq
  start with 3000000
  increment by 1;

-- 3) tabel transaksi (ledger)
create table if not exists transaksi (
  id uuid primary key,
  group_id uuid not null,
  nomor_transaksi varchar(32) not null,         -- boleh sama untuk 2 baris transfer
  tipe varchar(20) not null,                    -- DEPOSIT/WITHDRAWAL/TRANSFER
  channel varchar(20) not null,                 -- TELLER
  rekening_id bigint not null references rekening(id),
  jumlah numeric(38,2) not null,                -- + untuk masuk, - untuk keluar
  saldo_sebelum numeric(38,2) not null,
  saldo_sesudah numeric(38,2) not null,
  keterangan varchar(255),
  processed_by varchar(200) not null,           -- full_name
  processed_at timestamp without time zone not null
);

create index if not exists idx_transaksi_processed_at on transaksi(processed_at desc);
create index if not exists idx_transaksi_nomor on transaksi(nomor_transaksi);
create index if not exists idx_transaksi_rekening on transaksi(rekening_id);
create index if not exists idx_transaksi_tipe on transaksi(tipe);
create index if not exists idx_transaksi_group on transaksi(group_id);
