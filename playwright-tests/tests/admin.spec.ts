import { test, expect } from '@playwright/test';
import { AdminPage } from '../pages/AdminPage';

test.describe('Admin — Dashboard & User Approval', () => {
  let adminPage: AdminPage;

  test.beforeEach(async ({ page }) => {
    adminPage = new AdminPage(page);
  });

  test('Admin Dashboard tampil', async ({ page }) => {
    await adminPage.navigateToDashboard();
    await expect(page).toHaveURL(/\/admin\/dashboard/);
    // Admin dashboard uses h2, not h1
    await expect(page.locator('h2')).toContainText('Pending Registrations');
  });

  test('Admin Dashboard menampilkan daftar atau pesan kosong', async ({ page }) => {
    await adminPage.navigateToDashboard();
    // Either table is visible (pending users) OR empty message
    const tableOrEmpty = page.locator('table, .empty');
    await expect(tableOrEmpty.first()).toBeVisible();
  });

  test('Approve user pending (jika ada)', async ({ page }) => {
    await adminPage.navigateToDashboard();

    const hasPending = await adminPage.hasPendingUsers();
    if (!hasPending) {
      console.log('Tidak ada pending user, test di-skip');
      test.skip();
      return;
    }

    // Handle confirm dialog
    page.on('dialog', dialog => dialog.accept());

    // Klik link "Lihat / Konfirmasi" dari user pending pertama
    const detailLink = page.locator('a.detail').first();
    await detailLink.click();

    // Verifikasi halaman approval terbuka
    await expect(page).toHaveURL(/\/admin\/approval\/\d+/);

    // Klik APPROVE
    await page.locator('button.btn-approve:has-text("APPROVE")').click();

    // Verifikasi redirect ke dashboard
    await expect(page).toHaveURL(/\/admin\/dashboard/);
  });

  test('Reject user pending (jika ada)', async ({ page }) => {
    await adminPage.navigateToDashboard();

    const hasPending = await adminPage.hasPendingUsers();
    if (!hasPending) {
      console.log('Tidak ada pending user, test di-skip');
      test.skip();
      return;
    }

    // Handle confirm dialog
    page.on('dialog', dialog => dialog.accept());

    const detailLink = page.locator('a.detail').first();
    await detailLink.click();

    await expect(page).toHaveURL(/\/admin\/approval\/\d+/);

    // Klik REJECT
    await page.locator('button.btn-reject:has-text("REJECT")').click();

    // Verifikasi redirect ke dashboard
    await expect(page).toHaveURL(/\/admin\/dashboard/);
  });
});
