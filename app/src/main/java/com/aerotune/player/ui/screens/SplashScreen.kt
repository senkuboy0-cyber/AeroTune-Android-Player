package com.aerotune.player.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerotune.player.ui.theme.CyberpunkBg
import com.aerotune.player.ui.theme.NeonCyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    val entranceAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val iconScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        onNavigateToMain()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberpunkBg),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(entranceAnim.value * iconScale)
                    .background(Color(0x1AFF1744), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = NeonCyan,
                    modifier = Modifier.alpha(entranceAnim.value),
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "AEROTUNE",
                color = NeonCyan,
                modifier = Modifier.alpha(entranceAnim.value),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                letterSpacing = 8.sp
            )
        }
    }
}
