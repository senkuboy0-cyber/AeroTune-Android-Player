package com.aerotune.player.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerotune.player.ui.theme.AeroTuneTypography
import kotlinx.coroutines.delay

private val CyberCyan = Color(0xFF00D4FF)
private val DeepDark = Color(0xFF0A0A1A)
private val DarkBg = Color(0xFF050510)

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }
    
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "scale"
    )
    
    val alpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "alpha"
    )
    
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
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
                    colors = listOf(DeepDark, DarkBg, Color(0xFF020208))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        SplashBackgroundGlow()
        Column(
            modifier = Modifier
                .scale(scale * pulseScale)
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SplashLogo()
            Spacer(modifier = Modifier.height(32.dp))
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
                color = CyberCyan.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun SplashLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "logoPulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )
    Canvas(modifier = Modifier.size(150.dp)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val outerRadius = size.minDimension / 2 * 0.85f
        val innerRadius = outerRadius * 0.7f
        
        // Outer glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CyberCyan.copy(alpha = glowAlpha),
                    CyberCyan.copy(alpha = 0.1f),
                    Color.Transparent
                ),
                center = Offset(centerX, centerY),
                radius = outerRadius * 1.4f
            )
        )
        
        // Outer ring
        drawCircle(
            color = CyberCyan,
            radius = outerRadius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 3.dp.toPx())
        )
        
        // Inner glow
        drawCircle(
            color = CyberCyan.copy(alpha = 0.15f),
            radius = innerRadius,
            center = Offset(centerX, centerY)
        )
        
        // Play triangle
        val trianglePath = Path().apply {
            val triangleSize = outerRadius * 0.45f
            moveTo(centerX - triangleSize * 0.3f, centerY - triangleSize * 0.6f)
            lineTo(centerX - triangleSize * 0.3f, centerY + triangleSize * 0.6f)
            lineTo(centerX + triangleSize * 0.55f, centerY)
            close()
        }
        drawPath(path = trianglePath, color = CyberCyan)
    }
}

@Composable
private fun SplashBackgroundGlow() {
    val infiniteTransition = rememberInfiniteTransition(label = "bgGlow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgGlowAlpha"
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Top-left glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CyberCyan.copy(alpha = glowAlpha * 0.25f),
                    Color.Transparent
                ),
                center = Offset(0f, 0f),
                radius = size.width * 0.6f
            )
        )
        // Bottom-right glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CyberCyan.copy(alpha = glowAlpha * 0.15f),
                    Color.Transparent
                ),
                center = Offset(size.width, size.height),
                radius = size.width * 0.5f
            )
        )
    }
}
