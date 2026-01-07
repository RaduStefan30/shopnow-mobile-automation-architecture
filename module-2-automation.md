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

## 2. Android Automation

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

### Execution
Tests can be executed via:

```

./gradlew connectedAndroidTest

```

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
