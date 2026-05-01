package com.aerotune.player

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aerotune.player.ui.MusicViewModel
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
                val viewModel: MusicViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsState()

                // Request permission on start
                LaunchedEffect(Unit) {
                    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        arrayOf(
                            Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    } else {
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                    // For now, auto-request permission
                    viewModel.setPermissionGranted(true)
                }

                AnimatedContent(
                    targetState = showSplash,
                    transitionSpec = {
                        fadeIn(animationSpec = androidx.compose.animation.core.tween(500)) togetherWith
                                fadeOut(animationSpec = androidx.compose.animation.core.tween(500))
                    },
                    label = "splash_transition"
                ) { isSplash ->
                    if (isSplash) {
                        SplashScreen(onNavigateToMain = { showSplash = false })
                    } else {
                        MainScreen(
                            tracks = uiState.tracks,
                            currentTrack = uiState.currentTrack,
                            isPlaying = uiState.isPlaying,
                            isLoading = uiState.isLoading,
                            hasPermission = uiState.hasPermission,
                            onTrackClick = { track -> viewModel.playTrack(track) },
                            onPlayPause = { viewModel.togglePlayPause() },
                            onNext = { viewModel.nextTrack() },
                            onPrevious = { viewModel.previousTrack() },
                            onSeek = { progress -> viewModel.seekTo(progress) },
                            onPermissionRequest = { viewModel.setPermissionGranted(true) },
                            progress = uiState.progress
                        )
                    }
                }
            }
        }
    }
}
