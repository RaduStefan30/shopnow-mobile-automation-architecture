package com.example.shopnow.data

import com.example.shopnow.R
import kotlinx.coroutines.delay

class FakeBackend(
    private val networkDelayMs: Long = 2500L
) {
    private var fetchCount: Int = 0

    suspend fun fetchProducts(): List<Product> {
        delay(networkDelayMs)
        fetchCount++

        val base = listOf(
            Product("1","Neon Green Sneakers","€89.00","Lightweight neon green sneakers with great comfort.", R.drawable.shoes1),
            Product("2","Red Sneakers","€89.00","Lightweight red sneakers with great comfort.", R.drawable.shoes2),
            Product("3","Black T-Shirt","€19.00","Soft and premium T-Shirt made from unicorn hair.", R.drawable.t_shirt),
            Product("4","Navi Shirt","€199.00","Probably a navy Shirt", R.drawable.shirt),
        )

        val extra = Product(
            id = "r$fetchCount",
            name = "Promotional refreshable item #$fetchCount",
            price = "€${9 + fetchCount}.00",
            description = "This item appears after refresh to prove refresh works.",
            imageRes = R.drawable.cap
        )

        return if (fetchCount == 1) base else (listOf(extra) + base)
    }
}
