package com.recyclens.information.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.recyclens.R
import com.recyclens.core.presentation.Question

data class QuestionUi(
    val title: String,
    val answer: String
)

@Composable
fun Question.toQuestionUi(): QuestionUi {
    val (title, answer) = when(this) {
        Question.WHAT_IS_RECYCLENS -> stringResource(id = R.string.what_is_recyclens_title) to stringResource(id = R.string.what_is_recyclens_answer)
//        Question.HOW_THE_APP_WORKS -> TODO()
//        Question.WHY_USE_RECYCLENS -> TODO()
//        Question.WHAT_IS_RECYCLING -> TODO()
//        Question.WHY_RECYCLE -> TODO()
//        Question.PAPER_BIN -> TODO()
//        Question.PLASTIC_BIN -> TODO()
//        Question.MIXED_BIN -> TODO()
//        Question.BIO_BIN -> TODO()
//        Question.GLASS_BIN -> TODO()
//        Question.ELECTRONICS_BIN -> TODO()
        else -> stringResource(id = R.string.what_is_recyclens_title) to stringResource(id = R.string.what_is_recyclens_answer)
    }
    return QuestionUi(title, answer)
}