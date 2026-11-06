import { test, expect } from '@playwright/test';

test.describe('Unified Sidebar Consistency across All Modules', () => {

  test('Multi-role user (fauzan) retains all links and consistent header across CS, Teller, and Supervisor modules', async ({ page }) => {
    // 1. Login as fauzan
    await page.goto('/login');
    await page.fill('#username', 'fauzan');
    await page.fill('#password', 'fauzan');
    await page.click('#login-button');

    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Verify header on dashboard
    await expect(page.locator('#header-username')).toHaveText('fauzan');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');

    // All links must be visible on Dashboard
    const dashboardLink = page.locator('#dashboard-link');
    const customerLink = page.locator('#customer-link');
    const accountLink = page.locator('#account-link');
    const productLink = page.locator('#product-link');
    const transactionLink = page.locator('#transaction-link');
    const depositLink = page.locator('#deposit-link');
    const withdrawalLink = page.locator('#withdrawal-link');
    const transferLink = page.locator('#transfer-link');
    const passbookLink = page.locator('#passbook-link');
    const approvalQueueLink = page.locator('#approval-queue-link');

    await expect(dashboardLink).toBeVisible();
    await expect(customerLink).toBeVisible();
    await expect(accountLink).toBeVisible();
    await expect(productLink).toBeVisible();
    await expect(transactionLink).toBeVisible();
    await expect(depositLink).toBeVisible();
    await expect(withdrawalLink).toBeVisible();
    await expect(transferLink).toBeVisible();
    await expect(passbookLink).toBeVisible();
    await expect(approvalQueueLink).toBeVisible();

    // 2. Click Customers -> Navigate to /cs/customers
    await customerLink.click();
    await page.waitForURL('**/cs/customers');

    // All 10 links MUST remain visible on CS page!
    await expect(dashboardLink).toBeVisible();
    await expect(customerLink).toBeVisible();
    await expect(accountLink).toBeVisible();
    await expect(productLink).toBeVisible();
    await expect(transactionLink).toBeVisible();
    await expect(depositLink).toBeVisible();
    await expect(withdrawalLink).toBeVisible();
    await expect(transferLink).toBeVisible();
    await expect(passbookLink).toBeVisible();
    await expect(approvalQueueLink).toBeVisible();

    // Check active class on customer-link
    await expect(customerLink).toHaveClass(/active/);

    // Topbar header role and username MUST remain consistent!
    await expect(page.locator('#header-username')).toHaveText('fauzan');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(page.locator('.cs-brand-text')).toContainText('Tazkia Minibank');

    // 3. Click Transaksi -> Navigate to /teller/transaction/list
    await transactionLink.click();
    await page.waitForURL('**/teller/transaction/list');

    // All 10 links MUST remain visible on Teller page!
    await expect(dashboardLink).toBeVisible();
    await expect(customerLink).toBeVisible();
    await expect(accountLink).toBeVisible();
    await expect(productLink).toBeVisible();
    await expect(transactionLink).toBeVisible();
    await expect(depositLink).toBeVisible();
    await expect(withdrawalLink).toBeVisible();
    await expect(transferLink).toBeVisible();
    await expect(passbookLink).toBeVisible();
    await expect(approvalQueueLink).toBeVisible();

    // Check active class on transaction-link
    await expect(transactionLink).toHaveClass(/active/);
    await expect(page.locator('#header-username')).toHaveText('fauzan');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(page.locator('.cs-brand-text')).toContainText('Tazkia Minibank');

    // 4. Click Approval Nasabah -> Navigate to /supervisor/nasabah
    await approvalQueueLink.click();
    await page.waitForURL('**/supervisor/nasabah');

    // All 10 links MUST remain visible on Supervisor page!
    await expect(dashboardLink).toBeVisible();
    await expect(customerLink).toBeVisible();
    await expect(accountLink).toBeVisible();
    await expect(productLink).toBeVisible();
    await expect(transactionLink).toBeVisible();
    await expect(depositLink).toBeVisible();
    await expect(withdrawalLink).toBeVisible();
    await expect(transferLink).toBeVisible();
    await expect(passbookLink).toBeVisible();
    await expect(approvalQueueLink).toBeVisible();

    // Check active class on approval-queue-link
    await expect(approvalQueueLink).toHaveClass(/active/);
    await expect(page.locator('#header-username')).toHaveText('fauzan');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(page.locator('.cs-brand-text')).toContainText('Tazkia Minibank');
  });

  test('Single-role user (teller) sees strictly teller links and cannot see CS or Supervisor links', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'teller');
    await page.fill('#password', 'teller1234');
    await page.click('#login-button');

    await page.waitForURL('**/dashboard');

    // Teller links must be visible
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#transaction-link')).toBeVisible();
    await expect(page.locator('#deposit-link')).toBeVisible();
    await expect(page.locator('#withdrawal-link')).toBeVisible();
    await expect(page.locator('#transfer-link')).toBeVisible();
    await expect(page.locator('#passbook-link')).toBeVisible();

    // CS, Supervisor, Admin links must NOT be visible
    await expect(page.locator('#customer-link')).toHaveCount(0);
    await expect(page.locator('#account-link')).toHaveCount(0);
    await expect(page.locator('#product-link')).toHaveCount(0);
    await expect(page.locator('#approval-queue-link')).toHaveCount(0);
    await expect(page.locator('#admin-user-create-link')).toHaveCount(0);

    // Navigate to transaction list
    await page.click('#transaction-link');
    await page.waitForURL('**/teller/transaction/list');

    // On transaction list, teller links still visible, other roles still NOT visible
    await expect(page.locator('#transaction-link')).toBeVisible();
    await expect(page.locator('#customer-link')).toHaveCount(0);
    await expect(page.locator('#approval-queue-link')).toHaveCount(0);
    await expect(page.locator('#header-role')).toHaveText('Teller');
    await expect(page.locator('#header-username')).toHaveText('teller');
  });

  test('Single-role user (zann) sees strictly CS links and cannot see Teller or Supervisor links', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'zann');
    await page.fill('#password', 'zann');
    await page.click('#login-button');

    await page.waitForURL('**/dashboard');

    // CS links visible
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#customer-link')).toBeVisible();
    await expect(page.locator('#account-link')).toBeVisible();
    await expect(page.locator('#product-link')).toBeVisible();

    // Teller, Supervisor, Admin links must NOT be visible
    await expect(page.locator('#transaction-link')).toHaveCount(0);
    await expect(page.locator('#deposit-link')).toHaveCount(0);
    await expect(page.locator('#approval-queue-link')).toHaveCount(0);
    await expect(page.locator('#admin-user-create-link')).toHaveCount(0);

    // Navigate to customers
    await page.click('#customer-link');
    await page.waitForURL('**/cs/customers');

    // On customers, CS links still visible, other roles still NOT visible
    await expect(page.locator('#customer-link')).toBeVisible();
    await expect(page.locator('#transaction-link')).toHaveCount(0);
    await expect(page.locator('#approval-queue-link')).toHaveCount(0);
    await expect(page.locator('#header-role')).toHaveText('Customer Service');
    await expect(page.locator('#header-username')).toHaveText('zann');
  });

});

