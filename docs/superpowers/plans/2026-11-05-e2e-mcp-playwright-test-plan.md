# MiniBank E2E Testing Plan with Playwright MCP

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Execute comprehensive End-to-End (E2E) testing across MiniBank using Playwright MCP tools, reproducing and validating the authentication anomaly and verifying full functional workflows across all 4 system roles (Admin, Customer Service, Supervisor, Teller).

**Architecture:** Interactive browser automation driven by Playwright MCP (`browser_navigate`, `browser_fill_form`, `browser_click`, `browser_take_screenshot`, `browser_snapshot`). Each task tests a specific subsystem on the running Spring Boot server (`http://localhost:8080`) backed by PostgreSQL, with database reset via `/api/test/reset` for deterministic preconditions.

**Tech Stack:** Playwright MCP, Spring Boot 3, Spring Security, Thymeleaf, PostgreSQL 17, Docker Compose.

**Spec:** System functional specifications documented in `README.md`, existing Playwright specs in `playwright-tests/tests/`, and Spring MVC controllers in `src/main/java/id/ac/tazkia/minibank/controller/`.

## Global Constraints
- Target application URL: `http://localhost:8080`
- Test database reset endpoint: `POST http://localhost:8080/api/test/reset`
- Passwords for seeded users:
  - Admin: `admin` / `admin123` (Module: `ADMIN`)
  - CS: `zann` / `zann` (Module: `CS`)
  - Supervisor: `spv` / `spv1234` or `zann` / `zann` (Module: `SUPERVISOR`)
  - Teller: `teller` / `teller1234` or `zann` / `zann` (Module: `TELLER`)
- Every task must capture visual/DOM evidence using `browser_snapshot` or `browser_take_screenshot`.

## Review Focus
- **Input/Failure 1: Login role mismatch redirect trap** (Spec/Implementation bug in `LoginSuccessHandler.java:35` & `AuthController.java:49`): logging in with valid credentials but wrong module dropdown authenticates the user but redirects to `/login?error=role`, which `AuthController` redirects to `/`, trapping the user in authenticated state without a module.
- **Input/Failure 2: Unapproved user login attempt**: users created via `/signup` with `approved = false` attempting to log in must be rejected with an appropriate error message and not granted session access.
- **Input/Failure 3: Cross-module URL tampering**: an authenticated CS user directly browsing to `/admin/dashboard` or `/teller/dashboard` must be caught by `ActiveModuleFilter` and redirected back to `/cs/dashboard`.
- **Input/Failure 4: Withdrawal exceeding account balance**: Teller attempting to withdraw funds greater than the account balance must trigger a validation error without persisting any debit transaction.
- **Input/Failure 5: Account opening eligibility gate**: CS attempting to open an account must only be able to select nasabah with `status = ACTIVE`; `INACTIVE` or `REJECTED` nasabah must not appear in the eligible list.

---

### Task 1: Environment Baseline & Test Data Reset

**Files:**
- Test endpoint: `POST http://localhost:8080/api/test/reset`
- Controllers: `src/main/java/id/ac/tazkia/minibank/controller/rest/TestResetController.java`

**Interfaces:**
- Consumes: None (starting baseline)
- Produces: Clean PostgreSQL database seeded with default active/inactive nasabah, default savings product, default active accounts, and seeded pending users.

- [ ] **Step 1: Execute database reset via TestReset API**
  Invoke HTTP POST to `http://localhost:8080/api/test/reset` via curl or PowerShell command.
  Expected: JSON response `{"status":"success","message":"Database reset successfully"}`.

- [ ] **Step 2: Verify baseline users and accounts via SQL query**
  Run verification query in `minibank-postgres` container:
  `docker exec minibank-postgres psql -U minibank pgminibank -c "SELECT username, approved, enabled FROM users WHERE username IN ('admin', 'zann', 'spv', 'teller', 'pending_test_user2');"`
  Expected: All 4 active users exist and are enabled; `pending_test_user2` has `approved = f, enabled = f`.

- [ ] **Step 3: Navigate browser to login page**
  Use `call_mcp_tool` (`playwright:browser_navigate`) to `http://localhost:8080/login`.
  Expected: Login form renders with `Username`, `Password`, `Modul` dropdown, and `LOGIN` button.

