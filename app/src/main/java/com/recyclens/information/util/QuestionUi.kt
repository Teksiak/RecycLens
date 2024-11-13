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
        Question.HOW_THE_APP_WORKS -> stringResource(id = R.string.how_the_app_works_title) to stringResource(id = R.string.how_the_app_works_answer)
        Question.WHY_USE_RECYCLENS -> stringResource(id = R.string.why_use_recyclens_title) to stringResource(id = R.string.why_use_recyclens_answer)
        Question.WHAT_IS_RECYCLING -> stringResource(id = R.string.what_is_recycling_title) to stringResource(id = R.string.what_is_recycling_answer)
        Question.WHY_RECYCLE -> stringResource(id = R.string.why_recycle_title) to stringResource(id = R.string.why_recycle_answer)
        Question.PAPER_BIN -> stringResource(id = R.string.paper_bin_title) to stringResource(id = R.string.paper_bin_answer)
        Question.PLASTIC_BIN -> stringResource(id = R.string.plastic_bin_title) to stringResource(id = R.string.plastic_bin_answer)
        Question.MIXED_BIN ->  stringResource(id = R.string.mixed_bin_title) to stringResource(id = R.string.mixed_bin_answer)
        Question.BIO_BIN -> stringResource(id = R.string.bio_bin_title) to stringResource(id = R.string.bio_bin_answer)
        Question.GLASS_BIN -> stringResource(id = R.string.glass_bin_title) to stringResource(id = R.string.glass_bin_answer)
        Question.ELECTRONICS_BIN -> stringResource(id = R.string.electronics_bin_title) to stringResource(id = R.string.electronics_bin_answer)
    }
    return QuestionUi(title, answer)
}