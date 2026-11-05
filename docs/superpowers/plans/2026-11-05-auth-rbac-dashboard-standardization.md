# Modul Autentikasi, Akun Pengguna, RBAC & Dashboard Terpadu Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Menghilangkan dropdown modul pada login dengan auto-routing dinamis ke `/dashboard`, menyelaraskan role verifikator menjadi `BRANCH_MANAGER`, memungkinkan registrasi mahasiswa mandiri dengan persetujuan Admin yang dapat memilihkan/meng-custom role (RBAC), serta menyediakan fitur pembuatan user langsung oleh Admin.

**Architecture:** Spring Security 6 form-login diarahkan ke `LoginSuccessHandler` yang selalu mengarahkan sesi terautentikasi ke endpoint terpadu `/dashboard`. Controller `/dashboard` membaca `GrantedAuthority` pengguna dan menyajikan view dashboard yang sesuai (`Admin`, `Branch Manager`, `CS`, atau `Teller`). Di sisi manajemen akun, `AdminUserService` dan template `admin/approval.html` diperluas dengan pemilihan role granular (`ROLE_CS`, `ROLE_TELLER`, `ROLE_BRANCH_MANAGER`, `ROLE_ADMIN`) baik saat approval registrasi mandiri maupun saat pembuatan akun langsung oleh Admin.

**Tech Stack:** Java 21, Spring Boot 3, Spring Security, Spring Data JPA, Thymeleaf, PostgreSQL, Playwright MCP.

**Spec:** Rencana Reverse Engineering di `reverse-engineering-minibank-plan.md` dan kebutuhan pengguna terkait Modul Autentikasi & Akun Pengguna (3.1).

## Global Constraints
- Target URL: `http://localhost:8080`
- Role verifikator diselaraskan menjadi `BRANCH_MANAGER` (`ROLE_BRANCH_MANAGER`), dengan tetap menjaga kompatibilitas `ROLE_SUPERVISOR` jika ada endpoint lama.
- Tidak ada dropdown modul pada halaman login (`/login`).
- Mahasiswa/pengguna dapat registrasi mandiri via `/signup` (status: `approved=false, enabled=false`).
- Admin dapat menyetujui user pending dan meng-custom role yang diberikan.
- Admin dapat membuat user aktif secara langsung dengan pilihan role.
- Seluruh tes unit/integrasi Maven dan Playwright harus tetap lulus tanpa regresi.

## Review Focus
1. **Login tanpa modul:** User dengan kredensial valid harus langsung masuk ke `/dashboard` dan diarahkan ke halaman yang sesuai otoritasnya tanpa error `error=role` atau `error=module`.
2. **Akun belum diapprove:** User yang baru registrasi mandiri via `/signup` tidak boleh bisa login (`isApproved=false, isEnabled=false` ditolak Spring Security).
3. **Custom role approval:** Saat Admin menyetujui user dan hanya mencentang `Customer Service`, user tersebut hanya mendapatkan `ROLE_CS` dan tidak mendapatkan role lain.
4. **Admin direct create user:** Admin membuat user baru melalui form admin, user langsung tersimpan dengan status `approved=true, enabled=true` dan role yang dipilih, serta dapat langsung login.
5. **Akses multi-role / fallback:** Jika user memiliki lebih dari satu role atau tidak memiliki role operasional, `/dashboard` menangani dengan prioritas yang jelas tanpa melempar NullPointerException atau loop redirect.

---

