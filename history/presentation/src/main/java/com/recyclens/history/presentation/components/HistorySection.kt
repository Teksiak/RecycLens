@file:OptIn(ExperimentalLayoutApi::class)

package com.recyclens.history.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.recyclens.core.domain.history.HistoryWaste
import com.recyclens.core.domain.settings.Language
import com.recyclens.core.domain.settings.SettingsRepository
import com.recyclens.core.presentation.designsystem.ChevronDown
import com.recyclens.core.presentation.designsystem.DarkColor
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.core.presentation.util.getLocale
import com.recyclens.core.presentation.util.toWasteAmount
import com.recyclens.history.presentation.R
import com.recyclens.history.presentation.util.formatToHistoryDate
import com.recyclens.history.presentation.util.toHistoryWasteUi
import java.time.LocalDate

@Composable
fun HistorySection(
    date: LocalDate,
    wasteHistory: List<HistoryWaste>,
    isExpanded: Boolean,
    toggleExpanded: () -> Unit,
    onRemove: (HistoryWaste) -> Unit,
    modifier: Modifier = Modifier,
    currentLanguage: Language = SettingsRepository.DEFAULT_LANGUAGE
) {
    val chevronRotate by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = ""
    )

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                ) {
                    toggleExpanded()
                }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ChevronDown,
                contentDescription = if (isExpanded) stringResource(id = R.string.expand_history)
                else stringResource(id = R.string.collapse_history),
                tint = DarkColor,
                modifier = Modifier.rotate(chevronRotate)
            )
            Text(
                text = date.formatToHistoryDate(currentLanguage.getLocale()),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = wasteHistory.size.toWasteAmount(),
                style = MaterialTheme.typography.bodyMedium,
                color = LabelColor
            )
        }
        AnimatedVisibility(
            visible = isExpanded
        ) {
            val maxItemsInEachRow = 3
            val rowWidth = remember { mutableIntStateOf(0) }
            val spacingInPx = with(LocalDensity.current) { 16.dp.toPx() }
            val itemSize = remember(rowWidth.intValue) {
                (rowWidth.intValue - spacingInPx * (maxItemsInEachRow - 1)) / maxItemsInEachRow
            }
            val itemSizeInDp = with(LocalDensity.current) { itemSize.toDp() }
            ContextualFlowRow(
                itemCount = wasteHistory.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .onSizeChanged {
                        rowWidth.intValue = it.width
                    },
                maxItemsInEachRow = maxItemsInEachRow,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) { index ->
                wasteHistory.getOrNull(index)?.let { waste ->
                    HistoryItem(
                        modifier = Modifier
                            .width(itemSizeInDp),
                        wasteUi = waste.toHistoryWasteUi(),
                        onRemove = {
                            onRemove(waste)
                        }
                    )
                }
            }
        }
    }
}