//
//  ProductDetailView.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//


import SwiftUI

struct ProductDetailView: View {
    
    @EnvironmentObject var appState: AppState
    let product: Product
    
    var isFavorite: Bool {
        appState.favoriteIds.contains(product.id)
    }
    
    var body: some View {VStack(spacing: 0) {
        HStack {
            Button {
                appState.route = .products
            } label: {
                Image(systemName: "chevron.left")
                    .font(.headline)
            }
            .padding()
            
            Spacer()
        }
        
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                
                Image(product.imageName)
                    .resizable()
                    .scaledToFill()
                    .frame(height: 280)
                    .clipped()
                    .overlay(alignment: .topTrailing) {
                        Button {
                            appState.toggleFavorite(productId: product.id)
                        } label: {
                            Image(systemName: isFavorite ? "heart.fill" : "heart")
                                .font(.title2)
                                .foregroundStyle(Theme.primary)
                                .padding(12)
                                .background(.ultraThinMaterial)
                                .clipShape(Circle())
                        }
                        .accessibilityIdentifier("detailFavToggle")
                        .padding()
                    }
                
                VStack(alignment: .leading, spacing: 8) {
                    Text(product.name)
                        .font(.title2)
                        .fontWeight(.semibold)
                    
                    Text(product.price)
                        .font(.headline)
                        .foregroundStyle(.secondary)
                    
                    Text(product.description)
                        .font(.body)
                }
                .padding(.horizontal)
                
            }
        }
        .navigationBarBackButtonHidden(false)
        .accessibilityIdentifier("productDetail")
    }
    }
}
