package com.recyclens.settings.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.core.presentation.designsystem.PrimaryColor
import com.recyclens.core.presentation.designsystem.WhiteColor
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun <T> VerticalPicker(
    items: List<T>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemsCount = items.size
    val boxSize = DpSize(125.dp, 40.dp)
    val itemSize = with(LocalDensity.current) {
        boxSize.height.toPx()
    }
    val initialOffsetY = remember { selectedIndex * itemSize }

    var dragStartY by remember {
        mutableFloatStateOf(0f)
    }
    var currentOffsetY by remember {
        mutableFloatStateOf(initialOffsetY)
    }

    val animatedOffsetY by animateFloatAsState(
        targetValue = currentOffsetY,
        label = "animatedOffsetY"
    )

    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.bodyLarge

    Canvas(
        modifier = modifier
            .width(boxSize.width)
            .height(boxSize.height * 3)
            .alpha(0.99f)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        dragStartY = it.y
                    },
                    onDrag = { change, dragAmount ->
                        val changeY = change.position.y
                        val newY = currentOffsetY + (dragStartY - changeY) * 0.1f

                        currentOffsetY = newY.coerceIn(
                            minimumValue = 0f,
                            maximumValue = (itemsCount - 1) * itemSize
                        )
                    },
                    onDragEnd = {
                        val rest = currentOffsetY % itemSize
                        val roundUp = abs(rest / itemSize).roundToInt() == 1
                        val newY = if (roundUp) {
                            if (rest > 0) currentOffsetY - rest + itemSize
                            else currentOffsetY - rest - itemSize
                        } else currentOffsetY - rest

                        currentOffsetY = newY.coerceIn(
                            minimumValue = 0f,
                            maximumValue = (itemsCount - 1) * itemSize
                        )

                        val selectedItemIndex = (currentOffsetY / itemSize).toInt()
                        if(selectedItemIndex != selectedIndex) {
                            onItemSelected(selectedItemIndex)
                        }
                    }
                )
            }
            .pointerInput(selectedIndex) {
                detectTapGestures(
                    onTap = { offset ->
                        if(offset.y < itemSize) {
                            val previousItemIndex = selectedIndex - 1
                            if(previousItemIndex >= 0) {
                                currentOffsetY = previousItemIndex * itemSize
                                onItemSelected(previousItemIndex)
                            }
                        } else if(offset.y > itemSize * 2) {
                            val nextItemIndex = selectedIndex + 1
                            if(nextItemIndex < itemsCount) {
                                currentOffsetY = nextItemIndex * itemSize
                                onItemSelected(nextItemIndex)
                            }
                        }
                    }
                )
            }
    ) {
        val boxHeight = boxSize.height.toPx()
        val boxCornerRadius = 20.dp.toPx()

        drawRoundRect(
            color = PrimaryColor,
            topLeft = Offset(
                x = 0f,
                y = boxHeight
            ),
            size = Size(
                width = size.width,
                height = boxHeight
            ),
            cornerRadius = CornerRadius(boxCornerRadius),
        )

        for (i in 0 until itemsCount) {
            val currentY = i * boxHeight - animatedOffsetY + boxHeight

            val textLayoutResult = textMeasurer.measure(
                text = items[i].toString(),
                style = textStyle
            )

            translate(
                left = center.x - textLayoutResult.size.width / 2,
                top = currentY + textLayoutResult.size.height / 2
            ) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = items[i].toString(),
                    style = textStyle.copy(
                        color = WhiteColor
                    ),
                )
            }
        }

        drawRect(
            color = LabelColor,
            topLeft = Offset(
                x = 0f,
                y = 0f
            ),
            size = Size(
                width = size.width,
                height = boxHeight
            ),
            blendMode = BlendMode.SrcIn
        )
        drawRect(
            color = LabelColor,
            topLeft = Offset(
                x = 0f,
                y = 2 * boxHeight
            ),
            size = Size(
                width = size.width,
                height = boxHeight
            ),
            blendMode = BlendMode.SrcIn
        )
    }
}