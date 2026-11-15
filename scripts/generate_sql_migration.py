import json
import re
import uuid
from datetime import datetime
import os

input_json = "c:/project-minibank/src/main/resources/data/legacy_minibank_full_dump.json"
output_sql = "c:/project-minibank/src/main/resources/db/migration/V9__seed_legacy_minibank_2025_data.sql"

if not os.path.exists(input_json):
    print(f"Waiting for {input_json} to be created...")
    exit(1)

with open(input_json, "r", encoding="utf-8") as f:
    data = json.load(f)

customers = data.get("customers", [])
accounts = data.get("accounts", [])
transactions = data.get("transactions", [])

print(f"Processing {len(customers)} customers, {len(accounts)} accounts, {len(transactions)} transactions...")

lines = []
lines.append("-- ==============================================================================")
lines.append("-- Flyway Migration V9: Seed Legacy Minibank 2025 Data (100% Comprehensive)")
lines.append("-- Linked to Periode '2025/2026' (ARCHIVED) with Sequence Synchronization")
lines.append("-- ==============================================================================")
lines.append("")
lines.append("DO $$")
lines.append("DECLARE")
lines.append("    v_periode_2025_id BIGINT;")
lines.append("BEGIN")
lines.append("    SELECT id INTO v_periode_2025_id FROM periode_praktikum WHERE kode = '2025/2026';")
lines.append("    IF v_periode_2025_id IS NULL THEN")
lines.append("        INSERT INTO periode_praktikum (kode, nama, status, is_active, is_default, tanggal_mulai, tanggal_selesai, deskripsi)")
lines.append("        VALUES ('2025/2026', 'Praktikum Minibank 2025/2026 (Arsip)', 'ARCHIVED', false, false, '2025-11-01', '2026-01-31', 'Data historis praktikum minibank tahun ajaran 2025/2026')")
lines.append("        RETURNING id INTO v_periode_2025_id;")
lines.append("    END IF;")
lines.append("")

# 1. Insert Customers
lines.append("    -- 1. SEED 162 LEGACY CUSTOMERS")
for c in customers:
    cif = c.get("cif", "").strip()
    if not cif:
        continue
    name = (c.get("fullName") or c.get("name") or "Nasabah Legacy").strip().replace("'", "''")
    nik = (c.get("nik") or "3201000000000000").strip()
    if len(nik) > 16:
        nik = nik[:16]
    elif len(nik) < 16:
        nik = nik.ljust(16, '0')
    
    tgl_lahir = c.get("dateOfBirth") or "2000-01-01"
    try:
        # validate date format
        datetime.strptime(tgl_lahir, "%Y-%m-%d")
    except:
        tgl_lahir = "2000-01-01"
        
    ibu = (c.get("motherMaidenName") or "Ibu Kandung").strip().replace("'", "''")
    phone = (c.get("phoneNumber") or "08123456789").strip()
    email = (c.get("email") or f"{cif.lower()}@tazkia.ac.id").strip().replace("'", "''")
    if "[email" in email:
        email = f"{cif.lower()}@tazkia.ac.id"
    alamat = (c.get("address") or "Kampus Tazkia Sentul").strip().replace("'", "''")
    if len(alamat) > 35:
        alamat = alamat[:35]
    kota = (c.get("city") or "Bogor").strip().replace("'", "''")
    status = "ACTIVE" if "active" in c.get("status", "").lower() else "INACTIVE"
    created = c.get("createdDate") or "2025-11-08 08:00:00"
    
    lines.append(f"    IF NOT EXISTS (SELECT 1 FROM nasabah WHERE cif = '{cif}') THEN")
    lines.append(f"        INSERT INTO nasabah (cif, nik, nama_sesuai_identitas, tanggal_lahir, nama_ibu_kandung, no_hp, email, alamat_identitas, kota_identitas, status, created_at, periode_id)")
    lines.append(f"        VALUES ('{cif}', '{nik}', '{name}', '{tgl_lahir}', '{ibu}', '{phone}', '{email}', '{alamat}', '{kota}', '{status}', '{created}', v_periode_2025_id);")
    lines.append(f"    ELSE")
    lines.append(f"        UPDATE nasabah SET periode_id = v_periode_2025_id WHERE cif = '{cif}' AND periode_id IS NULL;")
    lines.append(f"    END IF;")

lines.append("")

# 2. Insert Accounts
lines.append("    -- 2. SEED 366 LEGACY ACCOUNTS")
for a in accounts:
    acc_no = a.get("accountNumber", "").strip()
    if not acc_no:
        continue
    acc_name = (a.get("accountName") or "Tabungan Wadiah").strip().replace("'", "''")
    cust_raw = a.get("customerRaw", "")
    cif_match = re.search(r'(C\d{7})', cust_raw)
    cif = cif_match.group(1) if cif_match else ""
    
    prod_raw = (a.get("productRaw") or "").strip().replace("'", "''")
    # clean balance
    bal_str = a.get("balance", "0")
    bal_clean = re.sub(r'[^\d.]', '', bal_str)
    try:
        balance = float(bal_clean) if bal_clean else 0.0
    except:
        balance = 0.0
    
    st_raw = (a.get("status") or "ACTIVE").upper()
    status = "ACTIVE" if "ACTIVE" in st_raw else ("CLOSED" if "CLOSED" in st_raw else "INACTIVE")
    st_active = "true" if status == "ACTIVE" else "false"
    
    opened = a.get("openedDate", "")
    try:
        # e.g. 02/01/2026
        dt = datetime.strptime(opened, "%d/%m/%Y")
        opened_iso = dt.strftime("%Y-%m-%d")
    except:
        opened_iso = "2025-11-08"

    lines.append(f"    IF NOT EXISTS (SELECT 1 FROM rekening WHERE nomor_rekening = '{acc_no}') THEN")
    lines.append(f"        INSERT INTO rekening (nomor_rekening, nama_rekening, cif_nasabah, nasabah_id, produk, saldo, status, status_active, tanggal_pembukaan, created_at, nominal_setoran_awal, periode_id)")
    lines.append(f"        VALUES ('{acc_no}', '{acc_name}', '{cif}', (SELECT id FROM nasabah WHERE cif = '{cif}' LIMIT 1), '{prod_raw}', {balance}, '{status}', {st_active}, '{opened_iso}', '{opened_iso} 08:00:00', {balance}, v_periode_2025_id);")
    lines.append(f"    ELSE")
    lines.append(f"        UPDATE rekening SET periode_id = v_periode_2025_id WHERE nomor_rekening = '{acc_no}' AND periode_id IS NULL;")
    lines.append(f"    END IF;")

