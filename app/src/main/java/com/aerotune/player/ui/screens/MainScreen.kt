package com.aerotune.player.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.data.model.PlaybackState
import com.aerotune.player.data.model.RepeatMode
import com.aerotune.player.ui.theme.AeroTuneTypography

private val CyberCyan = Color(0xFF00D4FF)
private val DeepDark = Color(0xFF0A0A1A)
private val DarkSurface = Color(0xFF121225)
private val DarkCard = Color(0xFF1A1A2E)
private val OnSurfaceLight = Color(0xFFE3E3E8)
private val OnSurfaceMuted = Color(0xFFB8B8C7)

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DeepDark, Color(0xFF050510))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            TabRow(
                selectedTabIndex = uiState.currentTab,
                containerColor = Color.Transparent,
                contentColor = CyberCyan,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.currentTab]),
                        color = CyberCyan,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = uiState.currentTab == 0,
                    onClick = { viewModel.setCurrentTab(0) },
                    text = {
                        Text(
                            text = "LIBRARY",
                            fontWeight = if (uiState.currentTab == 0) FontWeight.Bold else FontWeight.Normal,
                            letterSpacing = 2.sp
                        )
                    },
                    selectedContentColor = CyberCyan,
                    unselectedContentColor = OnSurfaceMuted
                )
                Tab(
                    selected = uiState.currentTab == 1,
                    onClick = { viewModel.setCurrentTab(1) },
                    text = {
                        Text(
                            text = "NOW PLAYING",
                            fontWeight = if (uiState.currentTab == 1) FontWeight.Bold else FontWeight.Normal,
                            letterSpacing = 2.sp
                        )
                    },
                    selectedContentColor = CyberCyan,
                    unselectedContentColor = OnSurfaceMuted
                )
            }

            AnimatedContent(
                targetState = uiState.currentTab,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                },
                label = "tab_content"
            ) { tab ->
                when (tab) {
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

        uiState.playbackState.currentTrack?.let { _ ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                PlayerBottomBar(
                    playbackState = uiState.playbackState,
                    onPlayPause = { viewModel.togglePlayPause() },
                    onNext = { viewModel.nextTrack() },
                    onPrevious = { viewModel.previousTrack() }
                )
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = CyberCyan)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Scanning...", color = OnSurfaceMuted)
                }
            }
        }
        tracks.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No tracks found", color = OnSurfaceLight, style = AeroTuneTypography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Add music to your device", color = OnSurfaceMuted)
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(tracks) { _, track ->
                    TrackCard(
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
private fun TrackCard(
    track: AudioTrack,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) DarkCard.copy(alpha = 0.9f) else DarkSurface.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isPlaying) CyberCyan.copy(alpha = 0.2f) else DarkCard),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = track.albumArtUri,
                    contentDescription = track.album,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                if (isPlaying) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Playing",
                            tint = CyberCyan,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = AeroTuneTypography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = if (isPlaying) CyberCyan else OnSurfaceLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = track.artist,
                    style = AeroTuneTypography.bodySmall,
                    color = OnSurfaceMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = track.formatDuration(),
                style = AeroTuneTypography.labelMedium,
                color = OnSurfaceMuted
            )
        }
    }
}

@Composable
private fun NowPlayingTab(
    playbackState: PlaybackState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (Long) -> Unit
) {
    val track = playbackState.currentTrack

    if (track == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Nothing Playing", style = AeroTuneTypography.titleLarge, color = OnSurfaceLight)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Select a track", color = OnSurfaceMuted)
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Album art glow
        Box(
            modifier = Modifier
                .size(280.dp)
                .blur(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(CyberCyan.copy(alpha = 0.4f), Color.Transparent)
                    )
                )
        )
        // Album art
        Box(
            modifier = Modifier
                .size(260.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(DarkCard)
        ) {
            AsyncImage(
                model = track.albumArtUri,
                contentDescription = track.album,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(32.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = track.title,
            style = AeroTuneTypography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = OnSurfaceLight,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = track.artist,
            style = AeroTuneTypography.bodyMedium,
            color = OnSurfaceMuted,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(32.dp))
        var sliderPosition by remember(playbackState.currentPosition) {
            mutableFloatStateOf(
                playbackState.currentPosition.toFloat() / playbackState.duration.coerceAtLeast(1).toFloat()
            )
        }
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            onValueChangeFinished = { onSeek((sliderPosition * playbackState.duration).toLong()) },
            colors = SliderDefaults.colors(
                thumbColor = CyberCyan,
                activeTrackColor = CyberCyan,
                inactiveTrackColor = DarkCard
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(playbackState.currentPosition),
                style = AeroTuneTypography.labelSmall,
                color = OnSurfaceMuted
            )
            Text(
                text = formatTime(playbackState.duration),
                style = AeroTuneTypography.labelSmall,
                color = OnSurfaceMuted
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onShuffle) {
                Icon(
                    Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = if (playbackState.shuffleEnabled) CyberCyan else OnSurfaceMuted
                )
            }
            IconButton(onClick = onPrevious) {
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = OnSurfaceLight,
                    modifier = Modifier.size(40.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(CyberCyan, Color(0xFF0088AA))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onPlayPause,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        if (playbackState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (playbackState.isPlaying) "Pause" else "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            IconButton(onClick = onNext) {
                Icon(
                    Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = OnSurfaceLight,
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(onClick = onRepeat) {
                Icon(
                    if (playbackState.repeatMode == RepeatMode.ONE) Icons.Default.RepeatOne else Icons.Default.Repeat,
                    contentDescription = "Repeat",
                    tint = if (playbackState.repeatMode != RepeatMode.OFF) CyberCyan else OnSurfaceMuted
                )
            }
        }
    }
}

@Composable
private fun PlayerBottomBar(
    playbackState: PlaybackState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val track = playbackState.currentTrack ?: return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard.copy(alpha = 0.95f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = track.albumArtUri,
                contentDescription = track.album,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = AeroTuneTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = OnSurfaceLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = AeroTuneTypography.bodySmall,
                    color = OnSurfaceMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onPrevious) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", tint = OnSurfaceLight)
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(CyberCyan),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onPlayPause,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        if (playbackState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (playbackState.isPlaying) "Pause" else "Play",
                        tint = Color.Black
                    )
                }
            }
            IconButton(onClick = onNext) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = OnSurfaceLight)
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    if (millis <= 0) return "0:00"
    val minutes = millis / 1000 / 60
    val seconds = (millis / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}
