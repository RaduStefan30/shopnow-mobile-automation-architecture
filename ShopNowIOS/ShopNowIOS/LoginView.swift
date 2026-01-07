//
//  LoginView.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//


import SwiftUI

struct LoginView: View {

    @EnvironmentObject var appState: AppState

    @State private var email = ""
    @State private var password = ""
    @State private var errorMessage: String?

    private let peach = Color(red: 1.0, green: 0.92, blue: 0.92) // ~#FFEBEB
    private let primary = Color(red: 0.46, green: 0.33, blue: 1.0) // close to your purple

    var body: some View {
        GeometryReader { geo in
            ZStack {
                peach.ignoresSafeArea()

                VStack(spacing: 0) {
                    Spacer()
                        .frame(height: 200)

                    ZStack(alignment: .top) {

                        WaveTopBackground(waveHeight: 70)
                            .fill(primary)
                            .opacity(1.0)
                            .offset(y: -10)

                        WaveTopBackground(waveHeight: 70)
                            .fill(Color.white)
                            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
                            .ignoresSafeArea(edges: .bottom)

                        VStack(spacing: 12) {
                            
                            Spacer().frame(height: 100)

                            Text("Welcome back!")
                                .font(.title3)
                                .fontWeight(.semibold)
                            
                            
                            Spacer().frame(height: 30)

                            TextField("Email", text: $email)
                                .textFieldStyle(.roundedBorder)
                                .textInputAutocapitalization(.never)
                                .autocorrectionDisabled(true)
                                .keyboardType(.emailAddress)
                                .accessibilityIdentifier("loginEmail")
                                .padding(2)
                                .textContentType(.none)

                            SecureField("Password", text: $password)
                                .textFieldStyle(.roundedBorder)
                                .accessibilityIdentifier("loginPassword")
                                .padding(2)
                                .textContentType(.none)

                            if let errorMessage {
                                Text(errorMessage)
                                    .foregroundStyle(.red)
                                    .font(.subheadline)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .accessibilityIdentifier("loginError")
                            }

                            Button {
                                handleLogin()
                            } label: {
                                Text("Login")
                                    .frame(maxWidth: .infinity)
                                    .padding(.vertical, 12)
                            }
                            .buttonStyle(.borderedProminent)
                            .tint(primary)
                            .accessibilityIdentifier("loginButton")

                            Spacer()

                            Text("Demo app • QA exercise")
                                .font(.caption)
                                .foregroundStyle(primary.opacity(0.9))
                                .padding(.bottom, 16)
                        }
                        .padding(.horizontal, 20)
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)

                }

                VStack {
                    Spacer().frame(height: 120)

                    Image("logo")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 56)
                        .accessibilityIdentifier("loginLogo")

                    Spacer()
                }
            }
        }
    }

    private func handleLogin() {
        let e = email.trimmingCharacters(in: .whitespacesAndNewlines)
        let p = password

        if e == "test@email.com" && p == "radu" {
            errorMessage = nil
            appState.userEmail = e
            appState.route = .products
        } else {
            errorMessage = "Invalid email or password"
        }
    }
}

/// A wave shape at the top edge (like your Compose Canvas wave)
struct WaveTopBackground: Shape {
    let waveHeight: CGFloat

    func path(in rect: CGRect) -> Path {
        let w = rect.width
        let h = rect.height
        let waveH = waveHeight

        var p = Path()
        p.move(to: CGPoint(x: 0, y: waveH))

        // first cubic
        p.addCurve(
            to: CGPoint(x: w * 0.75, y: waveH * 0.9),
            control1: CGPoint(x: w * 0.25, y: waveH * 0.2),
            control2: CGPoint(x: w * 0.55, y: waveH * 1.8)
        )

        // second cubic
        p.addCurve(
            to: CGPoint(x: w, y: waveH),
            control1: CGPoint(x: w * 0.88, y: waveH * 0.4),
            control2: CGPoint(x: w * 0.96, y: waveH * 1.2)
        )

        // fill rest
        p.addLine(to: CGPoint(x: w, y: h))
        p.addLine(to: CGPoint(x: 0, y: h))
        p.closeSubpath()
        return p
    }
}
