package com.recyclens.scanner.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recyclens.core.presentation.Question
import com.recyclens.core.presentation.components.TitleDialog
import com.recyclens.core.presentation.designsystem.CheckImageIcon
import com.recyclens.core.presentation.designsystem.CircleInfoIcon
import com.recyclens.core.presentation.designsystem.FlashIcon
import com.recyclens.core.presentation.designsystem.FlashOnIcon
import com.recyclens.core.presentation.designsystem.TrashInfoIcon
import com.recyclens.core.presentation.designsystem.HistoryIcon
import com.recyclens.core.presentation.designsystem.LabelColor
import com.recyclens.core.presentation.designsystem.LanguageIcon
import com.recyclens.core.presentation.designsystem.MenuIcon
import com.recyclens.core.presentation.designsystem.OutlineColor
import com.recyclens.core.presentation.designsystem.PrimaryColor
import com.recyclens.core.presentation.designsystem.SettingsIcon
import com.recyclens.core.presentation.designsystem.WhiteColor
import com.recyclens.core.presentation.util.getName
import com.recyclens.core.presentation.util.hasPermission
import com.recyclens.scanner.presentation.components.CameraOverlay
import com.recyclens.scanner.presentation.components.CameraPreview
import com.recyclens.scanner.presentation.components.Drawer
import com.recyclens.scanner.presentation.components.DrawerItem
import com.recyclens.scanner.presentation.components.ErrorDialog
import com.recyclens.scanner.presentation.components.LanguageDialog
import com.recyclens.scanner.presentation.components.PhotoButton
import com.recyclens.scanner.presentation.components.PredictionDialog
import com.recyclens.scanner.presentation.components.AnalysingAnimation
import com.recyclens.scanner.presentation.components.UnfinishedFeatureDialog
import com.recyclens.scanner.presentation.util.toQuestion
import com.recyclens.scanner.presentation.util.toPredictionUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScannerScreenRoot(
    viewModel: ScannerViewModel,
    onNavigateToInformation: (Question?) -> Unit,
    onNavigateToAboutUs: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        (context as ComponentActivity).enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(0),
        )

        onDispose {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(0, 0),
            )
        }
    }

    ScannerScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
            when(action) {
                is ScannerAction.NavigateToInformation -> {
                    coroutineScope.launch(Dispatchers.Main) {
                        if(state.classificationPrediction != null) {
                            delay(100)
                        }
                        onNavigateToInformation(action.question)
                    }
                }
                is ScannerAction.NavigateToSettings -> onNavigateToSettings()
                is ScannerAction.NavigateToAboutUs -> onNavigateToAboutUs()
                is ScannerAction.NavigateToHistory -> onNavigateToHistory()
                else -> Unit
            }
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
                            color = PrimaryColor
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
                            color = PrimaryColor
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
                    image.close()
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
                    onAction(ScannerAction.NavigateToInformation(prediction.wasteClass.toQuestion()))
                },
                onWrongSuggestion = {
                    onAction(ScannerAction.ShowWrongSuggestionsDialog)
                }
            )
        }

        if(state.isError) {
            ErrorDialog(
                onDismiss = {
                    onAction(ScannerAction.DismissErrorDialog)
                }
            )
        }

        if(state.showWrongSuggestionDialog) {
            TitleDialog(
                title = stringResource(id = R.string.wrong_suggestion),
                content = {
                    Text(
                        text = stringResource(id = R.string.wrong_suggestions_description),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = LabelColor
                    )
                },
                buttons = {
                    TextButton(
                        onClick = {
                            onAction(ScannerAction.DismissWrongSuggestionsDialog)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.understand),
                            style = MaterialTheme.typography.bodyLarge,
                            color = PrimaryColor
                        )
                    }
                }
            )
        }

        if(state.showLanguageDialog) {
            LanguageDialog(
                selectedLanguage = state.currentLanguage,
                onLanguageSelected = { language ->
                    onAction(ScannerAction.SetLanguage(language))
                },
                onDismiss = {
                    onAction(ScannerAction.HideLanguageDialog)
                }
            )
        }

        if(state.showUnfinishedFeatureDialog) {
            UnfinishedFeatureDialog(
                onDismiss = {
                    onAction(ScannerAction.DismissUnfinishedFeatureDialog)
                },
                onLearnMore = {
                    onAction(ScannerAction.NavigateToInformation(Question.HOW_THE_APP_WORKS))
                }
            )
        }

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Drawer {
                    DrawerItem(
                        icon = LanguageIcon,
                        title = stringResource(id = R.string.language),
                        subtitle = state.currentLanguage.getName(),
                        onClick = {
                            onAction(ScannerAction.ShowLanguageDialog)
                        }
                    )
                    DrawerItem(
                        icon = SettingsIcon,
                        title = stringResource(id = R.string.settings),
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onAction(ScannerAction.NavigateToSettings)
                        }
                    )
                    DrawerItem(
                        icon = CircleInfoIcon,
                        title = stringResource(id = R.string.about_us),
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onAction(ScannerAction.NavigateToAboutUs)
                        }
                    )
                }
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

                    if(previewBitmap.value != null) {
                        AnalysingAnimation(
                            amount = 15,
                            minSize = 12.dp,
                            maxSize = 28.dp
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(bottom = 72.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(
                            visible = !state.isLoading && state.classificationPrediction == null
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 24.dp),
                            ) {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = MenuIcon,
                                        contentDescription = stringResource(id = R.string.open_drawer),
                                        tint = OutlineColor
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(
                                    onClick = {
                                        onAction(ScannerAction.NavigateToInformation(null))
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = TrashInfoIcon,
                                        contentDescription = stringResource(id = R.string.informations),
                                        tint = WhiteColor
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.7f))
                                .padding(vertical = 4.dp, horizontal = 12.dp),
                            text = if(previewBitmap.value == null) {
                                stringResource(id = R.string.take_a_photo)
                            } else stringResource(id = R.string.analysing_photo),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            textAlign = TextAlign.Center,
                            color = WhiteColor
                        )
                        Spacer(modifier = Modifier.size(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(36.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(
                                visible = !state.isLoading && state.classificationPrediction == null
                            ) {
                                IconButton(
                                    onClick = {
                                        onAction(ScannerAction.ShowUnfinishedFeatureDialog)
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(32.dp),
                                        imageVector = CheckImageIcon,
                                        contentDescription = stringResource(id = R.string.image_classification),
                                        tint = LabelColor
                                    )
                                }
                            }
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
                            AnimatedVisibility(
                                visible = !state.isLoading && state.classificationPrediction == null
                            ) {
                                IconButton(
                                    onClick = {
                                        onAction(ScannerAction.NavigateToHistory)
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(32.dp),
                                        imageVector = HistoryIcon,
                                        contentDescription = stringResource(id = R.string.history),
                                        tint = WhiteColor
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        AnimatedVisibility(
                            visible = !state.isLoading && state.classificationPrediction == null
                        ) {
                            IconButton(
                                onClick = {
                                    cameraController.enableTorch(!state.isFlashOn)
                                    onAction(ScannerAction.ToggleFlash)
                                }
                            ) {
                                Icon(
                                    imageVector = if(state.isFlashOn) FlashOnIcon else FlashIcon,
                                    contentDescription = if(state.isFlashOn) stringResource(id = R.string.flash_off) else stringResource(id = R.string.flash_on),
                                    tint = WhiteColor
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