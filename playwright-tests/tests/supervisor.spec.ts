import { test, expect } from '@playwright/test';
import { SupervisorPage } from '../pages/SupervisorPage';

test.describe('Supervisor — Dashboard & Approval Rekening', () => {
  let spvPage: SupervisorPage;

  test.beforeEach(async ({ page }) => {
    spvPage = new SupervisorPage(page);
  });

  // ─── Dashboard ─────────────────────────────────────────────────────────────

  test('Supervisor Dashboard tampil', async ({ page }) => {
    await spvPage.navigateToDashboard();
    await spvPage.expectDashboardVisible();
  });

  test('Supervisor Dashboard menampilkan link antrean approval rekening', async ({ page }) => {
    await spvPage.navigateToDashboard();
    const actionCard = page.locator('a[href*="/supervisor/account"]');
    await expect(actionCard).toBeVisible();
    await expect(actionCard).toContainText(/Approval Rekening/i);
  });

  // ─── Daftar Rekening ────────────────────────────────────────────────────────

  test('Lihat daftar rekening PENDING (default)', async ({ page }) => {
    await spvPage.navigateToAccountList('PENDING');
    await expect(page).toHaveURL(/\/supervisor\/account/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Lihat daftar rekening APPROVED', async ({ page }) => {
    await spvPage.navigateToAccountList('APPROVED');
    await expect(page).toHaveURL(/filter=APPROVED/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Lihat daftar rekening REJECTED', async ({ page }) => {
    await spvPage.navigateToAccountList('REJECTED');
    await expect(page).toHaveURL(/filter=REJECTED/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Lihat daftar semua rekening (ALL)', async ({ page }) => {
    await spvPage.navigateToAccountList('ALL');
    await expect(page).toHaveURL(/filter=ALL/);
    await expect(page.locator('body')).toBeVisible();
  });

  // ─── Detail Rekening ────────────────────────────────────────────────────────

  test('Lihat detail rekening dari daftar', async ({ page }) => {
    await spvPage.navigateToAccountList('ALL');
    const link = page.locator('a.btn-detail').first();
    const count = await link.count();
    if (count === 0) {
      console.log('Tidak ada rekening di list, test di-skip');
      test.skip();
      return;
    }
    await link.click();
    await expect(page).toHaveURL(/\/supervisor\/account\/\d+/);
    await expect(page.locator('.page-title')).toContainText('Detail Rekening');
  });

  // ─── Approve & Reject Rekening ─────────────────────────────────────────────

  test('Approve rekening PENDING', async ({ page }) => {
    await spvPage.navigateToAccountList('PENDING');

    const detailLink = page.locator('a.btn-detail').first();
    if (await detailLink.count() === 0) {
      console.log('Tidak ada rekening pending, test di-skip');
      test.skip();
      return;
    }
    await detailLink.click();
    await expect(page).toHaveURL(/\/supervisor\/account\/\d+/);

    // Handle confirm dialog
    page.on('dialog', dialog => dialog.accept());

    // Klik tombol "Setujui Pembukaan Rekening"
    await page.locator('button.btn-approve:has-text("Setujui")').click();

    await expect(page).toHaveURL(/\/supervisor\/account/);
  });

  test('Reject rekening dengan alasan', async ({ page }) => {
    await spvPage.navigateToAccountList('PENDING');

    const detailLink = page.locator('a.btn-detail').first();
    if (await detailLink.count() === 0) {
      console.log('Tidak ada rekening pending, test di-skip');
      test.skip();
      return;
    }
    await detailLink.click();
    await expect(page).toHaveURL(/\/supervisor\/account\/\d+/);

    // Klik tombol "Tolak Rekening" yang membuka modal
    await page.locator('button.btn-reject:has-text("Tolak")').click();

    // Modal muncul — isi reason (wajib) dan notes
    const modal = page.locator('#rejectModal');
    await expect(modal).toBeVisible();
    await modal.locator('textarea[name="reason"]').fill('Dokumen tidak lengkap');
    await modal.locator('textarea[name="notes"]').fill('Informasi nasabah tidak sesuai');

    // Klik "Kirim Penolakan"
    await modal.locator('button[type="submit"]').click();

    await expect(page).toHaveURL(/\/supervisor\/account/);
  });

  test('Reject rekening tanpa alasan (Negative Test)', async ({ page }) => {
    await spvPage.navigateToAccountList('PENDING');

    const detailLink = page.locator('a.btn-detail').first();
    if (await detailLink.count() === 0) {
      console.log('Tidak ada rekening pending, test di-skip');
      test.skip();
      return;
    }
    await detailLink.click();
    await expect(page).toHaveURL(/\/supervisor\/account\/\d+/);

    // Klik "Tolak Rekening" untuk membuka modal
    await page.locator('button.btn-reject:has-text("Tolak")').click();
    
    const modal = page.locator('#rejectModal');
    await expect(modal).toBeVisible();

    // Reason field is required via HTML attribute, so browser will prevent submission
    const reasonField = modal.locator('textarea[name="reason"]');
    await reasonField.fill('');
    await modal.locator('button[type="submit"]').click();

    await expect(modal).toBeVisible();
  });
});
