package com.recyclens.scanner.presentation.util

import android.graphics.Bitmap

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    val aspectRatio: Float = width.toFloat() / height.toFloat()

    val (newWidth, newHeight) = if(width > height) {
        maxWidth to (maxWidth / aspectRatio).toInt()
    } else {
        (maxHeight * aspectRatio).toInt() to maxHeight
    }

    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}