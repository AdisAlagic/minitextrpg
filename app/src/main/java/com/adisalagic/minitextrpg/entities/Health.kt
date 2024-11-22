package com.adisalagic.minitextrpg.entities

import kotlin.math.max
import kotlin.math.min

class Health(private var maxHealth: Long) {
    private var _currentHealth = maxHealth
    val currentHealth: Long get() = _currentHealth
    val currentMaxHealth: Long get() = maxHealth

    fun applyAction(hp: Long, action: Action): Long {
        when (action) {
            Action.HURT -> _currentHealth -= hp
            Action.HEAL -> _currentHealth = min(maxHealth, _currentHealth + hp)
            Action.INCREASE_MAX_HP -> maxHealth += hp
        }
        return currentHealth
    }

    fun hurtBy(hp: Long): Long {
        return applyAction(hp, Action.HURT)
    }

    fun healBy(hp: Long): Long {
        return applyAction(hp, Action.HEAL)
    }

    fun increaseMaxHpBy(hp: Long): Long {
        return applyAction(hp, Action.INCREASE_MAX_HP)
    }

    enum class Action {
        HURT,
        HEAL,
        INCREASE_MAX_HP
    }
}