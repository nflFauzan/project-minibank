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
    username: string;
    password: string;
    confirmPassword: string;
    fullName: string;
    email: string;
    role: string; // e.g. 'CS', 'TELLER', 'SUPERVISOR'
  }) {
    await this.page.locator('#username').fill(data.username);
    await this.page.locator('#password').fill(data.password);
    await this.page.locator('#confirmPassword').fill(data.confirmPassword);
    await this.page.locator('#fullName').fill(data.fullName);
    await this.page.locator('#email').fill(data.email);
    // Role select
    const roleSelect = this.page.locator('select[name="role"], #role');
    if (await roleSelect.count() > 0) {
      await roleSelect.selectOption(data.role);
    }
  }

  async submitForm() {
    await this.page.locator('button[type="submit"]').click();
  }

  async expectRegisteredMessage() {
    // After successful signup, redirected to /login?registered
    await expect(this.page).toHaveURL(/\/login/);
    await expect(this.page.locator('body')).toContainText(/registrasi|berhasil|registered|sukses/i);
  }

  async expectValidationError() {
    await expect(this.page.locator('.alert-danger, .error, [class*="error"]')).toBeVisible();
  }
}
