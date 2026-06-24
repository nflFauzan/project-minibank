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
      fullName: `Test User ${unique}`,
      dosenPembimbing: 'Dr. Dosen Test',
      email: `testuser_${unique}@example.com`,
      username: `testuser_${unique}`,
      prodi: 'Informatics',
      password: 'password123',
      nim: `NIM${unique}`,
      confirmPassword: 'password123',
    });
    await signupPage.submitForm();

    // Setelah signup berhasil, redirect ke /login dengan pesan
    await signupPage.expectRegisteredMessage();
  });

  test('Registrasi dengan field wajib kosong (Negative Test)', async ({ page }) => {
    await signupPage.navigate();
    // Submit tanpa mengisi apapun — browser validation akan mencegah,
    // tapi kita bisa test server-side validation juga
    // Isi hanya sebagian field
    await page.locator('#fullName').fill('Test');
    await page.locator('#username').fill('');  // kosong
    await signupPage.submitForm();

    // Seharusnya tetap di halaman signup
    await expect(page).toHaveURL(/\/signup/);
  });
});
