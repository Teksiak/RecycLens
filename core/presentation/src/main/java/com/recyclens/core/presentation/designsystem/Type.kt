package com.recyclens.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.recyclens.core.presentation.R

val Roboto = FontFamily(
    Font(R.font.roboto_regular),
)
val Nunito = FontFamily(
    Font(R.font.nunito_var)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontSize = 30.sp,
        lineHeight = 38.sp,
        color = Dark
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        color = Dark
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontSize = 20.sp,
        lineHeight = 25.sp,
        color = Dark
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Dark
    ),
    bodyMedium = TextStyle(
        fontFamily = Nunito,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Dark
    ),
    bodySmall = TextStyle(
        fontFamily = Nunito,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = Dark
    ),
)