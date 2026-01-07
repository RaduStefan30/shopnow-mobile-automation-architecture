//
//  LoginScreen.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 06.01.2026.
//


import XCTest

final class LoginScreen {
    private let app: XCUIApplication
    
    init(app: XCUIApplication) { self.app = app }
    
    var email: XCUIElement { app.textFields["loginEmail"] }
    var password: XCUIElement { app.secureTextFields["loginPassword"] }
    var loginButton: XCUIElement { app.buttons["loginButton"] }
    var error: XCUIElement { app.staticTexts["loginError"] }
    
    func waitForDisplayed(timeout: TimeInterval = 3) {
        XCTAssertTrue(email.waitForExistence(timeout: timeout))
        XCTAssertTrue(loginButton.waitForExistence(timeout: timeout))
    }
    
    func login(email: String, password: String) {
        self.email.tap()
        self.email.typeText(email)
        
        self.password.tap()
        self.password.typeText(password)
        self.password.typeText("\n")
        
        loginButton.tap()
        
    }
    
    func assertErrorVisible(timeout: TimeInterval = 2) {
        XCTAssertTrue(error.waitForExistence(timeout: timeout))
    }
}
