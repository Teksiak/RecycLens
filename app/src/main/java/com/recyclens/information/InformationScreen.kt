package com.recyclens.information

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recyclens.R
import com.recyclens.core.presentation.Question
import com.recyclens.core.presentation.components.NavigationTopBar
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.Recycle
import com.recyclens.core.presentation.designsystem.Star
import com.recyclens.core.presentation.designsystem.Trash
import com.recyclens.information.components.QuestionsSection

@Composable
fun InformationScreenRoot(
    viewModel: InformationViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    InformationScreen(
        state = state,
        onAction = {
            when(it) {
                is InformationAction.NavigateBack -> onNavigateBack()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun InformationScreen(
    state: InformationState,
    onAction: (InformationAction) -> Unit
) {
    Scaffold(
        topBar = {
            NavigationTopBar(
                title = stringResource(id = R.string.informations),
                onNavigateBack = {
                    onAction(InformationAction.NavigateBack)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            QuestionsSection(
                title = stringResource(id = R.string.application),
                icon = Star,
                questions = listOf(
                    Question.WHAT_IS_RECYCLENS,
                    Question.HOW_THE_APP_WORKS,
                    Question.WHY_USE_RECYCLENS,
                ),
                currentExpandedQuestion = state.expandedQuestion,
                toggleExpanded = {
                    onAction(InformationAction.ToggleExpanded(it))
                }
            )
            QuestionsSection(
                title = stringResource(id = R.string.recycling),
                icon = Recycle,
                questions = listOf(
                    Question.WHAT_IS_RECYCLING,
                    Question.WHY_RECYCLE,
                ),
                currentExpandedQuestion = state.expandedQuestion,
                toggleExpanded = {
                    onAction(InformationAction.ToggleExpanded(it))
                }
            )
            QuestionsSection(
                title = stringResource(id = R.string.bins),
                icon = Trash,
                questions = listOf(
                    Question.PAPER_BIN,
                    Question.PLASTIC_BIN,
                    Question.MIXED_BIN,
                    Question.BIO_BIN,
                    Question.GLASS_BIN,
                    Question.ELECTRONICS_BIN,
                ),
                currentExpandedQuestion = state.expandedQuestion,
                toggleExpanded = {
                    onAction(InformationAction.ToggleExpanded(it))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
    RecycLensTheme {
        InformationScreen(
            state = InformationState(),
            onAction = {}
        )
    }
}