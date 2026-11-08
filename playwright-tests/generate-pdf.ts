/**
 * =============================================================================
 * MINIBANK TUTORIAL — MASTER PDF GENERATOR
 * =============================================================================
 * Compiles all screenshots and detailed Indonesian tutorials into:
 * 1. docs/panduan/panduan-minibank-lengkap.pdf (Master Book: All Roles)
 * 2. docs/panduan/panduan-minibank-cs.pdf      (Customer Service Specialist)
 * 3. docs/panduan/panduan-minibank-teller.pdf  (Teller Specialist)
 * 4. docs/panduan/panduan-minibank-supervisor.pdf (Supervisor & Manager)
 * 5. docs/panduan/panduan-minibank-admin.pdf   (System Administrator)
 * =============================================================================
 */
import { chromium } from '@playwright/test';
import * as path from 'path';
import * as fs from 'fs';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const DOCS_DIR = path.resolve(__dirname, '../docs/panduan');
const SCREENSHOT_DIR = path.resolve(DOCS_DIR, 'screenshots');

function getBase64Image(relPath: string): string {
  const fullPath = path.resolve(SCREENSHOT_DIR, relPath);
  if (fs.existsSync(fullPath)) {
    const ext = path.extname(fullPath).slice(1);
    const data = fs.readFileSync(fullPath).toString('base64');
    return `data:image/${ext};base64,${data}`;
  }
  return '';
}

function renderImageBlock(relPath: string, caption: string, figureNum: string): string {
  const base64 = getBase64Image(relPath);
  if (!base64) {
    return `
      <div class="img-placeholder">
        <p>📷 <strong>[Tangkapan Layar: ${caption}]</strong></p>
        <span class="img-missing">File gambar: ${relPath}</span>
      </div>
    `;
  }
  return `
    <figure class="doc-figure">
      <div class="img-wrapper">
        <img src="${base64}" alt="${caption}" />
      </div>
      <figcaption><strong>Gambar ${figureNum}:</strong> ${caption}</figcaption>
    </figure>
  `;
}

