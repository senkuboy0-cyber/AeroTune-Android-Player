package com.aerotune.player.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.DecelerateEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerotune.player.ui.theme.AeroTuneTypography
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = DecelerateEasing),
        label = "alpha"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LaunchedEffect(Unit) {
        delay(300)
        showContent = true
        delay(200)
        startAnimation = true
        delay(2500)
        onNavigateToMain()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A1A),
                        Color(0xFF050510),
                        Color(0xFF020208)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated background glow
        AnimatedBackgroundGlow()

        Column(
            modifier = Modifier
                .scale(scale * (if (startAnimation) pulseScale else 1f))
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            AeroTuneLogo(
                modifier = Modifier.size(150.dp),
                glowIntensity = pulseScale
            )

            Spacer(modifier = Modifier.height(32.dp))

            // App Name
            Text(
                text = "AEROTUNE",
                style = AeroTuneTypography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 8.sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Premium Music Experience",
                style = AeroTuneTypography.bodyMedium,
                color = Color(0xFF00D4FF).copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun AeroTuneLogo(
    modifier: Modifier = Modifier,
    glowIntensity: Float = 1f
) {
    val primaryCyan = Color(0xFF00D4FF)
    val darkBg = Color(0xFF1A1A2E)

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val outerRadius = size.minDimension / 2 * 0.9f
        val innerRadius = outerRadius * 0.75f
        val triangleSize = outerRadius * 0.5f

        // Outer glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    primaryCyan.copy(alpha = 0.3f * glowIntensity),
                    primaryCyan.copy(alpha = 0.1f * glowIntensity),
                    Color.Transparent
                ),
                center = Offset(centerX, centerY),
                radius = outerRadius * 1.3f
            )
        )

        // Outer ring
        drawCircle(
            color = primaryCyan,
            radius = outerRadius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 4.dp.toPx())
        )

        // Inner circle
        drawCircle(
            color = primaryCyan.copy(alpha = 0.3f),
            radius = innerRadius,
            center = Offset(centerX, centerY)
        )

        // Play triangle
        val trianglePath = androidx.compose.ui.graphics.Path().apply {
            moveTo(centerX - triangleSize * 0.4f, centerY - triangleSize * 0.6f)
            lineTo(centerX - triangleSize * 0.4f, centerY + triangleSize * 0.6f)
            lineTo(centerX + triangleSize * 0.5f, centerY)
            close()
        }
        drawPath(
            path = trianglePath,
            color = primaryCyan
        )
    }
}

@Composable
fun AnimatedBackgroundGlow() {
    val infiniteTransition = rememberInfiniteTransition(label = "bgGlow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val primaryCyan = Color(0xFF00D4FF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top left glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryCyan.copy(alpha = glowAlpha * 0.3f),
                        Color.Transparent
                    ),
                    center = Offset(0f, 0f),
                    radius = size.width * 0.5f
                )
            )

            // Bottom right glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryCyan.copy(alpha = glowAlpha * 0.2f),
                        Color.Transparent
                    ),
                    center = Offset(size.width, size.height),
                    radius = size.width * 0.6f
                )
            )
        }
    }
}
