package com.aerotune.player

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.VectorItem
import androidx.compose.ui.unit.dp

val PlayIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Play",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path {
            moveTo(8f, 5f)
            verticalLineTo(19f)
            lineTo(19f, 5f)
            close()
        }
    }.build()

val PauseIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Pause",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path {
            moveTo(6f, 19f)
            verticalLineTo(5f)
            lineTo(10f, 5f)
            verticalLineTo(19f)
            close()
        }
        path {
            moveTo(14f, 19f)
            verticalLineTo(5f)
            lineTo(18f, 5f)
            verticalLineTo(19f)
            close()
        }
    }.build()

val NextIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Next",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path {
            moveTo(6f, 18f)
            verticalLineTo(6f)
            lineTo(6f, 18f)
            close()
        }
        path {
            moveTo(6f, 6f)
            lineTo(18f, 12f)
            lineTo(6f, 18f)
            lineTo(6f, 6f)
            close()
        }
    }.build()

val PreviousIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Previous",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path {
            moveTo(18f, 18f)
            verticalLineTo(6f)
            lineTo(18f, 6f)
            close()
        }
        path {
            moveTo(18f, 6f)
            lineTo(6f, 12f)
            lineTo(18f, 18f)
            lineTo(18f, 6f)
            close()
        }
    }.build()

val BackIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Back",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path {
            moveTo(15f, 18f)
            lineTo(9f, 12f)
            lineTo(15f, 6f)
            lineTo(15f, 18f)
            close()
        }
    }.build()
