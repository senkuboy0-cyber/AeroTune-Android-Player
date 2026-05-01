package com.aerotune.player.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.ui.theme.*

@Composable
fun LibraryScreen(
    tracks: List<AudioTrack>,
    currentTrack: AudioTrack? = null,
    onTrackClick: (AudioTrack) -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(true) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberpunkBg)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "YOUR CYBERNETIC LIBRARY",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 48.dp, bottom = 20.dp, start = 4.dp)
        )

        AnimatedVisibility(visible = visible, enter = fadeIn()) {
            LazyColumn {
                items(tracks) { track ->
                    TrackListItem(
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
private fun TrackListItem(
    track: AudioTrack,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(GlassBg, RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = if (isPlaying) NeonCyan else GlassBorder,
                shape = RoundedCornerShape(16.dp)
            )
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
                        if (isPlaying) NeonCyan.copy(alpha = 0.1f) else NeonPink.copy(alpha = 0.1f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = if (isPlaying) NeonCyan else NeonPink,
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
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isPlaying) NeonCyan else TextWhite
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeonCyan
                )
            }
            Text(
                text = track.formatDuration(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
