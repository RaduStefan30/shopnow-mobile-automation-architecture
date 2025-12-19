package com.example.shopnow.features.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopnow.data.Product
import androidx.compose.material.icons.filled.ArrowBack


object ProductDetailTags {
    const val SCREEN = "product_detail_screen"
    const val IMAGE = "product_detail_image"
    const val TOGGLE_FAV = "product_detail_toggle_fav"
    const val BACK = "product_detail_back"
}

@Composable
fun ProductDetailScreen(
    product: Product,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(ProductDetailTags.SCREEN)
    ) {

        Spacer(Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag(ProductDetailTags.BACK)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Product details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(24.dp))

        Image(
            painter = painterResource(product.imageRes),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.large)
                .testTag(ProductDetailTags.IMAGE)
        )

        Spacer(Modifier.height(16.dp))

        // 📝 TITLE + FAV
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                product.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Toggle favourite",
                modifier = Modifier
                    .size(28.dp)
                    .testTag(ProductDetailTags.TOGGLE_FAV)
                    .clickable { onToggleFavorite() }
            )
        }

        Spacer(Modifier.height(8.dp))
        Text(product.price, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Text(product.description, style = MaterialTheme.typography.bodyMedium)
    }
}
