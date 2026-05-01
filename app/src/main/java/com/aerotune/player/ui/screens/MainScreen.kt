package com.aerotune.player.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.ui.theme.*

@Composable
fun MainScreen(
    tracks: List<AudioTrack> = emptyList(),
    currentTrack: AudioTrack? = null,
    isPlaying: Boolean = false,
    onTrackClick: (AudioTrack) -> Unit = {},
    onPlayPause: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {}
) {
    var isFullPlayerVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(CyberpunkBg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "YOUR CYBERNETIC LIBRARY",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 48.dp, bottom = 16.dp)
            )
            
            if (tracks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No tracks found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextGray
                    )
                }
            } else {
                tracks.forEach { track ->
                    TrackCard(
                        track = track,
                        isCurrentTrack = track == currentTrack,
                        onClick = {
                            onTrackClick(track)
                            isFullPlayerVisible = true
                        }
                    )
                }
            }
        }

        // Bottom Player Bar
        currentTrack?.let { track ->
            if (!isFullPlayerVisible) {
                AnimatedVisibility(
                    visible = !isFullPlayerVisible,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    BottomPlayerBar(
                        track = track,
                        isPlaying = isPlaying,
                        onPlayerClick = { isFullPlayerVisible = true },
                        onPlayPause = onPlayPause
                    )
                }
            }
        }

        // Full Player Overlay
        currentTrack?.let { track ->
            AnimatedVisibility(
                visible = isFullPlayerVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                FullPlayerScreen(
                    track = track,
                    isPlaying = isPlaying,
                    onBackClick = { isFullPlayerVisible = false },
                    onPlayPause = onPlayPause,
                    onNext = onNext,
                    onPrevious = onPrevious
                )
            }
        }
    }
}

@Composable
fun TrackCard(
    track: AudioTrack,
    isCurrentTrack: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(GlassBg, RoundedCornerShape(16.dp))
            .border(1.dp, if (isCurrentTrack) NeonCyan else GlassBorder, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        if (isCurrentTrack) NeonCyan.copy(alpha = 0.2f)
                        else NeonPink.copy(alpha = 0.1f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = if (isCurrentTrack) NeonCyan else NeonPink,
                    modifier = Modifier.size(30.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isCurrentTrack) NeonCyan else TextWhite
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextGray
                )
            }
            Text(
                text = track.formatDuration(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun BottomPlayerBar(
    track: AudioTrack,
    isPlaying: Boolean,
    onPlayerClick: () -> Unit,
    onPlayPause: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(GlassBg, RoundedCornerShape(24.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onPlayerClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(NeonPink.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = NeonCyan,
                modifier = Modifier.size(30.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = track.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = track.artist,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        IconButton(onClick = onPlayPause) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = NeonCyan,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun FullPlayerScreen(
    track: AudioTrack,
    isPlaying: Boolean,
    onBackClick: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val albumArtScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberpunkBg)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextWhite
                )
            }
            Text(
                text = "NOW PLAYING",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
                .scale(if (isPlaying) albumArtScale else 1f)
                .background(GlassBg, RoundedCornerShape(32.dp))
                .border(2.dp, GlassBorder, RoundedCornerShape(32.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = NeonCyan.copy(alpha = 0.5f),
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = track.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = track.artist,
            style = MaterialTheme.typography.titleMedium,
            color = NeonCyan,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Slider(
            value = 0.5f,
            onValueChange = {},
            colors = SliderDefaults.colors(
                thumbColor = NeonCyan,
                activeTrackColor = NeonCyan,
                inactiveTrackColor = GlassBorder
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = TextWhite,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(NeonPink, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onPlayPause,
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = TextWhite,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            IconButton(
                onClick = onNext,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = TextWhite,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
