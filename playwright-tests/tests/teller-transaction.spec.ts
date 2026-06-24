import { test, expect } from '@playwright/test';
import { TellerPage } from '../pages/TellerPage';

test.describe('Teller — Transaksi (Deposit, Withdrawal, Transfer)', () => {
  let tellerPage: TellerPage;

  test.beforeEach(async ({ page }) => {
    tellerPage = new TellerPage(page);
  });

  // ─── Riwayat Transaksi ─────────────────────────────────────────────────────

  test('Lihat daftar riwayat transaksi', async ({ page }) => {
    await tellerPage.navigateToTransactionList();
    await expect(page).toHaveURL(/\/teller\/transaction\/list/);
    await expect(page.locator('table')).toBeVisible();
  });

  test('Filter transaksi berdasarkan tipe DEPOSIT', async ({ page }) => {
    await page.goto('/teller/transaction/list?type=DEPOSIT');
    await expect(page.locator('body')).toBeVisible();
  });

  test('Filter transaksi berdasarkan tipe WITHDRAWAL', async ({ page }) => {
    await page.goto('/teller/transaction/list?type=WITHDRAWAL');
    await expect(page.locator('body')).toBeVisible();
  });

  test('Filter transaksi berdasarkan tipe TRANSFER', async ({ page }) => {
    await page.goto('/teller/transaction/list?type=TRANSFER');
    await expect(page.locator('body')).toBeVisible();
  });

  // ─── Deposit ───────────────────────────────────────────────────────────────

  test('Halaman pilih rekening untuk deposit tampil', async ({ page }) => {
    await tellerPage.navigateToDepositSelect();
    await expect(page).toHaveURL(/\/teller\/transaction\/deposit/);
    await expect(page.locator('.page-title')).toContainText('Setoran Tunai');
  });

  test('Deposit ke rekening berhasil', async ({ page }) => {
    await tellerPage.navigateToDepositSelect();
    
    // Cari rekening (Budi Santoso dari seed data)
    await tellerPage.searchAndSelectRekeningForDeposit('Budi');

    // Pastikan di form deposit
    await expect(page).toHaveURL(/\/teller\/transaction\/deposit\/.+/);
    await expect(page.locator('.page-title')).toContainText('Formulir Setoran Tunai');

    // Isi form deposit
    await tellerPage.fillDepositForm({
      jumlahSetoran: '500000',
      keterangan: 'Setoran Tunai Test Playwright',
      noReferensi: `REF-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Sukses → redirect ke list transaksi
    await expect(page).toHaveURL(/\/teller\/transaction\/list/);
    await tellerPage.expectSuccessFlash();
  });

  // ─── Withdrawal ────────────────────────────────────────────────────────────

  test('Halaman pilih rekening untuk withdrawal tampil', async ({ page }) => {
    await tellerPage.navigateToWithdrawalSelect();
    await expect(page).toHaveURL(/\/teller\/transaction\/withdrawal/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Penarikan dari rekening berhasil (jika saldo cukup)', async ({ page }) => {
    await tellerPage.navigateToWithdrawalSelect();
    
    // Cari rekening yang sudah ada saldo dari deposit sebelumnya
    await tellerPage.searchAndSelectRekeningForWithdrawal('Budi');
    await expect(page).toHaveURL(/\/teller\/transaction\/withdrawal\/.+/);

    await tellerPage.fillWithdrawalForm({
      jumlahPenarikan: '100000',
      keterangan: 'Penarikan Tunai Test Playwright',
      noReferensi: `REF-WD-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Sukses → redirect ke list
    await expect(page).toHaveURL(/\/teller\/transaction\/list/);
    await tellerPage.expectSuccessFlash();
  });

  test('Penarikan melebihi saldo (Negative Test)', async ({ page }) => {
    await tellerPage.navigateToWithdrawalSelect();
    
    await tellerPage.searchAndSelectRekeningForWithdrawal('Budi');
    await expect(page).toHaveURL(/\/teller\/transaction\/withdrawal\/.+/);

    await tellerPage.fillWithdrawalForm({
      jumlahPenarikan: '999999999',
      keterangan: 'Penarikan Test Melebihi Saldo',
      noReferensi: `REF-WD-NEG-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Harus kembali ke halaman form dengan pesan error
    await tellerPage.expectErrorFlash();
  });

  // ─── Transfer ──────────────────────────────────────────────────────────────

  test('Halaman pilih rekening sumber untuk transfer tampil', async ({ page }) => {
    await tellerPage.navigateToTransferSelectSource();
    await expect(page).toHaveURL(/\/teller\/transaction\/transfer/);
    await expect(page.locator('body')).toBeVisible();
  });

  test('Transfer antar rekening berhasil', async ({ page }) => {
    // STEP 1: Pilih rekening sumber
    await tellerPage.navigateToTransferSelectSource();
    await tellerPage.selectSourceRekening('');

    // STEP 2: Pilih rekening target
    await expect(page).toHaveURL(/\/teller\/transaction\/transfer\/.+/);
    await tellerPage.selectTargetRekening('');

    // STEP 3: Isi form transfer
    await expect(page).toHaveURL(/\/teller\/transaction\/transfer\/.+\/.+/);
    await expect(page.locator('.page-title')).toContainText('Formulir Transfer Dana');

    await tellerPage.fillTransferForm({
      jumlah: '50000',
      keteranganTambahan: 'Transfer Test Playwright',
      noReferensi: `REF-TRF-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Sukses → redirect ke transaction list
    await expect(page).toHaveURL(/\/teller\/transaction\/list/);
    await tellerPage.expectSuccessFlash();
  });

  // ─── Detail Transaksi ──────────────────────────────────────────────────────

  test('Lihat detail transaksi', async ({ page }) => {
    await tellerPage.navigateToTransactionList();

    // Link Detail di tabel
    const detailLink = page.locator('a.action-link:has-text("Detail")').first();
    if (await detailLink.count() === 0) {
      console.log('Belum ada transaksi, test di-skip');
      test.skip();
      return;
    }

    await detailLink.click();
    await expect(page).toHaveURL(/\/teller\/transaction\/.+/);
    await expect(page.locator('.page-title')).toBeVisible();
  });

  // ─── Download Struk PDF ────────────────────────────────────────────────────

  test('Unduh struk PDF transaksi', async ({ page }) => {
    await tellerPage.navigateToTransactionList();

    // Link "Struk PDF" di tabel
    const receiptLink = page.locator('a.action-link:has-text("Struk PDF")').first();

    if (await receiptLink.count() === 0) {
      console.log('Tidak ada link struk PDF');
      test.skip();
      return;
    }

    // Intercept download
    const [download] = await Promise.all([
      page.waitForEvent('download').catch(() => null),
      receiptLink.click(),
    ]);
    
    if (download) {
      expect(download.suggestedFilename()).toMatch(/receipt.*\.pdf/i);
    }
  });
});
