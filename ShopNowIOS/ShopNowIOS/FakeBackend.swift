import Foundation
import os

actor FakeBackend {

    private let poiLog = OSLog(subsystem: Bundle.main.bundleIdentifier ?? "ShopNowIOS",
                               category: "PointsOfInterest")

    private let delayNanoseconds: UInt64 = 2_500_000_000
    private var fetchCount: Int = 0

    func fetchProducts() async throws -> [Product] {
        // BEGIN signpost
        os_signpost(.begin, log: poiLog, name: "SHOPNOW_FETCH_PRODUCTS")

        defer {
            // END signpost
            os_signpost(.end, log: poiLog, name: "SHOPNOW_FETCH_PRODUCTS")
        }

        try await Task.sleep(nanoseconds: delayNanoseconds)
        fetchCount += 1

        let base = [
            Product(id: "1", name: "Neon Green Sneakers", price: "€89.00",
                    description: "Lightweight neon green sneakers with great comfort.", imageName: "shoes1"),
            Product(id: "2", name: "Red Sneakers", price: "€89.00",
                    description: "Lightweight red sneakers with great comfort.", imageName: "shoes2"),
            Product(id: "3", name: "Black T-Shirt", price: "€19.00",
                    description: "Soft and premium T-Shirt made from unicorn hair.", imageName: "t_shirt"),
            Product(id: "4", name: "Navy Shirt", price: "€199.00",
                    description: "Probably a navy shirt.", imageName: "shirt")
        ]

        let extra = Product(
            id: "r\(fetchCount)",
            name: "Promotional refreshable item #\(fetchCount)",
            price: "€\(9 + fetchCount).00",
            description: "This item appears after refresh to prove refresh works.",
            imageName: "cap"
        )

        return fetchCount == 1 ? base : [extra] + base
    }
}
