package com.adisalagic.minitextrpg.entities

class MarcusSword : Weapon() {
    override val damage: Damage
        get() = Damage(500)

    override val name: String
        get() = "Меч Маркуса"

    override val description: String
        get() = "Когда-то служившему бравому войну меч теперь является напоминанием того, во что превратился мир"
}