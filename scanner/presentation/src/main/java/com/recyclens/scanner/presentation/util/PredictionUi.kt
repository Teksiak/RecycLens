package com.recyclens.scanner.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.recyclens.core.domain.WasteClass
import com.recyclens.core.presentation.designsystem.BioBin
import com.recyclens.core.presentation.designsystem.BioDark
import com.recyclens.core.presentation.designsystem.BioGradient
import com.recyclens.core.presentation.designsystem.BioMain
import com.recyclens.core.presentation.designsystem.ElectronicsBin
import com.recyclens.core.presentation.designsystem.ElectronicsDark
import com.recyclens.core.presentation.designsystem.ElectronicsGradient
import com.recyclens.core.presentation.designsystem.ElectronicsMain
import com.recyclens.core.presentation.designsystem.GlassBin
import com.recyclens.core.presentation.designsystem.GlassDark
import com.recyclens.core.presentation.designsystem.GlassGradient
import com.recyclens.core.presentation.designsystem.GlassMain
import com.recyclens.core.presentation.designsystem.MixedBin
import com.recyclens.core.presentation.designsystem.MixedDark
import com.recyclens.core.presentation.designsystem.MixedGradient
import com.recyclens.core.presentation.designsystem.MixedMain
import com.recyclens.core.presentation.designsystem.PaperBin
import com.recyclens.core.presentation.designsystem.PaperDark
import com.recyclens.core.presentation.designsystem.PaperGradient
import com.recyclens.core.presentation.designsystem.PaperMain
import com.recyclens.core.presentation.designsystem.PlasticBin
import com.recyclens.core.presentation.designsystem.PlasticDark
import com.recyclens.core.presentation.designsystem.PlasticGradient
import com.recyclens.core.presentation.designsystem.PlasticMain
import com.recyclens.core.presentation.util.getGradient
import com.recyclens.core.presentation.util.getMainColor
import com.recyclens.core.presentation.util.getTrashBin
import com.recyclens.scanner.domain.ClassificationPrediction
import com.recyclens.scanner.presentation.R

data class PredictionUi(
    val trashBin: ImageVector,
    val wasteClassMainColor: Color,
    val wasteClassGradient: Brush,
    val trashBinName: String,
    val dialogDescription: String,
    val confidence: String,
)

@Composable
fun ClassificationPrediction.toPredictionUi(): PredictionUi {
    val (trashBinName, dialogDescription) = when(this.wasteClass) {
        WasteClass.PLASTIC -> stringResource(id = R.string.plastic_title) to stringResource(id = R.string.plastic_description)
        WasteClass.GLASS -> stringResource(id = R.string.glass_title) to stringResource(id = R.string.glass_description)
        WasteClass.BIO -> stringResource(id = R.string.bio_title) to stringResource(id = R.string.bio_description)
        WasteClass.MIXED -> stringResource(id = R.string.mixed_title) to stringResource(id = R.string.mixed_description)
        WasteClass.ELECTRONICS -> stringResource(id = R.string.electronics_title) to stringResource(id = R.string.electronics_description)
        WasteClass.PAPER -> stringResource(id = R.string.paper_title) to stringResource(id = R.string.paper_description)
    }

    return PredictionUi(
        trashBin = wasteClass.getTrashBin(),
        wasteClassMainColor = wasteClass.getMainColor(),
        wasteClassGradient = wasteClass.getGradient(),
        trashBinName = trashBinName,
        dialogDescription = dialogDescription,
        confidence = "${(this.confidence * 1000).toInt() / 10f}%"
    )
}
