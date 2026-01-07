//
//  SettingsScreen.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 06.01.2026.
//


import XCTest

final class SettingsScreen {
    private let app: XCUIApplication

    init(app: XCUIApplication) { self.app = app }

    var screen: XCUIElement { app.otherElements["settingsScreen"] }
    var logout: XCUIElement { app.buttons["logoutButton"] }
    var back: XCUIElement { app.buttons["settingsBack"] }
    var darkToggle: XCUIElement { app.switches["settingsDarkToggle"] }

    func waitForDisplayed(timeout: TimeInterval = 5) {
        XCTAssertTrue(screen.waitForExistence(timeout: timeout))
    }

    func logoutUser() {
        XCTAssertTrue(logout.waitForExistence(timeout: 3))
        logout.tap()
    }

    func goBack() {
        if back.exists {
            back.tap()
        } else {
            app.swipeRight()
        }
    }
}
