package com.example.shopnow.features.products

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopnow.data.Product

object ProductDetailTags {
    const val SCREEN = "product_detail_screen"
    const val BACK = "product_detail_back"
}

@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp)
            .testTag(ProductDetailTags.SCREEN)
    ) {
        Column {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(48.dp))

            Text(
                text = "Price: ${product.price}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "This is a demo product description.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .testTag(ProductDetailTags.BACK)
                .padding(bottom = 50.dp)
        ) {
            Text("Back to products")
        }
    }
}
