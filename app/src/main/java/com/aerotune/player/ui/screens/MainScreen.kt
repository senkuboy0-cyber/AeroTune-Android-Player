package com.aerotune.player.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.data.model.PlaybackState
import com.aerotune.player.data.model.RepeatMode
import com.aerotune.player.ui.components.PlayerBackground
import com.aerotune.player.ui.theme.AeroTuneColorScheme
import com.aerotune.player.ui.components.icons.RepeatIcon
import com.aerotune.player.ui.components.icons.RepeatOneIcon
import com.aerotune.player.ui.components.icons.ShuffleIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MusicPlayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.POST_NOTIFICATIONS)
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        viewModel.setPermissionGranted(allGranted)
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissions)
    }

    val gradientColors = listOf(
        AeroTuneColorScheme.background,
        Color(0xFF0A0A15),
        Color(0xFF050510)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                PlayerBottomBar(
                    playbackState = uiState.playbackState,
                    onPlayPause = { viewModel.togglePlayPause() },
                    onNext = { viewModel.nextTrack() },
                    onPrevious = { viewModel.previousTrack() }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tab Row
                TabRow(
                    selectedTabIndex = uiState.currentTab,
                    containerColor = Color.Transparent,
                    contentColor = AeroTuneColorScheme.primary,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.currentTab]),
                            color = AeroTuneColorScheme.primary
                        )
                    }
                ) {
                    Tab(
                        selected = uiState.currentTab == 0,
                        onClick = { viewModel.setCurrentTab(0) },
                        text = { Text("Library") }
                    )
                    Tab(
                        selected = uiState.currentTab == 1,
                        onClick = { viewModel.setCurrentTab(1) },
                        text = { Text("Now Playing") }
                    )
                }

                when (uiState.currentTab) {
                    0 -> LibraryTab(
                        tracks = uiState.tracks,
                        isLoading = uiState.isLoading,
                        onTrackClick = { viewModel.playTrack(it) },
                        currentTrack = uiState.playbackState.currentTrack
                    )
                    1 -> NowPlayingTab(
                        playbackState = uiState.playbackState,
                        onPlayPause = { viewModel.togglePlayPause() },
                        onNext = { viewModel.nextTrack() },
                        onPrevious = { viewModel.previousTrack() },
                        onShuffle = { viewModel.toggleShuffle() },
                        onRepeat = { viewModel.toggleRepeatMode() },
                        onSeek = { viewModel.seekTo(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LibraryTab(
    tracks: List<AudioTrack>,
    isLoading: Boolean,
    onTrackClick: (AudioTrack) -> Unit,
    currentTrack: AudioTrack?
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = AeroTuneColorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Scanning...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AeroTuneColorScheme.onSurfaceVariant
                    )
                }
            }
        }
        tracks.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No audio files found",
                        style = MaterialTheme.typography.titleMedium,
                        color = AeroTuneColorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap to scan for music",
                        style = MaterialTheme.typography.bodySmall,
                        color = AeroTuneColorScheme.onSurfaceVariant
                    )
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(tracks) { index, track ->
                    TrackItem(
                        track = track,
                        isPlaying = track == currentTrack,
                        onClick = { onTrackClick(track) }
                    )
                }
            }
        }
    }
}

@Composable
fun TrackItem(
    track: AudioTrack,
    isPlaying: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying)
                AeroTuneColorScheme.primaryContainer.copy(alpha = 0.3f)
            else
                AeroTuneColorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album Art
            AsyncImage(
                model = track.albumArtUri,
                contentDescription = track.album,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isPlaying)
                        AeroTuneColorScheme.primary
                    else
                        AeroTuneColorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = AeroTuneColorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = track.formatDuration(),
                style = MaterialTheme.typography.labelMedium,
                color = AeroTuneColorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun NowPlayingTab(
    playbackState: PlaybackState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (Long) -> Unit
) {
    val track = playbackState.currentTrack

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (track == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No track playing",
                    style = MaterialTheme.typography.titleMedium,
                    color = AeroTuneColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Select a track from the library",
                    style = MaterialTheme.typography.bodySmall,
                    color = AeroTuneColorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Album Art with glow effect
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .blur(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PlayerBackground()
                }

                AsyncImage(
                    model = track.albumArtUri,
                    contentDescription = track.album,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            AeroTuneColorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Track Info
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AeroTuneColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AeroTuneColorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Progress Slider
                Slider(
                    value = if (playbackState.duration > 0)
                        playbackState.currentPosition.toFloat() / playbackState.duration
                    else 0f,
                    onValueChange = { fraction ->
                        onSeek((fraction * playbackState.duration).toLong())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = AeroTuneColorScheme.primary,
                        activeTrackColor = AeroTuneColorScheme.primary,
                        inactiveTrackColor = AeroTuneColorScheme.surfaceVariant
                    )
                )

                // Time Labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(playbackState.currentPosition),
                        style = MaterialTheme.typography.labelSmall,
                        color = AeroTuneColorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatTime(playbackState.duration),
                        style = MaterialTheme.typography.labelSmall,
                        color = AeroTuneColorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Playback Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Shuffle
                    IconButton(onClick = onShuffle) {
                        Icon(
                            imageVector = ShuffleIcon,
                            contentDescription = "Shuffle",
                            tint = if (playbackState.shuffleEnabled)
                                AeroTuneColorScheme.primary
                            else
                                AeroTuneColorScheme.onSurfaceVariant
                        )
                    }

                    // Previous
                    IconButton(onClick = onPrevious) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous",
                            tint = AeroTuneColorScheme.onSurface,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Play/Pause
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(AeroTuneColorScheme.primary)
                            .clickable(onClick = onPlayPause),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (playbackState.isPlaying)
                                Icons.Default.Pause
                            else
                                Icons.Default.PlayArrow,
                            contentDescription = if (playbackState.isPlaying) "Pause" else "Play",
                            tint = AeroTuneColorScheme.onPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Next
                    IconButton(onClick = onNext) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next",
                            tint = AeroTuneColorScheme.onSurface,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Repeat
                    IconButton(onClick = onRepeat) {
                        Icon(
                            imageVector = when (playbackState.repeatMode) {
                                RepeatMode.ONE -> RepeatOneIcon
                                else -> RepeatIcon
                            },
                            contentDescription = "Repeat",
                            tint = if (playbackState.repeatMode != RepeatMode.OFF)
                                AeroTuneColorScheme.primary
                            else
                                AeroTuneColorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerBottomBar(
    playbackState: PlaybackState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val track = playbackState.currentTrack ?: return

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AeroTuneColorScheme.surfaceVariant.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mini Album Art
            AsyncImage(
                model = track.albumArtUri,
                contentDescription = track.album,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AeroTuneColorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = AeroTuneColorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Previous
            IconButton(onClick = onPrevious) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = AeroTuneColorScheme.onSurface
                )
            }

            // Play/Pause
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AeroTuneColorScheme.primary)
                    .clickable(onClick = onPlayPause),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (playbackState.isPlaying)
                        Icons.Default.Pause
                    else
                        Icons.Default.PlayArrow,
                    contentDescription = if (playbackState.isPlaying) "Pause" else "Play",
                    tint = AeroTuneColorScheme.onPrimary
                )
            }

            // Next
            IconButton(onClick = onNext) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = AeroTuneColorScheme.onSurface
                )
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    val minutes = millis / 1000 / 60
    val seconds = (millis / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}
