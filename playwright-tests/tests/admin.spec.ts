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
    await expect(page.locator('h2')).toContainText(/Admin/i);
  });

  test('Admin Dashboard menampilkan daftar atau pesan kosong', async ({ page }) => {
    await adminPage.navigateToDashboard();
    // Either table is visible (pending users / all users) OR empty message
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

    // Handle confirm dialog if any
    page.on('dialog', dialog => dialog.accept());

    // Klik link tinjau / konfirmasi dari user pending pertama
    await adminPage.clickFirstPendingUser();

    // Verifikasi halaman approval terbuka
    await expect(page).toHaveURL(/\/admin\/approval\/\d+/);

    // Klik APPROVE
    await adminPage.approveUser();

    // Verifikasi redirect ke dashboard dengan flash success
    await expect(page).toHaveURL(/\/admin\/dashboard/);
    await adminPage.expectSuccessFlash(/approved|berhasil|sukses/i);
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

    await adminPage.clickFirstPendingUser();

    await expect(page).toHaveURL(/\/admin\/approval\/\d+/);

    // Klik REJECT
    await adminPage.rejectUser();

    // Verifikasi redirect ke dashboard
    await expect(page).toHaveURL(/\/admin\/dashboard/);
  });
});
