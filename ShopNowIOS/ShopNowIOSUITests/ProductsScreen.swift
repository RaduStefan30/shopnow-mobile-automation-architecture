//
//  ProductsScreen.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 06.01.2026.
//


import XCTest

final class ProductsScreen {
    private let app: XCUIApplication

    init(app: XCUIApplication) { self.app = app }

    var loading: XCUIElement { app.otherElements["productsLoading"] }
    var list: XCUIElement { app.collectionViews["productsList"].firstMatch }
    var openSettings: XCUIElement { app.buttons["openSettings"] }
    var saveButton: XCUIElement { app.buttons["Save"] }
    
    func waitForList(timeout: TimeInterval = 10) {
        // prove async: loading appears then list appears
        _ = loading.waitForExistence(timeout: 2)
        XCTAssertTrue(list.waitForExistence(timeout: timeout))
        
        if (self.saveButton.exists)
        {
            self.saveButton.tap()
        }
    }

    func openProduct(id: String) {
        let card = app.images["productCard_\(id)"]
        XCTAssertTrue(card.waitForExistence(timeout: 5))
        card.tap()
    }

    func toggleFavInList(id: String) {
        let toggle = app.buttons["favToggle_\(id)"]
        XCTAssertTrue(toggle.waitForExistence(timeout: 5))
        toggle.tap()
    }
    
    func assertFavorite(id: String, state: String) {
        let btn = app.buttons["favToggle_\(id)"]
        XCTAssertTrue(
            btn.waitForExistence(timeout: 5),
            "Favorite toggle does not exist",
        )
        
        let value = btn.value as? String
        XCTAssertEqual(
            value,
            state,
            "Expected favorite='\(state)' but got '\(value ?? "nil")'"
        )
    }

    func openSettingsScreen() {
        XCTAssertTrue(openSettings.waitForExistence(timeout: 5))
        openSettings.tap()
    }

    // Pull-to-refresh
    func pullToRefresh() {
        let list = app.collectionViews["productsList"]
        XCTAssertTrue(list.waitForExistence(timeout: 5))

        let start = list.coordinate(withNormalizedOffset: CGVector(dx: 0.5, dy: 0.2))
        let end = list.coordinate(withNormalizedOffset: CGVector(dx: 0.5, dy: 0.8))

        start.press(forDuration: 0.1, thenDragTo: end)
    }
    
    func assertProductExists(id: String, timeout: TimeInterval = 8) {
        let card = app.images["productCard_\(id)"]
        XCTAssertTrue(card.waitForExistence(timeout: timeout))
    }
    
    
}
