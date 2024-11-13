package com.recyclens.scanner.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.Settings
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.rotationMatrix
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recyclens.core.presentation.Question
import com.recyclens.core.presentation.components.TitleDialog
import com.recyclens.core.presentation.designsystem.CheckImage
import com.recyclens.core.presentation.designsystem.History
import com.recyclens.core.presentation.designsystem.Primary
import com.recyclens.core.presentation.designsystem.White
import com.recyclens.core.presentation.util.hasPermission
import com.recyclens.scanner.presentation.components.CameraOverlay
import com.recyclens.scanner.presentation.components.CameraPreview
import com.recyclens.scanner.presentation.components.PhotoButton
import com.recyclens.scanner.presentation.components.PredictionDialog
import com.recyclens.scanner.presentation.util.toInformation
import com.recyclens.scanner.presentation.util.toPredictionUi

@Composable
fun ScannerScreenRoot(
    viewModel: ScannerViewModel,
    onNavigateToInformation: (Question?) -> Unit,
    onNavigateToAboutUs: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ScannerScreen(
        state = state.value,
        onAction = { action ->
            when(action) {
                is ScannerAction.LearnMore -> onNavigateToInformation(action.wasteClass.toInformation())
                is ScannerAction.NavigateToSettings -> onNavigateToSettings()
                is ScannerAction.NavigateToAboutUs -> onNavigateToAboutUs()
                is ScannerAction.NavigateToHistory -> onNavigateToHistory()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun ScannerScreen(
    state: ScannerState,
    onAction: (ScannerAction) -> Unit,
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            val activity = context as ComponentActivity
            val showCameraRationale = activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

            onAction(
                ScannerAction.RequestCameraPermission(
                    acceptedCameraPermission = isGranted,
                    showCameraPermissionRationale = showCameraRationale
                )
            )
        }
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        if(lifecycleState == Lifecycle.State.RESUMED) {
            val activity = context as ComponentActivity
            val hasCameraPermission = context.hasPermission(Manifest.permission.CAMERA)
            val showCameraRationale = activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

            onAction(
                ScannerAction.SubmitCameraPermissionInfo(
                    acceptedCameraPermission = hasCameraPermission,
                    showCameraPermissionRationale = showCameraRationale
                )
            )

            if(!hasCameraPermission && !state.requestedCameraPermission) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    if(!state.acceptedCameraPermission && state.requestedCameraPermission) {
        if(state.showCameraPermissionRationale) {
            TitleDialog(
                title = stringResource(id = R.string.permissions),
                isDismissible = false,
                content = {
                    Text(
                        text = stringResource(id = R.string.camera_permission_rationale_description),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                buttons = {
                    TextButton(
                        onClick = {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.grant_access),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Primary
                        )
                    }
                }
            )
        } else {
            TitleDialog(
                title = stringResource(id = R.string.permissions),
                isDismissible = false,
                content = {
                    Text(
                        text = stringResource(id = R.string.camera_permission_denied_description),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                buttons = {
                    TextButton(
                        onClick = {
                            (context as Activity).openAppSettings()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.settings),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Primary
                        )
                    }
                }
            )
        }
    }

    if(state.acceptedCameraPermission) {
        val cameraController = remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            }
        }
        val previewBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
        val imageCaptureCallback = remember {
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val imageBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    previewBitmap.value = imageBitmap.asImageBitmap()
                    onAction(
                        ScannerAction.OnImageCapture(
                            image = imageBitmap
                        )
                    )
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    onAction(
                        ScannerAction.OnImageCaptureError(
                            error = exception.message ?: ""
                        )
                    )
                }
            }
        }

        LaunchedEffect(lifecycleState) {
            if(lifecycleState == Lifecycle.State.RESUMED && state.isFlashOn) {
                cameraController.enableTorch(true)
            }
        }

        state.classificationPrediction?.let { prediction ->
            PredictionDialog(
                predictionDetails = prediction.toPredictionUi(),
                onDismiss = {
                    previewBitmap.value = null
                    onAction(ScannerAction.DismissPredictionDialog)
                },
                onLearnMore = {
                    onAction(ScannerAction.LearnMore(prediction.wasteClass))
                }
            )
        }

        ModalNavigationDrawer(
            drawerContent = {
                // TODO: Add Drawer content
            }
        ) {
            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    CameraPreview(
                        cameraController = cameraController,
                        previewImage = previewBitmap.value
                    )

                    CameraOverlay(
                        paddingValues = paddingValues
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(bottom = 112.dp),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    // TODO: Display dailog about unfinished feature
                                }
                            ) {
                                Icon(
                                    imageVector = CheckImage,
                                    contentDescription = stringResource(id = R.string.image_check),
                                    tint = White
                                )
                            }
                            Spacer(modifier = Modifier.width(36.dp))
                            PhotoButton(
                                isLoading = state.isLoading,
                                onClick = {
                                    onAction(ScannerAction.ScanImage)
                                    cameraController.takePicture(
                                        ContextCompat.getMainExecutor(context),
                                        imageCaptureCallback
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(36.dp))
                            IconButton(
                                onClick = {
                                    onAction(ScannerAction.NavigateToHistory)
                                }
                            ) {
                                Icon(
                                    imageVector = History,
                                    contentDescription = stringResource(id = R.string.history),
                                    tint = White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}