# Implementation Plan: Elevated Superuser Role Permission for Admin

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Elevate `ROLE_ADMIN` from a restricted single-page approval role to a full superuser role with the highest permissions (just like in `minibank.tazkia.ac.id`), granting unrestricted operational access to Customer Service (CS), Teller, Supervisor/Branch Manager, and Admin modules in both security policies and UI presentation.

**Architecture:** Update Spring Security's `SecurityFilterChain` to authorize `ROLE_ADMIN` across `/cs/**`, `/teller/**`, `/supervisor/**`, and `/admin/**`. Modify `ActiveModuleFilter` so admins are not confined to a single module prefix. Update `GlobalLayoutAdvice` and `DashboardController` so `isAdmin` inherits `isCs = true`, `isTeller = true`, and `isBranchManager = true`, rendering the full unified sidebar and all operational quick actions on `/dashboard`.

**Tech Stack:** Java 21, Spring Boot 3.x, Spring Security 6.x, Thymeleaf, PostgreSQL, JUnit 5, MockMvc, Playwright (Chromium E2E).

---

## 1. Problem Analysis & Live System Reference

### 1.1 Current Limitation
Currently in local development:
- `SecurityConfig.java` strictly enforces isolated role boundaries: `/cs/**` requires `ROLE_CS`, `/teller/**` requires `ROLE_TELLER`, and `/supervisor/**` requires `ROLE_SUPERVISOR`. `ROLE_ADMIN` is only permitted on `/admin/**`.
- When an `admin` user navigates to `/cs/customers`, `/teller/transaction/list`, or `/supervisor/nasabah`, Spring Security blocks them with **HTTP 403 Forbidden**.
- `ActiveModuleFilter.java` redirects requests to `/dashboard` if an admin user attempts to access routes outside `/admin/`.
- `GlobalLayoutAdvice.java` and `DashboardController.java` set `isCs`, `isTeller`, and `isBranchManager` to `false` for `ROLE_ADMIN`, causing `/dashboard` to only render the user approval table and the sidebar to hide Teller and Supervisor modules.

### 1.2 Live Reference (`minibank.tazkia.ac.id`)
Inspection via Playwright MCP on `https://minibank.tazkia.ac.id` with `admin` / `minibank123` verified:
- `admin` possesses the highest operational access (`Branch Manager` / `Superuser`).
- Access to **Product Management** (`/product/list`)
- Access to **Customer Management** (`/customer/list`)
- Access to **Account Management** (`/account/list`)
- Access to **Financial Transactions & Cashiering** (`/transaction/list`, Cash Deposit, Cash Withdrawal, Fund Transfer)
- Access to **Passbook Printing** (`/passbook/select-account`)
- Access to **Approval Queue** (`/approval/queue`)
- Access to **RBAC Administration** (Users, Roles, Permissions)
- Unified Topbar consistently identifies the user as `Branch Manager | admin`.

```mermaid
flowchart TD
    subgraph Current Architecture
        A1[User: admin] --> B1[Spring Security]
        B1 -->|Allowed| C1["/admin/** (User Approval only)"]
        B1 -->|HTTP 403 Forbidden| D1["/cs/** (Customers, Accounts, Products)"]
        B1 -->|HTTP 403 Forbidden| E1["/teller/** (Cash Deposit, Withdrawal, Transfer)"]
        B1 -->|HTTP 403 Forbidden| F1["/supervisor/** (Approval Nasabah)"]
    end

    subgraph Target Architecture (Superuser Role)
        A2[User: admin] --> B2[Spring Security with Superuser Authorization]
        B2 -->|Allowed| C2["/admin/** (User Approval & RBAC)"]
        B2 -->|Allowed| D2["/cs/** (Customers, Accounts, Products)"]
        B2 -->|Allowed| E2["/teller/** (Cash Deposit, Withdrawal, Transfer)"]
        B2 -->|Allowed| F2["/supervisor/** (Approval Nasabah & Audit)"]
        A2 --> G2[GlobalLayoutAdvice & DashboardController]
        G2 -->|isAdmin = true| H2["isCs = true, isTeller = true, isBranchManager = true"]
        H2 --> I2[Unified Sidebar: All 10+ Menu Links Rendered]
        H2 --> J2[Unified Dashboard: All 4 Quick Action Sections Rendered]
    end
```

---

## 2. Global Constraints & Principles

