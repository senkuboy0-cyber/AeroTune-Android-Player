package com.aerotune.player.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = CyberpunkBg,
    surface = CyberpunkBg,
    primary = NeonCyan,
    secondary = NeonPink,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun AeroTuneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AeroTuneTypography,
        content = content
    )
}
