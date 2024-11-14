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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.recyclens.core.domain.WasteClass
import com.recyclens.core.presentation.designsystem.Close
import com.recyclens.core.presentation.designsystem.Label
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.White
import com.recyclens.scanner.domain.ClassificationPrediction
import com.recyclens.scanner.presentation.R
import com.recyclens.scanner.presentation.util.PredictionUi
import com.recyclens.scanner.presentation.util.toPredictionUi

@Composable
fun PredictionDialog(
    predictionDetails: PredictionUi,
    onDismiss: () -> Unit = {},
    onLearnMore: () -> Unit = {},
    onWrongSuggestion: () -> Unit = {}
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
                    .background(predictionDetails.wasteClassGradient)
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .offset(y = 16.dp),
                    imageVector = predictionDetails.trashBin,
                    contentDescription = null,
                    tint = Color.Unspecified
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
                                        predictionDetails.wasteClassGradient,
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
                            val throwOut = stringResource(id = R.string.throw_out)
                            val typoStyle = MaterialTheme.typography.titleMedium
                            withStyle(
                                style = ParagraphStyle(lineHeight = 30.sp)
                            ) {
                                withStyle(
                                    style = typoStyle.toSpanStyle()
                                ) {
                                    append(throwOut)
                                    append("\n")
                                    withStyle(
                                        style = SpanStyle(color = predictionDetails.wasteClassMainColor)
                                    ){
                                        append(predictionDetails.trashBinName)
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
                        text = predictionDetails.dialogDescription,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    OutlinedButton(
                        onClick = onLearnMore,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, predictionDetails.wasteClassMainColor),
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.learn_more),
                            style = MaterialTheme.typography.bodyMedium,
                            color = predictionDetails.wasteClassMainColor
                        )
                    }
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.our_model_confidence) + " " + predictionDetails.confidence,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = Label
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.bodySmall.toSpanStyle(),
                            ) {
                                withLink(
                                    link = LinkAnnotation.Clickable(
                                        tag = stringResource(id = R.string.is_suggestion_wrong),
                                        linkInteractionListener = {
                                            onWrongSuggestion()
                                        }
                                    )
                                ) {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Label,
                                            textDecoration = TextDecoration.Underline
                                        )
                                    ) {
                                        append(stringResource(id = R.string.is_suggestion_wrong))
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PredictionDialogPreview() {
    RecycLensTheme {
        PredictionDialog(
            predictionDetails = ClassificationPrediction(
                wasteClass = WasteClass.PLASTIC,
                confidence = 0.998
            ).toPredictionUi()
        )
    }
}