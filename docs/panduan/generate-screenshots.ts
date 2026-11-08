/**
 * =============================================================================
 * MINIBANK TUTORIAL PDF — SCREENSHOT GENERATOR
 * =============================================================================
 * Captures ~100 screenshots across ALL roles (CS, Teller, Supervisor, Admin)
 * for the complete Tazkia Minibank User Manual.
 *
 * Usage:
 *   cd c:\project-minibank\playwright-tests
 *   npx playwright test ../docs/panduan/generate-screenshots.ts --project=chromium-no-auth --timeout=300000
 *
 * Or run directly:
 *   npx tsx ../docs/panduan/generate-screenshots.ts
 * =============================================================================
 */
import { chromium, Browser, Page, BrowserContext } from 'playwright';
import * as path from 'path';
import * as fs from 'fs';

const BASE_URL = 'http://localhost:8080';
const SCREENSHOT_DIR = path.resolve(__dirname, 'screenshots');
const VIEWPORT = { width: 1280, height: 800 };

// Ensure directories exist
function ensureDir(dir: string) {
  fs.mkdirSync(dir, { recursive: true });
}

// Wait a bit for page transitions and animations
async function settle(page: Page, ms = 800) {
  await page.waitForLoadState('networkidle').catch(() => {});
  await page.waitForTimeout(ms);
}

// Take a screenshot with nice naming
async function snap(page: Page, folder: string, name: string, fullPage = true) {
  const dir = path.join(SCREENSHOT_DIR, folder);
  ensureDir(dir);
  const filePath = path.join(dir, `${name}.png`);
  await page.screenshot({ path: filePath, fullPage });
  console.log(`  📸 ${folder}/${name}.png`);
}

// Login helper — returns authenticated page
async function login(browser: Browser, username: string, password: string, module?: string): Promise<{ page: Page; context: BrowserContext }> {
  const context = await browser.newContext({ viewport: VIEWPORT });
  const page = await context.newPage();

  await page.goto(`${BASE_URL}/login`);
  await page.waitForLoadState('networkidle');
  await page.fill('input[name="username"]', username);
  await page.fill('input[name="password"]', password);

  // Try module select if visible
  if (module) {
    const moduleSelect = page.locator('select[name="module"]');
    if (await moduleSelect.isVisible().catch(() => false)) {
      await moduleSelect.selectOption(module);
    }
  }

  await page.click('button[type="submit"]');
  await page.waitForURL('**/dashboard', { timeout: 15000 });
  await settle(page);
  return { page, context };
}

// ═══════════════════════════════════════════════════════════════════════════════
// PHASE 0: COMMON PAGES (no auth)
// ═══════════════════════════════════════════════════════════════════════════════
async function phase0_common(browser: Browser) {
  console.log('\n🏠 FASE 0: Halaman Umum (Common)');
  const context = await browser.newContext({ viewport: VIEWPORT });
  const page = await context.newPage();

  // C-01: Landing Page
  await page.goto(BASE_URL);
  await settle(page);
  await snap(page, '00-common', 'C-01_landing-page');

  // C-02: Login Page
  await page.goto(`${BASE_URL}/login`);
  await settle(page);
  await snap(page, '00-common', 'C-02_login-page');

  // C-03: Login Error
  await page.fill('input[name="username"]', 'wronguser');
  await page.fill('input[name="password"]', 'wrongpass');
  await page.click('button[type="submit"]');
  await settle(page, 1200);
  await snap(page, '00-common', 'C-03_login-error');

  // C-04: Signup Page
  await page.goto(`${BASE_URL}/signup`);
  await settle(page);
  await snap(page, '00-common', 'C-04_signup-page');

  await context.close();
}

