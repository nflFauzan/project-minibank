import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

test.describe('E2E Migration & Dynamic Academic Period Tests', () => {
  test.beforeEach(async () => {
    test.setTimeout(60_000);
  });

  test('1. Admin Module: Manajemen Periode Praktikum & Zero Code Change Creation', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.navigate();
    await loginPage.login('admin', 'minibank123', 'ADMIN');
    await page.waitForURL('**/dashboard');

    // Buka menu Periode Praktikum
    await page.goto('/admin/periods');
    await expect(page).toHaveURL(/\/admin\/periods/);

    // Verifikasi Header & Kartu Periode Aktif
    await expect(page.locator('h1')).toContainText('Master Periode Praktikum');
    await expect(page.locator('.banner-active-period').first()).toBeVisible();

    // Verifikasi tabel periode memuat 2025/2026 dan 2026/2027
    const bodyText = await page.textContent('body');
    expect(bodyText).toContain('2025/2026');
    expect(bodyText).toContain('2026/2027');

    // Buat periode baru secara dinamis (Zero Code Change proof)
    const testYear = '2028/2029';
    await page.locator('#btn-add-period').click();
    await expect(page.locator('#modal-new-period')).toBeVisible();

    await page.fill('#kode', testYear);
    await page.fill('#nama', 'Praktikum Angkatan 2028 Dynamic');
    await page.fill('#tanggalMulai', '2028-09-01');
    await page.fill('#tanggalSelesai', '2029-06-30');
    await page.locator('#modal-new-period form button[type="submit"]').click();

    // Verifikasi redirect / alert sukses dan periode baru muncul di tabel
    await expect(page).toHaveURL(/\/admin\/periods/);
    const updatedBody = await page.textContent('body');
    expect(updatedBody).toContain(testYear);
  });

  test('2. CS Customers: Filter Dinamis & Badge Arsip Periode', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.navigate();
    await loginPage.login('cs1', 'minibank123', 'CS');
    await page.waitForURL('**/dashboard');

    // Navigasi ke /cs/customers
    await page.goto('/cs/customers');
    await expect(page).toHaveURL(/\/cs\/customers/);

    // Verifikasi filter toolbar ada dropdown period dan date picker
    const periodSelect = page.locator('select[name="period"]');
    await expect(periodSelect).toBeVisible();
    await expect(page.locator('input[name="startDate"]')).toBeVisible();
    await expect(page.locator('input[name="endDate"]')).toBeVisible();

    // Filter ke Semua Periode atau Arsip jika dropdown memiliki opsi
    const optionsCount = await periodSelect.locator('option').count();
    expect(optionsCount).toBeGreaterThan(1);

    await periodSelect.selectOption('all');
    await page.locator('button:has-text("Filter")').click();

    // Verifikasi tabel nasabah ada
    const table = page.locator('table');
    await expect(table).toBeVisible();
  });

  test('3. CS Account Management: Filter Dinamis & Badge Arsip Rekening', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.navigate();
    await loginPage.login('cs1', 'minibank123', 'CS');
    await page.waitForURL('**/dashboard');

    // Navigasi ke /cs/account
    await page.goto('/cs/account');
    await expect(page).toHaveURL(/\/cs\/account/);

    // Verifikasi filter toolbar
    const periodSelect = page.locator('select[name="period"]');
    await expect(periodSelect).toBeVisible();

    // Pilih Semua Periode
    await periodSelect.selectOption('all');
    await page.locator('button:has-text("Filter")').click();

    // Verifikasi rekening tampil
    const table = page.locator('table');
    await expect(table).toBeVisible();
  });

  test('4. Teller Transaction: Filter Dinamis & Tracking Transaksi', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.navigate();
    await loginPage.login('teller1', 'minibank123', 'TELLER');
    await page.waitForURL('**/dashboard');

    // Navigasi ke /teller/transaction/list
    await page.goto('/teller/transaction/list');
    await expect(page).toHaveURL(/\/teller\/transaction\/list/);

    // Verifikasi filter toolbar
    const periodSelect = page.locator('select[name="period"]');
    await expect(periodSelect).toBeVisible();
    await expect(page.locator('select[name="type"]')).toBeVisible();

    // Pilih Semua Periode
    await periodSelect.selectOption('all');
    await page.locator('button:has-text("Filter")').click();

    // Verifikasi tabel transaksi
    const table = page.locator('table');
    await expect(table).toBeVisible();
  });
});
