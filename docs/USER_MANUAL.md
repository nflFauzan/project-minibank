# Panduan Operasional Sistem Tazkia Mini Bank (User Manual)

Dokumen ini merupakan panduan praktis alur kerja dan operasional aplikasi **Tazkia Mini Bank** untuk seluruh peran pengguna: **Customer Service (CS)**, **Teller**, **Supervisor (Dosen/Instruktur Lab)**, dan **Administrator (Admin)**.

---

## 1. Peran: Customer Service (CS)

### 1.1 Registrasi Nasabah Baru
1. Masuk ke menu **Nasabah** &rarr; **Pendaftaran Nasabah**.
2. Masukkan data identitas nasabah:
   - Semua kolom teks (Nama, Tempat Lahir, Alamat, Pekerjaan, dll.) secara otomatis dikonversi menjadi huruf kapital (**UPPERCASE**).
   - **Kewarganegaraan:** Bila memilih radio button **WNI**, kolom **Negara** akan terisi otomatis dengan `INDONESIA`.
   - **Status Pernikahan:** Pilih dari opsi dropdown standar (`BELUM MENIKAH`, `MENIKAH`, `DUDA`, `JANDA`).
   - **Agama:** Pilih dari opsi dropdown standar (`ISLAM`, `KRISTEN`, `KATOLIK`, `HINDU`, `BUDHA`, `KONGHUCU`, `LAINNYA`).
3. Tekan **Simpan Data Nasabah**. Nasabah baru akan masuk ke antrean status `INACTIVE / PENDING APPROVAL` untuk diverifikasi oleh Supervisor.

### 1.2 Pencarian Data Nasabah
1. Masuk ke menu **Daftar Nasabah** (`/cs/customers`).
2. Masukkan kata kunci (Nama, Nomor CIF, NIK, No. HP, atau Email) pada kotak pencarian di bagian atas tabel.
3. Klik tombol **Cari**. Untuk mengembalikan daftar penuh, klik tombol **Reset**.

### 1.3 Pembukaan Rekening
1. Masuk ke menu **Rekening** &rarr; **Buka Rekening**.
2. Pilih nasabah yang sudah berstatus `ACTIVE` (telah disetujui Supervisor).
3. Pilih produk tabungan (contoh: Wadiah Yad Dhamanah atau Mudharabah Mutlaqah), setoran awal, dan cabang.
4. Tekan **Buka Rekening**. Rekening baru akan berada pada status `PENDING` hingga di-approve oleh Supervisor.

---

## 2. Peran: Teller

### 2.1 Transaksi Setor Tunai (Cash Deposit)
1. Masuk ke menu **Transaksi** &rarr; **Setoran Tunai**.
2. Masukkan nomor rekening tujuan dan nominal setoran.
3. **Limit Teller (Rp 5.000.000):**
   - Transaksi **&le; Rp 5.000.000**: Langsung diproses berhasil (`SUCCESS`), saldo rekening langsung bertambah.
   - Transaksi **> Rp 5.000.000**: Sistem otomatis menetapkan status transaksi menjadi `PENDING_APPROVAL` (Menunggu Persetujuan Supervisor). Saldo rekening **belum bertambah** sampai transaksi di-approve oleh Supervisor.

### 2.2 Transaksi Tarik Tunai (Cash Withdrawal)
1. Masuk ke menu **Transaksi** &rarr; **Penarikan Tunai**.
2. Masukkan nomor rekening dan nominal penarikan.
3. **Limit Teller (Rp 5.000.000):**
   - Penarikan **&le; Rp 5.000.000**: Langsung diproses berhasil, saldo rekening langsung dipotong.
   - Penarikan **> Rp 5.000.000**: Masuk ke antrean `PENDING_APPROVAL` Supervisor. Saldo **belum dipotong** sampai disetujui Supervisor.

### 2.3 Transaksi Pemindahbukuan / Transfer
1. Masuk ke menu **Transaksi** &rarr; **Transfer Antar Rekening**.
2. Masukkan nomor rekening sumber, rekening tujuan, dan nominal transfer.
3. Transaksi **> Rp 5.000.000** akan ditahan dalam status `PENDING_APPROVAL` pada kedua sisi pembukuan.

### 2.4 Riwayat & Daftar Transaksi Teller
1. Masuk ke menu **Daftar Transaksi** (`/teller/transaction/list`).
2. Tampilan tabel telah distandardisasi dengan kolom berurutan:
   - **No. Transaksi**
   - **Tanggal & Waktu** (diurutkan kronologis secara default)
   - **Rekening**
   - **Debit (Keluar)** (nominal dengan pemisah ribuan titik `Rp X.XXX.XXX`)
   - **Kredit (Masuk)** (nominal dengan pemisah ribuan titik `Rp X.XXX.XXX`)
   - **Saldo Sekarang**
   - **Teller & Keterangan**
   - **Aksi (Detail & Struk)**

