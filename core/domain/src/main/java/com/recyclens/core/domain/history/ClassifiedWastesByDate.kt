package com.recyclens.core.domain.history

import java.time.LocalDate

data class ClassifiedWastesByDate(
    val date: LocalDate,
    val classifiedWastes: List<ClassifiedWaste>
)
