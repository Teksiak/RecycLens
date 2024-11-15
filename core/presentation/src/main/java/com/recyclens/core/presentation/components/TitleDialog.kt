package com.recyclens.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.recyclens.core.presentation.designsystem.Close
import com.recyclens.core.presentation.designsystem.Dark
import com.recyclens.core.presentation.designsystem.Label
import com.recyclens.core.presentation.designsystem.Outline
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.White

@Composable
fun TitleDialog(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = Dark,
    isDismissible: Boolean = true,
    dismissButtonColor: Color = Label,
    onDismiss: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
    content: @Composable ColumnScope.() -> Unit,
    buttons: (@Composable RowScope.() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = isDismissible,
            dismissOnBackPress =  isDismissible
        )
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(White)
                .padding(
                    bottom = if(buttons != null) 4.dp else contentPadding.calculateBottomPadding(),
                ),
            contentAlignment = Alignment.TopEnd
        ) {
            if (isDismissible) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = (-12).dp, y = 11.dp),
                    onClick = onDismiss
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Close,
                        contentDescription = stringResource(id = android.R.string.cancel),
                        tint = dismissButtonColor
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    color = titleColor,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp
                        )
                )
                HorizontalDivider(
                    color = Outline
                )
                Spacer(modifier = Modifier.size(contentPadding.calculateTopPadding()))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = contentPadding.calculateStartPadding(LayoutDirection.Ltr)),
                ) {
                    content()
                }
                buttons?.let {
                    Spacer(modifier = Modifier.size(contentPadding.calculateBottomPadding()))
                    HorizontalDivider(
                        color = Outline
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                            ),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        buttons()
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun TitleDialogPreview() {
    RecycLensTheme {
        TitleDialog(
            title = "Title",
            content = {
                Text("Content")
            },
            buttons = {
                Text("Button1")
                Spacer(modifier = Modifier.width(16.dp))
                Text("Button2")
            }
        )
    }
}