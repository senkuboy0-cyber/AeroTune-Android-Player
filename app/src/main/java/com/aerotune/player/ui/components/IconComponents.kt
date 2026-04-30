package com.aerotune.player.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aerotune.player.ui.theme.AeroTuneColorScheme

@Composable
fun PlayIcon(
    modifier: Modifier = Modifier,
    color: Color = AeroTuneColorScheme.primary
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val path = createPlayPath()
        drawPath(path, color)
    }
}

@Composable
fun PauseIcon(
    modifier: Modifier = Modifier,
    color: Color = AeroTuneColorScheme.primary
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val barWidth = size.width / 3
        val barHeight = size.height * 0.6f
        val startX = (size.width - barWidth) / 2
        val startY = size.height * 0.2f
        
        drawRect(
            color = color,
            topLeft = Offset(startX, startY),
            size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
        )
        drawRect(
            color = color,
            topLeft = Offset(startX + barWidth + 4.dp.toPx(), startY),
            size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
        )
    }
}

private fun DrawScope.createPlayPath(): Path {
    val path = Path()
    val trianglePath = floatArrayOf(
        0.2f, 0.15f,
        0.2f, 0.85f,
        0.85f, 0.5f
    )
    path.moveTo(size.width * trianglePath[0], size.height * trianglePath[1])
    path.lineTo(size.width * trianglePath[2], size.height * trianglePath[3])
    path.lineTo(size.width * trianglePath[4], size.height * trianglePath[5])
    path.close()
    return path
}
