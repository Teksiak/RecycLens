package com.recyclens.scanner.presentation.util

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.compressImageToTargetSize(targetSizeKB: Int = 2048): ByteArray {
    val initialStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, initialStream)
    val initialSize = initialStream.size()

    val quality = calculateQualityForTargetSize(initialSize, targetSizeKB)

    val compressedStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, compressedStream)

    return compressedStream.toByteArray()
}

fun calculateQualityForTargetSize(originalSize: Int, targetSizeKB: Int): Int {
    val targetSizeBytes = targetSizeKB * 1024
    val compressionRatio = targetSizeBytes.toFloat() / originalSize.toFloat()
    return (compressionRatio * 100).toInt().coerceIn(0, 100)
}