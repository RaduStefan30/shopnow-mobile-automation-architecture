package com.example.shopnow.app
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import com.example.shopnow.data.FakeBackend
import com.example.shopnow.data.Product
import com.example.shopnow.features.login.LoginScreen
import com.example.shopnow.features.products.ProductDetailScreen
import com.example.shopnow.features.products.ProductListScreen
import com.example.shopnow.features.products.ProductListUiState

private enum class AppRoute {
    LOGIN,
    PRODUCTS,
    PRODUCT_DETAIL
}


@Composable
fun ShopNowApp() {
    val backend = remember { FakeBackend(networkDelayMs = 2500L) }

    var route by remember { mutableStateOf(AppRoute.LOGIN) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }


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
                    if (ok) route = AppRoute.PRODUCTS
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
                onProductClick = { product ->
                    selectedProduct = product
                    route = AppRoute.PRODUCT_DETAIL
                }
            )
        }

        AppRoute.PRODUCT_DETAIL -> {
            ProductDetailScreen(
                product = selectedProduct!!,
                onBack = {
                    route = AppRoute.PRODUCTS
                }
            )
        }

    }
}
