package com.adisalagic.minitextrpg.locations

import com.adisalagic.minitextrpg.ItemDrop
import com.adisalagic.minitextrpg.ItemDropResult
import com.adisalagic.minitextrpg.entities.Option

interface Location {
    val name: String
    val onEnterDescription: String
    val prevLocation: Location?
    val nextLocation: Location?
    var onOptionClicked: (Option) -> Unit
    val options: List<Option>
    val drops: List<ItemDrop> get() = emptyList()

    fun dropItem(): ItemDropResult?
}