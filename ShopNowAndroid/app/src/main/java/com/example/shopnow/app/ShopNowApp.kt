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

private enum class AppRoute {
    LOGIN,
    PRODUCTS,
    PRODUCT_DETAIL,
    SETTINGS
}

@Composable
fun ShopNowApp() {
    var route by remember { mutableStateOf(AppRoute.LOGIN) }
    val backend = remember { FakeBackend(networkDelayMs = 2500L) }
    val userEmailState = remember { mutableStateOf<String?>(null) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val favoriteIds = remember { mutableStateOf(setOf<String>()) }
    var productsUiState by remember {
        mutableStateOf<ProductListUiState>(ProductListUiState.Loading)
    }
    var isRefreshing by remember { mutableStateOf(false) }
    var hasLoadedProducts by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val errorMessageState = remember { mutableStateOf<String?>(null) }
    val validEmail = "test"
    val validPassword = "radu"

    fun loadProducts(initial: Boolean) {
        scope.launch {
            if (!initial) isRefreshing = true
            try {
                val items = backend.fetchProducts()
                productsUiState = ProductListUiState.Data(items)
            } catch (_: Throwable) {
                productsUiState = ProductListUiState.Error("Failed to load products")
            } finally {
                if (!initial) isRefreshing = false
            }
        }
    }

    LaunchedEffect(route) {
        if (route == AppRoute.PRODUCTS && !hasLoadedProducts) {
            loadProducts(initial = true)
            hasLoadedProducts = true
        }
    }

    when (route) {

        AppRoute.LOGIN -> {
            LoginScreen(
                errorMessage = errorMessageState.value,
                onLoginClick = { email, password ->
                    val ok = (email == validEmail && password == validPassword)
                    errorMessageState.value =
                        if (ok) null else "Invalid email or password"

                    if (ok) {
                        userEmailState.value = email
                        route = AppRoute.PRODUCTS
                    }
                }
            )
        }

        AppRoute.PRODUCTS -> {
            ProductListScreen(
                uiState = productsUiState,
                isRefreshing = isRefreshing,
                onRefresh = { loadProducts(initial = false) },
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
            val product = selectedProduct!!
            val isFav = favoriteIds.value.contains(product.id)

            ProductDetailScreen(
                product = product,
                isFavorite = isFav,
                onToggleFavorite = {
                    favoriteIds.value =
                        if (isFav)
                            favoriteIds.value - product.id
                        else
                            favoriteIds.value + product.id
                },
                onBack = { route = AppRoute.PRODUCTS }
            )
        }

        AppRoute.SETTINGS -> {
            SettingsScreen(
                userEmail = userEmailState.value,
                onBack = { route = AppRoute.PRODUCTS },
                onLogout = {
                    userEmailState.value = null
                    selectedProduct = null
                    favoriteIds.value = emptySet()
                    productsUiState = ProductListUiState.Loading
                    hasLoadedProducts = false
                    route = AppRoute.LOGIN
                }
            )
        }
    }
}
