import { Page, expect } from '@playwright/test';

export class SignupPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigate() {
    await this.page.goto('/signup');
  }

  async fillSignupForm(data: {
    fullName: string;
    dosenPembimbing: string;
    email: string;
    username: string;
    prodi: string;       // 'Informatics' | 'Accounting' | 'Sharia Business Management'
    password: string;
    nim: string;
    confirmPassword: string;
  }) {
    // Thymeleaf th:field generates id from field name
    await this.page.locator('#fullName').fill(data.fullName);
    await this.page.locator('#dosenPembimbing').fill(data.dosenPembimbing);
    await this.page.locator('#email').fill(data.email);
    await this.page.locator('#username').fill(data.username);
    // Prodi is a select
    await this.page.locator('#prodi').selectOption(data.prodi);
    await this.page.locator('#password').fill(data.password);
    await this.page.locator('#nim').fill(data.nim);
    // Confirm password has name="confirmPasswordVisualOnly" (not a th:field)
    await this.page.locator('input[name="confirmPasswordVisualOnly"]').fill(data.confirmPassword);
  }

  async submitForm() {
    await this.page.locator('button[type="submit"]').click();
  }

  async expectRegisteredMessage() {
    // After successful signup, redirected to /login?registered
    await expect(this.page).toHaveURL(/\/login/);
    await expect(this.page.locator('.msg-success')).toContainText(/Registrasi berhasil/i);
  }

  async expectValidationError() {
    // Stays on /signup with .err div visible
    await expect(this.page.locator('.err')).toBeVisible();
  }
}
