package com.recyclens.history.presentation.util

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import com.recyclens.core.domain.history.HistoryWaste
import com.recyclens.core.presentation.util.getGradient
import com.recyclens.core.presentation.util.getName

data class HistoryWasteUi(
    val wasteClassGradient: Brush,
    val wasteClassName: String,
    val image: Bitmap,
    val time: String,
)

@Composable
fun HistoryWaste.toHistoryWasteUi(): HistoryWasteUi {
    return HistoryWasteUi(
        wasteClassGradient = this.wasteClass.getGradient(),
        wasteClassName = this.wasteClass.getName(),
        image = this.image.toBitmap(),
        time = "${this.date.hour}:${if(this.date.minute < 10) "0" else ""}${this.date.minute}",
    )
}
