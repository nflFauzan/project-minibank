import { Page, expect } from '@playwright/test';

export class LoginPage {
  readonly page: Page;
  readonly usernameInput: any;
  readonly passwordInput: any;
  readonly loginButton: any;
  readonly errorMessage: any;
  readonly logoutMessage: any;
  readonly moduleSelect: any;

  constructor(page: Page) {
    this.page = page;
    this.usernameInput = page.locator('input[name="username"]');
    this.passwordInput = page.locator('input[name="password"]');
    this.loginButton = page.locator('button[type="submit"]');
    this.errorMessage = page.locator('.msg-error');
    this.logoutMessage = page.locator('.msg-success');
    this.moduleSelect = page.locator('select[name="module"]');
  }

  async navigate() {
    await this.page.goto('/login');
  }

  async login(username: string, password: string, module?: string) {
    await this.usernameInput.fill(username);
    await this.passwordInput.fill(password);
    
    if (await this.moduleSelect.isVisible().catch(() => false)) {
      if (module) {
        await this.moduleSelect.selectOption(module);
      }
    }
    
    await this.loginButton.click();
  }

  async expectErrorMessage(message: string) {
    await expect(this.errorMessage).toContainText(message);
  }

  async expectLogoutMessage() {
    await expect(this.logoutMessage).toBeVisible();
  }
}
