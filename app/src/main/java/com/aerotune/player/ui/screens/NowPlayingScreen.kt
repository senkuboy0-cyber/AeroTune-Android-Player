package com.aerotune.player.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aerotune.player.data.model.AudioTrack
import com.aerotune.player.ui.MainViewModel
import com.aerotune.player.ui.theme.BackgroundPrimary
import com.aerotune.player.ui.theme.BackgroundSecondary
import com.aerotune.player.ui.theme.BackgroundTertiary
import com.aerotune.player.ui.theme.GradientEnd
import com.aerotune.player.ui.theme.GradientStart
import com.aerotune.player.ui.theme.OnSurface
import com.aerotune.player.ui.theme.OnSurfaceVariant
import com.aerotune.player.ui.theme.Primary
import com.aerotune.player.ui.theme.SurfaceGlass
import com.aerotune.player.ui.theme.SurfaceGlassDark
import kotlinx.coroutines.delay

@Composable
fun NowPlayingScreen(
    viewModel: MainViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val currentTrack by viewModel.currentTrack.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()

    var sliderPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }

    LaunchedEffect(isPlaying) {
        while (true) {
            viewModel.updatePosition()
            sliderPosition = viewModel.currentPosition.value
            duration = viewModel.getDuration()
            delay(500)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        currentTrack?.let { track ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                NowPlayingHeader(onBackClick = onBackClick)

                Spacer(modifier = Modifier.height(48.dp))

                AlbumArt(
                    track = track,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                TrackInfo(
                    track = track,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))


                ProgressBar(
                    currentPosition = sliderPosition,
                    duration = duration,
                    onSeek = { viewModel.seekTo(it) },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(24.dp))


                PlaybackControls(
                    isPlaying = isPlaying,
                    onPrevious = { viewModel.previous() },
                    onPlayPause = { viewModel.togglePlayPause() },
                    onNext = { viewModel.next() }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun NowPlayingHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = onBackClick) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(BackgroundSecondary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                BackIcon()
            }
        }
    }
}

@Composable
private fun AlbumArt(track: AudioTrack, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundSecondary)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (track.albumArtUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(track.albumArtUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = track.album,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(40.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                BackgroundPrimary.copy(alpha = 0.8f)
                            )
                        )
                    )
            )


            Box(
                modifier = Modifier
                    .fillMaxSize(0.85f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(BackgroundTertiary),
                contentAlignment = Alignment.Center
            ) {
                if (track.albumArtUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(track.albumArtUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = track.album,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    MusicNoteIcon(
                        modifier = Modifier.size(80.dp),
                        tint = OnSurfaceVariant
                    )
                }
            }
        }
    }
}


@Composable
private fun TrackInfo(track: AudioTrack, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = track.title,
            style = MaterialTheme.typography.headlineSmall,
            color = OnSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${track.artist} - ${track.album}",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
private fun ProgressBar(
    currentPosition: Long,
    duration: Long,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Slider(
            value = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
            onValueChange = { value ->
                onSeek((value * duration).toLong())
            },
            colors = SliderDefaults.colors(
                thumbColor = Primary,
                activeTrackColor = Primary,
                inactiveTrackColor = BackgroundTertiary
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDuration(currentPosition),
                style = MaterialTheme.typography.labelSmall,
                color = OnSurfaceVariant
            )
            Text(
                text = formatDuration(duration),
                style = MaterialTheme.typography.labelSmall,
                color = OnSurfaceVariant
            )
        }
    }
}

@Composable
private fun PlaybackControls(
    isPlaying: Boolean,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPrevious,
            modifier = Modifier.size(56.dp)
        ) {
            PreviousIcon()
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    ),
                    CircleShape
                )
                .clickable(onClick = onPlayPause),
            contentAlignment = Alignment.Center
        ) {
            if (isPlaying) {
                PauseIcon(modifier = Modifier.size(32.dp))
            } else {
                PlayIcon(modifier = Modifier.size(32.dp))
            }
        }

        IconButton(
            onClick = onNext,
            modifier = Modifier.size(56.dp)
        ) {
            NextIcon()
        }
    }
}

private fun formatDuration(millis: Long): String {
    if (millis <= 0) return "0:00"
    val minutes = millis / 1000 / 60
    val seconds = (millis / 1000) % 60
    return "%d:%02d".format(minutes, seconds)
}
