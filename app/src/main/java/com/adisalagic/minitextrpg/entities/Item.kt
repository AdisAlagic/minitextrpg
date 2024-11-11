package com.adisalagic.minitextrpg.entities

interface Item {
    fun getName(): String
    fun getDescription(): String
    fun getType(): ItemType = ItemType.ITEM

    val name: String get() = getName()
    val description: String get() = getDescription()
    val type get() = getType()
}

enum class ItemType {
    ITEM,
    WEAPON,
    CONSUMABLE
}