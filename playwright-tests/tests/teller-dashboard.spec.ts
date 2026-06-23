import { test, expect } from '@playwright/test';
import { TellerPage } from '../pages/TellerPage';

test.describe('Teller Dashboard', () => {
  let tellerPage: TellerPage;

  test.beforeEach(async ({ page }) => {
    tellerPage = new TellerPage(page);
  });

  test('Teller Dashboard tampil setelah login', async ({ page }) => {
    await tellerPage.navigateToDashboard();
    await tellerPage.expectDashboardVisible();
  });

  test('Teller Dashboard menampilkan statistik', async ({ page }) => {
    await tellerPage.navigateToDashboard();
    // Dashboard harus menampilkan minimal satu elemen statistik
    const stats = page.locator(
      '[class*="stat"], [class*="card"], [class*="summary"], [class*="total"]'
    );
    await expect(stats.first()).toBeVisible();
  });

  test('Teller Dashboard menampilkan daftar produk aktif', async ({ page }) => {
    await tellerPage.navigateToDashboard();
    const produkSection = page.locator('table, ul, [class*="produk"], [class*="product"]');
    await expect(produkSection.first()).toBeVisible();
  });

  test('Navigasi ke halaman Deposit dari sidebar', async ({ page }) => {
    await tellerPage.navigateToDashboard();
    const depositLink = page.locator('a[href*="deposit"], a:has-text("Deposit")');
    await expect(depositLink.first()).toBeVisible();
    await depositLink.first().click();
    await expect(page).toHaveURL(/deposit/);
  });

  test('Navigasi ke halaman Withdrawal dari sidebar', async ({ page }) => {
    await tellerPage.navigateToDashboard();
    const withdrawalLink = page.locator('a[href*="withdrawal"], a:has-text("Withdrawal"), a:has-text("Penarikan")');
    await expect(withdrawalLink.first()).toBeVisible();
    await withdrawalLink.first().click();
    await expect(page).toHaveURL(/withdrawal/);
  });

  test('Navigasi ke halaman Transfer dari sidebar', async ({ page }) => {
    await tellerPage.navigateToDashboard();
    const transferLink = page.locator('a[href*="transfer"], a:has-text("Transfer")');
    await expect(transferLink.first()).toBeVisible();
    await transferLink.first().click();
    await expect(page).toHaveURL(/transfer/);
  });
});
