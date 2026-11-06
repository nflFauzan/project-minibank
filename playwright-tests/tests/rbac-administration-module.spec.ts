import { test, expect } from '@playwright/test';

test.describe('RBAC Administration Module Suite', () => {

  test('Admin can navigate and manage Users, Roles, and Permissions in RBAC module', async ({ page }) => {
    test.setTimeout(90_000);

    // 1. Login as admin
    await page.goto('/login');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'minibank123');
    await page.click('#login-button');

    // Wait for URL redirect to dashboard
    await page.waitForURL('**/dashboard', { timeout: 15_000 });
    expect(page.url()).toMatch(/\/dashboard$/);

    // 2. Verify Administration navigation links in sidebar
    const usersLink = page.locator('#admin-users-link');
    const rolesLink = page.locator('#admin-roles-link');
    const permsLink = page.locator('#admin-permissions-link');

    await expect(usersLink).toBeVisible();
    await expect(rolesLink).toBeVisible();
    await expect(permsLink).toBeVisible();

    // ──────────────────────────────────────────────────────────────────────────
    // 3. USERS MODULE (/rbac/users/list)
    // ──────────────────────────────────────────────────────────────────────────
    await usersLink.click();
    await page.waitForURL('**/rbac/users/list');
    expect(page.url()).toContain('/rbac/users/list');
    await expect(page.locator('h1')).toHaveText('User Management');

    // Search existing user
    await page.fill('input[name="search"]', 'admin');
    await page.click('button:has-text("Search")');
    await expect(page.locator('table')).toContainText('Administrator');

    // Create User Form
    await page.click('#btn-create-user');
    await page.waitForURL('**/rbac/users/create');
    expect(page.url()).toContain('/rbac/users/create');
    await expect(page.locator('h1')).toHaveText('Create User');

    const testUser = 'rbac_test_' + Date.now();
    await page.fill('#fullName', 'RBAC Playwright Tester');
    await page.fill('#username', testUser);
    await page.fill('#email', testUser + '@tazkia.ac.id');
    await page.fill('#password', 'TestPassword123');

    // Toggle password visibility
    await page.click('#togglePasswordBtn');
    expect(await page.locator('#password').getAttribute('type')).toBe('text');
    await page.click('#togglePasswordBtn');
    expect(await page.locator('#password').getAttribute('type')).toBe('password');

    // Academic fields & datalist
    await page.fill('#nim', '211011001');
    await page.fill('#prodi', 'Akuntansi Syariah');
    await page.fill('#dosenPembimbing', 'Dr. M. Syafii Antonio');

    // Branch selection
    await page.selectOption('#branch', { index: 1 });

    // Role checkboxes: select Teller in addition to CS
    const tellerCheck = page.locator('input[name="roles"][value="ROLE_TELLER"]');
    await tellerCheck.check();

    // Submit form
    await page.click('#btn-submit-user');

    // Redirected back to list with success alert
    await page.waitForURL('**/rbac/users/list');
    await expect(page.locator('.alert-success')).toBeVisible();

    // Search and View newly created user
    await page.fill('input[name="search"]', testUser);
    await page.click('button:has-text("Search")');
    await expect(page.locator('table')).toContainText(testUser);

    const userRow = page.locator('tr', { hasText: testUser });
    await userRow.locator('a.action-view').click();
    await page.waitForURL('**/rbac/users/view/**');
    await expect(page.locator('h1')).toHaveText('View User');
    await expect(page.getByText(testUser, { exact: true })).toBeVisible();
    await expect(page.getByText('Akuntansi Syariah')).toBeVisible();
    await expect(page.getByText('211011001')).toBeVisible();

    // ──────────────────────────────────────────────────────────────────────────
    // 4. ROLES MODULE (/rbac/roles/list)
    // ──────────────────────────────────────────────────────────────────────────
    await rolesLink.click();
    await page.waitForURL('**/rbac/roles/list');
    expect(page.url()).toContain('/rbac/roles/list');
    await expect(page.locator('h1')).toHaveText('Role Management');

    // Verify existing default roles exist
    await expect(page.locator('table')).toContainText('ROLE_ADMIN');
    await expect(page.locator('table')).toContainText('ROLE_CS');
    await expect(page.locator('table')).toContainText('ROLE_TELLER');

    // Create Role Form
    await page.click('a:has-text("+ Create Role")');
    await page.waitForURL('**/rbac/roles/create');
    await expect(page.locator('h1')).toHaveText('Create Role');

    const testRoleCode = 'ROLE_PW_' + Date.now().toString().slice(-4);
    await page.fill('#roleCode', testRoleCode);
    await page.fill('#roleName', 'Playwright Automated Role');
    await page.fill('#description', 'Role created by automated Playwright test');
    await page.click('button:has-text("Create Role")');

    // Redirected back to roles list
    await page.waitForURL('**/rbac/roles/list');
    await expect(page.locator('.alert-success')).toBeVisible();

    // ──────────────────────────────────────────────────────────────────────────
    // 5. PERMISSIONS MODULE (/rbac/permissions/list)
    // ──────────────────────────────────────────────────────────────────────────
    await permsLink.click();
    await page.waitForURL('**/rbac/permissions/list');
    expect(page.url()).toContain('/rbac/permissions/list');
    await expect(page.locator('h1')).toHaveText('Permission Management');

    // Filter by category
    await page.selectOption('select[name="category"]', 'CUSTOMER');
    await page.click('button:has-text("Filter")');
    await page.waitForURL('**/rbac/permissions/list?**');
    await expect(page.locator('table')).toContainText('CUSTOMER_VIEW');

    // Create Permission Form
    await page.click('a:has-text("+ Create Permission")');
    await page.waitForURL('**/rbac/permissions/create');
    await expect(page.locator('h1')).toHaveText('Create Permission');

    const testPermCode = 'PERM_PW_' + Date.now().toString().slice(-4);
    await page.fill('#permissionCode', testPermCode);
    await page.fill('#permissionName', 'Playwright Test Permission');
    await page.selectOption('#category', 'USER');
    await page.fill('#description', 'Automated test permission description');
    await page.click('button:has-text("Create Permission")');

    // Redirected back to permissions list
    await page.waitForURL('**/rbac/permissions/list');
    await expect(page.locator('.alert-success')).toBeVisible();

    // 6. Return to Dashboard and verify everything is healthy
    await page.click('#dashboard-link');
    await page.waitForURL('**/dashboard');
    expect(page.url()).toMatch(/\/dashboard$/);

    // 7. Verify /admin/users/create redirects cleanly to /rbac/users/create
    await page.goto('/admin/users/create');
    await page.waitForURL('**/rbac/users/create');
    expect(page.url()).toContain('/rbac/users/create');
    await expect(page.locator('h1')).toHaveText('Create User');
  });

});

