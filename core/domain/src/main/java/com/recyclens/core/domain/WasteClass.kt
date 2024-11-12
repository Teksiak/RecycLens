package com.recyclens.core.domain

enum class WasteClass(val id: Int) {
    PLASTIC(0),
    GLASS(1),
    BIO(3),
    MIXED(4),
    ELECTRONICS(5),
    PAPER(6);

    companion object {
        fun fromId(id: Int): WasteClass {
            require(listOf(0, 1, 3, 4, 5, 6).contains(id)) { "Invalid id" }
            return entries.first { it.id == id }
        }
    }
}