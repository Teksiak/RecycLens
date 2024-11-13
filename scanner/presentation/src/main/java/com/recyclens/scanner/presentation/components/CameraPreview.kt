package com.recyclens.scanner.presentation.components

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
    cameraController: LifecycleCameraController,
    previewImage: ImageBitmap?,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .then(
                if(previewImage != null) {
                    Modifier.drawWithContent {
                        // TODO: Fix this to draw the image correctly
                        drawImage(
                            previewImage,
                            dstOffset = IntOffset(
                                0, 0
                            ),
                            dstSize = IntSize(
                                width = size.width.toInt(),
                                height = size.height.toInt()
                            ),
                        )
                    }
                } else Modifier
            )

        ,
        factory = { viewContext ->
            PreviewView(viewContext).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.BLACK)
                scaleType = PreviewView.ScaleType.FILL_START
                this.controller = cameraController
                cameraController.bindToLifecycle(lifecycleOwner)
            }
        }
    )
}