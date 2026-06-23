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
    await expect(this.page.locator('h1, .page-title, [class*="title"]')).toBeVisible();
  }

  async expectPendingUsersTable() {
    await expect(this.page.locator('table')).toBeVisible();
  }

  async clickApprovalDetail(userId: number | string) {
    await this.page.goto(`/admin/approval/${userId}`);
  }

  /** Klik approval detail dari baris pertama di table */
  async clickFirstPendingUser() {
    const link = this.page.locator('table tr a').first();
    await link.click();
  }

  async approveUser() {
    await this.page.locator('form[action*="/approve"] button[type="submit"], button:has-text("Approve")').first().click();
  }

  async rejectUser() {
    await this.page.locator('form[action*="/reject"] button[type="submit"], button:has-text("Reject")').first().click();
  }

  async expectSuccessFlash(keyword: RegExp | string = /approved|berhasil|sukses/i) {
    await expect(this.page.locator('.alert-success, [class*="success"]')).toContainText(keyword);
  }

  async hasPendingUsers(): Promise<boolean> {
    const rows = this.page.locator('table tbody tr');
    return (await rows.count()) > 0;
  }
}
