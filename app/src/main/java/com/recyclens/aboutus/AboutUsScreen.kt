package com.recyclens.aboutus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.R
import com.recyclens.core.presentation.components.NavigationTopBar
import com.recyclens.core.presentation.designsystem.RecycLensTheme

@Composable
fun AboutUsScreenRoot() {
    AboutUsScreen()
}

@Composable
fun AboutUsScreen() {
    Scaffold(
        topBar = {
            NavigationTopBar(
                title = stringResource(id = R.string.about_us),
                onNavigateBack = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
                .padding(bottom = 24.dp)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
    RecycLensTheme {
        AboutUsScreen()
    }
}