lines.append("")

# 3. Insert Transactions
lines.append("    -- 3. SEED 583 LEGACY TRANSACTIONS")
for t in transactions:
    trx_no = t.get("trxNumber", "").strip()
    if not trx_no:
        continue
    # clean nomor transaksi (e.g. 'T3000001 ACCOUNT-OPENING-A2000007' -> 'T3000001')
    m_no = re.match(r'(T\d{7})', trx_no)
    clean_trx_no = m_no.group(1) if m_no else trx_no[:20]
    
    raw_acc = t.get("accountRaw", "")
    m_acc = re.search(r'(A\d{7})', raw_acc)
    acc_no = m_acc.group(1) if m_acc else ""
    
    type_raw = t.get("type", "").upper()
    if "DEPOSIT" in type_raw:
        tipe = "DEPOSIT"
        sandi = "101"
    elif "WITHDRAWAL" in type_raw:
        tipe = "WITHDRAWAL"
        sandi = "201"
    else:
        tipe = "TRANSFER"
        sandi = "301"
        
    amt_raw = t.get("amountRaw", "")
    m_amt = re.search(r'([+-]?IDR\s*[\d,]+(?:\.\d+)?)', amt_raw)
    amt_num = 0.0
    saldo_num = 0.0
    if m_amt:
        amt_str = re.sub(r'[^\d.]', '', m_amt.group(1))
        try:
            amt_num = float(amt_str)
        except:
            amt_num = 0.0
    m_saldo = re.search(r'Saldo:\s*IDR\s*([\d,]+(?:\.\d+)?)', amt_raw)
    if m_saldo:
        s_str = re.sub(r'[^\d.]', '', m_saldo.group(1))
        try:
            saldo_num = float(s_str)
        except:
            saldo_num = 0.0
            
    notes = (t.get("notes") or "").strip().replace("'", "''")
    # extract teller username from notes (e.g. 'Oleh: teller1')
    m_user = re.search(r'Oleh:\s*([a-zA-Z0-9_.-]+)', notes)
    teller_user = m_user.group(1) if m_user else "teller1"
    
    date_raw = t.get("date", "")
    try:
        # e.g. 04/01/2026 20:12:22
        dt_t = datetime.strptime(date_raw, "%d/%m/%Y %H:%M:%S")
        date_iso = dt_t.strftime("%Y-%m-%d %H:%M:%S")
    except:
        date_iso = "2025-11-08 09:00:00"

    trx_id = str(uuid.uuid4())
    grp_id = str(uuid.uuid4())
    
    lines.append(f"    IF NOT EXISTS (SELECT 1 FROM transaksi WHERE nomor_transaksi = '{clean_trx_no}') THEN")
    lines.append(f"        INSERT INTO transaksi (id, group_id, nomor_transaksi, tipe, channel, rekening_id, nomor_rekening, nama_rekening, cif_nasabah, produk, jumlah, saldo_sebelum, saldo_sesudah, keterangan, processed_at, processed_by, processed_by_username, processed_by_full_name, status, sandi_code, printed, periode_id)")
    lines.append(f"        SELECT '{trx_id}', '{grp_id}', '{clean_trx_no}', '{tipe}', 'TELLER', r.id, r.nomor_rekening, r.nama_rekening, r.cif_nasabah, r.produk, {amt_num}, {max(0.0, saldo_num - amt_num)}, {saldo_num}, '{notes}', '{date_iso}', '{teller_user}', '{teller_user}', '{teller_user.capitalize()}', 'SUCCESS', '{sandi}', false, v_periode_2025_id")
    lines.append(f"        FROM rekening r WHERE r.nomor_rekening = '{acc_no}';")
    lines.append(f"    END IF;")

lines.append("")
lines.append("    -- 4. SYNCHRONIZE POSTGRESQL SEQUENCES TO AVOID DUPLICATE IDS IN FUTURE")
lines.append("    PERFORM setval('nasabah_id_seq', COALESCE((SELECT MAX(id) FROM nasabah), 1));")
lines.append("    PERFORM setval('rekening_id_seq', COALESCE((SELECT MAX(id) FROM rekening), 1));")
lines.append("    PERFORM setval('trx_seq', 2000);")
lines.append("END $$;")
lines.append("")

with open(output_sql, "w", encoding="utf-8") as f:
    f.write("\n".join(lines))

print(f"SUCCESS: Generated {output_sql} with {len(lines)} lines.")
