package com.recyclens.scanner.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import com.recyclens.core.presentation.designsystem.DarkColor

@Composable
fun CameraOverlay(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0f to DarkColor,
                    0.25f to DarkColor.copy(alpha = 0f),
                    0.75f to DarkColor.copy(alpha = 0f),
                    1f to DarkColor,
                )
            )
            .alpha(0.3f),
    )
}