### 2.5 Cetak Struk Transaksi Instan
1. Klik tombol **Struk** pada transaksi yang diinginkan, atau buka halaman detail transaksi.
2. Klik tombol biru **Cetak Struk**.
3. Dialog cetak peramban (`window.print()`) akan langsung terbuka. Area cetak diisolasi khusus format struk teller tanpa bilah samping (sidebar) dan header peramban.

### 2.6 Cetak Buku Tabungan (Blanko Fisik)
1. Masuk ke menu **Buku Tabungan** &rarr; **Cetak Buku Tabungan**.
2. Pilih nomor rekening dan baris awal cetak.
3. Kode Sandi Transaksi yang tercetak sesuai standar blanko fisik Tazkia:
   - `52` : Setoran Tunai
   - `26` : Penarikan Tunai
   - `213`: Transfer / Pemindahbukuan
4. **Mode Blanko Fisik (Cetak ke Kertas Buku Tabungan Asli):**
   - Centang opsi *"Mode Cetak Blanko Fisik (Hanya Isi Mutasi)"*.
   - Garis tabel, judul halaman, dan header kolom akan disembunyikan.
   - Printer akan mencetak angka dan tanggal secara presisi tepat di atas kolom blanko fisik buku tabungan Tazkia.

---

## 3. Peran: Supervisor (Dosen / Instruktur Lab)

### 3.1 Tab Navigasi Antrean Persetujuan (Approval Queue)
Supervisor dapat mengakses menu **Approval Queue** yang memiliki 3 tab terintegrasi lengkap dengan indikator jumlah transaksi pending:
1. **Tab 1: Approval Registrasi Nasabah** (`/supervisor/nasabah`): Memeriksa berkas identitas calon nasabah dari CS.
2. **Tab 2: Approval Pembukaan Rekening** (`/supervisor/account`): Memeriksa dan mengaktifkan rekening baru.
3. **Tab 3: Approval Transaksi Teller** (`/supervisor/transactions`):
   - Menampilkan seluruh transaksi teller dengan nominal **> Rp 5.000.000**.
   - Klik **Review** untuk melihat detail mutasi, teller pengaju, dan rekening terlibat.
   - Klik **Setujui Transaksi Teller**: Saldo rekening nasabah akan langsung dimutasi secara aman (`PESSIMISTIC_WRITE lock`).
   - Klik **Tolak Transaksi**: Masukkan alasan penolakan. Transaksi ditandai `REJECTED` dan saldo tidak dimutasi.

### 3.2 Reset Password Akun Mahasiswa / Staff
Jika mahasiswa atau staff lupa kata sandi login lab:
1. Masuk ke menu **Administration** &rarr; **Users** (`/rbac/users/list`).
2. Cari nama pengguna atau NIM mahasiswa yang bersangkutan.
3. Klik tombol **Password** pada baris pengguna.
4. Masukkan password baru (minimal 6 karakter) dan konfirmasi password.
5. Klik **Set Password**. Mahasiswa dapat langsung login kembali menggunakan password baru tersebut.

### 3.3 Laporan Operasional Berkala
1. Masuk ke menu **Reports** (`/reports`).
2. Pilih periode yang diinginkan:
   - **Harian:** Pilih tanggal kalender tertentu.
   - **Bulanan:** Pilih bulan dan tahun.
   - **Tahunan:** Pilih tahun.
3. Sistem secara otomatis menghitung dan menampilkan:
   - **Total Transaksi**
   - **Volume Dana Masuk (Kredit)**
   - **Volume Dana Keluar (Debit)**
   - **Arus Kas Bersih (Net Cash Flow)**
   - **Jumlah Rekening Baru Terbuka**
   - **Jumlah Nasabah Baru Terdaftar**
   - Tabel rincian seluruh mutasi pada periode tersebut.
4. Klik tombol **Cetak Laporan** untuk mencetak format laporan resmi berserta tanda tangan Petugas dan Supervisor / Dosen Pembimbing.

---

## 4. Peran: Administrator (Admin)

1. Memiliki wewenang penuh atas seluruh menu (CS, Teller, Supervisor, dan Sistem).
2. Mengelola pendaftaran akun pengguna baru di `/rbac/users/list` (Approve / Reject pendaftar baru).
3. Mengatur penugasan Role pengguna di `/rbac/roles/list`.
4. Mengelola hak akses Permission di `/rbac/permissions/list`.

