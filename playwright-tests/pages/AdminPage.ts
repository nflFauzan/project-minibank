import { Page, expect } from '@playwright/test';

export class AdminPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigateToDashboard() {
    await this.page.goto('/admin/dashboard');
  }

  async expectDashboardVisible() {
    await expect(this.page).toHaveURL(/\/admin\/dashboard/);
    // Admin dashboard uses h2 with "Pending Registrations"
    await expect(this.page.locator('h2')).toContainText('Pending Registrations');
  }

  async hasPendingUsers(): Promise<boolean> {
    // Table only rendered if users list is not empty
    const table = this.page.locator('table');
    return (await table.count()) > 0;
  }

  async clickFirstPendingUser() {
    // Link uses class "detail" with text "Lihat / Konfirmasi"
    const link = this.page.locator('a.detail').first();
    await link.click();
  }

  async approveUser() {
    // button with class btn-approve and text APPROVE
    await this.page.locator('button.btn-approve:has-text("APPROVE")').click();
  }

  async rejectUser() {
    // button with class btn-reject and text REJECT
    await this.page.locator('button.btn-reject:has-text("REJECT")').click();
  }

  async expectSuccessFlash(keyword: RegExp | string = /approved|berhasil|sukses/i) {
    const body = await this.page.locator('body').innerText();
    expect(body).toMatch(keyword instanceof RegExp ? keyword : new RegExp(keyword, 'i'));
  }
}