// Generate HTML Content
export function buildHtml(roleFilter?: 'CS' | 'TELLER' | 'SUPERVISOR' | 'ADMIN'): string {
  const isMaster = !roleFilter;
  const isCs = isMaster || roleFilter === 'CS';
  const isTeller = isMaster || roleFilter === 'TELLER';
  const isSpv = isMaster || roleFilter === 'SUPERVISOR';
  const isAdmin = isMaster || roleFilter === 'ADMIN';

  let title = 'BUKU PANDUAN PENGGUNAAN SISTEM TAZKIA MINIBANK';
  let subtitle = 'Pedoman Operasional Lengkap Berdasarkan Peran (Role-Based Core Banking Manual)';
  if (roleFilter === 'CS') {
    title = 'BUKU PANDUAN OPERASIONAL CUSTOMER SERVICE (CS)';
    subtitle = 'Pedoman Lengkap Pembukaan Rekening, Registrasi Nasabah & Layanan Produk — Tazkia Minibank';
  } else if (roleFilter === 'TELLER') {
    title = 'BUKU PANDUAN OPERASIONAL TELLER';
    subtitle = 'Pedoman Lengkap Setoran, Penarikan, Pemindahbukuan & Cetak Buku Tabungan — Tazkia Minibank';
  } else if (roleFilter === 'SUPERVISOR') {
    title = 'BUKU PANDUAN OPERASIONAL SUPERVISOR & BRANCH MANAGER';
    subtitle = 'Pedoman Otorisasi (Dual Control), Monitoring Transaksi, Laporan & Audit — Tazkia Minibank';
  } else if (roleFilter === 'ADMIN') {
    title = 'BUKU PANDUAN ADMINISTRATOR SISTEM';
    subtitle = 'Pedoman Konfigurasi Role, Hak Akses (RBAC), Manajemen Pengguna & Tata Kelola Sistem — Tazkia Minibank';
  }

  return `<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <title>${title}</title>
  <style>
    @page {
      size: A4;
      margin: 18mm 15mm 20mm 15mm;
      @bottom-right {
        content: counter(page);
      }
    }

    * {
      box-sizing: border-box;
      -webkit-print-color-adjust: exact !important;
      print-color-adjust: exact !important;
    }

    body {
      font-family: 'Segoe UI', -apple-system, BlinkMacSystemFont, Roboto, Helvetica, Arial, sans-serif;
      font-size: 10.5pt;
      line-height: 1.55;
      color: #1e293b;
      background-color: #ffffff;
      margin: 0;
      padding: 0;
    }

    /* ─── COVER PAGE ─── */
    .cover-page {
      page-break-after: always;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: 98vh;
      padding: 35px 20px 20px;
      text-align: center;
      border: 8px double #1e3a8a;
      border-radius: 12px;
      background: linear-gradient(180deg, #f8fafc 0%, #ffffff 60%, #eff6ff 100%);
    }

    .cover-header {
      margin-top: 15px;
    }

    .cover-institution {
      font-size: 15pt;
      font-weight: 800;
      letter-spacing: 1.5px;
      color: #1e3a8a;
      text-transform: uppercase;
      margin-bottom: 4px;
    }

    .cover-sub-inst {
      font-size: 11pt;
      font-weight: 600;
      color: #0d9488;
      letter-spacing: 0.8px;
      text-transform: uppercase;
    }

    .cover-divider {
      width: 140px;
      height: 4px;
      background: #1e3a8a;
      margin: 25px auto;
      border-radius: 2px;
    }

    .cover-title-box {
      margin: 30px 10px;
    }

    .cover-badge {
      display: inline-block;
      padding: 6px 16px;
      background: #1e3a8a;
      color: #ffffff;
      font-size: 9pt;
      font-weight: 700;
      border-radius: 50px;
      letter-spacing: 1.2px;
      text-transform: uppercase;
      margin-bottom: 20px;
    }

    .cover-title {
      font-size: 24pt;
      font-weight: 900;
      line-height: 1.25;
      color: #0f172a;
      margin: 0 0 16px 0;
    }

    .cover-subtitle {
      font-size: 12pt;
      color: #475569;
      max-width: 650px;
      margin: 0 auto;
      line-height: 1.5;
    }

    .cover-footer {
      border-top: 1px solid #cbd5e1;
      padding-top: 20px;
      font-size: 9.5pt;
      color: #64748b;
    }

    .cover-meta-grid {
      display: flex;
      justify-content: space-around;
      margin-bottom: 15px;
      text-align: left;
    }

    .meta-item strong {
      display: block;
      color: #1e3a8a;
      font-size: 9pt;
      text-transform: uppercase;
    }

    /* ─── HEADINGS & SECTIONS ─── */
    .page-break {
      page-break-before: always;
    }

    h1.chapter-title {
      font-size: 19pt;
      font-weight: 900;
      color: #1e3a8a;
      border-bottom: 3px solid #1e3a8a;
      padding-bottom: 8px;
      margin-top: 0;
      margin-bottom: 18px;
      page-break-after: avoid;
    }

    h2.section-title {
      font-size: 13.5pt;
      font-weight: 800;
      color: #0f172a;
      border-left: 4px solid #0284c7;
      padding-left: 10px;
      margin-top: 24px;
      margin-bottom: 12px;
      page-break-after: avoid;
    }

    h3.subsection-title {
      font-size: 11pt;
      font-weight: 700;
      color: #334155;
      margin-top: 16px;
      margin-bottom: 8px;
      page-break-after: avoid;
    }

    p {
      margin-top: 0;
      margin-bottom: 10px;
      text-align: justify;
    }

    /* ─── CALLOUT BOXES ─── */
    .callout {
      padding: 12px 16px;
      border-radius: 8px;
      margin: 14px 0;
      font-size: 10pt;
      page-break-inside: avoid;
    }

    .callout-info {
      background: #eff6ff;
      border-left: 4px solid #3b82f6;
      color: #1e40af;
    }

    .callout-warning {
      background: #fffbeb;
      border-left: 4px solid #f59e0b;
      color: #92400e;
    }

    .callout-success {
      background: #f0fdf4;
      border-left: 4px solid #10b981;
      color: #065f46;
    }

    .callout-tip {
      background: #f5f3ff;
      border-left: 4px solid #8b5cf6;
      color: #5b21b6;
    }

    .callout strong {
      display: block;
      margin-bottom: 4px;
      font-size: 10.5pt;
    }

    /* ─── TABLES ─── */
    table.data-table {
      width: 100%;
      border-collapse: collapse;
      margin: 14px 0;
      font-size: 9pt;
      page-break-inside: avoid;
    }

    table.data-table th, table.data-table td {
      border: 1px solid #cbd5e1;
      padding: 7px 10px;
      text-align: left;
    }

    table.data-table th {
      background: #1e3a8a;
      color: #ffffff;
      font-weight: 700;
      font-size: 9pt;
    }

    table.data-table tr:nth-child(even) {
      background: #f8fafc;
    }

    /* ─── STEP LISTS ─── */
    ol.step-list {
      margin: 10px 0 16px 20px;
      padding: 0;
    }

    ol.step-list > li {
      margin-bottom: 8px;
      padding-left: 4px;
    }

    ol.step-list > li strong {
      color: #0f172a;
    }

    /* ─── FIGURES & SCREENSHOTS ─── */
    figure.doc-figure {
      margin: 14px 0 20px 0;
      padding: 0;
      text-align: center;
      page-break-inside: avoid;
    }

    .img-wrapper {
      background: #ffffff;
      border: 1px solid #cbd5e1;
      border-radius: 8px;
      padding: 4px;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.08);
      display: inline-block;
      max-width: 100%;
    }

    .img-wrapper img {
      max-width: 100%;
      max-height: 480px;
      height: auto;
      border-radius: 5px;
      display: block;
    }

    figcaption {
      margin-top: 6px;
      font-size: 9pt;
      color: #475569;
      font-style: italic;
    }

    .img-placeholder {
      background: #f1f5f9;
      border: 2px dashed #94a3b8;
      border-radius: 8px;
      padding: 20px;
      text-align: center;
      margin: 14px 0;
      color: #64748b;
    }

    .img-missing {
      display: block;
      font-size: 8pt;
      color: #94a3b8;
      margin-top: 4px;
    }

    /* ─── TOC (Table of Contents) ─── */
    .toc-container {
      margin: 20px 0;
    }

    .toc-item {
      display: flex;
      justify-content: space-between;
      margin-bottom: 6px;
      font-size: 10pt;
    }

    .toc-item.ch {
      font-weight: 700;
      color: #1e3a8a;
      margin-top: 10px;
      border-bottom: 1px dotted #cbd5e1;
      padding-bottom: 3px;
    }

    .toc-item.sub {
      padding-left: 20px;
      color: #334155;
    }

    .badge-role {
      display: inline-block;
      padding: 2px 8px;
      border-radius: 4px;
      font-size: 8pt;
      font-weight: 700;
      color: #fff;
    }
    .badge-cs { background: #0284c7; }
    .badge-teller { background: #16a34a; }
    .badge-spv { background: #d97706; }
    .badge-admin { background: #dc2626; }
  </style>
</head>
<body>

  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- COVER PAGE -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="cover-page">
    <div class="cover-header">
      <div class="cover-institution">Institut Agama Islam Tazkia</div>
      <div class="cover-sub-inst">Laboratorium Mini Bank Syariah & Sistem Perbankan Terpadu</div>
      <div class="cover-divider"></div>
    </div>

    <div class="cover-title-box">
      <span class="cover-badge">Buku Panduan Resmi Sistem</span>
      <h1 class="cover-title">${title}</h1>
      <p class="cover-subtitle">${subtitle}</p>
    </div>

    <div class="cover-footer">
      <div class="cover-meta-grid">
        <div class="meta-item">
          <strong>Sistem Aplikasi</strong>
          Tazkia Minibank Core Banking System v2.0
        </div>
        <div class="meta-item">
          <strong>Sasaran Pengguna</strong>
          ${roleFilter ? 'Role: ' + roleFilter : 'Semua Role Operasional Perbankan'}
        </div>
        <div class="meta-item">
          <strong>Tahun Akademik</strong>
          2025/2026
        </div>
      </div>
      <div>&copy; 2026 Laboratorium Perbankan Syariah Tazkia. Dokumen ini digunakan sebagai standar operasional prosedur (SOP) dan modul praktikum.</div>
    </div>
  </div>

  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- DAFTAR ISI & PENDAHULUAN -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">Daftar Isi Panduan</h1>

  <div class="toc-container">
    <div class="toc-item ch"><span>BAB 1: PENGENALAN DAN ARSITEKTUR TAZKIA MINIBANK</span></div>
    <div class="toc-item sub"><span>1.1 Sekilas Mengenai Sistem Tazkia Minibank</span></div>
    <div class="toc-item sub"><span>1.2 Prinsip Pengendalian Ganda (Dual Control / Four-Eyes Principle)</span></div>
    <div class="toc-item sub"><span>1.3 Matriks Hak Akses & Pembagian Wewenang (RBAC)</span></div>
    <div class="toc-item sub"><span>1.4 Akun Standar Pengguna (Default Training Accounts)</span></div>
    <div class="toc-item sub"><span>1.5 Alur Akses Awal: Login, Error Handling & Registrasi Mandiri (Signup)</span></div>

    ${isCs ? `
    <div class="toc-item ch"><span>BAB 2: PANDUAN OPERASIONAL CUSTOMER SERVICE (CS)</span></div>
    <div class="toc-item sub"><span>2.1 Profil Peran & Navigasi Sidebar Customer Service</span></div>
    <div class="toc-item sub"><span>2.2 Dashboard Khusus Customer Service</span></div>
    <div class="toc-item sub"><span>2.3 Skenario 1: Pendaftaran Nasabah Baru (CIF Registration)</span></div>
    <div class="toc-item sub"><span>2.4 Skenario 2: Pencarian dan Pemeliharaan Data Nasabah (Customer Inquiry & Edit)</span></div>
    <div class="toc-item sub"><span>2.5 Skenario 3: Pembukaan Rekening Tabungan Baru (Account Opening Flow)</span></div>
    <div class="toc-item sub"><span>2.6 Skenario 4: Monitoring Mutasi dan Riwayat Transaksi Rekening</span></div>
    <div class="toc-item sub"><span>2.7 Skenario 5: Penutupan Rekening Nasabah (Account Closure)</span></div>
    <div class="toc-item sub"><span>2.8 Skenario 6: Eksplorasi & Pembuatan Katalog Produk Tabungan Syariah</span></div>
    ` : ''}

    ${isTeller ? `
    <div class="toc-item ch"><span>BAB 3: PANDUAN OPERASIONAL TELLER</span></div>
    <div class="toc-item sub"><span>3.1 Profil Peran & Navigasi Sidebar Teller</span></div>
    <div class="toc-item sub"><span>3.2 Dashboard Operasional Teller</span></div>
    <div class="toc-item sub"><span>3.3 Skenario 1: Transaksi Setoran Tunai (Cash Deposit)</span></div>
    <div class="toc-item sub"><span>3.4 Skenario 2: Transaksi Penarikan Tunai (Cash Withdrawal)</span></div>
    <div class="toc-item sub"><span>3.5 Skenario 3: Pemindahbukuan / Transfer Antar Rekening (3-Step Flow)</span></div>
    <div class="toc-item sub"><span>3.6 Skenario 4: Pencetakan Slip / Bukti Transaksi Teller (Receipt PDF)</span></div>
    <div class="toc-item sub"><span>3.7 Skenario 5: Layanan Buku Tabungan (Passbook Printing & Booklet)</span></div>
    ` : ''}

    ${isSpv ? `
    <div class="toc-item ch"><span>BAB 4: PANDUAN OPERASIONAL SUPERVISOR & BRANCH MANAGER</span></div>
    <div class="toc-item sub"><span>4.1 Profil Peran Supervisor & Branch Manager</span></div>
    <div class="toc-item sub"><span>4.2 Dashboard Supervisi & Antrean Otorisasi</span></div>
    <div class="toc-item sub"><span>4.3 Skenario 1: Otorisasi Pendaftaran Nasabah Baru (CIF Approval)</span></div>
    <div class="toc-item sub"><span>4.4 Skenario 2: Otorisasi Pembukaan Rekening Baru (Account Approval)</span></div>
    <div class="toc-item sub"><span>4.5 Skenario 3: Otorisasi Transaksi Teller (Financial Transaction Approval)</span></div>
    <div class="toc-item sub"><span>4.6 Skenario 4: Laporan Keuangan Harian, Bulanan, dan Tahunan (Financial Reports)</span></div>
    <div class="toc-item sub"><span>4.7 Skenario 5: Pengawasan Akun Pengguna & Reset Password Staff</span></div>
    <div class="toc-item sub"><span>4.8 Skenario 6: Pengawasan Log Aktivitas & Jejak Audit (Audit Trail)</span></div>
    ` : ''}

    ${isAdmin ? `
    <div class="toc-item ch"><span>BAB 5: PANDUAN ADMINISTRATOR SISTEM (SYSTEM ADMINISTRATOR)</span></div>
    <div class="toc-item sub"><span>5.1 Profil Peran & Struktur Navigasi Komprehensif Admin</span></div>
    <div class="toc-item sub"><span>5.2 Dashboard Utama Administrator</span></div>
    <div class="toc-item sub"><span>5.3 Skenario 1: Tata Kelola Pengguna Sistem (User Management Lifecycle)</span></div>
    <div class="toc-item sub"><span>5.4 Skenario 2: Manajemen Peran Sistem & Pemetaan Hak Akses (Role Management)</span></div>
    <div class="toc-item sub"><span>5.5 Skenario 3: Manajemen Hak Akses Granular (Granular Permissions Directory)</span></div>
    <div class="toc-item sub"><span>5.6 Skenario 4: Pengawasan Lintas Modul (Full-Access Supervision)</span></div>
    ` : ''}

    ${isMaster ? `
    <div class="toc-item ch"><span>BAB 6: SKENARIO LENGKAP END-TO-END (CROSS-ROLE WORKFLOWS)</span></div>
    <div class="toc-item sub"><span>6.1 Alur Hidup Nasabah & Rekening: Dari Registrasi Hingga Transaksi Perdana</span></div>
    <div class="toc-item sub"><span>6.2 Alur Transaksi Finansial Teller & Eskalasi Otorisasi Supervisor</span></div>
    <div class="toc-item sub"><span>6.3 Alur Onboarding Staff Baru & Persetujuan Administrator</span></div>
    ` : ''}
  </div>

  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- BAB 1: PENGENALAN SISTEM -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">BAB 1: Pengenalan dan Arsitektur Tazkia Minibank</h1>

  <h2 class="section-title">1.1 Sekilas Mengenai Sistem Tazkia Minibank</h2>
  <p>
    <strong>Tazkia Minibank Core Banking System</strong> adalah platform perbankan inti (core banking) berbasis web yang dirancang khusus untuk memenuhi standar simulasi operasional bank syariah modern. Sistem ini mensimulasikan seluruh siklus perbankan ritel, mulai dari pendaftaran nasabah (CIF), pembukaan rekening tabungan berbasis akad syariah (seperti Wadiah Yad Dhamanah dan Mudharabah Mutlaqah), pemrosesan transaksi teller (setoran, penarikan, pemindahbukuan), pencetakan buku tabungan fisik/booklet, otorisasi supervisor berjenjang, hingga pelaporan keuangan komprehensif dan manajemen hak akses (RBAC).
  </p>

  <h2 class="section-title">1.2 Prinsip Pengendalian Ganda (Dual Control / Four-Eyes Principle)</h2>
  <p>
    Salah satu pilar integritas dalam perbankan syariah dan konvensional adalah <em>Dual Control</em> (Prinsip 4 Mata / Four-Eyes Principle). Pada Tazkia Minibank, seorang Customer Service (Maker) dapat menginput permohonan nasabah baru atau rekening baru, namun data tersebut <strong>tidak langsung aktif</strong>. Sistem akan menempatkannya dalam status <code>PENDING_APPROVAL</code>. Data tersebut wajib diperiksa dan disetujui (Checker / Approver) oleh seorang <strong>Supervisor</strong> atau <strong>Branch Manager</strong> sebelum nasabah atau rekening dapat bertransaksi.
  </p>

  <div class="callout callout-warning">
    <strong>Aturan Keamanan Perbankan:</strong>
    Setiap transaksi atau pembukaan data yang melibatkan risiko keuangan (pembuatan CIF baru, rekening baru, dan transaksi di atas limit kewenangan) wajib melalui mekanisme persetujuan berjenjang. Sistem mencegah seorang staf menyetujui transaksinya sendiri.
  </div>

  <h2 class="section-title">1.3 Matriks Hak Akses & Pembagian Wewenang (RBAC)</h2>
  <p>
    Sistem menerapkan kontrol akses berbasis peran (<em>Role-Based Access Control / RBAC</em>) dengan pembagian menu pada sidebar navigasi yang otomatis menyesuaikan kewenangan login pengguna:
  </p>

  <table class="data-table">
    <thead>
      <tr>
        <th>Menu Navigasi Sidebar</th>
        <th>Customer Service</th>
        <th>Teller</th>
        <th>Supervisor / BM</th>
        <th>Administrator</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><strong>Dashboard</strong></td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Product (Katalog Produk)</strong></td>
        <td>✅ Akses</td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Customer (Data CIF Nasabah)</strong></td>
        <td>✅ Akses</td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Account (Daftar & Buka Rekening)</strong></td>
        <td>✅ Akses</td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Transaction (Setoran/Tarik/Transfer)</strong></td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Passbook (Cetak Buku Tabungan)</strong></td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Reports (Laporan Finansial)</strong></td>
        <td>❌ Tersembunyi</td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Approval Queue (Antrean Otorisasi)</strong></td>
        <td>❌ Tersembunyi</td>
        <td>❌ Tersembunyi</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
      <tr>
        <td><strong>Administration (Users, Roles, Permissions)</strong></td>
        <td>❌ Tersembunyi</td>
        <td>❌ Tersembunyi</td>
        <td>⚠️ Terbatas (Users)</td>
        <td>✅ Akses Penuh</td>
      </tr>
      <tr>
        <td><strong>Settings & Logout</strong></td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
        <td>✅ Akses</td>
      </tr>
    </tbody>
  </table>

  <h2 class="section-title">1.4 Akun Standar Pengguna (Default Training Accounts)</h2>
  <p>Untuk keperluan praktikum dan uji coba operasional, sistem telah menyediakan akun-akun bawaan:</p>

  <table class="data-table">
    <thead>
      <tr>
        <th>Role / Peran</th>
        <th>Username</th>
        <th>Password</th>
        <th>Nama Pegawai</th>
        <th>Unit Kerja / Cabang</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><span class="badge-role badge-cs">CS</span> Customer Service</td>
        <td><code>cs1</code> / <code>zann</code></td>
        <td><code>minibank123</code> / <code>zann</code></td>
        <td>Customer Service 1 / Zann</td>
        <td>Cabang Jakarta Timur (JKT01)</td>
      </tr>
      <tr>
        <td><span class="badge-role badge-teller">TELLER</span> Teller Kasir</td>
        <td><code>teller</code> / <code>teller1</code></td>
        <td><code>teller1234</code> / <code>minibank123</code></td>
        <td>Teller Default / Teller 1</td>
        <td>Cabang Jakarta Timur (JKT01)</td>
      </tr>
      <tr>
        <td><span class="badge-role badge-spv">SPV</span> Supervisor</td>
        <td><code>spv</code></td>
        <td><code>minibank123</code> / <code>spv1234</code></td>
        <td>Supervisor Default</td>
        <td>Kantor Pusat Jakarta (HO001)</td>
      </tr>
      <tr>
        <td><span class="badge-role badge-admin">ADMIN</span> Administrator</td>
        <td><code>admin</code></td>
        <td><code>minibank123</code> / <code>admin1234</code></td>
        <td>Super Admin Minibank</td>
        <td>Kantor Pusat Jakarta (HO001)</td>
      </tr>
    </tbody>
  </table>

  <h2 class="section-title">1.5 Alur Akses Awal: Login, Error Handling & Registrasi Mandiri</h2>
  <p>Pengguna dapat mengakses antarmuka Minibank melalui peramban web modern dengan alamat <code>http://localhost:8080/</code> atau domain server kampus yang ditentukan.</p>

  ${renderImageBlock('00-common/C-01_landing-page.png', 'Halaman Landing Page Tazkia Minibank Core Banking System', '1.1')}

  <h3 class="subsection-title">Langkah Melakukan Login:</h3>
  <ol class="step-list">
    <li>Buka halaman login di <code>/login</code> atau klik tombol <strong>Login ke Sistem</strong> dari Landing Page.</li>
    <li>Ketikkan <strong>Username</strong> dan <strong>Password</strong> terdaftar.</li>
    <li>Jika terdapat pilihan modul peran pada antarmuka, pilih modul yang sesuai dengan tugas kerja Anda (misal: <em>CUSTOMER SERVICE</em>, <em>TELLER</em>, dsb).</li>
    <li>Klik tombol <strong>Sign In</strong>. Sistem akan memvalidasi kredensial dan mengarahkan Anda ke Dashboard sesuai peran.</li>
  </ol>

  ${renderImageBlock('00-common/C-02_login-page.png', 'Formulir Login Autentikasi Pengguna Minibank', '1.2')}

  <h3 class="subsection-title">Penanganan Kesalahan Login:</h3>
  <p>
    Apabila username atau password salah dimasukkan, sistem akan menampilkan peringatan berwarna merah di atas form dan mencegah akses masuk untuk melindungi keamanan perbankan.
  </p>

  ${renderImageBlock('00-common/C-03_login-error.png', 'Peringatan Kesalahan Autentikasi Login Tidak Valid', '1.3')}

  <h3 class="subsection-title">Pendaftaran Mandiri (Self-Registration):</h3>
  <p>
    Mahasiswa atau staf magang yang belum memiliki akun dapat melakukan registrasi melalui tautan <code>/signup</code>. Akun yang didaftarkan melalui form ini berstatus <code>PENDING</code> dan wajib disetujui (diberi peran) oleh Administrator sebelum dapat digunakan.
  </p>

  ${renderImageBlock('00-common/C-04_signup-page.png', 'Formulir Pendaftaran Akun Staf Baru (Self-Registration)', '1.4')}

  ${isCs ? `
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- BAB 2: CUSTOMER SERVICE -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">BAB 2: Panduan Operasional Customer Service (CS)</h1>

  <h2 class="section-title">2.1 Profil Peran & Navigasi Sidebar Customer Service</h2>
  <p>
    <strong>Customer Service (CS)</strong> bertindak sebagai garda terdepan (frontline) yang berinteraksi langsung dengan calon nasabah maupun nasabah eksisting. Tanggung jawab utama CS mencakup verifikasi dokumen legalitas, pencatatan data identitas nasabah (CIF - <em>Customer Information File</em>), pembukaan rekening simpanan syariah, pemeliharaan data nasabah, dan penutupan rekening.
  </p>
  <p>
    Pada saat staf login dengan peran CS, sistem secara otomatis mengaktifkan menu sidebar khusus yang terdiri dari:
  </p>
  <ul>
    <li><strong>Dashboard:</strong> Ringkasan aktivitas dan metrik operasional CS.</li>
    <li><strong>Product:</strong> Katalog produk simpanan, konfigurasi nisbah bagi hasil, dan ketentuan setoran minimum.</li>
    <li><strong>Customer:</strong> Manajemen data CIF nasabah, pencarian nasabah, dan pendaftaran nasabah baru.</li>
    <li><strong>Account:</strong> Daftar rekening aktif, pengajuan pembukaan rekening baru, monitoring mutasi rekening, dan penutupan rekening.</li>
    <li><strong>Settings & Logout:</strong> Profil akun dan penghentian sesi operasional secara aman.</li>
  </ul>

  <h2 class="section-title">2.2 Dashboard Khusus Customer Service</h2>
  <p>
    Dashboard Customer Service menyajikan informasi penting seperti ringkasan jumlah nasabah yang ditangani, status permohonan pembukaan rekening, serta pengumuman operasional cabang.
  </p>

  ${renderImageBlock('01-cs/CS-01_dashboard.png', 'Antarmuka Dashboard Customer Service Tazkia Minibank', '2.1')}

  <h2 class="section-title">2.3 Skenario 1: Pendaftaran Nasabah Baru (CIF Registration)</h2>
  <p>
    Setiap calon nasabah yang ingin membuka rekening di Bank Mini Tazkia wajib terlebih dahulu memiliki nomor <strong>CIF (Customer Information File)</strong>. Pendaftaran CIF dilakukan satu kali untuk seumur hidup nasabah (<em>Single Customer Identity</em>).
  </p>

  <h3 class="subsection-title">Langkah-Langkah Pendaftaran CIF:</h3>
  <ol class="step-list">
    <li>Klik menu <strong>Customer</strong> pada sidebar navigasi di sebelah kiri.</li>
    <li>Pada halaman daftar nasabah, klik tombol biru <strong>Daftarkan Nasabah Baru</strong> di sudut kanan atas.</li>
  </ol>

  ${renderImageBlock('01-cs/CS-11_customer-list.png', 'Halaman Direktori Nasabah (Customer List)', '2.2')}

  <ol class="step-list" start="3">
    <li>
      Sistem akan menampilkan formulir pendaftaran nasabah komprehensif. Isi bagian <strong>Data Identitas Diri</strong>:
      <ul>
        <li><strong>Nomor Induk Kependudukan (NIK):</strong> 16 digit sesuai e-KTP.</li>
        <li><strong>Nama Lengkap Sesuai KTP:</strong> Nama resmi tanpa singkatan.</li>
        <li><strong>Tempat & Tanggal Lahir:</strong> Kota kelahiran dan tanggal lahir.</li>
        <li><strong>Jenis Kelamin & Status Perkawinan:</strong> Pilih dari dropdown opsi.</li>
        <li><strong>Nama Gadis Ibu Kandung:</strong> Data keamanan krusial untuk verifikasi perbankan.</li>
      </ul>
    </li>
  </ol>

  ${renderImageBlock('01-cs/CS-14_customer-register-identity.png', 'Formulir Pendaftaran Nasabah - Seksi Identitas Diri', '2.3')}

  <ol class="step-list" start="4">
    <li>
      Lanjutkan mengisi bagian <strong>Alamat Domisili & Kontak</strong>:
      <ul>
        <li><strong>Alamat KTP:</strong> Nama jalan, RT/RW, nomor rumah.</li>
        <li><strong>Provinsi, Kota/Kabupaten, Kecamatan, dan Kelurahan:</strong> Gunakan bantuan data wilayah terintegrasi.</li>
        <li><strong>Nomor HP & Alamat Email:</strong> Kontak aktif nasabah untuk pengiriman informasi notifikasi.</li>
      </ul>
    </li>
  </ol>

  ${renderImageBlock('01-cs/CS-15_customer-register-address.png', 'Formulir Pendaftaran Nasabah - Seksi Alamat & Kontak', '2.4')}

  <ol class="step-list" start="5">
    <li>
      Lengkapi data pelengkap pada bagian <strong>Pekerjaan & Sumber Dana</strong>:
      <ul>
        <li><strong>Pekerjaan / Profesi:</strong> Misal Mahasiswa, Wiraswasta, PNS, Karyawan Swasta.</li>
        <li><strong>Penghasilan Bulanan:</strong> Rentang estimasi pendapatan bulanan.</li>
        <li><strong>Tujuan Hubungan Bisnis:</strong> Penyimpanan dana tabungan atau transaksi perkuliahan.</li>
      </ul>
    </li>
  </ol>

  ${renderImageBlock('01-cs/CS-16_customer-register-other.png', 'Formulir Pendaftaran Nasabah - Seksi Data Finansial & Pekerjaan', '2.5')}

  <ol class="step-list" start="6">
    <li>Periksa kembali seluruh isian formulir untuk memastikan tidak ada kesalahan ketik nama atau NIK.</li>
    <li>Klik tombol <strong>Simpan Data Nasabah</strong> di bagian bawah formulir.</li>
  </ol>

  ${renderImageBlock('01-cs/CS-18_customer-register-success.png', 'Notifikasi Keberhasilan Registrasi CIF Nasabah Baru', '2.6')}

  <div class="callout callout-success">
    <strong>CIF Berhasil Diterbitkan:</strong>
    Setelah formulir disimpan, sistem secara otomatis menerbitkan Nomor CIF unik (contoh: <code>CIF-2026-XXXX</code>). Catat nomor ini untuk diberikan kepada nasabah. Status data nasabah berada dalam antrean peninjauan Supervisor.
  </div>

  <h2 class="section-title">2.4 Skenario 2: Pencarian dan Pemeliharaan Data Nasabah (Customer Inquiry & Edit)</h2>
  <p>
    CS dapat melakukan pencarian data nasabah eksisting berdasarkan nama, NIK, atau nomor CIF melalui kolom pencarian di bagian atas tabel nasabah.
  </p>

  ${renderImageBlock('01-cs/CS-12_customer-list-search.png', 'Fitur Pencarian Nasabah Cepat Berdasarkan Kata Kunci', '2.7')}

  <p>
    Untuk memeriksa profil lengkap nasabah, klik tombol <strong>Detail / View</strong> pada baris nasabah yang bersangkutan. Halaman detail menyajikan data lengkap identitas, daftar rekening yang dimiliki nasabah, dan tanggal registrasi.
  </p>

  ${renderImageBlock('01-cs/CS-19_customer-view.png', 'Tampilan Informasi Profil Nasabah Lengkap (CIF Detail)', '2.8')}

  <p>
    Apabila nasabah melaporkan perubahan nomor telepon, alamat domisili, atau pekerjaan, CS dapat mengklik tombol <strong>Edit</strong> untuk memperbarui data tersebut.
  </p>

  ${renderImageBlock('01-cs/CS-20_customer-edit.png', 'Formulir Pembaruan Data Profil Nasabah (Customer Edit)', '2.9')}

  <h2 class="section-title">2.5 Skenario 3: Pembukaan Rekening Tabungan Baru (Account Opening Flow)</h2>
  <p>
    Setelah nasabah memiliki CIF aktif, langkah berikutnya adalah membuka rekening tabungan.
  </p>

  <h3 class="subsection-title">Langkah Pembukaan Rekening:</h3>
  <ol class="step-list">
    <li>Klik menu <strong>Account</strong> pada sidebar navigasi.</li>
    <li>Sistem menampilkan daftar rekening seluruh nasabah di cabang. Klik tombol <strong>Buka Rekening Baru</strong> (atau akses <code>/account/open</code>).</li>
  </ol>

  ${renderImageBlock('01-cs/CS-21_account-list.png', 'Direktori Rekening Nasabah (Account Directory)', '2.10')}

  <ol class="step-list" start="3">
    <li>
      Sistem menyajikan daftar nasabah yang telah berstatus <code>ACTIVE</code> (lolos verifikasi). Pilih nasabah yang diinginkan dengan mengklik tombol <strong>Pilih Nasabah</strong> pada kolom aksi.
    </li>
  </ol>

  ${renderImageBlock('01-cs/CS-24_account-open-select.png', 'Seleksi Nasabah yang Memenuhi Syarat Pembukaan Rekening', '2.11')}

  <ol class="step-list" start="4">
    <li>
      Pada formulir pembukaan rekening yang muncul, isi parameter berikut:
      <ul>
        <li><strong>Produk Tabungan:</strong> Pilih produk yang diinginkan (misal: <em>Tabungan Mudharabah Reguler</em> atau <em>Tabungan Wadiah Pelajar</em>).</li>
        <li><strong>Nama Rekening:</strong> Judul rekening (biasanya nama nasabah diikuti jenis tabungan).</li>
        <li><strong>Setoran Awal:</strong> Nominal komitmen setoran perdana (wajib memenuhi batas minimum produk).</li>
        <li><strong>Tujuan Pembukaan Rekening:</strong> Contoh: Tabungan Pribadi / Biaya Kuliah.</li>
      </ul>
    </li>
  </ol>

  ${renderImageBlock('01-cs/CS-27_account-open-filled.png', 'Formulir Pengajuan Pembukaan Rekening Tabungan Baru Terisi', '2.12')}

  <ol class="step-list" start="5">
    <li>Klik tombol <strong>Ajukan Pembukaan Rekening</strong>.</li>
    <li>
      Sistem akan memproses pengajuan. Rekening diterbitkan dengan nomor rekening resmi namun berstatus <code>PENDING_APPROVAL</code>.
    </li>
  </ol>

  <div class="callout callout-info">
    <strong>Eskalasi ke Supervisor:</strong>
    Permohonan pembukaan rekening baru langsung masuk ke dalam antrean <strong>Approval Queue</strong> Supervisor. Informasikan kepada nasabah untuk menunggu sejenak hingga supervisor memvalidasi rekening sebelum nasabah melakukan setoran awal di loket Teller.
  </div>

  <h2 class="section-title">2.6 Skenario 4: Monitoring Mutasi dan Riwayat Transaksi Rekening</h2>
  <p>
    Customer Service berhak melihat riwayat transaksi atau mutasi rekening atas permintaan nasabah resmi (misal untuk cetak rekening koran atau pencocokan saldo).
  </p>

  ${renderImageBlock('01-cs/CS-29_account-view.png', 'Informasi Detail Rekening, Saldo Terkini, dan Status Rekening', '2.13')}

  <p>
    Klik tombol <strong>Lihat Mutasi</strong> pada halaman detail rekening untuk menampilkan rincian debit/kredit, tanggal transaksi, kode transaksi, dan saldo akhir tiap mutasi. CS juga dapat menyaring mutasi berdasarkan rentang tanggal tertentu.
  </p>

  ${renderImageBlock('01-cs/CS-30_account-transactions.png', 'Laporan Mutasi Rekening Nasabah (Account Statement)', '2.14')}

  <h2 class="section-title">2.7 Skenario 5: Penutupan Rekening Nasabah (Account Closure)</h2>
  <p>
    Jika nasabah mengajukan penutupan rekening secara tertulis (karena telah lulus studi atau tidak lagi membutuhkan layanan simpanan):
  </p>
  <ol class="step-list">
    <li>Pastikan saldo nasabah telah ditarik hingga bernilai nol di loket Teller.</li>
    <li>Buka detail rekening di menu <strong>Account</strong>.</li>
    <li>Klik tombol <strong>Tutup Rekening</strong> di bagian bawah detail rekening.</li>
    <li>Masukkan alasan penutupan rekening secara jelas (misal: "Permintaan nasabah - Selesai Masa Studi").</li>
    <li>Klik tombol <strong>Konfirmasi Penutupan Rekening</strong>. Rekening akan berubah status menjadi <code>CLOSED</code>.</li>
  </ol>

  ${renderImageBlock('01-cs/CS-32_account-close.png', 'Halaman Konfirmasi Penutupan Rekening Nasabah', '2.15')}

  <h2 class="section-title">2.8 Skenario 6: Eksplorasi & Pembuatan Katalog Produk Tabungan Syariah</h2>
  <p>
    Menu <strong>Product</strong> memungkinkan CS memahami karakteristik setiap produk simpanan syariah yang ditawarkan kepada nasabah (akad, nisbah bagi hasil, setoran minimum, dan biaya administrasi).
  </p>

  ${renderImageBlock('01-cs/CS-03_product-list.png', 'Katalog Produk Tabungan Syariah Tazkia Minibank', '2.16')}

  <p>
    CS dapat mengklik tombol <strong>Create Product</strong> jika diberikan wewenang untuk merancang simulasi produk tabungan baru dengan skema syariah kustom.
  </p>

  ${renderImageBlock('01-cs/CS-07_product-create-filled.png', 'Formulir Konfigurasi Parameter Produk Tabungan Baru', '2.17')}
  ` : ''}

  ${isTeller ? `
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- BAB 3: TELLER -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">BAB 3: Panduan Operasional Teller</h1>

  <h2 class="section-title">3.1 Profil Peran & Navigasi Sidebar Teller</h2>
  <p>
    <strong>Teller</strong> adalah petugas operasional kasir bank yang menangani transaksi penerimaan dan penyetoran fisik uang tunai, penarikan dana tabungan nasabah, transfer dana antar rekening, serta layanan pencetakan fisik buku tabungan (passbook).
  </p>
  <p>Sidebar Teller dirancang efisien dan fokus pada kecepatan layanan kasir:</p>
  <ul>
    <li><strong>Dashboard:</strong> Ringkasan transaksi harian teller, posisi kas masuk/keluar, dan status shift kasir.</li>
    <li><strong>Transaction:</strong> Akses cepat layanan Setoran Tunai (Cash Deposit), Penarikan Tunai (Cash Withdrawal), dan Pemindahbukuan (Transfer).</li>
    <li><strong>Passbook:</strong> Layanan inisialisasi buku tabungan baru, pencetakan lembar identitas, dan cetak mutasi buku tabungan.</li>
    <li><strong>Settings & Logout:</strong> Informasi user teller aktif dan keluar sesi kasir.</li>
  </ul>

  <h2 class="section-title">3.2 Dashboard Operasional Teller</h2>
  <p>
    Dashboard Teller menyajikan metrik transaksi real-time hari berjalan, membantu teller memastikan kesesuaian fisik kas dengan catatan sistem (<em>balance reconciliation</em>).
  </p>

  ${renderImageBlock('02-teller/TL-01_dashboard.png', 'Antarmuka Dashboard Kasir Operasional Teller', '3.1')}

  <h2 class="section-title">3.3 Skenario 1: Transaksi Setoran Tunai (Cash Deposit)</h2>
  <p>
    Setoran tunai dapat berupa setoran awal pembukaan rekening baru atau setoran rutin tabungan nasabah.
  </p>

  <h3 class="subsection-title">Langkah-Langkah Setoran Tunai:</h3>
  <ol class="step-list">
    <li>Klik menu <strong>Transaction</strong> pada sidebar, lalu pilih sub-menu atau tombol <strong>Setoran Tunai (Cash Deposit)</strong>.</li>
    <li>Sistem menampilkan daftar rekening aktif. Cari nomor rekening atau nama nasabah melalui kolom pencarian.</li>
  </ol>

  ${renderImageBlock('02-teller/TL-04_deposit-select.png', 'Layar Seleksi Rekening Tujuan Setoran Tunai', '3.2')}

  <ol class="step-list" start="3">
    <li>
      Klik tombol <strong>Pilih / Setor</strong> pada baris rekening nasabah. Sistem membuka formulir transaksi setoran tunai.
    </li>
    <li>
      Hitung uang fisik yang diserahkan nasabah. Masukkan <strong>Nominal Setoran</strong> pada kolom yang tersedia (misal: <code>Rp 1.000.000</code>).
    </li>
    <li>Isi kolom <strong>Keterangan Transaksi</strong> (misal: "Setoran Tunai Tabungan Bulanan").</li>
  </ol>

  ${renderImageBlock('02-teller/TL-07_deposit-form-filled.png', 'Formulir Transaksi Setoran Tunai dengan Nominal & Keterangan', '3.3')}

  <ol class="step-list" start="6">
    <li>Pastikan saldo sebelum transaksi dan saldo setelah transaksi yang dikalkulasi sistem telah sesuai.</li>
    <li>Klik tombol <strong>Proses Setoran</strong>. Sistem akan membukukan transaksi secara instan ke rekening nasabah.</li>
  </ol>

  <div class="callout callout-success">
    <strong>Setoran Berhasil Dibukukan:</strong>
    Saldo rekening nasabah bertambah secara real-time. Teller dapat langsung mencetak slip bukti setoran dan memberikan tanda terima kepada nasabah.
  </div>

  <h2 class="section-title">3.4 Skenario 2: Transaksi Penarikan Tunai (Cash Withdrawal)</h2>
  <p>
    Penarikan tunai dilakukan ketika nasabah ingin mengambil uang tunai dari rekening tabungannya di loket bank.
  </p>

  <h3 class="subsection-title">Langkah-Langkah Penarikan Tunai:</h3>
  <ol class="step-list">
    <li>Verifikasi identitas nasabah dan buku tabungan yang dibawa.</li>
    <li>Klik tombol <strong>Penarikan Tunai (Cash Withdrawal)</strong> pada menu transaksi.</li>
    <li>Cari dan pilih nomor rekening nasabah yang akan didebit.</li>
  </ol>

  ${renderImageBlock('02-teller/TL-10_withdrawal-select.png', 'Layar Seleksi Rekening Sumber Penarikan Tunai', '3.4')}

  <ol class="step-list" start="4">
    <li>Sistem menampilkan saldo efektif yang dapat ditarik nasabah (dengan memperhitungkan saldo mengendap minimum).</li>
    <li>Ketikkan <strong>Nominal Penarikan</strong> dan <strong>Keterangan</strong>.</li>
  </ol>

  ${renderImageBlock('02-teller/TL-12_withdrawal-form-filled.png', 'Formulir Penarikan Tunai dengan Validasi Saldo', '3.5')}

  <ol class="step-list" start="6">
    <li>Klik <strong>Proses Penarikan</strong>. Serahkan uang tunai fisik yang telah dihitung rapi bersama slip bukti penarikan kepada nasabah.</li>
  </ol>

  <div class="callout callout-warning">
    <strong>Batas Limit Teller:</strong>
    Penarikan di atas limit kewenangan teller otomatis akan dialihkan ke layar otorisasi Supervisor. Transaksi baru dapat diproses setelah disetujui supervisor cabang.
  </div>

  <h2 class="section-title">3.5 Skenario 3: Pemindahbukuan / Transfer Antar Rekening (3-Step Flow)</h2>
  <p>
    Tazkia Minibank mengadopsi alur transfer 3-langkah (<em>3-Step Transfer Flow</em>) untuk mencegah kesalahan transfer antar nasabah:
  </p>

  <h3 class="subsection-title">Tahap 1: Pemilihan Rekening Sumber (Source Account)</h3>
  <p>Teller memilih rekening nasabah yang akan didebit (pengirim dana).</p>
  ${renderImageBlock('02-teller/TL-15_transfer-step1-source.png', 'Transfer Tahap 1: Memilih Rekening Sumber Dana', '3.6')}

  <h3 class="subsection-title">Tahap 2: Pemilihan Rekening Tujuan (Target Account)</h3>
  <p>Teller memilih rekening nasabah yang akan dikredit (penerima dana). Sistem secara cerdas memfilter agar rekening sumber tidak muncul sebagai pilihan tujuan.</p>
  ${renderImageBlock('02-teller/TL-16_transfer-step2-target.png', 'Transfer Tahap 2: Memilih Rekening Penerima Transfer', '3.7')}

  <h3 class="subsection-title">Tahap 3: Konfirmasi Nominal & Eksekusi Transfer</h3>
  <p>Teller memasukkan nominal transfer dan keterangan. Sistem menampilkan ringkasan nama pengirim dan penerima untuk diverifikasi nasabah sebelum tombol kirim ditekan.</p>
  ${renderImageBlock('02-teller/TL-18_transfer-step3-filled.png', 'Transfer Tahap 3: Konfirmasi dan Eksekusi Pemindahbukuan', '3.8')}

  <h2 class="section-title">3.6 Skenario 4: Layanan Buku Tabungan (Passbook Printing & Booklet)</h2>
  <p>
    Sistem Minibank mendukung pencetakan buku tabungan fisik secara presisi untuk keperluan latihan simulasi bank.
  </p>

  <h3 class="subsection-title">1. Seleksi Rekening Buku Tabungan:</h3>
  <p>Klik menu <strong>Passbook</strong> di sidebar, kemudian pilih rekening nasabah yang ingin dicetak bukunya.</p>
  ${renderImageBlock('02-teller/TL-23_passbook-select.png', 'Pencarian & Seleksi Rekening untuk Layanan Buku Tabungan', '3.9')}

  <h3 class="subsection-title">2. Inisialisasi Buku Tabungan Baru:</h3>
  <p>Untuk nasabah yang baru membuka rekening, lakukan inisialisasi nomor seri buku tabungan dan nomor baris awal pencetakan.</p>
  ${renderImageBlock('02-teller/TL-26_passbook-initialize.png', 'Formulir Inisialisasi Nomor Seri Buku Tabungan Baru', '3.10')}

  <h3 class="subsection-title">3. Cetak Lembar Identitas Nasabah:</h3>
  <p>Digunakan untuk mencetak cover bagian dalam buku tabungan yang memuat Nomor CIF, Nama, Alamat, dan Kantor Cabang.</p>
  ${renderImageBlock('02-teller/TL-28_passbook-identity.png', 'Format Cetak Halaman Identitas Buku Tabungan Nasabah', '3.11')}

  <h3 class="subsection-title">4. Cetak Baris Transaksi (Booklet Full View):</h3>
  <p>Mencetak baris mutasi debit, kredit, dan saldo mutakhir pada kisi-kisi buku tabungan fisik sesuai nomor baris terakhir.</p>
  ${renderImageBlock('02-teller/TL-29_passbook-booklet.png', 'Tampilan Kisi-Kisi Baris Cetak Buku Tabungan (Booklet View)', '3.12')}
  ` : ''}

  ${isSpv ? `
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- BAB 4: SUPERVISOR & BRANCH MANAGER -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">BAB 4: Panduan Operasional Supervisor & Branch Manager</h1>

  <h2 class="section-title">4.1 Profil Peran Supervisor & Branch Manager</h2>
  <p>
    <strong>Supervisor / Branch Manager</strong> memegang fungsi kontrol dan pengawasan tertinggi di kantor cabang. Supervisor bertindak sebagai verifikator akhir (<em>Checker & Approver</em>) yang mengesahkan registrasi nasabah, pembukaan rekening baru, serta menyetujui transaksi operasional yang melebihi limit wewenang staf.
  </p>
  <p>Menu khusus yang dimiliki Supervisor mencakup:</p>
  <ul>
    <li><strong>Approval Queue:</strong> Antrean otorisasi satu pintu untuk persetujuan CIF, Rekening, dan Transaksi Keuangan.</li>
    <li><strong>Reports:</strong> Modul laporan keuangan bank komprehensif (Harian, Bulanan, dan Tahunan).</li>
    <li><strong>User Activities (Audit Trail):</strong> Jejak rekam aktivitas setiap staf dan nasabah untuk keperluan kepatuhan (compliance).</li>
    <li><strong>Users:</strong> Pengawasan akun staf dan layanan bantuan reset password internal cabang.</li>
  </ul>

  <h2 class="section-title">4.2 Dashboard Supervisi & Antrean Otorisasi</h2>
  <p>
    Dashboard Supervisor langsung menonjolkan widget <strong>Pending Approval</strong> yang memerlukan tindakan segera dari pejabat bank.
  </p>

  ${renderImageBlock('03-supervisor/SV-01_dashboard.png', 'Dashboard Eksekutif Supervisor & Pemantauan Operasional Cabang', '4.1')}

  <h2 class="section-title">4.3 Skenario 1: Otorisasi Pendaftaran Nasabah Baru (CIF Approval)</h2>
  <p>
    Ketika CS mendaftarkan nasabah baru, nasabah tersebut masuk ke antrean <code>/supervisor/nasabah</code>.
  </p>
  <ol class="step-list">
    <li>Buka menu <strong>Approval Queue &gt; Nasabah</strong>.</li>
    <li>Sistem menyajikan daftar permohonan CIF berstatus <code>PENDING</code>.</li>
  </ol>

  ${renderImageBlock('03-supervisor/SV-02_nasabah-queue.png', 'Daftar Antrean Permohonan Nasabah Baru yang Menunggu Persetujuan', '4.2')}

  <ol class="step-list" start="3">
    <li>Klik pada baris nasabah untuk menelaah dokumen dan keabsahan NIK.</li>
    <li>
      Jika data valid, klik tombol hijau <strong>Approve Nasabah</strong>. Status nasabah seketika berubah menjadi <code>ACTIVE</code>.
    </li>
    <li>Jika terdapat kejanggalan dokumen, klik tombol merah <strong>Reject</strong> dengan menyertakan catatan perbaikan untuk CS.</li>
  </ol>

  ${renderImageBlock('03-supervisor/SV-05_nasabah-detail.png', 'Layar Penelaahan Dokumen Identitas Nasabah (Review CIF)', '4.3')}

  <h2 class="section-title">4.4 Skenario 2: Otorisasi Pembukaan Rekening Baru (Account Approval)</h2>
  <p>
    Permohonan rekening baru yang diajukan oleh CS ditinjau melalui antrean <code>/supervisor/account</code>:
  </p>
  <ol class="step-list">
    <li>Buka antrean rekening pending approval.</li>
    <li>Periksa kesesuaian produk tabungan dengan akad syariah yang dipilih dan komitmen setoran awal.</li>
    <li>Klik tombol <strong>Approve Rekening</strong>. Rekening aktif secara resmi dan siap menerima setoran di loket teller.</li>
  </ol>

  ${renderImageBlock('03-supervisor/SV-09_account-queue.png', 'Antrean Pengesahan Rekening Tabungan Baru (Account Approval Queue)', '4.4')}

  <h2 class="section-title">4.5 Skenario 3: Laporan Keuangan Harian, Bulanan, dan Tahunan (Financial Reports)</h2>
  <p>
    Supervisor bertanggung jawab atas rekapitulasi kinerja kas kantor cabang. Modul <strong>Reports</strong> diakses melalui <code>/reports</code>:
  </p>

  ${renderImageBlock('03-supervisor/SV-16_reports-index.png', 'Modul Pelaporan Keuangan dan Rekapitulasi Kas Kantor Cabang', '4.5')}

  <ul>
    <li><strong>Laporan Harian (Daily Report):</strong> Ringkasan total uang masuk, total penarikan, dan saldo akhir kas cabang per hari.</li>
    <li><strong>Laporan Bulanan (Monthly Report):</strong> Agregasi transaksi untuk evaluasi target tabungan dan perhitungan bagi hasil nasabah.</li>
    <li><strong>Laporan Tahunan (Yearly Report):</strong> Audit tahunan perputaran dana tabungan syariah.</li>
  </ul>

  <h2 class="section-title">4.6 Skenario 4: Pengawasan Log Aktivitas & Jejak Audit (Audit Trail)</h2>
  <p>
    Menu <strong>User Activities</strong> (<code>/audit/activities</code>) mencatat setiap klik, aksi login, pembuatan rekening, otorisasi, dan transaksi finansial lengkap dengan waktu (timestamp) dan alamat IP staf. Data ini dapat diekspor ke format CSV untuk keperluan audit kepatuhan syariah dan internal audit.
  </p>

  ${renderImageBlock('03-supervisor/SV-23_audit-activities.png', 'Log Rekam Jejak Audit Aktivitas Pengguna (Audit Trail Compliance)', '4.6')}
  ` : ''}

  ${isAdmin ? `
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- BAB 5: ADMINISTRATOR -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">BAB 5: Panduan Administrator Sistem (System Administrator)</h1>

  <h2 class="section-title">5.1 Profil Peran & Struktur Navigasi Administrator</h2>
  <p>
    <strong>Administrator</strong> memiliki hak akses tertinggi di seluruh platform Tazkia Minibank. Admin bertanggung jawab atas tata kelola pengguna (<em>User Management</em>), struktur peran (<em>Role-Based Access Control</em>), direktori hak akses (<em>Permissions Directory</em>), persetujuan registrasi pengguna baru, serta memiliki kapabilitas pengawasan lintas modul.
  </p>

  ${renderImageBlock('04-admin/AD-01_dashboard.png', 'Dashboard Komprehensif Administrator Sistem Tazkia Minibank', '5.1')}

  <h2 class="section-title">5.2 Skenario 1: Tata Kelola Pengguna Sistem (User Management Lifecycle)</h2>
  <p>
    Manajemen pengguna diakses melalui menu <code>/rbac/users/list</code>:
  </p>

  ${renderImageBlock('04-admin/AD-03_users-list.png', 'Direktori Pengguna Sistem, Status Akun, dan Penugasan Peran', '5.2')}

  <h3 class="subsection-title">Langkah Membuat Akun Pengguna Baru:</h3>
  <ol class="step-list">
    <li>Klik tombol <strong>Create User</strong> pada halaman daftar pengguna.</li>
    <li>Isi Username, Nama Lengkap Pegawai, Email Institusi, dan Password awal.</li>
    <li>Tentukan kantor cabang penugasan (misal: <em>Kantor Cabang Jakarta Timur</em> atau <em>Kantor Pusat</em>).</li>
    <li>Simpan data pengguna baru.</li>
  </ol>

  ${renderImageBlock('04-admin/AD-07_users-create-filled.png', 'Formulir Pembuatan Akun Pegawai Bank Baru oleh Administrator', '5.3')}

  <h3 class="subsection-title">Pemberian Peran (Assign Role):</h3>
  <p>
    Buka profil user melalui menu <code>View</code>, kemudian klik tab <strong>Manage Roles</strong>. Admin dapat menyematkan satu atau lebih peran (misal: <code>ROLE_CS</code>, <code>ROLE_TELLER</code>, <code>ROLE_SUPERVISOR</code>) sesuai penugasan kerja staf di laboratorium.
  </p>

  ${renderImageBlock('04-admin/AD-10_users-roles.png', 'Antarmuka Penugasan Hak Peran Pegawai (Role Assignment)', '5.4')}

  <h2 class="section-title">5.3 Skenario 2: Manajemen Peran Sistem & Pemetaan Hak Akses (Role Management)</h2>
  <p>
    Admin dapat mengelola definisi peran pada menu <code>/rbac/roles/list</code>:
  </p>

  ${renderImageBlock('04-admin/AD-14_roles-list.png', 'Daftar Peran Sistem (RBAC Roles Catalog)', '5.5')}

  <p>
    Pada setiap peran, Admin dapat memetakan hak akses granular (seperti <code>CUSTOMER_CREATE</code>, <code>TRANSACTION_DEPOSIT</code>, dsb) secara dinamis tanpa perlu mengubah baris kode program.
  </p>

  ${renderImageBlock('04-admin/AD-18_roles-permissions.png', 'Matriks Pemetaan Permissions ke dalam Peran Tertentu', '5.6')}

  <h2 class="section-title">5.4 Skenario 3: Manajemen Hak Akses Granular (Granular Permissions Directory)</h2>
  <p>
    Katalog seluruh wewenang fungsi di sistem terdokumentasi pada menu <code>/rbac/permissions/list</code>. Terdapat 26 hak akses granular yang terbagi ke dalam 9 kategori (CUSTOMER, ACCOUNT, TRANSACTION, APPROVAL, REPORT, AUDIT, PRODUCT, USER, BRANCH).
  </p>

  ${renderImageBlock('04-admin/AD-20_permissions-list.png', 'Direktori Seluruh Hak Akses Granular Sistem Perbankan', '5.7')}

  <h2 class="section-title">5.5 Skenario 4: Pengawasan Lintas Modul (Full-Access Supervision)</h2>
  <p>
    Administrator diberikan izin otomatis untuk meninjau seluruh modul operasional bank (Product, Customer, Account, Transaction, Passbook, dan Reports) untuk keperluan supervisi teknis dan audit integritas sistem.
  </p>
  ` : ''}

  ${isMaster ? `
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <!-- BAB 6: CROSS-ROLE WORKFLOWS -->
  <!-- ═════════════════════════════════════════════════════════════════════════ -->
  <div class="page-break"></div>
  <h1 class="chapter-title">BAB 6: Skenario Lengkap End-to-End (Cross-Role Workflows)</h1>

  <h2 class="section-title">6.1 Alur Hidup Nasabah & Rekening: Dari Registrasi Hingga Transaksi Perdana</h2>
  <p>
    Berikut adalah skenario praktikum perbankan terpadu yang menghubungkan seluruh peran dalam satu alur kerja harmonis:
  </p>

  <ol class="step-list">
    <li>
      <strong>Customer Service:</strong> Mendaftarkan calon nasabah baru dengan menginput NIK dan identitas diri. Nasabah memperoleh Nomor CIF berstatus <code>PENDING</code>.
    </li>
    <li>
      <strong>Supervisor:</strong> Membuka antrean <em>Approval Queue &gt; Nasabah</em>, memeriksa validitas berkas, dan menyetujui (<em>Approve</em>) CIF nasabah menjadi <code>ACTIVE</code>.
    </li>
    <li>
      <strong>Customer Service:</strong> Mengajukan pembukaan rekening tabungan Mudharabah untuk nasabah tersebut dengan komitmen setoran awal Rp 500.000. Rekening berstatus <code>PENDING_APPROVAL</code>.
    </li>
    <li>
      <strong>Supervisor:</strong> Membuka antrean <em>Approval Queue &gt; Rekening</em>, memverifikasi akad syariah, dan menyetujui (<em>Approve</em>) pembukaan rekening menjadi <code>ACTIVE</code>.
    </li>
    <li>
      <strong>Teller:</strong> Menerima uang tunai fisik Rp 500.000 dari nasabah di loket kasir, memproses transaksi <strong>Setoran Tunai (Cash Deposit)</strong> ke rekening nasabah, dan mencetak slip setoran.
    </li>
    <li>
      <strong>Teller:</strong> Melakukan <strong>Inisialisasi Buku Tabungan</strong>, mencetak lembar identitas nasabah, dan mencetak baris mutasi setoran awal pada buku tabungan fisik.
    </li>
    <li>
      <strong>Supervisor & Admin:</strong> Memantau rekapitulasi saldo kas pada <strong>Laporan Harian</strong> serta memastikan seluruh proses tercatat sempurna di <strong>Log Audit Aktivitas</strong>.
    </li>
  </ol>

  <div class="callout callout-success">
    <strong>Simulasi Perbankan Tuntas:</strong>
    Dengan menyelesaikan alur kerja di atas, seluruh fungsi utama perbankan syariah ritel telah berhasil dijalankan sesuai standar tata kelola perbankan yang sehat (<em>Good Corporate Governance</em>).
  </div>
  ` : ''}

</body>
</html>`;
}

