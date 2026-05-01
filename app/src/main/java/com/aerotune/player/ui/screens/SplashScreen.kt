package com.aerotune.player.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerotune.player.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    // Entrance animations
    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "logo_scale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "logo_alpha"
    )

    val textOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 50f,
        animationSpec = tween(durationMillis = 600, delayMillis = 300),
        label = "text_offset"
    )

    // Infinite pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Particle positions
    val particles = remember {
        listOf(
            Offset(0.1f, 0.2f),
            Offset(0.9f, 0.15f),
            Offset(0.15f, 0.8f),
            Offset(0.85f, 0.75f),
            Offset(0.5f, 0.1f),
            Offset(0.5f, 0.9f)
        )
    }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(3500)
        onNavigateToMain()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberpunkBg),
        contentAlignment = Alignment.Center
    ) {
        // Animated particles background
        Canvas(modifier = Modifier.fillMaxSize().blur(60.dp)) {
            particles.forEachIndexed { index, pos ->
                val color = when (index % 3) {
                    0 -> NeonCyan
                    1 -> NeonPink
                    else -> NeonPurple
                }
                drawCircle(
                    color = color.copy(alpha = glowAlpha * 0.3f),
                    radius = 40.dp.toPx(),
                    center = Offset(size.width * pos.x, size.height * pos.y)
                )
            }
        }

        // Rotating ring
        Box(
            modifier = Modifier
                .size(200.dp)
                .rotate(rotation)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(NeonCyan, NeonPink, NeonPurple, NeonCyan)
                    ),
                    radius = size.minDimension / 2,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                )
            }
        }

        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Glowing circle with pulse
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(logoScale * pulseScale),
                contentAlignment = Alignment.Center
            ) {
                // Glow effect
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    NeonCyan.copy(alpha = glowAlpha * 0.4f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Main icon circle
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            brush = Brush.linearGradient(GradientCyanPink),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = Color.White,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alpha(logoAlpha)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Animated text
            Text(
                text = "AEROTUNE",
                color = NeonCyan,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 10.sp,
                modifier = Modifier
                    .offset(y = textOffset.dp)
                    .alpha(logoAlpha)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "CYBERPUNK MUSIC PLAYER",
                color = TextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                modifier = Modifier.alpha(logoAlpha)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Loading indicator
            LoadingDots(alpha = logoAlpha)
        }

        // Version text at bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Version 1.0.0",
                color = TextGray.copy(alpha = 0.5f),
                fontSize = 10.sp,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
private fun LoadingDots(alpha: Float) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(3) { index ->
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = index * 200),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot_$index"
            )

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .scale(scale)
                    .background(NeonCyan.copy(alpha = alpha), CircleShape)
            )
        }
    }
}
