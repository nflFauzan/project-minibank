import { test as setup, request } from '@playwright/test';

setup('reset database', async ({ baseURL }) => {
  const context = await request.newContext();
  const response = await context.post(`${baseURL}/api/test/reset`);
  if (!response.ok()) {
    const errorBody = await response.text();
    throw new Error(`Failed to reset database: ${response.status()} ${errorBody}`);
  }
  console.log('Database reset successful');
});
