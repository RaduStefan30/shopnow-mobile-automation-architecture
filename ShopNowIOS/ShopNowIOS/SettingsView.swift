//
//  SettingsView.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//


import SwiftUI

struct SettingsView: View {

    @EnvironmentObject var appState: AppState
    @State private var darkThemeUiOnly: Bool = false

    var body: some View {
        VStack(spacing: 0) {

            // Header (back + Settings)
            HStack {
                Button {
                    appState.route = .products
                } label: {
                    Image(systemName: "chevron.left")
                        .font(.headline)
                }
                .padding(.horizontal, 12)
                .padding(.vertical, 10)
                .accessibilityIdentifier("settingsBack")

                Text("Settings")
                    .font(.headline)
                    .fontWeight(.semibold)

                Spacer()
            }

            // Content with horizontal padding
            VStack(alignment: .leading, spacing: 16) {

                Text("User information")
                    .font(.headline)

                VStack(alignment: .leading, spacing: 6) {
                    Text("Email")
                        .font(.caption)
                        .foregroundStyle(.secondary)

                    Text(appState.userEmail ?? "—")
                        .font(.body)
                        .fontWeight(.medium)
                        .accessibilityIdentifier("settingsEmail")
                }
                .padding(14)
                .background(.background)
                .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
                .overlay(
                    RoundedRectangle(cornerRadius: 14, style: .continuous)
                        .stroke(.black.opacity(0.06), lineWidth: 1)
                )

                Text("Appearance")
                    .font(.headline)

                HStack {
                    VStack(alignment: .leading, spacing: 4) {
                        Text("Dark theme")
                            .fontWeight(.medium)
                        Text("Visible only (demo). Not applied to the app theme.")
                            .font(.caption)
                            .foregroundStyle(.secondary)
                    }

                    Spacer()

                    Toggle("", isOn: $darkThemeUiOnly)
                        .labelsHidden()
                        .accessibilityIdentifier("settingsDarkToggle")
                }
                .padding(14)
                .background(.background)
                .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
                .overlay(
                    RoundedRectangle(cornerRadius: 14, style: .continuous)
                        .stroke(.black.opacity(0.06), lineWidth: 1)
                )

                Spacer()

                Button {
                    appState.logout()
                } label: {
                    Text("Log out")
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 14)
                }
                .buttonStyle(.borderedProminent)
                .accessibilityIdentifier("logoutButton")

            }
            .padding(.horizontal, 16)
            .padding(.top, 12)
            .padding(.bottom, 16)
            .accessibilityIdentifier("settingsScreen")
        }
    }
}
