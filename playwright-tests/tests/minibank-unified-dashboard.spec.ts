import { test, expect } from '@playwright/test';

test.describe('Minibank Unified Dashboard - Reverse Engineered Flow', () => {

  test('Login page matches minibank.tazkia.ac.id aesthetics and structure', async ({ page }) => {
    await page.goto('/login');

    // Branding header
    await expect(page.locator('#page-title')).toHaveText('Minibank');
    await expect(page.locator('text=Please sign in to continue')).toBeVisible();

    // Form inputs and buttons
    await expect(page.locator('#username')).toBeVisible();
    await expect(page.locator('#password')).toBeVisible();
    await expect(page.locator('#remember-me')).toBeVisible();
    await expect(page.locator('#forgot-password-link')).toBeVisible();
    await expect(page.locator('#login-button')).toBeVisible();
    await expect(page.locator('#signup-link')).toBeVisible();
  });

  test('CS login lands directly on /dashboard with Customer Service perspective', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'zann');
    await page.fill('#password', 'zann');
    await page.click('#login-button');

    // Must stay on unified /dashboard URL, NOT /cs/dashboard
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Top bar user info
    await expect(page.locator('#header-username')).toHaveText('zann');
    await expect(page.locator('#header-role')).toHaveText('Customer Service');

    // Sidebar navigation
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#product-link')).toBeVisible();
    await expect(page.locator('#customer-link')).toBeVisible();
    await expect(page.locator('#account-link')).toBeVisible();
    await expect(page.locator('#passbook-link')).toBeVisible();
    await expect(page.locator('#reports-link')).toBeVisible();

    // Transaction and Admin sections hidden from CS
    await expect(page.locator('#transaction-link')).toHaveCount(0);
    await expect(page.locator('#approval-queue-link')).toHaveCount(0);
    await expect(page.locator('#administration-section')).toHaveCount(0);

    // Quick Actions for CS
    await expect(page.locator('#new-customer-button')).toBeVisible();
    await expect(page.locator('#open-account-button')).toBeVisible();
    await expect(page.locator('#account-lookup-button')).toBeVisible();
    await expect(page.locator('#process-transaction-button')).toHaveCount(0);

    // Stats cards visible
    await expect(page.locator('#statistics-cards')).toBeVisible();
  });

  test('Teller login lands directly on /dashboard with Teller perspective', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'teller');
    await page.fill('#password', 'teller1234');
    await page.click('#login-button');

    // Must stay on unified /dashboard URL, NOT /teller/dashboard
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Top bar user info
    await expect(page.locator('#header-username')).toHaveText('teller');
    await expect(page.locator('#header-role')).toHaveText('Teller');

    // Sidebar navigation
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#transaction-link')).toBeVisible();
    await expect(page.locator('#passbook-link')).toBeVisible();
    await expect(page.locator('#approval-queue-link')).toHaveCount(0);
    await expect(page.locator('#administration-section')).toHaveCount(0);

    // Quick Actions for Teller
    await expect(page.locator('#process-transaction-button')).toBeVisible();
    await expect(page.locator('#reports-button')).toBeVisible();
    await expect(page.locator('#account-lookup-button')).toBeVisible();
    await expect(page.locator('#new-customer-button')).toHaveCount(0);
  });

  test('Admin login lands directly on /dashboard with Branch Manager perspective', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'admin123');
    await page.click('#login-button');

    // Must stay on unified /dashboard URL, NOT /admin/dashboard
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Top bar user info
    await expect(page.locator('#header-username')).toHaveText('admin');
    await expect(page.locator('#header-role')).toHaveText('Branch Manager');

    // Full Sidebar navigation
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#product-link')).toBeVisible();
    await expect(page.locator('#customer-link')).toBeVisible();
    await expect(page.locator('#account-link')).toBeVisible();
    await expect(page.locator('#transaction-link')).toBeVisible();
    await expect(page.locator('#passbook-link')).toBeVisible();
    await expect(page.locator('#approval-queue-link')).toBeVisible();
    await expect(page.locator('#administration-section')).toBeVisible();
    await expect(page.locator('#users-link')).toBeVisible();
    await expect(page.locator('#roles-link')).toBeVisible();
    await expect(page.locator('#permissions-link')).toBeVisible();

    // Quick Actions for Admin
    await expect(page.locator('#new-customer-button')).toBeVisible();
    await expect(page.locator('#open-account-button')).toBeVisible();
    await expect(page.locator('#process-transaction-button')).toBeVisible();
    await expect(page.locator('#reports-button')).toBeVisible();
    await expect(page.locator('#create-user-button')).toBeVisible();
    await expect(page.locator('#account-lookup-button')).toBeVisible();
    await expect(page.locator('#system-config-link')).toBeVisible();

    // User dropdown and logout
    await page.click('#userMenuButton');
    await expect(page.locator('#userMenu')).toBeVisible();
    await expect(page.locator('#user-menu-username')).toHaveText('admin');
    await expect(page.locator('#user-menu-role')).toHaveText('Branch Manager');

    await page.click('#logout-button');
    await page.waitForURL('**/login*');
    await expect(page.locator('#success-message')).toBeVisible();
  });
});

