package com.aerotune.player.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.data.model.PlaybackState
import com.aerotune.player.data.model.RepeatMode
import com.aerotune.player.data.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MusicPlayerUiState(
    val tracks: List<AudioTrack> = emptyList(),
    val isLoading: Boolean = false,
    val currentTab: Int = 0,
    val hasPermission: Boolean = false,
    val playbackState: PlaybackState = PlaybackState()
)

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val audioRepository: AudioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MusicPlayerUiState())
    val uiState: StateFlow<MusicPlayerUiState> = _uiState.asStateFlow()

    fun loadTracks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val tracks = audioRepository.loadAudioFiles()
                _uiState.update { it.copy(tracks = tracks, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun setPermissionGranted(granted: Boolean) {
        _uiState.update { it.copy(hasPermission = granted) }
        if (granted) {
            loadTracks()
        }
    }

    fun setCurrentTab(tab: Int) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun playTrack(track: AudioTrack) {
        _uiState.update {
            it.copy(
                playbackState = it.playbackState.copy(
                    currentTrack = track,
                    isPlaying = true,
                    currentPosition = 0L,
                    duration = track.duration
                )
            )
        }
    }

    fun togglePlayPause() {
        _uiState.update {
            it.copy(
                playbackState = it.playbackState.copy(
                    isPlaying = !it.playbackState.isPlaying
                )
            )
        }
    }

    fun seekTo(position: Long) {
        _uiState.update {
            it.copy(
                playbackState = it.playbackState.copy(
                    currentPosition = position
                )
            )
        }
    }

    fun nextTrack() {
        val tracks = _uiState.value.tracks
        val currentTrack = _uiState.value.playbackState.currentTrack
        if (tracks.isEmpty() || currentTrack == null) return

        val currentIndex = tracks.indexOf(currentTrack)
        val nextIndex = if (currentIndex < tracks.size - 1) currentIndex + 1 else 0
        playTrack(tracks[nextIndex])
    }

    fun previousTrack() {
        val tracks = _uiState.value.tracks
        val currentTrack = _uiState.value.playbackState.currentTrack
        if (tracks.isEmpty() || currentTrack == null) return

        val currentIndex = tracks.indexOf(currentTrack)
        val prevIndex = if (currentIndex > 0) currentIndex - 1 else tracks.size - 1
        playTrack(tracks[prevIndex])
    }

    fun toggleShuffle() {
        _uiState.update {
            it.copy(
                playbackState = it.playbackState.copy(
                    shuffleEnabled = !it.playbackState.shuffleEnabled
                )
            )
        }
    }

    fun toggleRepeatMode() {
        _uiState.update {
            val newMode = when (it.playbackState.repeatMode) {
                RepeatMode.OFF -> RepeatMode.ALL
                RepeatMode.ALL -> RepeatMode.ONE
                RepeatMode.ONE -> RepeatMode.OFF
            }
            it.copy(
                playbackState = it.playbackState.copy(
                    repeatMode = newMode
                )
            )
        }
    }
}
