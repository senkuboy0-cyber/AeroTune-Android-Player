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
val Primary = Color(0xFF00D4FF)           // Cyan accent
val OnPrimary = Color(0xFF000000)
val PrimaryVariant = Color(0xFF003544)
val OnPrimaryContainer = Color(0xFF97EAFE)

val Secondary = Color(0xFFB4C7FF)
val OnSecondary = Color(0xFF1F3067)
val SecondaryContainer = Color(0xFF364781)
val OnSecondaryContainer = Color(0xFFDDE2FF)

val Tertiary = Color(0xFFDE9FF6)
val OnTertiary = Color(0xFF4A2566)
val TertiaryContainer = Color(0xFF63387D)
val OnTertiaryContainer = Color(0xFFF5DAFF)

val BackgroundPrimary = Color(0xFF0D0D1A)   // Deep dark blue-black
val OnBackground = Color(0xFFE3E3E8)
val BackgroundSecondary = Color(0xFF121225)  // Slightly lighter
val OnSurface = Color(0xFFE3E3E8)
val BackgroundTertiary = Color(0xFF1F1F3A)
val OnSurfaceVariant = Color(0xFFB8B8C7)

val Error = Color(0xFFFF9991)
val OnError = Color(0xFF680009)
val ErrorContainer = Color(0xFF93000C)
val OnErrorContainer = Color(0xFFFFDAD4)

val Outline = Color(0xFF45455F)
val OutlineVariant = Color(0xFF252540)

val InverseSurface = Color(0xFFE3E3E8)
val InverseOnSurface = Color(0xFF1A1A2E)
val InversePrimary = Color(0xFF1A5060)

val SurfaceTint = Color(0xFF00D4FF)
val Scrim = Color(0xFF000000)

val AeroTuneColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = BackgroundPrimary,
    onBackground = OnBackground,
    surface = BackgroundSecondary,
    onSurface = OnSurface,
    surfaceVariant = BackgroundTertiary,
    onSurfaceVariant = OnSurfaceVariant,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    outline = Outline,
    outlineVariant = OutlineVariant,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    inversePrimary = InversePrimary,
    surfaceTint = SurfaceTint,
    scrim = Scrim
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
