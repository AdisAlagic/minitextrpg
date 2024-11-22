package com.adisalagic.minitextrpg.entities

interface Item {
    val name: String
    val description: String
    val type get() = ItemType.ITEM
}

enum class ItemType {
    ITEM,
    WEAPON,
    CONSUMABLE
}