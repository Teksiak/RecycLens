package com.recyclens.aboutus

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.recyclens.core.presentation.designsystem.RecycLensTheme

@Composable
fun AboutUsScreenRoot() {
    AboutUsScreen()
}

@Composable
fun AboutUsScreen() {

}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
    RecycLensTheme {
        AboutUsScreen()
    }
}