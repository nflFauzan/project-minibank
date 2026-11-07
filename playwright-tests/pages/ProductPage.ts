import { Page, expect } from '@playwright/test';

export class ProductPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigateToList() {
    await this.page.goto('/cs/product');
  }

  async navigateToCreateForm() {
    await this.page.goto('/cs/product/new');
  }

  async fillProductForm(data: {
    kodeProduk: string;
    namaProduk: string;
    deskripsiSingkat?: string;
    jenisAkad: string;   // e.g. 'WADIAH', 'MUDHARABAH'
    setoranAwalMinimum: string; // e.g. '100000'
  }) {
    const code = this.page.locator('#productCode, #kodeProduk').first();
    await code.fill(data.kodeProduk);

    const name = this.page.locator('#productName, #namaProduk').first();
    await name.fill(data.namaProduk);

    if (data.deskripsiSingkat) {
      const desc = this.page.locator('#description, #deskripsiSingkat').first();
      if (await desc.count() > 0) await desc.fill(data.deskripsiSingkat);
    }

    const typeSelect = this.page.locator('#productType').first();
    if (await typeSelect.count() > 0) {
      const val = data.jenisAkad.toUpperCase().startsWith('TABUNGAN_')
        ? data.jenisAkad.toUpperCase()
        : ('TABUNGAN_' + data.jenisAkad.toUpperCase());
      await typeSelect.selectOption(val).catch(async () => {
        await typeSelect.selectOption({ index: 4 });
      });
    }

    // Akad select
    const akadSelect = this.page.locator('select[name="jenisAkad"], #profitSharingType, #jenisAkad').first();
    if (await akadSelect.count() > 0) {
      await akadSelect.selectOption(data.jenisAkad).catch(() => {});
    }

    const setoran = this.page.locator('#minimumOpeningBalance, #setoranAwalMinimum').first();
    await setoran.fill(data.setoranAwalMinimum);
  }

  async submitForm() {
    await this.page.locator('button[type="submit"]').click();
  }

  async expectProductInList(namaProduk: string) {
    await this.navigateToList();
    await expect(this.page.locator('table')).toContainText(namaProduk);
  }

  async clickEditProduct(namaProduk: string) {
    const row = this.page.locator('tr', { hasText: namaProduk }).first();
    const editBtn = row.locator('a:has-text("Edit"), a[href*="/edit"]');
    await editBtn.click();
  }

  async clickToggleStatus(namaProduk: string) {
    const row = this.page.locator('tr', { hasText: namaProduk }).first();
    const toggleBtn = row.locator('button[type="submit"], form[action*="/toggle"] button, form[action*="/delete"] button');
    await toggleBtn.click();
  }

  async clickDetailProduct(namaProduk: string) {
    const row = this.page.locator('tr', { hasText: namaProduk }).first();
    const detailLink = row.locator('a:has-text("Detail"), a:has-text("View"), a[href*="/cs/product/"], a[href*="/product/view/"]').first();
    await detailLink.click();
  }

  async expectSuccessFlash() {
    await expect(this.page.locator('.alert-success, [class*="success"]')).toBeVisible();
  }

  async expectErrorFlash() {
    await expect(this.page.locator('.alert-danger, [class*="error"]')).toBeVisible();
  }

  async expectListTableVisible() {
    await expect(this.page.locator('table')).toBeVisible();
  }
}
