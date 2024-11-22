package com.adisalagic.minitextrpg.entities

class HealthPotion : Consumable() {
    private val effect = ConsumableEffect(5000, Health.Action.HEAL)
    override fun consume(): ConsumableEffect {
        return effect
    }

    override val name: String
        get() = "Большое зелье здоровья"

    override val description: String
        get() = "Зелье восстанавливающие то, чего так иногда не хватает. Восстанавливает ${effect.first} здоровья"
}