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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.R
import com.recyclens.core.presentation.designsystem.Back
import com.recyclens.core.presentation.designsystem.RecycLensTheme

@Composable
fun NavigationTopBar(
    title: String,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth().padding(end = 60.dp),
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
                    imageVector = Back,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
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