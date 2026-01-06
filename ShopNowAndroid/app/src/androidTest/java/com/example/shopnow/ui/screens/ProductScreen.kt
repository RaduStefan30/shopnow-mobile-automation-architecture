package com.example.shopnow.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.shopnow.features.products.ProductTags

class ProductsScreen<A : ComponentActivity>(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>
) {
    fun assertDisplayed() {
        rule.onNodeWithTag(ProductTags.SCREEN).assertIsDisplayed()
    }

    fun assertLoadingVisible() {
        rule.onNodeWithTag(ProductTags.LOADING).assertIsDisplayed()
    }

    fun waitForListVisible(timeoutMs: Long = 10_000) {
        rule.waitUntil(timeoutMs) {
            rule.onAllNodesWithTag(ProductTags.LIST).fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag(ProductTags.LIST).assertIsDisplayed()
    }

    fun openProduct(productId: String) {
        rule.onNodeWithTag(ProductTags.ITEM_PREFIX + productId).assertIsDisplayed()
        rule.onNodeWithTag(ProductTags.ITEM_PREFIX + productId).performClick()
    }

    fun pullToRefresh() {
        // gesture pe lista
        rule.onNodeWithTag(ProductTags.LIST)
            .performTouchInput { swipeDown() }
    }

    fun waitForProductItem(productId: String, timeoutMs: Long = 10_000) {
        rule.waitUntil(timeoutMs) {
            rule.onAllNodesWithTag(ProductTags.ITEM_PREFIX + productId).fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag(ProductTags.ITEM_PREFIX + productId).assertIsDisplayed()
    }

    fun openSettings() {
        rule.onNodeWithTag(ProductTags.SETTINGS).performClick()
    }

    fun assertItemFavoriteOn(productId: String) {
        rule.onNodeWithTag(ProductTags.FAV_PREFIX + productId).assertIsDisplayed()
    }
}
