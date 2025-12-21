//
//  AppState.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//

import Foundation
import SwiftUI
import Combine

@MainActor
final class AppState: ObservableObject {

    enum Route {
        case login
        case products
        case productDetail(Product)
        case settings
    }

    // Navigation
    @Published var route: Route = .login

    // Session
    @Published var userEmail: String? = nil
    @Published var favoriteIds: Set<String> = []

    // Products
    @Published var products: [Product] = []
    @Published var isLoading: Bool = false
    @Published var isRefreshing: Bool = false
    private var hasLoadedOnce: Bool = false

    private let backend = FakeBackend()

    func loadProducts(initial: Bool) async {
        if initial {
            isLoading = true
        } else {
            isRefreshing = true
        }

        do {
            let items = try await backend.fetchProducts()
            products = items
            hasLoadedOnce = true
        } catch {
            products = []
        }

        isLoading = false
        isRefreshing = false
    }

    func loadProductsIfNeeded() async {
        guard !hasLoadedOnce else { return }
        await loadProducts(initial: true)
    }

    func logout() {
        userEmail = nil
        favoriteIds.removeAll()
        products.removeAll()
        hasLoadedOnce = false
        route = .login
    }

    func toggleFavorite(productId: String) {
        if favoriteIds.contains(productId) {
            favoriteIds.remove(productId)
        } else {
            favoriteIds.insert(productId)
        }
    }
}
