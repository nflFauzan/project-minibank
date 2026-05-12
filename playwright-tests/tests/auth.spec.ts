import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

test.describe('Authentication & Authorization', () => {
  
  test('Login failed with wrong credentials', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.navigate();
    await loginPage.login('wronguser', 'wrongpass');
    
    // TODO: verify selector against actual HTML
    await loginPage.expectErrorMessage('Login gagal');
  });

  test('Logout success', async ({ page }) => {
    const loginPage = new LoginPage(page);
    // First login
    await loginPage.navigate();
    await loginPage.login('zann', 'zann');
    
    // Then logout
    // TODO: verify selector against actual HTML (logout button)
    const logoutBtn = page.locator('a:has-text("Logout"), button:has-text("Logout")');
    await logoutBtn.click();
    
    await expect(page).toHaveURL(/\/login/);
    await loginPage.expectLogoutMessage();
  });

  test('Role-based access: CS cannot access Admin area', async ({ page }) => {
    const loginPage = new LoginPage(page);
    // Login as CS
    await loginPage.navigate();
    await loginPage.login('zann', 'zann');
    
    // Attempt to access Admin dashboard
    await page.goto('/admin/dashboard');
    
    // Expect redirect to login or a forbidden page
    // TODO: verify behavior against actual application
    const url = page.url();
    expect(url).not.toContain('/admin/dashboard');
  });
});
