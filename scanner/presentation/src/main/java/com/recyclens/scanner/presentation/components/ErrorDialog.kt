package com.recyclens.scanner.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.recyclens.core.presentation.designsystem.Close
import com.recyclens.core.presentation.designsystem.ErrorDark
import com.recyclens.core.presentation.designsystem.ErrorMain
import com.recyclens.core.presentation.designsystem.Label
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.TrashError
import com.recyclens.core.presentation.designsystem.White
import com.recyclens.scanner.presentation.R

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress =  true
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(80.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(ErrorMain, ErrorDark)
                        )
                    )
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(108.dp),
                    imageVector = TrashError,
                    contentDescription = null,
                    tint = White
                )
            }
            Box(
                modifier = Modifier
                    .offset(y = (-80).dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(White),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = (-12).dp, y = 12.dp),
                    onClick = onDismiss
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer(alpha = 0.5f)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(ErrorMain, ErrorDark)
                                        ),
                                        blendMode = BlendMode.SrcAtop
                                    )
                                }
                            },
                        imageVector = Close,
                        contentDescription = stringResource(id = android.R.string.cancel),
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = buildAnnotatedString {
                            val whoopsText = stringResource(id = R.string.whoops)
                            val errorText = stringResource(id = R.string.something_went_wrong)
                            val typoStyle = MaterialTheme.typography.titleMedium
                            withStyle(
                                style = ParagraphStyle(lineHeight = 30.sp)
                            ) {
                                withStyle(
                                    style = typoStyle.toSpanStyle()
                                ) {
                                    append(whoopsText)
                                    append("\n")
                                    withStyle(
                                        style = SpanStyle(color = ErrorMain)
                                    ){
                                        append(errorText)
                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.error_dialog_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ErrorMain),
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.try_again),
                            style = MaterialTheme.typography.bodyMedium,
                            color = ErrorMain
                        )
                    }
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.error_rationale),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = Label
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorDialogPreview() {
    RecycLensTheme {
        ErrorDialog()
    }
}