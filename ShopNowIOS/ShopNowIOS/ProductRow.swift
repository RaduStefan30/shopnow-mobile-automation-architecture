//
//  ProductRow.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//
import SwiftUI

struct ProductRow: View {

    @EnvironmentObject var appState: AppState
    let product: Product

    private var isFavorite: Bool {
        appState.favoriteIds.contains(product.id)
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {

            // Image header (similar to Android card image)
            Image(product.imageName)
                .resizable()
                .scaledToFill()
                .frame(height: 180)          // <- mărește imaginea (era 84)
                .clipped()

            // Content
            HStack(alignment: .top, spacing: 12) {
                VStack(alignment: .leading, spacing: 6) {
                    Text(product.name)
                        .font(.headline)
                        .fontWeight(.semibold)
                        .foregroundStyle(.black)

                    Text(product.description)
                        .font(.subheadline)
                        .foregroundStyle(.black.opacity(0.60))
                        .lineLimit(2)

                    Text(product.price)
                        .font(.subheadline)
                        .fontWeight(.semibold)
                        .foregroundStyle(.black)
                }

                Spacer()

                // Heart on the far right (as requested)
                Button {
                    appState.toggleFavorite(productId: product.id)
                } label: {
                    Image(systemName: isFavorite ? "heart.fill" : "heart")
                        .font(.title3)
                        .foregroundStyle(Theme.primary)
                        .padding(10)
                        .background(.white.opacity(0.9))
                        .clipShape(Circle())
                }
                .buttonStyle(.plain)
                .accessibilityIdentifier("favToggle_\(product.id)")
            }
            .padding(14)
        }
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
        .overlay(
            RoundedRectangle(cornerRadius: 18, style: .continuous)
                .stroke(.black.opacity(0.06), lineWidth: 1)
        )
        .accessibilityIdentifier("productCard_\(product.id)")
    }
}
