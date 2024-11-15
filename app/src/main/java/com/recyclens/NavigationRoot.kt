package com.recyclens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.recyclens.aboutus.AboutUsScreenRoot
import com.recyclens.core.presentation.NavigationRoute
import com.recyclens.history.presentation.HistoryScreenRoot
import com.recyclens.history.presentation.HistoryViewModel
import com.recyclens.information.InformationScreenRoot
import com.recyclens.information.InformationViewModel
import com.recyclens.scanner.presentation.ScannerScreenRoot
import com.recyclens.scanner.presentation.ScannerViewModel
import com.recyclens.settings.presentation.SettingsScreenRoot
import com.recyclens.settings.presentation.SettingsViewModel

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
                    navController.navigate(NavigationRoute.SettingsRoute)
                },
                onNavigateToHistory = {
                    navController.navigate(NavigationRoute.HistoryRoute)
                },
            )
        }
        composable<NavigationRoute.HistoryRoute> {
            HistoryScreenRoot(
                viewModel = hiltViewModel<HistoryViewModel>(),
                onNavigateToSettings = {
                    navController.navigate(NavigationRoute.SettingsRoute)
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
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
        composable<NavigationRoute.SettingsRoute> {
             SettingsScreenRoot(
                 viewModel = hiltViewModel<SettingsViewModel>(viewModelStoreOwner),
                 onNavigateBack = {
                     navController.navigateUp()
                 }
             )
        }
        composable<NavigationRoute.AboutUsRoute> {
            AboutUsScreenRoot(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}