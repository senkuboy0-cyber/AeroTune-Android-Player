package com.aerotune.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassmorphismBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xD9121225), Color(0xF20D0D1A))
            )
        )
    ) { content() }
}

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF0D0D1A), Color(0xFF050510))
            )
        )
    ) { content() }
}

@Composable
fun PlayerBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(300.dp).background(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x4000D4FF), Color(0x2000D4FF), Color.Transparent)
            )
        )
    )
}
