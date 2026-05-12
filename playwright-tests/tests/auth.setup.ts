import { test as setup } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import path from 'path';

const authDir = path.join(__dirname, '../.auth');

setup('authenticate as CS', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('zann', 'zann', 'CS');
  
  // Wait for navigation to dashboard or check for successful login
  // TODO: verify selector against actual HTML (dashboard check)
  await page.waitForURL('**/cs/dashboard'); 
  
  await page.context().storageState({ path: path.join(authDir, 'cs.json') });
});

setup('authenticate as Admin', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.navigate();
  await loginPage.login('admin', 'admin1234', 'ADMIN');
  
  // Wait for navigation to dashboard
  // TODO: verify selector against actual HTML (dashboard check)
  await page.waitForURL('**/admin/dashboard'); 
  
  await page.context().storageState({ path: path.join(authDir, 'admin.json') });
});
