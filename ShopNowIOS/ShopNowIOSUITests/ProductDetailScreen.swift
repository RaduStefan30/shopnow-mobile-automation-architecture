//
//  ProductDetailScreen.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 06.01.2026.
//


import XCTest

final class ProductDetailScreen {
    private let app: XCUIApplication

    init(app: XCUIApplication) { self.app = app }

    var favToggle: XCUIElement { app.buttons["detailFavToggle"] }

    func toggleFavourite() {
        XCTAssertTrue(favToggle.waitForExistence(timeout: 2))
        favToggle.tap()
    }

    func goBack() {
        let back = app.buttons["chevron.left"]
        back.tap()
    }
}
