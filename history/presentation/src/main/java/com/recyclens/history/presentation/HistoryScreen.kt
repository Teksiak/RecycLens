package com.recyclens.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recyclens.core.presentation.components.NavigationTopBar
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.core.presentation.designsystem.PrimaryColor
import com.recyclens.core.presentation.util.toWasteAmount
import com.recyclens.history.presentation.components.HistorySection
import com.recyclens.history.presentation.components.RemoveDialog
import com.recyclens.history.presentation.util.toBitmap

@Composable
fun HistoryScreenRoot(
    viewModel: HistoryViewModel,
    onNavigateToSettings: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        onAction = {
            viewModel.onAction(it)
            when (it) {
                is HistoryAction.NavigateToSettings -> onNavigateToSettings()
                is HistoryAction.NavigateBack -> onNavigateBack()
                else -> Unit
            }
        }
    )
}

@Composable
private fun HistoryScreen(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit
) {
    state.historyWasteToRemove?.let { waste ->
        RemoveDialog(
            wasteImage = waste.image.toBitmap().asImageBitmap(),
            onDismiss = { onAction(HistoryAction.CancelRemove) },
            onConfirm = { onAction(HistoryAction.ConfirmRemove) }
        )
    }

    Scaffold(
        topBar = {
            NavigationTopBar(
                title = stringResource(id = R.string.history),
                onNavigateBack = {
                    onAction(HistoryAction.NavigateBack)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                state.history.keys.toList(),
                key = { date -> date.toString() }
            ) { date ->
                HistorySection(
                    date = date,
                    wasteHistory = state.history[date] ?: emptyList(),
                    isExpanded = state.expandedDates.contains(date),
                    onRemove = { waste ->
                        onAction(HistoryAction.RemoveHistoryWaste(waste))
                    },
                    toggleExpanded = {
                        onAction(HistoryAction.ToggleExpandDate(date))
                    },
                    currentLanguage = state.currentLanguage
                )
            }
            item {
                Spacer(modifier = Modifier.size(if(state.history.isNotEmpty()) 24.dp else 0.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.bodySmall.toSpanStyle(),
                        ) {
                            if(state.history.isNotEmpty()) {
                                withStyle(
                                    style = SpanStyle(
                                        color = LabelColor
                                    )
                                ) {
                                    append(stringResource(id = R.string.history_disclaimer))
                                    append(state.settingsHistorySize.toWasteAmount() + ". ")
                                }
                                withLink(
                                    link = LinkAnnotation.Clickable(
                                        tag = stringResource(id = R.string.settings),
                                        linkInteractionListener = {
                                            onAction(HistoryAction.NavigateToSettings)
                                        }
                                    )
                                ) {
                                    withStyle(
                                        style = SpanStyle(
                                            color = PrimaryColor,
                                            textDecoration = TextDecoration.Underline
                                        )
                                    ) {
                                        append(stringResource(id = R.string.settings))
                                    }
                                }
                            } else {
                                withStyle(
                                    style = SpanStyle(
                                        color = LabelColor
                                    )
                                ) {
                                    append(stringResource(id = R.string.history_empty))
                                }
                            }
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}