# Unified Dashboard Routing & Native UI Restoration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Restore the project's native glassmorphism and deep-blue UI design on `login.html` and `dashboard.html` while preserving the simplified Tazkia business flow (no module dropdown, unified maker-checker flow), completely removing all legacy sub-dashboard endpoints (`/cs/dashboard`, `/teller/dashboard`, `/admin/dashboard`, `/supervisor/dashboard`) so that `GET /dashboard` is the sole dashboard endpoint for all roles.

**Architecture:**
- **Presentation Layer:** Strictly use the project's native stylesheet bundle (`cs-shell.css`, `minibank-ui.css`, `custom.css`, `account.css`, Poppins font, and `login-bg.jpg`). Remove all external BSI Tailwind CSS (`output.css`) dependencies.
- **Web Controller Layer:** Consolidate all dashboard operations into `DashboardController.java` (`GET /dashboard`). Fully remove `@GetMapping("/dashboard")` from `CsDashboardController`, `TellerDashboardController`, `SupervisorController`, and `AdminUserController`. Sub-dashboard URLs will strictly return HTTP 404.
- **Filter & Redirection Layer:** Update `ActiveModuleFilter` and admin action handlers so redirect targets never point to `/cs/dashboard` or other sub-dashboards, but directly to `/dashboard`.
- **Test Automation Layer:** Refactor unit tests and Playwright E2E suites to verify native UI selectors, role-adaptive rendering on `/dashboard`, and confirm that `/cs/dashboard`, `/teller/dashboard`, etc., are completely non-existent (HTTP 404).

**Tech Stack:** Java 21, Spring Boot 3.5.8, Spring Security 6, Thymeleaf, HTML5/CSS3 (Native Glassmorphism & Minibank Shell), Playwright Test (TypeScript).

---

## Global Constraints

- **Preserve Native Design Language:** Glassmorphism card (`backdrop-filter: blur(22px)`), background `url('/images/login-bg.jpg')`, Poppins font, deep blue (`#0d1b45`, `#1e3a8a`, `#2563eb`), and soft blue button gradients (`#B8D9FF` -> `#9CC5FF`).
- **Single Dashboard URL:** The browser URL for all authenticated roles must strictly remain `http://localhost:8080/dashboard`.
- **Sub-dashboards Non-Existent:** Navigating to `/cs/dashboard`, `/teller/dashboard`, `/admin/dashboard`, or `/supervisor/dashboard` MUST return HTTP 404 (Not Found).
- **Simplified Login Flow:** The login form must NOT have a module dropdown.
- **Dual Credentials Support:** Both live Tazkia credentials (`admin`, `cs1`, `teller1` / `minibank123`) and existing test users (`zann`, `teller`, `spv`) must work seamlessly.

---

## Review Focus

1. **Sub-dashboard 404 Verification:** Ensure direct requests to `/cs/dashboard`, `/teller/dashboard`, `/admin/dashboard`, and `/supervisor/dashboard` return HTTP 404 instead of rendering a view or redirecting.
2. **Native Style Purity:** Ensure no BSI teal classes or `output.css` remain in `login.html` or `dashboard.html`.
3. **Role-Adaptive Content on `/dashboard`:** Ensure CS sees customer/account cards & product catalog, Teller sees cash deposit/withdrawal/transfer cards & product catalog, Admin sees RBAC tables & approval queue, and Supervisor/BM sees verification queues.
4. **ActiveModuleFilter Behavior:** Ensure no infinite redirect loop occurs and cross-module accesses redirect to `/dashboard`.
5. **Playwright E2E Suite Pass Rate:** All E2E tests (`minibank-unified-dashboard.spec.ts`, `auth.spec.ts`, `cs-dashboard.spec.ts`, etc.) must pass 100%.

---

## Tasks

