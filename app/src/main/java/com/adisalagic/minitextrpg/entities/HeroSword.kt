package com.adisalagic.minitextrpg.entities

class HeroSword : Weapon() {
    override val damage: Damage
        get() = Damage(100)

    override fun getName(): String {
        return "Меч героя"
    }

    override fun getDescription(): String {
        return "Меч героя, который был создан для спасения людей"
    }
}