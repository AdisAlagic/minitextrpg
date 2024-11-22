package com.adisalagic.minitextrpg.viewmodels

import androidx.lifecycle.ViewModel
import com.adisalagic.minitextrpg.appendLine
import com.adisalagic.minitextrpg.compareTo
import com.adisalagic.minitextrpg.entities.Consumable
import com.adisalagic.minitextrpg.entities.Enemy
import com.adisalagic.minitextrpg.entities.Item
import com.adisalagic.minitextrpg.entities.MarcusKnight
import com.adisalagic.minitextrpg.entities.Player
import com.adisalagic.minitextrpg.entities.Weapon
import com.adisalagic.minitextrpg.locations.Garden
import com.adisalagic.minitextrpg.locations.Hall
import com.adisalagic.minitextrpg.locations.Location
import com.adisalagic.minitextrpg.locations.Options
import com.adisalagic.minitextrpg.percent
import com.adisalagic.minitextrpg.takeRandom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlin.math.max
import kotlin.math.min

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(Storage())
    val state = _state.asStateFlow()

    private val data get() = _state.value

    init {
        _state.update {
            it.copy(currentLocation = it.currentLocation.apply {
                onOptionClicked = { opt ->
                    when (opt.id) {
                        Options.LOOK_AROUND.ordinal -> {
                            val result = data.currentLocation.dropItem()
                            if (result != null) {
                                repeat(result.amount) { data.player.addItem(result.item) }
                                updateText("Найдено: ${result.item.name} ${if (result.amount > 1) "x${result.amount}" else ""}")
                            } else {
                                updateText("Ничего не нашел")
                            }
                        }

                        Options.AHEAD.ordinal -> {
                            _state.update { s -> s.copy(currentLocation = data.currentLocation.nextLocation!!.apply {
                                onOptionClicked = { opt ->
                                    when(opt.id) {
                                        Options.TALK.ordinal -> {
                                            updateText("Не разговорчив")
                                        }

                                        Options.ATTACK.ordinal -> {
                                            updateBattleText("Вы начали сражение")
                                            _state.update { s -> s.copy(state = State.BATTLE) }
                                        }
                                    }
                                }
                            }) }
                        }
                    }
                }
            })
        }
    }

    fun showEnter() {
        updateText(data.currentLocation.onEnterDescription)
    }


    private fun clearText() {
        _state.update { it.copy(currentText = "") }
    }

    fun attack() {
        var damageToEnemy = data.player.weapon.damage.rollDamage()
        if (data.enemy is MarcusKnight) {
            damageToEnemy -=
                if ((data.enemy as MarcusKnight).defending) (data.enemy.currentArmor * 1.5).toLong()
                else data.enemy.currentArmor
        }
        damageToEnemy = max(damageToEnemy, 0)
        if ((data.enemy as MarcusKnight).defending) data.enemy.stopDefend()
        data.enemy.currentHealth.hurtBy(damageToEnemy)
        updateBattleText("Вы атакуете на $damageToEnemy")
        nextTurn()
    }

    fun defend() {
        data.player.defend()
        updateBattleText("Вы защищаетесь...")
        nextTurn()
    }

    private fun enemyTurn() {
        val turn = if (data.enemy.currentHealth.currentHealth < 30.percent) {
            Enemy.EnemyTurn.USE_ITEM
        } else {
            data.enemy.rollTurn()
        }
        when (turn) {
            Enemy.EnemyTurn.ATTACK -> {
                val playerArmor =
                    if (data.player.isDefending)
                        (data.player.armor * 1.5).toLong()
                    else
                        data.player.armor
                val damage = data.enemy.currentWeapon.damage.rollDamage() - playerArmor
                data.player.health.hurtBy(damage)
                if (data.player.isDefending) data.player.stopDefending()
                updateBattleText("${data.enemy.name} атакует вас на $damage")
            }

            Enemy.EnemyTurn.DEFEND -> {
                data.enemy.defend()
                updateBattleText("${data.enemy.name} готовиться к удару")
            }

            Enemy.EnemyTurn.NONE -> updateBattleText("${data.enemy.name} смотрит на вас")
            Enemy.EnemyTurn.USE_ITEM -> {
                val item = data.enemy.inventory.takeRandom()
                if (item == null) {
                    updateBattleText("${data.enemy.name} смотрит в свои пустые карманы...")
                    return
                }
                if (item is Consumable) {
                    val effect = item.consume()
                    data.enemy.currentHealth.applyAction(effect.first, effect.second)
                    updateBattleText("${data.enemy.name} использует ${item.name}")
                }
            }
        }
        nextTurn()
    }

    private fun updateBattleText(action: String) {
        clearText()
        updateText(
            """
            $action
            ---
            Ваше здоровье: ${data.player.health.currentHealth}❤️
            Ваша броня: ${data.player.armor}🛡️
            Ваше оружие: ${data.player.weapon.name} ${data.player.weapon.damage.maxDamage}⚔️
            ---
            ${data.enemy.name}
            Здоровье врага: ${data.enemy.currentHealth.currentHealth}❤️
            Броня врага: ${data.enemy.currentArmor}🛡️
            Оружие врага: ${data.enemy.currentWeapon.name} ${data.enemy.currentWeapon.damage.maxDamage}⚔️
        """.trimIndent()
        )
    }

    fun useItem(item: Item) {
        if (item is Weapon) {
            val lastWeapon = data.player.weapon
            data.player.weapon = item
            data.player.dropItem(item)
            data.player.addItem(lastWeapon)
        }
        if (item is Consumable) {
            val effect = data.player.useItem(item)
            if (effect != null) data.player.health.applyAction(effect.first, effect.second)
        }
        if (data.state == State.BATTLE) {
            nextTurn()
        }
    }

    fun skipTurn() = nextTurn()

    fun nextTurn() {
        if (data.playersTurn == Turn.PLAYER || data.playersTurn == Turn.ENEMY) {
            _state.update { it.copy(playersTurn = Turn.MIDDLE, lastTurn = data.playersTurn) }
        }else if (data.playersTurn == Turn.MIDDLE) {
            if (data.lastTurn == Turn.PLAYER) {
                _state.update { it.copy(playersTurn = Turn.ENEMY, lastTurn = Turn.ENEMY) }
            } else {
                _state.update { it.copy(playersTurn = Turn.PLAYER, lastTurn = Turn.PLAYER) }
            }
        }
        if (data.playersTurn == Turn.ENEMY) {
            enemyTurn()
        }
    }

    private fun updateText(text: String) {
        _state.update { it.copy(currentText = data.currentText.appendLine(text)) }
    }

    data class Storage(
        val state: State = State.EXPLORING,
        val currentLocation: Location = Garden(),
        val player: Player = Player(),
        val currentText: String = "",
        val enemy: Enemy = MarcusKnight(),
        val playersTurn: Turn = Turn.PLAYER,
        val lastTurn: Turn = Turn.PLAYER
    )

    enum class Turn {
        PLAYER,
        ENEMY,
        MIDDLE
    }

    enum class State {
        CHATTING,
        BATTLE,
        EXPLORING
    }
}
