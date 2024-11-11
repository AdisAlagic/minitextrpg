package com.adisalagic.minitextrpg.entities

class FakeHeroSword : Weapon() {
    override val damage: Damage
        get() = Damage(3000)

    override fun getName(): String {
        return "Фальшивый меч героя"
    }

    override fun getDescription(): String {
        return "Меч героя, который забыл, кто он такой. Созданный для грязной работы и убийства не только нечисти..."
    }
}