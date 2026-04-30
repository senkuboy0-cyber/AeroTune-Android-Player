package com.aerotune.player.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aerotune.player.ui.theme.OnSurfaceVariant
import com.aerotune.player.ui.theme.Primary

@Composable
fun PlayIcon(modifier: Modifier = Modifier(64.dp)) {
    Canvas(modifier = modifier) {
        val path = createPlayPath()
        drawPath(path, Primary)
    }
}

@Composable
fun PauseIcon(modifier: Modifier = Modifier(64.dp)) {
    Canvas(modifier = modifier) {
        drawRect(Primary, size.copy(size.width / 3, size.height * 0.6f).to androidx.compose.ui.geometry.Offset((size.width - size.width / 3) / 2, size.height * 0.2f))
        drawRect(Primary, size.copy(size.width / 3, size.height * 0.6f).to androidx.compose.ui.geometry.Offset((size.width - size.width / 3) / 2 + size.width / 3 + 4.dp.toPx(), size.height * 0.2f))
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
