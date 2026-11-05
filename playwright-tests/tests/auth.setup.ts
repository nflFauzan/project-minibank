import { test as setup } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import path from 'path';

const authDir = path.join(__dirname, '../.auth');

setup('authenticate as CS', async ({ page }) => {
  setup.setTimeout(60_000); // Extra time for slowMo
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('cs1', 'minibank123', 'CS');
  
  // Wait for navigation to dashboard or check for successful login
  await page.waitForURL('**/dashboard', { timeout: 30_000 }); 
  
  await page.context().storageState({ path: path.join(authDir, 'cs.json') });
});

setup('authenticate as Admin', async ({ page }) => {
  setup.setTimeout(60_000); // Extra time for slowMo
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('admin', 'minibank123', 'ADMIN');
  
  // Wait for navigation to dashboard
  await page.waitForURL('**/dashboard', { timeout: 30_000 }); 
  
  await page.context().storageState({ path: path.join(authDir, 'admin.json') });
});

setup('authenticate as Supervisor', async ({ page }) => {
  setup.setTimeout(60_000); // Extra time for slowMo
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('spv', 'spv1234', 'SUPERVISOR');
  
  await page.waitForURL('**/dashboard', { timeout: 30_000 });
  
  await page.context().storageState({ path: path.join(authDir, 'supervisor.json') });
});

setup('authenticate as Teller', async ({ page }) => {
  setup.setTimeout(60_000); // Extra time for slowMo
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('teller1', 'minibank123', 'TELLER');
  
  await page.waitForURL('**/dashboard', { timeout: 30_000 });
  
  await page.context().storageState({ path: path.join(authDir, 'teller.json') });
});
