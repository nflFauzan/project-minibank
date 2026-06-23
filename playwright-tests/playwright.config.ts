import { defineConfig, devices } from '@playwright/test';
import path from 'path';

/**
 * Read environment variables from file.
 * https://github.com/motdotla/dotenv
 */
// require('dotenv').config();

export const STORAGE_STATE_CS         = path.join(__dirname, '.auth/cs.json');
export const STORAGE_STATE_ADMIN      = path.join(__dirname, '.auth/admin.json');
export const STORAGE_STATE_SUPERVISOR = path.join(__dirname, '.auth/supervisor.json');
export const STORAGE_STATE_TELLER     = path.join(__dirname, '.auth/teller.json');

export default defineConfig({
  testDir: './tests',
  /* Run tests in files in parallel */
  fullyParallel: false,
  /* Fail the build on CI if you accidentally left test.only in the source code. */
  forbidOnly: !!process.env.CI,
  /* Retry on CI only */
  retries: process.env.CI ? 2 : 0,
  /* Opt out of parallel tests on CI. */
  workers: 1,
  /* Reporter to use. See https://playwright.dev/docs/test-reporters */
  reporter: 'html',
  /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
  use: {
    /* Base URL to use in actions like `await page.goto('/')`. */
    baseURL: 'http://localhost:8080',

    /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    
    /* Agar gerakan terlihat jelas saat mode headed */
    launchOptions: {
      slowMo: 800, // Memberikan jeda 0.8 detik di setiap aksi (klik, ketik, dll)
    },
  },

  /* Configure projects for major browsers */
  projects: [
    // ─── Setup projects ────────────────────────────────────────────────────────
    {
      name: 'reset',
      testMatch: /reset\.setup\.ts/,
    },
    {
      name: 'setup',
      testMatch: /auth\.setup\.ts/,
      dependencies: ['reset'],
    },

    // ─── No-auth tests (signup, login flow) ────────────────────────────────────
    {
      name: 'chromium-no-auth',
      use: { ...devices['Desktop Chrome'] },
      testMatch: [/auth\.spec\.ts/, /signup\.spec\.ts/],
    },

    // ─── CS tests ──────────────────────────────────────────────────────────────
    {
      name: 'chromium-cs',
      use: { 
        ...devices['Desktop Chrome'],
        storageState: STORAGE_STATE_CS,
      },
      dependencies: ['setup'],
      testMatch: [
        /nasabah\.spec\.ts/,
        /rekening\.spec\.ts/,
        /product\.spec\.ts/,
        /cs-dashboard\.spec\.ts/,
        /cs-account-close\.spec\.ts/,
      ],
    },

    // ─── Admin tests ───────────────────────────────────────────────────────────
    {
      name: 'chromium-admin',
      use: { 
        ...devices['Desktop Chrome'],
        storageState: STORAGE_STATE_ADMIN,
      },
      dependencies: ['setup'],
      testMatch: [/admin\.spec\.ts/],
    },

    // ─── Supervisor tests ──────────────────────────────────────────────────────
    {
      name: 'chromium-supervisor',
      use: { 
        ...devices['Desktop Chrome'],
        storageState: STORAGE_STATE_SUPERVISOR,
      },
      dependencies: ['setup'],
      testMatch: [/supervisor\.spec\.ts/],
    },

    // ─── Teller tests ──────────────────────────────────────────────────────────
    {
      name: 'chromium-teller',
      use: { 
        ...devices['Desktop Chrome'],
        storageState: STORAGE_STATE_TELLER,
      },
      dependencies: ['setup'],
      testMatch: [
        /teller-dashboard\.spec\.ts/,
        /teller-transaction\.spec\.ts/,
      ],
    },
  ],
});

