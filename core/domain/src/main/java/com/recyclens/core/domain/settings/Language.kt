package com.recyclens.core.domain.settings

import java.util.Locale

// TODO: Remove isAvailable
enum class Language(
    val tag: String,
    val isAvailable: Boolean
) {
    ENGLISH("en", true),
    POLISH("pl", true);

    companion object {
        fun fromTag(tag: String): Language? {
            return entries.firstOrNull { it.tag == tag }
        }
        fun fromLocale(locale: Locale): Language? {
            return with(locale.language) {
                when {
                    startsWith("en") -> ENGLISH
                    startsWith("pl") -> POLISH
                    else -> null
                }
            }
        }
    }
}