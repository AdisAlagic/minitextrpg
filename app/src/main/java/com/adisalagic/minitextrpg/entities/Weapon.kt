package com.adisalagic.minitextrpg.entities

abstract class Weapon : Item {
    abstract val damage: Damage
    override val type: ItemType
        get() = ItemType.WEAPON
}