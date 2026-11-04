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
    // Submit tanpa mengisi apapun — browser native validation (required) akan mencegah submit.
    // Kita isi hanya sebagian field, lalu cek form tidak ter-submit.
    await page.locator('#fullName').fill('Test');
    await page.locator('#username').fill('');  // kosong

    // Klik submit — HTML5 required validation akan mencegah pengiriman
    await page.locator('button[type="submit"]').click();

    // Tunggu sebentar untuk memastikan tidak ada navigasi
    await page.waitForTimeout(1000);

    // Seharusnya tetap di halaman signup karena browser validation mencegah submit
    await expect(page).toHaveURL(/\/signup/);
  });
});
