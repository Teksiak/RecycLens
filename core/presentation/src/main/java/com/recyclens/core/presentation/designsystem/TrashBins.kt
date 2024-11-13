package com.recyclens.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.recyclens.core.presentation.R

val PlasticBin: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.plastic_bin)
val PaperBin: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.paper_bin)
val BioBin: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.bio_bin)
val GlassBin: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.glass_bin)
val MixedBin: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.mixed_bin)
val ElectronicsBin: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.electronic_bin)