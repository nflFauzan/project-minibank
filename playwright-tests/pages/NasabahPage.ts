import { Page, expect } from '@playwright/test';

export class NasabahPage {
  readonly page: Page;
  
  constructor(page: Page) {
    this.page = page;
  }

  async navigateToList() {
    await this.page.goto('/cs/customers');
  }

  async navigateToRegistration() {
    await this.page.goto('/cs/customers/new');
  }

  async fillRegistrationForm(data: any) {
    // TODO: verify selector against actual HTML
    await this.page.locator('#nik').fill(data.nik);
    await this.page.locator('#namaLengkap').fill(data.nama);
    await this.page.locator('#tempatLahir').fill(data.tempatLahir);
    await this.page.locator('#tanggalLahir').fill(data.tanggalLahir);
    await this.page.locator('#namaIbuKandung').fill(data.ibuKandung);
    
    if (data.jenisKelamin) {
      await this.page.locator(`input[name="jenisKelamin"][value="${data.jenisKelamin}"]`).check();
    }
    
    await this.page.locator('#alamatIdentitas').fill(data.alamat);
  }

  async triggerPostalCodeAutofill(code: string) {
    // TODO: verify selector against actual HTML
    const postalInput = this.page.locator('#kodePosIdentitas');
    await postalInput.fill(code);
    await postalInput.blur(); // Trigger blur event for autofill
    
    // Wait for network idle or a specific change if possible
    await this.page.waitForLoadState('networkidle');
  }

  async expectPostalCodeData(city: string, province: string) {
    // TODO: verify selector against actual HTML
    const citySelect = this.page.locator('#kotaIdentitas');
    const provinceSelect = this.page.locator('#provinsiIdentitas');
    
    await expect(citySelect).toHaveValue(city);
    await expect(provinceSelect).toHaveValue(province);
  }

  async submitForm() {
    // TODO: verify selector against actual HTML
    await this.page.locator('button[type="submit"]').click();
  }

  async expectCustomerInList(name: string) {
    await this.navigateToList();
    // TODO: verify selector against actual HTML
    await expect(this.page.locator('table')).toContainText(name);
  }

  async viewCustomerDetail(name: string) {
    // TODO: verify selector against actual HTML
    const row = this.page.locator('tr', { hasText: name }).first();
    await row.locator('a:has-text("View")').click();
  }

  async expectValidationErrors() {
    // TODO: verify selector against actual HTML
    await expect(this.page.locator('.alert-danger')).toBeVisible();
  }
}
