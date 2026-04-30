package com.aerotune.player.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// AeroTune Ultra-Premium Dark Color Palette
private val AeroTuneDarkPrimary = Color(0xFF00D4FF)          // Cyan accent
private val AeroTuneDarkOnPrimary = Color(0xFF000000)
private val AeroTuneDarkPrimaryContainer = Color(0xFF003544)
private val AeroTuneDarkOnPrimaryContainer = Color(0xFF97EAFE)

private val AeroTuneDarkSecondary = Color(0xFFB4C7FF)
private val AeroTuneDarkOnSecondary = Color(0xFF1F3067)
private val AeroTuneDarkSecondaryContainer = Color(0xFF364781)
private val AeroTuneDarkOnSecondaryContainer = Color(0xFFDDE2FF)

private val AeroTuneDarkTertiary = Color(0xFFDE9FF6)
private val AeroTuneDarkOnTertiary = Color(0xFF4A2566)
private val AeroTuneDarkTertiaryContainer = Color(0xFF63387D)
private val AeroTuneDarkOnTertiaryContainer = Color(0xFFF5DAFF)

private val AeroTuneDarkBackground = Color(0xFF0D0D1A)       // Deep dark blue-black
private val AeroTuneDarkOnBackground = Color(0xFFE3E3E8)
private val AeroTuneDarkSurface = Color(0xFF121225)        // Slightly lighter
private val AeroTuneDarkOnSurface = Color(0xFFE3E3E8)
private val AeroTuneDarkSurfaceVariant = Color(0xFF1F1F3A)
private val AeroTuneDarkOnSurfaceVariant = Color(0xFFB8B8C7)

private val AeroTuneDarkError = Color(0xFFFF9991)
private val AeroTuneDarkOnError = Color(0xFF680009)
private val AeroTuneDarkErrorContainer = Color(0xFF93000C)
private val AeroTuneDarkOnErrorContainer = Color(0xFFFFDAD4)

private val AeroTuneDarkOutline = Color(0xFF45455F)
private val AeroTuneDarkOutlineVariant = Color(0xFF252540)

private val AeroTuneDarkInverseSurface = Color(0xFFE3E3E8)
private val AeroTuneDarkInverseOnSurface = Color(0xFF1A1A2E)
private val AeroTuneDarkInversePrimary = Color(0xFF1A5060)

private val AeroTuneDarkSurfaceTint = Color(0xFF00D4FF)
private val AeroTuneDarkScrim = Color(0xFF000000)

private val AeroTuneColorScheme = darkColorScheme(
    primary = AeroTuneDarkPrimary,
    onPrimary = AeroTuneDarkOnPrimary,
    primaryContainer = AeroTuneDarkPrimaryContainer,
    onPrimaryContainer = AeroTuneDarkOnPrimaryContainer,
    secondary = AeroTuneDarkSecondary,
    onSecondary = AeroTuneDarkOnSecondary,
    secondaryContainer = AeroTuneDarkSecondaryContainer,
    onSecondaryContainer = AeroTuneDarkOnSecondaryContainer,
    tertiary = AeroTuneDarkTertiary,
    onTertiary = AeroTuneDarkOnTertiary,
    tertiaryContainer = AeroTuneDarkTertiaryContainer,
    onTertiaryContainer = AeroTuneDarkOnTertiaryContainer,
    background = AeroTuneDarkBackground,
    onBackground = AeroTuneDarkOnBackground,
    surface = AeroTuneDarkSurface,
    onSurface = AeroTuneDarkOnSurface,
    surfaceVariant = AeroTuneDarkSurfaceVariant,
    onSurfaceVariant = AeroTuneDarkOnSurfaceVariant,
    error = AeroTuneDarkError,
    onError = AeroTuneDarkOnError,
    errorContainer = AeroTuneDarkErrorContainer,
    onErrorContainer = AeroTuneDarkOnErrorContainer,
    outline = AeroTuneDarkOutline,
    outlineVariant = AeroTuneDarkOutlineVariant,
    inverseSurface = AeroTuneDarkInverseSurface,
    inverseOnSurface = AeroTuneDarkInverseOnSurface,
    inversePrimary = AeroTuneDarkInversePrimary,
    surfaceTint = AeroTuneDarkSurfaceTint,
    scrim = AeroTuneDarkScrim
)

@Composable
fun AeroTuneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = AeroTuneColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AeroTuneTypography,
        content = content
    )
}
