package com.recyclens.history.database

import com.recyclens.core.domain.history.HistoryWaste

fun HistoryEntity.toHistoryWaste(): HistoryWaste {
    return HistoryWaste(
        id = id,
        wasteClass = wasteClass,
        image = image,
        date = date
    )
}

fun HistoryWaste.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        wasteClass = wasteClass,
        image = image,
        date = date
    )
}