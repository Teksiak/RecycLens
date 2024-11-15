package com.recyclens.core.presentation

import kotlinx.serialization.Serializable

interface NavigationRoute {
    @Serializable
    data object ScannerRoute: NavigationRoute
    @Serializable
    data object HistoryRoute: NavigationRoute
    @Serializable
    data object SettingsRoute: NavigationRoute
    @Serializable
    data class InformationRoute(val question: Question?): NavigationRoute
    @Serializable
    data object AboutUsRoute: NavigationRoute
}