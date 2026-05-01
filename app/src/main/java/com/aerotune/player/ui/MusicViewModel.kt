package com.aerotune.player.ui

import android.app.Application
import android.content.ContentResolver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.data.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MusicUiState(
    val tracks: List<AudioTrack> = emptyList(),
    val currentTrack: AudioTrack? = null,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val hasPermission: Boolean = false,
    val currentIndex: Int = -1,
    val progress: Float = 0f
)

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    private val application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MusicUiState())
    val uiState: StateFlow<MusicUiState> = _uiState.asStateFlow()

    private val contentResolver: ContentResolver
        get() = application.contentResolver

    fun setPermissionGranted(granted: Boolean) {
        _uiState.update { it.copy(hasPermission = granted) }
        if (granted) {
            loadTracks()
        }
    }

    fun loadTracks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val tracks = audioRepository.loadAudioFiles()
                _uiState.update { 
                    it.copy(
                        tracks = tracks,
                        isLoading = false
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun playTrack(track: AudioTrack) {
        val index = _uiState.value.tracks.indexOf(track)
        _uiState.update {
            it.copy(
                currentTrack = track,
                isPlaying = true,
                currentIndex = index,
                progress = 0f
            )
        }
    }

    fun togglePlayPause() {
        _uiState.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun nextTrack() {
        val tracks = _uiState.value.tracks
        val currentIndex = _uiState.value.currentIndex
        if (tracks.isEmpty()) return
        
        val nextIndex = if (currentIndex < tracks.size - 1) currentIndex + 1 else 0
        val nextTrack = tracks[nextIndex]
        playTrack(nextTrack)
    }

    fun previousTrack() {
        val tracks = _uiState.value.tracks
        val currentIndex = _uiState.value.currentIndex
        if (tracks.isEmpty()) return
        
        val prevIndex = if (currentIndex > 0) currentIndex - 1 else tracks.size - 1
        val prevTrack = tracks[prevIndex]
        playTrack(prevTrack)
    }

    fun seekTo(progress: Float) {
        _uiState.update { it.copy(progress = progress) }
    }
}
