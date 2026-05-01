package com.aerotune.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aerotune.player.ui.screens.MainScreen
import com.aerotune.player.ui.screens.SplashScreen
import com.aerotune.player.ui.theme.AeroTuneTheme
import com.aerotune.player.ui.theme.CyberpunkBg
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AeroTuneTheme {
                var showSplash by remember { mutableStateOf(true) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    AnimatedContent(
                        targetState = showSplash,
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
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
                                onPrevious = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