// ═══════════════════════════════════════════════════════════════════════════════
// PHASE 1: CUSTOMER SERVICE (CS)
// ═══════════════════════════════════════════════════════════════════════════════
async function phase1_cs(browser: Browser) {
  console.log('\n👤 FASE 1: Customer Service (CS) — login as cs1');
  // Try cs1/minibank123 first, fallback to zann/zann
  let session: { page: Page; context: BrowserContext };
  try {
    session = await login(browser, 'cs1', 'minibank123', 'CS');
  } catch {
    session = await login(browser, 'zann', 'zann', 'CS');
  }
  const { page, context } = session;

  // ── 1.1 Dashboard ──
  console.log('  📂 Dashboard CS');
  await snap(page, '01-cs', 'CS-01_dashboard');

  // ── 1.2 Product ──
  console.log('  📂 Produk');
  await page.goto(`${BASE_URL}/product/list`);
  await settle(page);
  await snap(page, '01-cs', 'CS-03_product-list');

  // Search product
  const prodSearchInput = page.locator('input[name="q"], input[name="search"], input[placeholder*="earch"], input[placeholder*="cari"]').first();
  if (await prodSearchInput.isVisible().catch(() => false)) {
    await prodSearchInput.fill('wadiah');
    // Try submit
    const searchBtn = page.locator('button[type="submit"], button:has-text("Search"), button:has-text("Cari")').first();
    if (await searchBtn.isVisible().catch(() => false)) {
      await searchBtn.click();
    } else {
      await prodSearchInput.press('Enter');
    }
    await settle(page);
    await snap(page, '01-cs', 'CS-04_product-list-search');
    // Clear search
    await page.goto(`${BASE_URL}/cs/product`);
    await settle(page);
  }

  // Create Product form
  await page.goto(`${BASE_URL}/cs/product/new`);
  await settle(page);
  await snap(page, '01-cs', 'CS-06_product-create-form');

  // Fill product form
  const prodNameField = page.locator('input[name="productName"], input[id="productName"]').first();
  if (await prodNameField.isVisible().catch(() => false)) {
    await prodNameField.fill('Tabungan Wadiah Pelajar');
  }
  const prodCodeField = page.locator('input[name="productCode"], input[id="productCode"]').first();
  if (await prodCodeField.isVisible().catch(() => false)) {
    await prodCodeField.fill('TWP-001');
  }
  await snap(page, '01-cs', 'CS-07_product-create-filled');

  // View existing product
  await page.goto(`${BASE_URL}/cs/product`);
  await settle(page);
  const firstProductLink = page.locator('table tbody tr:first-child a, .card a[href*="/cs/product/"]').first();
  if (await firstProductLink.isVisible().catch(() => false)) {
    await firstProductLink.click();
    await settle(page);
    await snap(page, '01-cs', 'CS-08_product-detail');

    // Edit product
    const editBtn = page.locator('a:has-text("Edit"), button:has-text("Edit"), a[href*="edit"]').first();
    if (await editBtn.isVisible().catch(() => false)) {
      await editBtn.click();
      await settle(page);
      await snap(page, '01-cs', 'CS-09_product-edit');
    }
  }

  // ── 1.3 Customer / Nasabah ──
  console.log('  📂 Customer / Nasabah');
  await page.goto(`${BASE_URL}/customer/list`);
  await settle(page);
  await snap(page, '01-cs', 'CS-11_customer-list');

  // Search nasabah
  const custSearchInput = page.locator('input[name="q"], input[name="search"], input[placeholder*="earch"], input[placeholder*="cari"]').first();
  if (await custSearchInput.isVisible().catch(() => false)) {
    await custSearchInput.fill('test');
    const searchBtn = page.locator('button[type="submit"], button:has-text("Search"), button:has-text("Cari")').first();
    if (await searchBtn.isVisible().catch(() => false)) {
      await searchBtn.click();
    } else {
      await custSearchInput.press('Enter');
    }
    await settle(page);
    await snap(page, '01-cs', 'CS-12_customer-list-search');
  }

  // Registration form
  await page.goto(`${BASE_URL}/cs/customers/new`);
  await settle(page);
  await snap(page, '01-cs', 'CS-13_customer-register-empty');

  // Fill registration form — section by section
  // Identity section
  const nikField = page.locator('input[name="nik"], input[id="nik"]').first();
  if (await nikField.isVisible().catch(() => false)) {
    await nikField.fill('3201234567890001');
  }
  const namaField = page.locator('input[name="namaSesuaiIdentitas"], input[id="namaSesuaiIdentitas"], input[name="namaLengkap"]').first();
  if (await namaField.isVisible().catch(() => false)) {
    await namaField.fill('AHMAD FAUZI RAHMAN');
  }
  // Try tempatLahir
  const tempatLahirField = page.locator('input[name="tempatLahir"]').first();
  if (await tempatLahirField.isVisible().catch(() => false)) {
    await tempatLahirField.fill('JAKARTA');
  }
  // Try tanggalLahir
  const tglLahirField = page.locator('input[name="tanggalLahir"]').first();
  if (await tglLahirField.isVisible().catch(() => false)) {
    await tglLahirField.fill('1990-05-15');
  }
  // Try gender
  const genderSelect = page.locator('select[name="jenisKelamin"]').first();
  if (await genderSelect.isVisible().catch(() => false)) {
    await genderSelect.selectOption({ index: 1 });
  }
  await snap(page, '01-cs', 'CS-14_customer-register-identity');

  // Address section
  const alamatField = page.locator('input[name="alamat"], textarea[name="alamat"]').first();
  if (await alamatField.isVisible().catch(() => false)) {
    await alamatField.fill('JL. MERDEKA NO. 10 RT 001/002');
  }
  const noHpField = page.locator('input[name="nomorHp"], input[name="noHp"]').first();
  if (await noHpField.isVisible().catch(() => false)) {
    await noHpField.fill('081234567890');
  }
  const emailField = page.locator('input[name="email"]').first();
  if (await emailField.isVisible().catch(() => false)) {
    await emailField.fill('ahmad.fauzi@gmail.com');
  }
  // Scroll down for address section
  await page.evaluate(() => window.scrollTo(0, document.body.scrollHeight / 2));
  await settle(page, 400);
  await snap(page, '01-cs', 'CS-15_customer-register-address');

  // Scroll to bottom and take full form
  await page.evaluate(() => window.scrollTo(0, document.body.scrollHeight));
  await settle(page, 400);
  await snap(page, '01-cs', 'CS-16_customer-register-other');

  // Full page filled
  await page.evaluate(() => window.scrollTo(0, 0));
  await settle(page, 300);
  await snap(page, '01-cs', 'CS-17_customer-register-filled', true);

  // Submit registration
  const submitBtn = page.locator('button[type="submit"]:has-text("Simpan"), button[type="submit"]:has-text("Daftar"), button[type="submit"]').first();
  if (await submitBtn.isVisible().catch(() => false)) {
    await submitBtn.click();
    await settle(page, 1500);
    await snap(page, '01-cs', 'CS-18_customer-register-success');
  }

  // View nasabah detail
  await page.goto(`${BASE_URL}/cs/customers`);
  await settle(page);
  const firstCustLink = page.locator('table tbody tr:first-child a, a[href*="/cs/customers/"]').first();
  if (await firstCustLink.isVisible().catch(() => false)) {
    const href = await firstCustLink.getAttribute('href');
    if (href && !href.includes('/new')) {
      await firstCustLink.click();
      await settle(page);
      await snap(page, '01-cs', 'CS-19_customer-view');

      // Edit nasabah
      const editCustBtn = page.locator('a:has-text("Edit"), a[href*="edit"]').first();
      if (await editCustBtn.isVisible().catch(() => false)) {
        await editCustBtn.click();
        await settle(page);
        await snap(page, '01-cs', 'CS-20_customer-edit');
      }
    }
  }

  // ── 1.4 Account / Rekening ──
  console.log('  📂 Account / Rekening');
  await page.goto(`${BASE_URL}/account/list`);
  await settle(page);
  await snap(page, '01-cs', 'CS-21_account-list');

  // Filter by status
  const statusFilter = page.locator('select[name="status"]').first();
  if (await statusFilter.isVisible().catch(() => false)) {
    await statusFilter.selectOption('ACTIVE');
    const filterBtn = page.locator('button[type="submit"], button:has-text("Filter"), button:has-text("Search")').first();
    if (await filterBtn.isVisible().catch(() => false)) {
      await filterBtn.click();
    }
    await settle(page);
    await snap(page, '01-cs', 'CS-23_account-list-filter');
  }

  // Open account — select customer
  await page.goto(`${BASE_URL}/cs/account/open`);
  await settle(page);
  await snap(page, '01-cs', 'CS-24_account-open-select');

  // Click first eligible customer
  const firstEligible = page.locator('a[href*="/cs/account/open/"], table tbody tr:first-child a, .btn:has-text("Buka"), .btn:has-text("Pilih")').first();
  if (await firstEligible.isVisible().catch(() => false)) {
    await firstEligible.click();
    await settle(page);
    await snap(page, '01-cs', 'CS-26_account-open-form');

    // Fill form
    const prodSelect = page.locator('select[name="produkId"]').first();
    if (await prodSelect.isVisible().catch(() => false)) {
      await prodSelect.selectOption({ index: 1 });
    }
    const namaRekField = page.locator('input[name="namaRekening"]').first();
    if (await namaRekField.isVisible().catch(() => false)) {
      await namaRekField.fill('Tabungan Pribadi Ahmad');
    }
    const setoranField = page.locator('input[name="nominalSetoranAwal"]').first();
    if (await setoranField.isVisible().catch(() => false)) {
      await setoranField.fill('500000');
    }
    await snap(page, '01-cs', 'CS-27_account-open-filled');
  }

  // View existing account
  await page.goto(`${BASE_URL}/cs/account`);
  await settle(page);
  const firstAccountLink = page.locator('a[href*="/cs/account/"]:not([href*="open"])').first();
  if (await firstAccountLink.isVisible().catch(() => false)) {
    const href = await firstAccountLink.getAttribute('href');
    if (href && /\/cs\/account\/\d+/.test(href)) {
      await firstAccountLink.click();
      await settle(page);
      await snap(page, '01-cs', 'CS-29_account-view');

      // Transactions / Mutasi
      const mutasiLink = page.locator('a:has-text("Mutasi"), a:has-text("Transaction"), a[href*="transactions"]').first();
      if (await mutasiLink.isVisible().catch(() => false)) {
        await mutasiLink.click();
        await settle(page);
        await snap(page, '01-cs', 'CS-30_account-transactions');
      }

      // Close account page
      const closeLink = page.locator('a:has-text("Tutup"), a[href*="close"]').first();
      if (await closeLink.isVisible().catch(() => false)) {
        await closeLink.click();
        await settle(page);
        await snap(page, '01-cs', 'CS-32_account-close');
      }
    }
  }

  await context.close();
  console.log('  ✅ CS screenshots complete');
}

