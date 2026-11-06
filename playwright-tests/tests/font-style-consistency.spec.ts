import { test, expect } from '@playwright/test';

test.describe('Font & Style Consistency across Modules & Sidebar', () => {

  test('Public pages (Login & Index) use consistent Poppins font styling', async ({ page }) => {
    await page.goto('/login');
    const loginFont = await page.evaluate(() => window.getComputedStyle(document.body).fontFamily);
    expect(loginFont).toContain('Poppins');

    await page.goto('/');
    const indexFont = await page.evaluate(() => window.getComputedStyle(document.body).fontFamily);
    expect(indexFont).toContain('Poppins');
  });

  test('Passbook and other sidebar modules share identical Poppins font & style', async ({ page }) => {
    // 1. Login as fauzan (multi-role: BM / Teller / CS)
    await page.goto('/login');
    await page.fill('#username', 'fauzan');
    await page.fill('#password', 'fauzan');
    await page.click('#login-button');
    await page.waitForURL('**/dashboard');

    const checkPageStyle = async (url: string, activeLinkId?: string) => {
      await page.goto(url);
      await page.waitForLoadState('domcontentloaded');

      const styles = await page.evaluate((linkId) => {
        const body = window.getComputedStyle(document.body);
        const sidebar = document.querySelector('.cs-sidebar');
        const sidebarStyle = sidebar ? window.getComputedStyle(sidebar) : null;
        const activeLink = linkId ? document.querySelector(`#${linkId}`) : null;
        const activeLinkStyle = activeLink ? window.getComputedStyle(activeLink) : null;

        return {
          bodyFont: body.fontFamily,
          bodyBg: body.backgroundColor,
          sidebarFont: sidebarStyle ? sidebarStyle.fontFamily : null,
          activeLinkFont: activeLinkStyle ? activeLinkStyle.fontFamily : null
        };
      }, activeLinkId);

      expect(styles.bodyFont, `Body font on ${url} should contain Poppins`).toContain('Poppins');
      expect(styles.sidebarFont, `Sidebar font on ${url} should contain Poppins`).toContain('Poppins');
      // Background color should be #f8fafc (rgb(248, 250, 252))
      expect(styles.bodyBg, `Body background on ${url} should be #f8fafc`).toBe('rgb(248, 250, 252)');
    };

    // Verify Dashboard
    await checkPageStyle('/dashboard', 'dashboard-link');

    // Verify Passbook (the one the user specifically flagged!)
    await checkPageStyle('/teller/passbook/select-account', 'passbook-link');

    // Verify Passbook page title and content card
    const passbookTitle = page.locator('.page-title');
    await expect(passbookTitle).toBeVisible();
    await expect(passbookTitle).toContainText('Passbook');

    const passbookCard = page.locator('.content-card');
    await expect(passbookCard).toBeVisible();

    // Verify Transaksi
    await checkPageStyle('/teller/transaction/list', 'transaction-link');

    // Verify Setoran Tunai & Penarikan Tunai pages styling
    await checkPageStyle('/teller/transaction/deposit');
    await checkPageStyle('/teller/transaction/withdrawal');

    // Verify Customers
    await checkPageStyle('/cs/customers', 'customer-link');

    // Verify Account
    await checkPageStyle('/cs/account', 'account-link');

    // Verify Produk
    await checkPageStyle('/cs/product', 'product-link');

    // Verify Settings (should route cleanly without 404 and have Poppins)
    await page.goto('/settings');
    await page.waitForLoadState('domcontentloaded');
    expect(page.url()).toContain('/teller/settings');
    const settingsFont = await page.evaluate(() => window.getComputedStyle(document.body).fontFamily);
    expect(settingsFont).toContain('Poppins');
  });

});

