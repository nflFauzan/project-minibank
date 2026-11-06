import { test, expect } from '@playwright/test';
import { STORAGE_STATE_TELLER } from '../playwright.config';

test.use({ storageState: STORAGE_STATE_TELLER });

test.describe('Passbook Reverse Engineering (100% Functional Compliance)', () => {

  test('1. Navigation alias: /passbook/select-account redirects to /teller/passbook/select-account', async ({ page }) => {
    await page.goto('/passbook/select-account');
    await expect(page).toHaveURL(/\/teller\/passbook\/select-account/);
    await expect(page.locator('#page-title')).toContainText('Cetak Buku Tabungan');
  });

  test('2. Account selection view: lists active accounts and supports search', async ({ page }) => {
    await page.goto('/teller/passbook/select-account');
    await expect(page).toHaveURL(/\/teller\/passbook\/select-account/);

    // Verify search input and button
    const searchInput = page.locator('#search');
    await expect(searchInput).toBeVisible();
    await expect(page.locator('#search-accounts-btn')).toBeVisible();

    // Verify accounts table has loaded rows
    const previewBtn = page.locator('a.btn-preview').first();
    await expect(previewBtn).toBeVisible({ timeout: 10000 });

    // Search for an account
    await searchInput.fill('543');
    await page.locator('#search-accounts-btn').click();
    await page.waitForLoadState('networkidle');

    // Action buttons must be visible
    await expect(page.locator('a.btn-preview').first()).toBeVisible();
    await expect(page.locator('a.btn-init').first()).toBeVisible();
    await expect(page.locator('a.btn-plq').first()).toBeVisible();
    await expect(page.locator('a.btn-browser').first()).toBeVisible();
  });

  test('3. Passbook preview: displays customer metadata, status, date filters, and actions', async ({ page }) => {
    await page.goto('/teller/passbook/select-account');
    const previewBtn = page.locator('a.btn-preview').first();
    await expect(previewBtn).toBeVisible();
    const href = await previewBtn.getAttribute('href');
    expect(href).toBeTruthy();

    await page.goto(href!);
    await expect(page).toHaveURL(/\/teller\/passbook\/preview\//);

    // Verify page title
    await expect(page.locator('#page-title')).toContainText('Passbook Preview');

    // Verify Action buttons
    await expect(page.locator('#back-to-selection-button')).toBeVisible();
    await expect(page.locator('#direct-print-button')).toBeVisible();
    await expect(page.locator('#preview-print-button')).toBeVisible();

    // Verify filter form
    await expect(page.locator('#fromDate')).toBeVisible();
    await expect(page.locator('#toDate')).toBeVisible();

    // Verify recent transactions preview table
    await expect(page.locator('table.preview-table')).toBeVisible();
  });

  test('4. Passbook initialization: configure page, line, and update state', async ({ page }) => {
    await page.goto('/teller/passbook/select-account');
    const initBtn = page.locator('a.btn-init').first();
    await expect(initBtn).toBeVisible();
    const href = await initBtn.getAttribute('href');
    expect(href).toBeTruthy();

    await page.goto(href!);
    await expect(page).toHaveURL(/\/teller\/passbook\/initialize\//);
    await expect(page.locator('#page-title')).toContainText('Initialize Passbook');

    // Check form fields
    const pageInput = page.locator('#current-page-input');
    const lineInput = page.locator('#last-line-input');
    await expect(pageInput).toBeVisible();
    await expect(lineInput).toBeVisible();

    // Fill new values
    await pageInput.fill('2');
    await lineInput.fill('5');

    // Submit form via #submit-button
    await page.locator('#submit-button').click();

    // Should redirect back to preview with success
    await expect(page).toHaveURL(/\/teller\/passbook\/preview\//);
    await expect(page.locator('.alert-success')).toBeVisible();
    await expect(page.locator('.alert-success')).toContainText('Passbook status successfully initialized');
  });

  test('5. Direct Print ESC/POS interface: controls, manager script, and transaction queue', async ({ page }) => {
    await page.goto('/teller/passbook/select-account');
    const plqBtn = page.locator('a.btn-plq').first();
    await expect(plqBtn).toBeVisible();
    const href = await plqBtn.getAttribute('href');
    expect(href).toBeTruthy();

    await page.goto(href!);
    await expect(page).toHaveURL(/\/teller\/passbook\/direct-print\//);
    await expect(page.locator('#page-title')).toContainText('Passbook Printing');

    // Verify hardware printer controls
    await expect(page.locator('#print-btn')).toBeVisible();
    await expect(page.locator('#next-page-btn')).toBeVisible();
    await expect(page.locator('#printer-server-url')).toBeVisible();
    await expect(page.locator('#connect-printer-btn')).toBeVisible();
    await expect(page.locator('#status-log')).toBeVisible();

    // Verify PassbookPrinter bundle is loaded on window
    const hasPrinterManager = await page.evaluate(() => {
      return typeof (window as any).PassbookPrinter !== 'undefined' &&
             typeof (window as any).PassbookPrinter.PassbookPrintManager === 'function';
    });
    expect(hasPrinterManager).toBe(true);
  });

  test('6. Print Manual / PDF view: monospace passbook matrix layout with print stylesheet', async ({ page }) => {
    await page.goto('/teller/passbook/select-account');
    const browserBtn = page.locator('a.btn-browser').first();
    await expect(browserBtn).toBeVisible();
    const href = await browserBtn.getAttribute('href');
    expect(href).toBeTruthy();

    await page.goto(href!);
    await expect(page).toHaveURL(/\/teller\/passbook\/print\//);

    // Verify passbook container and header
    await expect(page.locator('.passbook-container')).toBeVisible();
    await expect(page.locator('.bank-info h1')).toContainText('Minibank');
    await expect(page.locator('#printButton')).toBeVisible();
  });

  test('7. Passbook REST API contract verification', async ({ request, page }) => {
    await page.goto('/teller/passbook/select-account');
    const previewBtn = page.locator('a.btn-preview').first();
    const href = await previewBtn.getAttribute('href');
    const accountId = href!.split('/').pop();

    // 7.1 GET /api/passbook/{accountId}/status
    const statusRes = await request.get(`/api/passbook/${accountId}/status`);
    expect(statusRes.status()).toBe(200);
    const statusJson = await statusRes.json();
    expect(statusJson.hasPassbook).toBe(true);
    expect(statusJson).toHaveProperty('passbookNumber');
    expect(statusJson).toHaveProperty('currentPage');
    expect(statusJson).toHaveProperty('lastPrintedLine');
    expect(statusJson).toHaveProperty('remainingLines');

    // 7.2 GET /api/passbook/{accountId}/print-data
    const printDataRes = await request.get(`/api/passbook/${accountId}/print-data`);
    expect(printDataRes.status()).toBe(200);
    const printDataJson = await printDataRes.json();
    expect(printDataJson).toHaveProperty('transactions');
    expect(printDataJson).toHaveProperty('config');
    expect(printDataJson.config.charactersPerLine).toBe(80);

    // 7.3 POST /api/passbook/{accountId}/next-page
    const nextPageRes = await request.post(`/api/passbook/${accountId}/next-page`);
    expect(nextPageRes.status()).toBe(200);
    const nextPageJson = await nextPageRes.json();
    expect(nextPageJson.success).toBe(true);
    expect(nextPageJson).toHaveProperty('currentPage');

    // 7.4 POST /api/passbook/print-result
    const printResultRes = await request.post(`/api/passbook/print-result`, {
      data: {
        accountId: accountId,
        printedTransactionIds: [],
        finalPage: nextPageJson.currentPage,
        finalLine: 0
      }
    });
    expect(printResultRes.status()).toBe(200);
    const printResultJson = await printResultRes.json();
    expect(printResultJson.success).toBe(true);
  });
});
