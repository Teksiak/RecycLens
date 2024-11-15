package com.recyclens.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.recyclens.core.presentation.R

@Composable
fun Int.toWasteAmount(): String {
    return "$this " + when(this) {
        1 -> stringResource(id = R.string.single_waste)
        in 2..4 -> stringResource(id = R.string.few_waste)
        else -> stringResource(id = R.string.many_waste)
    }
}