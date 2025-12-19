package com.example.shopnow.app

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import com.example.shopnow.data.FakeBackend
import com.example.shopnow.data.Product
import com.example.shopnow.features.login.LoginScreen
import com.example.shopnow.features.products.ProductDetailScreen
import com.example.shopnow.features.products.ProductListScreen
import com.example.shopnow.features.products.ProductListUiState
import com.example.shopnow.features.settings.SettingsScreen

private enum class AppRoute { LOGIN, PRODUCTS, PRODUCT_DETAIL, SETTINGS }


@Composable
fun ShopNowApp() {
    var route by remember { mutableStateOf(AppRoute.LOGIN) }
    val backend = remember { FakeBackend(networkDelayMs = 2500L) }
    val userEmailState = remember { mutableStateOf<String?>(null) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val favoriteIds = remember { mutableStateOf(setOf<String>()) }

    val errorMessageState = remember { mutableStateOf<String?>(null) }
    val validEmail = "test"
    val validPassword = "radu"


    when (route) {
        AppRoute.LOGIN -> {
            LoginScreen(
                errorMessage = errorMessageState.value,
                onLoginClick = { email, password ->
                    val ok = (email == validEmail && password == validPassword)
                    errorMessageState.value = if (ok) null else "Invalid email or password"
                    if (ok) {
                        route = AppRoute.PRODUCTS
                        userEmailState.value = email
                    }

                }
            )
        }

        AppRoute.PRODUCTS -> {
            var uiState by remember { mutableStateOf<ProductListUiState>(ProductListUiState.Loading) }
            var isRefreshing by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()


            fun load(initial: Boolean) {
                scope.launch {
                    if (!initial) isRefreshing = true
                    try {
                        val items = backend.fetchProducts()
                        uiState = ProductListUiState.Data(items)
                    } catch (_: Throwable) {
                        uiState = ProductListUiState.Error("Failed to load products")
                    } finally {
                        if (!initial) isRefreshing = false
                    }
                }
            }

            LaunchedEffect(Unit) { load(initial = true) }

            ProductListScreen(
                uiState = uiState,
                isRefreshing = isRefreshing,
                onRefresh = { load(initial = false) },
                favoriteIds = favoriteIds.value,
                onToggleFavorite = { productId ->
                    favoriteIds.value =
                        if (favoriteIds.value.contains(productId))
                            favoriteIds.value - productId
                        else
                            favoriteIds.value + productId
                },
                onProductClick = { product ->
                    selectedProduct = product
                    route = AppRoute.PRODUCT_DETAIL
                },
                onOpenSettings = { route = AppRoute.SETTINGS }
            )
        }

        AppRoute.PRODUCT_DETAIL -> {
            val p = selectedProduct!!
            val isFav = favoriteIds.value.contains(p.id)

            ProductDetailScreen(
                product = p,
                isFavorite = isFav,
                onToggleFavorite = {
                    favoriteIds.value =
                        if (isFav) favoriteIds.value - p.id
                        else favoriteIds.value + p.id
                },
                onBack = { route = AppRoute.PRODUCTS }
            )
        }

        AppRoute.SETTINGS -> {
            SettingsScreen(
                userEmail = userEmailState.value,
                onBack = { route = AppRoute.PRODUCTS },
                onLogout = {
                    // session reset
                    userEmailState.value = null
                    selectedProduct = null
                    favoriteIds.value = emptySet()
                    route = AppRoute.LOGIN
                }
            )
        }
    }
}