// Convert HTML to PDF using Playwright Chromium
export async function generatePdf(outputPath: string, roleFilter?: 'CS' | 'TELLER' | 'SUPERVISOR' | 'ADMIN') {
  console.log(`\n📄 Generating PDF: ${path.basename(outputPath)}...`);
  const htmlContent = buildHtml(roleFilter);

  const tempHtmlPath = path.resolve(DOCS_DIR, `temp-${roleFilter || 'all'}.html`);
  fs.writeFileSync(tempHtmlPath, htmlContent, 'utf-8');

  const browser = await chromium.launch({ headless: true });
  try {
    const context = await browser.newContext();
    const page = await context.newPage();

    await page.goto(`file://${tempHtmlPath}`, { waitUntil: 'networkidle' });
    await page.waitForTimeout(1000);

    await page.pdf({
      path: outputPath,
      format: 'A4',
      printBackground: true,
      margin: {
        top: '15mm',
        bottom: '18mm',
        left: '12mm',
        right: '12mm',
      },
      displayHeaderFooter: true,
      headerTemplate: `
        <div style="font-size: 8pt; color: #94a3b8; width: 100%; padding: 0 12mm; display: flex; justify-content: space-between; font-family: sans-serif;">
          <span>Tazkia Minibank Core Banking System — Buku Panduan Resmi</span>
          <span>Pedoman Operasional Perbankan</span>
        </div>
      `,
      footerTemplate: `
        <div style="font-size: 8pt; color: #94a3b8; width: 100%; padding: 0 12mm; display: flex; justify-content: space-between; font-family: sans-serif;">
          <span>Laboratorium Bank Mini Syariah Institut Tazkia</span>
          <span>Halaman <span class="pageNumber"></span> dari <span class="totalPages"></span></span>
        </div>
      `,
    });

    console.log(`✅ Berhasil dibuat: ${outputPath} (${(fs.statSync(outputPath).size / 1024 / 1024).toFixed(2)} MB)`);
  } finally {
    await browser.close();
    if (fs.existsSync(tempHtmlPath)) {
      fs.unlinkSync(tempHtmlPath);
    }
  }
}

