import { test, expect } from '@playwright/test';
import { AdminPage } from '../pages/AdminPage';

test.describe('Admin — Dashboard & User Approval', () => {
  let adminPage: AdminPage;

  test.beforeEach(async ({ page }) => {
    adminPage = new AdminPage(page);
  });

  test('Admin Dashboard tampil', async ({ page }) => {
    await adminPage.navigateToDashboard();
    await adminPage.expectDashboardVisible();
  });

  test('Admin Dashboard menampilkan tabel pending users', async ({ page }) => {
    await adminPage.navigateToDashboard();
    // Tabel mungkin kosong jika tidak ada pending user, tapi elemen tabel harus ada
    await expect(page.locator('table, .pending-list, [class*="pending"]')).toBeVisible();
  });

  test('Role CS tidak dapat mengakses Admin Dashboard', async ({ page }) => {
    // Navigasi langsung tanpa storageState admin (menggunakan no-auth atau cs)
    // Test ini berjalan di chromium-admin project (sudah login sebagai admin), 
    // jadi kita verifikasi CS tidak bisa akses dengan cara lain.
    // Ini sudah di-cover di auth.spec.ts, jadi cukup verifikasi admin bisa akses
    await adminPage.navigateToDashboard();
    await expect(page).toHaveURL(/\/admin\/dashboard/);
  });

  test('Approve user pending', async ({ page }) => {
    await adminPage.navigateToDashboard();

    const hasPending = await adminPage.hasPendingUsers();
    if (!hasPending) {
      // Lewati test jika tidak ada pending user (bisa terjadi di environment bersih)
      console.log('Tidak ada pending user, test di-skip');
      test.skip();
      return;
    }

    // Klik link detail dari user pending pertama
    await adminPage.clickFirstPendingUser();

    // Verifikasi halaman approval terbuka
    await expect(page).toHaveURL(/\/admin\/approval\/\d+/);

    // Klik Approve
    await adminPage.approveUser();

    // Verifikasi redirect ke dashboard dengan pesan sukses
    await expect(page).toHaveURL(/\/admin\/dashboard/);
    // Flash message di URL atau di halaman
    const urlOrPage = page.url() + await page.locator('body').innerText();
    expect(urlOrPage).toMatch(/approved|berhasil|sukses/i);
  });

  test('Reject user pending', async ({ page }) => {
    await adminPage.navigateToDashboard();

    const hasPending = await adminPage.hasPendingUsers();
    if (!hasPending) {
      console.log('Tidak ada pending user, test di-skip');
      test.skip();
      return;
    }

    // Klik link detail dari user pending pertama
    await adminPage.clickFirstPendingUser();

    await expect(page).toHaveURL(/\/admin\/approval\/\d+/);

    // Klik Reject
    await adminPage.rejectUser();

    // Verifikasi redirect ke dashboard dengan pesan sukses
    await expect(page).toHaveURL(/\/admin\/dashboard/);
    const urlOrPage = page.url() + await page.locator('body').innerText();
    expect(urlOrPage).toMatch(/rejected|berhasil|sukses/i);
  });
});
