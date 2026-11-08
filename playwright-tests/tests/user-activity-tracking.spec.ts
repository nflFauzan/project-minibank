import { test, expect } from '@playwright/test';

test.describe('User Activity Tracking & Audit Trail Suite', () => {

  test('Admin can access User Activity Tracking page and view User Profile Activity Breakdown', async ({ page }) => {
    test.setTimeout(90_000);

    // 1. Login sebagai Admin
    await page.goto('/login');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'minibank123');
    await page.click('#login-button');

    await page.waitForURL('**/dashboard', { timeout: 15_000 });
    expect(page.url()).toMatch(/\/dashboard$/);

    // 2. Navigasi Sidebar ke "User Activities"
    const auditLink = page.locator('#admin-audit-link');
    await expect(auditLink).toBeVisible();
    await auditLink.click();

    await page.waitForURL('**/audit/activities');
    expect(page.url()).toContain('/audit/activities');
    await expect(page.locator('h1')).toHaveText('User Activity Tracking');

    // 3. Verifikasi Filter Form
    const userSelect = page.locator('#filter-username');
    const moduleSelect = page.locator('#filter-module');
    const qInput = page.locator('#filter-q');
    const exportBtn = page.locator('#export-csv-btn');

    await expect(userSelect).toBeVisible();
    await expect(moduleSelect).toBeVisible();
    await expect(qInput).toBeVisible();
    await expect(exportBtn).toBeVisible();
    expect(await exportBtn.getAttribute('href')).toContain('/audit/activities/export');

    // 4. Lakukan filter pencarian
    await qInput.fill('admin');
    await page.click('#filter-submit-btn');
    await page.waitForURL('**/audit/activities?**');

    // 5. Verifikasi Halaman Detail Pengguna (/rbac/users/view/1)
    await page.goto('/rbac/users/view/1');
    await expect(page.locator('h1')).toHaveText('View User');

    // Verifikasi Kartu Statistik Aktivitas
    const statCards = page.locator('.stat-card');
    await expect(statCards.first()).toBeVisible();
    await expect(page.locator('text=Nasabah Didaftarkan')).toBeVisible();
    await expect(page.locator('text=Rekening Dibuka')).toBeVisible();
    await expect(page.locator('text=Transaksi Teller')).toBeVisible();

    // Verifikasi Section Riwayat Aktivitas & Tabs
    const activitySection = page.locator('#activity-tracking-section');
    await expect(activitySection).toBeVisible();

    // Klik Tab "Nasabah Didaftarkan"
    await page.click('button:has-text("Nasabah Didaftarkan")');
    await expect(page.locator('#tab-nasabah')).toHaveClass(/active/);

    // Klik Tab "Rekening Dibuka"
    await page.click('button:has-text("Rekening Dibuka")');
    await expect(page.locator('#tab-rekening')).toHaveClass(/active/);

    // Klik Tab "Transaksi Teller"
    await page.click('button:has-text("Transaksi Teller")');
    await expect(page.locator('#tab-transaksi')).toHaveClass(/active/);

    // Klik Tab "Timeline Aktivitas"
    await page.click('button:has-text("Timeline Aktivitas")');
    await expect(page.locator('#tab-timeline')).toHaveClass(/active/);
  });

  test('Supervisor also has access to User Activities menu', async ({ page }) => {
    test.setTimeout(90_000);

    // Login sebagai Supervisor
    await page.goto('/login');
    await page.fill('#username', 'supervisor');
    await page.fill('#password', 'minibank123');
    await page.click('#login-button');

    await page.waitForURL('**/dashboard', { timeout: 15_000 });

    // Link User Activities harus terlihat untuk supervisor
    const auditLink = page.locator('#admin-audit-link');
    await expect(auditLink).toBeVisible();
    await auditLink.click();

    await page.waitForURL('**/audit/activities');
    expect(page.url()).toContain('/audit/activities');
    await expect(page.locator('h1')).toHaveText('User Activity Tracking');
  });

});

