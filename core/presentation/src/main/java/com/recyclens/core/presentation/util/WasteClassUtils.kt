package com.recyclens.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.recyclens.core.domain.WasteClass
import com.recyclens.core.presentation.R
import com.recyclens.core.presentation.designsystem.BioBin
import com.recyclens.core.presentation.designsystem.BioGradient
import com.recyclens.core.presentation.designsystem.BioMain
import com.recyclens.core.presentation.designsystem.ElectronicsBin
import com.recyclens.core.presentation.designsystem.ElectronicsGradient
import com.recyclens.core.presentation.designsystem.ElectronicsMain
import com.recyclens.core.presentation.designsystem.GlassBin
import com.recyclens.core.presentation.designsystem.GlassGradient
import com.recyclens.core.presentation.designsystem.GlassMain
import com.recyclens.core.presentation.designsystem.MixedBin
import com.recyclens.core.presentation.designsystem.MixedGradient
import com.recyclens.core.presentation.designsystem.MixedMain
import com.recyclens.core.presentation.designsystem.PaperBin
import com.recyclens.core.presentation.designsystem.PaperGradient
import com.recyclens.core.presentation.designsystem.PaperMain
import com.recyclens.core.presentation.designsystem.PlasticBin
import com.recyclens.core.presentation.designsystem.PlasticGradient
import com.recyclens.core.presentation.designsystem.PlasticMain

@Composable
fun WasteClass.getName() = when(this) {
    WasteClass.PLASTIC -> stringResource(id = R.string.plastic)
    WasteClass.GLASS -> stringResource(id = R.string.glass)
    WasteClass.MIXED -> stringResource(id = R.string.mixed)
    WasteClass.BIO -> stringResource(id = R.string.bio)
    WasteClass.ELECTRONICS -> stringResource(id = R.string.electronics)
    WasteClass.PAPER -> stringResource(id = R.string.paper)
}

@Composable
fun WasteClass.getMainColor() = when(this) {
    WasteClass.PLASTIC -> PlasticMain
    WasteClass.GLASS -> GlassMain
    WasteClass.BIO -> BioMain
    WasteClass.MIXED -> MixedMain
    WasteClass.ELECTRONICS -> ElectronicsMain
    WasteClass.PAPER -> PaperMain
}

@Composable
fun WasteClass.getGradient(): Brush = when(this) {
    WasteClass.PLASTIC -> PlasticGradient
    WasteClass.GLASS -> GlassGradient
    WasteClass.MIXED -> MixedGradient
    WasteClass.BIO -> BioGradient
    WasteClass.ELECTRONICS -> ElectronicsGradient
    WasteClass.PAPER -> PaperGradient
}

@Composable
fun WasteClass.getTrashBin(): ImageVector = when(this) {
    WasteClass.PLASTIC -> PlasticBin
    WasteClass.GLASS -> GlassBin
    WasteClass.BIO -> BioBin
    WasteClass.MIXED -> MixedBin
    WasteClass.ELECTRONICS -> ElectronicsBin
    WasteClass.PAPER -> PaperBin
}