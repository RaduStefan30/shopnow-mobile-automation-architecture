//
//  ShopNowIOSApp.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//

import SwiftUI
import os

private let launchLog = OSLog(subsystem: Bundle.main.bundleIdentifier ?? "ShopNowIOS",
                              category: "PointsOfInterest")

@main
struct ShopNowIOSApp: App {

    @StateObject private var appState = AppState()
    
    init() {
          os_signpost(.begin, log: launchLog, name: "SHOPNOW_COLD_START")
      }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(appState)
        }
    }
}
