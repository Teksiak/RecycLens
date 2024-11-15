package com.recyclens.history.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.components.TitleDialog
import com.recyclens.core.presentation.designsystem.ErrorMain
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.history.presentation.R

@Composable
fun RemoveDialog(
    wasteImage: ImageBitmap,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    TitleDialog(
        title = stringResource(id = R.string.remove_from_history),
        titleColor = ErrorMain,
        onDismiss = onDismiss,
        content = {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(140.dp)
                    .aspectRatio(0.75f)
                    .clip(RoundedCornerShape(8.dp)),
                bitmap = wasteImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = stringResource(id = R.string.remove_from_history_message),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        },
        buttons = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.bodyLarge,
                    color = LabelColor
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(id = R.string.remove),
                    style = MaterialTheme.typography.bodyLarge,
                    color = ErrorMain
                )
            }
        }
    )

}