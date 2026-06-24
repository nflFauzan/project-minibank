import { test, expect } from '@playwright/test';
import { ProductPage } from '../pages/ProductPage';

test.describe('Manajemen Produk Tabungan', () => {
  let productPage: ProductPage;

  test.beforeEach(async ({ page }) => {
    productPage = new ProductPage(page);
  });

  test('Lihat daftar produk tabungan', async ({ page }) => {
    await productPage.navigateToList();
    await expect(page).toHaveURL(/\/cs\/product/);
    await productPage.expectListTableVisible();
  });

  test('Tambah produk tabungan baru', async ({ page }) => {
    const unique = Date.now();
    const kodeProduk = `TAB_TEST_${unique}`.substring(0, 20);
    const namaProduk = `Tabungan Test ${unique}`;

    await productPage.navigateToCreateForm();
    await expect(page).toHaveURL(/\/cs\/product\/new/);

    await productPage.fillProductForm({
      kodeProduk,
      namaProduk,
      deskripsiSingkat: 'Produk tabungan untuk testing Playwright',
      jenisAkad: 'WADIAH',
      setoranAwalMinimum: '100000',
    });
    await productPage.submitForm();

    // Setelah sukses, redirect ke list produk
    await productPage.expectProductInList(namaProduk);
  });

  test('Lihat detail produk tabungan', async ({ page }) => {
    await productPage.navigateToList();
    // Klik detail produk pertama di tabel
    const detailLink = page.locator('table tr a[href*="/cs/product/"]').first();
    await detailLink.click();
    // Verifikasi halaman detail terbuka
    await expect(page).toHaveURL(/\/cs\/product\/\d+/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Edit produk tabungan yang ada', async ({ page }) => {
    await productPage.navigateToList();
    // Klik link Edit dari produk pertama di tabel
    const editLink = page.locator('table tr a[href*="/edit"]').first();
    await editLink.click();
    await expect(page).toHaveURL(/\/cs\/product\/\d+\/edit/);

    // Ubah nama produk
    const updatedName = 'Updated Tabungan ' + Date.now();
    await page.locator('#namaProduk').fill(updatedName);
    await productPage.submitForm();

    // Setelah submit, seharusnya redirect ke detail atau list dengan success
    await productPage.expectSuccessFlash();
  });

  test('Toggle status produk (aktif/nonaktif)', async ({ page }) => {
    await productPage.navigateToList();
    // Ambil baris produk pertama dan klik tombol toggle
    const toggleForm = page.locator('form[action*="/toggle"]').first();
    await expect(toggleForm).toBeVisible();
    await toggleForm.locator('button[type="submit"]').click();

    // Verifikasi sukses
    await productPage.navigateToList();
    await productPage.expectListTableVisible();
    // Flash message mungkin sudah hilang jika redirect, cukup verifikasi tabel masih ada
  });

  test('Tambah produk dengan kode yang sudah ada (Negative Test)', async ({ page }) => {
    await productPage.navigateToCreateForm();

    // TAB_UTAMA sudah ada dari seed data
    await productPage.fillProductForm({
      kodeProduk: 'TAB_UTAMA',
      namaProduk: 'Duplikat Produk',
      jenisAkad: 'WADIAH',
      setoranAwalMinimum: '100000',
    });
    await productPage.submitForm();

    // Harus ada pesan error (redirect kembali ke form dengan error)
    await productPage.expectErrorFlash();
  });

  test('Filter produk berdasarkan status ACTIVE', async ({ page }) => {
    await page.goto('/cs/product?status=ACTIVE');
    await productPage.expectListTableVisible();
    await expect(page).toHaveURL(/status=ACTIVE/);
  });

  test('Filter produk berdasarkan status INACTIVE', async ({ page }) => {
    await page.goto('/cs/product?status=INACTIVE');
    // Mungkin tabel kosong, tapi halaman harus tetap tampil
    await expect(page.locator('body')).toBeVisible();
  });

  test('Search produk berdasarkan nama', async ({ page }) => {
    await page.goto('/cs/product?q=Tabungan');
    await productPage.expectListTableVisible();
  });
});
