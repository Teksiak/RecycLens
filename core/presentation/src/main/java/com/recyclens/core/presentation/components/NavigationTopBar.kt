@file:OptIn(ExperimentalMaterial3Api::class)

package com.recyclens.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.R
import com.recyclens.core.presentation.designsystem.BackIcon
import com.recyclens.core.presentation.designsystem.DarkColor
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.WhiteColor

@Composable
fun NavigationTopBar(
    title: String,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                modifier = Modifier.padding(end = 48.dp).fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Icon(
                    imageVector = BackIcon,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WhiteColor,
            titleContentColor = DarkColor,
            navigationIconContentColor = DarkColor
        )
    )
}

@Preview
@Composable
private fun NavigationTopBarPreview() {
    RecycLensTheme {
        NavigationTopBar(
            title = "Title",
            onNavigateBack = {}
        )
    }
}