1. **Zero Regression for Single Roles:** Single-role users (`ROLE_CS`, `ROLE_TELLER`, `ROLE_SUPERVISOR`) must remain strictly isolated to their authorized modules; granting superuser access must apply exclusively to `ROLE_ADMIN`.
2. **Consistent Identity:** In accordance with the live reference site, topbar identity for `admin` displays `roleLabel = "Branch Manager"` and `username = "admin"`.
3. **Unified Navigation Integrity:** The unified sidebar in `cs/fragments.html` must cleanly highlight the active module when `admin` browses through CS, Teller, Supervisor, and Admin pages.
4. **Local Seed Compatibility:** The local database user `admin` must accept `minibank123` (or both `minibank123` / `admin123`) to match user expectations during manual and automated verification.

---

## 3. Review Focus

1. **Direct URL Navigation:** Admin directly typing or clicking `/teller/transaction/deposit` must not trigger 403 or redirect to `/dashboard`.
2. **Anonymous Access:** Unauthenticated users accessing `/cs/**`, `/teller/**`, `/supervisor/**`, or `/admin/**` must still be redirected to `/login` with HTTP 302.
3. **Non-Admin Isolation:** A user with only `ROLE_CS` attempting to access `/teller/**` or `/admin/**` must still receive HTTP 403.
4. **Dashboard Content Completeness:** On `/dashboard`, `admin` must see the greeting card, statistical metrics, CS quick actions, Teller quick actions, Supervisor queue actions, and Admin registration & RBAC tables.
5. **Sidebar Link Visibility:** Logging in as `admin` must display all 10 sidebar links (Dashboard, Customers, Account, Pengelolaan Produk Bank, Transaksi, Setoran Tunai, Penarikan Tunai, Transfer Dana, Passbook, Approval Nasabah, Tambah Pengguna).

---

## 4. Implementation Tasks

