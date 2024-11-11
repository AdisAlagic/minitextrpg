package com.adisalagic.minitextrpg.entities

abstract class Weapon : Item {
    abstract val damage: Damage
    override fun getType(): ItemType = ItemType.WEAPON
}