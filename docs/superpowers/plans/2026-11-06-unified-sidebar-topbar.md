# Global Unified Sidebar & Consistent Header Navigation Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Establish a single, global, role-based Unified Sidebar and consistent Topbar across all modules (CS, Teller, Supervisor, Admin) so that users with multi-roles (such as `fauzan`) retain their full navigation menu and consistent identity across every page, with the active module properly highlighted and uniform "Tazkia Minibank" branding.

**Architecture:**
- **Global Controller Advice Layer:** Enhance `GlobalLayoutAdvice.java` to inject uniform user identity attributes (`isAdmin`, `isBranchManager`, `isCs`, `isTeller`, `roleLabel`, `username`, `currentDate`, `nowText`) into the Spring MVC Model for every authenticated request. Deprecate URL-based role overriding in `CsViewAdvice` and `TellerViewAdvice` so identity remains consistent across routes.
- **Routing & Aliasing Layer:** Add missing route aliases in `NavigationRoutingController.java` (`/teller/deposit`, `/teller/withdrawal`, `/teller/transfer`) redirecting to operational endpoints, avoiding 404 errors from cross-module navigation.
- **Thymeleaf Presentation Layer:** Consolidate `sidebar`, `tellerSidebar`, and `supervisorSidebar` fragments in `cs/fragments.html` into one unified, role-aware sidebar component. Ensure brand text is strictly "Tazkia Minibank" and element IDs (`#dashboard-link`, `#customer-link`, `#transaction-link`, etc.) match unified dashboard conventions.
- **Test Automation Layer:** Create `unified-sidebar-consistency.spec.ts` in Playwright and unit tests to verify sidebar persistence and header consistency across `/dashboard`, `/cs/*`, `/teller/*`, and `/supervisor/*`.

**Tech Stack:** Java 21, Spring Boot 3.5.8, Spring Security 6, Thymeleaf, HTML5/CSS3 (Minibank Shell), Playwright Test (TypeScript).

---

## Global Constraints

- **Single Brand Identity:** The sidebar brand text across all pages must strictly remain "Tazkia Minibank" (no "Teller Tazkia" or "Supervisor" brand overrides).
- **Role-Based Dynamic Menu:** Menus are shown based on the authenticated user's Spring Security authorities:
  - Universal: `Dashboard` (`#dashboard-link`)
  - CS (`isCs` or `isAdmin`): `Customers` (`#customer-link`), `Account` (`#account-link`), `Pengelolaan Produk Bank` (`#product-link`)
  - Teller (`isTeller`): `Transaksi` (`#transaction-link`), `Setoran Tunai` (`#deposit-link`), `Penarikan Tunai` (`#withdrawal-link`), `Transfer Dana` (`#transfer-link`), `Passbook` (`#passbook-link`)
  - Supervisor / Branch Manager (`isBranchManager`): `Approval Nasabah` (`#approval-queue-link`)
  - Admin (`isAdmin`): `Tambah Pengguna` (`#admin-user-create-link`)
- **Active Menu Highlighting:** The active link must receive the `.active` CSS class matching the current route (`active == 'customers'`, `active == 'transaction'`, etc.).
- **Consistent Header Meta:** The topbar header must consistently reflect the user's highest role label (e.g., `Branch Manager | fauzan`) and current date/time on all pages, rather than switching to the URL's module name.
- **Backward Compatibility:** Existing fragment references (`~{cs/fragments :: sidebar(...)}`, `~{cs/fragments :: tellerSidebar(...)}`, `~{cs/fragments :: supervisorSidebar(...)}`) must all resolve to the unified sidebar to prevent breaking any template.

---

## Review Focus

1. **Multi-Role Persistence:** For user `fauzan` (Branch Manager + CS + Teller), navigating between `/dashboard`, `/cs/customers`, `/teller/transaction/list`, and `/supervisor/nasabah` must display all 10 navigation links without disappearing.
2. **Single-Role Isolation:** For single-role users (e.g. `teller` or `zann`), the unified sidebar must strictly display only the links authorized for their role (no permission leakage).
3. **Active State Correctness:** Clicking each menu item correctly shifts the `.active` styling to the corresponding link.
4. **Header Consistency:** Header role label does not regress to hardcoded values (`Customer Service`, `Supervisor | ID00010003`) when visiting submodule URLs.
5. **Route Continuity:** Links like `/teller/deposit` or `/teller/withdrawal` route smoothly to the respective view without HTTP 404 or unexpected redirects.

---

## Tasks

