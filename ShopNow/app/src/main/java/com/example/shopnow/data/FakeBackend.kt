package com.example.shopnow.data

import kotlinx.coroutines.delay

class FakeBackend(
    private val networkDelayMs: Long = 2500L
) {
    private var fetchCount: Int = 0

    suspend fun fetchProducts(): List<Product> {
        delay(networkDelayMs)
        fetchCount++

        val base = listOf(
            Product("1", "Purple Hoodie", "€49.99"),
            Product("2", "Pink Sneakers", "€89.00"),
            Product("3", "Wireless Earbuds", "€129.00"),
            Product("4", "Smart Watch", "€199.00"),
        )

        // Make refresh visibly different
        val extra = Product(
            id = "r$fetchCount",
            name = "Refresh item #$fetchCount",
            price = "€${9 + fetchCount}.00"
        )

        // On first load, return base only; on refresh, add extra
        return if (fetchCount == 1) base else (listOf(extra) + base)
    }
}
