package com.recyclens.scanner.presentation.components

import android.util.Log
import androidx.compose.runtime.Composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.recyclens.core.presentation.designsystem.TertiaryColor
import com.recyclens.core.presentation.designsystem.WhiteColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

@Composable
fun RecognisingAnimation(
    amount: Int,
    minSize: Dp,
    maxSize: Dp,
) {
    val localDensity = LocalDensity.current
    val screenSize = remember { mutableStateOf(IntSize(0, 0)) }
    val circles = remember { mutableStateListOf<CircleData>() }

    LaunchedEffect(screenSize) {
        if(screenSize.value == IntSize(0, 0)) return@LaunchedEffect

        val screenWidth =  with(localDensity) { screenSize.value.width.toDp() }
        val screenHeight = with(localDensity) { screenSize.value.height.toDp() }


        withContext(Dispatchers.Unconfined) {
            repeat(amount) {
                var position: Pair<Dp, Dp>
                var size: Dp
                do {
                    position = Pair(
                        (0 .. screenWidth.value.toInt()).random().dp,
                        (0 .. screenHeight.value.toInt()).random().dp
                    )
                    size = (minSize.value.toInt() .. maxSize.value.toInt()).random().dp
                } while (isTooClose(position, size, circles, 36.dp))

                circles.add(CircleData(position.first, position.second, size))
            }
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 98.dp,
                bottom = 270.dp
            )
            .onSizeChanged {
                screenSize.value = it
            },
        contentAlignment = Alignment.TopStart
    ) {
        circles.forEachIndexed { index, circle ->
            AnimatedCircle(
                modifier = Modifier
                    .offset(circle.x, circle.y)
                    .size(circle.size),
                delayMillis = index * 500
            )
        }
    }
}

@Composable
fun AnimatedCircle(
    modifier: Modifier,
    delayMillis: Int
) {
    val alphaAnim = rememberInfiniteTransition(label = "")
    val alpha by alphaAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, delayMillis = delayMillis, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = modifier
            .alpha(alpha)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        WhiteColor.copy(alpha = 0.1f),
                        WhiteColor.copy(alpha = 0.3f),
                    ),
                ),
                shape = CircleShape
            )
    )
}

private fun isTooClose(
    position: Pair<Dp, Dp>,
    size: Dp,
    circles: List<CircleData>,
    minDistance: Dp
): Boolean {
    val (x, y) = position
    val radius = size.value / 2
    return circles.any { other ->
        val otherRadius = other.size.value / 2
        val dx = (other.x.value + otherRadius) - (x.value + radius)
        val dy = (other.y.value + otherRadius) - (y.value + radius)
        val centerDistance = sqrt((dx * dx + dy * dy).toDouble())
        centerDistance < (radius + otherRadius + minDistance.value)
    }
}

private data class CircleData(
    val x: Dp,
    val y: Dp,
    val size: Dp
)
