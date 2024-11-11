package com.adisalagic.minitextrpg.locations

import com.adisalagic.minitextrpg.ItemDrop
import com.adisalagic.minitextrpg.ItemDropResult
import com.adisalagic.minitextrpg.entities.FakeHeroSword
import com.adisalagic.minitextrpg.entities.HealthPotion
import com.adisalagic.minitextrpg.entities.Option
import com.adisalagic.minitextrpg.takeRandom

class Garden(
    override val prevLocation: Location? = null,
    override val nextLocation: Location? = Hall(),
    override var onOptionClicked: (Option) -> Unit = {},
) : Location {
    private val itemsToDrop = mutableListOf(
        ItemDrop(FakeHeroSword(), 0.0), ItemDrop(HealthPotion(), 70.0, 2)
    )
    override val name: String
        get() = "Сад"
    override val onEnterDescription: String
        get() = "Прекрасный сад находится в середине замка. Очень жаль, " +
                "что вам не получиться здесь остаться подольше"
    override val options: List<Option>
        get() = listOf(
            Option("Вперед") { onOptionClicked(it) },
            Option("Осмотреться") { onOptionClicked(it) }
        )

    override val drops: List<ItemDrop>
        get() = itemsToDrop

    override fun dropItem(): ItemDropResult? {
        return itemsToDrop.takeRandom().drop()
    }


}