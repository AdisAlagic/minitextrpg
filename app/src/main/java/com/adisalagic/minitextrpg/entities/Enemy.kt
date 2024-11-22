package com.adisalagic.minitextrpg.entities

abstract class Enemy {
    abstract val name: String
    abstract val currentWeapon: Weapon
    val currentHealth: Health = Health(10000)
    abstract val currentArmor: Long
    abstract val inventory: MutableList<Item>
    abstract fun rollTurn(): EnemyTurn
    abstract fun defend()
    abstract fun stopDefend()
    abstract fun claim(): Enemy

    enum class EnemyTurn {
        ATTACK,
        DEFEND,
        NONE,
        USE_ITEM
    }
}