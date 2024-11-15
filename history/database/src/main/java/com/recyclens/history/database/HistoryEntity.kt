package com.recyclens.history.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.recyclens.core.domain.WasteClass
import java.time.LocalDateTime

@Entity(
    tableName = "history",
)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val wasteClass: WasteClass,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray,
    val date: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HistoryEntity

        if (id != other.id) return false
        if (wasteClass != other.wasteClass) return false
        if (!image.contentEquals(other.image)) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + wasteClass.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}
