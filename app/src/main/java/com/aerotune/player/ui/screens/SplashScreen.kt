package com.aerotune.player.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerotune.player.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (startAnimation) 1f else 0.5f, animationSpec = tween(800), label = "splash_scale")
    val alpha by animateFloatAsState(targetValue = if (startAnimation) 1f else 0f, animationSpec = tween(800), label = "splash_alpha")
    
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000)
        onNavigateToMain()
    }
    
    Box(
        modifier = Modifier.fillMaxSize().background(CyberpunkBg),
        contentAlignment = Alignment.Center
    ) {
        // Background glow
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            NeonCyan.copy(alpha = 0.3f),
                            NeonPink.copy(alpha = 0.2f),
                            android.graphics.Color.Transparent
                        )
                    )
                )
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha)
        ) {
            Text(
                text = "AERO",
                style = MaterialTheme.typography.displayLarge,
                color = NeonCyan,
                fontWeight = FontWeight.Bold,
                letterSpacing = 8.sp
            )
            Text(
                text = "TUNE",
                style = MaterialTheme.typography.displayLarge,
                color = NeonPink,
                fontWeight = FontWeight.Bold,
                letterSpacing = 8.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cyberpunk Music Player",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                letterSpacing = 2.sp
            )
        }
    }
}
