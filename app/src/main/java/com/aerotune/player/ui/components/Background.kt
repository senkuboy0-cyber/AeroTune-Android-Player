package com.aerotune.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aerotune.player.ui.theme.AeroTuneColorScheme

@Composable
fun GlassmorphismBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AeroTuneColorScheme.surface.copy(alpha = 0.85f),
                        AeroTuneColorScheme.background.copy(alpha = 0.95f)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF0D0D1A),
                        Color(0xFF050510)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun PlayerBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(300.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x4000D4FF),
                        Color(0x2000D4FF),
                        Color(0x0000D4FF)
                    )
                )
            )
    )
}