### Task 1: Role Naming & Model Aliasing (`BRANCH_MANAGER`)

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/config/DataSeedConfig.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/config/SecurityConfig.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/rest/TestResetController.java`
- Test: `src/test/java/id/ac/tazkia/minibank/config/RoleSecurityIntegrationTest.java` (create)

**Interfaces:**
- Consumes: `RoleRepository`, `UserRepository`, `Role`
- Produces: `ROLE_BRANCH_MANAGER` seeded & recognized in SecurityFilterChain

- [ ] **Step 1: Write integration test for `ROLE_BRANCH_MANAGER` authorization**
  Create `src/test/java/id/ac/tazkia/minibank/config/RoleSecurityIntegrationTest.java`:
  Test that an authenticated user with `ROLE_BRANCH_MANAGER` can access `/supervisor/dashboard` and `/dashboard`.

- [ ] **Step 2: Run test to verify failure**
  Run: `.\mvnw test "-Dtest=RoleSecurityIntegrationTest"`
  Expected: FAIL (Role not seeded or not matched in SecurityFilterChain).

- [ ] **Step 3: Update `DataSeedConfig.java`, `TestResetController.java`, and `SecurityConfig.java`**
  - In `DataSeedConfig.java`: seed `ROLE_BRANCH_MANAGER` in `RoleRepository`. Add `spv` user with `ROLE_BRANCH_MANAGER` (and keep `ROLE_SUPERVISOR` as alias).
  - In `TestResetController.java`: ensure `ROLE_BRANCH_MANAGER` is seeded during reset.
  - In `SecurityConfig.java`: update `.requestMatchers("/supervisor/**").hasAnyRole("SUPERVISOR", "BRANCH_MANAGER")`.

- [ ] **Step 4: Run test to verify it passes**
  Run: `.\mvnw test "-Dtest=RoleSecurityIntegrationTest"`
  Expected: PASS.

- [ ] **Step 5: Commit changes**
  `git commit -am "feat: add ROLE_BRANCH_MANAGER and update security rules"`

---

### Task 2: Simplify Login UI & Dynamic Auto-Routing

**Files:**
- Modify: `src/main/resources/templates/login.html`
- Modify: `src/main/java/id/ac/tazkia/minibank/security/LoginSuccessHandler.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/security/ActiveModuleFilter.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/AuthController.java`
- Modify: `playwright-tests/pages/LoginPage.ts`

**Interfaces:**
- Consumes: Spring Security `Authentication`, `LoginSuccessHandler`
- Produces: Seamless login redirecting directly to `/dashboard` without module dropdown

- [ ] **Step 1: Write failing test for login without module**
  Create or update test in `src/test/java/id/ac/tazkia/minibank/security/LoginSuccessHandlerTest.java` verifying that `onAuthenticationSuccess` redirects directly to `/dashboard` regardless of request parameters.

- [ ] **Step 2: Run test to verify failure**
  Run: `.\mvnw test "-Dtest=LoginSuccessHandlerTest"`
  Expected: FAIL (`LoginSuccessHandler` previously checked `request.getParameter("module")`).

- [ ] **Step 3: Implement clean auto-routing**
  - In `login.html`: Remove `<select name="module">...</select>`. Keep only `username`, `password`, eye toggle, forgot link, and submit button.
  - In `LoginSuccessHandler.java`: Remove parameter check `request.getParameter("module")`. Always clear authentication attributes and redirect to `/dashboard`.
  - In `ActiveModuleFilter.java`: Remove forced redirect to `/{module}/dashboard` based on session module. Let standard Spring Security URL authorization protect `/admin/**`, `/cs/**`, `/teller/**`, `/supervisor/**`.
  - In `AuthController.java`: Ensure `login` GET redirects authenticated users to `/dashboard`.
  - In `LoginPage.ts`: Make module optional and do not require selecting a module dropdown.

- [ ] **Step 4: Run test to verify it passes**
  Run: `.\mvnw test "-Dtest=LoginSuccessHandlerTest"`
  Expected: PASS.

- [ ] **Step 5: Commit changes**
  `git commit -am "feat: remove module dropdown from login and route directly to /dashboard"`

---

### Task 3: Centralized Dashboard Controller (`/dashboard`)

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/DashboardController.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/CsDashboardController.java` (if needed for shared metrics)
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/TellerDashboardController.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/SupervisorController.java`
- Create/Modify: `src/main/resources/templates/dashboard.html` (status fallback view)
- Test: `src/test/java/id/ac/tazkia/minibank/controller/DashboardControllerTest.java` (create)

**Interfaces:**
- Consumes: `Authentication`, `AdminUserService`, `NasabahRepository`, `RekeningRepository`
- Produces: Role-based dashboard dispatching for `ADMIN`, `BRANCH_MANAGER` / `SUPERVISOR`, `CS`, and `TELLER`.

- [ ] **Step 1: Write failing tests in `DashboardControllerTest.java`**
  Test `/dashboard` for users with:
  1. `ROLE_ADMIN` -> returns view or redirect to Admin Dashboard.
  2. `ROLE_BRANCH_MANAGER` -> returns view or redirect to Branch Manager Dashboard.
  3. `ROLE_CS` -> returns view or redirect to CS Dashboard.
  4. `ROLE_TELLER` -> returns view or redirect to Teller Dashboard.
  5. User with no operational roles -> returns "dashboard" with message "Akun Anda belum memiliki akses operasional".

- [ ] **Step 2: Run test to verify failure**
  Run: `.\mvnw test "-Dtest=DashboardControllerTest"`
  Expected: FAIL.

- [ ] **Step 3: Implement `DashboardController.java`**
  - Implement role resolution:
    - Check for `ROLE_ADMIN` -> redirect to `/admin/dashboard`.
    - Check for `ROLE_BRANCH_MANAGER` or `ROLE_SUPERVISOR` -> redirect to `/supervisor/dashboard`.
    - Check for `ROLE_CS` -> redirect to `/cs/dashboard`.
    - Check for `ROLE_TELLER` -> redirect to `/teller/dashboard`.
    - If none, render `dashboard.html` with model attributes explaining the account status.
  - Update `templates/supervisor/dashboard.html`: Update title and greeting to "Branch Manager - Dashboard" with subtitle "Kepala Cabang".

- [ ] **Step 4: Run test to verify it passes**
  Run: `.\mvnw test "-Dtest=DashboardControllerTest"`
  Expected: PASS.

- [ ] **Step 5: Commit changes**
  `git commit -am "feat: centralize /dashboard routing by user authority"`

---

### Task 4: Admin Custom Role Assignment & Direct User Creation (RBAC)

**Files:**
- Modify: `src/main/java/id/ac/tazkia/minibank/service/AdminUserService.java`
- Modify: `src/main/java/id/ac/tazkia/minibank/controller/AdminUserController.java`
- Modify: `src/main/resources/templates/admin/approval.html`
- Modify: `src/main/resources/templates/admin/dashboard.html`
- Create: `src/main/resources/templates/admin/user_create.html`
- Create: `src/main/resources/templates/admin/user_edit_roles.html`
- Create: `src/main/java/id/ac/tazkia/minibank/dto/AdminCreateUserDto.java`
- Test: `src/test/java/id/ac/tazkia/minibank/service/AdminUserServiceTest.java`
- Test: `src/test/java/id/ac/tazkia/minibank/controller/AdminUserControllerTest.java`

**Interfaces:**
- Consumes: `UserRepository`, `RoleRepository`, `PasswordEncoder`
- Produces:
  - `AdminUserService.approve(Long userId, List<String> roleNames)`
  - `AdminUserService.createUser(AdminCreateUserDto dto)`
  - `AdminUserService.updateRoles(Long userId, List<String> roleNames)`
  - `AdminUserService.listAllUsers()`

- [ ] **Step 1: Write failing tests for custom role assignment**
  In `AdminUserServiceTest.java`:
  - `approve_withSelectedRoles_shouldAssignOnlySelectedRoles()`
  - `createUser_byAdmin_shouldSaveEnabledUserWithRoles()`
  - `updateRoles_shouldChangeUserRoles()`

- [ ] **Step 2: Run test to verify failure**
  Run: `.\mvnw test "-Dtest=AdminUserServiceTest"`
  Expected: FAIL (`method approve(Long, List) not found`).

- [ ] **Step 3: Implement Service & Controller logic**
  - In `AdminUserService.java`:
    - Implement `approve(Long userId, List<String> roleNames)`. If `roleNames` is empty/null, assign default `ROLE_CS`. Map `ROLE_BRANCH_MANAGER` / `ROLE_CS` / `ROLE_TELLER` / `ROLE_ADMIN`.
    - Keep `approve(Long userId)` calling `approve(userId, List.of("ROLE_CS"))` or configured defaults to preserve existing tests.
    - Implement `createUser(AdminCreateUserDto dto)`: encode password, set `approved=true, enabled=true`, assign selected roles, save.
    - Implement `updateRoles(Long userId, List<String> roleNames)`.
    - Implement `listActiveUsers()`.
  - In `AdminUserController.java`:
    - In `GET /admin/approval/{id}`: pass `availableRoles` (`ROLE_BRANCH_MANAGER`, `ROLE_CS`, `ROLE_TELLER`, `ROLE_ADMIN`) to model.
    - In `POST /admin/approve/{id}`: accept `@RequestParam(name = "roles", required = false) List<String> roles`.
    - Add `GET /admin/users/create` & `POST /admin/users/create`.
    - Add `GET /admin/users/{id}/roles` & `POST /admin/users/{id}/roles`.
  - In `admin/approval.html`:
    - Add role selection checkboxes with clean labels ("Branch Manager", "Customer Service", "Teller", "Administrator").
  - In `admin/dashboard.html`:
    - Add section/tab to view all registered users and button to create user directly or edit their roles.
  - Create `admin/user_create.html` & `admin/user_edit_roles.html`.

- [ ] **Step 4: Run tests to verify they pass**
  Run: `.\mvnw test "-Dtest=AdminUserServiceTest,AdminUserControllerTest"`
  Expected: PASS.

- [ ] **Step 5: Commit changes**
  `git commit -am "feat: implement RBAC custom role assignment on approval and admin direct user creation"`

---

### Task 5: End-to-End Regression & Playwright Verification

**Files:**
- Modify: `playwright-tests/tests/auth.setup.ts`
- Modify: `playwright-tests/tests/signup.spec.ts`
- Modify: `docs/superpowers/plans/2026-11-05-e2e-mcp-playwright-test-plan.md`
- Test: Playwright MCP E2E execution

**Interfaces:**
- Consumes: Running Spring Boot server at `http://localhost:8080`, Playwright MCP
- Produces: 100% passing E2E verification of new Auth & RBAC workflows

- [ ] **Step 1: Update Playwright tests to align with no-module login**
  In `auth.setup.ts`, remove module argument or pass empty to verify auto-routing works.
  In `signup.spec.ts`, verify signup -> pending -> admin approves with custom role -> user logs in to designated dashboard.

- [ ] **Step 2: Run complete Maven test suite**
  Run: `.\mvnw test`
  Expected: All tests pass.

- [ ] **Step 3: Run Playwright MCP verification**
  Use Playwright MCP to test:
  1. Login with `admin` -> lands on `/admin/dashboard` or `/dashboard`.
  2. Login with `zann` (CS) -> lands on `/cs/dashboard`.
  3. Login with `spv` (Branch Manager) -> lands on `/supervisor/dashboard`.
  4. Login with `teller` (Teller) -> lands on `/teller/dashboard`.
  5. Signup new student account -> Admin approves and assigns `ROLE_TELLER` -> student logs in and lands directly on `/teller/dashboard`!

- [ ] **Step 4: Commit final changes**
  `git commit -am "test: verify auth, RBAC, and unified dashboard workflows with Playwright"`

