package com.adisalagic.minitextrpg.locations

import com.adisalagic.minitextrpg.ItemDropResult

class Hall(
    override val prevLocation: Location? = null,
    override val nextLocation: Location? = null
) : Location {
    override val name: String
        get() = "Зал"

    override val onEnterDescription: String
        get() = "Вы заходите в зал. Перед вами стоит рыцарь, который познал бездну. У вас нет другого " +
                "пути, как убить его"
    override val options: List<String>
        get() = listOf(
            "Назад",
            "Поговорить"
        )

    override fun dropItem(): ItemDropResult? {
        return null
    }
}