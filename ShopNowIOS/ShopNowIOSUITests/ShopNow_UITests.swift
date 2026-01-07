//
//  ShopNow.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 06.01.2026.
//


import XCTest

final class ShopNow_UITests: XCTestCase {

    private let validEmail = "test@email.com"
    private let validPassword = "radu"

    private func launchApp() -> XCUIApplication {
        let app = XCUIApplication()
        app.launch()
        return app
    }

    // 1) Task A: login -> loading -> list visible after delay
    func test_taskA_loginShowsLoading_thenDisplaysProductsList() {
        let app = launchApp()
        let login = LoginScreen(app: app)
        let products = ProductsScreen(app: app)

        login.waitForDisplayed()
        login.login(email: validEmail, password: validPassword)
        products.waitForList(timeout: 12)
    }

    // 2) Favourites sync (toggle in detail -> reflected in list)
    func test_favourites_toggleInDetail_reflectedInList() {
        let app = launchApp()
        let login = LoginScreen(app: app)
        let products = ProductsScreen(app: app)
        let detail = ProductDetailScreen(app: app)

        login.login(email: validEmail, password: validPassword)
        products.waitForList()

        products.openProduct(id: "1")
        detail.toggleFavourite()
        detail.goBack()

        products.assertFavorite(id: "1", state: "on")
}

    // 3) Logout resets session and returns to login
    func test_settings_logout_resetsSession_andReturnsToLogin() {
        let app = launchApp()
        let login = LoginScreen(app: app)
        let products = ProductsScreen(app: app)
        let settings = SettingsScreen(app: app)

        login.login(email: validEmail, password: validPassword)
        products.waitForList()

        products.openSettingsScreen()
        settings.waitForDisplayed()

        settings.logoutUser()

        login.waitForDisplayed()
    }

    // 4) Invalid login shows error
    func test_login_invalidCredentials_showsError() {
        let app = launchApp()
        let login = LoginScreen(app: app)

        login.waitForDisplayed()
        login.login(email: "wrong", password: "wrong")

        login.assertErrorVisible()
    }

    // 5) Pull-to-refresh adds new item (assuming your iOS fake backend does the same)
    func test_products_pullToRefresh_addsNewItem() {
        let app = launchApp()
        let login = LoginScreen(app: app)
        let products = ProductsScreen(app: app)

        login.login(email: validEmail, password: validPassword)
        products.waitForList()

        products.pullToRefresh()
        products.assertProductExists(id: "r2", timeout: 10)
    }
}
