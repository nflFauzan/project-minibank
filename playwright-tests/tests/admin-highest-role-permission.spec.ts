import { test, expect } from '@playwright/test';

test.describe('Admin Superuser Highest Role Permission Suite', () => {

  test('Admin user has access to all CS, Teller, Supervisor, and Admin modules with Branch Manager identity', async ({ page }) => {
    // 1. Login as admin
    await page.goto('/login');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'minibank123');
    await page.click('#login-button');

    // Wait for URL redirect to dashboard
    await page.waitForURL('**/dashboard', { timeout: 15_000 });
    expect(page.url()).toMatch(/\/dashboard$/);

    // 2. Verify Topbar identity
    await expect(page.locator('#header-username')).toHaveText('admin');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');

    // 3. Verify all Dashboard Action Sections are visible for Admin superuser
    // CS Quick Actions
    await expect(page.locator('#new-customer-button')).toBeVisible();
    await expect(page.locator('#open-account-button')).toBeVisible();
    await expect(page.locator('#account-lookup-button')).toBeVisible();

    // Teller Quick Actions
    await expect(page.locator('#process-transaction-button')).toBeVisible();
    await expect(page.locator('#reports-button')).toBeVisible();

    // Supervisor Quick Actions
    await expect(page.getByText('Antrean Approval Nasabah')).toBeVisible();

    // Admin Section
    await expect(page.getByText('Antrean Approval Registrasi Pengguna Baru')).toBeVisible();
    await expect(page.locator('#create-user-button')).toBeVisible();

    // 4. Verify all Sidebar Navigation Links are visible
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

    // 5. Cross-module Navigation: CS module (/cs/customers)
    await customerLink.click();
    await page.waitForURL('**/cs/customers');
    expect(page.url()).toContain('/cs/customers');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(customerLink).toHaveClass(/active/);
    await expect(transactionLink).toBeVisible();
    await expect(approvalQueueLink).toBeVisible();

    // 6. Cross-module Navigation: Teller module (/teller/transaction/list)
    await transactionLink.click();
    await page.waitForURL('**/teller/transaction/list');
    expect(page.url()).toContain('/teller/transaction/list');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(transactionLink).toHaveClass(/active/);
    await expect(customerLink).toBeVisible();

    // 7. Cross-module Navigation: Teller deposit (/teller/transaction/deposit)
    await depositLink.click();
    await page.waitForURL('**/teller/transaction/deposit');
    expect(page.url()).toContain('/teller/transaction/deposit');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');

    // 8. Cross-module Navigation: Supervisor approval queue (/supervisor/nasabah)
    await approvalQueueLink.click();
    await page.waitForURL('**/supervisor/nasabah');
    expect(page.url()).toContain('/supervisor/nasabah');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(approvalQueueLink).toHaveClass(/active/);

    // 9. Admin Direct Navigation: /admin/users/create
    await page.goto('/admin/users/create');
    await page.waitForURL('**/admin/users/create');
    expect(page.url()).toContain('/admin/users/create');
    await expect(page.locator('h2')).toHaveText('Buat Akun Pengguna Langsung');

    // Return to dashboard via back button
    await page.locator('a.back').click();
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(page.locator('#header-username')).toHaveText('admin');
  });

});

