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

  async viewFirstTransactionDetail() {
    const link = this.page.locator('table tr a').first();
    await link.click();
  }

  // ─── Deposit ───────────────────────────────────────────────────────────────

  async navigateToDepositSelect() {
    await this.page.goto('/teller/transaction/deposit');
  }

  async searchAndSelectRekeningForDeposit(query: string) {
    const searchInput = this.page.locator('input[name="q"], #q, input[type="search"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill(query);
      await searchInput.press('Enter');
    }
    // Pilih rekening pertama yang muncul
    const selectBtn = this.page.locator('table tr a, table tr button').first();
    await selectBtn.click();
  }

  async fillDepositForm(data: {
    jumlahSetoran: string;
    keterangan?: string;
    noReferensi?: string;
  }) {
    await this.page.locator('#jumlahSetoran, input[name="jumlahSetoran"]').fill(data.jumlahSetoran);
    if (data.keterangan) {
      const ket = this.page.locator('#keterangan, input[name="keterangan"], textarea[name="keterangan"]');
      if (await ket.count() > 0) await ket.fill(data.keterangan);
    }
    if (data.noReferensi) {
      const ref = this.page.locator('#noReferensi, input[name="noReferensi"]');
      if (await ref.count() > 0) await ref.fill(data.noReferensi);
    }
  }

  // ─── Withdrawal ────────────────────────────────────────────────────────────

  async navigateToWithdrawalSelect() {
    await this.page.goto('/teller/transaction/withdrawal');
  }

  async searchAndSelectRekeningForWithdrawal(query: string) {
    const searchInput = this.page.locator('input[name="q"], #q, input[type="search"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill(query);
      await searchInput.press('Enter');
    }
    const selectBtn = this.page.locator('table tr a, table tr button').first();
    await selectBtn.click();
  }

  async fillWithdrawalForm(data: {
    jumlahPenarikan: string;
    keterangan?: string;
    noReferensi?: string;
  }) {
    await this.page.locator('#jumlahPenarikan, input[name="jumlahPenarikan"]').fill(data.jumlahPenarikan);
    if (data.keterangan) {
      const ket = this.page.locator('#keterangan, input[name="keterangan"], textarea[name="keterangan"]');
      if (await ket.count() > 0) await ket.fill(data.keterangan);
    }
    if (data.noReferensi) {
      const ref = this.page.locator('#noReferensi, input[name="noReferensi"]');
      if (await ref.count() > 0) await ref.fill(data.noReferensi);
    }
  }

  // ─── Transfer ──────────────────────────────────────────────────────────────

  async navigateToTransferSelectSource() {
    await this.page.goto('/teller/transaction/transfer');
  }

  async selectSourceRekening(query: string) {
    const searchInput = this.page.locator('input[name="q"], #q, input[type="search"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill(query);
      await searchInput.press('Enter');
    }
    const selectBtn = this.page.locator('table tr a, table tr button').first();
    await selectBtn.click();
  }

  async selectTargetRekening(query: string) {
    // Setelah memilih source, akan diarahkan ke halaman pilih target
    const searchInput = this.page.locator('input[name="q"], #q, input[type="search"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill(query);
      await searchInput.press('Enter');
    }
    const selectBtn = this.page.locator('table tr a, table tr button').first();
    await selectBtn.click();
  }

  async fillTransferForm(data: {
    jumlah: string;
    keteranganTambahan?: string;
    noReferensi?: string;
  }) {
    await this.page.locator('#jumlah, input[name="jumlah"]').fill(data.jumlah);
    if (data.keteranganTambahan) {
      const ket = this.page.locator('#keteranganTambahan, input[name="keteranganTambahan"], textarea[name="keteranganTambahan"]');
      if (await ket.count() > 0) await ket.fill(data.keteranganTambahan);
    }
    if (data.noReferensi) {
      const ref = this.page.locator('#noReferensi, input[name="noReferensi"]');
      if (await ref.count() > 0) await ref.fill(data.noReferensi);
    }
  }

  // ─── Shared ────────────────────────────────────────────────────────────────

  async submitForm() {
    await this.page.locator('button[type="submit"]').click();
  }

  async expectSuccessFlash() {
    await expect(this.page.locator('.alert-success, [class*="success"]')).toBeVisible();
  }

  async expectErrorFlash() {
    await expect(this.page.locator('.alert-danger, [class*="error"]')).toBeVisible();
  }
}