### Task 1: Unify Global Controller Advice & Model Attributes

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/config/GlobalLayoutAdvice.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/CsViewAdvice.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/TellerViewAdvice.java`
- Test: `src/test/java/id/ac/tazkia/minibank/config/GlobalLayoutAdviceTest.java`

**Interfaces:**
- Produces: Model attributes `isAdmin` (boolean), `isBranchManager` (boolean), `isCs` (boolean), `isTeller` (boolean), `roleLabel` (String), `username` (String), `currentDate` (String), `nowText` (String).

- [ ] **Step 1: Write unit test for `GlobalLayoutAdvice`**
  Verify that for an Authentication token with roles `ROLE_CS`, `ROLE_TELLER`, and `ROLE_SUPERVISOR`:
  - `isCs`, `isTeller`, `isBranchManager` are all `true`.
  - `roleLabel` is `"Branch Manager"`.
  - `username` is populated with the principal's name.

- [ ] **Step 2: Run test to verify it fails**
  Run: `mvn test -Dtest=GlobalLayoutAdviceTest`
  Expected: FAIL (missing boolean flags and roleLabel resolution).

- [ ] **Step 3: Implement full role attribute injection in `GlobalLayoutAdvice`**
  Add `isCs`, `isTeller`, `isBranchManager`, `isAdmin`, `username`, and formatted date to model attributes.
  In `CsViewAdvice.java` and `TellerViewAdvice.java`, remove hardcoded `roleLabel = "Customer Service"` and `roleLabel = "Teller"` overwrites so that the user's authentic role is preserved.

- [ ] **Step 4: Run test to verify it passes**
  Run: `mvn test -Dtest=GlobalLayoutAdviceTest`
  Expected: PASS.

- [ ] **Step 5: Commit changes**
  `git add src/main/java/id/ac/tazkia/minibank/config/ src/main/java/id/ac/tazkia/minibank/controller/ src/test/java/id/ac/tazkia/minibank/config/`
  `git commit -m "feat(nav): inject global role flags and consistent identity in GlobalLayoutAdvice"`

---

### Task 2: Implement Navigation Route Aliases in `NavigationRoutingController`

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/NavigationRoutingController.java`
- Test: `src/test/java/id/ac/tazkia/minibank/controller/NavigationRoutingControllerTest.java`

**Interfaces:**
- Produces: HTTP redirects for `/teller/deposit`, `/teller/withdrawal`, `/teller/transfer` to `/teller/transaction/*`.

- [ ] **Step 1: Write unit test for teller route aliases**
  Add mock MVC tests for `GET /teller/deposit`, `GET /teller/withdrawal`, and `GET /teller/transfer`.
  Verify they return HTTP 302 redirecting to `/teller/transaction/deposit`, `/teller/transaction/withdrawal`, and `/teller/transaction/transfer` respectively.

- [ ] **Step 2: Run test to verify it fails**
  Run: `mvn test -Dtest=NavigationRoutingControllerTest`
  Expected: FAIL (endpoints return 404).

- [ ] **Step 3: Implement route aliases in `NavigationRoutingController.java`**
  Add `@GetMapping({"/teller/deposit", "/teller/transaction/deposit-alias"})` -> `redirect:/teller/transaction/deposit`
  Add `@GetMapping("/teller/withdrawal")` -> `redirect:/teller/transaction/withdrawal`
  Add `@GetMapping("/teller/transfer")` -> `redirect:/teller/transaction/transfer`

- [ ] **Step 4: Run test to verify it passes**
  Run: `mvn test -Dtest=NavigationRoutingControllerTest`
  Expected: PASS.

- [ ] **Step 5: Commit changes**
  `git add src/main/java/id/ac/tazkia/minibank/controller/NavigationRoutingController.java src/test/java/id/ac/tazkia/minibank/controller/NavigationRoutingControllerTest.java`
  `git commit -m "feat(routing): add alias redirects for teller deposit, withdrawal, and transfer"`

---

### Task 3: Unify Sidebar and Topbar Fragments in `cs/fragments.html`

**Files:**
- Modify: `src/main/resources/templates/cs/fragments.html`
- Modify: `src/main/resources/templates/teller/fragments.html`

**Interfaces:**
- Consumes: Model attributes `isCs`, `isTeller`, `isBranchManager`, `isAdmin`, `roleLabel`, `username`, `currentDate`.
- Produces: Thymeleaf fragments `sidebar(active)`, `tellerSidebar(active)`, `supervisorSidebar(active)`, and `topbar`.

- [ ] **Step 1: Refactor `sidebar(active)` in `cs/fragments.html`**
  - Brand header: logo + "Tazkia Minibank".
  - Universal link: `#dashboard-link` (`/dashboard`).
  - CS links conditional on `${isCs or isAdmin}`:
    - `#customer-link` (`/cs/customers`)
    - `#account-link` (`/cs/account`)
    - `#product-link` (`/cs/product`)
  - Teller links conditional on `${isTeller}`:
    - `#transaction-link` (`/teller/transaction/list`)
    - `#deposit-link` (`/teller/transaction/deposit`)
    - `#withdrawal-link` (`/teller/transaction/withdrawal`)
    - `#transfer-link` (`/teller/transaction/transfer`)
    - `#passbook-link` (`/teller/passbook/select-account`)
  - Supervisor links conditional on `${isBranchManager}`:
    - `#approval-queue-link` (`/supervisor/nasabah`)
  - Admin links conditional on `${isAdmin}`:
    - `#admin-user-create-link` (`/admin/users/create`)
  - Bottom actions: `#settings-link` (`/settings`) and `#logout-button` (`/logout`).

