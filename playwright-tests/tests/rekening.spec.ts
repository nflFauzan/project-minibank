import { test, expect } from '@playwright/test';
import { RekeningPage } from '../pages/RekeningPage';

// Using CS storage state for these tests


test.describe('Manajemen Rekening', () => {
  let rekeningPage: RekeningPage;

  test.beforeEach(async ({ page }) => {
    rekeningPage = new RekeningPage(page);
  });

  test('Lihat daftar rekening', async ({ page }) => {
    await rekeningPage.navigateToList();
    // TODO: verify selector against actual HTML
    await expect(page.locator('table')).toBeVisible();
    await expect(page.locator('h1, .page-title')).toContainText('Account');
  });

  test('Buka rekening baru', async ({ page }) => {
    const testData = {
      nasabah: 'Budi Santoso', // Using seeded active nasabah
      produk: 'Tabungan Utama (TAB_UTAMA)', // Match actual label in HTML
      setoran: '500000',
      tujuan: 'Tabungan Masa Depan'
    };

    await rekeningPage.navigateToOpenAccount();
    await rekeningPage.selectNasabah(testData.nasabah);

    await rekeningPage.fillAccountForm(testData);
    await rekeningPage.submitForm();
    
    // Verifikasi sukses (usually redirects to list or detail)
    // TODO: verify behavior after submit
    await expect(page.locator('.alert-success, body')).toContainText(/berhasil|Success/i);
  });

  test('Lihat detail rekening', async ({ page }) => {
    await rekeningPage.navigateToList();
    // Click the first View link in the table for robustness
    const viewBtn = page.locator('table tr a:has-text("View")').first();
    await viewBtn.click();
    
    await expect(page.locator('.page-title, h1')).toBeVisible();
  });

/*
  test('Buka rekening tanpa memilih nasabah (Negative Test)', async ({ page }) => {
    await rekeningPage.navigateToOpenAccount();
    // Try to submit form without selecting nasabah (if possible) or with empty fields
    // Assuming we are on the form page
    await rekeningPage.submitForm();
    
    await rekeningPage.expectValidationErrors();
  });
*/
});
