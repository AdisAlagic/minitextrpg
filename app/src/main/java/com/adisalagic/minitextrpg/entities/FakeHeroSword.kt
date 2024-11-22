package com.adisalagic.minitextrpg.entities

class FakeHeroSword : Weapon() {
    override val damage: Damage
        get() = Damage(3000)

    override val name: String
        get() = "Фальшивый меч героя"

    override val description: String
        get() = "Меч героя, который забыл, кто он такой. Созданный для грязной работы и убийства не только нечисти..."
}
