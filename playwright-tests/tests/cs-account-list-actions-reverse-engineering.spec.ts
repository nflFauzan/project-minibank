import { test, expect } from '@playwright/test';
import { STORAGE_STATE_CS } from '../playwright.config';

test.use({ storageState: STORAGE_STATE_CS });

test.describe('CS Account List & Actions Reverse Engineering (100% minibank.tazkia.ac.id Compliance)', () => {

  test('1. Navigation & Route Alias: /account/list redirects to /cs/account', async ({ page }) => {
    await page.goto('/account/list');
    await expect(page).toHaveURL(/\/cs\/account/);

    // Verify Page Header matching minibank.tazkia.ac.id
    const heading = page.locator('h1');
    await expect(heading).toContainText('Account Management');

    // Verify Open New Account button
    const openBtn = page.locator('a:has-text("Open New Account")');
    await expect(openBtn).toBeVisible();
    await expect(openBtn).toHaveAttribute('href', /\/cs\/account\/open|\/account\/open/);
  });

  test('2. Toolbar & Table Columns: Exact Layout matching minibank.tazkia.ac.id', async ({ page }) => {
    await page.goto('/cs/account');

    // Verify search input
    const searchInput = page.locator('input[name="search"]');
    await expect(searchInput).toBeVisible();
    await expect(searchInput).toHaveAttribute('placeholder', 'Search by account number or account name...');

    // Verify status dropdown options
    const statusSelect = page.locator('select[name="status"]');
    await expect(statusSelect).toBeVisible();
    await expect(statusSelect.locator('option[value="ALL"]')).toContainText('All Statuses');
    await expect(statusSelect.locator('option[value="ACTIVE"]')).toHaveCount(1);
    await expect(statusSelect.locator('option[value="CLOSED"]')).toHaveCount(1);

    // Verify table headers (8 columns)
    const headers = page.locator('table thead th');
    await expect(headers.nth(0)).toContainText('Account Number');
    await expect(headers.nth(1)).toContainText('Account Name');
    await expect(headers.nth(2)).toContainText('Customer');
    await expect(headers.nth(3)).toContainText('Product');
    await expect(headers.nth(4)).toContainText('Balance');
    await expect(headers.nth(5)).toContainText('Status');
    await expect(headers.nth(6)).toContainText('Opened Date');
    await expect(headers.nth(7)).toContainText('Actions');

    // Verify at least one row exists and format matches
    const firstRow = page.locator('table tbody tr').first();
    await expect(firstRow).toBeVisible();
    
    // Check pipe-separated action links
    await expect(firstRow.locator('a:has-text("View")')).toBeVisible();
    await expect(firstRow.locator('a:has-text("Transactions")')).toBeVisible();
  });

  test('3. Action View (/account/view/{id}): Account Detail & KYC Information', async ({ page }) => {
    await page.goto('/cs/account');

    const firstRow = page.locator('table tbody tr').first();
    const viewLink = firstRow.locator('a:has-text("View")').first();
    await viewLink.click();

    // Expect detail page
    await expect(page).toHaveURL(/\/cs\/account\/\d+/);
    await expect(page.locator('h1')).toContainText('Account Detail');

    // Check account information & KYC details
    await expect(page.locator('text=Nomor Rekening')).toBeVisible();
    await expect(page.locator('text=Account Name')).toBeVisible();
    await expect(page.locator('text=Informasi Nasabah')).toBeVisible();
    await expect(page.locator('text=Current Balance')).toBeVisible();

    // Verify Quick Actions
    const transactionsBtn = page.locator('a:has-text("View Transactions")');
    await expect(transactionsBtn).toBeVisible();
  });

  test('4. Action Transactions (/transaction/account/{id}): Complete Mutasi Rekening & Statement', async ({ page }) => {
    await page.goto('/cs/account');

    const firstRow = page.locator('table tbody tr').first();
    const trxLink = firstRow.locator('a:has-text("Transactions")').first();
    await trxLink.click();

    // Expect transactions page
    await expect(page).toHaveURL(/\/cs\/account\/\d+\/transactions/);
    await expect(page.locator('h1')).toContainText('Account Transactions (Mutasi Rekening)');

    // Verify Account Summary & Inflow/Outflow statistics
    await expect(page.locator('text=Account Information')).toBeVisible();
    await expect(page.locator('text=Current Balance')).toBeVisible();
    await expect(page.locator('text=Total Credit / Masuk (+)')).toBeVisible();
    await expect(page.locator('text=Total Debit / Keluar (-)')).toBeVisible();

    // Verify Filter Toolbar & Print Button
    await expect(page.locator('button:has-text("Print Statement")')).toBeVisible();
    await expect(page.locator('input[name="startDate"]')).toBeVisible();
    await expect(page.locator('input[name="endDate"]')).toBeVisible();
    await expect(page.locator('select[name="type"]')).toBeVisible();

    // Verify Transactions Table Headers
    const trxHeaders = page.locator('table thead th');
    await expect(trxHeaders.nth(0)).toContainText('Date & Time');
    await expect(trxHeaders.nth(1)).toContainText('Reference No');
    await expect(trxHeaders.nth(2)).toContainText('Type');
    await expect(trxHeaders.nth(3)).toContainText('Description');
    await expect(trxHeaders.nth(4)).toContainText('Debit (-)');
    await expect(trxHeaders.nth(5)).toContainText('Credit (+)');
    await expect(trxHeaders.nth(6)).toContainText('Balance');
  });

  test('5. Action Close (/account/{id}/close): Dedicated Close Page matching minibank.tazkia.ac.id', async ({ page }) => {
    // Open a new account first to close safely without altering initial seeded accounts
    await page.goto('/cs/account/open');
    const firstCard = page.locator('.customer-card').first();
    await firstCard.locator('a:has-text("Open Account")').click();
    await expect(page).toHaveURL(/\/cs\/account\/open\/\d+/);

    const uniqueName = `Temporary Close Test ${Date.now()}`;
    await page.locator('#accountName, input[name="namaRekening"]').fill(uniqueName);
    await page.locator('button[type="submit"]').click();
    await expect(page).toHaveURL(/\/cs\/account/);

    // Search for newly created account
    await page.locator('input[name="search"]').fill(uniqueName);
    await page.locator('button:has-text("Search")').click();

    const targetRow = page.locator('table tbody tr', { hasText: uniqueName }).first();
    await expect(targetRow).toBeVisible();

    // Click Close action link
    const closeLink = targetRow.locator('a:has-text("Close")');
    await closeLink.click();

    // Verify Dedicated Close Page matching minibank.tazkia.ac.id
    await expect(page).toHaveURL(/\/cs\/account\/\d+\/close/);
    await expect(page.locator('h1')).toContainText('Close Account');
    await expect(page.locator('a:has-text("Back to Account List")')).toBeVisible();

    // Check Account Information Card
    await expect(page.locator('h2:has-text("Account Information")')).toBeVisible();
    await expect(page.locator('text=Account Number:')).toBeVisible();
    await expect(page.locator('text=Current Balance:')).toBeVisible();

    // Check Account Closure Warning Card
    await expect(page.locator('h3:has-text("Account Closure Warning")')).toBeVisible();
    await expect(page.locator('text=The account balance must be exactly IDR 0.00 (zero)')).toBeVisible();
    await expect(page.locator('text=All pending transactions must be completed')).toBeVisible();
    await expect(page.locator('li:has-text("This action cannot be undone")')).toBeVisible();

    // Fill Reason and check Confirmation Checkbox
    await page.locator('textarea[name="reason"]').fill('Permintaan nasabah untuk penutupan rekening uji coba');
    await page.locator('#confirmCheckbox').check();

    // Submit closure
    await page.locator('#submitCloseBtn').click();

    // Expect redirect to account list with success notification
    await expect(page).toHaveURL(/\/cs\/account/);
    await expect(page.locator('.alert-success')).toContainText('berhasil ditutup');
  });

});

