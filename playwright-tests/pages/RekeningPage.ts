import { Page, expect } from '@playwright/test';

export class RekeningPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigateToList() {
    await this.page.goto('/cs/account');
  }

  async navigateToOpenAccount() {
    await this.page.goto('/cs/account/open');
  }

  async selectNasabah(name: string) {
    // Navigate to the search page if not already there
    if (!this.page.url().includes('/cs/account/open')) {
      await this.navigateToOpenAccount();
    }
    
    // Fill search input and submit
    await this.page.locator('input[name="search"]').fill(name);
    await this.page.locator('button:has-text("Cari Nasabah")').click();
    
    // Select the nasabah from the list (clicking the "Pilih & Buka Rekening" button in the correct card)
    const card = this.page.locator('.customer-card', { hasText: name }).first();
    await card.locator('a:has-text("Pilih & Buka Rekening")').click();
    
    // Should now be on the form page
    await expect(this.page).toHaveURL(/\/cs\/account\/open\/\d+/);
  }

  async fillAccountForm(data: any) {
    // TODO: verify selector against actual HTML
    if (data.produk) {
      await this.page.locator('select[name="produkId"]').selectOption({ label: data.produk });
    }
    await this.page.locator('input[name="nominalSetoranAwal"]').fill(data.setoran);
    await this.page.locator('input[name="tujuanPembukaan"]').fill(data.tujuan);
  }

  async submitForm() {
    // TODO: verify selector against actual HTML
    await this.page.locator('button[type="submit"]').click();
  }

  async expectAccountInList(accountNumber: string) {
    await this.navigateToList();
    // TODO: verify selector against actual HTML
    await expect(this.page.locator('table')).toContainText(accountNumber);
  }

  async viewAccountDetail(accountNumber: string) {
    // TODO: verify selector against actual HTML
    const row = this.page.locator('tr', { hasText: accountNumber }).first();
    await row.locator('a:has-text("View")').click();
  }

  async expectValidationErrors() {
    // TODO: verify selector against actual HTML
    await expect(this.page.locator('.alert-danger, .error-message')).toBeVisible();
  }
}
