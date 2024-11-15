package com.recyclens.information

import com.recyclens.core.presentation.Question

sealed interface InformationAction {
    data class ToggleExpanded(val question: Question): InformationAction
    data object NavigateBack: InformationAction
}