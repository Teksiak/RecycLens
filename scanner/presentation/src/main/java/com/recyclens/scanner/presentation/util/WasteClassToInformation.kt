package com.recyclens.scanner.presentation.util

import com.recyclens.core.domain.WasteClass
import com.recyclens.core.presentation.Question

fun WasteClass.toInformation(): Question = when(this) {
    WasteClass.PLASTIC -> Question.PLASTIC_BIN
    WasteClass.GLASS -> Question.GLASS_BIN
    WasteClass.BIO -> Question.BIO_BIN
    WasteClass.MIXED -> Question.MIXED_BIN
    WasteClass.ELECTRONICS -> Question.ELECTRONICS_BIN
    WasteClass.PAPER -> Question.PAPER_BIN
}