package com.recyclens.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

val PrimaryGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(PrimaryColor, SecondaryColor),
    )

val PlasticGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(PlasticMain, PlasticDark),
    )

val GlassGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(GlassMain, GlassDark),
    )

val BioGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(BioMain, BioDark),
    )

val MixedGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(MixedMain, MixedDark),
    )

val ElectronicsGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(ElectronicsMain, ElectronicsDark),
    )

val PaperGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(PaperMain, PaperDark),
    )