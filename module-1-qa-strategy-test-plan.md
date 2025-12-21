# Module 1 — QA Strategy & Test Plan

## Part A — QA Strategy

### 1. Purpose & Context

In a real-world project, the QA strategy and test plan would be defined after an initial discovery and analysis phase, including clarified product requirements, functional specifications, and non-functional expectations.

For the purpose of this technical exercise, the following QA Strategy defines the high-level quality principles, goals, and decision-making framework that would guide testing activities from the beginning of the project. The goal is not feature completeness, but demonstrating a scalable quality approach, automation readiness, and practical handling of asynchronous and performance-critical scenarios.

This strategy is intentionally designed to remain valid even if the application scope evolves, while the concrete Test Plan (defined later in this document) may change as features are added or refined.

---

### 2. Quality Principles

The following principles guide all quality-related decisions in this project:

* **Automation as fast feedback**
  Test automation is primarily used to provide fast and reliable feedback to developers, not as a late validation step after feature completion with case by case exceptions.

* **Stability over quantity**
  Test reliability and maintainability are prioritised over having a large number of automated tests. A smaller, stable suite is preferred to a large, flaky one.

* **Happy-path coverage by default**
  Each major application component is covered by at least one automated happy path to ensure core functionality remains intact.

* **Selective negative testing**
  Negative and edge-case scenarios are automated selectively, with focus on business-critical flows rather than exhaustive coverage.

* **Parallel development and testing**
  Automation is designed to be developed in parallel with feature implementation, enabling continuous feedback rather than sequential handoffs between development and QA.

---

### 3. Quality Goals

This QA strategy aims to achieve the following quality objectives:

* Prevent critical functional regressions in core user flows such as authentication, navigation, and favourites management.
* Ensure predictable and stable behaviour of existing functionality as the application evolves.
* Reduce assumptions by validating behaviour against clearly clarified requirements.
* Provide sufficient confidence for release decisions through a combination of automated regression and targeted manual validation.
* Maintain cross-platform consistency between Android and iOS implementations.

---

### 4. Quality Approach & Test Pyramid

The quality approach follows a pragmatic test pyramid adapted for a mobile application:

* **Unit tests**
  Used to validate pure logic and deterministic behaviour at the lowest level. These tests are created by the developers, but can be reviewed by automation QA engineers if needed.

* **Integration tests**
  Used to validate interactions between state, data layers, and business logic.

* **UI / End-to-End tests**
  Used sparingly to validate critical user journeys on real UI, focusing on stability rather than volume.

* **Targeted manual testing**
  Used for exploratory validation, UX assessment, and scenarios that are not cost-effective or feasible to automate.

This distribution maximises confidence while minimising flakiness and maintenance overhead.

---

### 5. Environment Strategy

For this technical exercise, testing is performed in controlled local environments to ensure determinism and reproducibility:

* Android emulator via Android Studio
* iOS simulator via Xcode
* Deterministic fake backend with configurable network delay
* No dependency on external services or real network calls

In a production setup, this strategy would be extended to include CI environments (running on pull requests, nightly, and on demand, based on the need for fast developer feedback), multiple device configurations (using on-prem device farms, rented machines, or cloud providers such as BrowserStack or SauceLabs), and real backend environments.

---

### 6. Risk Strategy

The QA strategy acknowledges the following categories of risk:

* **Technical risks**

  * Asynchronous UI flakiness caused by delayed backend responses
  * Inconsistent shared state between screens
  * Navigation regressions
  * Unreliable UI automation due to unstable selectors

* **Product and process risks**

  * Unclear or insufficiently documented requirements leading to incorrect assumptions
  * Limited test coverage in high-risk future areas such as cart and payment flows
  * Team relies too much on automation quality for release confidence and reduces manual efforts

Risks are mitigated through deterministic test environments, state-driven synchronisation, selective automation of critical paths, and close collaboration between QA, development, and product roles.

---

### 7. Metrics & KPIs

The effectiveness of the QA strategy is evaluated using the following indicators:

* **Automation Coverage (Critical Flows)**
  Percentage of priority user flows covered by automated tests.
  
* **Automation stability**
  Low fluctuation in test results across multiple executions without code changes.

* **Defects Detected by Automation**
  Defects detected by automation testing.

