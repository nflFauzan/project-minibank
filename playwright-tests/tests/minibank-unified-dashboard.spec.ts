import { test, expect } from '@playwright/test';

test.describe('Minibank Unified Dashboard - Native UI & Flow Verification', () => {

  test('Login page retains 100% native glassmorphism styling and structure', async ({ page }) => {
    await page.goto('/login');

    // Native Glassmorphism card & title
    await expect(page.locator('.glass-card')).toBeVisible();
    await expect(page.locator('#page-title')).toHaveText('Welcome Back!');

    // Form inputs and buttons
    await expect(page.locator('#username')).toBeVisible();
    await expect(page.locator('#password')).toBeVisible();
    await expect(page.locator('.password-eye')).toBeVisible();
    await expect(page.locator('#forgot-password-link')).toBeVisible();
    await expect(page.locator('#login-button')).toBeVisible();
    await expect(page.locator('#signup-link')).toBeVisible();

    // Verify NO module dropdown exists
    await expect(page.locator('select[name="module"]')).toHaveCount(0);
  });

  test('CS login lands directly on unified /dashboard with native CS view', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'zann');
    await page.fill('#password', 'zann');
    await page.click('#login-button');

    // Must stay strictly on unified /dashboard URL, NOT /cs/dashboard
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Shell, greeting card and user info
    await expect(page.locator('.cs-shell')).toBeVisible();
    await expect(page.locator('.greeting-card')).toBeVisible();
    await expect(page.locator('#header-username')).toHaveText('zann');
    await expect(page.locator('#header-role')).toHaveText('Customer Service');

    // Sidebar navigation
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#product-link')).toBeVisible();
    await expect(page.locator('#customer-link')).toBeVisible();
    await expect(page.locator('#account-link')).toBeVisible();

    // Quick Actions for CS
    await expect(page.locator('#new-customer-button')).toBeVisible();
    await expect(page.locator('#open-account-button')).toBeVisible();
    await expect(page.locator('#account-lookup-button')).toBeVisible();

    // Stats cards
    await expect(page.locator('#statistics-cards')).toBeVisible();
    await expect(page.locator('.stat-card')).toHaveCount(4);
  });

  test('Teller login lands directly on unified /dashboard with native Teller view', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'teller');
    await page.fill('#password', 'teller1234');
    await page.click('#login-button');

    // Must stay strictly on unified /dashboard URL, NOT /teller/dashboard
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Shell, greeting card and user info
    await expect(page.locator('.cs-shell')).toBeVisible();
    await expect(page.locator('.greeting-card')).toBeVisible();
    await expect(page.locator('#header-username')).toHaveText('teller');
    await expect(page.locator('#header-role')).toHaveText('Teller');

    // Sidebar navigation for Teller
    await expect(page.locator('#dashboard-link')).toBeVisible();
    await expect(page.locator('#transaction-link')).toBeVisible();
    await expect(page.locator('#deposit-link')).toBeVisible();
    await expect(page.locator('#withdrawal-link')).toBeVisible();
    await expect(page.locator('#transfer-link')).toBeVisible();
    await expect(page.locator('#passbook-link')).toBeVisible();

    // Quick Actions for Teller
    await expect(page.locator('#process-transaction-button')).toBeVisible();
    await expect(page.locator('#reports-button')).toBeVisible();
  });

  test('Admin login lands directly on unified /dashboard with native Admin view & RBAC', async ({ page }) => {
    await page.goto('/login');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'admin123');
    await page.click('#login-button');

    // Must stay strictly on unified /dashboard URL, NOT /admin/dashboard
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // Shell and user info
    await expect(page.locator('.cs-shell')).toBeVisible();
    await expect(page.locator('.greeting-card')).toBeVisible();
    await expect(page.locator('#header-username')).toHaveText('admin');

    // Admin-specific actions and tables
    await expect(page.locator('#create-user-button')).toBeVisible();
    await expect(page.locator('.admin-approval-section')).toBeVisible();
    await expect(page.locator('text=Pengelolaan Pengguna & Hak Akses (RBAC)')).toBeVisible();

    // Logout and redirect to login
    await page.click('#logout-button');
    await page.waitForURL('**/login*');
    await expect(page.locator('#success-message')).toBeVisible();
  });

  test('Sub-dashboard endpoints are completely eliminated and return HTTP 404', async ({ page }) => {
    // Login first as admin to have valid session
    await page.goto('/login');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'admin123');
    await page.click('#login-button');
    await page.waitForURL('**/dashboard');

    // Direct access to all old sub-dashboards MUST return 404
    const resCs = await page.goto('/cs/dashboard');
    expect(resCs?.status()).toBe(404);

    const resTeller = await page.goto('/teller/dashboard');
    expect(resTeller?.status()).toBe(404);

    const resAdmin = await page.goto('/admin/dashboard');
    expect(resAdmin?.status()).toBe(404);

    const resSpv = await page.goto('/supervisor/dashboard');
    expect(resSpv?.status()).toBe(404);
  });
});
