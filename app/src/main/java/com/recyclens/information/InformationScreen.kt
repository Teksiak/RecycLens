package com.recyclens.information

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.recyclens.core.presentation.designsystem.RecycLensTheme

@Composable
fun InformationScreenRoot() {
    InformationScreen()
}

@Composable
fun InformationScreen() {

}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
    RecycLensTheme {
        InformationScreen()
    }
}