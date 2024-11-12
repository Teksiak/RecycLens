package com.recyclens.scanner.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageAnalysis
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recyclens.core.presentation.components.TitleDialog
import com.recyclens.core.presentation.designsystem.Primary
import com.recyclens.core.presentation.util.hasPermission
import com.recyclens.scanner.presentation.components.CameraOverlay
import com.recyclens.scanner.presentation.components.CameraPreview
import com.recyclens.scanner.presentation.components.PhotoButton

@Composable
fun ScannerScreenRoot(
    viewModel: ScannerViewModel,
    onNavigateToSettings: () -> Unit, // TODO: Add Information param
    onNavigateToAboutUs: () -> Unit,
    onNavigateToHistory: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ScannerScreen(
        state = state.value,
        onAction = { action ->
            when(action) {
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

        LaunchedEffect(lifecycleState) {
            if(lifecycleState == Lifecycle.State.RESUMED && state.isFlashOn) {
                cameraController.enableTorch(true)
            }
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
                        .background(Color.Black)
                ) {
                    CameraPreview(
                        cameraController = cameraController
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
                        ) {
                            PhotoButton(
                                onClick = {
                                    // TODO
                                }
                            )
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