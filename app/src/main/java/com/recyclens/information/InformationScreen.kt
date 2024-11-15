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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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
import com.recyclens.core.presentation.util.ObserveAsEvents
import com.recyclens.information.components.QuestionsSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun InformationScreenRoot(
    viewModel: InformationViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    InformationScreen(
        state = state,
        events = viewModel.events,
        onAction = {
            viewModel.onAction(it)
            when(it) {
                is InformationAction.NavigateBack -> onNavigateBack()
                else -> Unit
            }
        }
    )
}

@Composable
fun InformationScreen(
    state: InformationState,
    events: Flow<InformationEvent>,
    onAction: (InformationAction) -> Unit
) {
    val questionCoordinates = remember { mutableMapOf<Question, Int>() }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val topPadding = with(LocalDensity.current) {
        92.dp.toPx().toInt()
    }

    ObserveAsEvents(flow = events) { event ->
        when(event) {
            is InformationEvent.ExpandQuestion -> {
                onAction(InformationAction.ToggleExpanded(event.question))
                coroutineScope.launch {
                    delay(300)
                    val y = questionCoordinates[event.question] ?: 0
                    scrollState.animateScrollTo((y - topPadding).coerceIn(0, scrollState.maxValue))
                }
            }
        }
    }

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
                .verticalScroll(scrollState)
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
                },
                onQuestionGloballyPositioned = { question, y ->
                    questionCoordinates[question] = y
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
                },
                onQuestionGloballyPositioned = { question, y ->
                    questionCoordinates[question] = y
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
                },
                onQuestionGloballyPositioned = { question, y ->
                    questionCoordinates[question] = y
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
            events = flowOf(),
            onAction = {}
        )
    }
}