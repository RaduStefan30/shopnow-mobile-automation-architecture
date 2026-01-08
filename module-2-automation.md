# 📄 Module 2 — Automation

This module covers the implementation of real, executable UI automation
for both Android and iOS, following the requirements of the QA Mobile Architect exercise.

Automation focuses on validating critical user flows, asynchronous behavior,
and state consistency, rather than exhaustive UI coverage.

## 1. Automation Scope

The following critical user flows are automated:

- Login (valid and invalid scenarios)
- Asynchronous product loading with visible loading indicator
- Manual refresh of product list
- Product detail navigation
- Favourite toggle and state consistency
- Settings navigation and logout

The goal is to validate application behavior and state transitions, not visual styling.

## Task A - Android Automation

### Framework & Approach
- Jetpack Compose UI Testing (Espresso-based instrumentation)
- Kotlin
- Screen Pattern (Page Object Model)
- Stable test tags via Compose semantics

### Key Characteristics
- No fixed sleeps or time-based waits
- Synchronization based on UI state and Compose idling
- Deterministic fake backend with simulated delay
- Each test is fully independent

### Implemented Tests
1. Login → loading indicator → product list displayed (async validation)
2. Favourite toggle reflected between list and detail
3. Logout resets session and returns to login
4. Invalid login shows error
5. Pull-to-refresh updates product list

## 3. iOS Automation

### Framework & Approach
- XCUITest
- Swift
- Screen Pattern (Page Object Model)
- Accessibility identifiers for stable element targeting

### Key Characteristics
- Asynchronous synchronization via waitForExistence
- No hardcoded delays
- Deterministic fake backend behavior
- Tests isolated and repeatable

### Implemented Tests
1. Login → loading indicator → product list displayed (async validation)
2. Favourite toggle consistency between list and detail
3. Logout resets session
4. Invalid login shows error
5. Pull-to-refresh updates product list

## Evidence & Reproducibility

<table align="center">
  <tr>
    <th>Android Automation</th>
    <th>iOS Automation</th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/d3d5b232-a2e8-4721-a0dc-7c61a5d58238" width="100%" />
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/a344083e-22fb-4140-92cf-f5a9cff1d6f0" width="100%" />
    </td>
  </tr>
</table>

All tests are reproducible by cloning the repository and running them
in a standard Android emulator or iOS simulator environment.


The same principles can be scaled in a real production environment
using API-level setup, dependency injection, and CI integration.

## Task B — Test Synchronisation

### Determining when the app is ready

**Android**
- Synchronisation relies on state-driven UI (Loading / Data)
- Jetpack Compose UI testing automatically waits for recomposition and idle state
- Tests assert on UI state changes (loading → list), not on time delays

**iOS**
- XCUITest uses waitForExistence(timeout:) to wait for UI elements
- Synchronisation is based on element availability, not fixed sleeps

---

### Preventing flakiness

- Stable test tags (Android) and accessibility identifiers (iOS)
- Deterministic fake backend with predictable delays (in this example), in a real production environment for some tests we could use mocking 
- Independent, self-contained UI tests,
- Avoiding Thread.sleep or hardcoded delays

---

### Handling intermittent CI failures

- Capture screenshots and execution videos
- Re-run failed tests to identify flaky patterns
- Investigate state transitions rather than increasing timeouts
- Prefer backend or test-hook setup for expensive flows (e.g. login) in production

## Task C — Async Testing: Technical Explanation

This section explains how asynchronous behavior is handled and validated
in the ShopNow mobile application, both on Android and iOS.

The focus is on reliable synchronization, avoiding flakiness,
and validating real UI state transitions.

---

### 1. Android Approach

On Android, asynchronous behavior is tested using a state-driven UI approach
combined with Jetpack Compose UI testing.

The application exposes explicit UI states (Loading / Data),
and tests assert on these states rather than relying on time-based waits.

Jetpack Compose UI tests automatically synchronize with recompositions
and idle states, allowing tests to proceed only when the UI is ready.

Key points:
- No fixed delays or Thread.sleep calls are used
- Synchronization is achieved by observing UI state changes
- Loading indicators are validated explicitly before asserting final content

---

### 2. iOS Approach

On iOS, XCUITest synchronization is achieved using element-based waiting.

Tests rely on waitForExistence(timeout:) to proceed only when
specific UI elements become available.

This approach ensures that tests wait for real UI readiness
instead of assuming timing behavior.

Key points:
- No fixed delays are used
- Synchronization is based on accessibility identifiers
- Tests validate visible UI state transitions

---

### 3. Anti-patterns Avoided

The following asynchronous testing anti-patterns were intentionally avoided:

- Using Thread.sleep or fixed delays
- Polling UI state without explicit conditions
- Relying on animation completion timing
- Creating dependencies between tests
- Validating backend logic in UI tests
