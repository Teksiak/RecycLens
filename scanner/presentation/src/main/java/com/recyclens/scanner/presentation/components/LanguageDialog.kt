package com.recyclens.scanner.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.core.domain.settings.Language
import com.recyclens.core.presentation.components.TitleDialog
import com.recyclens.core.presentation.designsystem.Label
import com.recyclens.core.presentation.designsystem.Outline
import com.recyclens.core.presentation.designsystem.Primary
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.util.getName
import com.recyclens.scanner.presentation.R

@Composable
fun LanguageDialog(
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
    onDismiss: () -> Unit,
) {
    TitleDialog(
        title = stringResource(id = R.string.choose_language),
        onDismiss = onDismiss,
        contentPadding = PaddingValues(0.dp),
        content = {
            Language.entries.forEach {
                LanguageItem(
                    language = it,
                    isSelected = it == selectedLanguage,
                    onSelect = { onLanguageSelected(it) }
                )
            }
        }
    )
}

@Composable
private fun LanguageItem(
    language: Language,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = language.isAvailable
            ) {
                onSelect()
            }
            .padding(
                horizontal = 16.dp,
                vertical = if(language.isAvailable) 4.dp else 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = Primary,
                unselectedColor = Label,
                disabledSelectedColor = Outline,
                disabledUnselectedColor = Outline,
            ),
            enabled = language.isAvailable
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = language.getName(),
                style = MaterialTheme.typography.bodyLarge,
            )
            if (!language.isAvailable) {
                Text(
                    text = stringResource(id = R.string.under_development),
                    style = MaterialTheme.typography.bodySmall,
                    color = Label
                )
            }
        }
    }
}

@Preview
@Composable
fun LanguageDialogPreview() {
    RecycLensTheme {
        LanguageDialog(
            selectedLanguage = Language.POLISH,
            onLanguageSelected = {},
            onDismiss = {}
        )
    }
}