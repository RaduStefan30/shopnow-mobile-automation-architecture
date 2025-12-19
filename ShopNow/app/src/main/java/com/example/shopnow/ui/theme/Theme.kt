package com.example.shopnow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ================= DARK (poți să-l ignori momentan) =================
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Accent,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

// ================= LIGHT – FOLOSIT =================
private val LightColorScheme = lightColorScheme(
    primary = Primary,            // #7653FF
    secondary = Accent,           // #FF6CAB
    background = Peach,           // #FFEBEB
    surface = Color.White,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,   // #1A1A1A
    onSurface = TextPrimary       // #1A1A1A
)

@Composable
fun ShopNowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
