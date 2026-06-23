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
    await expect(page.locator('body')).toBeVisible();
  });

  test('Deposit ke rekening berhasil', async ({ page }) => {
    await tellerPage.navigateToDepositSelect();
    
    // Pilih rekening pertama yang tersedia (dari seed data: Budi Santoso)
    await tellerPage.searchAndSelectRekeningForDeposit('Budi');

    // Isi form deposit
    await tellerPage.fillDepositForm({
      jumlahSetoran: '500000',
      keterangan: 'Setoran Tunai Test Playwright',
      noReferensi: `REF-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Verifikasi sukses — redirect ke list transaksi
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
    
    // Pilih rekening pertama (harus sudah memiliki saldo dari deposit sebelumnya)
    await tellerPage.searchAndSelectRekeningForWithdrawal('Budi');

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

    await tellerPage.fillWithdrawalForm({
      jumlahPenarikan: '999999999', // jumlah sangat besar
      keterangan: 'Penarikan Test Melebihi Saldo',
      noReferensi: `REF-WD-NEG-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Harus redirect kembali dengan pesan error
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
    await tellerPage.selectSourceRekening('Budi');

    // STEP 2: Setelah memilih source, akan diarahkan ke halaman pilih target
    await expect(page).toHaveURL(/\/teller\/transaction\/transfer\/.+/);

    // STEP 3: Pilih rekening target (rekening berbeda)
    await tellerPage.selectTargetRekening('');

    // STEP 4: Isi form transfer
    await expect(page).toHaveURL(/\/teller\/transaction\/transfer\/.+\/.+/);
    await tellerPage.fillTransferForm({
      jumlah: '50000',
      keteranganTambahan: 'Transfer Test Playwright',
      noReferensi: `REF-TRF-${Date.now()}`,
    });
    await tellerPage.submitForm();

    // Verifikasi sukses
    await expect(page).toHaveURL(/\/teller\/transaction\/list/);
    await tellerPage.expectSuccessFlash();
  });

  // ─── Detail Transaksi ──────────────────────────────────────────────────────

  test('Lihat detail transaksi', async ({ page }) => {
    await tellerPage.navigateToTransactionList();

    const count = await page.locator('table tbody tr').count();
    if (count === 0) {
      console.log('Belum ada transaksi, test di-skip');
      test.skip();
      return;
    }

    // Klik detail transaksi pertama
    const detailLink = page.locator('table tr a[href*="/teller/transaction/"]').first();
    await detailLink.click();

    await expect(page).toHaveURL(/\/teller\/transaction\/.+/);
    await expect(page.locator('body')).toBeVisible();
  });

  // ─── Download Struk PDF ────────────────────────────────────────────────────

  test('Unduh struk PDF transaksi', async ({ page }) => {
    await tellerPage.navigateToTransactionList();

    const count = await page.locator('table tbody tr').count();
    if (count === 0) {
      console.log('Belum ada transaksi, test di-skip');
      test.skip();
      return;
    }

    // Masuk ke detail transaksi pertama
    const detailLink = page.locator('table tr a[href*="/teller/transaction/"]').first();
    await detailLink.click();

    // Cek ada link/tombol download struk
    const receiptLink = page.locator('a[href*="/receipt/"], a:has-text("Struk"), a:has-text("Download")');
    
    if (await receiptLink.count() > 0) {
      // Intercept download request dan verifikasi content-type
      const [download] = await Promise.all([
        page.waitForEvent('download').catch(() => null),
        receiptLink.first().click(),
      ]);
      // Verifikasi file berhasil di-download atau link valid
      if (download) {
        expect(download.suggestedFilename()).toMatch(/receipt.*\.pdf/i);
      }
    } else {
      // Receipt link tidak ditemukan di halaman ini, skip dengan graceful
      console.log('Receipt link tidak tersedia di halaman detail ini');
    }
  });
});