// ═══════════════════════════════════════════════════════════════════════════════
// PHASE 2: TELLER
// ═══════════════════════════════════════════════════════════════════════════════
async function phase2_teller(browser: Browser) {
  console.log('\n💰 FASE 2: Teller — login as teller');
  const { page, context } = await login(browser, 'teller', 'teller1234', 'TELLER');

  // ── 2.1 Dashboard ──
  console.log('  📂 Dashboard Teller');
  await snap(page, '02-teller', 'TL-01_dashboard');

  // ── 2.2 Transaction List ──
  console.log('  📂 Daftar Transaksi');
  await page.goto(`${BASE_URL}/teller/transaction/list`);
  await settle(page);
  await snap(page, '02-teller', 'TL-02_transaction-list');

  // ── 2.3 Deposit ──
  console.log('  📂 Setoran Tunai');
  await page.goto(`${BASE_URL}/teller/transaction/deposit`);
  await settle(page);
  await snap(page, '02-teller', 'TL-04_deposit-select');

  // Search for account
  const depSearch = page.locator('input[name="search"], input[name="q"], input[placeholder*="earch"], input[placeholder*="cari"]').first();
  if (await depSearch.isVisible().catch(() => false)) {
    await depSearch.fill('test');
    await depSearch.press('Enter');
    await settle(page);
    await snap(page, '02-teller', 'TL-05_deposit-select-search');
  }

  // Click first account for deposit
  const depAccountLink = page.locator('a[href*="/teller/transaction/deposit/"], .btn:has-text("Setor"), .btn:has-text("Deposit"), table tbody tr:first-child a').first();
  if (await depAccountLink.isVisible().catch(() => false)) {
    await depAccountLink.click();
    await settle(page);
    await snap(page, '02-teller', 'TL-06_deposit-form');

    // Fill deposit form
    const amountField = page.locator('input[name="amount"], input[name="nominal"], input[name="jumlah"], input[type="number"]').first();
    if (await amountField.isVisible().catch(() => false)) {
      await amountField.fill('1000000');
    }
    const descField = page.locator('input[name="description"], input[name="keterangan"], textarea[name="keterangan"], textarea[name="description"]').first();
    if (await descField.isVisible().catch(() => false)) {
      await descField.fill('Setoran tunai nasabah');
    }
    await snap(page, '02-teller', 'TL-07_deposit-form-filled');

    // Submit deposit
    const submitDep = page.locator('button[type="submit"]').first();
    if (await submitDep.isVisible().catch(() => false)) {
      await submitDep.click();
      await settle(page, 1500);
      await snap(page, '02-teller', 'TL-08_deposit-success');
    }
  }

  // View transaction detail
  await page.goto(`${BASE_URL}/teller/transaction/list`);
  await settle(page);
  const firstTxLink = page.locator('a[href*="/teller/transaction/"]:not([href*="deposit"]):not([href*="withdrawal"]):not([href*="transfer"]):not([href*="list"]):not([href*="receipt"])').first();
  if (await firstTxLink.isVisible().catch(() => false)) {
    await firstTxLink.click();
    await settle(page);
    await snap(page, '02-teller', 'TL-09_transaction-detail');
  }

  // ── 2.4 Withdrawal ──
  console.log('  📂 Penarikan Tunai');
  await page.goto(`${BASE_URL}/teller/transaction/withdrawal`);
  await settle(page);
  await snap(page, '02-teller', 'TL-10_withdrawal-select');

  const wdAccountLink = page.locator('a[href*="/teller/transaction/withdrawal/"], .btn:has-text("Tarik"), table tbody tr:first-child a').first();
  if (await wdAccountLink.isVisible().catch(() => false)) {
    await wdAccountLink.click();
    await settle(page);
    await snap(page, '02-teller', 'TL-11_withdrawal-form');

    const wdAmount = page.locator('input[name="amount"], input[name="nominal"], input[name="jumlah"], input[type="number"]').first();
    if (await wdAmount.isVisible().catch(() => false)) {
      await wdAmount.fill('200000');
    }
    const wdDesc = page.locator('input[name="description"], input[name="keterangan"], textarea[name="keterangan"]').first();
    if (await wdDesc.isVisible().catch(() => false)) {
      await wdDesc.fill('Penarikan tunai nasabah');
    }
    await snap(page, '02-teller', 'TL-12_withdrawal-form-filled');

    const submitWd = page.locator('button[type="submit"]').first();
    if (await submitWd.isVisible().catch(() => false)) {
      await submitWd.click();
      await settle(page, 1500);
      await snap(page, '02-teller', 'TL-13_withdrawal-success');
    }
  }

  // ── 2.5 Transfer ──
  console.log('  📂 Transfer Antar Rekening');
  await page.goto(`${BASE_URL}/teller/transaction/transfer`);
  await settle(page);
  await snap(page, '02-teller', 'TL-15_transfer-step1-source');

  const srcLink = page.locator('a[href*="/teller/transaction/transfer/"], .btn:has-text("Pilih"), table tbody tr:first-child a').first();
  if (await srcLink.isVisible().catch(() => false)) {
    await srcLink.click();
    await settle(page);
    await snap(page, '02-teller', 'TL-16_transfer-step2-target');

    // Select target
    const tgtLink = page.locator('a[href*="/teller/transaction/transfer/"], .btn:has-text("Pilih"), table tbody tr a').nth(0);
    if (await tgtLink.isVisible().catch(() => false)) {
      await tgtLink.click();
      await settle(page);
      await snap(page, '02-teller', 'TL-17_transfer-step3-form');

      const tfAmount = page.locator('input[name="amount"], input[name="nominal"], input[type="number"]').first();
      if (await tfAmount.isVisible().catch(() => false)) {
        await tfAmount.fill('150000');
      }
      const tfDesc = page.locator('input[name="description"], input[name="keterangan"], textarea[name="keterangan"]').first();
      if (await tfDesc.isVisible().catch(() => false)) {
        await tfDesc.fill('Transfer antar rekening');
      }
      await snap(page, '02-teller', 'TL-18_transfer-step3-filled');
    }
  }

  // ── 2.6 Passbook ──
  console.log('  📂 Buku Tabungan (Passbook)');
  await page.goto(`${BASE_URL}/teller/passbook/select-account`);
  await settle(page);
  await snap(page, '02-teller', 'TL-23_passbook-select');

  const pbAccountLink = page.locator('a[href*="/teller/passbook/preview/"], a[href*="/teller/passbook/initialize/"], table tbody tr:first-child a, .btn:has-text("Pilih")').first();
  if (await pbAccountLink.isVisible().catch(() => false)) {
    const pbHref = await pbAccountLink.getAttribute('href') || '';
    // Extract account ID
    const idMatch = pbHref.match(/\/(\d+)/);
    if (idMatch) {
      const accountId = idMatch[1];

      // Preview
      await page.goto(`${BASE_URL}/teller/passbook/preview/${accountId}`);
      await settle(page);
      await snap(page, '02-teller', 'TL-25_passbook-preview');

      // Initialize
      await page.goto(`${BASE_URL}/teller/passbook/initialize/${accountId}`);
      await settle(page);
      await snap(page, '02-teller', 'TL-26_passbook-initialize');

      // Print view
      await page.goto(`${BASE_URL}/teller/passbook/print/${accountId}`);
      await settle(page);
      await snap(page, '02-teller', 'TL-27_passbook-print');

      // Identity page
      await page.goto(`${BASE_URL}/teller/passbook/print-identity/${accountId}`);
      await settle(page);
      await snap(page, '02-teller', 'TL-28_passbook-identity');

      // Booklet
      await page.goto(`${BASE_URL}/teller/passbook/booklet/${accountId}`);
      await settle(page);
      await snap(page, '02-teller', 'TL-29_passbook-booklet');
    }
  }

  // ── 2.7 Settings ──
  console.log('  📂 Settings');
  await page.goto(`${BASE_URL}/settings`);
  await settle(page);
  await snap(page, '05-settings', 'ST-01_settings');

  await context.close();
  console.log('  ✅ Teller screenshots complete');
}

