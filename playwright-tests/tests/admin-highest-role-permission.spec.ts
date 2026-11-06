import { test, expect } from '@playwright/test';

test.describe('Admin Superuser Highest Role Permission Suite', () => {

  test('Admin user has access to all CS, Teller, Supervisor, and Admin modules with Branch Manager identity', async ({ page }) => {
    test.setTimeout(60_000);
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

    // 4. Verify Sidebar Navigation Links (clean teller links: Transaksi & Passbook)
    const dashboardLink = page.locator('#dashboard-link');
    const customerLink = page.locator('#customer-link');
    const accountLink = page.locator('#account-link');
    const productLink = page.locator('#product-link');
    const transactionLink = page.locator('#transaction-link');
    const passbookLink = page.locator('#passbook-link');
    const approvalQueueLink = page.locator('#approval-queue-link');

    await expect(dashboardLink).toBeVisible();
    await expect(customerLink).toBeVisible();
    await expect(accountLink).toBeVisible();
    await expect(productLink).toBeVisible();
    await expect(transactionLink).toBeVisible();
    await expect(passbookLink).toBeVisible();
    await expect(approvalQueueLink).toBeVisible();

    await expect(page.locator('#deposit-link')).toHaveCount(0);
    await expect(page.locator('#withdrawal-link')).toHaveCount(0);
    await expect(page.locator('#transfer-link')).toHaveCount(0);

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

    // 7. Cross-module Navigation: Teller passbook (/teller/passbook/select-account)
    await passbookLink.click();
    await page.waitForURL('**/teller/passbook/select-account');
    expect(page.url()).toContain('/teller/passbook/select-account');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');

    // 8. Cross-module Navigation: Supervisor approval queue (/supervisor/nasabah)
    await approvalQueueLink.click();
    await page.waitForURL('**/supervisor/nasabah');
    expect(page.url()).toContain('/supervisor/nasabah');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(approvalQueueLink).toHaveClass(/active/);

    // 9. Admin Direct Navigation: /admin/users/create redirects to unified form /rbac/users/create
    await page.goto('/admin/users/create');
    await page.waitForURL('**/rbac/users/create');
    expect(page.url()).toContain('/rbac/users/create');
    await expect(page.locator('h1')).toHaveText('Create User');

    // Return to dashboard via sidebar link
    await page.click('#dashboard-link');
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');
    await expect(page.locator('#header-username')).toHaveText('admin');
  });

});

