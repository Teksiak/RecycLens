package com.recyclens.core.domain.settings

enum class Language(
    val tag: String
) {
    ENGLISH("en"),
    POLISH("pl");

    companion object {
        fun fromTag(tag: String): Language? {
            return entries.firstOrNull { it.tag == tag }
        }
    }
}