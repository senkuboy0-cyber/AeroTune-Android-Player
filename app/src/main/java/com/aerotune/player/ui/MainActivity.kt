package com.aerotune.player.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.aerotune.player.ui.screens.LibraryScreen
import com.aerotune.player.ui.screens.NowPlayingScreen
import com.aerotune.player.ui.theme.AeroTuneTheme
import com.aerotune.player.ui.theme.BackgroundPrimary

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.setPermissionGranted(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            AeroTuneTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundPrimary)
                ) {
                    val currentScreen by viewModel.currentScreen.collectAsState()

                    LaunchedEffect(Unit) {
                        checkAndRequestPermission()
                    }

                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        },
                        label = "screen_transition"
                    ) { screen ->
                        when (screen) {
                            Screen.LIBRARY -> LibraryScreen(
                                viewModel = viewModel,
                                onTrackClick = { index ->
                                    viewModel.playTrackAt(index)
                                    viewModel.navigateTo(Screen.NOW_PLAYING)
                                },
                                onNowPlayingClick = {
                                    viewModel.navigateTo(Screen.NOW_PLAYING)
                                }
                            )
                            Screen.NOW_PLAYING -> NowPlayingScreen(
                                viewModel = viewModel,
                                onBackClick = {
                                    viewModel.navigateTo(Screen.LIBRARY)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkAndRequestPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.setPermissionGranted(true)
            }
            shouldShowRequestPermissionRationale(permission) -> {
                viewModel.setPermissionGranted(false)
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}
