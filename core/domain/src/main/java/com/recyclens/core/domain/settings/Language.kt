package com.recyclens.core.domain.settings

import java.util.Locale

enum class Language(
    val tag: String,
    val isAvailable: Boolean
) {
    POLISH("pl", true),
    ENGLISH("en", true);

    companion object {
        fun fromLocale(locale: Locale): Language? {
            return when(locale.language) {
                "US", "GB" -> ENGLISH
                "PL" -> POLISH
                else -> null
            }
        }
    }
}