async function main() {
  console.log('╔══════════════════════════════════════════════════════╗');
  console.log('║  MINIBANK TUTORIAL — PDF BUNDLER                    ║');
  console.log('║  Compiling HTML & Embedded Screenshots to PDF        ║');
  console.log('╚══════════════════════════════════════════════════════╝');

  fs.mkdirSync(DOCS_DIR, { recursive: true });

  // 1. Master PDF (All Roles)
  await generatePdf(path.resolve(DOCS_DIR, 'panduan-minibank-lengkap.pdf'));

  // 2. CS Specialist PDF
  await generatePdf(path.resolve(DOCS_DIR, 'panduan-minibank-cs.pdf'), 'CS');

  // 3. Teller Specialist PDF
  await generatePdf(path.resolve(DOCS_DIR, 'panduan-minibank-teller.pdf'), 'TELLER');

  // 4. Supervisor Specialist PDF
  await generatePdf(path.resolve(DOCS_DIR, 'panduan-minibank-supervisor.pdf'), 'SUPERVISOR');

  // 5. Admin Specialist PDF
  await generatePdf(path.resolve(DOCS_DIR, 'panduan-minibank-admin.pdf'), 'ADMIN');

  console.log('\n🎉 ALL PDF MANUALS GENERATED SUCCESSFULLY!');
}

main().catch(err => {
  console.error('❌ PDF Generation Error:', err);
  process.exit(1);
});

