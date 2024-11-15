package com.recyclens.information

import com.recyclens.core.presentation.Question

sealed interface InformationEvent {
    data class ExpandQuestion(val question: Question): InformationEvent
}