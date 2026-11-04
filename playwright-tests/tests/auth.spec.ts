import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

test.describe('Authentication & Authorization', () => {
  
  test('Login failed with wrong credentials', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.navigate();
    await loginPage.login('wronguser', 'wrongpass');
    
    await loginPage.expectErrorMessage('Login gagal');
  });

  test('Logout success', async ({ page }) => {
    const loginPage = new LoginPage(page);
    // First login
    await loginPage.navigate();
    await loginPage.login('zann', 'zann');
    
    // Then logout
    const logoutBtn = page.locator('a:has-text("Logout"), button:has-text("Logout")');
    await logoutBtn.first().click();
    
    await expect(page).toHaveURL(/\/login/);
    await loginPage.expectLogoutMessage();
  });

  test('Role-based access: CS cannot access Admin area', async ({ page }) => {
    const loginPage = new LoginPage(page);
    // Login as CS
    await loginPage.navigate();
    await loginPage.login('zann', 'zann');
    
    // Attempt to access Admin dashboard directly
    const response = await page.goto('/admin/dashboard');
    
    // Spring Security enforces RBAC and returns HTTP 403 Forbidden
    expect(response?.status()).toBe(403);
  });
});
