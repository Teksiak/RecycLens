package com.recyclens.information

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.recyclens.core.presentation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val questionParam = savedStateHandle.toRoute<NavigationRoute.InformationRoute>().question

    private val _state = MutableStateFlow(InformationState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<InformationEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(300)
            questionParam?.let { question ->
                eventChannel.send(InformationEvent.ExpandQuestion(question))
            }
        }
    }

    fun onAction(action: InformationAction) {
        when(action) {
            is InformationAction.ToggleExpanded -> {
                _state.update { state ->
                    state.copy(
                        expandedQuestion = action.question.takeUnless { it == state.expandedQuestion }
                    )
                }
            }
            else -> Unit
        }
    }
}