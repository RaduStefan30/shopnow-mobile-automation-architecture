//
//  Product.swift
//  ShopNowIOS
//
//  Created by Radu-Stefan Ranzascu on 21.12.2025.
//


import Foundation

struct Product: Identifiable, Equatable {
    let id: String
    let name: String
    let price: String
    let description: String
    let imageName: String
}
