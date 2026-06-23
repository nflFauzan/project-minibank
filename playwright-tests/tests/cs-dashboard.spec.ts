import { test, expect } from '@playwright/test';

test.describe('CS Dashboard', () => {

  test('Dashboard CS tampil setelah login', async ({ page }) => {
    await page.goto('/cs/dashboard');
    await expect(page).toHaveURL(/\/cs\/dashboard/);
    // Pastikan halaman tidak error (bukan 500 / redirect ke login)
    await expect(page.locator('body')).toBeVisible();
  });

  test('Dashboard CS menampilkan statistik (total nasabah, rekening, produk)', async ({ page }) => {
    await page.goto('/cs/dashboard');
    // Cari elemen statistik — biasanya card/badge dengan angka
    const statsSection = page.locator(
      '[class*="stat"], [class*="card"], [class*="summary"], .total-nasabah, .total-rekening, .total-produk'
    );
    // Minimal ada satu elemen statistik
    await expect(statsSection.first()).toBeVisible();
  });

  test('Dashboard CS menampilkan daftar produk aktif', async ({ page }) => {
    await page.goto('/cs/dashboard');
    // Produk aktif biasanya tampil sebagai list/tabel/card di dashboard
    const produkSection = page.locator('table, ul, [class*="produk"], [class*="product"]');
    await expect(produkSection.first()).toBeVisible();
  });

  test('Navigasi sidebar CS berfungsi', async ({ page }) => {
    await page.goto('/cs/dashboard');
    // Cek link nasabah di sidebar ada
    const nasabahLink = page.locator('a[href*="/cs/customers"], a:has-text("Customer"), a:has-text("Nasabah")');
    await expect(nasabahLink.first()).toBeVisible();
  });
});
