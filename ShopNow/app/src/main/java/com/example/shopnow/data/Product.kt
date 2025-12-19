package com.example.shopnow.data

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    @DrawableRes val imageRes: Int
)
