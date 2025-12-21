//
//  ProductListView.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//


import SwiftUI

struct ProductListView: View {

    @EnvironmentObject var appState: AppState

    var body: some View {
        NavigationStack {
            Group {
                if appState.isLoading {
                    ProgressView("Loading products...")
                        .accessibilityIdentifier("productsLoading")
                } else {
                    List(appState.products) { product in
                        ProductRow(product: product)
                            .environmentObject(appState)
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets(top: 10, leading: 16, bottom: 10, trailing: 16))
                            .listRowBackground(Color.clear)
                            .contentShape(Rectangle())
                            .onTapGesture {
                                appState.route = .productDetail(product)
                            }
                    }
                    .listStyle(.plain)
                    .scrollContentBackground(.hidden)
                    .refreshable {
                        await appState.loadProducts(initial: false)
                    }
                    .accessibilityIdentifier("productsList")
                }
            }
            .task {
                await appState.loadProductsIfNeeded()
            }
            .refreshable {
                await appState.loadProducts(initial: false)
            }.toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        appState.route = .settings
                    } label: {
                        Image(systemName: "gearshape")
                    }
                    .accessibilityIdentifier("openSettings")
                }
            }

        }
    }
}
