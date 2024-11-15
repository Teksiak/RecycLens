package com.recyclens.information.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.R
import com.recyclens.core.presentation.Question
import com.recyclens.core.presentation.designsystem.ChevronDown
import com.recyclens.core.presentation.designsystem.ContainerColor
import com.recyclens.core.presentation.designsystem.DarkColor
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.core.presentation.designsystem.OutlineColor
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.StarIcon
import com.recyclens.core.presentation.designsystem.WhiteColor
import com.recyclens.information.util.QuestionUi
import com.recyclens.information.util.toQuestionUi

@Composable
fun QuestionsSection(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    questions: List<Question>,
    currentExpandedQuestion: Question?,
    onQuestionGloballyPositioned: (Question, Int) -> Unit = { _, _ ->},
    toggleExpanded: (Question) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = LabelColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = DarkColor
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = OutlineColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
        ) {
            questions.forEachIndexed { index, question ->
                ExpandableQuestion(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        val y = coordinates.positionInRoot().y.toInt()
                        onQuestionGloballyPositioned(question, y)
                    },
                    question = question.toQuestionUi(),
                    isExpanded = currentExpandedQuestion == question,
                    toggleExpanded = {
                        toggleExpanded(question)
                    }
                )
                if (index < questions.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = OutlineColor,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableQuestion(
    question: QuestionUi,
    isExpanded: Boolean,
    toggleExpanded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val chevronRotate by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = ""
    )
    val chevronColor by animateColorAsState(
        targetValue = if (isExpanded) DarkColor else LabelColor,
        animationSpec = tween(300),
        label = ""
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (isExpanded) WhiteColor else ContainerColor,
        animationSpec = tween(300),
        label = ""
    )

    Column(
        modifier = modifier
            .background(backgroundColor)
            .padding(12.dp)
            .clickable(
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                toggleExpanded()
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Icon(
                imageVector = ChevronDown,
                contentDescription = if (isExpanded) stringResource(id = R.string.expand_question)
                    else stringResource(id = R.string.collapse_question),
                tint = chevronColor,
                modifier = Modifier.rotate(chevronRotate)
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = question.answer,
                style = MaterialTheme.typography.bodyMedium,
                color = LabelColor
            )
        }
    }
}

@Preview
@Composable
private fun QuestionsSectionPreview() {
    RecycLensTheme {
        QuestionsSection(
            title = "Aplikacja",
            icon = StarIcon,
            questions = listOf(
                Question.WHAT_IS_RECYCLENS,
                Question.HOW_THE_APP_WORKS
            ),
            currentExpandedQuestion = Question.WHAT_IS_RECYCLENS,
            toggleExpanded = {}
        )
    }
}