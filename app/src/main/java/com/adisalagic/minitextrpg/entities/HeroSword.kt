package com.adisalagic.minitextrpg.entities

class HeroSword : Weapon() {
    override val damage: Damage
        get() = Damage(150)

    override val name: String
        get() = "Меч героя"

    override val description: String
        get() = "Меч героя, который был создан для спасения людей"
}