package com.aerotune.player.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Cyberpunk Cyan
val CyberCyan = Color(0xFF00D4FF)
val DarkCyan = Color(0xFF0088AA)
// Deep Dark Theme
val DeepDark = Color(0xFF0A0A1A)
val DarkSurface = Color(0xFF121225)
val DarkCard = Color(0xFF1A1A2E)
val DarkTertiary = Color(0xFF252540)
// Text Colors
val OnSurfaceLight = Color(0xFFE3E3E8)
val OnSurfaceMuted = Color(0xFFB8B8C7)
// Error
val ErrorRed = Color(0xFFFF9991)

private val DarkColorScheme = darkColorScheme(
    primary = CyberCyan,
    onPrimary = Color.Black,
    primaryContainer = DarkCyan,
    onPrimaryContainer = OnSurfaceLight,
    secondary = Color(0xFFB4C7FF),
    onSecondary = Color(0xFF1F3067),
    secondaryContainer = Color(0xFF364781),
    onSecondaryContainer = OnSurfaceLight,
    tertiary = Color(0xFFDE9FF6),
    onTertiary = Color(0xFF4A2566),
    background = DeepDark,
    onBackground = OnSurfaceLight,
    surface = DarkSurface,
    onSurface = OnSurfaceLight,
    surfaceVariant = DarkCard,
    onSurfaceVariant = OnSurfaceMuted,
    error = ErrorRed,
    onError = Color.Black,
    outline = Color(0xFF45455F),
    outlineVariant = Color(0xFF252540)
)

@Composable
fun AeroTuneTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DeepDark.toArgb()
            window.navigationBarColor = DeepDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AeroTuneTypography,
        content = content
    )
}
