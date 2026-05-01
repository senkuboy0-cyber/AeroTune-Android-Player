package com.aerotune.player.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
    onPrevious: () -> Unit = {},
    onSeek: (Float) -> Unit = {},
    progress: Float = 0f
) {
    var isFullPlayerVisible by remember { mutableStateOf(false) }
    var showPermissionRequest by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        showPermissionRequest = !allGranted
    }

    LaunchedEffect(Unit) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissions)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberpunkBg)
    ) {
        // Animated background particles
        AnimatedBackground()

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            AnimatedHeader()

            if (showPermissionRequest) {
                PermissionCard(onRequestPermission = {
                    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        arrayOf(
                            Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    } else {
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                    permissionLauncher.launch(permissions)
                })
            } else if (tracks.isEmpty()) {
                EmptyLibraryView()
            } else {
                // Track list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = tracks,
                        key = { _, track -> track.id }
                    ) { index, track ->
                        TrackItem(
                            track = track,
                            isPlaying = track == currentTrack && isPlaying,
                            isCurrent = track == currentTrack,
                            onClick = {
                                onTrackClick(track)
                                isFullPlayerVisible = true
                            },
                            animationDelay = index * 50
                        )
                    }
                }
            }
        }

        // Bottom Player Bar
        currentTrack?.let { track ->
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
                    progress = progress,
                    onBackClick = { isFullPlayerVisible = false },
                    onPlayPause = onPlayPause,
                    onNext = onNext,
                    onPrevious = onPrevious,
                    onSeek = onSeek
                )
            }
        }
    }
}

@Composable
private fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Top glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            NeonCyan.copy(alpha = glowAlpha),
                            Color.Transparent
                        )
                    )
                )
        )

        // Bottom glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            NeonPink.copy(alpha = glowAlpha * 0.5f)
                        )
                    )
                )
        )
    }
}

@Composable
private fun AnimatedHeader() {
    var visible by remember { mutableStateOf(false) }
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else -30f,
        animationSpec = tween(600),
        label = "header_offset"
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
            .offset(y = offsetY.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "AEROTUNE",
                style = MaterialTheme.typography.headlineMedium,
                color = NeonCyan,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = "Your Cyberpunk Library",
                style = MaterialTheme.typography.bodySmall,
                color = TextGray
            )
        }

        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = NeonCyan
            )
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = TextGray
            )
        }
    }
}

@Composable
private fun PermissionCard(onRequestPermission: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .clickable(onClick = onRequestPermission),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = null,
                    tint = NeonCyan,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Access Your Music",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap to allow AeroTune to access your audio files",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRequestPermission,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
                ) {
                    Text("Grant Access", color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun EmptyLibraryView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.MusicOff,
                contentDescription = null,
                tint = TextGray,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Tracks Found",
                style = MaterialTheme.typography.titleMedium,
                color = TextWhite
            )
            Text(
                text = "Add music files to your device",
                style = MaterialTheme.typography.bodySmall,
                color = TextGray
            )
        }
    }
}

@Composable
private fun TrackItem(
    track: AudioTrack,
    isPlaying: Boolean,
    isCurrent: Boolean,
    onClick: () -> Unit,
    animationDelay: Int = 0
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(animationDelay.toLong())
        visible = true
    }

    val offsetX by animateFloatAsState(
        targetValue = if (visible) 0f else -50f,
        animationSpec = tween(400),
        label = "track_offset"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400),
        label = "track_alpha"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = offsetX.dp)
            .alpha(alpha)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent)
                NeonCyan.copy(alpha = 0.15f)
            else
                GlassBg
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isCurrent) NeonCyan else GlassBorder,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album art placeholder
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.linearGradient(
                            if (isPlaying) GradientCyanPink
                            else GradientPurpleCyan
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isPlaying) {
                    PlayingIndicator()
                } else {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isCurrent) NeonCyan else TextWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = track.formatDuration(),
                style = MaterialTheme.typography.labelMedium,
                color = NeonCyan
            )
        }
    }
}

@Composable
private fun PlayingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "playing")

    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(3) { index ->
            val height by infiniteTransition.animateFloat(
                initialValue = 8f,
                targetValue = 24f,
                animationSpec = infiniteRepeatable(
                    animation = tween(400, delayMillis = index * 100),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bar_$index"
            )

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(height.dp)
                    .background(NeonCyan, RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
private fun BottomPlayerBar(
    track: AudioTrack,
    isPlaying: Boolean,
    onPlayerClick: () -> Unit,
    onPlayPause: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onPlayerClick),
        colors = CardDefaults.cardColors(containerColor = GlassBg),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(NeonPink.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = NeonCyan
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(48.dp)
                    .background(NeonPink, CircleShape)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun FullPlayerScreen(
    track: AudioTrack,
    isPlaying: Boolean,
    progress: Float,
    onBackClick: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (Float) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "full_player")

    val albumScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isPlaying) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "album_scale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberpunkBg)
            .padding(20.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Minimize",
                    tint = TextWhite,
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = "NOW PLAYING",
                style = MaterialTheme.typography.labelMedium,
                color = NeonCyan,
                modifier = Modifier.weight(1f),
                letterSpacing = 4.sp
            )

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = TextGray
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Album art with glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            // Glow effect
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                NeonCyan.copy(alpha = glowAlpha * 0.4f),
                                NeonPink.copy(alpha = glowAlpha * 0.2f),
                                Color.Transparent
                            )
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .blur(30.dp)
            )

            // Album art container
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .scale(albumScale)
                    .background(
                        brush = Brush.linearGradient(GradientCyanPink),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(2.dp, GlassBorder, RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(120.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Track info
        Text(
            text = track.title,
            style = MaterialTheme.typography.headlineSmall,
            color = TextWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = track.artist,
            style = MaterialTheme.typography.bodyLarge,
            color = NeonCyan,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Progress slider
        Slider(
            value = progress,
            onValueChange = onSeek,
            colors = SliderDefaults.colors(
                thumbColor = NeonCyan,
                activeTrackColor = NeonCyan,
                inactiveTrackColor = GlassBorder
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "0:00",
                style = MaterialTheme.typography.labelSmall,
                color = TextGray
            )
            Text(
                text = track.formatDuration(),
                style = MaterialTheme.typography.labelSmall,
                color = TextGray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = TextWhite,
                    modifier = Modifier.size(36.dp)
                )
            }

            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.linearGradient(GradientCyanPink),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = onNext,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = TextWhite,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
