package com.aerotune.player.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color.Black,
    primaryContainer = NeonCyan.copy(alpha = 0.2f),
    onPrimaryContainer = NeonCyan,
    secondary = NeonPink,
    onSecondary = Color.White,
    secondaryContainer = NeonPink.copy(alpha = 0.2f),
    onSecondaryContainer = NeonPink,
    tertiary = NeonPurple,
    onTertiary = Color.White,
    tertiaryContainer = NeonPurple.copy(alpha = 0.2f),
    onTertiaryContainer = NeonPurple,
    background = CyberpunkBg,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceElevated,
    onSurfaceVariant = TextGray,
    error = Color(0xFFCF6679),
    onError = Color.Black,
    outline = GlassBorder,
    outlineVariant = GlassHighlight
)

@Composable
fun AeroTuneTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AeroTuneTypography,
        content = content
    )
}
