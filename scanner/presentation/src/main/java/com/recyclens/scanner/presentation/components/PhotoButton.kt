package com.recyclens.scanner.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.designsystem.PrimaryColor
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.SecondaryColor
import com.recyclens.core.presentation.designsystem.TertiaryColor

@Composable
fun PhotoButton(
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .border(3.dp, TertiaryColor.copy(alpha = 0.9f), shape = CircleShape)
            .clip(CircleShape)
            .clickable(
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    color = PrimaryColor,
                ),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(54.dp),
                strokeWidth = 3.dp,
                color = TertiaryColor
            )
        }
        else {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SecondaryColor,
                                PrimaryColor
                            ),
                            startY = -50f,
                        ),
                        alpha = 0.95f
                    )
            )
        }
    }
}

@Preview
@Composable
fun PhotoButtonPreview() {
    RecycLensTheme {
        PhotoButton(
            onClick = {}
        )
    }
}