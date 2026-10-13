package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = VijayaGold,
    onPrimary = Color.Black,
    primaryContainer = VijayaGoldDark,
    onPrimaryContainer = Color.White,
    secondary = VijayaEmerald,
    onSecondary = Color.Black,
    secondaryContainer = VijayaEmeraldDark,
    onSecondaryContainer = Color.White,
    tertiary = VijayaCyan,
    onTertiary = Color.Black,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = ObsidianCardBorder,
    error = VijayaCrimson,
    onError = Color.White
)

private val LightColorScheme = darkColorScheme(
    // Vijaya is designed as an immersive dark esports gaming experience
    primary = VijayaGold,
    onPrimary = Color.Black,
    secondary = VijayaEmerald,
    tertiary = VijayaCyan,
    background = ObsidianBackground,
    surface = ObsidianSurface,
    surfaceVariant = ObsidianSurfaceVariant
)

@Composable
fun VijayaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