---

### Task 2: Authentication Module & Login Anomaly E2E Verification

**Files:**
- Template: `src/main/resources/templates/login.html`
- Handlers: `src/main/java/id/ac/tazkia/minibank/security/LoginSuccessHandler.java`
- Controllers: `src/main/java/id/ac/tazkia/minibank/controller/AuthController.java`

**Interfaces:**
- Consumes: Seeded users from Task 1
- Produces: Verified authentication behaviors for all 4 roles, negative login validation, and verified root-cause reproduction of the login anomaly.

- [ ] **Step 1: Test valid login for each role**
  - CS: Navigate to `/login`, fill `zann` / `zann`, select `CS`, click LOGIN. Verify redirection to `http://localhost:8080/cs/dashboard`. Click Logout (`a[href="/logout"]`). Verify URL is `/login?logout`.
  - Admin: Navigate to `/login`, fill `admin` / `admin123`, select `Admin`, click LOGIN. Verify redirection to `http://localhost:8080/admin/dashboard`. Click Logout.
  - Supervisor: Navigate to `/login`, fill `spv` / `spv1234`, select `Supervisor`, click LOGIN. Verify redirection to `http://localhost:8080/supervisor/dashboard`. Click Logout.
  - Teller: Navigate to `/login`, fill `teller` / `teller1234`, select `Teller`, click LOGIN. Verify redirection to `http://localhost:8080/teller/dashboard`. Click Logout.

- [ ] **Step 2: Test invalid credentials & unapproved user**
  - Navigate to `/login`, submit `wronguser` / `wrongpass`, module `CS`. Verify page remains on `/login?error=true` showing error message `.msg-error` "Login gagal. Cek username/password atau akun belum disetujui."
  - Navigate to `/login`, submit `pending_test_user2` / `test1234`, module `CS`. Verify error message is displayed and login is rejected.

- [ ] **Step 3: Reproduce and verify the Login Anomaly (Role Mismatch Redirect Trap)**
  - Ensure browser is logged out (navigate to `/logout` then `/login`).
  - Fill username `zann`, password `zann`, but select module `Admin`.
  - Click `LOGIN`.
  - Observe and verify:
    1. `LoginSuccessHandler` redirects to `/login?error=role` because `zann` does not have `ROLE_ADMIN`.
    2. Spring Security has already authenticated the user.
    3. `AuthController.login` checks `auth.isAuthenticated()` and redirects to `/` (landing page).
    4. The user lands on `http://localhost:8080/` without seeing the error message and gets trapped in an authenticated state with no active module assigned.
  - Document this anomaly behavior for the user report.
  - Clean up session by navigating to `/logout`.

---

### Task 3: Admin Module E2E Testing (User Approval Flow)

**Files:**
- Template: `src/main/resources/templates/admin/dashboard.html`
- Controller: `src/main/java/id/ac/tazkia/minibank/controller/AdminUserController.java`
- Service: `src/main/java/id/ac/tazkia/minibank/service/AdminUserService.java`

**Interfaces:**
- Consumes: Seeded pending user `pending_test_user2` from Task 1
- Produces: Approved user with assigned roles capable of subsequent login.

- [ ] **Step 1: Login as Admin and navigate to Admin Dashboard**
  Navigate to `/login`, fill `admin` / `admin123`, select `Admin`, click LOGIN.
  Verify dashboard title and pending user table at `/admin/dashboard`.

- [ ] **Step 2: Inspect pending user list**
  Take snapshot of `/admin/dashboard`. Confirm `pending_test_user2` (or `Pending Test User 2`) appears in the pending table with an "Action" or "Review" button.

- [ ] **Step 3: Execute user approval**
  Click the approval/detail link for `pending_test_user2` (navigates to `/admin/approval/{id}`).
  Verify user details (NIM, Prodi, Dosen Pembimbing).
  Click `Approve` button (submits `POST /admin/approve/{id}`).
  Verify redirected to `/admin/dashboard?approved`.

- [ ] **Step 4: Verify approved user can now log in**
  Logout from Admin.
  Attempt login with `pending_test_user2` / `test1234` with module `CS` (or assigned role).
  Verify login is no longer blocked with disabled exception.
  Logout.