### Task 1: Complete Elimination of Sub-Dashboard Endpoints

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/CsDashboardController.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/TellerDashboardController.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/SupervisorController.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/AdminUserController.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/security/ActiveModuleFilter.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/NavigationRoutingController.java`
- Test: `src/test/java/id/ac/tazkia/minibank/controller/SubDashboardRemovalTest.java`

- [ ] Remove `@GetMapping("/cs/dashboard")` from `CsDashboardController.java`.
- [ ] Remove `@GetMapping("/dashboard")` from `TellerDashboardController.java`.
- [ ] Remove `@GetMapping("/dashboard")` from `SupervisorController.java`.
- [ ] Remove `@GetMapping("/dashboard")` from `AdminUserController.java`.
- [ ] Update admin controller redirects (`redirect:/admin/dashboard?approved`, etc.) to `redirect:/dashboard?approved`, etc.
- [ ] In `ActiveModuleFilter.java`, change line 76 `response.sendRedirect(prefix + "dashboard")` to `response.sendRedirect("/dashboard")`.
- [ ] In `NavigationRoutingController.java`, remove any redirection to `/admin/dashboard`.
- [ ] Write `SubDashboardRemovalTest.java` asserting that `GET /cs/dashboard`, `GET /teller/dashboard`, `GET /admin/dashboard`, and `GET /supervisor/dashboard` return HTTP 404 (status isNotFound).
- [ ] Run test: `.\mvnw test -Dtest=SubDashboardRemovalTest` and verify all tests pass.

---

### Task 2: Native Glassmorphism Login Page Restoration

**Files:**
- Modify: `src/main/resources/templates/login.html`
- Test: `playwright-tests/tests/minibank-unified-dashboard.spec.ts`

- [ ] Restore the native glassmorphism styling in `src/main/resources/templates/login.html`:
  - Background: `url('/images/login-bg.jpg') no-repeat center center/cover`
  - Container `.overlay` with `backdrop-filter: blur(10px)`
  - Card `.glass-card` with `border-radius: 30px`, `padding: 45px 40px`, `backdrop-filter: blur(22px)`
  - Header: Back button `<a href="/" class="back-btn">←</a>`, Title "Welcome Back!" (`color: #1f3c88`)
  - Form inputs: `.input-field` with `border-radius: 32px`, font Poppins
  - Password wrapper with eye toggle button `.password-eye`
  - Alert messages: `.msg-error` (`#error-message`) and `.msg-success` (`#success-message`)
  - "Forgot Username or Password?" link
  - Submit button `.btn-login` with gradient `linear-gradient(to bottom, #B8D9FF, #9CC5FF)`
  - Sign up link: "Create an Account? SIGN UP here" linking to `/signup`
  - NO module select dropdown.
- [ ] Verify using Playwright: `npx playwright test tests/minibank-unified-dashboard.spec.ts -g "Login page"`.

---

### Task 3: Native Minibank Shell Unified Dashboard (`/dashboard`)

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/DashboardController.java`
- Modify: `src/main/resources/templates/dashboard.html`
- Test: `src/test/java/id/ac/tazkia/minibank/controller/DashboardControllerTest.java`

- [ ] Update `DashboardController.java` to inject `DashboardService`, `AdminUserService`, `TellerDashboardService`:
  - Fetch summary metrics (`totalNasabah`, `totalRekeningAktif`, `totalDeposit`, `totalTransaksi`, `nasabahTerbaru`).
  - Fetch active products (`products`).
  - For Admin / Branch Manager: populate pending user approvals (`users`) and all users (`allUsers`).
  - Populate authority flags (`isAdmin`, `isBranchManager`, `isCs`, `isTeller`, `roleLabel`, `username`, `currentDate`).
  - Return `"dashboard"`.
- [ ] Build `src/main/resources/templates/dashboard.html` using the project's native CSS (`/css/cs-shell.css`, `/css/minibank-ui.css`, `/css/custom.css`, `/css/account.css`):
  - Sidebar: Deep blue `#0d1b45`, Brand text `#1e3a8a` pill, nav links with active state.
  - Topbar: User greeting ("Halo, [Name]"), role badge, currentDate, and logout button.
  - Greeting Card: Gradient `linear-gradient(135deg, #1e3a8a, #2563eb)` with greeting title & subtitle.
  - Summary Grid (4 stat cards): Total Nasabah, Total Rekening Aktif, Total Deposit / Saldo, Total Transaksi.
  - Role-adaptive Sections:
    - **CS View (`isCs`):** Quick Action buttons (Buka Rekening, Nasabah Baru, Cari Rekening) + Tabel Katalog Produk Tabungan Aktif.
    - **Teller View (`isTeller`):** Quick Action buttons (Setoran Tunai, Penarikan Tunai, Transfer Dana, Cetak Buku Tabungan) + Katalog Produk Tabungan.
    - **Admin View (`isAdmin`):** Antrean Approval Registrasi Pengguna + Tabel Pengelolaan Pengguna & Hak Akses (RBAC) dengan tombol Tambah Pengguna (`/admin/users/create`).
    - **Branch Manager View (`isBranchManager`):** Antrean Verifikasi Nasabah & Rekening.
