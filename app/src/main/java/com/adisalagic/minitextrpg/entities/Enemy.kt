package com.adisalagic.minitextrpg.entities

interface Enemy {
    fun getName(): String
    fun getCurrentWeapon(): Weapon
    fun getCurrentHealth(): Health
    fun getCurrentArmor(): Long

    val name get() = getName()
    val currentWeapon get() = getCurrentWeapon()
    val currentHealth get() = getCurrentHealth()
    val currentArmor get() = getCurrentArmor()
}