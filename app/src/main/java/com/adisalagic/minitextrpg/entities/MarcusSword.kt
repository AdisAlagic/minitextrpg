package com.adisalagic.minitextrpg.entities

class MarcusSword : Weapon() {
    override val damage: Damage
        get() = Damage(250)

    override fun getName(): String {
        return "Меч Маркуса"
    }

    override fun getDescription(): String {
        return "Когда-то служившему бравому войну меч теперь является напоминанием того, во что превратился мир"
    }
}