- [ ] Test controller with `DashboardControllerTest.java` and ensure all assertions pass.

---

### Task 4: Template Link Standardization

**Files:**
- Modify: `src/main/resources/templates/admin/approval.html`
- Modify: `src/main/resources/templates/admin/user_create.html`
- Modify: `src/main/resources/templates/admin/user_edit_roles.html`
- Modify: `src/main/resources/templates/cs/_layout.html`
- Modify: `src/main/resources/templates/cs/fragments.html`
- Modify: `src/main/resources/templates/teller/fragments.html`
- Modify: `src/main/resources/templates/teller/layout.html`

- [ ] In `templates/admin/approval.html`: update `<a href="/admin/dashboard"` to `<a href="/dashboard"`.
- [ ] In `templates/admin/user_create.html`: update `<a href="/admin/dashboard"` to `<a href="/dashboard"`.
- [ ] In `templates/admin/user_edit_roles.html`: update `<a href="/admin/dashboard"` to `<a href="/dashboard"`.
- [ ] In `templates/cs/_layout.html`: update `th:href="@{/cs/dashboard}"` to `th:href="@{/dashboard}"`.
- [ ] In `templates/cs/fragments.html`: update all sub-dashboard links to `th:href="@{/dashboard}"`.
- [ ] In `templates/teller/fragments.html` and `templates/teller/layout.html`: update `th:href="@{/teller/dashboard}"` to `th:href="@{/dashboard}"`.

---

### Task 5: Backend Unit and Integration Test Refactoring

**Files:**
- Modify: `src/test/java/id/ac/tazkia/minibank/config/SecurityConfigTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/config/GlobalLayoutAdviceTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/config/RoleSecurityIntegrationTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/security/ActiveModuleFilterTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/security/ActiveModuleFilterAdditionalTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/controller/TellerDashboardControllerTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/controller/CsDashboardControllerTest.java`
- Modify: `src/test/java/id/ac/tazkia/minibank/controller/AdminUserControllerTest.java`

- [ ] Update `SecurityConfigTest.java`: replace tests expecting `/admin/dashboard`, `/cs/dashboard`, etc., with `/dashboard` or operational paths (`/admin/users/create`, `/cs/nasabah/create`, `/teller/transaction`).
- [ ] Update `ActiveModuleFilterTest.java` and `ActiveModuleFilterAdditionalTest.java`: assert redirection to `/dashboard` instead of `prefix + "dashboard"`.
- [ ] Update `AdminUserControllerTest.java`: assert redirects go to `/dashboard` or `/admin/users`.
- [ ] Run full test suite: `.\mvnw test` and confirm 100% PASS with 0 failures.

---

### Task 6: Playwright E2E Test Suite Alignment & Execution

**Files:**
- Modify: `playwright-tests/pages/AdminPage.ts`
- Modify: `playwright-tests/pages/SupervisorPage.ts`
- Modify: `playwright-tests/pages/TellerPage.ts`
- Modify: `playwright-tests/tests/cs-dashboard.spec.ts`
- Modify: `playwright-tests/tests/auth.spec.ts`
- Modify: `playwright-tests/tests/minibank-unified-dashboard.spec.ts`

- [ ] In `AdminPage.ts`, `SupervisorPage.ts`, `TellerPage.ts`: change `goto('.../dashboard')` to `goto('/dashboard')`.
- [ ] In `cs-dashboard.spec.ts`: update to navigate to `/dashboard`.
- [ ] In `minibank-unified-dashboard.spec.ts`:
  - Assert native glassmorphism styling on `/login` (`.glass-card`, `Welcome Back!`, `.btn-login`).
  - Assert `/dashboard` displays native Minibank shell (`.cs-sidebar`, `.greeting-card`, `.summary-grid`).
  - Add explicit test asserting that navigating to `/cs/dashboard`, `/teller/dashboard`, `/admin/dashboard`, and `/supervisor/dashboard` receives HTTP 404.
  - Verify all 4 roles (`cs1`, `teller1`, `admin`, `spv`) on `/dashboard`.
- [ ] Run Playwright suite: `npx playwright test tests/minibank-unified-dashboard.spec.ts --project=chromium-no-auth` and confirm all tests pass.
- [ ] Run full Playwright test regression: `npx playwright test`.

