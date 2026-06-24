import { Page, expect } from '@playwright/test';

export class TellerPage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  // ─── Dashboard ─────────────────────────────────────────────────────────────

  async navigateToDashboard() {
    await this.page.goto('/teller/dashboard');
  }

  async expectDashboardVisible() {
    await expect(this.page).toHaveURL(/\/teller\/dashboard/);
    await expect(this.page.locator('body')).toBeVisible();
  }

  // ─── Transaction List ──────────────────────────────────────────────────────

  async navigateToTransactionList() {
    await this.page.goto('/teller/transaction/list');
  }

  async expectTransactionListVisible() {
    await expect(this.page.locator('table')).toBeVisible();
  }

  // ─── Deposit ───────────────────────────────────────────────────────────────

  async navigateToDepositSelect() {
    await this.page.goto('/teller/transaction/deposit');
  }

  /** Search and click "Pilih Rekening" on first account card */
  async searchAndSelectRekeningForDeposit(query: string) {
    // Search input has name="q"
    const searchInput = this.page.locator('input[name="q"]');
    await searchInput.fill(query);
    // Click Cari button
    await this.page.locator('button:has-text("Cari")').click();
    await this.page.waitForLoadState('networkidle');
    // Cards layout — click "Pilih Rekening" link on first card
    const selectBtn = this.page.locator('a.btn-primary-custom:has-text("Pilih Rekening")').first();
    await selectBtn.click();
  }

  async fillDepositForm(data: {
    jumlahSetoran: string;
    keterangan?: string;
    noReferensi?: string;
  }) {
    // Thymeleaf th:field="${form.jumlahSetoran}" generates id="jumlahSetoran"
    await this.page.locator('#jumlahSetoran').fill(data.jumlahSetoran);
    if (data.keterangan) {
      await this.page.locator('#keterangan').fill(data.keterangan);
    }
    if (data.noReferensi) {
      await this.page.locator('#noReferensi').fill(data.noReferensi);
    }
  }

  // ─── Withdrawal ────────────────────────────────────────────────────────────

  async navigateToWithdrawalSelect() {
    await this.page.goto('/teller/transaction/withdrawal');
  }

  async searchAndSelectRekeningForWithdrawal(query: string) {
    const searchInput = this.page.locator('input[name="q"]');
    await searchInput.fill(query);
    await this.page.locator('button:has-text("Cari")').click();
    await this.page.waitForLoadState('networkidle');
    const selectBtn = this.page.locator('a.btn-withdrawal-gradient:has-text("Pilih Rekening")').first();
    await selectBtn.click();
  }

  async fillWithdrawalForm(data: {
    jumlahPenarikan: string;
    keterangan?: string;
    noReferensi?: string;
  }) {
    // Thymeleaf th:field="*{jumlahPenarikan}" generates id="jumlahPenarikan"
    await this.page.locator('#jumlahPenarikan').fill(data.jumlahPenarikan);
    if (data.keterangan) {
      await this.page.locator('#keterangan').fill(data.keterangan);
    }
    if (data.noReferensi) {
      await this.page.locator('#noReferensi').fill(data.noReferensi);
    }
  }

  // ─── Transfer ──────────────────────────────────────────────────────────────

  async navigateToTransferSelectSource() {
    await this.page.goto('/teller/transaction/transfer');
  }

  async selectSourceRekening(query: string) {
    const searchInput = this.page.locator('input[name="q"]');
    if (query) {
      await searchInput.fill(query);
      await this.page.locator('button:has-text("Cari")').click();
      await this.page.waitForLoadState('networkidle');
    }
    // Click "Pilih Rekening" on first card
    const selectBtn = this.page.locator('a.btn-primary-gradient:has-text("Pilih Rekening")').first();
    await selectBtn.click();
  }

  async selectTargetRekening(query: string) {
    // After selecting source, we're on select target page
    if (query) {
      const searchInput = this.page.locator('input[name="q"]');
      await searchInput.fill(query);
      await this.page.locator('button:has-text("Cari")').click();
      await this.page.waitForLoadState('networkidle');
    }
    // Click "Pilih Tujuan" or "Pilih Rekening" on first card
    const selectBtn = this.page.locator('a.btn-primary-gradient:has-text("Pilih Tujuan")').first();
    await selectBtn.click();
  }

  async fillTransferForm(data: {
    jumlah: string;
    keteranganTambahan?: string;
    noReferensi?: string;
  }) {
    // Thymeleaf th:field="*{jumlah}" generates id="jumlah"
    await this.page.locator('#jumlah').fill(data.jumlah);
    if (data.keteranganTambahan) {
      await this.page.locator('#keteranganTambahan').fill(data.keteranganTambahan);
    }
    if (data.noReferensi) {
      await this.page.locator('#noReferensi').fill(data.noReferensi);
    }
  }

  // ─── Shared ────────────────────────────────────────────────────────────────

  async submitForm() {
    await this.page.locator('button[type="submit"]').click();
  }

  async expectSuccessFlash() {
    // Flash success message style used in teller templates
    await expect(this.page.locator('[style*="background:#ecfdf5"], [style*="background:#f0fdf4"], .alert-success')).toBeVisible();
  }

  async expectErrorFlash() {
    // Flash error message style
    await expect(this.page.locator('[style*="background:#fef2f2"], .alert-danger, .error-alert, .alert-error')).toBeVisible();
  }
}
