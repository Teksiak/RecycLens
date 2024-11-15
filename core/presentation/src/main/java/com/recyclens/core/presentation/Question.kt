package com.recyclens.core.presentation

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class Question {
    WHAT_IS_RECYCLENS,
    HOW_THE_APP_WORKS,
    WHY_USE_RECYCLENS,

    WHAT_IS_RECYCLING,
    WHY_RECYCLE,

    PAPER_BIN,
    PLASTIC_BIN,
    MIXED_BIN,
    BIO_BIN,
    GLASS_BIN,
    ELECTRONICS_BIN,
}