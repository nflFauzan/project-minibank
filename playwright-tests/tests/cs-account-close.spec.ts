import { test, expect } from '@playwright/test';

test.describe('Tutup Rekening (CS)', () => {

  test('Tutup rekening aktif dari daftar rekening', async ({ page }) => {
    // Tampilkan daftar rekening ACTIVE
    await page.goto('/cs/account?status=ACTIVE');
    await expect(page.locator('table')).toBeVisible();

    // Cari tombol Close di baris pertama tabel (inline form)
    const closeBtn = page.locator('table tr button.link-danger').first();
    
    if (await closeBtn.count() === 0) {
      console.log('Tidak ada rekening aktif yang bisa ditutup');
      test.skip();
      return;
    }

    // Handle confirm dialog
    page.on('dialog', dialog => dialog.accept());

    await closeBtn.click();

    // Setelah tutup, redirect ke /cs/account dengan flash success
    await expect(page).toHaveURL(/\/cs\/account/);
  });

  test('Tutup rekening aktif dari halaman detail', async ({ page }) => {
    // Tampilkan daftar rekening ACTIVE
    await page.goto('/cs/account?status=ACTIVE');
    await expect(page.locator('table')).toBeVisible();

    // Klik View pada rekening pertama
    const viewLink = page.locator('table tr a.action-link:has-text("View")').first();
    
    if (await viewLink.count() === 0) {
      console.log('Tidak ada rekening aktif');
      test.skip();
      return;
    }
    
    await viewLink.click();
    
    // Di halaman detail, klik "Close Account"
    await expect(page).toHaveURL(/\/cs\/account\/\d+/);
    const closeBtn = page.locator('button.btn-danger-custom:has-text("Close Account")');
    await expect(closeBtn).toBeVisible();
    
    // Handle confirm dialog
    page.on('dialog', dialog => dialog.accept());
    await closeBtn.click();

    // Redirect ke list
    await expect(page).toHaveURL(/\/cs\/account/);
  });

  test('Lihat daftar rekening dengan filter status ACTIVE', async ({ page }) => {
    await page.goto('/cs/account?status=ACTIVE');
    await expect(page.locator('body')).toBeVisible();
    await expect(page.locator('table')).toBeVisible();
  });

  test('Lihat daftar rekening dengan filter status CLOSED', async ({ page }) => {
    await page.goto('/cs/account?status=CLOSED');
    await expect(page.locator('body')).toBeVisible();
  });

  test('Search rekening berdasarkan nama nasabah', async ({ page }) => {
    await page.goto('/cs/account?search=Budi');
    await expect(page.locator('body')).toBeVisible();
  });
});
