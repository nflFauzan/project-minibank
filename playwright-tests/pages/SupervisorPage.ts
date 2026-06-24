import { Page, expect } from '@playwright/test';

export class SupervisorPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigateToDashboard() {
    await this.page.goto('/supervisor/dashboard');
  }

  async expectDashboardVisible() {
    await expect(this.page).toHaveURL(/\/supervisor\/dashboard/);
    await expect(this.page.locator('body')).toBeVisible();
  }

  /** Navigasi ke daftar nasabah dengan filter opsional: PENDING | APPROVED | REJECTED | ALL */
  async navigateToNasabahList(filter?: 'PENDING' | 'APPROVED' | 'REJECTED' | 'ALL') {
    const url = filter
      ? `/supervisor/nasabah?filter=${filter}`
      : '/supervisor/nasabah';
    await this.page.goto(url);
  }

  async expectNasabahListVisible() {
    await expect(this.page.locator('table, .nasabah-list, ul')).toBeVisible();
  }

  async clickFirstNasabahDetail() {
    const link = this.page.locator('table tr a, .nasabah-list a').first();
    await link.click();
  }

  async viewNasabahDetail(id: number | string) {
    await this.page.goto(`/supervisor/nasabah/${id}`);
  }

  async approveNasabah(notes?: string) {
    if (notes) {
      const notesField = this.page.locator('textarea[name="notes"], #notes');
      if (await notesField.count() > 0) {
        await notesField.fill(notes);
      }
    }
    await this.page.locator('form[action*="/approve"] button[type="submit"], button:has-text("Approve")').first().click();
  }

  async rejectNasabah(reason: string, notes?: string) {
    const reasonField = this.page.locator('input[name="reason"], textarea[name="reason"], #reason');
    await reasonField.fill(reason);
    if (notes) {
      const notesField = this.page.locator('textarea[name="notes"], #notes');
      if (await notesField.count() > 0) {
        await notesField.fill(notes);
      }
    }
    await this.page.locator('form[action*="/reject"] button[type="submit"], button:has-text("Reject")').first().click();
  }

  async expectSuccessFlash(keyword: RegExp | string = /berhasil|sukses/i) {
    await expect(this.page.locator('.alert-success, [class*="success"]')).toContainText(keyword);
  }

  async expectErrorFlash(keyword: RegExp | string = /wajib|error|gagal/i) {
    await expect(this.page.locator('.alert-danger, .alert-warning, [class*="error"]')).toContainText(keyword);
  }

  async hasPendingNasabah(): Promise<boolean> {
    const rows = this.page.locator('table tbody tr, .nasabah-list li');
    return (await rows.count()) > 0;
  }
}
