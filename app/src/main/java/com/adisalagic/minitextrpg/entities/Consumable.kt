package com.adisalagic.minitextrpg.entities

typealias ConsumableEffect = Pair<Long, Health.Action>

abstract class Consumable : Item {
    abstract fun consume(): ConsumableEffect
    override fun getType(): ItemType = ItemType.CONSUMABLE
}