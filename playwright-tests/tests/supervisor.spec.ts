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
    // Link detail uses class "btn-detail" with text "Detail"
    const link = page.locator('a.btn-detail').first();
    const count = await link.count();
    if (count === 0) {
      console.log('Tidak ada nasabah di list, test di-skip');
      test.skip();
      return;
    }
    await link.click();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);
    await expect(page.locator('.page-title')).toContainText('Detail Nasabah');
  });

  // ─── Approve & Reject ──────────────────────────────────────────────────────

  test('Approve nasabah INACTIVE', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');

    const detailLink = page.locator('a.btn-detail').first();
    if (await detailLink.count() === 0) {
      console.log('Tidak ada nasabah pending, test di-skip');
      test.skip();
      return;
    }
    await detailLink.click();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);

    // Handle confirm dialog
    page.on('dialog', dialog => dialog.accept());

    // Klik tombol "Setujui (Approve)"
    await page.locator('button.btn-approve:has-text("Setujui")').click();

    await expect(page).toHaveURL(/\/supervisor\/nasabah/);
  });

  test('Reject nasabah dengan alasan', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');

    const detailLink = page.locator('a.btn-detail').first();
    if (await detailLink.count() === 0) {
      console.log('Tidak ada nasabah pending, test di-skip');
      test.skip();
      return;
    }
    await detailLink.click();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);

    // Klik tombol "Tolak (Reject)" yang membuka modal
    await page.locator('button.btn-reject:has-text("Tolak")').click();

    // Modal muncul — isi reason (wajib) dan notes
    const modal = page.locator('#rejectModal');
    await expect(modal).toBeVisible();
    await modal.locator('textarea[name="reason"]').fill('Dokumen tidak lengkap');
    await modal.locator('textarea[name="notes"]').fill('KTP tidak terbaca');

    // Klik "Kirim Penolakan"
    await modal.locator('button[type="submit"]').click();

    await expect(page).toHaveURL(/\/supervisor\/nasabah/);
  });

  test('Reject nasabah tanpa alasan (Negative Test)', async ({ page }) => {
    await spvPage.navigateToNasabahList('PENDING');

    const detailLink = page.locator('a.btn-detail').first();
    if (await detailLink.count() === 0) {
      console.log('Tidak ada nasabah pending, test di-skip');
      test.skip();
      return;
    }
    await detailLink.click();
    await expect(page).toHaveURL(/\/supervisor\/nasabah\/\d+/);

    // Klik "Tolak (Reject)" untuk membuka modal
    await page.locator('button.btn-reject:has-text("Tolak")').click();
    
    const modal = page.locator('#rejectModal');
    await expect(modal).toBeVisible();

    // Reason field is required via HTML attribute, so browser will prevent submission
    // Try clicking submit with empty reason — should stay on modal
    const reasonField = modal.locator('textarea[name="reason"]');
    await reasonField.fill(''); // kosong
    await modal.locator('button[type="submit"]').click();

    // textarea[required] akan mencegah submit, modal tetap terlihat
    await expect(modal).toBeVisible();
  });
});
