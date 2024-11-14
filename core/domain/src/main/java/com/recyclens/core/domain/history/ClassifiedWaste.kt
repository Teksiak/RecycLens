package com.recyclens.core.domain.history

import com.recyclens.core.domain.WasteClass
import java.time.LocalDateTime

data class ClassifiedWaste(
    val id: Int = 0,
    val wasteClass: WasteClass,
    val image: ByteArray,
    val date: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassifiedWaste

        if (date != other.date) return false
        if (wasteClass != other.wasteClass) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + wasteClass.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}