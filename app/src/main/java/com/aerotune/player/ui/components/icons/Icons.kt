package com.aerotune.player.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PlayIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "Play",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(8f, 5f)
                verticalLineTo(19f)
                horizontalLineTo(19f)
                close()
            }
        }.build()
    }

val PauseIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "Pause",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6f, 19f)
                horizontalLineTo(10f)
                verticalLineTo(5f)
                horizontalLineTo(6f)
                close()
                moveTo(14f, 19f)
                horizontalLineTo(18f)
                verticalLineTo(5f)
                horizontalLineTo(14f)
                close()
            }
        }.build()
    }

val SkipNextIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "SkipNext",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6f, 18f)
                verticalLineTo(6f)
                lineTo(15f, 12f)
                close()
                moveTo(15f, 18f)
                horizontalLineTo(18f)
                verticalLineTo(6f)
                horizontalLineTo(15f)
                close()
            }
        }.build()
    }

val SkipPreviousIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "SkipPrevious",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9f, 6f)
                horizontalLineTo(6f)
                verticalLineTo(18f)
                horizontalLineTo(9f)
                close()
                moveTo(9f, 6f)
                verticalLineTo(18f)
                lineTo(18f, 12f)
                close()
            }
        }.build()
    }

val ShuffleIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "Shuffle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16f, 3f)
                horizontalLineTo(21f)
                verticalLineTo(8f)
                close()
                moveTo(21f, 16f)
                horizontalLineTo(16f)
                verticalLineTo(21f)
                close()
                moveTo(4f, 20f)
                lineTo(4f, 16f)
                lineTo(8f, 16f)
                lineTo(12f, 20f)
                close()
                moveTo(21f, 4f)
                lineTo(16f, 4f)
                lineTo(12f, 8f)
                lineTo(8f, 4f)
                lineTo(4f, 4f)
                lineTo(4f, 8f)
            }
        }.build()
    }

val RepeatIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "Repeat",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17f, 1f)
                lineTo(21f, 5f)
                lineTo(17f, 9f)
                close()
                moveTo(21f, 5f)
                horizontalLineTo(10f)
                curveTo(5f, 5f, 3f, 7f, 3f, 12f)
                verticalLineTo(14f)
                curveTo(3f, 19f, 5f, 21f, 10f, 21f)
                close()
                moveTo(7f, 3f)
                horizontalLineTo(3f)
                verticalLineTo(19f)
                horizontalLineTo(7f)
            }
        }.build()
    }

val RepeatOneIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "RepeatOne",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17f, 1f)
                lineTo(21f, 5f)
                lineTo(17f, 9f)
                close()
                moveTo(21f, 5f)
                horizontalLineTo(10f)
                curveTo(5f, 5f, 3f, 7f, 3f, 12f)
                verticalLineTo(14f)
                curveTo(3f, 19f, 5f, 21f, 10f, 21f)
                close()
                moveTo(7f, 15f)
                lineTo(7f, 19f)
                moveTo(11f, 15f)
                lineTo(11f, 19f)
            }
        }.build()
    }

val LibraryIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "Library",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(4f, 6f)
                horizontalLineTo(20f)
                close()
                moveTo(4f, 18f)
                horizontalLineTo(20f)
                close()
            }
        }.build()
    }

val NowPlayingIcon: ImageVector
    get() {
        ImageVector.Builder(
            name = "NowPlaying",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
            pathFillType = PathFillType.NonZero
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00D4FF)),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
                curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
                curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
                moveTo(12f, 6f)
                curveTo(15.31f, 6f, 18f, 8.69f, 18f, 12f)
                curveTo(18f, 15.31f, 15.31f, 18f, 12f, 18f)
                curveTo(8.69f, 18f, 6f, 15.31f, 6f, 12f)
            }
        }.build()
    }
