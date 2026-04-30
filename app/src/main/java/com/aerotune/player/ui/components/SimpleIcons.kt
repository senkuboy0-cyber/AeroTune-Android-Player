package com.aerotune.player.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.VectorPath
import androidx.compose.ui.graphics.vector.toVectorPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aerotune.player.ui.theme.OnSurfaceVariant
import com.aerotune.player.ui.theme.Primary

val PlayIcon: ImageVector
    get() {
        val path = Path().apply {
            moveTo(19f, 5f)
            lineTo(19f, 19f)
            lineTo(5f, 19f)
            lineTo(5f, 5f)
            close()
        }
        return androidx.compose.ui.graphics.vector.ImageVector.Builder(
            name = "Play",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).build()
    }

@Composable
fun PlayButtonIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    androidx.compose.foundation.Canvas(
        modifier = Modifier.size(modifier)
    ) {
        val path = Path().apply {
            moveTo(size.width * 0.25f, size.height * 0.15f)
            lineTo(size.width * 0.25f, size.height * 0.85f)
            lineTo(size.width * 0.8f, size.height * 0.5f)
            close()
        }
        drawPath(path, tint)
    }
}

@Composable
fun PauseButtonIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    Canvas(modifier = Modifier.size(modifier)) {
        drawRect(tint, androidx.compose.ui.geometry.Offset.Zero, androidx.compose.ui.geometry.Size(size.width / 3, size.height * 0.7f))
        drawRect(tint, androidx.compose.ui.geometry.Offset(size.width * 0.66f, 0f), androidx.compose.ui.geometry.Size(size.width / 3, size.height * 0.7f))
    }
}

@Composable
fun NextButtonIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    Canvas(modifier = Modifier.size(modifier)) {
        val barWidth = size.width * 0.08f
        drawRect(tint, androidx.compose.ui.geometry.Offset(size.width * 0.55f, 0f), androidx.compose.ui.geometry.Size(barWidth, size.height * 0.6f))
        val trianglePath = Path().apply {
            moveTo(size.width * 0.35f, size.height * 0.2f)
            lineTo(size.width * 0.35f, size.height * 0.8f)
            lineTo(size.width * 0.8f, size.height * 0.5f)
            close()
        }
        drawPath(trianglePath, tint)
    }
}

@Composable
fun PreviousButtonIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    Canvas(modifier = Modifier.size(modifier)) {
        val barWidth = size.width * 0.08f
        drawRect(tint, androidx.compose.ui.geometry.Offset(size.width * 0.37f, 0f), androidx.compose.ui.geometry.Size(barWidth, size.height * 0.6f))
        val trianglePath = Path().apply {
            moveTo(size.width * 0.65f, size.height * 0.2f)
            lineTo(size.width * 0.65f, size.height * 0.8f)
            lineTo(size.width * 0.2f, size.height * 0.5f)
            close()
        }
        drawPath(trianglePath, tint)
    }
}

@Composable
fun BackButtonIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    Canvas(modifier = Modifier.size(modifier)) {
        val path = Path().apply {
            moveTo(size.width * 0.55f, size.height * 0.15f)
            lineTo(size.width * 0.35f, size.height * 0.5f)
            lineTo(size.width * 0.55f, size.height * 0.85f)
            close()
        }
        drawPath(path, tint)
    }
}

@Composable
fun NextTrackIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    Canvas(modifier = Modifier.size(modifier)) {
        val barWidth = size.width * 0.06f
        drawRect(tint, androidx.compose.ui.geometry.Offset(size.width * 0.52f, size.height * 0.2f), androidx.compose.ui.geometry.Size(barWidth, size.height * 0.6f))
        val trianglePath = Path().apply {
            moveTo(size.width * 0.3f, size.height * 0.2f)
            lineTo(size.width * 0.3f, size.height * 0.8f)
            lineTo(size.width * 0.78f, size.height * 0.5f)
            close()
        }
        drawPath(trianglePath, tint)
    }
}

@Composable
fun MusicNoteIcon(
    modifier: Dp = 24.dp,
    tint: Color = Primary
) {
    Canvas(modifier = Modifier.size(modifier)) {
        val headRadius = size.width * 0.18f
        drawCircle(tint, headRadius, androidx.compose.ui.geometry.Offset(headRadius * 1.5f, size.height * 0.78f))
        val stemPath = Path().apply {
            moveTo(size.width * 0.7f, size.height * 0.75f)
            lineTo(size.width * 0.7f, size.height * 0.2f)
            lineTo(size.width * 0.9f, size.height * 0.2f)
            lineTo(size.width * 0.9f, size.height * 0.75f)
            close()
        }
        drawPath(stemPath, tint)
    }
}
