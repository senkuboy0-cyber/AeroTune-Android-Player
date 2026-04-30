package com.aerotune.player.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.data.repository.AudioRepository
import com.aerotune.player.player.AudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val audioRepository = AudioRepository(application.contentResolver)
    private val audioPlayer = AudioPlayer(application)

    private val _tracks = MutableStateFlow<List<AudioTrack>>(emptyList())
    val tracks: StateFlow<List<AudioTrack>> = _tracks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val currentTrack: StateFlow<AudioTrack?> = audioPlayer.currentTrack
    val isPlaying: StateFlow<Boolean> = audioPlayer.isPlaying
    val currentPosition: StateFlow<Long> = audioPlayer.currentPosition
    val currentIndex: StateFlow<Int> = audioPlayer.currentIndex

    private val _currentScreen = MutableStateFlow(Screen.LIBRARY)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _hasPermission = MutableStateFlow(false)
    val hasPermission: StateFlow<Boolean> = _hasPermission.asStateFlow()

    fun setPermissionGranted(granted: Boolean) {
        _hasPermission.value = granted
        if (granted) {
            loadTracks()
        }
    }

    fun loadTracks() {
        viewModelScope.launch {
            _isLoading.value = true
            val audioTracks = audioRepository.getAudioFiles()
            _tracks.value = audioTracks
            audioPlayer.setPlaylist(audioTracks)
            _isLoading.value = false
        }
    }

    fun playTrackAt(index: Int) {
        audioPlayer.playTrackAt(index)
    }

    fun togglePlayPause() {
        audioPlayer.togglePlayPause()
    }

    fun next() {
        audioPlayer.next()
    }

    fun previous() {
        audioPlayer.previous()
    }

    fun seekTo(position: Long) {
        audioPlayer.seekTo(position)
    }

    fun updatePosition() {
        audioPlayer.updatePosition()
    }

    fun getDuration(): Long {
        return audioPlayer.getDuration()
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}

enum class Screen {
    LIBRARY,
    NOW_PLAYING
}
