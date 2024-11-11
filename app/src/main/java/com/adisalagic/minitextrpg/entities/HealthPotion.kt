package com.adisalagic.minitextrpg.entities

class HealthPotion : Consumable() {
    private val effect = ConsumableEffect(50000, Health.Action.HEAL)
    override fun consume(): ConsumableEffect {
        return effect
    }

    override fun getName(): String {
        return "Большое зелье здоровья"
    }

    override fun getDescription(): String {
        return "Зелье восстанавливающие то, чего так иногда не хватает. Восстанавливает ${effect.first} здоровья"
    }
}