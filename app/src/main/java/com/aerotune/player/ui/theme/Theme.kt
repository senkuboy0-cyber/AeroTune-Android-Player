package com.aerotune.player.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Cyberpunk Theme Colors
val NeonCyan = Color(0xFF00D4FF)
val NeonPink = Color(0xFFFF0080)
val NeonPurple = Color(0xFF9D00FF)
val CyberpunkBg = Color(0xFF0D0D1A)
val SurfaceDark = Color(0xFF1A1A2E)
val GlassBg = Color(0xFF252540)
val GlassBorder = Color(0xFF3D3D5C)
val TextWhite = Color(0xFFE3E3E8)
val TextGray = Color(0xFF8B8BA3)

val GradientCyanPink = listOf(NeonCyan, NeonPink)
val GradientPurpleCyan = listOf(NeonPurple, NeonCyan)

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color.Black,
    primaryContainer = NeonCyan.copy(alpha = 0.2f),
    onPrimaryContainer = NeonCyan,
    secondary = NeonPink,
    onSecondary = Color.Black,
    background = CyberpunkBg,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = GlassBg,
    onSurfaceVariant = TextGray
)

@Composable
fun AeroTuneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
