package com.recyclens.settings.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.designsystem.OutlineColor

@Composable
fun SettingBox(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .border(
                width = 1.dp,
                color = OutlineColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}