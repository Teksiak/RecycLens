package com.recyclens.history.database

import androidx.room.TypeConverter
import com.recyclens.core.domain.WasteClass
import java.time.LocalDateTime

class HistoryConverters {
    @TypeConverter
    fun fromDate(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun toDate(date: String): LocalDateTime = LocalDateTime.parse(date)

    @TypeConverter
    fun fromWasteClass(wasteClass: WasteClass): Int = wasteClass.id

    @TypeConverter
    fun toWasteClass(id: Int): WasteClass = WasteClass.fromId(id)

    @TypeConverter
    fun fromByteArray(image: ByteArray): String = image.contentToString()
}