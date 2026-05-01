package com.aerotune.player.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system Sans-Serif as fallback (Inter font can be added manually)
private val AeroTuneFont = FontFamily.SansSerif

val AeroTuneTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = AeroTuneFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        color = TextWhite
    ),
    titleMedium = TextStyle(
        fontFamily = AeroTuneFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = TextWhite
    ),
    bodyLarge = TextStyle(
        fontFamily = AeroTuneFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextWhite
    ),
    bodyMedium = TextStyle(
        fontFamily = AeroTuneFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextGray
    ),
    labelMedium = TextStyle(
        fontFamily = AeroTuneFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = NeonCyan
    )
)
