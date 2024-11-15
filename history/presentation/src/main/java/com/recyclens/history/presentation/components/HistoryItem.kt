package com.recyclens.history.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.core.presentation.designsystem.RemoveIcon
import com.recyclens.core.presentation.designsystem.WhiteColor
import com.recyclens.history.presentation.R
import com.recyclens.history.presentation.util.HistoryWasteUi

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    wasteUi: HistoryWasteUi,
    onRemove: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                bitmap = wasteUi.image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd),
                onClick = onRemove,
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = RemoveIcon,
                    contentDescription = stringResource(id = R.string.remove),
                    tint = WhiteColor
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(wasteUi.wasteClassGradient)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                modifier = Modifier.weight(1f).padding(end = 2.dp),
                text = wasteUi.wasteClassName,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = wasteUi.time,
                style = MaterialTheme.typography.bodySmall,
                color = LabelColor
            )
        }
    }

}