---

### Task 4: Customer Service (CS) Module E2E Testing

**Files:**
- Templates: `src/main/resources/templates/cs/*`
- Controllers: `CsCustomerController.java`, `CsAccountController.java`, `CsProductController.java`

**Interfaces:**
- Consumes: Admin-approved session, baseline postal codes
- Produces: New registered Nasabah (status `INACTIVE`), verified savings product, new opened account (Rekening).

- [ ] **Step 1: Login as CS and verify CS Dashboard**
  Navigate to `/login`, fill `zann` / `zann`, select `CS`, click LOGIN.
  Verify `/cs/dashboard` displays stats (Total Nasabah, Total Rekening, Total Produk).

- [ ] **Step 2: Register a new Nasabah (`/cs/customers/new`)**
  Click "Customers" in navigation sidebar (or navigate to `/cs/customers`).
  Click "Tambah Nasabah" (navigates to `/cs/customers/new`).
  Fill required fields:
  - NIK: `3201012304950001`
  - Nama Lengkap: `Ahmad Pratama E2E`
  - Tempat/Tanggal Lahir: `Bogor`, `1995-04-23`
  - Nama Ibu Kandung: `Mariana`
  - Kode Pos: `16110` (verify auto-fill or selection)
  Submit registration form.
  Verify redirect to `/cs/customers` with success notification.
  Verify new customer status in list is `INACTIVE` (Menunggu Approval Supervisor).

- [ ] **Step 3: Verify Master Produk Tabungan (`/cs/product`)**
  Navigate to `/cs/product`.
  Verify product `Tabungan Utama` (WADIAH, Min Setoran Rp 100.000, Status Aktif) is listed.

- [ ] **Step 4: Open new bank account for active customer (`/cs/account/open`)**
  Navigate to `/cs/account`.
  Click "Buka Rekening Baru" (navigates to `/cs/account/open`).
  Verify only `ACTIVE` customers appear in eligibility list (`Budi Santoso` CIF `C0000001` is present; newly registered `Ahmad Pratama E2E` is NOT present because status is `INACTIVE`).
  Select `Budi Santoso`.
  In open account form:
  - Select product `Tabungan Utama`
  - Set initial deposit: `500000`
  - Submit form.
  Verify redirect to `/cs/account` and new account number is displayed with active status and balance Rp 500.000.

- [ ] **Step 5: Logout from CS**
  Click Logout link (`/logout`).

---

### Task 5: Supervisor Module E2E Testing (Customer Approval)

**Files:**
- Template: `src/main/resources/templates/supervisor/*`
- Controller: `src/main/java/id/ac/tazkia/minibank/controller/SupervisorNasabahController.java`

**Interfaces:**
- Consumes: `INACTIVE` customer registered in Task 4 (`Ahmad Pratama E2E`)
- Produces: Approved customer with status `ACTIVE`.

- [ ] **Step 1: Login as Supervisor**
  Navigate to `/login`, fill `spv` / `spv1234`, select `Supervisor`, click LOGIN.
  Verify landing on `/supervisor/dashboard`.

- [ ] **Step 2: Inspect pending approval queue**
  Navigate to `/supervisor/nasabah` (filter `PENDING`).
  Verify `Ahmad Pratama E2E` is listed in pending approvals.

- [ ] **Step 3: Approve customer**
  Click detail/review on `Ahmad Pratama E2E` (`/supervisor/nasabah/{id}`).
  Review submitted identity data.
  Click `Approve` button (submits `POST /supervisor/nasabah/{id}/approve`).
  Verify redirect to `/supervisor/nasabah` with success banner.
  Verify status changed to `ACTIVE`.

- [ ] **Step 4: Verify rejected flow with second inactive customer**
  Locate existing seeded inactive customer (`C0000003` - `Ahmad Fadillah`).
  Click detail, enter rejection reason: `Dokumen KTP buram dan tidak terbaca`.
  Click `Reject` button.
  Verify status changed to `REJECTED` and reason is recorded.

- [ ] **Step 5: Logout from Supervisor**
  Click Logout link (`/logout`).

