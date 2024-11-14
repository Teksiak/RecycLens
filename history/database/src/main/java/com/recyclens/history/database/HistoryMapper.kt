package com.recyclens.history.database

import com.recyclens.core.domain.history.ClassifiedWaste

fun HistoryEntity.toClassifiedWaste(): ClassifiedWaste {
    return ClassifiedWaste(
        id = id,
        wasteClass = wasteClass,
        image = image,
        date = date
    )
}

fun ClassifiedWaste.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        wasteClass = wasteClass,
        image = image,
        date = date
    )
}