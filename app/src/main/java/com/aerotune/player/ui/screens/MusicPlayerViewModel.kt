package com.aerotune.player.ui.screens

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.data.model.PlaybackState
import com.aerotune.player.data.model.RepeatMode
import com.aerotune.player.data.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
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
    private val audioRepository: AudioRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _uiState = MutableStateFlow(MusicPlayerUiState())
    val uiState: StateFlow<MusicPlayerUiState> = _uiState.asStateFlow()

    private var player: ExoPlayer? = null
    private var positionUpdateJob: Job? = null

    init {
        initializePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(context).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> {
                            _uiState.update {
                                it.copy(
                                    playbackState = it.playbackState.copy(
                                        duration = player?.duration ?: 0L
                                    )
                                )
                            }
                        }
                        Player.STATE_ENDED -> {
                            nextTrack()
                        }
                        else -> {}
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _uiState.update {
                        it.copy(
                            playbackState = it.playbackState.copy(isPlaying = isPlaying)
                        )
                    }
                    if (isPlaying) {
                        startPositionUpdates()
                    } else {
                        stopPositionUpdates()
                    }
                }
            })
        }
    }

    private fun startPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = viewModelScope.launch {
            while (isActive) {
                player?.let { p ->
                    _uiState.update {
                        it.copy(
                            playbackState = it.playbackState.copy(
                                currentPosition = p.currentPosition,
                                duration = p.duration.takeIf { d -> d > 0 } ?: it.playbackState.duration
                            )
                        )
                    }
                }
                delay(1000)
            }
        }
    }

    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

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
        player?.let { p ->
            val mediaItem = MediaItem.fromUri(track.uri)
            p.setMediaItem(mediaItem)
            p.prepare()
            p.play()

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
    }

    fun togglePlayPause() {
        player?.let { p ->
            if (p.isPlaying) {
                p.pause()
            } else {
                p.play()
            }
        }
    }

    fun seekTo(position: Long) {
        player?.seekTo(position)
        _uiState.update {
            it.copy(
                playbackState = it.playbackState.copy(currentPosition = position)
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
                playbackState = it.playbackState.copy(repeatMode = newMode)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopPositionUpdates()
        player?.release()
        player = null
    }
}
