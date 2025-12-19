package com.example.shopnow.features.products

import com.example.shopnow.data.Product

sealed interface ProductListUiState {
    data object Loading : ProductListUiState
    data class Data(val items: List<Product>) : ProductListUiState
    data class Error(val message: String) : ProductListUiState
}