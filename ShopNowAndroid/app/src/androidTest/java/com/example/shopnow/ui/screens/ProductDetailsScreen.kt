package com.example.shopnow.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.example.shopnow.features.products.ProductDetailTags

class ProductDetailScreen(p: SemanticsNodeInteractionsProvider) {

    private val screen = p.onNodeWithTag(ProductDetailTags.SCREEN)
    private val toggleFav = p.onNodeWithTag(ProductDetailTags.TOGGLE_FAV)
    private val back = p.onNodeWithTag(ProductDetailTags.BACK)

    fun assertDisplayed() = screen.assertIsDisplayed()

    fun toggleFavorite() {
        toggleFav.performClick()
    }

    fun back() {
        back.performClick()
    }
}
