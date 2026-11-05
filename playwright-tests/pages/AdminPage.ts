import { Page, expect } from '@playwright/test';

export class AdminPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigateToDashboard() {
    await this.page.goto('/dashboard');
  }

  async expectDashboardVisible() {
    await expect(this.page).toHaveURL(/\/dashboard/);
    await expect(this.page.locator('h2, .section-title, .greeting-title')).toBeVisible();
  }

  async hasPendingUsers(): Promise<boolean> {
    const detailLinks = this.page.locator('a.detail, a.action-link');
    return (await detailLinks.count()) > 0;
  }

  async clickFirstPendingUser() {
    const link = this.page.locator('a.detail, a.action-link').first();
    await link.click();
  }

  async approveUser() {
    await this.page.locator('button.btn-approve:has-text("APPROVE")').click();
  }

  async rejectUser() {
    await this.page.locator('button.btn-reject:has-text("REJECT")').click();
  }

  async expectSuccessFlash(keyword: RegExp | string = /approved|berhasil|sukses/i) {
    const body = await this.page.locator('body').innerText();
    expect(body).toMatch(keyword instanceof RegExp ? keyword : new RegExp(keyword, 'i'));
  }
}