---

### Task 6: Teller Module E2E Testing (Financial Transactions)

**Files:**
- Templates: `src/main/resources/templates/teller/*`
- Controller: `src/main/java/id/ac/tazkia/minibank/controller/TellerTransactionController.java`
- Services: `TellerDepositService.java`, `TellerWithdrawalService.java`, `TellerTransferService.java`

**Interfaces:**
- Consumes: Active accounts (`54300000301` starting balance 500.000, `54300000401` starting balance 500.000)
- Produces: Verified financial ledger entries (Deposit, Withdrawal, Transfer) and transaction receipts.

- [ ] **Step 1: Login as Teller**
  Navigate to `/login`, fill `teller` / `teller1234`, select `Teller`, click LOGIN.
  Verify `/teller/dashboard` is displayed.

- [ ] **Step 2: Perform Cash Deposit (Setoran Tunai)**
  Navigate to Setoran Tunai form (`/teller/transaction/deposit`).
  Enter account number `54300000301`.
  Enter deposit amount: `200000`.
  Enter notes: `Setoran Tunai E2E Test`.
  Submit transaction.
  Verify success receipt page with:
  - Tipe: `DEPOSIT`
  - Jumlah: `Rp 200.000`
  - Saldo Akhir: `Rp 700.000`

- [ ] **Step 3: Perform Cash Withdrawal (Penarikan Tunai)**
  Navigate to Penarikan Tunai form (`/teller/transaction/withdrawal`).
  Enter account number `54300000301`.
  Enter withdrawal amount: `100000`.
  Enter notes: `Tarik Tunai E2E Test`.
  Submit transaction.
  Verify success receipt page with Saldo Akhir: `Rp 600.000`.

- [ ] **Step 4: Test Negative Withdrawal (Insufficient Balance)**
  Navigate to Penarikan Tunai form (`/teller/transaction/withdrawal`).
  Enter account number `54300000301`.
  Enter amount exceeding balance: `50000000` (Rp 50.000.000).
  Submit transaction.
  Verify transaction is rejected with error message "Saldo tidak mencukupi" and no balance is deducted.

- [ ] **Step 5: Perform Internal Transfer (Transfer Antar Rekening)**
  Navigate to Transfer form (`/teller/transaction/transfer`).
  Source account: `54300000301`
  Destination account: `54300000401`
  Amount: `150000`
  Submit transfer.
  Verify receipt shows successful transfer of Rp 150.000.
  Verify source account `54300000301` balance becomes `Rp 450.000`.
  Verify destination account `54300000401` balance becomes `Rp 650.000`.

- [ ] **Step 6: Verify Transaction History List**
  Navigate to `/teller/transaction/list`.
  Verify all 3 transactions (Deposit, Withdrawal, Transfer) appear with correct timestamps and amounts.

- [ ] **Step 7: Logout from Teller**
  Click Logout link (`/logout`).

---

### Task 7: Cross-Role Boundary & RBAC Isolation Verification

**Files:**
- Filter: `src/main/java/id/ac/tazkia/minibank/security/ActiveModuleFilter.java`
- Config: `src/main/java/id/ac/tazkia/minibank/config/SecurityConfig.java`

**Interfaces:**
- Consumes: Authenticated sessions from previous tasks
- Produces: Confirmed security boundaries preventing privilege escalation.

- [ ] **Step 1: Login as CS and test unauthorized URL access**
  Navigate to `/login`, fill `zann` / `zann`, select `CS`, click LOGIN.
  Attempt direct navigation to `/admin/dashboard`.
  Verify `ActiveModuleFilter` redirects back to `/cs/dashboard`.
  Attempt direct navigation to `/teller/dashboard`.
  Verify `ActiveModuleFilter` redirects back to `/cs/dashboard`.

- [ ] **Step 2: Test unauthenticated access to protected endpoints**
  Logout (`/logout`).
  Attempt direct navigation to `/cs/dashboard`.
  Verify redirection to `/login`.
  Attempt direct navigation to `/admin/dashboard`.
  Verify redirection to `/login`.
  Attempt direct navigation to `/teller/dashboard`.
  Verify redirection to `/login`.

---

