# Project MiniBank (MVP) — Aplikasi Mini Bank

Repository: `https://github.com/nflFauzan/project-minibank.git`

Aplikasi web berbasis Spring Boot untuk kebutuhan MVP MiniBank: autentikasi + role-based access, manajemen nasabah, pembukaan rekening, serta master data pendukung (kodepos & produk tabungan). Database menggunakan PostgreSQL via Docker Compose.

---

## 1) Anggota Kelompok
> Isi nama/NIM sesuai kelompok kamu (jangan dibiarkan placeholder).

| No | Nama | NIM | Peran |
|---:|---|---|---|
| 1 | **[ISI NAMA]** | **[ISI NIM]** | Project Manager / Developer |
| 2 | **[ISI NAMA]** | **[ISI NIM]** | Analyst / Developer |
| 3 | **[ISI NAMA]** | **[ISI NIM]** | Database / Developer |
| 4 | **[ISI NAMA]** | **[ISI NIM]** | QA / Dokumentasi |

---

## 2) Tech Stack
**Backend**
- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security (Login + Role-based Access)

**Frontend**
- Thymeleaf (SSR)
- HTML/CSS (Bootstrap jika digunakan)

**Database**
- PostgreSQL (Docker Compose)

**Build Tool**
- Maven Wrapper (`./mvnw`)

**Dev Tools**
- Docker Desktop

---

## 3) Fitur (MVP)
### Autentikasi & Otorisasi
- Login pengguna
- Role-based access (pembatasan menu & URL)
- Logout
- Validasi input & error handling dasar

### Customer Service (CS)
- Nasabah
  - Lihat daftar nasabah
  - Tambah nasabah
  - Lihat detail nasabah
  - **Kodepos terisi otomatis** (master `postal_code`)
- Rekening
  - Lihat daftar rekening
  - Buka rekening baru
  - Lihat detail rekening

### Master Data
- `produk_tabungan`
- `postal_code`

---

## 4) Prasyarat
- **Docker Desktop** (Windows/Mac/Linux)
- **JDK 17+**
- Git

Cek Java:
```bash
java -version
5) Cara Menjalankan (Dari Clone sampai http://localhost:8080)
Step 1 — Clone Repository
bash
Salin kode
git clone https://github.com/nflFauzan/project-minibank.git
cd project-minibank
Step 2 — Jalankan Database dengan Docker Compose
Pastikan Docker Desktop sudah running.

bash
Salin kode
docker compose up -d
Cek container:

bash
Salin kode
docker ps
Jika environment kamu masih memakai format lama:

bash
Salin kode
docker-compose up -d
Step 3 — Jalankan Aplikasi
Windows PowerShell

powershell
Salin kode
./mvnw.cmd clean spring-boot:run
Linux/Mac

bash
Salin kode
./mvnw clean spring-boot:run
Step 4 — Akses Aplikasi
Buka:

http://localhost:8080

6) Akun Demo
Gunakan akun berikut untuk kebutuhan demo/presentasi.

Role	Username	Password
CS	zann	zann
Teller	zann	zann
Supervisor	zann	zann
Admin	admin	admin1234