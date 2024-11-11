package com.adisalagic.minitextrpg.entities

class MarcusKnight : Enemy {
    override fun getName(): String = "Маркус, Поглащенный бездной рыцарь"
    override fun getCurrentWeapon(): Weapon = MarcusSword()
    override fun getCurrentHealth(): Health = Health(10000)
    override fun getCurrentArmor(): Long = 100
}