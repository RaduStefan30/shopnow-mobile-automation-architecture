# ShopNow Mobile — QA Mobile Architect Exercise

This repository contains a **demo mobile e-commerce application** built to demonstrate **QA strategy, testability, automation readiness, async handling, and performance considerations**, as requested in the **QA Mobile Architect technical exercise**.

The project focuses on **quality architecture**, not production completeness.


## 📱 Application Overview

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


**ShopNow Mobile** is a fictional mobile e-commerce application designed for quick purchases from a smartphone.

The app allows users to:

* Log in
* Browse a product catalog
* View product details
* Add / remove favourite products
* Refresh content manually
* Manage basic user settings

The backend is intentionally **fake and deterministic**, enabling reliable testing and full reproducibility.

---

## 🧩 Implemented Features

### 1. Login

* Email & password input
* Valid / invalid authentication handling
* Error message displayed on invalid credentials
* Navigation to Products on success

Authentication is intentionally simplified (hardcoded credentials) to focus on testing behavior rather than security.

---

### 2. Product List

* Product cards with image, name, description, and price
* Manual refresh via **pull-to-refresh**
* Loading indicator during data fetch (simulated)
* Backend delay simulated via **Kotlin Coroutines**
* Navigation to Product Detail

---

### 3. Product Detail

* Product image (fixed aspect ratio)
* Name, description, and price
* Favourite toggle (heart icon)

---

### 4. Favourites

* Session-level persistence (in-memory)
* Add / remove favourites from Product List and Product Detail
* Favourite state reflected consistently across screens

---

### 5. Settings

* Accessible from Product List (top-right icon)
* User information (email only)
* Dark theme toggle (**UI only, non-functional**)
* Log out (session reset)

---

## 🤖 Automation Overview

UI automation is implemented using a **Screen / Page Object pattern** with stable identifiers.

* **Android:** Jetpack Compose UI Testing (Espresso-based instrumentation)
* **iOS:** XCUITest using accessibilityIdentifier

Automated scenarios cover:

* Login and authentication validation
* Async loading state handling
* Backend delay and UI update verification
* Favourites consistency (list ↔ detail)
* Pull-to-refresh behavior
* Logout and session reset

---

## ▶️ Running the Applications

### Android App

**Prerequisites**

* Android Studio
* Android SDK installed
* Android Emulator running or a physical device connected

Java is provided by Android Studio via the embedded JDK. No separate Java installation is required.

**Run the app**

* Open the Android project in Android Studio
* Select an emulator or device
* Click **Run**

From terminal (Android project root):

```bash
./gradlew installDebug
````

---

### iOS App

**Prerequisites**

* macOS
* Xcode 15 or newer
* iOS Simulator installed

**Run the app**

* Open ShopNowIOS.xcodeproj in Xcode
* Select an iOS Simulator
* Press **Run (⌘R)**

---

## 🧪 Running Automated Tests

### Android UI Tests

**Prerequisites**

* Android Emulator running or device connected with USB debugging enabled

#### (Optional) Running Android Emulator from Terminal

Android UI tests can be executed fully from the terminal without opening Android Studio, using the Android SDK command-line tools.

**Prerequisites**

* Android SDK installed (via Android Studio)
* At least one Android Virtual Device (AVD) already created

Verify available emulators:

```bash
emulator -list-avds
```

If the emulator command is not found, ensure the Android SDK tools are available in the system PATH.

On macOS, the default SDK location is:

```text
~/Library/Android/sdk
```

Temporary usage (without modifying PATH):

```bash
~/Library/Android/sdk/emulator/emulator -list-avds
~/Library/Android/sdk/emulator/emulator -avd <AVD_NAME>
```

Recommended: Add Android SDK tools to PATH (macOS)

Add the following lines to `~/.zshrc`:

```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

Reload the shell configuration:

```bash
source ~/.zshrc
```

Start emulator from terminal:

```bash
emulator -avd <AVD_NAME> -no-snapshot -netdelay none -netspeed full
```

Run tests after emulator is started:

```bash
adb devices
./gradlew connectedAndroidTest
```

**Run all UI tests**

```bash
./gradlew connectedAndroidTest
```

**Run a specific test class**

```bash
./gradlew connectedAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.example.shopnow.ui.tests.ShopNowUiTests
```

Android UI tests are located under:

```text
app/src/androidTest/
```

---

### iOS UI Tests

**Run from Xcode**

* Open ShopNowIOS.xcodeproj
* Press **⌘U**

**Run from Terminal**

```bash
xcodebuild test \
  -scheme ShopNowIOS \
  -destination "platform=iOS Simulator,name=iPhone 17,OS=latest"
```

iOS UI tests are located in the `ShopNowIOSUITests` target.

---

## 📌 Notes

* All tests are **fully reproducible** in a standard environment
* No external backend, API keys, or credentials are required
* The project intentionally prioritizes **testability, determinism, and clarity** over production concerns
