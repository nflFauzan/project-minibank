import { test, expect } from '@playwright/test';
import { STORAGE_STATE_CS } from '../playwright.config';

test.use({ storageState: STORAGE_STATE_CS });

test.describe('CS Account Opening Reverse Engineering (100% minibank.tazkia.ac.id Compliance)', () => {

  test('1. Navigation & Route Alias: /account/open redirects to CS Customer Selection', async ({ page }) => {
    await page.goto('/account/open');
    await expect(page).toHaveURL(/\/cs\/account\/open/);

    // Verify Page Header matching minibank.tazkia.ac.id
    const pageTitle = page.locator('#page-title, h1');
    await expect(pageTitle).toContainText('Select Customer for Account Opening');

    // Verify top action link
    const manageCustomersLink = page.locator('#manage-customers-link, a:has-text("Manage Customers")');
    await expect(manageCustomersLink).toBeVisible();
    await expect(manageCustomersLink).toHaveAttribute('href', /\/cs\/customers|\/customer\/list/);
  });

  test('2. Customer Selection Page: Layout, Search, Glassmorphic Cards & Badges', async ({ page }) => {
    await page.goto('/cs/account/open');

    // Verify Search Box matching minibank.tazkia.ac.id
    const searchInput = page.locator('input[name="search"]');
    await expect(searchInput).toBeVisible();
    await expect(searchInput).toHaveAttribute('placeholder', 'Search by customer number, name, or email...');

    const searchBtn = page.locator('#search-btn, button:has-text("Search")');
    await expect(searchBtn).toBeVisible();

    // Verify Customer Cards and metadata
    const firstCard = page.locator('.customer-card').first();
    await expect(firstCard).toBeVisible();

    // Check Badges inside card
    await expect(firstCard.locator('.badge-type, :text("PERSONAL")')).toBeVisible();
    await expect(firstCard.locator('.status-badge, :text("ACTIVE")')).toBeVisible();

    // Check Metadata fields
    await expect(firstCard).toContainText('Customer Number:');
    await expect(firstCard).toContainText('Email:');
    await expect(firstCard).toContainText('Phone:');
    await expect(firstCard).toContainText('Branch:');

    // Check Open Account action button
    const openAccountBtn = firstCard.locator('a:has-text("Open Account")');
    await expect(openAccountBtn).toBeVisible();
  });

  test('3. Open Account Form: Exact Layout matching minibank.tazkia.ac.id without Nominal Setoran & Tujuan Pembukaan', async ({ page }) => {
    await page.goto('/cs/account/open');

    // Select first customer card
    const firstCard = page.locator('.customer-card').first();
    const openBtn = firstCard.locator('a:has-text("Open Account")');
    await openBtn.click();

    // Expect URL to match form
    await expect(page).toHaveURL(/\/cs\/account\/open\/\d+/);

    // Verify Heading & Back Link
    await expect(page.locator('h1')).toContainText('Open New Account');
    const backLink = page.locator('#back-to-customer-selection, a:has-text("Back to Customer Selection")');
    await expect(backLink).toBeVisible();

    // Verify Customer Information Card
    await expect(page.locator('h2:has-text("Customer Information")')).toBeVisible();
    await expect(page.locator('.badge-type, :text("PERSONAL")')).toBeVisible();
    await expect(page.locator('.status-badge, :text("ACTIVE")')).toBeVisible();
    await expect(page.locator('text=Customer Number:')).toBeVisible();
    await expect(page.locator('text=Branch:')).toBeVisible();

    // Verify Form Fields matching minibank.tazkia.ac.id
    // Field 1: Select Product *
    const productSelect = page.locator('#produkId, select[name="produkId"]');
    await expect(productSelect).toBeVisible();
    await expect(page.locator('text=Choose an Islamic banking product for saving or checking')).toBeVisible();

    // Section: Product Details box
    await expect(page.locator('h3:has-text("Product Details")')).toBeVisible();
    await expect(page.locator('text=Product Type:')).toBeVisible();
    await expect(page.locator('text=Category:')).toBeVisible();
    await expect(page.locator('text=Minimum Opening Balance:')).toBeVisible();
    await expect(page.locator('text=Description:')).toBeVisible();

    // Field 2: Account Name *
    const accountNameInput = page.locator('#accountName, input[name="namaRekening"]');
    await expect(accountNameInput).toBeVisible();
    await expect(accountNameInput).toHaveAttribute('placeholder', 'e.g., John Doe - Personal Savings');
    await expect(page.locator('text=Descriptive name for this account (max 200 characters)')).toBeVisible();

    // Verify Absence of Nominal Setoran and Tujuan Pembukaan as visible form inputs!
    const visibleSetoran = page.locator('input[name="nominalSetoranAwal"]:not([type="hidden"])');
    const visibleTujuan = page.locator('input[name="tujuanPembukaan"]:not([type="hidden"])');
    await expect(visibleSetoran).toHaveCount(0);
    await expect(visibleTujuan).toHaveCount(0);

    // Verify Action Buttons
    await expect(page.locator('#cancel-btn, a:has-text("Cancel")')).toBeVisible();
    await expect(page.locator('#open-account-btn, button:has-text("Open Account")')).toBeVisible();
  });

  test('4. Dynamic Product Details interactive preview on product selection', async ({ page }) => {
    await page.goto('/cs/account/open');
    await page.locator('.customer-card a:has-text("Open Account")').first().click();

    // Initial state before selection (or first option)
    const productSelect = page.locator('#produkId');
    const options = await productSelect.locator('option:not([disabled])').all();
    expect(options.length).toBeGreaterThan(0);

    // Select the first valid product option
    const firstVal = await options[0].getAttribute('value');
    await productSelect.selectOption(firstVal!);

    // Verify dynamic update in Product Details box
    const typeText = await page.locator('#detail-product-type').textContent();
    const minBalanceText = await page.locator('#detail-minimum-balance').textContent();

    expect(typeText).not.toBe('-');
    expect(minBalanceText).toMatch(/Rp/);
  });

  test('5. Complete account opening submission and verification in account list & view', async ({ page }) => {
    await page.goto('/cs/account/open');
    await page.locator('.customer-card a:has-text("Open Account")').first().click();

    // Select product
    const productSelect = page.locator('#produkId');
    const options = await productSelect.locator('option:not([disabled])').all();
    const firstVal = await options[0].getAttribute('value');
    await productSelect.selectOption(firstVal!);

    // Customize Account Name
    const uniqueAccountName = 'Budi Santoso - Reverse Engineered Savings';
    const accountNameInput = page.locator('#accountName');
    await accountNameInput.fill(uniqueAccountName);

    // Submit form
    await page.locator('#open-account-btn').click();

    // Should redirect to account list with success message
    await expect(page).toHaveURL(/\/cs\/account$/);
    await expect(page.locator('.alert-success, body').first()).toContainText('Rekening berhasil dibuka');

    // Verify new account appears in the list
    await expect(page.locator('table')).toContainText(uniqueAccountName);

    // View Account Details
    const accountRow = page.locator('tr', { hasText: uniqueAccountName }).first();
    await accountRow.locator('a:has-text("View")').click();

    // Verify Account Details page
    await expect(page).toHaveURL(/\/cs\/account\/\d+/);
    await expect(page.locator('h1')).toContainText('Account Detail');
    await expect(page.locator('.glass-detail-card, .detail-card')).toContainText(uniqueAccountName);
  });

});

