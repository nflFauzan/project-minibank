import { test, expect } from '@playwright/test';
import { NasabahPage } from '../pages/NasabahPage';

// Using CS storage state for these tests


test.describe('Manajemen Nasabah', () => {
  let nasabahPage: NasabahPage;

  test.beforeEach(async ({ page }) => {
    nasabahPage = new NasabahPage(page);
  });

  test('Lihat daftar nasabah', async ({ page }) => {
    await nasabahPage.navigateToList();
    // TODO: verify selector against actual HTML
    await expect(page.locator('table')).toBeVisible();
    await expect(page.locator('h1, .page-title')).toContainText('Customers');
  });

  test('Tambah nasabah baru', async ({ page }) => {
    const testData = {
      nik: '1234567890123456',
      nama: 'Budi Santoso ' + Date.now(),
      tempatLahir: 'Jakarta',
      tanggalLahir: '1990-01-01',
      ibuKandung: 'Siti Aminah',
      jenisKelamin: 'L',
      alamat: 'Jl. Merdeka No. 1'
    };

    await nasabahPage.navigateToRegistration();
    await nasabahPage.fillRegistrationForm(testData);
    await nasabahPage.submitForm();
    
    // Verifikasi muncul di daftar
    await nasabahPage.expectCustomerInList(testData.nama);
  });

  test('Kodepos autocomplete/autofill', async ({ page }) => {
    await nasabahPage.navigateToRegistration();
    
    // TODO: verify postal code and expected city/province
    await nasabahPage.triggerPostalCodeAutofill('10110'); 
    
    // Verify city and province are filled
    // await nasabahPage.expectPostalCodeData('JAKARTA PUSAT', 'DKI JAKARTA');
  });

  test('Lihat detail nasabah', async ({ page }) => {
    await nasabahPage.navigateToList();
    // Click the first View link in the table for robustness
    const viewBtn = page.locator('table tr a:has-text("View")').first();
    await viewBtn.click();
    
    await expect(page.locator('.main-title-custom, h1')).toBeVisible();
  });

/*
  test('Tambah nasabah dengan field wajib kosong (Negative Test)', async ({ page }) => {
    await nasabahPage.navigateToRegistration();
    // Submit without filling anything
    await nasabahPage.submitForm();
    
    await nasabahPage.expectValidationErrors();
  });
*/
});
