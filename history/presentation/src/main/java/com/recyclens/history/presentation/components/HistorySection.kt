@file:OptIn(ExperimentalLayoutApi::class)

package com.recyclens.history.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.recyclens.core.domain.history.HistoryWaste
import com.recyclens.core.presentation.designsystem.ChevronDown
import com.recyclens.core.presentation.designsystem.Dark
import com.recyclens.core.presentation.designsystem.Label
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
    modifier: Modifier = Modifier
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
                .clickable(
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
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
                tint = Dark,
                modifier = Modifier.rotate(chevronRotate)
            )
            Text(
                text = date.formatToHistoryDate(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = wasteHistory.size.toWasteAmount(),
                style = MaterialTheme.typography.bodyMedium,
                color = Label
            )
        }
        AnimatedVisibility(
            visible = isExpanded
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                wasteHistory.forEach {
                    HistoryItem(
                        wasteUi = it.toHistoryWasteUi(),
                        onRemove = { /*TODO*/ }
                    )
                }
            }
        }
    }
}