package com.recyclens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.recyclens.aboutus.AboutUsScreenRoot
import com.recyclens.core.presentation.NavigationRoute
import com.recyclens.information.InformationScreenRoot
import com.recyclens.information.InformationViewModel
import com.recyclens.scanner.presentation.ScannerScreenRoot
import com.recyclens.scanner.presentation.ScannerViewModel

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.ScannerRoute
    ) {
        composable<NavigationRoute.ScannerRoute> {
            ScannerScreenRoot(
                viewModel = hiltViewModel<ScannerViewModel>(viewModelStoreOwner),
                onNavigateToAboutUs = {
                    navController.navigate(NavigationRoute.AboutUsRoute)
                },
                onNavigateToInformation = {
                    navController.navigate(NavigationRoute.InformationRoute(question = it))
                },
                onNavigateToSettings = {
                    navController.navigate(NavigationRoute.SettingsRoute(setting = null))
                },
                onNavigateToHistory = {
                    navController.navigate(NavigationRoute.HistoryRoute)
                },
            )
        }
        composable<NavigationRoute.InformationRoute> {
            InformationScreenRoot(
                viewModel = hiltViewModel<InformationViewModel>(),
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<NavigationRoute.AboutUsRoute> {
            AboutUsScreenRoot()
        }
    }
}