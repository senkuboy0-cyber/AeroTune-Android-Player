package com.aerotune.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.*
import com.aerotune.player.ui.screens.MainScreen
import com.aerotune.player.ui.screens.SplashScreen
import com.aerotune.player.ui.theme.AeroTuneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AeroTuneTheme {
                var showSplash by remember { mutableStateOf(true) }
                AnimatedContent(
                    targetState = showSplash,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(500)) togetherWith
                                fadeOut(animationSpec = tween(500))
                    },
                    label = "splash_transition"
                ) { isSplash ->
                    if (isSplash) {
                        SplashScreen(onNavigateToMain = { showSplash = false })
                    } else {
                        MainScreen(
                            tracks = emptyList(),
                            currentTrack = null,
                            isPlaying = false,
                            onTrackClick = {},
                            onPlayPause = {},
                            onNext = {},
                            onPrevious = {},
                            onSeek = {},
                            progress = 0f
                        )
                    }
                }
            }
        }
    }
}
