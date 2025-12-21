//
//  ContentView.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//

import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject var appState: AppState
    
    var body: some View {
        switch appState.route {
        case .login:
            LoginView()
        case .products:
            ProductListView()
        case .productDetail(let product):
            ProductDetailView(product: product)
        case .settings:
            SettingsView()
        }
    }
}
