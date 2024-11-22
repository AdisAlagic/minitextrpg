package com.adisalagic.minitextrpg.locations

import com.adisalagic.minitextrpg.ItemDropResult
import com.adisalagic.minitextrpg.Option
import com.adisalagic.minitextrpg.entities.Option

class Hall(
    override val prevLocation: Location? = null,
    override val nextLocation: Location? = null,
    override var onOptionClicked: (Option) -> Unit = {}
) : Location {
    override val name: String
        get() = "Зал"

    override val onEnterDescription: String
        get() = "Вы заходите в зал. Перед вами стоит рыцарь, который познал бездну. У вас нет другого " +
                "пути, как убить его. Дверь закрылась за вами"
    override val options: List<Option>
        get() = listOf(
            Option(Options.TALK, "Поговорить", onOptionClicked),
            Option(Options.ATTACK, "Атаковать", onOptionClicked)
        )

    override fun dropItem(): ItemDropResult? {
        return null
    }
}