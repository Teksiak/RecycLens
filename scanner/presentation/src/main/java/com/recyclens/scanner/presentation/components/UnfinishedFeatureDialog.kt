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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.recyclens.core.presentation.designsystem.CloseIcon
import com.recyclens.core.presentation.designsystem.ErrorDark
import com.recyclens.core.presentation.designsystem.ErrorMain
import com.recyclens.core.presentation.designsystem.RecycleIcon
import com.recyclens.core.presentation.designsystem.WhiteColor
import com.recyclens.scanner.presentation.R

@Composable
fun UnfinishedFeatureDialog(
    onDismiss: () -> Unit,
    onLearnMore: () -> Unit
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
                    imageVector = RecycleIcon,
                    contentDescription = null,
                    tint = WhiteColor
                )
            }
            Box(
                modifier = Modifier
                    .offset(y = (-80).dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(WhiteColor),
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
                        imageVector = CloseIcon,
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
                            val featureName = stringResource(id = R.string.image_classification)
                            val underDevelopment = stringResource(id = R.string.feature_under_development)
                            val typoStyle = MaterialTheme.typography.titleMedium
                            withStyle(
                                style = ParagraphStyle(lineHeight = 30.sp)
                            ) {
                                withStyle(
                                    style = typoStyle.toSpanStyle()
                                ) {
                                    append(featureName)
                                    append("\n")
                                    withStyle(
                                        style = SpanStyle(color = ErrorMain)
                                    ){
                                        append(underDevelopment)
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
                        text = stringResource(id = R.string.feature_under_development_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    OutlinedButton(
                        onClick = onLearnMore,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, ErrorMain),
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.learn_more),
                            style = MaterialTheme.typography.bodyMedium,
                            color = ErrorMain
                        )
                    }
                }
            }
        }
    }
}