* **Performance monitoring**
  Establishment of baseline performance metrics at a stable application state and evaluation of regressions over time rather than relying solely on absolute thresholds (unless specified explicitly by product).

---

### 8. Definition of Done

**Definition of Done for a user story:**

* Requirements are clarified and validated
* Relevant test cases are defined
* Automated tests are implemented or updated
* The story is deployed to the staging/production environment without any issues

**Definition of Done from a testing perspective (project level):**

* Critical user flows are covered by stable automated tests
* Test execution is reproducible in a clean environment
* Agreed test coverage thresholds are met in collaboration with product stakeholders
* The application reaches a stable or maintenance phase with predictable quality signals

---

## Part B — Test Plan

### 9. Scope

#### In Scope

* Functional validation of critical user journeys:

  * Login with valid and invalid credentials
  * Product list loading, including loading indicators and delayed responses
  * Navigation to product detail
  * Add / remove favourites and session-level state consistency
  * Settings (logout, user information, UI-only dark theme toggle only)

* Validation of asynchronous behaviour:

  * State-driven UI updates
  * Proper handling of loading states
  * Stability under delayed backend responses

* Performance checks:

  * Cold start behaviour
  * Product list loading time under simulated network delay

#### Out of Scope (for this exercise)

* Real backend integration and API contract testing
* Long-term data persistence and offline scenarios
* Accessibility and localisation testing
* Security and penetration testing
* Large-scale load and stress testing

---

### 10. Test Coverage & Prioritisation

### Requirements Traceability Matrix (RTM)

| Req ID | Requirement Description | Priority | Related Test Cases |
|------|--------------------------|----------|--------------------|
| R1 | User authentication using email and password | P0 | TC-LOGIN-01, TC-LOGIN-02 |
| R2 | Product list loading state while data is being fetched | P0 | TC-LIST-01 |
| R3 | Product list updated when backend data becomes available | P0 | TC-LIST-01 |
| R4 | Manual refresh of the product list | P1 | TC-LIST-02 |
| R5 | Navigation from product list to product detail | P1 | TC-DETAIL-01 |
| R6 | Display of product details (image, name, description, price) | P1 | TC-DETAIL-01 |
| R7 | Add and remove products from favourites | P1 | TC-FAV-01 |
| R8 | Favourite state consistency between list and detail views | P1 | TC-FAV-01 |
| R9 | Session-level reset of favourites on logout | P1 | TC-LOGOUT-01 |
| R10 | Display of user information in settings | P2 | TC-SET-01 |
| R11 | Visibility of dark theme toggle (UI-only) | P2 | TC-SET-02 |
| R12 | User logout functionality | P1 | TC-LOGOUT-01 |

### Test Case Reference

| Test Case ID | Test Case Description | Type |
|-------------|-----------------------|------|
| TC-LOGIN-01 | Login with valid credentials | UI (E2E) |
| TC-LOGIN-02 | Login with invalid credentials | UI |
| TC-LIST-01 | Product list shows loading indicator and updates after delayed backend response | UI (Async) |
| TC-LIST-02 | Manual refresh reloads product list | UI |
| TC-DETAIL-01 | Open product detail and validate displayed information | UI |
| TC-FAV-01 | Add and remove favourites and verify state consistency | UI |
| TC-LOGOUT-01 | Logout resets session and favourites | UI (E2E) |
| TC-SET-01 | Settings screen displays user information | UI |
| TC-SET-02 | Dark theme toggle is visible | UI |

---

### 12. Execution & Failure Handling

* A minimal end-to-end smoke flow (login → logout) is executed on pull requests to provide fast feedback.
* Broader regression suites can be executed on demand or multiple times per day, depending on available resources.
* Intermittently failing tests are temporarily disabled to preserve trust in the test suite and tracked via dedicated investigation tasks to identify root causes.
* Release decisions and test execution scope are agreed collaboratively across QA, development, and product teams.

---

### 13. Test Deliverables

The following deliverables are produced as part of this test plan:

* Automated test execution reports
* Coverage and automation metrics per component
* Logs, screenshots, and failure evidence
* Video recordings or test replays where supported by the execution platform

---

### Final Note

This document intentionally separates long-term quality strategy from feature-specific test planning. While the Test Plan may evolve as the application grows, the QA Strategy provides a stable foundation for consistent, scalable quality practices.
