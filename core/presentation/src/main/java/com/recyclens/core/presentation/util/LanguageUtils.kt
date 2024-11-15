package com.recyclens.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.recyclens.core.domain.settings.Language
import com.recyclens.core.presentation.R
import java.util.Locale

@Composable
fun Language.getName(): String = when(this) {
    Language.POLISH -> stringResource(id = R.string.polish)
    Language.ENGLISH -> stringResource(id = R.string.english)
}

@Composable
fun Language.getLocale(): Locale = when(this) {
    Language.POLISH -> Locale("pl")
    Language.ENGLISH -> Locale("en")
}