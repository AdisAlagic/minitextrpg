package com.adisalagic.minitextrpg.entities

class Player {
    var isDefending = false
    var weapon: Weapon = HeroSword()
    val health: Health = Health(5000)
    val armor: Long = 100
    private val inventory = mutableListOf<Item>()
    val playerInventory get() = inventory

    fun addItem(item: Item) {
        inventory.add(item)
    }

    fun useItem(item: Item): ConsumableEffect? {
        if (item !is Consumable) return null
        return if (inventory.remove(item))
            item.consume()
        else
            null
    }

    fun dropItem(item: Item) {
        inventory.remove(item)
    }

    fun defend() {isDefending = true}
    fun stopDefending() { isDefending = false}
}