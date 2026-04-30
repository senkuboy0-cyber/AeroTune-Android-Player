package com.aerotune.player.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aerotune.player.ui.theme.Primary

@Composable
fun PlayIcon(modifier: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(modifier)) {
        val path = Path().apply {
            moveTo(size.width * 0.25f, size.height * 0.15f)
            lineTo(size.width * 0.25f, size.height * 0.85f)
            lineTo(size.width * 0.8f, size.height * 0.5f)
            close()
        }
        drawPath(path, Primary)
    }
}

@Composable
fun PauseIcon(modifier: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(modifier)) {
        val barWidth = size.width * 0.25f
        val barHeight = size.height * 0.6f
        val gap = size.width * 0.1f
        drawRect(Primary, Offset.Zero, Size(barWidth, barHeight))
        drawRect(Primary, Offset(gap + barWidth, 0f), Size(barWidth, barHeight))
    }
}

@Composable
fun NextIcon(modifier: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(modifier)) {
        val barWidth = size.width * 0.08f
        drawRect(Primary, Offset(size.width * 0.55f, size.height * 0.2f), Size(barWidth, size.height * 0.6f))
        val path = Path().apply {
            moveTo(size.width * 0.35f, size.height * 0.2f)
            lineTo(size.width * 0.35f, size.height * 0.8f)
            lineTo(size.width * 0.8f, size.height * 0.5f)
            close()
        }
        drawPath(path, Primary)
    }
}


@Composable
fun PreviousIcon(modifier: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(modifier)) {
        val barWidth = size.width * 0.08f
        drawRect(Primary, Offset(size.width * 0.37f, size.height * 0.2f), Size(barWidth, size.height * 0.6f))
        val path = Path().apply {
            moveTo(size.width * 0.65f, size.height * 0.2f)
            lineTo(size.width * 0.65f, size.height * 0.8f)
            lineTo(size.width * 0.2f, size.height * 0.5f)
            close()
        }
        drawPath(path, Primary)
    }
}

@Composable
fun BackIcon(modifier: Dp = 24.dp) {
    Canvas(modifier = Modifier.size(modifier)) {
        val path = Path().apply {
            moveTo(size.width * 0.55f, size.height * 0.15f)
            lineTo(size.width * 0.35f, size.height * 0.5f)
            lineTo(size.width * 0.55f, size.height * 0.85f)
            close()
        }
        drawPath(path, Primary)
    }
}

@Composable
fun MusicNoteIcon(modifier: Dp = 64.dp) {
    Canvas(modifier = Modifier.size(modifier)) {
        val radius = size.width * 0.15f
        drawCircle(Primary, radius, Offset(size.width * 0.3f, size.height * 0.8f))
        val stemPath = Path().apply {
            moveTo(size.width * 0.65f, size.height * 0.78f)
            lineTo(size.width * 0.65f, size.height * 0.2f)
            lineTo(size.width * 0.88f, size.height * 0.2f)
            lineTo(size.width * 0.88f, size.height * 0.78f)
            close()
        }
        drawPath(stemPath, Primary)
    }
}
