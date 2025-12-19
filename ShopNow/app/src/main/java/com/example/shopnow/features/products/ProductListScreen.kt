package com.example.shopnow.features.products

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.example.shopnow.data.Product
import kotlin.math.min
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip


object ProductTags {
    const val SCREEN = "products_screen"
    const val LOADING = "products_loading"
    const val LIST = "products_list"
    const val REFRESHING = "products_refreshing"
    const val ITEM_PREFIX = "product_item_"
}

@Composable
fun ProductListScreen(
    uiState: ProductListUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    favoriteIds: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onOpenSettings: () -> Unit
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current

    val refreshThresholdPx = with(density) { 90.dp.toPx() }
    val refreshHoldPx = with(density) { 56.dp.toPx() }

    // how much user has pulled down
    var pulledPx by remember { mutableFloatStateOf(0f) }

    // avoid capturing stale lambda
    val onRefreshLatest by rememberUpdatedState(onRefresh)

    val hasData = uiState is ProductListUiState.Data

    val connection = remember(hasData, isRefreshing) {
        object : NestedScrollConnection {

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val atTop =
                    listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0

                // available.y > 0 means user drags down and child can't consume more scroll
                if (source == NestedScrollSource.UserInput && available.y > 0 && atTop && hasData && !isRefreshing) {
                    // cap pull so it doesn't go infinite
                    pulledPx = min(refreshThresholdPx * 2f, pulledPx + available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (hasData && !isRefreshing && pulledPx >= refreshThresholdPx) {
                    onRefreshLatest()
                }
                // always snap back pull amount on release
                pulledPx = 0f
                return Velocity.Zero
            }
        }
    }

    // Content offset: whole UI moves with finger (elastic), and stays slightly down while refreshing
    val targetOffsetPx = when {
        isRefreshing -> refreshHoldPx
        hasData -> pulledPx / 2f
        else -> 0f
    }
    val contentOffsetY by animateFloatAsState(
        targetValue = targetOffsetPx,
        label = "contentOffset"
    )

    // Header height animates with pull, or stays visible while refreshing
    val headerTargetDp = when {
        isRefreshing -> 56.dp
        hasData && pulledPx > 0f -> with(density) { (pulledPx / 2f).toDp().coerceAtMost(56.dp) }
        else -> 0.dp
    }
    val headerHeight by animateDpAsState(
        targetValue = headerTargetDp,
        label = "refreshHeaderHeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(ProductTags.SCREEN)
            .nestedScroll(connection)
            .padding(
                start = 16.dp, end = 16.dp, top = 48.dp, bottom = 24.dp
            )
    ) {
        // MAIN CONTENT (moves down on pull)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = contentOffsetY }
        ) {
            when (uiState) {
                ProductListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(ProductTags.LOADING)
                    )
                }

                is ProductListUiState.Data -> {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Products",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = onOpenSettings,
                                modifier = Modifier.testTag("products_open_settings")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }

                        Spacer(Modifier.height(32.dp))
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag(ProductTags.LIST),
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            contentPadding = PaddingValues(bottom = 20.dp)
                        )
                        {
                            items(uiState.items) { p ->
                                ProductCard(
                                    product = p,
                                    isFavorite = favoriteIds.contains(p.id),
                                    onToggleFavorite = onToggleFavorite,
                                    onClick = onProductClick
                                )
                            }

                        }
                    }
                }

                is ProductListUiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        // HEADER (stays at top, shows spinner/text)
        if (hasData) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .clipToBounds()
                    .height(headerHeight)
            ) {
                if (headerHeight > 0.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(18.dp)
                                .testTag(ProductTags.REFRESHING)
                        )
                        Spacer(Modifier.width(10.dp))

                        val thresholdReached = pulledPx >= refreshThresholdPx
                        val text = when {
                            isRefreshing -> "Refreshing…"
                            thresholdReached -> "Release to refresh"
                            else -> "Pull to refresh"
                        }

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onToggleFavorite: (String) -> Unit,
    onClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(ProductTags.ITEM_PREFIX + product.id),
        onClick = { onClick(product) },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Image block (uniform look)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f) // a bit wider than square; change to 1f if you want square
            ) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Heart overlay (top-right)
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Toggle favourite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(26.dp)
                        .testTag("fav_toggle_${product.id}")
                        .clickable { onToggleFavorite(product.id) }
                )
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    maxLines = 2
                )
            }
        }
    }
}