- [ ] **Step 2: Unify `tellerSidebar(active)` and `supervisorSidebar(active)`**
  Update `tellerSidebar(active)` and `supervisorSidebar(active)` in `cs/fragments.html` to delegate to or include the exact same unified sidebar markup, ensuring total backward compatibility.
  Update `teller/fragments.html` to match.

- [ ] **Step 3: Refactor `topbar` in `cs/fragments.html`**
  Ensure topbar meta renders:
  `<span id="header-role" th:text="${roleLabel} ?: 'Staff'"></span> | <span id="header-username" th:text="${username} ?: 'User'"></span>`.

- [ ] **Step 4: Verify template compilation and syntax**
  Run: `mvn test-compile`
  Expected: BUILD SUCCESS.

- [ ] **Step 5: Commit changes**
  `git add src/main/resources/templates/cs/fragments.html src/main/resources/templates/teller/fragments.html`
  `git commit -m "feat(ui): unify sidebar and topbar fragments with role-based navigation"`

---

### Task 4: Template Active State Audit across Sub-Modules

**Files:**
- Inspect & Align:
  - `src/main/resources/templates/cs/customers/*.html` (active: `'customers'`)
  - `src/main/resources/templates/cs/account/*.html` (active: `'account'`)
  - `src/main/resources/templates/cs/product/*.html` (active: `'product'`)
  - `src/main/resources/templates/teller/transaction/*.html` (active: `'transaction'`, `'deposit'`, `'withdrawal'`, `'transfer'`)
  - `src/main/resources/templates/teller/passbook/*.html` (active: `'passbook-printing'`)
  - `src/main/resources/templates/supervisor/nasabah/*.html` (active: `'nasabah'`)

- [ ] **Step 1: Audit each template's fragment invocation**
  Ensure each template passes the correct active key:
  - Customers pages pass `'customers'`.
  - Account pages pass `'account'`.
  - Product pages pass `'product'`.
  - Transaction list passes `'transaction'`.
  - Deposit pages pass `'deposit'`.
  - Withdrawal pages pass `'withdrawal'`.
  - Transfer pages pass `'transfer'`.
  - Passbook passes `'passbook-printing'`.
  - Supervisor pages pass `'nasabah'`.

- [ ] **Step 2: Ensure stylesheet consistency**
  Ensure each template links `/css/cs-shell.css` and `/css/minibank-ui.css` so sidebar and topbar render with pixel-perfect styling.

- [ ] **Step 3: Run Spring Boot integration tests**
  Run: `mvn test`
  Expected: All server-side tests pass.

- [ ] **Step 4: Commit changes**
  `git add src/main/resources/templates/`
  `git commit -m "fix(ui): align active navigation state and shell css across sub-modules"`

---

### Task 5: End-to-End Playwright Verification across Roles

**Files:**
- Create: `playwright-tests/tests/unified-sidebar-consistency.spec.ts`

- [ ] **Step 1: Write E2E test for multi-role user (`fauzan`)**
  - Login as `fauzan` / `fauzan`.
  - Verify `/dashboard` has all 10 links (`#dashboard-link`, `#customer-link`, `#account-link`, `#product-link`, `#transaction-link`, `#deposit-link`, `#withdrawal-link`, `#transfer-link`, `#passbook-link`, `#approval-queue-link`).
  - Click `#customer-link` -> verify URL is `/cs/customers` and all 10 links are still present, `#customer-link` has `.active`, header is `Branch Manager | fauzan`.
  - Click `#transaction-link` -> verify URL is `/teller/transaction/list` and all 10 links are still present, `#transaction-link` has `.active`, brand is "Tazkia Minibank", header is `Branch Manager | fauzan`.
  - Click `#approval-queue-link` -> verify URL is `/supervisor/nasabah` and all 10 links are still present, `#approval-queue-link` has `.active`.

- [ ] **Step 2: Write E2E test for single-role users**
  - Login as `teller` -> verify CS, Supervisor, and Admin links are NOT visible in the sidebar across `/dashboard` and `/teller/transaction/list`.
  - Login as `zann` (CS) -> verify Teller, Supervisor, and Admin links are NOT visible in the sidebar across `/dashboard` and `/cs/customers`.

- [ ] **Step 3: Execute Playwright E2E suite**
  Run: `npx playwright test tests/unified-sidebar-consistency.spec.ts tests/minibank-unified-dashboard.spec.ts`
  Expected: 100% tests PASS.

- [ ] **Step 4: Live MCP Playwright Inspection**
  Use Playwright MCP to log in as `fauzan`, click from Dashboard to CS, Teller, and Supervisor, taking snapshots to confirm visual consistency.

- [ ] **Step 5: Commit test and plan artifacts**
  `git add playwright-tests/tests/unified-sidebar-consistency.spec.ts docs/superpowers/plans/`
  `git commit -m "test(e2e): add comprehensive tests for unified sidebar persistence"`

