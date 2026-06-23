import { test, expect } from '@playwright/test';

test.describe('Tutup Rekening (CS)', () => {

  test('Tutup rekening aktif', async ({ page }) => {
    await page.goto('/cs/account');
    await expect(page.locator('table')).toBeVisible();

    // Klik View pada rekening pertama yang tersedia
    const viewLink = page.locator('table tr a[href*="/cs/account/"]').first();
    await viewLink.click();

    // Pastikan di halaman detail rekening
    await expect(page).toHaveURL(/\/cs\/account\/\d+/);

    // Klik tombol Tutup Rekening
    const closeBtn = page.locator('form[action*="/close"] button[type="submit"], button:has-text("Tutup"), button:has-text("Close")').first();
    await expect(closeBtn).toBeVisible();
    await closeBtn.click();

    // Setelah tutup, redirect ke list rekening dengan flash success
    await expect(page).toHaveURL(/\/cs\/account/);
    await expect(page.locator('.alert-success, [class*="success"]')).toBeVisible();
  });

  test('Lihat daftar rekening dengan filter status ACTIVE', async ({ page }) => {
    await page.goto('/cs/account?status=ACTIVE');
    await expect(page.locator('body')).toBeVisible();
    await expect(page.locator('table')).toBeVisible();
  });

  test('Lihat daftar rekening dengan filter status CLOSED', async ({ page }) => {
    await page.goto('/cs/account?status=CLOSED');
    // Mungkin kosong tapi halaman harus tampil
    await expect(page.locator('body')).toBeVisible();
  });

  test('Search rekening berdasarkan nama nasabah', async ({ page }) => {
    await page.goto('/cs/account?search=Budi');
    await expect(page.locator('body')).toBeVisible();
  });
});
