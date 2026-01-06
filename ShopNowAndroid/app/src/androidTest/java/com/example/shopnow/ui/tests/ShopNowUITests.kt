package com.example.shopnow.ui.tests

import com.example.shopnow.MainActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import com.example.shopnow.ui.screens.LoginScreen
import com.example.shopnow.ui.screens.ProductsScreen
import com.example.shopnow.ui.screens.ProductDetailScreen
import com.example.shopnow.ui.screens.SettingsScreen

class ShopNowUiTests {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val validEmail = "test"
    private val validPassword = "radu"

    /**
     * 1) Task A: login -> loading visible -> list visible after delayed backend
     */
    @Test
    fun taskA_loginShowsLoading_thenDisplaysProductsList() {
        val login = LoginScreen(rule)
        val products = ProductsScreen(rule)

        login.assertDisplayed()
        login.login(validEmail, validPassword)

        products.assertLoadingVisible()
        products.waitForListVisible()
        products.assertDisplayed()
    }

    /**
     * 2) Favourites sync (detail -> list reflected)
     * Needs ProductTags.FAV_PREFIX + per-item fav tag in ProductList UI.
     */
    @Test
    fun favourites_toggleInDetail_reflectedInList() {
        val login = LoginScreen(rule)
        val products = ProductsScreen(rule)
        val detail = ProductDetailScreen(rule)

        login.login(validEmail, validPassword)
        products.waitForListVisible()

        products.openProduct("1")
        detail.assertDisplayed()
        detail.toggleFavorite()
        detail.back()

        products.assertItemFavoriteOn("1")
    }

    /**
     * 3) Logout resets session (returns to login)
     * Needs ProductTags.SETTINGS tag for the settings button on Products screen.
     */
    @Test
    fun settings_logout_resetsSession_andReturnsToLogin() {
        val login = LoginScreen(rule)
        val products = ProductsScreen(rule)
        val settings = SettingsScreen(rule)

        login.login(validEmail, validPassword)
        products.waitForListVisible()

        products.openSettings()
        settings.assertDisplayed()

        settings.logout()
        login.assertDisplayed()
    }

    /**
     * 4) Invalid login shows error and stays on login
     */
    @Test
    fun login_invalidCredentials_showsError() {
        val login = LoginScreen(rule)

        login.assertDisplayed()
        login.login("wrong", "wrong")
        login.assertErrorDisplayed()
        login.assertDisplayed()
    }

    /**
     * 5) Pull-to-refresh changes data (fake backend adds r2 on refresh)
     */
    @Test
    fun products_pullToRefresh_addsNewItem() {
        val login = LoginScreen(rule)
        val products = ProductsScreen(rule)

        login.login(validEmail, validPassword)
        products.waitForListVisible()

        products.pullToRefresh()

        // After refresh, FakeBackend should insert item with id "r2"
        products.waitForProductItem("r2")
    }
}