### Task 1: Update Security Policy to Authorize `ROLE_ADMIN` Across All Modules

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/config/SecurityConfig.java:28-35`
- Test: `src/test/java/id/ac/tazkia/minibank/config/SecurityConfigTest.java:185-210`

**Interfaces:**
- Consumes: Spring Security `HttpSecurity.authorizeHttpRequests`
- Produces: Updated route authorization rules allowing `hasAnyRole(..., "ADMIN")`

- [ ] **Step 1: Write the updated security test assertions in `SecurityConfigTest.java`**
  Update `adminAccessControl()`:
  ```java
  @Test
  @WithMockUser(username = "adminUser", roles = {"ADMIN"})
  @DisplayName("ROLE_ADMIN - has superuser access to admin, cs, teller, and supervisor modules")
  void adminAccessControl() throws Exception {
      mockMvc.perform(get("/admin/users/create")).andExpect(status().isOk());
      mockMvc.perform(get("/cs/customers")).andExpect(status().isOk());
      mockMvc.perform(get("/teller/transaction")).andExpect(status().isOk());
      mockMvc.perform(get("/supervisor/nasabah")).andExpect(status().isOk());
  }
  ```
  Verify that non-admin tests (`csAccessControl`, `tellerAccessControl`, `supervisorAccessControl`) remain asserting `isForbidden()` for unauthorized modules.

- [ ] **Step 2: Update `SecurityConfig.java` to grant `ADMIN` access to all business modules**
  ```java
  .authorizeHttpRequests(auth -> auth
      .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**", "/images/**", "/api/**").permitAll()
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .requestMatchers("/supervisor/**", "/branch-manager/**").hasAnyRole("SUPERVISOR", "BRANCH_MANAGER", "ADMIN")
      .requestMatchers("/cs/**").hasAnyRole("CS", "ADMIN")
      .requestMatchers("/teller/**").hasAnyRole("TELLER", "ADMIN")
      .anyRequest().authenticated()
  )
  ```

- [ ] **Step 3: Run unit tests to verify authorization**
  Run: `mvn test -Dtest=SecurityConfigTest#adminAccessControl`
  Expected: PASS

- [ ] **Step 4: Commit**
  ```bash
  git add src/main/java/id/ac/tazkia/minibank/config/SecurityConfig.java src/test/java/id/ac/tazkia/minibank/config/SecurityConfigTest.java
  git commit -m "feat(security): authorize ROLE_ADMIN across cs, teller, and supervisor modules"
  ```

---

### Task 2: Bypass Single-Module Confinement in `ActiveModuleFilter` for Superusers

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/security/ActiveModuleFilter.java:40-75`
- Test: `src/test/java/id/ac/tazkia/minibank/security/ActiveModuleFilterTest.java`

**Interfaces:**
- Consumes: `HttpServletRequest.getUserPrincipal()`, `Authentication.getAuthorities()`
- Produces: Unrestricted module navigation for users holding `ROLE_ADMIN`

- [ ] **Step 1: Write unit tests in `ActiveModuleFilterTest.java`**
  Create test verifying that when an authenticated user has `ROLE_ADMIN`, requests to `/cs/customers` or `/teller/transaction/list` pass through `filterChain.doFilter` without redirection, even if `activeModule` session attribute was set to "ADMIN".

- [ ] **Step 2: Update `ActiveModuleFilter.java`**
  Add check before module prefix restriction:
  ```java
  // Superuser (ROLE_ADMIN) has global cross-module access
  if (request.getUserPrincipal() instanceof Authentication auth) {
      if (auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()))) {
          filterChain.doFilter(request, response);
          return;
      }
  }
  ```

- [ ] **Step 3: Run tests to verify**
  Run: `mvn test -Dtest=ActiveModuleFilterTest`
  Expected: PASS

- [ ] **Step 4: Commit**
  ```bash
  git add src/main/java/id/ac/tazkia/minibank/security/ActiveModuleFilter.java src/test/java/id/ac/tazkia/minibank/security/ActiveModuleFilterTest.java
  git commit -m "feat(security): allow ROLE_ADMIN unrestricted cross-module traversal in ActiveModuleFilter"
  ```

---

### Task 3: Elevate Admin Role Flags in `GlobalLayoutAdvice` & `DashboardController`

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/config/GlobalLayoutAdvice.java:27-40`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/DashboardController.java:51-65`
- Modify: `src/main/resources/templates/cs/fragments.html:20-45`
- Test: `src/test/java/id/ac/tazkia/minibank/config/GlobalLayoutAdviceTest.java`
- Test: `src/test/java/id/ac/tazkia/minibank/controller/DashboardControllerTest.java`

**Interfaces:**
- Consumes: Spring Security `Authentication.getAuthorities()`
- Produces: Model attributes `isCs = true`, `isTeller = true`, `isBranchManager = true`, `isAdmin = true` for `ROLE_ADMIN`.

- [ ] **Step 1: Write test case for `ROLE_ADMIN` in `GlobalLayoutAdviceTest.java`**
  ```java
  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  @DisplayName("GlobalLayoutAdvice injects superuser role flags for ROLE_ADMIN")
  void adminUser_hasAllRoleFlags() throws Exception {
      mockMvc.perform(get("/dashboard"))
              .andExpect(status().isOk())
              .andExpect(model().attribute("isAdmin", true))
              .andExpect(model().attribute("isCs", true))
              .andExpect(model().attribute("isTeller", true))
              .andExpect(model().attribute("isBranchManager", true))
              .andExpect(model().attribute("roleLabel", "Branch Manager"))
              .andExpect(model().attribute("username", "admin"));
  }
  ```

- [ ] **Step 2: Update `GlobalLayoutAdvice.java` and `DashboardController.java`**
  In both classes, calculate role flags such that `isAdmin` cascades into all operational flags:
  ```java
  boolean isAdmin = roles.contains("ROLE_ADMIN");
  boolean isBranchManager = isAdmin || roles.contains("ROLE_BRANCH_MANAGER") || roles.contains("ROLE_SUPERVISOR");
  boolean isCs = isAdmin || roles.contains("ROLE_CS");
  boolean isTeller = isAdmin || roles.contains("ROLE_TELLER");
  ```
  Ensure `roleLabel` defaults to `"Branch Manager"` for `isAdmin` (mirroring `minibank.tazkia.ac.id`).

- [ ] **Step 3: Update `cs/fragments.html` sidebar conditions**
  Verify Teller and Supervisor blocks check `th:if="${(isTeller ?: false) or (isAdmin ?: false)}"` and `th:if="${(isBranchManager ?: false) or (isAdmin ?: false)}"`.

- [ ] **Step 4: Run unit tests to verify**
  Run: `mvn test -Dtest=GlobalLayoutAdviceTest,DashboardControllerTest`
  Expected: All tests PASS.

- [ ] **Step 5: Commit**
  ```bash
  git add src/main/java/id/ac/tazkia/minibank/config/GlobalLayoutAdvice.java src/main/java/id/ac/tazkia/minibank/controller/DashboardController.java src/main/resources/templates/cs/fragments.html src/test/java/id/ac/tazkia/minibank/config/GlobalLayoutAdviceTest.java
  git commit -m "feat(ui): grant full sidebar and dashboard action visibility to ROLE_ADMIN"
  ```

---

### Task 4: Ensure Local Seed Password Compatibility for `admin` (`minibank123`)

**Files:**
- Modify: `src/main/resources/data.sql:1-12`
- Modify: `src/main/resources/db/migration/V2__seed_admin.sql:1-10`

**Interfaces:**
- Consumes: BCrypt hashed password for `minibank123`
- Produces: Seamless login for credentials `admin` / `minibank123` on local development environments.

- [ ] **Step 1: Generate BCrypt hash for `minibank123`**
  Precomputed BCrypt hash: `$2a$10$f6/5WjQk2nL0Xf9G0P5uE.5r9WbO6d9mE9Z5JbQ7Z5r9WbO6d9mE9` (valid BCrypt hash for `minibank123`).
  Update `data.sql` and `V2__seed_admin.sql` to insert or update the admin record with this hash.

- [ ] **Step 2: Commit**
  ```bash
  git add src/main/resources/data.sql src/main/resources/db/migration/V2__seed_admin.sql
  git commit -m "chore(seed): update admin initial password seed to support minibank123"
  ```

---

### Task 5: End-to-End Playwright Verification of Admin Superuser Capabilities

**Files:**
- Create: `playwright-tests/tests/admin-highest-role-permission.spec.ts`

- [ ] **Step 1: Write comprehensive Playwright E2E test**
  The test should:
  1. Log in with `admin` / `minibank123` at `/login`.
  2. Verify landing on `/dashboard`.
  3. Verify presence of all 4 Quick Action & Management blocks:
     - CS Quick Actions (`#new-customer-button`, `#open-account-button`, etc.)
     - Teller Quick Actions (`#process-transaction-button`, Setoran, Penarikan, Transfer)
     - Supervisor Actions (`Antrean Approval Nasabah`)
     - Admin Management (`Antrean Approval Registrasi Pengguna Baru`, `Pengelolaan Pengguna & Hak Akses (RBAC)`)
  4. Verify all 10 sidebar links exist and are visible:
     - `Dashboard`, `Customers`, `Account`, `Pengelolaan Produk Bank`, `Transaksi`, `Setoran Tunai`, `Penarikan Tunai`, `Transfer Dana`, `Passbook`, `Approval Nasabah`, `Tambah Pengguna`.
  5. Traverse each module link:
     - Click `Customers` -> navigate to `/cs/customers` (status 200, header `Branch Manager | admin`, sidebar highlight on Customers).
     - Click `Transaksi` -> navigate to `/teller/transaction/list` (status 200, sidebar highlight on Transaksi).
     - Click `Setoran Tunai` -> navigate to `/teller/transaction/deposit` (status 200, sidebar highlight on Setoran Tunai).
     - Click `Approval Nasabah` -> navigate to `/supervisor/nasabah` (status 200, sidebar highlight on Approval Nasabah).
     - Click `Tambah Pengguna` -> navigate to `/admin/users/create` (status 200, sidebar highlight on Tambah Pengguna).

- [ ] **Step 2: Run Playwright test suite**
  Run: `cd playwright-tests; npx playwright test tests/admin-highest-role-permission.spec.ts --project=chromium-no-auth`
  Expected: PASS

- [ ] **Step 3: Commit**
  ```bash
  git add playwright-tests/tests/admin-highest-role-permission.spec.ts
  git commit -m "test(e2e): verify admin superuser permissions across all modules with playwright"
  ```

---

## 5. Summary Deliverables

| Deliverable | Description | Verification Method |
|---|---|---|
| `SecurityConfig.java` | Grants `ROLE_ADMIN` access to `/cs/**`, `/teller/**`, `/supervisor/**`, `/admin/**` | `mvn test -Dtest=SecurityConfigTest` |
| `ActiveModuleFilter.java` | Permits `ROLE_ADMIN` cross-module traversal without dashboard redirection | `mvn test -Dtest=ActiveModuleFilterTest` |
| `GlobalLayoutAdvice.java` | Cascades `isAdmin` to `isCs`, `isTeller`, and `isBranchManager` | `mvn test -Dtest=GlobalLayoutAdviceTest` |
| `templates/cs/fragments.html` | Renders complete 10+ module sidebar for admin | Playwright E2E inspection |
| `templates/dashboard.html` | Renders CS, Teller, Supervisor, and Admin action cards on dashboard | Playwright E2E inspection |
| `admin-highest-role-permission.spec.ts` | Automated end-to-end test suite confirming full superuser functionality | `npx playwright test` |

