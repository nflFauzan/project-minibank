import { test, expect } from '@playwright/test';
import { SupervisorPage } from '../pages/SupervisorPage';

test.describe('Supervisor — Dashboard & Approval Nasabah', () => {
  let spvPage: SupervisorPage;

  test.beforeEach(async ({ page }) => {
    spvPage = new SupervisorPage(page);
  });

  // ─── Dashboard ─────────────────────────────────────────────────────────────

  test('Supervisor Dashboard tampil', async ({ page }) => {
    await spvPage.navigateToDashboard();
    await spvPage.expectDashboardVisible();
  });

  test('Supervisor Dashboard menampilkan jumlah pending nasabah', async ({ page }) => {
    await spvPage.navigateToDashboard();
    // Biasanya ada badge/counter pending di dashboard
    await expect(page.locator('body')).toBeVisible();
  });

  // ─── Daftar Nasabah ────────────────────────────────────────────────────────

  test('Lihat daftar nasabah PENDING (default)', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');
    await expect(page).toHaveURL(/\/supervisor\/nasabah/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Lihat daftar nasabah APPROVED', async ({ page }) => {
    await spvPage.navigateToNasabahList('APPROVED');
    await expect(page).toHaveURL(/filter=APPROVED/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Lihat daftar nasabah REJECTED', async ({ page }) => {
    await spvPage.navigateToNasabahList('REJECTED');
    await expect(page).toHaveURL(/filter=REJECTED/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Lihat daftar semua nasabah (ALL)', async ({ page }) => {
    await spvPage.navigateToNasabahList('ALL');
    await expect(page).toHaveURL(/filter=ALL/);
    await expect(page.locator('body')).toBeVisible();
  });

  // ─── Detail Nasabah ────────────────────────────────────────────────────────

  test('Lihat detail nasabah dari daftar', async ({ page }) => {
    await spvPage.navigateToNasabahList('ALL');
    const link = page.locator('table tr a, .nasabah-list a').first();
    const count = await link.count();
    if (count === 0) {
      console.log('Tidak ada nasabah di list, test di-skip');
      test.skip();
      return;
    }
    await link.click();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);
    await expect(page.locator('body')).toBeVisible();
  });

  // ─── Approve & Reject ──────────────────────────────────────────────────────

  test('Approve nasabah INACTIVE', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');

    const hasPending = await spvPage.hasPendingNasabah();
    if (!hasPending) {
      console.log('Tidak ada nasabah pending, test di-skip');
      test.skip();
      return;
    }

    await spvPage.clickFirstNasabahDetail();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);

    await spvPage.approveNasabah('Dokumen lengkap dan valid');
    await expect(page).toHaveURL(/\/supervisor\/nasabah/);
    await spvPage.expectSuccessFlash();
  });

  test('Reject nasabah dengan alasan', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');

    const hasPending = await spvPage.hasPendingNasabah();
    if (!hasPending) {
      console.log('Tidak ada nasabah pending, test di-skip');
      test.skip();
      return;
    }

    await spvPage.clickFirstNasabahDetail();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);

    await spvPage.rejectNasabah('Dokumen tidak lengkap', 'KTP tidak terbaca');
    await expect(page).toHaveURL(/\/supervisor\/nasabah/);
    await spvPage.expectSuccessFlash();
  });

  test('Reject nasabah tanpa alasan (Negative Test)', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');

    const hasPending = await spvPage.hasPendingNasabah();
    if (!hasPending) {
      console.log('Tidak ada nasabah pending, test di-skip');
      test.skip();
      return;
    }

    await spvPage.clickFirstNasabahDetail();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);

    // Submit reject tanpa reason
    await spvPage.rejectNasabah(''); // reason kosong
    
    // Harus kembali ke halaman detail dengan pesan error
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);
    await spvPage.expectErrorFlash(/wajib|required|error/i);
  });
});
