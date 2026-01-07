import SwiftUI

struct ProductRow: View {

    @EnvironmentObject var appState: AppState
    let product: Product

    private var isFavorite: Bool {
        appState.favoriteIds.contains(product.id)
    }

    var body: some View {
        VStack(spacing: 0) {

            ZStack(alignment: .topTrailing) {
                Image(product.imageName)
                    .resizable()
                    .scaledToFill()
                    .frame(height: 180)
                    .clipped()
                    .accessibilityIdentifier("productCard_\(product.id)")

                Button {
                    appState.toggleFavorite(productId: product.id)
                } label: {
                    Image(systemName: isFavorite ? "heart.fill" : "heart")
                        .font(.title3)
                        .foregroundStyle(Theme.primary)
                        .padding(10)
                        .background(.ultraThinMaterial)
                        .clipShape(Circle())
                }
                .buttonStyle(.plain)
                .padding(10)
                .accessibilityIdentifier("favToggle_\(product.id)")
                .accessibilityValue(isFavorite ? "on" : "off")
            }

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
                }

                Spacer()

                Text(product.price)
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .foregroundStyle(.black)
                    .padding(.top, 2)
                    .accessibilityIdentifier("price_\(product.id)")
            }
            .padding(14)
            .background(Color.black.opacity(0.04))
        }
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
        .overlay(
            RoundedRectangle(cornerRadius: 18, style: .continuous)
                .stroke(.black.opacity(0.06), lineWidth: 1)
        )
    }
}