// ═══════════════════════════════════════════════════════════════════════════════
// PHASE 3: SUPERVISOR / BRANCH MANAGER
// ═══════════════════════════════════════════════════════════════════════════════
async function phase3_supervisor(browser: Browser) {
  console.log('\n🔍 FASE 3: Supervisor/Branch Manager — login as spv');
  let session: { page: Page; context: BrowserContext };
  try {
    session = await login(browser, 'spv', 'minibank123', 'SUPERVISOR');
  } catch {
    session = await login(browser, 'spv', 'spv1234', 'SUPERVISOR');
  }
  const { page, context } = session;

  // ── 3.1 Dashboard ──
  console.log('  📂 Dashboard Supervisor');
  await snap(page, '03-supervisor', 'SV-01_dashboard');

  // ── 3.2 Sidebar (full view) ──
  await snap(page, '03-supervisor', 'SV-01b_sidebar-full', false);

  // ── 3.3 Approval Queue — Nasabah ──
  console.log('  📂 Approval Nasabah');
  await page.goto(`${BASE_URL}/supervisor/nasabah`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-02_nasabah-queue');

  // Filter pending
  await page.goto(`${BASE_URL}/supervisor/nasabah/pending`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-03_nasabah-pending');

  // History
  await page.goto(`${BASE_URL}/supervisor/nasabah/history`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-04_nasabah-history');

  // Detail nasabah
  const nasabahDetailLink = page.locator('a[href*="/supervisor/nasabah/"]:not([href*="pending"]):not([href*="history"])').first();
  if (await nasabahDetailLink.isVisible().catch(() => false)) {
    await nasabahDetailLink.click();
    await settle(page);
    await snap(page, '03-supervisor', 'SV-05_nasabah-detail');
  }

  // ── 3.4 Approval Queue — Account ──
  console.log('  📂 Approval Rekening');
  await page.goto(`${BASE_URL}/supervisor/account`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-09_account-queue');

  await page.goto(`${BASE_URL}/supervisor/account/pending`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-10_account-pending');

  const accDetailLink = page.locator('a[href*="/supervisor/account/"]:not([href*="pending"]):not([href*="history"])').first();
  if (await accDetailLink.isVisible().catch(() => false)) {
    await accDetailLink.click();
    await settle(page);
    await snap(page, '03-supervisor', 'SV-11_account-detail');
  }

  // ── 3.5 Approval Queue — Transaction ──
  console.log('  📂 Approval Transaksi');
  await page.goto(`${BASE_URL}/supervisor/transactions`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-13_transaction-queue');

  const txDetailLink = page.locator('a[href*="/supervisor/transactions/"]:not([href*="pending"])').first();
  if (await txDetailLink.isVisible().catch(() => false)) {
    await txDetailLink.click();
    await settle(page);
    await snap(page, '03-supervisor', 'SV-14_transaction-detail');
  }

  // ── 3.6 Reports ──
  console.log('  📂 Laporan');
  await page.goto(`${BASE_URL}/reports`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-16_reports-index');

  // ── 3.7 User Management ──
  console.log('  📂 User Management (Supervisor)');
  await page.goto(`${BASE_URL}/rbac/users/list`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-20_users-list');

  const userViewLink = page.locator('a[href*="/rbac/users/view/"]').first();
  if (await userViewLink.isVisible().catch(() => false)) {
    await userViewLink.click();
    await settle(page);
    await snap(page, '03-supervisor', 'SV-21_user-view');
  }

  // ── 3.8 Audit ──
  console.log('  📂 Audit Log');
  await page.goto(`${BASE_URL}/audit/activities`);
  await settle(page);
  await snap(page, '03-supervisor', 'SV-23_audit-activities');

  await context.close();
  console.log('  ✅ Supervisor screenshots complete');
}

// ═══════════════════════════════════════════════════════════════════════════════
// PHASE 4: ADMINISTRATOR
// ═══════════════════════════════════════════════════════════════════════════════
async function phase4_admin(browser: Browser) {
  console.log('\n🛡️ FASE 4: Administrator — login as admin');
  const { page, context } = await login(browser, 'admin', 'minibank123', 'ADMIN');

  // ── 4.1 Dashboard ──
  console.log('  📂 Dashboard Admin');
  await snap(page, '04-admin', 'AD-01_dashboard');
  // Sidebar close-up (viewport crop)
  await snap(page, '04-admin', 'AD-02_sidebar-full', false);

  // ── 4.2 RBAC Users ──
  console.log('  📂 RBAC Users');
  await page.goto(`${BASE_URL}/rbac/users/list`);
  await settle(page);
  await snap(page, '04-admin', 'AD-03_users-list');

  // Filter pending
  const userStatusFilter = page.locator('select[name="status"], a:has-text("Pending"), a[href*="status=PENDING"]').first();
  if (await userStatusFilter.isVisible().catch(() => false)) {
    if (await userStatusFilter.evaluate((el: Element) => el.tagName === 'SELECT').catch(() => false)) {
      await userStatusFilter.selectOption('PENDING');
      const filterBtn = page.locator('button[type="submit"]').first();
      if (await filterBtn.isVisible().catch(() => false)) await filterBtn.click();
    } else {
      await userStatusFilter.click();
    }
    await settle(page);
    await snap(page, '04-admin', 'AD-04_users-list-pending');
  }

  // Create user form
  await page.goto(`${BASE_URL}/rbac/users/create`);
  await settle(page);
  await snap(page, '04-admin', 'AD-06_users-create');

  // Fill user form
  const uNameField = page.locator('input[name="username"]').first();
  if (await uNameField.isVisible().catch(() => false)) {
    await uNameField.fill('cs_baru');
  }
  const uFullNameField = page.locator('input[name="fullName"]').first();
  if (await uFullNameField.isVisible().catch(() => false)) {
    await uFullNameField.fill('CS Cabang Baru');
  }
  const uEmailField = page.locator('input[name="email"]').first();
  if (await uEmailField.isVisible().catch(() => false)) {
    await uEmailField.fill('cs_baru@tazkia.ac.id');
  }
  const uPwdField = page.locator('input[name="password"]').first();
  if (await uPwdField.isVisible().catch(() => false)) {
    await uPwdField.fill('minibank123');
  }
  await snap(page, '04-admin', 'AD-07_users-create-filled');

  // View user detail
  await page.goto(`${BASE_URL}/rbac/users/list`);
  await settle(page);
  const adminUserViewLink = page.locator('a[href*="/rbac/users/view/"]').first();
  if (await adminUserViewLink.isVisible().catch(() => false)) {
    await adminUserViewLink.click();
    await settle(page);
    await snap(page, '04-admin', 'AD-08_users-view');

    // Get user ID for role management
    const currentUrl = page.url();
    const userIdMatch = currentUrl.match(/\/rbac\/users\/view\/(\d+)/);
    if (userIdMatch) {
      const userId = userIdMatch[1];

      // Edit user
      await page.goto(`${BASE_URL}/rbac/users/edit/${userId}`);
      await settle(page);
      await snap(page, '04-admin', 'AD-09_users-edit');

      // User roles
      await page.goto(`${BASE_URL}/rbac/users/${userId}/roles`);
      await settle(page);
      await snap(page, '04-admin', 'AD-10_users-roles');

      // Password reset
      await page.goto(`${BASE_URL}/rbac/users/${userId}/password`);
      await settle(page);
      await snap(page, '04-admin', 'AD-11_users-password');
    }
  }

  // ── 4.3 RBAC Roles ──
  console.log('  📂 RBAC Roles');
  await page.goto(`${BASE_URL}/rbac/roles/list`);
  await settle(page);
  await snap(page, '04-admin', 'AD-14_roles-list');

  // Create role form
  await page.goto(`${BASE_URL}/rbac/roles/create`);
  await settle(page);
  await snap(page, '04-admin', 'AD-15_roles-create');

  // View role detail
  await page.goto(`${BASE_URL}/rbac/roles/list`);
  await settle(page);
  const roleViewLink = page.locator('a[href*="/rbac/roles/view/"]').first();
  if (await roleViewLink.isVisible().catch(() => false)) {
    await roleViewLink.click();
    await settle(page);
    await snap(page, '04-admin', 'AD-16_roles-view');

    const roleUrl = page.url();
    const roleIdMatch = roleUrl.match(/\/rbac\/roles\/view\/(\d+)/);
    if (roleIdMatch) {
      const roleId = roleIdMatch[1];

      // Edit role
      await page.goto(`${BASE_URL}/rbac/roles/edit/${roleId}`);
      await settle(page);
      await snap(page, '04-admin', 'AD-17_roles-edit');

      // Role permissions
      await page.goto(`${BASE_URL}/rbac/roles/${roleId}/permissions`);
      await settle(page);
      await snap(page, '04-admin', 'AD-18_roles-permissions');
    }
  }

  // ── 4.4 RBAC Permissions ──
  console.log('  📂 RBAC Permissions');
  await page.goto(`${BASE_URL}/rbac/permissions/list`);
  await settle(page);
  await snap(page, '04-admin', 'AD-20_permissions-list');

  // Filter by category
  const permCatFilter = page.locator('select[name="category"]').first();
  if (await permCatFilter.isVisible().catch(() => false)) {
    await permCatFilter.selectOption({ index: 1 });
    const filterBtn = page.locator('button[type="submit"]').first();
    if (await filterBtn.isVisible().catch(() => false)) await filterBtn.click();
    await settle(page);
    await snap(page, '04-admin', 'AD-21_permissions-list-filter');
  }

  // Create permission form
  await page.goto(`${BASE_URL}/rbac/permissions/create`);
  await settle(page);
  await snap(page, '04-admin', 'AD-22_permissions-create');

  // View permission detail
  await page.goto(`${BASE_URL}/rbac/permissions/list`);
  await settle(page);
  const permViewLink = page.locator('a[href*="/rbac/permissions/view/"]').first();
  if (await permViewLink.isVisible().catch(() => false)) {
    await permViewLink.click();
    await settle(page);
    await snap(page, '04-admin', 'AD-23_permissions-view');
  }

  // ── 4.5 Admin sees all modules — capture Product, Customer, Account etc through admin eyes ──
  console.log('  📂 Admin Full Access Verification');
  await page.goto(`${BASE_URL}/cs/product`);
  await settle(page);
  await snap(page, '04-admin', 'AD-30_admin-product-access');

  await page.goto(`${BASE_URL}/cs/customers`);
  await settle(page);
  await snap(page, '04-admin', 'AD-31_admin-customer-access');

  await page.goto(`${BASE_URL}/teller/transaction/list`);
  await settle(page);
  await snap(page, '04-admin', 'AD-32_admin-transaction-access');

  await context.close();
  console.log('  ✅ Admin screenshots complete');
}

// ═══════════════════════════════════════════════════════════════════════════════
// MAIN
// ═══════════════════════════════════════════════════════════════════════════════
async function main() {
  console.log('╔══════════════════════════════════════════════════════╗');
  console.log('║  MINIBANK TUTORIAL — Screenshot Generator           ║');
  console.log('║  Target: ~100 screenshots across 5 roles            ║');
  console.log('╚══════════════════════════════════════════════════════╝');

  const browser = await chromium.launch({ headless: true });

  try {
    await phase0_common(browser);
    await phase1_cs(browser);
    await phase2_teller(browser);
    await phase3_supervisor(browser);
    await phase4_admin(browser);

    console.log('\n════════════════════════════════════════════════════════');
    console.log('✅ ALL SCREENSHOTS COMPLETE!');
    console.log(`📁 Output: ${SCREENSHOT_DIR}`);

    // Count screenshots
    let total = 0;
    for (const sub of ['00-common', '01-cs', '02-teller', '03-supervisor', '04-admin', '05-settings']) {
      const dir = path.join(SCREENSHOT_DIR, sub);
      if (fs.existsSync(dir)) {
        const files = fs.readdirSync(dir).filter(f => f.endsWith('.png'));
        console.log(`  ${sub}: ${files.length} screenshots`);
        total += files.length;
      }
    }
    console.log(`  TOTAL: ${total} screenshots`);
    console.log('════════════════════════════════════════════════════════');
  } finally {
    await browser.close();
  }
}

main().catch(err => {
  console.error('❌ FATAL ERROR:', err);
  process.exit(1);
});

