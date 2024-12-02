package com.recyclens.settings.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.core.presentation.components.LanguageDialog
import com.recyclens.core.presentation.components.NavigationTopBar
import com.recyclens.core.presentation.components.TitleDialog
import com.recyclens.core.presentation.designsystem.OutlineColor
import com.recyclens.core.presentation.designsystem.PrimaryColor
import com.recyclens.core.presentation.util.getName
import com.recyclens.settings.presentation.components.VerticalPicker
import com.recyclens.core.presentation.util.toWasteAmount
import com.recyclens.settings.presentation.components.SettingBox

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = {
            viewModel.onAction(it)
            when (it) {
                SettingsAction.NavigateBack -> onNavigateBack()
                else -> Unit
            }
        }
    )
}

@Composable
private fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    if(state.showHistorySizeDialog) {
        TitleDialog(
            title = stringResource(R.string.history_size),
            onDismiss = { onAction(SettingsAction.HideHistorySizeDialog) },
            content = {
                VerticalPicker(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    items = SettingsRepository.HISTORY_SIZES.map { it.toWasteAmount() },
                    selectedIndex = SettingsRepository.HISTORY_SIZES.indexOf(state.historySize),
                    onItemSelected = { itemIndex ->
                        onAction(SettingsAction.SetHistorySize(SettingsRepository.HISTORY_SIZES[itemIndex]))
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(R.string.history_size_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        )
    }

    if(state.showLanguageDialog) {
        LanguageDialog(
            selectedLanguage = state.language,
            onLanguageSelected = { language ->
                onAction(SettingsAction.SetLanguage(language))
            },
            onDismiss = {
                onAction(SettingsAction.HideLanguageDialog)
            }
        )
    }

    Scaffold(
        topBar = {
            NavigationTopBar(
                title = stringResource(id = R.string.settings),
                onNavigateBack = {
                    onAction(SettingsAction.NavigateBack)
                }
            )
        }
    ) { paddingValues ->
        if (!state.areSettingsLoaded) return@Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                SettingBox(
                    content = {
                        Text(
                            text = state.language.getName(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = PrimaryColor
                        )
                    },
                    onClick = { onAction(SettingsAction.ShowLanguageDialog) }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.history_size),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                SettingBox(
                    content = {
                        Text(
                            text = state.historySize.toWasteAmount(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = PrimaryColor
                        )
                    },
                    onClick = { onAction(SettingsAction.ShowHistorySizeDialog) }
                )
            }
        }
    }
}