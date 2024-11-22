package com.adisalagic.minitextrpg.entities

import com.adisalagic.minitextrpg.takeRandom

class MarcusKnight : Enemy() {
    private var isDefending = false
    val defending get() = isDefending
    override val name: String
        get() = "Маркус, Поглащенный бездной рыцарь"
    override val currentWeapon: Weapon
        get() = MarcusSword()
    override val currentHealth: Health
        get() = Health(10000)
    override val currentArmor: Long
        get() = 130
    override val inventory: MutableList<Item>
        get() = mutableListOf(
            HealthPotion(),
            HealthPotion(),
            HealthPotion(),
            HealthPotion(),
        )

    override fun rollTurn(): EnemyTurn {
        val turn = EnemyTurn.entries.random()
        if (turn == EnemyTurn.USE_ITEM) {
            if (inventory.isEmpty() || currentHealth.currentHealth == currentHealth.currentMaxHealth) {
                val turns = EnemyTurn.entries.toMutableList().apply { remove(EnemyTurn.USE_ITEM) }
                return turns.random()
            }
        }
        return turn
    }

    override fun claim(): Enemy {
        return this
    }

    override fun defend() {
        isDefending = true
    }

    override fun stopDefend() {
        isDefending = false
    }
}