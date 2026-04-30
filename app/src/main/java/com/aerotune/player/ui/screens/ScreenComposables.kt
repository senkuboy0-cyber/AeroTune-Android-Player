package com.aerotune.player.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.aerotune.player.data.model.PlaybackState

@Composable
fun MainScreenContent(
    viewModel: MusicPlayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LibraryTab(
        tracks = uiState.tracks,
        isLoading = uiState.isLoading,
        onTrackClick = { viewModel.playTrack(it) },
        currentTrack = uiState.playbackState.currentTrack
    )
}

@Composable
fun NowPlayingTabContent(
    playbackState: PlaybackState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (Long) -> Unit
) {
    NowPlayingTab(
        playbackState = playbackState,
        onPlayPause = onPlayPause,
        onNext = onNext,
        onPrevious = onPrevious,
        onShuffle = onShuffle,
        onRepeat = onRepeat,
        onSeek = onSeek
    )
}
