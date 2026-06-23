import { test, expect } from '@playwright/test';
import { SignupPage } from '../pages/SignupPage';

test.describe('Registrasi Akun (Signup)', () => {
  let signupPage: SignupPage;

  test.beforeEach(async ({ page }) => {
    signupPage = new SignupPage(page);
  });

  test('Halaman signup bisa diakses tanpa login', async ({ page }) => {
    await signupPage.navigate();
    await expect(page).toHaveURL(/\/signup/);
    await expect(page.locator('form')).toBeVisible();
  });

  test('Registrasi akun baru berhasil', async ({ page }) => {
    await signupPage.navigate();
    const unique = Date.now();
    await signupPage.fillSignupForm({
      username: `testuser_${unique}`,
      password: 'password123',
      confirmPassword: 'password123',
      fullName: `Test User ${unique}`,
      email: `testuser_${unique}@example.com`,
      role: 'CS',
    });
    await signupPage.submitForm();

    // Setelah signup, redirect ke /login dengan pesan registrasi berhasil
    await signupPage.expectRegisteredMessage();
  });

  test('Registrasi dengan password tidak cocok (Negative Test)', async ({ page }) => {
    await signupPage.navigate();
    const unique = Date.now();
    await signupPage.fillSignupForm({
      username: `testuser_${unique}`,
      password: 'password123',
      confirmPassword: 'berbeda456',
      fullName: `Test User ${unique}`,
      email: `testuser_${unique}@example.com`,
      role: 'CS',
    });
    await signupPage.submitForm();

    // Seharusnya tetap di halaman signup dengan error validasi
    await expect(page).toHaveURL(/\/signup/);
    await signupPage.expectValidationError();
  });
});
